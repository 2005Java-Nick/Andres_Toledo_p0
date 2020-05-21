package com.revature.andres.db;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.andres.notebook.Notebook;
import com.revature.andres.notebook.Page;
import com.revature.andres.security.Encryption;
import com.revature.andres.user.User;

public class PSQLConnection {
	//project run settings set env variables
	private final String url=System.getenv("URL_DATABASE");
	private final String user=System.getenv("USER_DATABASE");
	private final String password=System.getenv("USER_PASSWORD");
	
	//One Logger for entire Application
	private static Logger log=Logger.getRootLogger();
	
	//Establish connection to database
	public Connection connect() {
	        Connection conn = null;
	        try {
	            conn = DriverManager.getConnection(url, user, password);
	            log.info("PSQLConnection:connect: Connected to the PostgreSQL server successfully.");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            log.error("PSQLConnection:connect: Could not connect to database");
	            return null;
	        }
	        return conn;
	 }
	
	 //Gets user pages
	 public Notebook getPages(String username) {

	        String SQL = "select * from get_pages('"+username+"')";

	        try (Connection conn = connect();
	                Statement stmt = conn.createStatement();
	                ResultSet rs = stmt.executeQuery(SQL)) {
	            	// display page information
	        		log.info("PSQLConnection:getPages: Attempting to retrieve user Notebook for user "+username);
	            	return buildNotebook(rs);
	        } catch (SQLException ex) {
	            log.error("PSQLConnection:getPages: Could not load user Notebook for user "+username);
	            return null;
	        }
	 }
	 
	 //Generates a notebook from retrieved user pages from getPages
	 public Notebook buildNotebook(ResultSet rs) throws SQLException {
	     Notebook temp=new Notebook();
	     try
	     {
		     rs.next();
			 do{
				 int id=rs.getInt("page_id");
				 String title = rs.getString("page_title");
				 String text =rs.getString("page_text");
				 temp.addPage(new Page(id,title,text,rs.getBoolean("page_encrypted")));
		     }while (rs.next());
	     }catch(NullPointerException e)
	     {
	    	 log.error("PSQLconnection:buildnotebook: No pages for user found continuing from local backup");
	    	 return null;
	     }
		 return temp;
	 }
	 
	 //Finds notebook for current user
	 public int getNotebookID(String username) {
	        String SQL = "select get_notebook_id('"+username+"')";
	        int nID = 0;
	        try (Connection conn = connect();
	                Statement stmt = conn.createStatement();
	                ResultSet rs = stmt.executeQuery(SQL)) {
	            rs.next();
	            nID = rs.getInt(1);
	            log.info("PSQLConnection:getNotebookID: Found user Notebook");
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	            log.error("PSQLConnection:getNotebookID: Could not find user notebook");
	            return -1;
	        }
	        return nID;
	 }
	 
	 //Save notebook data and replaces old data through a stored procedure in database
	 public boolean saveNotebookData(String username,List<Page>pages)
	 {
		 
		 int nID=getNotebookID(username);
		 /*
		 try {
			 Connection conn = connect();
			 CallableStatement upperProc = conn.prepareCall("call removeOldPagesNotebook( "+nID+" )");
			 upperProc.execute();
			 upperProc.close();
			 log.info("PSQLConnection:saveNotebookData:Old pages were dropped");
		 }
		 catch(Exception e) {
			 e.printStackTrace();
			 log.error("PSQLConnection:saveNotebookData:Unable to drop old pages");
			 //return false;
		 }
		 */
		 try {
			 log.info("PSQLConnection:saveNotebookData:Old data is obsolete updating changes");
			 for(Page page : pages)
			 {
				 Connection conn = connect();
				 if(page.getPageId()>=0)
				 {
					 CallableStatement updPage = conn.prepareCall("call updatePage( "+page.getPageId()+",'"+page.getTitle()+"','"+page.getText()+"',"+page.isEncrypted()+" )");
					 updPage.execute();
					 updPage.close();
					 log.info("PSQLConnection:saveNotebookData:Page "+page.getTitle()+" was updated successully");
				 }else
				 {
					 CallableStatement insPage = conn.prepareCall("call insertPageUserNotebook( "+nID+",'"+page.getTitle()+"',"+page.isEncrypted()+",'"+page.getText()+"' )");
					 insPage.execute();
					 insPage.close();
					 log.info("PSQLConnection:saveNotebookData:Page "+page.getTitle()+" was saved successully");
				 }
			 } 
			 return true;
		 }catch (Exception e){
			 log.info("PSQLConnection:saveNotebookData: Unable to save data");
			 return false;
		 }
	 }
	 
	 //Creates new user in database
	 public boolean createUser(User u,String seed,Encryption e)
	 {
		 try {
			 log.info("PSQLConnection:createUser:Validating user database");
			 Connection conn = connect();
			 CallableStatement upperProc = conn.prepareCall("call addNewUser( '"+e.decryptString(seed, u.getUsername())+"','"+u.getUsername()+"','"+u.getPassword()+"' )");
			 upperProc.execute();
			 upperProc.close();
			 log.info("PSQLConnection:createUser:Created user "+e.decryptString(seed, u.getUsername())+" was saved successully");
			 return true;
		 }catch (Exception e1){
			 log.info("PSQLConnection:createUser: User already exists");
			 return true;
		 }
	 }
	 
	 //Verifies user credentials 
	 public String verifyUserCredentials(User u,String seed,Encryption e)
	 {
		 try (Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery("select * from verify_user('"+e.decryptString(seed,u.getUsername())+"')")) {
			 // display page information
			 log.info("PSQLConnection:verifyUserCredentials: Attempting to validate credentials for user "+e.decryptString(seed,u.getUsername()));
			 String temp="";
			 rs.next();
			 do{
				 String pwd = rs.getString("password");
				 if(e.decryptString(seed, u.getPassword()).equalsIgnoreCase(e.decryptString(seed, pwd)))
				 {
					 temp=u.getUsername()+".udb";
				 }
		     }while (rs.next());
            	
			 return temp;
			 
			 
	        }catch(NullPointerException e1)
		 	{
	        	log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return "";
		 	}catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	            log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return "";
	        } 
	 }
	 
	 public void deletePage(int pgID)
	 {
		 try {
			 log.info("PSQLConnection:deletePage:Atempting to delete page from database");
			 Connection conn = connect();
			 CallableStatement updPage = conn.prepareCall("call deletePage( "+pgID+")");
			 updPage.execute();
			 updPage.close();
			 log.info("PSQLConnection:saveNotebookData:Page "+pgID+" was deleted successully");
			 
		 }catch (Exception e){
			 log.info("PSQLConnection:saveNotebookData: Unable to save data");
		 }
	 }
	 
	 public boolean sharePage(String user,int pageID)
	 {
		 int noteID=getNotebookID(user);
		 try {
			 log.info("PSQLConnection:deletePage:Atempting to delete page from database");
			 Connection conn = connect();
			 CallableStatement updPage = conn.prepareCall("call sharePage( "+noteID+","+pageID+")");
			 updPage.execute();
			 updPage.close();
			 log.info("PSQLConnection:sharePage:Page "+pageID+" was shared successully");
			 return true;
			 
		 }catch (Exception e){
			 log.info("PSQLConnection:sharePage: Unable to share page");
			 return false;
		 }
	 }
	 
	
}

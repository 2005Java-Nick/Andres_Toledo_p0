package com.revature.andres.security;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.revature.andres.interfaces.AuthenticationInterface;
import com.revature.andres.io.FileIO;
import com.revature.andres.user.User;

public class Authentication implements AuthenticationInterface{

	//-----------------------------------Variables-----------------------------------------
	
	//Handles encryption
	private Encryption encryption;
	
	//Current user working directory
	private String workingUserDirectory="";
	
	//Handles IO
	private FileIO fileIo;
	
	//Handles Logger
	private static Logger log = Logger.getRootLogger();

	//-----------------------------------Methods-----------------------------------------
	
	//Constructor
	public Authentication() {
		encryption=new Encryption();
		this.setFileIo(new FileIO());
	}
	
	//Verify user credentials
	public boolean verifyUserCredentials(User u,String seed){
		this.workingUserDirectory=this.fileIo.verifyUserExists(u, seed, this.encryption);
		if(this.workingUserDirectory.contains(".udb"))
		{
			log.warn("Authentication:verifyUserCredentials:User "+this.workingUserDirectory.replace(".udb", "")+" logged in");
			return true;
		}else
		{
			try {
				log.warn("Authentication:verifyUserCredentials:User attempted login from "+InetAddress.getLocalHost().getHostName()+ " but password was incorrect");
			} catch (UnknownHostException e) {
				log.warn("Authentication:verifyUserCredentials:User attempted login but password was incorrect from unknown host");
				e.printStackTrace();
			}
			return false;
		}
	}
	
	//Loads user workspace
	public User loadData()
	{
		if(workingUserDirectory.length()>0)
		{
			log.info("Aunthentication:loadData: User directory ready for use");
			return this.fileIo.getUser(this.workingUserDirectory);
		}
		else
		{
			log.fatal("Aunthentication:loadData: No working directory found");
			return null;
		}
	}
	
	//Creates new access token 
	public AccessToken createAccessToken() {
		AccessToken access =new AccessToken(true);
		return access;
	}
	
	//Verifies if session is still active
	public boolean validateSession(Session s) {
		if(s.getTimeRemaining()<=0)
		{
			return false;
		}else
		{
			return true;
		}
	}
	
	//Saves users data
	public boolean saveUser(User u) {
		return this.getFileIo().saveUser(u,this.getWorkingUserDirectory()); 
	}
	
	//Creates new user file
	public boolean createUser(User u,String seed) {
		return this.fileIo.createUser(u, seed, this.encryption);
	}
	

	
	//------------------------------------Getters and Setters----------------------------------
	private FileIO getFileIo() {
		return fileIo;
	}

	private void setFileIo(FileIO fileIo) {
		this.fileIo = fileIo;
	}

	public String getWorkingUserDirectory() {
		return workingUserDirectory;
	}

	public void setWorkingUserDirectory(String workingUserDirectory) {
		this.workingUserDirectory = workingUserDirectory;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		Authentication.log = log;
	}
	
	public Encryption getEncryption() {
		return encryption;
	}

}

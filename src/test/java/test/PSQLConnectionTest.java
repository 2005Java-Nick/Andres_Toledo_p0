package test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import com.revature.andres.db.PSQLConnection;
import com.revature.andres.notebook.Notebook;
import com.revature.andres.security.Encryption;
import com.revature.andres.user.User;

public class PSQLConnectionTest {

	PSQLConnection con;
	@Before
	public void Setup()
	{
		con=new PSQLConnection();
		con.connect();
	}
	@Test
	public void testConnect() {
		Connection result = con.connect();
		assertEquals("If result is instance of Connection shoudld be true", true, result instanceof Connection);
	}
	@Test
	public void testConnectandBuildNotebook() {
		Notebook result = con.getPages("Andres");
		assertEquals("If result is instance of Notebook then shoudld be true", true, result instanceof Notebook);
	}
	
	@Test
	public void testGetNotebookIDExistingUser() {
		int notebookid=con.getNotebookID("test123");
		boolean result=false;
		if(notebookid>=0)
		{
			result=true;
		}
		assertEquals("If user exists should return NotebookId", true,result);
	}
	
	@Test
	public void testGetNotebookIDNewUser() {
		int notebookid=con.getNotebookID("Pedro");
		boolean result=false;
		if(notebookid<0)
		{
			result=true;
		}
		assertEquals("If user is new should return negative NotebookId", false,result);
	}
	
	@Test
	public void testGetNotebookIDNonExistingUser() {
		int notebookid=con.getNotebookID("asdfasdfasdfasdf");
		boolean result=false;
		if(notebookid<0)
		{
			result=true;
		}
		assertEquals("If user is non existant should return negative NotebookId", false,result);
	}
	
	@Test
	public void testVerifyUserCredentials()
	{
		String un="194XpTFYg89fxWOs4C5uNA==";
		String pwd="pvssyv+2Zf2c6BCS1sSiSQ==";
		Encryption e=new Encryption();
		User test= new User(un,pwd);
		String validation = con.verifyUserCredentials(test, "test123", e);
		boolean result=false;
		if(validation.length()>0)
		{
			result=true;
		}
		assertEquals("Valid user credentials", true,result);
	}

}

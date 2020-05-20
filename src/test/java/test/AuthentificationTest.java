package test;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.revature.andres.db.PSQLConnection;
import com.revature.andres.security.AccessToken;
import com.revature.andres.security.Authentication;
import com.revature.andres.security.Encryption;
import com.revature.andres.security.Session;
import com.revature.andres.user.User;

public class AuthentificationTest {

	Authentication authenticator;
	Encryption encrypt;
	PSQLConnection con;
	
	@Before
	public void Setup()
	{
		authenticator =new Authentication();
		encrypt=new Encryption();
		con=new PSQLConnection();
	}
	
	//Test Authentication verifyUserCredentials(User u,String seed)
	
	@Test
	public void testVerifyCredentialsValidUser() {
		String username="CxM81Ov3ZS2Vn43OJxmaJdBhooCVJYBl";
		String seed="Password";
		String password=encrypt.encryptString(seed, seed);
		boolean result=authenticator.verifyUserCredentials(new User(username,password), seed, con);
		assertEquals("User with valid credentials should return true", true,result);
	}

	@Test
	public void testVerifyCredentialsInvalidUser() {
		boolean result=authenticator.verifyUserCredentials(null, "Wrong seed", con);
		assertEquals("User with invalid credentials should return false", false,result);
	}
	
	//Test Authentication loadData()
	
	@Test
	public void testLoadDataValidDirectory() {
		authenticator.setWorkingUserDirectory("./"+"CxM81Ov3ZS2Vn43OJxmaJdBhooCVJYBl"+".udb");
		boolean result= authenticator.loadData() instanceof User;
		assertEquals("Should not be able to load file from existing directory",true,result);
	}
	
	@Test
	public void testLoadDataInvalidDirectory() {
		authenticator.setWorkingUserDirectory("./"+"afg"+".udb");
		boolean result= authenticator.loadData() instanceof User;
		assertEquals("Should not be able to load file from non-existing directory",false,result);
	}

	//Test Authentication createAccessToken()
	
	@Test
	public void testCreateAccessToken() {
		boolean result= authenticator.createAccessToken() instanceof AccessToken;
		assertEquals("Should not be able to load file from non-existing directory",true,result);
	}

	//Test Authentication validateSession()
	
	@Test
	public void testValidateSessionValidSession()
	{
		Session s =new Session(10);
		assertEquals("Session with time remaining is a valid session",true, authenticator.validateSession(s));
	}
	
	@Test
	public void testValidateSessionInvalidSession()
	{
		Session s =new Session(0);
		assertEquals("Session with 0 time remaining is an invalid session",false, authenticator.validateSession(s));	
	}
	
	@Test
	public void testValidateSessionInvalidSession1()
	{
		Session s =new Session(-1);
		assertEquals("Session with negative time remaining is an invalid session",false, authenticator.validateSession(s));	
	}

	//Test Authentication saveUser()
	
	@Test
	public void testSaveUserCurrentUser()
	{
		authenticator.setWorkingUserDirectory("./rlwUhVru2LHF2D5gXUd2IXwmxNpNbTiU.udb");
		boolean result=authenticator.saveUser(new User("rlwUhVru2LHF2D5gXUd2IXwmxNpNbTiU",encrypt.encryptString("password", "password")));
		assertEquals("Valid user with working directory shoudl be saved successfully", true,result);
	}
	
	@Test
	public void testSaveUserCurrentUserInvalidDirectory()
	{
		authenticator.setWorkingUserDirectory("./aswUhVru2LHF2D5gXUd2IXwmxNpNbTiU.udb");
		boolean result=authenticator.saveUser(new User("rlwUhVru2LHF2D5gXUd2IXwmxNpNbTiU",encrypt.encryptString("password", "password")));
		assertEquals("Valid user with invalid working directory should be saved successfully due to nature of IO save user method", true,result);
	}
}

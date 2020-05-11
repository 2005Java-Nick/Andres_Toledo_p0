package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revature.andres.gui.LoginScreen;
import com.revature.andres.interfaces.AuthenticationInterface;
import com.revature.andres.notebook.ExperienceManager;
import com.revature.andres.security.Encryption;
import com.revature.andres.user.User;

@RunWith(MockitoJUnitRunner.class)
public class ExperienceManagerInterfaceTest {

	ExperienceManager manager;
	Encryption encrypt;
	@Mock
	AuthenticationInterface authenticatorI;
	
	@Before
	public void Setup()
	{
		manager=new ExperienceManager();
		manager.setUser(new User());
		manager.getUser().setUsername("asdfasdf");
		manager.getUser().setPassword("asdfasdf");
		encrypt=new Encryption();
	}
	//Testing ExperienceManager validatePassword()
	@Test
	public void testPasswordLenghtLessThan5() {
		assertEquals("Passwords can not be less than 5 characters", false ,manager.validatePassword("asdf"));
	}
	
	@Test
	public void testPasswordLenghtMoreThan20() {
		assertEquals("Passwords can not be more than 20 characters", false ,manager.validatePassword("asdfdsfasdfasdl;kj;ljas;dlkfjaladfasdf"));
	}
	
	@Test
	public void testPasswordCannotHaveWhiteSpaces() {
		assertEquals("Passwords can not be less than 5 characters", false ,manager.validatePassword("as df"));
		assertEquals("Passwords can not be less than 5 characters", false ,manager.validatePassword("as d  f"));
	}
	
	//Testing ExperienceManager validateUsername()
	@Test
	public void testUsernameLenghtLessThan5() {
		assertEquals("Passwords can not be less than 5 characters", false ,manager.validateUsername(""));
	}
	
	@Test
	public void testUsernameLenghtMoreThan20() {
		assertEquals("Passwords can not be more than 20 characters", false ,manager.validateUsername("asdfdsfasdfasdl;kj;ljas;dlkfjaladfasdf"));
	}
	
	@Test
	public void testUsernameCannotHaveWhiteSpaces() {
		assertEquals("Passwords can not be less than 5 characters", false ,manager.validateUsername("as df"));
		assertEquals("Passwords can not be less than 5 characters", false ,manager.validateUsername("as d  f"));
	}
	
	@Test
	public void testUsernameValidLettersOnly() {
		assertEquals("Valid passwords can contain only letters", true ,manager.validateUsername("Andres"));
	}
	
	//Testing ExperienceManager displayLogin()
	
	@Test
	public void testDisplayLoginFailed() {
		manager.setLogin(null);
		boolean result= manager.getLogin() instanceof LoginScreen;
		assertEquals("Login screen should have been created", false,result);
	}
	
	@Test
	public void testDisplayLoginWhenCalled() {
		manager.displayLogin();
		boolean result= manager.getLogin() instanceof LoginScreen;
		assertEquals("Login screen should have been created", true,result);
	}
	
	//Testing ExperienceManager requestLogin(User user,String seed)
	
	@Test
	public void testRequestLoginValidUser() {
		String username="CxM81Ov3ZS2Vn43OJxmaJdBhooCVJYBl";
		String seed="Password";
		String password=encrypt.encryptString(seed, seed);
		boolean result=manager.requestLogin(new User(username,password), seed);
		assertEquals("User with valid credentials should return true", true,result);
	}

	@Test
	public void testRequestLoginInvalidUser() {
		boolean result=manager.requestLogin(null, "Wrong seed");
		assertEquals("User with invalid credentials should return false", false,result);
	}

	//Testing ExperienceManager loadUserData()
	
	@Test
	public void testLoadUserDataValidDirectory() {
		manager.getAuthenticator().setWorkingUserDirectory("./"+"CxM81Ov3ZS2Vn43OJxmaJdBhooCVJYBl"+".udb");
		manager.loadUserData();
		boolean result= manager.getUser() instanceof User;
		assertEquals("Should not be able to load file from existing directory",true,result);
	}
	
	@Test
	public void testLoadUserDataInvalidDirectory() {
		manager.getAuthenticator().setWorkingUserDirectory("./"+"afg"+".udb");
		manager.loadUserData();
		boolean result= manager.getUser() instanceof User;
		assertEquals("Should not be able to load file from non-existing directory",false,result);
	}
	
	//Testing ExperienceManager saveCurrentUser()
	@Test
	public void testSaveUserCurrentUser()
	{
		manager.setUser(new User("rlwUhVru2LHF2D5gXUd2IXwmxNpNbTiU",encrypt.encryptString("password", "password")));
		manager.getAuthenticator().setWorkingUserDirectory("./rlwUhVru2LHF2D5gXUd2IXwmxNpNbTiU.udb");
		boolean result=manager.saveCurrentUser();
		assertEquals("Valid user with working directory shoudl be saved successfully", true,result);
	}
	
	@Test
	public void testSaveUserCurrentUserInvalidDirectory()
	{
		manager.setUser(new User("rlwUhVru2LHF2D5gXUd2IXwmxNpNbTiU",encrypt.encryptString("password", "password")));
		manager.getAuthenticator().setWorkingUserDirectory("./aswUhVru2LHF2D5gXUd2IXwmxNpNbTiU.udb");
		boolean result=manager.saveCurrentUser();
		assertEquals("Valid user with invalid working directory should be saved successfully due to nature of IO save user method", true,result);
	}
}

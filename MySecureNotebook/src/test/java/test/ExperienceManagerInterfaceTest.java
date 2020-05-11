package test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revature.andres.interfaces.AuthenticationInterface;
import com.revature.andres.notebook.ExperienceManager;
import com.revature.andres.user.User;

@RunWith(MockitoJUnitRunner.class)
public class ExperienceManagerInterfaceTest {

	ExperienceManager manager;
	
	@Mock
	AuthenticationInterface authenticatorI;
	
	@Before
	public void Setup()
	{
		manager=new ExperienceManager();
		manager.setUser(new User());
		manager.getUser().setUsername("asdfasdf");
		manager.getUser().setPassword("asdfasdf");
	}
	//Testing UserExeperience validatePassword()
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
	
	//Testing UserExeperience validateUsername()
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
	
	//Mock test creates user method;
	@Test
	public void testValidateRequestCreateAccount() {
		//when(authenticatorI.createUser("asdfd", "asdfasd","")).thenReturn(true);
		//manager.au=authenticatorI;
		//manager.requestCreateAccount("asdfd", "asdfasd","");
		//verify(authenticatorI).createUser("asdfd", "asdfasd","");

	}
	
	
}

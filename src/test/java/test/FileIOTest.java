package test;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.revature.andres.io.FileIO;
import com.revature.andres.security.Encryption;
import com.revature.andres.user.User;

public class FileIOTest {

	FileIO io;
	User testUser;
	Encryption encrypt;
	
	@Before
	public void Setup()
	{
		io= new FileIO();
		encrypt=new Encryption();
		testUser=new User("Username","Password");
	}
	
	//Test saveUser(User p,String workingDirectory)
	
	@Test
	public void testSaveUserWithValidDirectory() {
		assertEquals("Writing to existing directory should return true", true,io.saveUser(testUser, "./testCreate.txt"));
	}
	
	@Test
	public void testSaveUserWithInvalidDirectory() {
		assertEquals("Writing to existing directory should stil create a new file", true,io.saveUser(testUser, "./abcdefghijk=_"));
	}
	
	//Test getUser(String filename)
	
	@Test
	public void testGetUserWithValidDirectory() {
		User u = io.getUser("./testCreate.txt");
		boolean equals = testUser.equals(u);
		assertEquals("Reading from existing directory should return true", true,equals);
	}
	
	@Test
	public void testGetUserWithInvalidDirectory() {
		User u = io.getUser("./NotValidDirectory");
		assertEquals("Reading from invalid directory should return null", null,u);
	}
	
	//Test Verify create User
	
		@Test
		public void testCreatUserExistingUser() {
			String username="CxM81Ov3ZS2Vn43OJxmaJdBhooCVJYBl";
			String seed="Password";
			String password=encrypt.encryptString(seed, seed);
			boolean result=io.createUser(new User(username,password),seed, encrypt);
			assertEquals("Should return false for user already exists",false,result);
		}
		
		@Test
		public void testCreatUserNonExistingUser() {
			byte[] array = new byte[30]; // Random new username length is bounded by 20
		    new Random().nextBytes(array);
		    String usernameD = new String(array, Charset.forName("UTF-8"));
		    String seed="Password";
		    String username=encrypt.encryptString(seed, usernameD);
			String password=encrypt.encryptString(seed, seed);
			boolean result=io.createUser(new User(username,password),seed, encrypt);
			assertEquals("Should return false and create new user directory",false,result);
		}	
	
	//Test Verify UserExists
	
	//CxM81Ov3ZS2Vn43OJxmaJdBhooCVJYBl
	//Password
	
	@Test
	public void testUserExists() {
		String username="CxM81Ov3ZS2Vn43OJxmaJdBhooCVJYBl";
		String seed="Password";
		String password=encrypt.encryptString(seed, seed);
		String result=io.verifyUserExists(new User(username,password),seed, encrypt);
		assertEquals("Valid user credentials should return user working directory",username+".udb",result);
	}	
	
	@Test
	public void testUseExistsInvalidUser() {
		String username="Non Existing user";
		String seed="Pass1234";
		String password=encrypt.encryptString(seed, seed);
		String result=io.verifyUserExists(new User(username,password),seed, encrypt);
		assertEquals("Valid user credentials should return user working directory","",result);
	}	
	
	

}

package test;


import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import com.revature.andres.security.Authentication;

public class AuthentificationTest {

	Authentication authenticator;
	
	@Before
	public void Setup()
	{
		authenticator =new Authentication();
		
	}
	
	@Test
	public void testCreateDirectory() throws IOException {
	//	authenticator
		//authenticator.createUserDirectory("UDMSN", ".udb");
		//assertEquals("", true, authenticator.verifyUserDirectory("UDMSN", ".udb",""));
	}
	
	@Test
	public void testVerifyUserDirectory() throws IOException {
	//	assertEquals("", true, authenticator.verifyUserDirectory("UDMSN", ".udb",""));
	}
	

}

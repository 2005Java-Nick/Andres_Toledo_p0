package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.andres.security.Session;

public class SessionTest {

	Session sTest;
	
	@Before
	public void Setup()
	{
		sTest=new Session();
		sTest.setTimeRemaining(10);
		
	}
	
	@Test
	public void testSetGetTimeRemainging() {
		sTest.setTimeRemaining(5);
		assertEquals("Set to 5 seconds should return 5", 5,sTest.getTimeRemaining());
	}
	
	@Test
	public void testMinusSeconds() {
		sTest.minusSeconds(1);
		assertEquals("Time 10 seconds minus one should be 9", 9,sTest.getTimeRemaining());
	}
	
	@Test
	public void testMinusSecondsForZero() {
		sTest.setTimeRemaining(0);
		sTest.minusSeconds(1);
		assertEquals("Time 0 seconds should not go below 0", 0,sTest.getTimeRemaining());
	}
	
	


}

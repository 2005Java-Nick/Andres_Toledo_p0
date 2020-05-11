package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import com.revature.andres.security.Encryption;

public class EncryptionTest {

	Encryption encryption;
	
	@Before
	public void Setup()
	{
		encryption=new Encryption();
	}
	
	@Test
	public void testEncryptDecrypt() {
		String text="TestText";
		String seed="seed";
		String result=encryption.decryptString(seed, encryption.encryptString(seed, text));
		assertEquals("Encrypting and Decrypting with the appropriate seed should result in the original text", text,result);
	}
	
	@Test
	public void testEncryptDecryptWithInvalidSeed() {
		String text="TestText";
		String seed="seed";
		String result=encryption.decryptString("different seed", encryption.encryptString(seed, text));
		assertEquals("Encrypting and Decrypting with the different seeds should not be possible", "Decryption not possible",result);
	}

}

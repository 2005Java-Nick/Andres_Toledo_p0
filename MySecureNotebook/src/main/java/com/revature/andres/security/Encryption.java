package com.revature.andres.security;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

import com.revature.andres.interfaces.EncryptionInterface;

public class Encryption implements EncryptionInterface{

	//-----------------------------------Variables-----------------------------------------
	
	private StandardPBEStringEncryptor encryptor;
	private static Logger log=Logger.getRootLogger();
	
	//-----------------------------------Methods-----------------------------------------
	
	public String encryptString(String seed, String text) {
		//Encrypts text
		encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(seed);
		return encryptor.encrypt(text);
	}

	public String decryptString(String seed,String text) {
		//Decrypts text
		try {
		encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(seed);
		return encryptor.decrypt(text);
		}catch(EncryptionOperationNotPossibleException e)
		{
			log.warn("Encryption:decrypt: Decryption not possible");
			return "Decryption not possible";
		}catch(IllegalArgumentException e1)
		{
			log.warn("Encryption:decrypt: Decryption not possible");
			return "Decryption not possible";
		}
	}


}

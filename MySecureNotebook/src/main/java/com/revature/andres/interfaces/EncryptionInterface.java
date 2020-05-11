package com.revature.andres.interfaces;

public interface EncryptionInterface {
	public String encryptString(String seed,String text);
	public String decryptString(String seed,String text);
}

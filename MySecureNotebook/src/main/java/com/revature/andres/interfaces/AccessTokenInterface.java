package com.revature.andres.interfaces;

public interface AccessTokenInterface {
	public boolean isExpired();
	public void createAccessToken(boolean verified);
	
}

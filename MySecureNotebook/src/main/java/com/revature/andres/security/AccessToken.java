package com.revature.andres.security;

import java.io.Serializable;

import com.revature.andres.interfaces.AccessTokenInterface;

public class AccessToken implements AccessTokenInterface,Serializable{

	//-----------------------------------Variables-----------------------------------------
	
	//Serialization UID
	private static final long serialVersionUID = -5287451088738310226L;
	
	//User verified flag	
	private boolean verified;
	
	//Stores session data
	private Session session;
	
	//-----------------------------------Methods---------------------------------------------
	
	//Constructor
	public AccessToken(boolean verified) {
		this.createAccessToken(verified);
	}
	
	//Verifies if session is expired
	public boolean isExpired() {
		if(this.getSession().getTimeRemaining()<=0)
		{
			return false;
		}else
		{
			return true;
		}
	}

	//Creates new access token with specified parameters 
	public void createAccessToken(boolean verified) {
		this.setVerified(verified);
		this.setSession(new Session());
	}	
	
	//---------------------------------Getters and Setters------------------------------------
	
	public boolean isVerified() {
		return verified;
	}

	private void setVerified(boolean verified) {
		this.verified = verified;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}

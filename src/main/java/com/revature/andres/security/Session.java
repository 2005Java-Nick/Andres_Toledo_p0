package com.revature.andres.security;

import java.io.Serializable;

public class Session implements Serializable{

	//-----------------------------------Variables-----------------------------------------
	
	//Serialization UID
	private static final long serialVersionUID = 5396937925128246097L;
	
	//Stores remaining session time
	private int timeRemaining;
	
	//-----------------------------------Methods-----------------------------------------
	
	public Session()
	{
		//this.timeRemaining=3600;
		this.timeRemaining=120;
	}
	public Session(int timeSeconds)
	{
		this.timeRemaining=timeSeconds;
	}
	
	//-----------------------------------Getters and Setters----------------------------------
	
	public int getTimeRemaining() {
		return timeRemaining;
	}
	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}
	public void minusSeconds(int seconds) {
		if(this.getTimeRemaining()>0)
			this.setTimeRemaining(this.getTimeRemaining()-seconds);
	}

}

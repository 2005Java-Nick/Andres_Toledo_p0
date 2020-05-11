package com.revature.andres.notebook;

import java.io.Serializable;

public class Page implements Serializable{

	//-----------------------------------Variables-----------------------------------------
	
	//Serialization UID
	private static final long serialVersionUID = 6243034337875048415L;
	
	//Page Title
	private String title;
	
	//Page text content
	private String text;
	
	//Page encryption flag
	private boolean encrypted;
	
	//-----------------------------------Methods-------------------------------------------
	
	//Constructor
	public Page(String title) {
		super();
		this.setTitle(title);
		this.text="";
	}

	//-----------------------------------Getters and Setters-----------------------------------
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

}

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
	
	//For database placeholder
	private int pageId;
	
	//Page encryption flag
	private boolean encrypted;
	
	//-----------------------------------Methods-------------------------------------------
	
	//Constructor
	public Page(String title) {
		super();
		this.setTitle(title);
		this.text="";
	}
	
	//--parameters constructor
	public Page(String title,String text,boolean status) {
		super();
		this.setTitle(title);
		this.setText(text);
		this.setEncrypted(status);
	}
	
	//--NEWDB page all parameters constructor
	public Page(int id,String title,String text,boolean status) {
		super();
		this.setTitle(title);
		this.setText(text);
		this.setPageId(id);
		this.setEncrypted(status);
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

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

}

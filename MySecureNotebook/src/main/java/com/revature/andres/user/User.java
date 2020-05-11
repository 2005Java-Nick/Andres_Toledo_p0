package com.revature.andres.user;

import java.io.Serializable;

import com.revature.andres.interfaces.UserInterface;
import com.revature.andres.notebook.Notebook;
import com.revature.andres.security.AccessToken;

public class User implements UserInterface,Serializable {

	//-----------------------------------Variables-----------------------------------------
	
	//Serialization UID
	private static final long serialVersionUID = 7390925351396024685L;
	
	//User access token
	private AccessToken token;
	
	//User data
	private String username;
	private String password;
	private Notebook notebook;
	
	//-----------------------------------Methods-----------------------------------------
	
	public User() {
		this.setNotebook(new Notebook());
		this.setAccessToken(null);
	}
	
	public User(String username,String password) {
		this.setUsername(username);
		this.setPassword(password);
		this.setNotebook(new Notebook());
	}

	//------------------------------Getters and setters-----------------------------------------
	
	public AccessToken getAcessToken() {
		return token;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccessToken(AccessToken token) {
		this.token=token;
	}

	public Notebook getNotebook() {
		return notebook;
	}

	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}

	@Override
	public String toString() {
		String userData= "Username : "+this.getUsername()
		+"\nPassword : "+this.getPassword()+"\nPages in Notebook : "+this.getNotebook().getPages().size();
		return userData;
	}

	@Override
	public boolean equals(Object obj) {
		User u=(User)obj;
		if(u.getPassword().equals(this.getPassword()) && u.getUsername().equals(this.getUsername()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}	
	
	
}

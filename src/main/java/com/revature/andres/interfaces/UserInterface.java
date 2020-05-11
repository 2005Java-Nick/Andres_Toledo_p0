package com.revature.andres.interfaces;

import com.revature.andres.notebook.Notebook;
import com.revature.andres.security.AccessToken;

public interface UserInterface {

	public AccessToken getAcessToken();
	public void setAccessToken(AccessToken token);
	public String getUsername();
	public void setUsername(String name);
	public String getPassword();
	public void setPassword(String password);
	public Notebook getNotebook();
	public void setNotebook(Notebook notebook);
	
}

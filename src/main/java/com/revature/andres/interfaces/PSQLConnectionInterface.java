package com.revature.andres.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import com.revature.andres.notebook.Notebook;
import com.revature.andres.notebook.Page;
import com.revature.andres.security.Encryption;
import com.revature.andres.user.User;

public interface PSQLConnectionInterface {
	public Connection connect();
	//Gets user pages
	public Notebook getPages(String username);
	//Generates a notebook from retrieved user pages from getPages
	public Notebook buildNotebook(ResultSet rs);
	//Finds notebook for current user
	public int getNotebookID(String username);
	//Save notebook data and replaces old data through a stored procedure in database
	public boolean saveNotebookData(String username,List<Page>pages);
	//Creates new user in database
	public boolean createUser(User u,String seed,Encryption e);
	//Verifies user credentials 
	public String verifyUserCredentials(User u,String seed,Encryption e);
}

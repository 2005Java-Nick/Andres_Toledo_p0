package com.revature.andres.interfaces;


import com.revature.andres.db.PSQLConnection;
import com.revature.andres.security.AccessToken;
import com.revature.andres.security.Session;
import com.revature.andres.user.User;

public interface AuthenticationInterface {
	
	//Verifies user credentials on login
	public boolean verifyUserCredentials(User u,String seed,PSQLConnection con);
	//Loads user data
	public User loadData();
	//Creates new access token
	public AccessToken createAccessToken();
	//Verifies if session is still active
	public boolean validateSession(Session s);
	//Saves users data
	public boolean saveUser(User u);
	//Creates new user
	public boolean createUser(User u,String seed);
}

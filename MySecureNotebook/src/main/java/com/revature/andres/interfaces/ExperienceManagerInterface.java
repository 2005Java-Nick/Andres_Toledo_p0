package com.revature.andres.interfaces;

import com.revature.andres.user.User;

public interface ExperienceManagerInterface {
	
	//Prints message no title
	public void printMessage(String msg);
	//Prints message with specified title
	public void printMessage(String msg,String title);
	//Requests input from user
	public String requestInput(String msg);
	//Prompts user for password
	public String requestPassword(String msg);
	//Prompts password input and verifies it
	public String requestPassword();
	//Displays login prompt
	public void displayLogin();
	//Handles user login requests
	public boolean requestLogin(User user,String seed);
	//Handles user data request
	public void loadUserData();
	//Saves user data
	public boolean saveCurrentUser();
	//Validates user inputs
	public boolean validateUsername(String input);
	public boolean validatePassword(String input);
	//Handles creating new user account
	public boolean createAccount(User u,String seed);

}

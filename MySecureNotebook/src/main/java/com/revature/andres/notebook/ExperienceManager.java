package com.revature.andres.notebook;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.apache.log4j.Logger;

import com.revature.andres.gui.LoginScreen;
import com.revature.andres.interfaces.AuthenticationInterface;
import com.revature.andres.interfaces.ExperienceManagerInterface;
import com.revature.andres.security.Authentication;
import com.revature.andres.user.User;

public class ExperienceManager implements ExperienceManagerInterface {

	//-----------------------------------Variables-----------------------------------------
	
	//One Experience manager for entire Application
	private static ExperienceManager program;
	
	//One Logger for entire Application
	private static Logger log=Logger.getRootLogger();
	
	//Handles authentication and Encryption
	private Authentication authenticator;
	
	//Current Application User
	private User user;
	
	//Login screen 
	private LoginScreen login;
	
	//Mock variables
	public AuthenticationInterface au;
	
	//-----------------------------------Methods-----------------------------------------
	
	//Creates Singleton
	public static ExperienceManager getExperienceManager() {
		if(program==null)
		{
			program= new ExperienceManager();
			log.info("ExperienceManager: Singleton created");
		}
		return program;
	}
	
	//Class Constructor
	public ExperienceManager()
	{
		this.authenticator=new Authentication();
		log.info("Program started running.");
	}
	
	//printMessage,requestInput,requestPassword methods handle user inputs and outputs to user
	
	//Prints message no title
	public void printMessage(String msg)
	{
		JOptionPane.showMessageDialog(null, msg);
		log.info(msg);
	}	
	
	//Prints message with specified title
	public void printMessage(String msg,String title)
	{
		JOptionPane.showMessageDialog(null, msg,title,JOptionPane.INFORMATION_MESSAGE);
		log.info(msg);
	}	
	
	//Requests input from user
	public String requestInput(String msg)
	{
		log.info(msg);
		return JOptionPane.showInputDialog(msg);
	}	
	
	//Prompts user for password
	public String requestPassword(String msg) {
		JPasswordField passwordField=new JPasswordField(10);
		JOptionPane.showMessageDialog(null, passwordField,msg,JOptionPane.DEFAULT_OPTION);
		return String.valueOf(passwordField.getPassword());		
	}
	
	//Prompts password input and verifies it
	public String requestPassword()
	{
		String seed = requestPassword("Type in your password"); 

		while(!getAuthenticator().getEncryption().decryptString(seed,getUser().getPassword()).equals(seed))
		{
			seed = requestPassword("Password incorrect try again"); 
		}
		return seed;
	}

	//Displays login screen
	public void displayLogin() {
		login=new LoginScreen();
		login.setVisible(true);
		log.info("Displayed login screen");
	}

	//Request user login
	public boolean requestLogin(User user,String seed) {
		boolean loginResult=authenticator.verifyUserCredentials(user,seed);
		if(!loginResult)
		{
			log.info("ExperienceManager:requestLogin: Failed login request for username : "+ getAuthenticator().getEncryption().decryptString(seed, user.getUsername()));
		}
		return loginResult;
	}
	
	//Loads user data for current user working directory
	public void loadUserData()
	{
		this.setUser(this.getAuthenticator().loadData());
		log.info("ExperienceManager:loadUserData: User information loaded");
	}
	
	//Saves current user
	public boolean saveCurrentUser()
	{
		return this.getAuthenticator().saveUser(this.user);
	}

	//Validates Username
	public boolean validateUsername(String input) {
		if(input.replaceAll("[^a-zA-Z0-9]+", "").length()!=input.length()|| input.length() < 5 || input.length()>20)
		{
			//Validates only letters and numbers in username
			printMessage("The Username must have a length of 5 to 20 character.\nValid characters are letters a-z or A-Z and numbers 0-9 no special characters allowed.\nPlease re-enter username: \n","Error");
			return false;
		}else 
		{
			return true;
		}
		
	}

	//Validates Password
	public boolean validatePassword(String input) {
		if(input.length() > 5 && input.length()<20 && input.replaceAll("[\\s]+", "").length()==input.length())
		{
			return true;
		}else 
		{
			printMessage("The password must have a length of 5 to 20 character.\nPlease re-enter password:\n");
			return false;
		}
	}

	//Handles creating new users
	public boolean createAccount(User u,String seed) {
		//Mock Test: this.au.createUser(user, password);
		return authenticator.createUser(u,seed);
	}

	//---------------------------------------Getters and Setters----------------------------------------
	
	public Authentication getAuthenticator() {
		return this.authenticator;
	}

	public void setAuthenticator(Authentication authenticator) {
		this.authenticator=authenticator;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

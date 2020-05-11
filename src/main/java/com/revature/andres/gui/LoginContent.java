package com.revature.andres.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.revature.andres.interfaces.LoginInterface;
import com.revature.andres.notebook.ExperienceManager;
import com.revature.andres.user.User;

public class LoginContent extends JPanel implements ActionListener,LoginInterface{

	//-------------------------------------Variables-------------------------------------
	
	//Serialization UID
	private static final long serialVersionUID = 8916465511563052553L;
	
	//Creates Buttons
	private JButton btnSubmit;
	private JButton btnRegister;
	
	//Creates Labels
	private JLabel labUsername;
	private JLabel labPassword;
	
	//Creates Input Fields
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	
	//Creates three panels for components
	private JPanel panUsername;
	private JPanel panPassword;
	private JPanel panButtons;
	
	//Parent window
	private LoginScreen parent;
	
	//References Singleton
	private static ExperienceManager manager=ExperienceManager.getExperienceManager();
	
	//-------------------------------------Methods-------------------------------------
	
	//Initializes and set content for panel
	public LoginContent(LoginScreen parent)
	{
		super();
		//Sets Parent
		this.setParentFrame(parent);
		//Sets Layout
		this.setLayout(new GridLayout(3, 1));
		//Initializes Buttons
		this.setBtnSubmit(new JButton("Submit"));
		this.setBtnRegister(new JButton("Register"));
		this.setLabUsername(new JLabel("Username:"));
		this.setLabPassword(new JLabel("Password:"));
		//Initializes input fields
		this.setTxtUsername(new JTextField(15));
		this.setTxtPassword(new JPasswordField(15));
		//Initializes Panels
		this.setPanUsername(new JPanel(new FlowLayout()));
		this.setPanPassword(new JPanel(new FlowLayout()));
		this.setPanButtons(new JPanel(new FlowLayout()));
		//Adds Action Listeners
		this.getBtnRegister().addActionListener(this);
		this.getBtnSubmit().addActionListener(this);
		//Adds components to panels
		this.getPanUsername().add(this.getLabUsername());
		this.getPanPassword().add(this.getLabPassword());	
		this.getPanUsername().add(this.getTxtUsername());
		this.getPanPassword().add(this.getTxtPassword());
		this.getPanButtons().add(this.getBtnSubmit());
		this.getPanButtons().add(this.getBtnRegister());
		//Sets Style
		this.getPanUsername().setBackground(Color.WHITE);
		this.getPanPassword().setBackground(Color.WHITE);
		this.getPanButtons().setBackground(Color.WHITE);
		//Adds Panels to this class
		this.add(getPanUsername());
		this.add(getPanPassword());
		this.add(getPanButtons());
	}

	//Attempts to login
	public void login() {
		//If inputs are validate and user credentials are verified then load user data request a new access token and login
		if(this.validateInputs()&& manager.requestLogin(manager.getUser(), String.valueOf(this.getTxtPassword().getPassword())))
		{	
				manager.loadUserData();
				manager.getUser().setAccessToken(manager.getAuthenticator().createAccessToken());
				this.getParentFrame().openLoginWindow();
		}
		else
		{
			manager.printMessage("User does not exist");
		}
	}
	
	//Attempts to create user
	public void createUser()
	{
		//Validates inputs and tries to create new user if user doesn't exist
		if(this.validateInputs())
		{
			if(manager.createAccount(manager.getUser(), String.valueOf(this.getTxtPassword().getPassword())))
			{
				manager.printMessage("User Created");
			}else
			{
				manager.printMessage("User Exists");
			}
		}
	}
	
	//Validates button actions
	@Override
	public void actionPerformed(ActionEvent action) {
		//Attempts to login
		if(action.getSource().equals(btnSubmit))
		{
			login();
		}
		//Attempts to register User
		if(action.getSource().equals(btnRegister))
		{
			createUser();
		}
		
	}
	
	//Validates inputs
	public boolean validateInputs()
	{
		//Validates user credentials and returns a boolean value true if user exists
		if(manager.validatePassword(String.valueOf(this.getTxtPassword().getPassword())))
		{
			if(manager.validateUsername(this.getTxtUsername().getText())) {
				String username=manager.getAuthenticator().getEncryption().encryptString(String.valueOf(this.getTxtPassword().getPassword()), this.getTxtUsername().getText());
				String password=manager.getAuthenticator().getEncryption().encryptString(String.valueOf(this.getTxtPassword().getPassword()), String.valueOf(this.getTxtPassword().getPassword()));
				User user=new User(username, password);
				manager.setUser(user);
			}else {return false;}
		}else {return false;}
		return true;
	}
	
	//-------------------------------------Getters and Setters-------------------------------------
	
	public JButton getBtnSubmit() {
		return btnSubmit;
	}

	public void setBtnSubmit(JButton btnSubmit) {
		this.btnSubmit = btnSubmit;
	}

	public JButton getBtnRegister() {
		return btnRegister;
	}

	public void setBtnRegister(JButton btnRegister) {
		this.btnRegister = btnRegister;
	}

	public JLabel getLabUsername() {
		return labUsername;
	}

	public void setLabUsername(JLabel labUsername) {
		this.labUsername = labUsername;
	}

	public JLabel getLabPassword() {
		return labPassword;
	}

	public void setLabPassword(JLabel labPassword) {
		this.labPassword = labPassword;
	}

	public JTextField getTxtUsername() {
		return txtUsername;
	}

	public void setTxtUsername(JTextField txtUsername) {
		this.txtUsername = txtUsername;
	}

	public JPasswordField getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(JPasswordField txtPassword) {
		this.txtPassword = txtPassword;
	}

	public JPanel getPanUsername() {
		return panUsername;
	}

	public void setPanUsername(JPanel panUsername) {
		this.panUsername = panUsername;
	}

	public JPanel getPanPassword() {
		return panPassword;
	}

	public void setPanPassword(JPanel panPassword) {
		this.panPassword = panPassword;
	}

	public JPanel getPanButtons() {
		return panButtons;
	}

	public void setPanButtons(JPanel panButtons) {
		this.panButtons = panButtons;
	}

	public LoginScreen getParentFrame() {
		return parent;
	}

	public void setParentFrame(LoginScreen parent) {
		this.parent = parent;
	}

}

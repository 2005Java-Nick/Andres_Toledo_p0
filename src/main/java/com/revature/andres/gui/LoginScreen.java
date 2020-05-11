package com.revature.andres.gui;

import javax.swing.JFrame;


public class LoginScreen extends JFrame{

	//-------------------------------------Variables-------------------------------------

	//Serialization UID if ever needed
	private static final long serialVersionUID = 5940873584589611699L;
	//JPanel Content
	private LoginContent content;
	//Main program window child form
	private MainProgramWindow programWindow;
	
	//-------------------------------------Methods-------------------------------------
	
	//Constructor: initializes GUI parameters
	public LoginScreen()
	{
		this.setContent(new LoginContent(this));
		this.setTitle("My Secure Notebook Login");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setContentPane(this.getContent());
		this.setSize(400,200);
	}
	
	//Opens login window
	public void openLoginWindow()
	{
		this.setVisible(false);
		this.setProgramWindow(new MainProgramWindow(content.getTxtUsername().getText(),this));
		this.content.getTxtPassword().setText("");
		this.getProgramWindow().setVisible(true);
	}
	
	//------------------------------Getters and Setters-------------------------------------

	public LoginContent getContent() {
		return content;
	}

	public void setContent(LoginContent content) {
		this.content = content;
	}

	public MainProgramWindow getProgramWindow() {
		return programWindow;
	}

	public void setProgramWindow(MainProgramWindow programWindow) {
		this.programWindow = programWindow;
	}
	
}

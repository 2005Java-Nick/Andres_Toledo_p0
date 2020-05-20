package com.revature.andres.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.revature.andres.notebook.ExperienceManager;
import com.revature.andres.notebook.Notebook;

public class MainProgramWindow extends JFrame implements WindowListener{

	//-------------------------------------Variables-------------------------------------
	
	//Serialization UID if ever needed
	private static final long serialVersionUID = 4166101993013519022L;
	
	//Singleton Experience manager
	private static ExperienceManager manager= ExperienceManager.getExperienceManager();
	
	//User Interface title string variables
	private String userName="";
	private String pageName="";
	
	//JPanel of content
	private MainProgramContent content;
	
	//Parent Login screen for call back
	private LoginScreen parent;
	
	//One Logger for entire Application
	private static Logger log=Logger.getRootLogger();
	
	//Decides which data to use moving forward
	
	//-------------------------------------Methods-------------------------------------

	//Initializes UI components
	public MainProgramWindow(String username,LoginScreen parent)
	{
		this.userName=username;
		Notebook userNotebook=manager.getConnector().getPages(username);
		if(userNotebook!=null)
		{
			manager.getUser().setNotebook(userNotebook);
			manager.getUser().setAccessToken(manager.getAuthenticator().createAccessToken());
			log.info("MainProgramWindow:Constructor: Correctly loaded user notbook from database");
		}else
		{
			log.info("MainProgramWindow:Constructor: Error loading data from database proceding to use local copy");
		}
		this.setParentLogin(parent);
		this.setContent(new MainProgramContent(this));
		this.setUserName(username);
		this.setSize(800, 800);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		SessionTimer timer =new SessionTimer();
		timer.start();
		this.setTitle("Welcome: " + this.getUserName()+" Page: "+pageName+"	     Session Time Remaining: "+manager.getUser().getAcessToken().getSession().getTimeRemaining()+" seconds");
		this.setContentPane(this.getContent());
		this.addWindowListener(this);
	}
	
	//-------------------------------------Getters and Setters-------------------------------------
	
	public MainProgramContent getContent() {
		return content;
	}


	public void setContent(MainProgramContent content) {
		this.content = content;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPageName() {
		return pageName;
	}


	public void setPageName(String pageName) {
		this.pageName = pageName;
	}


	public LoginScreen getParentLogin() {
		return parent;
	}

	public void setParentLogin(LoginScreen parent) {
		this.parent = parent;
	}

	//-------------------------------------Inner Class-------------------------------------
	
	//Handles Session for user access token
	public class SessionTimer extends Thread{
		public void run()
		{
			while (manager.getAuthenticator().validateSession(manager.getUser().getAcessToken().getSession()))
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				manager.getUser().getAcessToken().getSession().minusSeconds(1);
				setTitle("Welcome: " + getUserName()+"     Page: "+pageName+"     Session Time Remaining: "+manager.getUser().getAcessToken().getSession().getTimeRemaining()+" seconds");
			}
			manager.getConnector().saveNotebookData(getUserName(),content.getPages());
			manager.printMessage("Session timed out please login again");
			getParentLogin().setVisible(true);
			dispose();
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		manager.getConnector().saveNotebookData(getUserName(),content.getPages());
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}

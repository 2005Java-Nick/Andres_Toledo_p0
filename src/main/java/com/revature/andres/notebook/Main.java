package com.revature.andres.notebook;

public class Main {

	//Singleton program manager
	public static ExperienceManager program= ExperienceManager.getExperienceManager();
	
	public static void main(String [] args)
	{
		//Initializes program
		program.displayLogin();
	}

}

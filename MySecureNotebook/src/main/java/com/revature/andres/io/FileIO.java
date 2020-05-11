package com.revature.andres.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import com.revature.andres.interfaces.FileIOInterface;
import com.revature.andres.security.Encryption;
import com.revature.andres.user.User;

public class FileIO implements FileIOInterface{

	private static Logger log = Logger.getRootLogger();
	
	//Saves user to working directory file
	public boolean saveUser(User p,String workingDirectory) {
		try (FileOutputStream fos = new FileOutputStream(workingDirectory,false);ObjectOutputStream oos = new ObjectOutputStream(fos)){
			oos.writeObject(p);
			return true;
		} catch (FileNotFoundException e) {
			log.error("FileIO:saveUser:File not found");
			return false;
		} catch (IOException e) {
			log.error("FileIO:saveUser:IoException");
			e.printStackTrace();
			return false;
		}

	}

	//Gets user from directory
	@Override
	public User getUser(String filename) {	
		User u= null;
		try(FileInputStream fis = new FileInputStream(filename); ObjectInputStream ois = new ObjectInputStream(fis)){
			u=(User)ois.readObject();
			return u;
		} catch (FileNotFoundException e) {
			log.error("FileIO:getUser:File not found");
			return null;
		} catch (IOException e) {
			log.error("FileIO:getUser:IOException");
			return null;
		} catch (ClassNotFoundException e) {
			log.error("FileIO:getUser:ClassNotFoundException");
			return null;
		}
	}
	
	//Creates user if directory does not exist
	public boolean createUser(User u,String seed,Encryption e)
	{
		File folder = new File("./");
		File[] listOfFiles = folder.listFiles();
		boolean found=true;
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()&&listOfFiles[i].getName().contains(".udb")) {
			  try {
				  String du=e.decryptString(seed, listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length()-4));
				  String un=e.decryptString(seed, u.getUsername());
				  if(du.equalsIgnoreCase(un))
				  {
					  return false;
				  }
				} catch (Exception e2) {
					log.error("FileIO:createUser:Decryption Error");
				}
		  }
		}
		try {
		      File myObj = new File("./"+u.getUsername()+".udb");
		      if (myObj.createNewFile()) {
		        log.info("FileIO:createUser:User File created");
		      } else {
		        
		        log.info("FileIO:createUser:User already exists");
		      }
		    } catch (IOException e3) {
		      return false;
		    }
		this.saveUser(u,u.getUsername()+".udb");
		return found;
	}
	
	//Verifies user and returns user working directory
	public String verifyUserExists(User u,String seed,Encryption e)
	{
		File folder = new File("./");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()&&listOfFiles[i].getName().contains(".udb")) {
			  try {
				  String du=e.decryptString(seed, listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length()-4));
				  String un=e.decryptString(seed, u.getUsername());
				  if(du.equalsIgnoreCase(un))
				  {
					  log.info("FileIO:verifyUserExists: User File found ignore Decryption Errors if you see this message");
					  return listOfFiles[i].getName();
				  }
				} catch (Exception e2) {
					log.info("FileIO:verifyUserExists: Decryption Error");
				}
		  }
		}
		return "";
	}

}

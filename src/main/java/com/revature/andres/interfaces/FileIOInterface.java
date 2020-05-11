package com.revature.andres.interfaces;

import com.revature.andres.user.User;

public interface FileIOInterface {
	public boolean saveUser(User p,String workingDirectory);
	public User getUser(String filename);
}

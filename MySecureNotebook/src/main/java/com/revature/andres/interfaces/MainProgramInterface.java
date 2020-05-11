package com.revature.andres.interfaces;

public interface MainProgramInterface {
	//Sets Notebook to first page
	public void setFirstPage();
	//Changes page to number specified
	public void changePage(int i);
	//Validates if a page is encrypted
	public void verifyPageEncrypted();
	//Sets GUI components according to page pointers
	public void validatePageIndex();
	//Saves user changers
	public void saveChanges();
	//Encrypts current page
	public boolean encryptCurrentPage();
	//Decrypts current page
	public boolean decryptCurrentPage();
	
}

package com.revature.andres.notebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.revature.andres.interfaces.NotebookInterface;

public class Notebook implements NotebookInterface,Serializable{

	//-----------------------------------Variables-----------------------------------------
	
	//Serialization UID
	private static final long serialVersionUID = 3775728357045861439L;
	
	//List of pages in Notebook
	private List<Page>pages=new LinkedList<Page>();
	
	//NEW: notebook name
	
	private String name;
	
	//-------------------------------------Methods-----------------------------------------
	
	//Constructor
	public Notebook() {
		this.pages=new ArrayList<Page>();
	}
	
	//Adds new page to notebook
	public void addPage(Page p)
	{
		this.getPages().add(p);
	}
	//Removes page from Notebook
	public void removePage(int index)
	{
		if(index>=0 && index<this.getPages().size())
			this.getPages().remove(index);
	}
	
	//NEW: returns amount of notebook pages
	public int getPageCount()
	{
		return this.getPages().size();
	}
	
	//---------------------------------Getters and Setters-------------------------------
	
	//Returns a list of pages
	public List<Page> getPages() {
		return pages;
	}
	//Sets pages List
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

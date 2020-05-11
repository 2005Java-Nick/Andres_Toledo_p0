package com.revature.andres.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

import com.revature.andres.interfaces.MainProgramInterface;
import com.revature.andres.notebook.ExperienceManager;
import com.revature.andres.notebook.Page;

public class MainProgramContent extends JPanel implements MouseListener,KeyListener,MainProgramInterface{


	//-----------------------------------Variables-------------------------------------
	
	//Serialization UID if ever needed
	private static final long serialVersionUID = -4807585536193855673L;
	
	//Logger
	private static Logger log = Logger.getRootLogger();
	
	//Parent Window
	private MainProgramWindow parent;
	
	//Buttons
	private JMenu btnPreviousPage;
	private JMenu btnNextPage;
	private JMenu btnNewPage;
	private JMenu btnEncryptPage;
	private JMenu btnDecryptPage;
	private JMenu btnDeletePage;
	
	//Text Area
	private JTextArea txtPage;
	private JScrollPane bar;
	
	//Menu
	private JMenuBar jMenuBar;
	
	//Array of current pages
	private int pageIndex;
	private List<Page> pages;
	
	//Manager singleton
	private static ExperienceManager manager=ExperienceManager.getExperienceManager();
	
	//-------------------------------------Methods-------------------------------------
	
	//Constructor : Initializes UI elements
	public MainProgramContent(MainProgramWindow parent) {
		super();
		
		//Sets Parent
		this.setParent(parent);
		this.setLayout(new GridLayout(1, 1));
		
		//Initialize variables
		this.setJMenuBar(new JMenuBar());
		this.setBtnPreviousPage(new JMenu("Previous Page"));
		this.setBtnNextPage(new JMenu("Next Page"));
		this.setBtnNewPage(new JMenu("New Page"));
		this.setBtnEncryptPage(new JMenu("Encrypt Page"));
		this.setBtnDecryptPage(new JMenu("Decrypt Page"));
		this.setBtnDeletePage(new JMenu("Delete Page"));
		this.setTxtPage(new JTextArea(""));
		this.setBar(new JScrollPane(this.getTxtPage()));
		this.getTxtPage().setLineWrap(true);
		this.setPages(manager.getUser().getNotebook().getPages());
	
		//Add action listeners
		
		this.getTxtPage().addKeyListener(this);
		this.getBtnPreviousPage().addMouseListener(this);
		this.getBtnNextPage().addMouseListener(this);
		this.getBtnEncryptPage().addMouseListener(this);
		this.getBtnDecryptPage().addMouseListener(this);
		this.getBtnNewPage().addMouseListener(this);
		this.getBtnDeletePage().addMouseListener(this);

		
		//Add text field to form
		this.add(BorderLayout.CENTER,bar);
		
		//Add JMenu to for form
		this.txtPage.setBackground(Color.WHITE);
		this.txtPage.setForeground(Color.BLACK);
		this.getTxtPage().setFont(new Font("Times New Roman", Font.PLAIN, 20));
		this.getJMenuBar().add(this.getBtnPreviousPage());
		this.getJMenuBar().add(this.getBtnNextPage());
		this.getJMenuBar().add(this.getBtnEncryptPage());
		this.getJMenuBar().add(this.getBtnDecryptPage());
		this.getJMenuBar().add(this.getBtnNewPage());
		this.getJMenuBar().add(this.getBtnDeletePage());
		//this.getJMenuBar().add(this.getJMenu());
		this.parent.setJMenuBar(this.getJMenuBar());
		
		//Initial values
		this.setFirstPage();

	}
	
	//Sets Notebook to first page
	public void setFirstPage()
	{
		//Validates starting conditions
		if(this.getPages().size()>0)
		{
			this.getTxtPage().setEnabled(true);
			this.pageIndex=0;
			this.changePage(pageIndex);
			validatePageIndex();
		}else
		{
			this.getTxtPage().setText("");
			this.getTxtPage().setEnabled(false);
			pageIndex=-1;
			validatePageIndex();
		}
	}
	
	//Changes page to number specified
	public void changePage(int i) {
		this.getTxtPage().setText(pages.get(i).getText());
		this.getParent().setPageName(pages.get(i).getTitle());
	}

	//Validates if a page is encrypted
	public void verifyPageEncrypted() {
		if(this.getPageIndex()>=0)
			{
			if(this.getPages().get(pageIndex).isEncrypted())
			{
				this.getTxtPage().setEnabled(false);
				this.getBtnEncryptPage().setEnabled(false);
				this.getBtnDecryptPage().setEnabled(true);
			}else
			{
				this.getTxtPage().setEnabled(true);
				this.getBtnEncryptPage().setEnabled(true);
				this.getBtnDecryptPage().setEnabled(false);
			}
		}
	}
	
	//Sets GUI components according to page pointers
	public void validatePageIndex()
	{
		if(this.getPageIndex()<0)
		{
			this.getBtnPreviousPage().setEnabled(false);
			this.getBtnNextPage().setEnabled(false);
			this.getTxtPage().setEnabled(false);
			this.getBtnDeletePage().setEnabled(false);
			this.getBtnEncryptPage().setEnabled(false);
			this.getBtnDecryptPage().setEnabled(false);
		}else
		if(this.getPageIndex()==0 && this.getPageIndex()==this.getPages().size()-1)
		{
			this.getTxtPage().setEnabled(true);
			this.getBtnPreviousPage().setEnabled(false);
			this.getBtnNextPage().setEnabled(false);
			this.getBtnDeletePage().setEnabled(true);
			this.getBtnEncryptPage().setEnabled(true);
			this.getBtnDecryptPage().setEnabled(true);
			
		}else if(this.getPageIndex()==0 && this.getPageIndex()<this.getPages().size()-1)
		{
			this.getTxtPage().setEnabled(true);
			this.getBtnPreviousPage().setEnabled(false);
			this.getBtnNextPage().setEnabled(true);
			this.getBtnDeletePage().setEnabled(true);
			this.getBtnEncryptPage().setEnabled(true);
			this.getBtnDecryptPage().setEnabled(true);
		}else if(this.getPageIndex()>0 && this.getPageIndex()<this.getPages().size()-1)
		{
			this.getTxtPage().setEnabled(true);
			this.getBtnPreviousPage().setEnabled(true);
			this.getBtnNextPage().setEnabled(true);
			this.getBtnDeletePage().setEnabled(true);
			this.getBtnEncryptPage().setEnabled(true);
			this.getBtnDecryptPage().setEnabled(true);
		}
		else if(this.getPageIndex()==this.getPages().size()-1)
		{
			this.getTxtPage().setEnabled(true);
			this.getBtnPreviousPage().setEnabled(true);
			this.getBtnNextPage().setEnabled(false);
			this.getBtnDeletePage().setEnabled(true);
			this.getBtnEncryptPage().setEnabled(true);
			this.getBtnDecryptPage().setEnabled(true);
		}

		verifyPageEncrypted();
	}
	
	//Saves user changers
	public void saveChanges()
	{
		if(this.getPageIndex()>=0)
		{
			this.getPages().get(pageIndex).setText(this.getTxtPage().getText());
		}
		manager.getUser().getNotebook().setPages(this.pages);
		manager.saveCurrentUser();
	}
	
	//Encrypts current page
	public boolean encryptCurrentPage() 
	{
		try
		{
			String seed=manager.requestPassword();
			this.getTxtPage().setText(manager.getAuthenticator().getEncryption().encryptString(seed, this.getTxtPage().getText()));
			this.getPages().get(pageIndex).setEncrypted(true);
			verifyPageEncrypted();
			saveChanges();
			return true;
		}catch(EncryptionOperationNotPossibleException e1)
		{
			log.error("MainProgramContent:encryptCurrentPage: Encryption password error");
			return false;
		}
	}
	
	//Decrypts current page
	public boolean decryptCurrentPage()
	{
		try
		{
			String seed=manager.requestPassword();
			this.getTxtPage().setText(manager.getAuthenticator().getEncryption().decryptString(seed, this.getTxtPage().getText()));
			this.getPages().get(pageIndex).setEncrypted(false);
			verifyPageEncrypted();
			saveChanges();
			return true;
		}catch(EncryptionOperationNotPossibleException e1)
		{
			log.error("MainProgramContent:decryptCurrentPage: Decryption password error");
			return false;
		}
		
	}
	
	//------------------------------Action and Mouse Listeners------------------------------
	
	//Sets components to necessary state
	@Override
	public void keyPressed(KeyEvent e) {
		verifyPageEncrypted();
		validatePageIndex();
	}
	//Saves changes to user data
	@Override
	public void keyReleased(KeyEvent e) {
		this.saveChanges();
		log.info("Saved changes");
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	//Manages click on menu items
	@Override
	public void mouseClicked(MouseEvent e) {
		//If enabled encrypts a page
		if(e.getSource().equals(this.getBtnEncryptPage()) && this.getBtnEncryptPage().isEnabled())
		{
			boolean status=encryptCurrentPage();
			while(!status)
			{
				status=encryptCurrentPage();
			}
		}
		//If enabled decrypts a page
		if(e.getSource().equals(this.getBtnDecryptPage())&& this.getBtnDecryptPage().isEnabled())
		{
			boolean status=decryptCurrentPage();
			while(!status)
			{
				status=decryptCurrentPage();
			}
		}
		//If enabled goes to previous page
		if(e.getSource().equals(this.getBtnPreviousPage())&& this.getBtnPreviousPage().isEnabled())
		{
			if(pageIndex>0)
			{
				pageIndex--;
				changePage(pageIndex);
				validatePageIndex();
			}
		}
		//If enabled goes to next page
		if(e.getSource().equals(this.getBtnNextPage())&& this.getBtnNextPage().isEnabled())
		{
			if(pageIndex<this.getPages().size()-1)
			{
				pageIndex++;
				changePage(pageIndex);
				validatePageIndex();
			}
		}
		//Creates new page
		if(e.getSource().equals(this.getBtnNewPage())&& this.getBtnNewPage().isEnabled())
		{
			String title=manager.requestInput("Enter page title");
			this.pageIndex++;
			Page p =new Page(title);
			this.getPages().add(p);
			changePage(pageIndex);
			validatePageIndex();
		}
		//Deletes a page
		if(e.getSource().equals(this.getBtnDeletePage())&& this.getBtnDeletePage().isEnabled())
		{
			if(pageIndex>=0) {
				this.getPages().remove(pageIndex);
				this.setPageIndex(this.getPageIndex()-1);
			}
			this.setFirstPage();
			saveChanges();
		}
	}

	//Verifies if data is encrypted 
	@Override
	public void mouseEntered(MouseEvent e) {
		verifyPageEncrypted();
		validatePageIndex();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	//---------------------------------- Getters and Setters------------------------------------
	
	public MainProgramWindow getParent() {
		return parent;
	}
	public void setParent(MainProgramWindow parent) {
		this.parent = parent;
	}
	
	public JTextArea getTxtPage() {
		return txtPage;
	}
	public void setTxtPage(JTextArea txtPage) {
		this.txtPage = txtPage;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public List<Page> getPages() {
		return pages;
	}
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
	
	public JScrollPane getBar() {
		return bar;
	}

	public void setBar(JScrollPane bar) {
		this.bar = bar;
	}

	public JMenu getBtnPreviousPage() {
		return btnPreviousPage;
	}

	public void setBtnPreviousPage(JMenu btnPreviousPage) {
		this.btnPreviousPage = btnPreviousPage;
	}

	public JMenu getBtnNextPage() {
		return btnNextPage;
	}

	public void setBtnNextPage(JMenu btnNextPage) {
		this.btnNextPage = btnNextPage;
	}

	public JMenu getBtnNewPage() {
		return btnNewPage;
	}

	public void setBtnNewPage(JMenu btnNewPage) {
		this.btnNewPage = btnNewPage;
	}

	public JMenu getBtnEncryptPage() {
		return btnEncryptPage;
	}

	public void setBtnEncryptPage(JMenu btnEncryptPage) {
		this.btnEncryptPage = btnEncryptPage;
	}

	public JMenu getBtnDecryptPage() {
		return btnDecryptPage;
	}

	public void setBtnDecryptPage(JMenu btnDecryptPage) {
		this.btnDecryptPage = btnDecryptPage;
	}

	public JMenu getBtnDeletePage() {
		return btnDeletePage;
	}

	public void setBtnDeletePage(JMenu btnDeletePage) {
		this.btnDeletePage = btnDeletePage;
	}

	public JMenuBar getJMenuBar() {
		return jMenuBar;
	}

	public void setJMenuBar(JMenuBar JMenuBar) {
		this.jMenuBar = JMenuBar;
	}


}

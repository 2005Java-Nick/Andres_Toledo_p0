package com.revature.andres.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

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
	private JMenu btnEditorMode;
	
	//Text Area
	private JTextPane txtPage;
	DefaultStyledDocument document = new DefaultStyledDocument();
	private JScrollPane bar;
	private ArrayList<String>reservedWords;

	//Menu
	private JMenuBar jMenuBar;
	
	//Array of current pages
	private int pageIndex;
	private List<Page> pages;
	
	//Manager singleton
	private static ExperienceManager manager=ExperienceManager.getExperienceManager();
	
	//Editor Mode
	private boolean editorMode=false;
	
	//-------------------------------------Methods-------------------------------------
	
	//Constructor : Initializes UI elements
	/**
	 * @param parent
	 */
	public MainProgramContent(MainProgramWindow parent) {
		super();
		reservedWords=manager.getAuthenticator().getFileIo().getReservedWords();
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
		this.setBtnEditorMode(new JMenu("Toggle Editor Mode"));
		this.setTxtPage(new JTextPane(document));
		//this.getTxtPage().setEditorKit(new TabSizeEditorKit());
		this.setBar(new JScrollPane(this.getTxtPage()));
		//this.getTxtPage().setLineWrap(true)
		try
		{
			this.setPages(manager.getUser().getNotebook().getPages());
		}catch(NullPointerException e)
		{
			this.setPages(new ArrayList<Page>());
			log.info("MainProgramContent:Cons: Pages not found");
		}
		this.setBar(new JScrollPane(this.getTxtPage()));
		//this.getTxtPage().setLineWrap(true);
	
		//Add action listeners
		
		this.getTxtPage().addKeyListener(this);
		this.getBtnPreviousPage().addMouseListener(this);
		this.getBtnNextPage().addMouseListener(this);
		this.getBtnEncryptPage().addMouseListener(this);
		this.getBtnDecryptPage().addMouseListener(this);
		this.getBtnNewPage().addMouseListener(this);
		this.getBtnDeletePage().addMouseListener(this);
		this.getBtnEditorMode().addMouseListener(this);

		
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
		this.getJMenuBar().add(this.getBtnEditorMode());
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
		formatDocumentText();
		verifyPageEncrypted();
		validatePageIndex();
		if(e.getKeyCode()==8)
		{
			this.keyBackspace();
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			this.keyLeft();
		}
	}
	//Saves changes to user data
	@Override
	public void keyReleased(KeyEvent e) {
		this.saveChanges();
		log.info("Saved changes");
	}
	//Unimplemented key event
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
		if(e.getSource().equals(this.getBtnEditorMode())&& this.getBtnEditorMode().isEnabled())
		{
			if(this.editorMode==false)
			{
				this.setEditorMode(true);
				this.getBtnEditorMode().setBackground(Color.GREEN);
				formatDocumentText();
			}else
			{
				this.setEditorMode(false);
				this.getBtnEditorMode().setBackground(Color.RED);
				formatDocumentText();
			}
		}
	}
	//Verifies if data is encrypted 
	@Override
	public void mouseEntered(MouseEvent e) {
		verifyPageEncrypted();
		validatePageIndex();
	}
	//Unimplemented mouse event
	@Override
	public void mouseExited(MouseEvent e) {	
	}
	//Unimplemented mouse event
	@Override
	public void mousePressed(MouseEvent e) {
	}
	//Unimplemented mouse event
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	//---------------------------------- Getters and Setters------------------------------------
	
	public MainProgramWindow getParent() {
		return parent;
	}
	public void setParent(MainProgramWindow parent) {
		this.parent = parent;
	}
	
	public JTextPane getTxtPage() {
		return txtPage;
	}
	public void setTxtPage(JTextPane txtPage) {
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
	
	public ArrayList<String> getReservedWords() {
		return reservedWords;
	}

	public void setReservedWords(ArrayList<String> reservedWords) {
		this.reservedWords = reservedWords;
	}
	
	public JMenu getBtnEditorMode() {
		return btnEditorMode;
	}

	public void setBtnEditorMode(JMenu btnEditorMode) {
		this.btnEditorMode = btnEditorMode;
	}

	public boolean isEditorMode() {
		return editorMode;
	}

	public void setEditorMode(boolean editorMode) {
		this.editorMode = editorMode;
	}
	
	public void formatDocumentText()
	{	
		  StyleContext context = new StyleContext();
		  Style styleBlue = context.addStyle("reserved_words", null);
		  Style styleBlack = context.addStyle("normal_words", null);
		  StyleConstants.setForeground(styleBlue, Color.BLUE);
		  StyleConstants.setForeground(styleBlack, Color.BLACK);
		  String content = this.getTxtPage().getText();
		  int temp=this.getTxtPage().getCaretPosition();
		  try {
			document.replace(0, this.getTxtPage().getText().length(),content,styleBlack);
			this.getTxtPage().setCaretPosition(temp);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			//log.error("MainProgramWindow:formatDocumentText: Bad location error");
		}
		  
		  if(this.isEditorMode())
		  {
			  content = this.getTxtPage().getText();
			  for(String word : getReservedWords() )
			  {
			  		Pattern pattern = Pattern.compile("[^\\W]+");
			  		Matcher matcher = pattern.matcher(content);
			  		while(matcher.find()) {
			  			try {
			  				int begin=matcher.start();
			  				int end= matcher.end();
			  				String wordFound=content.substring(begin,end);
			  				if(word.equalsIgnoreCase(wordFound))
			  				{
			  					document.replace(begin,end-begin,word,styleBlue);
			  					appendToPane("", Color.BLACK);
			  				}
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
			  		}
			  		this.getTxtPage().setCaretPosition(temp);
			  }
		  }
		
	}
	
	 public void appendToPane(String yourText, Color colour)
	 {
	      StyleContext sc = StyleContext.getDefaultStyleContext();
	      AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, colour);
	      aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Times New Roman");

	      int len = getTxtPage().getDocument().getLength();
	      getTxtPage().setCaretPosition(len);
	      getTxtPage().setCharacterAttributes(aset, false);
	      getTxtPage().replaceSelection(yourText);
	 }
	 
	 public void keyBackspace()
	 {
	/*	 try {
			document.remove(document.getLength() - 1, 1);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			log.error("MainProgramWindow:backspace: Backspace error");
		}*/
	 }
	 
	 public void keyLeft()
	 {
		 //getTxtPage().setCaretPosition(getTxtPage().getCaretPosition()-1);
	 }


	 

}

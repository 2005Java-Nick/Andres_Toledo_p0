package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.andres.notebook.Notebook;
import com.revature.andres.notebook.Page;

public class NotebookTest {

	Notebook book;
	
	@Before
	public void Setup()
	{
		book=new Notebook();
	}
	
	//Validates addPage
	
	@Test
	public void testAddPage() {
		int before=book.getPages().size();
		book.addPage(new Page(""));
		int after=book.getPages().size();
		assertEquals("",true,before<after);
	}
	
	//Validates removePage
	
	@Test
	public void testRemovePageInvalid() {
		int before=book.getPages().size();
		book.removePage(-1);
		int after=book.getPages().size();
		assertEquals("Negative indexes are invalid",false,before<after);
	}
	
	@Test
	public void testRemovePageInvalid1() {
		int before=book.getPages().size();
		book.removePage(100);
		int after=book.getPages().size();
		assertEquals("Negative indexes are invalid",false,before<after);
	}
	
	@Test
	public void testRemovePageValid() {
		int before=book.getPages().size();
		book.removePage(0);
		int after=book.getPages().size();
		assertEquals("Negative indexes are invalid",true,before>after);
	}


}

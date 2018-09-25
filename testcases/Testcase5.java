package testcases;

import org.junit.Test;

import com.thoughtworks.selenium.webdriven.commands.GetTitle;

import wrappers.GenericWrappers;

/**
 * @author Just Jelly Group
 *
 */
public class Testcase5 extends GenericWrappers {
	@Test
	public void createlead() throws InterruptedException{
    System.out.println("*** Testcases Starts ****");
    
		//Login URL with proper credential
		invokeApp("chrome", "http://demo1.opentaps.org/opentaps/control/main");
		enterById("username", "DemoCSR");
		enterById("password", "crmsfa");
		clickByClassName("decorativeSubmit");
		
		//Create the lead
		clickByLink("CRM/SFA");
		clickByLink("Create Lead");
		clickByLink("Find Leads");
		clickByXpath("//span[contains(text(),'Email')]");
		enterByName("emailAddress","iburahim786@gmail.com");
		clickByXpath("//button[contains(text(),'Find Leads')]");
		Thread.sleep(5000);
		String name= getTextByXpath("(//div[contains(@class,'x-grid3-cell-inner x-grid3-col-firstName')])/a[1]");
		clickByXpath("(//div[contains(@class,'x-grid3-cell-inner x-grid3-col-firstName')])/a[1]");
		clickByXpath("//a[contains(text(),'Duplicate Lead')]");
		Thread.sleep(10000);
		verifyTitle("Duplicate Lead | opentaps CRM");
		clickByName("submitButton");
		verifyTextById("viewLead_firstName_sp",name);
		quitBrowser();
	}
	}


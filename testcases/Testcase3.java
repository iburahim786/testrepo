package testcases;

import org.junit.Test;

import wrappers.GenericWrappers;

/**
 * @author Just Jelly Group
 *
 */
public class Testcase3 extends GenericWrappers {
	@Test
	public void createlead(){
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
		clickByXpath("//span[contains(text(),'Phone')]");
		enterByName("phoneNumber","9524524574");
		clickByXpath("//button[contains(text(),'Find Leads')]");
		
		String IdValue= getTextByXpath("(//div[contains(@class,'x-grid3-cell-inner x-grid3-col-partyId')])/1/a[1]");
		clickByXpath("(//a[contains(@href,'/crmsfa/control/viewLead?partyId=')])[1]");
		clickByClassName("subMenuButtonDangerous");
		clickByLink("Find Leads");
		enterByName("id",IdValue);
		clickByLink("Find Leads");
		verifyTextByClass("x-paging-info","No records to display");
		quitBrowser();
	}
	}


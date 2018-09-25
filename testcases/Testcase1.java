package testcases;

import org.junit.Test;

import wrappers.GenericWrappers;

/**
 * @author Just Jelly Group
 *
 */
public class Testcase1 extends GenericWrappers {
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
		
		//Enter the all details
	    enterById("createLeadForm_companyName","Just jelly");
		enterById("createLeadForm_firstName","Demo");
		enterById("createLeadForm_lastName","test");
		selectVisibileTextById("createLeadForm_dataSourceId","Employee");
		selectVisibileTextById("createLeadForm_marketingCampaignId","Automobile");
		enterById("createLeadForm_primaryPhoneNumber","9524524574");
		enterById("createLeadForm_primaryEmail","iburahim786@gmail.com");
		clickByName("submitButton");
		getTextById("viewLead_companyName_sp");
		quitBrowser();
	}
	}


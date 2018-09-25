package testcases;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import wrappers.GenericWrappers;

/**
 * @author Just Jelly Group
 *
 */
public class Testcase2 extends GenericWrappers {

	/**
	 * @param Update the Company Name Using Wrappers Method
	 *     
	 */
	@Test

	public void editLeadOpenTaps() {
		
		// login the OpenTaps With Proper Credential
		
		System.out.println("*** Testcases Starts ****");
		
		invokeApp("chrome", "http://demo1.opentaps.org/opentaps/control/main");
		enterById("username", "DemoCSR");
		enterById("password", "crmsfa");
		clickByClassName("decorativeSubmit");
		
		// Click the CRM/SFA link
		clickByLink("CRM/SFA");
		// Update the Company Name as Just Jelly Group
		clickByLink("Create Lead");
		clickByLink("Find Leads");
		enterByXpath("(//input[@name='firstName'])[3]", "Demo");
		clickByXpath("//button[contains(text(),'Find Leads')]");
		
		// Wait for Searching result to Appear
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//a[contains(@href,'/crmsfa/control/viewLead?partyId=')])[1]")));
		
		clickByXpath("(//a[contains(@href,'/crmsfa/control/viewLead?partyId=')])[1]");
		verifyTitle("View Lead | opentaps CRM");
		clickByXpath("//a[contains(text(),'Edit')]");
		enterByXpath("(//input[@id='updateLeadForm_companyName'])", "Just Jelly");
		clickByXpath("//input[@value='Update']");
		
		// Just Jelly Company name Successfully Updated.
		verifyTextContainsByXpath("//span[contains(text(),'Just Jelly')]", "Just Jelly");
		System.out.println("***** Company Name Successfully Updated****");
		System.out.println("*** Given Testcase Successfully Completed ****");
		quitBrowser();
		

	}

}

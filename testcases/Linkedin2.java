package testcases;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import wrappers.GenericWrappers;


public class Linkedin2 extends GenericWrappers {
	
	@Test

	public void searchLinkedin() throws InterruptedException {
		
System.out.println("*** Testcases Starts ****");
		
		invokeApp("chrome", "https://www.linkedin.com");
		enterById("login-email", "iburahim786@gmail.com");
		enterById("login-password", "@yeeesha");
		clickByName("submit");
		clickByLink("Jobs");
		enterById("job-search-box","selenium");
		clickByName("jsearch");
		clickByXpath("(//a[@class='primary-action-button label'])[1]");
     	getTextByXpath("//h1[@class='title']");
		quitBrowser();

		
	
	}

}

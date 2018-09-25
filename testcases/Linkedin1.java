package testcases;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import wrappers.GenericWrappers;


public class Linkedin1 extends GenericWrappers {
	
	@Test

	public void searchLinkedin() throws InterruptedException {
		
System.out.println("*** Testcases Starts ****");
		
		invokeApp("chrome", "https://www.linkedin.com");
		enterById("login-email", "iburahim786@gmail.com");
		enterById("login-password", "@yeeesha");
		clickByXpath("//body[@id='pagekey-uno-reg-guest-home']/div[1]/div/form/input[6]");
		//clickByName("submit");
		clickById("advanced-search");
		enterById("advs-keywords","selenium");
		enterById("advs-company","CTS");
		clickByName("submit");
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Send InMail")));
		System.out.println(getTextByXpath("(//div[@class='search-info']/p/strong)"));
		quitBrowser();
	
	}

}

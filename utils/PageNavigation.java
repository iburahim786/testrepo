package com.cisco.ui.test.compliance.wrappers.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.IntegerSyntax;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.test.tea.log.TestLogger;
import com.cisco.ui.test.compliance.pages.PiHomePage;
import com.cisco.ui.test.compliance.pages.PrimeLogin;
import com.cisco.ui.test.compliance.wrappers.utils.OpentapsWrappers;

import junit.framework.Assert;

public class PageNavigation extends OpentapsWrappers {
	 
//	public void NavigateTo (String Submenu, String Page){
//		clickByXpath(prop.getProperty("FromHome.ClickMenu.Xpath"));
//		WebElement submenuEle = driver.findElementByLinkText(Submenu);
//		Actions action= new Actions(driver);
//		action.moveToElement(submenuEle).perform();
//		clickByLink(Page);
//	}
	
	public static void NavigateToPage (String Submenu, String Page){
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".toggleIcon.icon-cisco-menu")));
			driver.findElement(By.cssSelector(".toggleIcon.icon-cisco-menu")).click();
			TestLogger.debug("Menu toggle clicked");
			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.linkText(Submenu)));
			driver.findElement(By.linkText(Submenu)).click();
			TestLogger.debug(Submenu+ "link is clicked");
			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.linkText(Page)));
			driver.findElement(By.linkText(Page)).click();
			TestLogger.debug(Page+ " link is clicked");
		}catch(WebDriverException e) {
			//If any web driver exception is occurred while navigating to HA page.
			NavigateToPage(Submenu, Page);

}
	}
	
	   public static void logout(final WebDriver driver,boolean bClose) {
		   
	       try {
	             driver.findElement(By.cssSelector(".dijitInline.settingsNode")).click();
	             driver.findElement(By.id("logout_text")).click();
	             TestLogger.debug("Logged out from server");
	       } catch (final Exception e) {
	             try {
//	                 driver.navigate().refresh();
	                   new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".dijitInline.settingsNode")));
	                   driver.findElement(By.cssSelector(".dijitInline.settingsNode")).click();
	                   new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.id("logout_text")));
	                   driver.findElement(By.id("logout_text")).click();
	             } catch (final Exception e1) {
	                   TestLogger.debug("Logout failure: " + e1.getMessage() + "--> May be session went down..");
	             }

	       }
	       if(bClose)
	    	   driver.close();
	 }
	   
	   public static void PopUpHandling(final WebDriver driver) throws InterruptedException{
		    waitUntilLoginCompleted(driver);
	 		clearTelemetryDialog(driver);
	 		clearLicenseAlert(driver);
	 		clearBackupFailsDialog(driver);
	 		clearTutorialWindow(driver);;
	   }
	   
	   public static void clearTelemetryDialog(final WebDriver driver) {
			try {
				if (driver.findElements(By.id("telemetryDialog")).size() > 0 && driver.findElement(By.id("telemetryDialog")).isDisplayed()) {
					final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();
					TestLogger.debug("Cleared Telemetry dialog..");
				}
			} catch (final Exception e) {
				TestLogger.debug(e.getMessage());
			}

		}

		public static void clearLicenseAlert(final WebDriver driver) {
			try {
				if (driver.findElements(By.id("telemetryDialog")).size() > 0 && driver.findElement(By.id("telemetryDialog")).isDisplayed()) {
					final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();
					TestLogger.debug("Cleared Telemetry dialog..");
				}
				if (driver.findElements(By.id("warningLoginDialog")).size() > 0 && driver.findElement(By.id("warningLoginDialog")).isDisplayed()) {
					/*final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();*/
					//Clicking ok button instead of pressing Escape key of license warning
					driver.findElement(By.xpath("//*[@id=\"xwt_widget_form_TextButtonGroup_2\"]/span[2]/span")).click();
					TestLogger.debug("Cleared License Warning alert..");
				}
			} catch (final Exception e) {
				TestLogger.debug(e.getMessage());
			}

		}
		
		public static void clearBackupFailsDialog(final WebDriver driver) {
			try {
				if (driver.findElements(By.id("telemetryDialog")).size() > 0 && driver.findElement(By.id("telemetryDialog")).isDisplayed()) {
					final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();
					TestLogger.debug("Cleared Telemetry dialog..");
				}
				if (driver.findElements(By.id("warningLoginDialog")).size() > 0 && driver.findElement(By.id("warningLoginDialog")).isDisplayed()) {
				/*	final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();*/
					//Clicking ok button instead of pressing Escape key of license warning
					driver.findElement(By.xpath("//*[@id=\"xwt_widget_form_TextButtonGroup_2\"]/span[2]/span")).click();
					TestLogger.debug("Cleared License Warning alert..");
				}
				if (driver.findElements(By.id("backupFailsDialog")).size() > 0 && driver.findElement(By.id("backupFailsDialog")).isDisplayed()) {
					final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();
					TestLogger.debug("Cleared BackupFailsDialog Warning alert..");
				}
			} catch (final Exception e) {
				TestLogger.debug(e.getMessage());
			}
		}
		
		public static void dismissLicenseDialog(final WebDriver driver) {
		  try {	
			if (driver.findElements(By.id("warningLoginDialog")).size() > 0 && driver.findElement(By.id("warningLoginDialog")).isDisplayed()) {
				/*	final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();*/
					//Clicking ok button instead of pressing Escape key of license warning
					driver.findElement(By.xpath("//*[@id=\"xwt_widget_form_TextButtonGroup_2\"]/span[2]/span")).click();
					TestLogger.debug("Cleared License Warning alert..");
				}
			else {
				TestLogger.debug("License dialog not displayed...");
			}
		  }
			 catch (final Exception e) {
				TestLogger.debug("License dialog not displayed..."+ e.getMessage());
		  }
	   }
		
		public static void dismissTelemetryDialog(final WebDriver driver) {
				  try {
						if (driver.findElements(By.id("telemetryDialog")).size() > 0 && driver.findElement(By.id("telemetryDialog")).isDisplayed()) {
							final Actions builder = new Actions(driver);
							builder.sendKeys(Keys.ESCAPE).perform();
							TestLogger.debug("Cleared Telemetry dialog..");
						}
				
			  }
				 catch (final Exception e) {
					TestLogger.debug("Telemetry dialog not displayed..."+ e.getMessage());
			  }
		   }
		
		public static void dismissOutOfDiskDialog(final WebDriver driver) {
			  try {	
				if (driver.findElements(By.id("warningOutOfDiskSpaceDialog")).size() > 0 && driver.findElement(By.id("warningOutOfDiskSpaceDialog")).isDisplayed()) {
					final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();
					TestLogger.debug("Cleared Out Of Disk Warning dialog....");
					}
				
			  }
				 catch (final Exception e) {
					TestLogger.debug("Out Of Disk Warning dialog not displayed..."+ e.getMessage());
			  }
		   }
		
		public static void dismissBackupFailsDialog(final WebDriver driver) {
			  try {	
				  if (driver.findElements(By.id("backupFailsDialog")).size() > 0 && driver.findElement(By.id("backupFailsDialog")).isDisplayed()) {
						final Actions builder = new Actions(driver);
						builder.sendKeys(Keys.ESCAPE).perform();
						TestLogger.debug("Cleared BackupFailsDialog Dialog..");
					}
				
			  }
				 catch (final Exception e) {
					TestLogger.debug("BackupFails Dialog not displayed..."+ e.getMessage());
			  }
		   }
		
		public static void dismissOkDialog(final WebDriver driver) {
			  try {
				   String OkElement="//div[@class='shellTutorialOverlay']/div[6]/descendant::span[6]"; 
				  if (driver.findElements(By.xpath(OkElement)).size() > 0 && driver.findElement(By.xpath(OkElement)).isDisplayed()) {
					    driver.findElement(By.xpath("//div[@class='shellTutorialOverlay']/div[6]/descendant::input")).click();
					    Thread.sleep(3000);
					    driver.findElement(By.xpath(OkElement)).click();
						TestLogger.debug("Cleared Okay, got it Dialog ..");
					}
				
			  }
				 catch (final Exception e) {
					TestLogger.debug("Okay, got it Dialog not displayed..."+ e.getMessage());
			  }
		   }
		
		
		public static void clearTutorialWindow(final WebDriver driver) {
			try {
				if (driver.findElements(By.id("telemetryDialog")).size() > 0 && driver.findElement(By.id("telemetryDialog")).isDisplayed()) {
					final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();
					TestLogger.debug("Cleared Telemetry dialog..");
				}
				if (driver.findElements(By.id("warningLoginDialog")).size() > 0 && driver.findElement(By.id("warningLoginDialog")).isDisplayed()) {
					/*final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();*/
					//Clicking ok button instead of pressing Escape key of license warning
					driver.findElement(By.xpath("//*[@id=\"xwt_widget_form_TextButtonGroup_2\"]/span[2]/span")).click();
					TestLogger.debug("Cleared License Warning alert..");
				}
				if (driver.findElements(By.id("backupFailsDialog")).size() > 0 && driver.findElement(By.id("backupFailsDialog")).isDisplayed()) {
					final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();
					TestLogger.debug("Cleared BackupFailsDialog Warning alert..");
				}
				if (driver.findElements(By.cssSelector(".shellTutorialOverlay")).size() > 0 && driver.findElement(By.cssSelector(".shellTutorialOverlay")).isDisplayed()) {
					TestLogger.debug("Clearing Tutorial window");
					driver.findElement(By.cssSelector(".shellTutorialOverlay")).findElement(By.cssSelector(".dijitReset.dijitInline.dijitButtonNode")).click();
					TestLogger.debug("Cleared Tutorial window");
				}
			} catch (final Exception e) {
				TestLogger.debug(e.getMessage());
			}
		}
		
		public static void waitUntilLoginCompleted(final WebDriver driver) throws InterruptedException {
			boolean stillLoading = true;
			int timout = 0;
			WebDriverWait wait = new WebDriverWait(driver, 10);
			while (stillLoading) {
				timout++;
				TestLogger.debug("Page loading.. Waiting for page elements..");
				Thread.sleep(5000);
				if (driver.findElements(By.id("telemetryDialog")).size() > 0) {
					stillLoading = false;
					TestLogger.debug("Page Loading completed and licence Agreement dialog present");
					dismissTelemetryDialog(driver);
				}
				else {
					TestLogger.debug("Telemetry dialog not displayed...");
				}
				Thread.sleep(5000);
				if (driver.findElements(By.id("warningLoginDialog")).size() > 0) {
					stillLoading = false;
					TestLogger.debug("Page Loading completed and licence waring present");
					dismissLicenseDialog(driver);
				}
				else {
					TestLogger.debug("License dialog not displayed...");
				}
				Thread.sleep(5000);
				if (driver.findElements(By.id("warningOutOfDiskSpaceDialog")).size() > 0){
					stillLoading = false;
					TestLogger.debug("Page Loading completed and OutOf Disk waring present");
					dismissOutOfDiskDialog(driver);
				}
				else {
					TestLogger.debug("Out Of Disk Warning dialog not displayed...");
				}
				Thread.sleep(5000);
				if(driver.findElements(By.id("backupFailsDialog")).size() > 0){
					stillLoading = false;
					TestLogger.debug("Page Loading completed and Backup fails waring present");
					dismissBackupFailsDialog(driver);
				}
				else {
					TestLogger.debug("Backup fails waring dialog not displayed...");
				}
				Thread.sleep(5000);
				TestLogger.debug("Searching for Okay got it button");
				String OkElement="//div[@class='shellTutorialOverlay']/div[6]/descendant::span[6]"; 
			   if (driver.findElements(By.xpath(OkElement)).size() > 0) {
				    stillLoading = false;
					TestLogger.debug("Page Loading completed, OKay, Got it button present");
					dismissOkDialog(driver);
			   }
			   else {
					TestLogger.debug("Okay, got it Dialog not displayed...");
			   }
			   System.out.println("Total Timeout is :"+ timout);
//			   if (timout == 1) {
//					TestLogger.debug("Expected elements not found,So doing Page refresh at secs :  " + timout);
//					driver.navigate().refresh();
//			   }
			   if (timout == 1) {
//					TestLogger.debug("Page not loading in 120 seconds.. time out");
				   TestLogger.debug("Prime Popup validated successfully, Test case execution Starting..");
					break;
			   }
			}
		}
		
		 public static String toasterMsgVerification(String text){
	    	 String toasterVerify="";
	    	 List<WebElement> toaster = driver.findElementsByXPath(prop.getProperty("ToProfile.Toaster.Xpath"));
	    	 List<WebElement> toasterText=driver.findElementsByXPath("//div[text()='"+text+"']");
	         if (toaster.size()>0 & toasterText.size()>0 ){
	         	System.out.println("toaster Message: "+text+" verified successfully");
	         	toasterVerify="Yes";
	         }
	         else {
	         	System.out.println("toaster Message: "+text+" not verified successfully");
	         	toasterVerify="No";
	         }
			return toasterVerify;
	     }
		
      public static void ComplianceEnable(WebDriver driver,Dataset ds) throws InterruptedException{
    	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    	  NavigateToPage("Administration", "System Settings");
    	  Thread.sleep(3000);
    	  driver.findElement(By.xpath("//span[text()='Server']")).click();
    	  Thread.sleep(3000);
    	  String maxSessionStr =driver.findElement(By.name("maxSessions")).getAttribute("value");
    	  int maxsessionInt= Integer.parseInt(maxSessionStr);
    	  if(maxsessionInt>=ds.getAsInt("sValue")){
    		  TestLogger.debug("Session already set below 50 and No need to Change the Session..");
    	  }
    	  else {
    		  driver.findElement(By.name("maxSessions")).clear();
    		  driver.findElement(By.name("maxSessions")).sendKeys(ds.getAsString("sValue"));
    		  TestLogger.debug("Session limit has been changed to 49 successfully..");
    	  }
    	  Thread.sleep(3000);
    	  System.out.println(driver.findElement(By.className("sectionTitle")).getText());
    	  JavascriptExecutor js =(JavascriptExecutor) driver;
    	  List <WebElement> Title=driver.findElements(By.className("sectionTitle"));
    	 if (Title.size()>=8){
    		 TestLogger.debug(Title.get(Title.size()-1).getText()+ " is available");
    		 List<WebElement> oRadioButton=driver.findElements(By.name("complianceEngineEnable"));
    		 boolean aValue=false;
    		 boolean bValue=false;
    		 aValue=oRadioButton.get(0).isSelected();
    		 bValue=oRadioButton.get(1).isSelected();
    		 System.out.println(bValue);
    		 System.out.println(aValue);
    		 if (aValue){
    			 System.out.println("compliance Already Selected, logout and Login");
    			 logout(driver,true);
    		 }
    		 else if(bValue){
    			 oRadioButton.get(0).click();
    			 System.out.println("Compliance Feature has been Enabled Successfully, Please logout and login...");
    			 driver.findElement(By.id("save_label")).click();
    			 Thread.sleep(2000);
    			 String msg=toasterMsgVerification("Server Settings saved successfully");
    			if(msg.equalsIgnoreCase("Yes")){
    				logout(driver,true);
    			}
    			else if(msg.equalsIgnoreCase("No")){
    				ComplianceEnable(driver, ds);
    			}
    		 }
    	   }
        else{
    		Assert.fail("FAILED: Compliance feature is not Available, Please check the OVA Flavour");
    		}
    	 
               
      }
      
      public static void MailserverSetting(WebDriver driver,Dataset ds) throws InterruptedException{
    	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    	  NavigateToPage("Administration", "System Settings");
    	  Thread.sleep(3000);
    	  driver.findElement(By.xpath("//span[text()='Mail Server Configuration']")).click();
    	  Thread.sleep(3000);
    	  driver.findElement(By.id("primaryMailServer")).clear();
    	  driver.findElement(By.id("primaryMailServer")).sendKeys(ds.getAsString("mailServerName"));
    	  Thread.sleep(3000);
    	  driver.findElement(By.id("toAddress")).clear();
    	  driver.findElement(By.id("toAddress")).sendKeys(ds.getAsString("mailerAddress"));
    	  Thread.sleep(3000);
    	  driver.findElement(By.id("globalSubject")).clear();
    	  driver.findElement(By.id("globalSubject")).sendKeys(ds.getAsString("mailSubject"));
    	  driver.findElement(By.id("save_label")).click();
    	  Thread.sleep(3000);
    	  Alert alert=driver.switchTo().alert();
    	  String alertText=alert.getText();
    	  if(alertText.equalsIgnoreCase("Mail server configuration was saved successfully.")){
    		  alert.accept();
    	  }
    	  else {
    		  System.out.println("alert message is not displayed");
    	  }
      }
      
      public static void JobMailNotification(WebDriver driver,Dataset ds) throws InterruptedException{
    	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    	  NavigateToPage("Administration", "System Settings");
    	  Thread.sleep(3000);
    	  driver.findElement(By.xpath("//span[text()='Job Notification Mail']")).click();
    	  Thread.sleep(3000);
    	  WebElement JobNotChkbox=driver.findElement(By.xpath("//div[@id='JobNotificationMailDetails']/descendant::div/input"));
    	  if(JobNotChkbox.getAttribute("aria-checked").equals("false")){
    		  JobNotChkbox.click();
    		  driver.findElement(By.xpath(prop.getProperty("Syssetting.JobMailToAddr.Xpath"))).clear();
    		  driver.findElement(By.xpath(prop.getProperty("Syssetting.JobMailToAddr.Xpath"))).sendKeys(ds.getAsString("mailerAddress"));
    		  driver.findElement(By.xpath(prop.getProperty("Syssetting.JobmailSubject.Xpath"))).clear();
    		  driver.findElement(By.xpath(prop.getProperty("Syssetting.JobmailSubject.Xpath"))).sendKeys(ds.getAsString("mailSubject"));
    	  }
    	  else {
    		  System.out.println("Job Notification checkbox already checked and No Need to click again");
    		  driver.findElement(By.xpath(prop.getProperty("Syssetting.JobMailToAddr.Xpath"))).clear();
    		  driver.findElement(By.xpath(prop.getProperty("Syssetting.JobMailToAddr.Xpath"))).sendKeys(ds.getAsString("mailerAddress"));
    		  driver.findElement(By.xpath(prop.getProperty("Syssetting.JobmailSubject.Xpath"))).clear();
    		  driver.findElement(By.xpath(prop.getProperty("Syssetting.JobmailSubject.Xpath"))).sendKeys(ds.getAsString("mailSubject"));
    	  }
    	  
    	  WebElement JobSuccessChkbox=driver.findElement(By.xpath(prop.getProperty("Syssetting.SuccessChkBox.Xpath")));
    	  WebElement JobFailureChkbox=driver.findElement(By.xpath(prop.getProperty("Syssetting.FailureChkBox.Xpath")));
    	  
    	  if(JobSuccessChkbox.getAttribute("aria-checked").equals("false") && (JobFailureChkbox.getAttribute("aria-checked").equals("false"))) {
    		  JobSuccessChkbox.click();
    		  JobFailureChkbox.click();
    	  }
    	  else {
    		  System.out.println("Job Status check box already clicked");
    	  }
    	 String[] JobType ={"Compliance Audit Job","Compliance Fix Job","PAS Profiling Job"};
    	for (String Jtype:JobType ){
    		WebElement ComplAuditJobchkBox =driver.findElement(By.xpath("//td[@title='"+Jtype+"']/preceding-sibling::td/div/div"));
            if(ComplAuditJobchkBox.getAttribute("class").equalsIgnoreCase("dijitCheckBox dijitCheckBoxChecked")){
           	 System.out.println(Jtype +" already Selected");
            }
            else if(ComplAuditJobchkBox.getAttribute("class").equalsIgnoreCase("dijitCheckBox")){
           	  ComplAuditJobchkBox.click();
           	  System.out.println(Jtype +" checked now");
            }
            Thread.sleep(3000);
    	}
    	
    	driver.findElement(By.xpath("//span[text()='Save']")).click();
    	Thread.sleep(2000);
    	String msg=toasterMsgVerification(ds.getAsString("JobNotToasterMsg"));
    	if(msg.equalsIgnoreCase("Yes")){
			System.out.println("Job Notification Settings saved successfully");
		}
		else if(msg.equalsIgnoreCase("No")){
			Assert.fail("FAILED:Job Notification Settings not saved successfully ");
		}
      }
      
		
		
}

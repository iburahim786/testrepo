package com.cisco.ui.test.compliance.pages;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.tools.ant.taskdefs.WaitFor.Unit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentTest;
import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.test.tea.log.TestLogger;
import com.cisco.ui.test.compliance.wrappers.utils.OpentapsWrappers;
import com.cisco.ui.test.compliance.wrappers.utils.PageNavigation;

public class ComplProfile extends OpentapsWrappers {
	

	public  ComplProfile(RemoteWebDriver driver, ExtentTest test){
		this.driver = driver;
		this.test = test;
//		clickByXpath(prop.getProperty("FromHome.ClickMenu.Xpath"));
//		WebElement config = driver.findElementByLinkText("Configuration");
//		Actions action= new Actions(driver);
//		action.moveToElement(config).perform();
//		clickByLink("Profiles");
		PageNavigation.NavigateToPage("Configuration", "Profiles");
		
	}
	public ComplProfile CreateProfile(Dataset ds,String ProName) throws InterruptedException{
//		WebDriverWait wait = new WebDriverWait(driver,30);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("ToProfile.AddPolicyProfile.Xpath"))));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(20000);
		clickByXpath(prop.getProperty("ToProfile.AddPolicyProfile.Xpath"));
		Thread.sleep(3000);
		enterById(prop.getProperty("ToProfile.ProfileName.Id"),ProName);
		enterById(prop.getProperty("ToProfile.ProfileDesc.Id"),ds.getAsString("ProDesc"));
		Thread.sleep(5000);
		clickByXpath(prop.getProperty("ToProfile.ProNameSave.Xpath"));
		Thread.sleep(10000);
		return this;
	}
	
	public ComplProfile PolicySysPolMap(Dataset ds) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
		clickById(prop.getProperty("Toprofile.AddPolicy.Id"));
		Thread.sleep(3000);
		clickByXpathwithuinput(ds.getAsString("Polcat"));
		clickByXpathwithuinput1(ds.getAsString("Policy"));
		clickByXpath(prop.getProperty("ToProfile.SavePolMap.Xpath"));
		return this;
	}
	
	public ComplProfile PolicyCusPolMap(Dataset ds,String Policy) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		Thread.sleep(10000);
		clickById(prop.getProperty("Toprofile.AddPolicy.Id"));
		Thread.sleep(3000);
		clickByXpathwithuinput(ds.getAsString("Polcat"));
		clickByXpathwithuinput1(Policy);
		clickByXpath(prop.getProperty("ToProfile.SavePolMap.Xpath"));
		return this;
	}
     public ComplProfile PolRuleSelect() throws InterruptedException{
    	 Thread.sleep(5000);
 		List<WebElement> rulecounts= driver.findElementsByClassName("rule_div");
 		int rulecount=rulecounts.size();
 		System.out.println(rulecount);
 		int[] reqrule = {2,3};
 		for (int r: reqrule){
 		  if(r<=rulecount){
 			driver.findElement(By.xpath("(//div[@class='rule_div']/span/div/input)["+ r +"]")).click();
 			String rulename=rulecounts.get(r-1).getText();
 			System.out.println(rulename);
 			Thread.sleep(3000);
 			if(rulename.contains("*")){
 				System.out.println("Mandory rule is present, hence need to put required value in the rule text box");
 				List<WebElement> runamecounts=driver.findElementsByXPath("//td[@class='rule_td_label']/div");
 				int runamesize= runamecounts.size();
 				 System.out.println(runamesize);
 				 for (int i = 1; i<=runamesize; i++){
// 					 for(WebElement rulenamec : runamecounts ){
 						 String rulname = runamecounts.get(i).getText();
 						 System.out.println(rulname);
 						 if (rulename.contains(rulname)){
 							 System.out.println("hello");
 							 enterByXpath("(//td[@class='rule_td_input']/div/div/following-sibling::div/input[2])["+ i +"]", "2");
 						 }
 						else{
 							System.out.println("it doesnt match with rulename");
 						 }
 					 
 				 }
 			}
 			else{
 				System.out.println("Mandory rule is not present, hence no need to put required value in the rule text box");
 			}
 		}
 		  else{
 			  System.out.println("There is no change in the default rule selection");
 		  }
 		}
 		Thread.sleep(3000);
 		int[] notreqrule = {5,6};
 		for (int nr: notreqrule){
 			if(nr>=1){
 			driver.findElement(By.xpath("(//div[@class='rule_div']/span/div/input)["+ nr +"]")).click();
 		}
 		}
 	 	clickById("profileSaveButton_label");
 	 	return this;
 	}
     
     public ComplProfile RunJobwithsingleDevice(Dataset ds,String JbName,String Did,String Recurrence) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
    	 Thread.sleep(10000);
    	 WebDriverWait wait = new WebDriverWait(driver, 10);
 		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("ToProfile.Runjob.Xpath"))));
    	 clickByXpath(prop.getProperty("ToProfile.Runjob.Xpath"));
    	 Thread.sleep(5000);
    	 clickByXpath(prop.getProperty("ToProfile.Qfilter.Xpath"));
    	 Thread.sleep(3000);
    	 clickByXpath(prop.getProperty("ToProfile.DevClick.Xpath"));
    	 enterByXpath(prop.getProperty("ToProfile.DevEnter.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
    	 Thread.sleep(5000);
    	 clickByXpath(prop.getProperty("ToProfile.CheckBox.Xpath"));
    	 Thread.sleep(4000);
    	 WebElement Latest= driver.findElementById(prop.getProperty("ToProfile.LatestArch.Id"));
    	 WebElement Current=driver.findElementById(prop.getProperty("ToProfile.CurrentArch.Id"));
    	 if(ds.getAsString("ArchiveSel").equalsIgnoreCase("Current")){
    			 Current.click();
    		 }
    		 else if(ds.getAsString("ArchiveSel").contains("Latest")){
    			 Latest.click();
    		 }
         Thread.sleep(2000);
         clickById(prop.getProperty("Toprofile.Nextbtn.Id"));
         enterByXpath(prop.getProperty("ToProfile.JobnameEnt.Xpath"),JbName);
         Thread.sleep(2000);
         if(Recurrence.equalsIgnoreCase("yes")){
        	 clickByXpath(prop.getProperty("ToProfile.RecurrenceOptionMinute.Xpath"));
        	 driver.findElement(By.id(prop.getProperty("ToProfile.RecurrenceMinuteValue.Id"))).clear();
        	 enterById(prop.getProperty("ToProfile.RecurrenceMinuteValue.Id"), ds.getAsString("MinValue"));
        	 System.out.println("Recurrence Value Minute is selected");
         }
         else {
        	 System.out.println("No Recurrence value is selected");
         }
         clickById(prop.getProperty("ToProfile.JobSubmit.Id"));
		return this;
     }
     
     public ComplProfile RunJobwithmultiDevice(Dataset ds,String JbName, final String[] DeviceIds,String Recurrence) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
    	 Thread.sleep(5000);
    	 WebDriverWait wait = new WebDriverWait(driver, 10);
 		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("ToProfile.Runjob.Xpath"))));
    	 clickByXpath(prop.getProperty("ToProfile.Runjob.Xpath"));
    	 Thread.sleep(3000);
    	 clickByXpath(prop.getProperty("ToProfile.Qfilter.Xpath"));
    	 Thread.sleep(2000);
    	 for (final String Did : DeviceIds){
    	 clickByXpath(prop.getProperty("ToProfile.DevClick.Xpath"));
    	 driver.findElement(By.xpath(prop.getProperty("ToProfile.DevEnter.Xpath"))).clear();
    	 Thread.sleep(2000);
    	 enterByXpath(prop.getProperty("ToProfile.DevEnter.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
    	 Thread.sleep(5000);
    	 clickByXpath(prop.getProperty("ToProfile.CheckBox.Xpath"));
    	 Thread.sleep(3000);
    	 }
    	 WebElement Latest= driver.findElementById(prop.getProperty("ToProfile.LatestArch.Id"));
    	 WebElement Current=driver.findElementById(prop.getProperty("ToProfile.CurrentArch.Id"));
    	 if(ds.getAsString("ArchiveSel").equalsIgnoreCase("Current")){
    			 Current.click();
    		 }
    		 else if(ds.getAsString("ArchiveSel").contains("Latest")){
    			 Latest.click();
    		 }
         Thread.sleep(2000);
         clickById(prop.getProperty("Toprofile.Nextbtn.Id"));
         enterByXpath(prop.getProperty("ToProfile.JobnameEnt.Xpath"),JbName);
         Thread.sleep(2000);
         if(Recurrence.equalsIgnoreCase("yes")){
        	 clickByXpath(prop.getProperty("ToProfile.RecurrenceOptionMinute.Xpath"));
        	 driver.findElement(By.id(prop.getProperty("ToProfile.RecurrenceMinuteValue.Id"))).clear();
        	 enterById(prop.getProperty("ToProfile.RecurrenceMinuteValue.Id"), ds.getAsString("MinValue"));
        	 System.out.println("Recurrence Value Minute is selected");
         }
         else {
        	 System.out.println("No Recurrence value is selected");
         }
         clickById(prop.getProperty("ToProfile.JobSubmit.Id"));
		return this;
     }
    
     public ComplProfile ValidateRuleInputInvalidValues(Dataset ds,String ProName) throws InterruptedException{
    	    Thread.sleep(3000);
//			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'"+ProName+"')]")));
//			clickByXpath("//div[contains(text(),'"+ProName+"')]");
//			Thread.sleep(3000);
			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("ToProfile.SelPolicySel.Xpath"))));
	    	clickByXpath(prop.getProperty("ToProfile.SelPolicySel.Xpath"));
	    	clickByXpath(prop.getProperty("ToProfile.StringValue.Xpath"));
	    	enterByXpath(prop.getProperty("ToProfile.StringValue.Xpath"),"string");
	    	Thread.sleep(2000);
	    	clickByXpath(prop.getProperty("ToProfile.IntegerValue.Xpath"));
//	    	driver.findElement(By.xpath(prop.getProperty("ToProfile.IntegerValue.Xpath"))).clear();
	    	enterByXpath(prop.getProperty("ToProfile.IntegerValue.Xpath"),"3");
	    	Thread.sleep(3000);
	    	WebElement saveClick=driver.findElementByXPath("//span[@id='profileSaveButton']/child::span[3]");
	    	WebDriverWait wait = new WebDriverWait(driver, 60);
	    	wait.until(ExpectedConditions.elementToBeClickable(saveClick));
	    	saveClick.click();
//	    	Actions action = new Actions(driver); 
//	    	action.sendKeys(Keys.TAB).build().perform();
//	    	Thread.sleep(3000);
//	    	action.sendKeys(Keys.ENTER).build().perform();
	    	Thread.sleep(2000);
	    	String toastermsg1=toasterVerification("Updated Successfully.!!!");
	    	Thread.sleep(2000);
	    	if (toastermsg1.equalsIgnoreCase("Yes")){
	    		System.out.println("Policy saved successfully");
	    		Thread.sleep(10000);
//	        	clickByXpath(prop.getProperty("ToProfile.SelPolicySel.Xpath"));
//	        	Thread.sleep(3000);
    			PageNavigation.NavigateToPage("Configuration","Profiles");
    			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'"+ProName+"')]")));
    			clickByXpath("//div[contains(text(),'"+ProName+"')]");
    			Thread.sleep(3000);
    			clickByXpath(prop.getProperty("ToProfile.StringValue.Xpath"));
	    		driver.findElement(By.xpath(prop.getProperty("ToProfile.StringValue.Xpath"))).clear();
	        	enterByXpath(prop.getProperty("ToProfile.StringValue.Xpath"),"Hello234234234");
	        	clickByXpath(prop.getProperty("ToProfile.IntegerValue.Xpath"));
	        	driver.findElement(By.xpath(prop.getProperty("ToProfile.IntegerValue.Xpath"))).clear();
	        	enterByXpath(prop.getProperty("ToProfile.IntegerValue.Xpath"),"12");
	        	clickByXpath(prop.getProperty("ToProfile.PolicySave.Xpath"));
	        	Thread.sleep(2000);
	        	WebElement validationIcon=driver.findElement(By.xpath(prop.getProperty("ToProfile.ValidationIcon.Xpath")));
//	        	String toastermsg=toasterVerification("Updated Successfully.!!!");
//	        	System.out.println(toastermsg);
	        	if (!validationIcon.isDisplayed()){
	        		Assert.fail("FAILED Due to Compliance policy saved for incorrect rule");
	        	}
	        	else {
	        		System.out.println("Policy is not saved for Incorrect rule inputs, Validation Successful");
	        		if (getTextByXpath(prop.getProperty("ToProfile.StringValue.Xpath")).length() > 10){
	        		  Assert.fail("FAILED Due to Compliance policy saved for incorrect rule input");
	        		}
	        		else {
	        			Thread.sleep(3000);
	        			System.out.println("String Max length validation is Successful");
	        		}
	        	}
	    	}
	    	else {
	    		Assert.fail("FAILED due to Proper input is not saved succssfully");
	    	}
    	
		return this;
    	 
     }
     
     public ComplProfile RunJobAllDevice(Dataset ds,String JbName,String Recurrence) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
    	 Thread.sleep(5000);
    	 WebDriverWait wait = new WebDriverWait(driver, 10);
 		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("ToProfile.Runjob.Xpath"))));
    	 clickByXpath(prop.getProperty("ToProfile.Runjob.Xpath"));
    	 Thread.sleep(3000);
    	 clickByXpath(prop.getProperty("ToProfile.Qfilter.Xpath"));
    	 Thread.sleep(2000);
    	 clickByXpath(prop.getProperty("ToProfile.DevClick.Xpath"));
//    	 enterByXpath(prop.getProperty("ToProfile.DevEnter.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
    	 Thread.sleep(5000);
    	 clickByXpath(prop.getProperty("ToProfile.CheckBox.Xpath"));
    	 Thread.sleep(10000);
    	 WebElement Latest= driver.findElementById(prop.getProperty("ToProfile.LatestArch.Id"));
    	 WebElement Current=driver.findElementById(prop.getProperty("ToProfile.CurrentArch.Id"));
    	 if(ds.getAsString("ArchiveSel").equalsIgnoreCase("Current")){
    			 Current.click();
    		 }
    		 else if(ds.getAsString("ArchiveSel").contains("Latest")){
    			 Latest.click();
    		 }
         Thread.sleep(2000);
         clickById(prop.getProperty("Toprofile.Nextbtn.Id"));
         enterByXpath(prop.getProperty("ToProfile.JobnameEnt.Xpath"),JbName);
         Thread.sleep(2000);
         if(Recurrence.equalsIgnoreCase("yes")){
        	 clickByXpath(prop.getProperty("ToProfile.RecurrenceOptionMinute.Xpath"));
        	 driver.findElement(By.id(prop.getProperty("ToProfile.RecurrenceMinuteValue.Id"))).clear();
        	 enterById(prop.getProperty("ToProfile.RecurrenceMinuteValue.Id"), ds.getAsString("MinValue"));
        	 System.out.println("Recurrence Value Minute is selected");
         }
         else {
        	 System.out.println("No Recurrence value is selected");
         }
         clickById(prop.getProperty("ToProfile.JobSubmit.Id"));
		return this;
     }
     
     public ComplProfile ProfileSelectOnExisting(Dataset ds,String ProName) throws InterruptedException{
    	Thread.sleep(7000);
    	WebElement RuleTablePolicyName=driver.findElementByXPath(prop.getProperty("ToProfile.RuleTablePolicyName.Xpath"));
    	List<WebElement> RuleTableBlank=driver.findElementsByXPath(prop.getProperty("ToProfile.RuleTablePolicyName.Xpath"));
        String RuleTitle =RuleTablePolicyName.getText();
        if(RuleTableBlank.size()>0){
		    if (!RuleTitle.contains(ds.getAsString("policyName"))){
		    	System.out.println("Required Policy Profile is not selected, Now Selecting");
		    	String PolicyNameElement="//div[text()='"+ProName+"']";
		    	if (driver.findElementsByXPath(PolicyNameElement).size()>0){
		    		clickByXpath(PolicyNameElement);
		    	}
		    	else{
		    		Assert.fail("FAILED: Policy Profile not Exsist, please Check once");
		    	}
		    }
		    else {
		    	System.out.println("Required Policy Profile is already selected");
		    }
        }
        else {
        	System.out.println("Required Policy Profile is not selected, Now Selecting");
        	String PolicyNameElement="//div[text()='"+ProName+"']";
	    	if (driver.findElementsByXPath(PolicyNameElement).size()>0){
	    		clickByXpath(PolicyNameElement);
	    	}
	    	else{
	    		Assert.fail("FAILED: Policy Profile not Exsist, please Check once");
	    	}
        	
        }
		return this;
    	 
     }
     
     public ComplProfile CheckProfilePresence(String profileName){
    	 String Profiles = getTextByXpath(prop.getProperty("//div[@id='complianceProfileObjSel']//div[@class='dijitReset xwtobjectselectorlist']"));
    			 if (Profiles.contains(profileName))
    				 TestLogger.info("Searched Profile is Present");
    			 else 
    				 Assert.fail("FAILED: Profile name not exists");
    	return this;
     }
     
     public String toasterVerification(String text){
    	 String toasterVerify="";
    	 List<WebElement> toaster = driver.findElementsByXPath(prop.getProperty("ToProfile.Toaster.Xpath"));
    	 List<WebElement> toasterText=driver.findElementsByXPath("//div[text()='"+text+"']");
         if (toaster.size()>0 & toasterText.size()>0 ){
         	System.out.println("Policy saveed saved and toaster Message verified successfully");
         	toasterVerify="Yes";
         }
         else {
         	System.out.println("Policy not save check the rule require inputs");
         	toasterVerify="No";
         }
		return toasterVerify;
     }
     
 }
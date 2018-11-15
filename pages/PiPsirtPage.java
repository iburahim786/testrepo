package com.cisco.ui.test.compliance.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.test.tea.log.TestLogger;
import com.cisco.ui.test.compliance.wrappers.utils.HashMapValue;
import com.cisco.ui.test.compliance.wrappers.utils.OpentapsWrappers;
import com.cisco.ui.test.compliance.wrappers.utils.PageNavigation;
import com.cisco.ui.test.compliance.wrappers.utils.SSHloginUtil;

public class PiPsirtPage extends OpentapsWrappers {
	
	public PiPsirtPage(RemoteWebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageNavigation.NavigateToPage("Reports","PSIRT and EOX");
	}
   
	public PiPsirtPage JobSchedule(Dataset ds) throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Thread.sleep(2000);
	  if (driver.findElement(By.id(prop.getProperty("ToPsirt.JobSchbtn.Id"))).getAttribute("aria-disabled").contains("false")){
		wait.until(ExpectedConditions.elementToBeClickable(By.id(prop.getProperty("ToPsirt.JobSchedule.Id")))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("ToPsirt.JobScheduleAgree.xpath")))).click();
		Thread.sleep(2000);
		final Actions builder = new Actions(driver);
		builder.sendKeys(Keys.ESCAPE).perform();
	  }
	  else {
		  System.out.println("PAS Jobs already triggered, Please check the Job status");
	  }
		return this;
		
	}
	
	public PiPsirtPage PsirtstatusUnknown(String status) throws Exception{
	    int i=1;
	    while(status.equalsIgnoreCase("UNKNOWN")){
	    	Thread.sleep(5000);
	    	System.out.println("Status is showing: 'UNKNOWN', Please Schedule the job again");
	    	driver.navigate().refresh();
			PageNavigation.waitUntilLoginCompleted(driver);
			Thread.sleep(3000);
	    	JobSchedule(ds);
	    	WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(By.id(prop.getProperty("ToPsirt.JobView.Id")))).click();
			Thread.sleep(2000);
		    String Unknstatus=driver.findElementById(prop.getProperty("ToPsirt.OperationalStatus.id")).getText();
		    status=Unknstatus;
		    System.out.println("For loop-"+i+ " --> Status is: "+ status);
	    	i=i+1;
	    	if(i>=3){
	    		 Assert.fail("FAILED: Tried 3 times, Still Job Status is UNKNOWN");
				 break;
	    	}
	    }
		return this;
	}
	
	public PiPsirtPage PsirtJobStatus(Dataset ds) throws Exception{
		clickByXpath(prop.getProperty("ToPsirt.PageRefresh.Xpath"));
		Thread.sleep(5000);
		clickByXpath(prop.getProperty("ToPsirt.PageRefresh.Xpath"));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(prop.getProperty("ToPsirt.JobView.Id")))).click();
		Thread.sleep(2000);
	    String status=driver.findElementById(prop.getProperty("ToPsirt.OperationalStatus.id")).getText();
	    System.out.println("After schedule the PSIRT Job, Initial Status is: "+status);
	    PsirtstatusUnknown(status);
	    if(status.equalsIgnoreCase("UNKNOWN")){
	    System.out.println("Status is Unknown, Hence need to chech the status again");}
	    String status1=driver.findElementById(prop.getProperty("ToPsirt.OperationalStatus.id")).getText();
	    status=status1;
		   if(status.equalsIgnoreCase("COMPLETED_WITH_SUCCESS")){
			   System.out.println("Completed with success");
			   Thread.sleep(3000);
			   driver.navigate().refresh();
			   PageNavigation.waitUntilLoginCompleted(driver);
//			   builder.sendKeys(Keys.ESCAPE).perform();
		   }
		   else if(status.equalsIgnoreCase("COMPLETED_WITH_FAILURE")){
			   System.out.println("Completed with Failure");
			   Assert.fail("FAILED: Job completed with Failure");
		   }
		   else {
	            while(status.equalsIgnoreCase("JOB_RUNNING")) {
			    	Thread.sleep(1000);
			    	final Actions builder = new Actions(driver);
					builder.sendKeys(Keys.ESCAPE).perform();
					Thread.sleep(3000);
					wait.until(ExpectedConditions.elementToBeClickable(By.id(prop.getProperty("ToPsirt.JobView.Id")))).click();
					String currentstatus=driver.findElementById(prop.getProperty("ToPsirt.OperationalStatus.id")).getText();
					status=currentstatus;
					System.out.println("Current job status is :" +status);
				   if (status.equalsIgnoreCase("COMPLETED_WITH_SUCCESS")){
					   System.out.println("Completed with success");
					   Thread.sleep(3000);
					   driver.navigate().refresh();
					   PageNavigation.waitUntilLoginCompleted(driver);
		//			   builder.sendKeys(Keys.ESCAPE).perform();
					   break;
				   }
				   else if(status.equalsIgnoreCase("COMPLETED_WITH_FAILURE")){
					   System.out.println("Completed with Failure");
					   Assert.fail("FAILED: Job completed with Failure");
					   break;
				   }
				   else if(status.equalsIgnoreCase("UNKNOWN")){
					   Thread.sleep(3000);
					   driver.navigate().refresh();
					   PageNavigation.waitUntilLoginCompleted(driver);
					   PsirtJobStatus(ds);
				   }
			    }
		   }
		return this;
	}
	
	public static String PsirtLastRunStatusDetails() throws InterruptedException{
		Thread.sleep(3000);
		String LastRunStatus="";
		LastRunStatus =driver.findElement(By.id("lastrunresult")).getText();
		return LastRunStatus;
		
	}
	
	public PiPsirtPage PsirtLastRunStatusValidation(String text) throws InterruptedException{
		String LastRunStatus=PsirtLastRunStatusDetails();
		if (LastRunStatus.equalsIgnoreCase(text))
			TestLogger.info("PSIRT job details verified Successfully");
		else
			Assert.fail("FAILED:PSIRT job details is not verified Successfully ");
		
		return this;
		
	}
	public PiPsirtPage PsirtHelpIconValidation(Dataset ds) throws InterruptedException{
		 Thread.sleep(3000);
		  if (driver.findElementsByXPath(prop.getProperty("ToJob.HelpIcon.Xpath")).size()>0){
			clickByXpath(prop.getProperty("ToJob.HelpIcon.Xpath"));
			Thread.sleep(7000);
			String pwindow = driver.getWindowHandle();
			String PsirtPageTitle=driver.getTitle();
			System.out.println("Parent Window Title is : "+PsirtPageTitle);
			Set<String> s1=driver.getWindowHandles();
			Iterator<String> I1= s1.iterator();
			while(I1.hasNext()){
			    String cwindow=I1.next();
				Thread.sleep(2000);
				if(!pwindow.equals(cwindow))
				{
				driver.switchTo().window(cwindow);
				String wintitle=driver.switchTo().window(cwindow).getTitle();
				System.out.println("Child Window Title is: "+ wintitle);
				if (wintitle.equalsIgnoreCase(ds.getAsString("HelpWindowTitle"))){
					System.out.println("PSIRT Page Help Icon is Validated Successfully");
				}
				else {
					Assert.fail("Failed:PSIRT Page Help Icon is not Validated Successfully");
				}
				driver.close();
				}
		   }
			driver.switchTo().window(pwindow);
		  }
		  else {
			  Assert.fail("Failed:PSIRT Page Help Icon element is not Available/Clicable");
		  }
			return this;
		}
	
	public PiPsirtPage PsirtDNSAddedDeviceEOXValidation() throws InterruptedException{
		Thread.sleep(3000);
		clickById(prop.getProperty("ToPsirt.PSIRTFilterExpand.Id"));
		Thread.sleep(2000);
		clickByXpath(prop.getProperty("ToPsirt.PSIRTIpaddressColumn.Xpath"));
		enterByXpath(prop.getProperty("ToPsirt.PSIRTIpaddressColumn.Xpath"),"10.197.72.23");
		Thread.sleep(2000);
		String PsirttableCount=driver.findElement(By.xpath(prop.getProperty("ToPsirt.PsirtTotalCount.Xpath"))).getText();
		String[] CountSplit=PsirttableCount.split(" ");
		int Count = Integer.parseInt(CountSplit[1]);
        if(Count>1){
        	System.out.println("PSIRT data Validation is Successfull");
        }
        else {
        	Assert.fail("FAILED: PSIRT data is not Populated Successfully");
        }
        clickById(prop.getProperty("ToPsirt.HwEoxtab.id"));
        Thread.sleep(2000);
        clickById(prop.getProperty("ToPsirt.HwEox.FilterExpand.id"));
        Thread.sleep(2000);
        clickByXpath(prop.getProperty("ToPsirt.HwIpaddressColumn.Xpath"));
        enterByXpath(prop.getProperty("ToPsirt.HwIpaddressColumn.Xpath"),"10.197.72.23");
        Thread.sleep(3000);
        String HwtotalCount=driver.findElement(By.xpath(prop.getProperty("ToPsirt.HwTotalCount.xpath"))).getText();
        if(HwtotalCount.equalsIgnoreCase("Total 1")){
        	System.out.println("HW data Validation is Successfull");
        } 
        else {
        	Assert.fail("FAILED: HW data is not Populated Successfully");
        }
        
        clickById(prop.getProperty("ToPsirt.SwEoxtab.id"));
        Thread.sleep(2000);
        clickById(prop.getProperty("ToPsirt.SwEox.FilterExpand.id"));
        Thread.sleep(2000);
        clickByXpath(prop.getProperty("ToPsirt.SwIpaddressColumn.Xpath"));
        enterByXpath(prop.getProperty("ToPsirt.SwIpaddressColumn.Xpath"),"10.197.72.23");
        Thread.sleep(3000);
        String SwtotalCount=driver.findElement(By.xpath(prop.getProperty("ToPsirt.SwTotalCount.xpath"))).getText();
        if(SwtotalCount.equalsIgnoreCase("Total 1")){
        	System.out.println("SW data Validation is Successfull");
        } 
        else {
        	Assert.fail("FAILED: SW data is not Populated Successfully");
        }
        
		return this;
	}
   
//	public PiPsirtPage PsirtSwEoxImageVerCheck(Dataset ds,String version) throws Exception{
//		Thread.sleep(3000);
//		clickById(prop.getProperty("ToPsirt.PSIRTFilterExpand.Id"));
//		Thread.sleep(2000);
//		clickByXpath(prop.getProperty("ToPsirt.PSIRTIpaddressColumn.Xpath"));
//		enterByXpath(prop.getProperty("ToPsirt.PSIRTIpaddressColumn.Xpath"),deviceprop.getProperty(""+ds.getAsString("deviceid")+".Ip_Dns"));
//		Thread.sleep(2000);
//	    String pVersion=driver.findElement(By.xpath(prop.getProperty("ToPsirt.PsirtVersionColnm.Xpath"))).getText();
//        if(pVersion.equalsIgnoreCase(version)){
//        	System.out.println("In PSIRT page, Device Image version is verified Successfully");
//        }
//        else{
//        	Assert.fail("FAILED:In PSIRT page, Device Image version is not verified Successfully ");
//        }
//        Thread.sleep(5000);
//        clickById(prop.getProperty("ToPsirt.SwEoxtab.id"));
//        Thread.sleep(2000);
//        clickById(prop.getProperty("ToPsirt.SwEox.FilterExpand.id"));
//        Thread.sleep(2000);
//        clickByXpath(prop.getProperty("ToPsirt.SwIpaddressColumn.Xpath"));
//        enterByXpath(prop.getProperty("ToPsirt.SwIpaddressColumn.Xpath"),deviceprop.getProperty(""+ds.getAsString("deviceid")+".Ip_Dns"));
//        Thread.sleep(5000);
//	    String sVersion=driver.findElement(By.xpath(prop.getProperty("ToPsirt.SofEoxVersionColnm.Xpath"))).getText();
//        if(sVersion.equalsIgnoreCase(version)){
//        	System.out.println("In Software EOX page, Device Image version is verified Successfully");
//        }
//        else{
//        	Assert.fail("FAILED:In Software EOX page, Device Image version is not verified Successfully ");
//        }
//		return this;
//	}
	
	public PiPsirtPage PsirtSwEoxImageVerCheck(String Platform,String deviceId,String version) throws Exception{
		Thread.sleep(3000);
		clickById(prop.getProperty("ToPsirt.PSIRTtab.Id"));
		Thread.sleep(2000);
		clickById(prop.getProperty("ToPsirt.PSIRTFilterExpand.Id"));
		Thread.sleep(2000);
		clickByXpath(prop.getProperty("ToPsirt.PSIRTIpaddressColumn.Xpath"));
		enterByXpath(prop.getProperty("ToPsirt.PSIRTIpaddressColumn.Xpath"),deviceprop.getProperty(""+deviceId+".Ip_Dns"));
		Thread.sleep(2000);
	    String pVersion=driver.findElement(By.xpath(prop.getProperty("ToPsirt.PsirtVersionColnm.Xpath"))).getText();
        if(pVersion.equalsIgnoreCase(version)){
        	System.out.println("In PSIRT page, "+Platform+" Device Image version is verified Successfully in PSIRT tab");
        }
        else{
        	Assert.fail("FAILED:In PSIRT page, "+Platform+" Device Image version is not verified Successfully in PSIRT tab ");
        }
        Thread.sleep(5000);
        clickById(prop.getProperty("ToPsirt.SwEoxtab.id"));
        Thread.sleep(2000);
        clickById(prop.getProperty("ToPsirt.SwEox.FilterExpand.id"));
        Thread.sleep(2000);
        clickByXpath(prop.getProperty("ToPsirt.SwIpaddressColumn.Xpath"));
        enterByXpath(prop.getProperty("ToPsirt.SwIpaddressColumn.Xpath"),deviceprop.getProperty(""+deviceId+".Ip_Dns"));
        Thread.sleep(5000);
	    String sVersion=driver.findElement(By.xpath(prop.getProperty("ToPsirt.SofEoxVersionColnm.Xpath"))).getText();
        if(sVersion.equalsIgnoreCase(version)){
        	System.out.println("In Software EOX page, "+Platform+" Device Image version is verified Successfully in S/W EOX tab");
        }
        else{
        	Assert.fail("FAILED:In Software EOX page, "+Platform+" Device Image version is not verified Successfully in S/W EOX tab ");
        }
		return this;
	}
	
	public PiPsirtPage PasRbmldatesVerification(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		String pasDt=wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(prop.getProperty("ToPsirt.PasDates.Id"))))).getText();
		String rbmlDt=wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(prop.getProperty("ToPsirt.RbmlDates.Id"))))).getText();
		if (pasDt.contains("201") && rbmlDt.contains("201")){
			TestLogger.debug("PAS and RBML dates are present in the PSIRT page");
		}
		return this;
	}
	
	public PiPsirtPage PasRbmlDatesCrossVerify(Dataset ds) throws Exception{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		String pasDtUi=wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(prop.getProperty("ToPsirt.PasDates.Id"))))).getText();
		String rbmlDtUi=wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(prop.getProperty("ToPsirt.RbmlDates.Id"))))).getText();
		SSHloginUtil.UnzipRbmlBundlefile(ds);
		Thread.sleep(5000);
		HashMapValue.getFileFromPIServer(ipaddress, SshUsername, SshPassword, "/opt/CSCOlumos/compliance/bin/PAS_LOG/", ds.getAsString("PasFile"),ds.getAsString("File"));
		HashMapValue.getFileFromPIServer(ipaddress, SshUsername, SshPassword, "/opt/CSCOlumos/compliance/bin/ZIP_DATA/PROCESSED/",ds.getAsString("RbmlFile"),ds.getAsString("File"));
		String rbmlstr=HashMapValue.XmlRead("Backup","CreatedOn",ds.getAsString("RbmlFile"));
		String passtr=HashMapValue.XmlRead("Backup","CreatedOn",ds.getAsString("PasFile"));
		long rbmllong = Long.parseLong(rbmlstr);
		long paslong = Long.parseLong(passtr);
        
		String rbmlDtBknd=HashMapValue.UnixDateConverter(rbmllong);
		String pasDtBknd=HashMapValue.UnixDateConverter(paslong);
		
		if (pasDtUi.contains(pasDtBknd) && rbmlDtUi.contains(rbmlDtBknd)){
			TestLogger.debug(pasDtUi+" is matched with BackEnd Date"+pasDtBknd);
			TestLogger.debug(rbmlDtUi+" is matched with BackEnd Date"+rbmlDtBknd);
		}
		else {
			Assert.fail("FAILED:"+ pasDtBknd+" and "+rbmlDtBknd+" is not verified");
		}
		return this;
	}
	
	public PiPsirtPage PresenceOfCVEnCaveatColumnndSorting(Dataset ds) throws Exception{
		Thread.sleep(10000);
//      String MatchReason =driver.findElementByXPath(prop.getProperty("ToPsirt.MatchReaColmn.Xpath")).getText();
        String Caveat =driver.findElementByXPath(prop.getProperty("ToPsirt.CaveatColmn.Xpath")).getText();
        String Cve =driver.findElementByXPath(prop.getProperty("ToPsirt.CveColumn.Xpath")).getText();
		clickByXpath(prop.getProperty("ToPsirt.MatchReaColmn.Xpath")); //Match reason column clicked.
	if (Caveat.equalsIgnoreCase("Caveat") && Cve.equalsIgnoreCase("CVE"))
		TestLogger.debug(Caveat+" and "+Cve+" are present the PSIRT tab.");
	else{
		System.err.println(Caveat+" and "+Cve+" are not present the PSIRT tab.");
	    Assert.fail();
	}
		Thread.sleep(3000);
    	String rowEle="(//table[@class='row-table']/tbody/tr)";
 		List<WebElement> row=driver.findElements(By.xpath(rowEle));
 		int rowsize= row.size();
 		System.out.println("PSIRT tab Row Size is :" + rowsize);
 	if(rowsize>=2){
 		clickByXpath(prop.getProperty("ToPsirt.CaveatColmn.Xpath"));
 		Thread.sleep(5000);
 		WebElement AfterSortAscClick = driver.findElement(By.xpath(("(//table[@class='row-table']/tbody/tr)[1]/td[10]/div")));
 		String Asceclick=AfterSortAscClick.getText().trim();
 		Thread.sleep(5000);
 		clickByXpath(prop.getProperty("ToPsirt.CaveatColmn.Xpath"));
 		WebElement AfterSortDesClick = driver.findElement(By.xpath(("(//table[@class='row-table']/tbody/tr)[1]/td[10]/div")));
 		Thread.sleep(3000);
 		String Desclick="";
 		String Message="";
		try {
			Desclick = AfterSortDesClick.getText().trim();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			   Message=e.getMessage();
			   System.out.println("Exception Message is "+Message);
		}
 		if(Asceclick!=Desclick || Message.contains("xception") ){
 			System.out.println("Caveat Column sort is working fine and Caveat Empty Value Throwing Exception");
 		}
 		else if(AfterSortDesClick.getText()==""){
 			System.out.println("Caveat Column sort is working fine and Caveat Column has Empty value ");
 		}
 		else
 	    System.err.println("Caveat Column sort is not working fine");
 		Thread.sleep(3000);
 		clickByXpath(prop.getProperty("ToPsirt.CveColumn.Xpath"));
 		Thread.sleep(5000);
 		WebElement AfterSortAscClick1 = driver.findElement(By.xpath(("(//table[@class='row-table']/tbody/tr)[1]/td[11]/div")));
 		String Asceclick1=AfterSortAscClick1.getText().trim();
 		Thread.sleep(3000);
 		clickByXpath(prop.getProperty("ToPsirt.CveColumn.Xpath"));
 		Thread.sleep(5000);
 		WebElement AfterSortDesClick1 = driver.findElement(By.xpath(("(//table[@class='row-table']/tbody/tr)[1]/td[11]/div")));
 		Thread.sleep(3000);
 		String Desclick1="";
 		String Message1="";
		try {
			Desclick1 = AfterSortDesClick1.getText().trim();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			   Message1=e.getMessage();
			   System.out.println("Exception Message is "+Message1);
		}
 		if(Asceclick1!=Desclick1 || Message1.contains("xception") ){
 			System.out.println("Caveat Column sort is working fine and CVE Empty Value Throwing Exception");
 		}
 		else if(AfterSortDesClick.getText()==""){
 			System.out.println("Caveat Column sort is working fine and CVE Column has Empty value");
 		}
 		else
 	    Assert.fail("FAILED: Caveat Column sort is not working fine");
 	}
 	else {
 		System.out.println("Only one data is available, Sorting is not Applicable for one data in Table");
 	}
   
 	return this;
	}
	
	public PiPsirtPage SimilarDevicePSIRTdetailsValidation(String DevType) throws Exception{
		Thread.sleep(3000);
		clickById(prop.getProperty("ToPsirt.PSIRTtab.Id"));
		Thread.sleep(2000);
		clickById(prop.getProperty("ToPsirt.PSIRTFilterExpand.Id"));
		Thread.sleep(2000);
		clickByXpath(prop.getProperty("ToPsirt.PSIRTDevNameColumn.Xpath"));
		enterByXpath(prop.getProperty("ToPsirt.PSIRTDevNameColumn.Xpath"),"16.1.1.2");
		Thread.sleep(2000);
		clickByXpath(prop.getProperty("ToPsirt.PSIRTDevTypeColumn.Xpath"));
		enterByXpath(prop.getProperty("ToPsirt.PSIRTDevTypeColumn.Xpath"),DevType);
		Thread.sleep(2000);
	    String tcount=driver.findElement(By.xpath(prop.getProperty("ToPsirt.PsirtTotalCount.Xpath"))).getText();
	    String[] countStr=tcount.split(" ");
	    int countInt=Integer.parseInt(countStr[1]);
        if(countInt%2==0){
        	System.out.println("In PSIRT page, "+DevType+" Device having proper PISRT for both device in PSIRT tab");
        }
        else{
        	Assert.fail("FAILED:In PSIRT page, "+DevType+" Device doesn't having proper PISRT for both device in PSIRT tab");
        }
		return this;
	}
	
	public PiPsirtPage psirtTitleValidation(String titleText) throws InterruptedException{
		Thread.sleep(3000);
		clickById(prop.getProperty("ToPsirt.PSIRTtab.Id"));
		Thread.sleep(2000);
		clickById(prop.getProperty("ToPsirt.PSIRTFilterExpand.Id"));
		Thread.sleep(2000);
		clickByXpath(prop.getProperty("ToPsirt.PSIRTPsirtColumn.Xpath"));
		enterByXpath(prop.getProperty("ToPsirt.PSIRTPsirtColumn.Xpath"),titleText);
		Thread.sleep(2000);
	    String actualPsirtTitle=driver.findElement(By.xpath(prop.getProperty("ToPsirt.PSIRTPsirtCell.Xpath"))).getText();
        if(actualPsirtTitle.contains(titleText)){
        	System.out.println("In PSIRT page, Psirt Title: "+actualPsirtTitle+" successfully verified in PSIRT tab");
        }
        else{
        	Assert.fail("FAILED:In PSIRT page, Psirt Title: "+actualPsirtTitle+" not successfully verified in PSIRT tab");
        }
		return this;
	}
	
	public PiPsirtPage HwEolValidation(String titleText) throws InterruptedException{
		Thread.sleep(3000);
		clickById(prop.getProperty("ToPsirt.HwEoxtab.id"));
		Thread.sleep(2000);
		clickById(prop.getProperty("ToPsirt.HwEox.FilterExpand.id"));
		Thread.sleep(2000);
		clickByXpath(prop.getProperty("ToPsirt.HWEolColumn.Xpath"));
		enterByXpath(prop.getProperty("ToPsirt.HWEolColumn.Xpath"),titleText);
		Thread.sleep(2000);
	    String actualHwEolTitle=driver.findElement(By.xpath(prop.getProperty("ToPsirt.HWEolCell.Xpath"))).getText();
        if(actualHwEolTitle.contains(titleText)){
        	System.out.println("In PSIRT page, HW EOL Title: "+actualHwEolTitle+" successfully verified in PSIRT tab");
        }
        else{
        	Assert.fail("FAILED:In PSIRT page, HW EOL Title: "+actualHwEolTitle+" not successfully verified in PSIRT tab");
        }
		return this;
	}
	
	public PiPsirtPage SwEolValidation(String titleText) throws InterruptedException{
		Thread.sleep(3000);
		clickById(prop.getProperty("ToPsirt.SwEoxtab.id"));
		Thread.sleep(2000);
		clickById(prop.getProperty("ToPsirt.SwEox.FilterExpand.id"));
		Thread.sleep(2000);
		clickByXpath(prop.getProperty("ToPsirt.SWEolColumn.Xpath"));
		enterByXpath(prop.getProperty("ToPsirt.SWEolColumn.Xpath"),titleText);
		Thread.sleep(2000);
	    String actualSwEolTitle=driver.findElement(By.xpath(prop.getProperty("ToPsirt.SWEolCell.Xpath"))).getText();
        if(actualSwEolTitle.contains(titleText)){
        	System.out.println("In PSIRT page, SW EOL Title: "+actualSwEolTitle+" successfully verified in PSIRT tab");
        }
        else{
        	Assert.fail("FAILED:In PSIRT page, SW EOL Title: "+actualSwEolTitle+" not successfully verified in PSIRT tab");
        }
		return this;
	}
	
	public PiPsirtPage FnValidation(String titleText) throws InterruptedException{
		Thread.sleep(3000);
		clickById(prop.getProperty("ToPsirt.FNEoxtab.id"));
		Thread.sleep(2000);
		clickById(prop.getProperty("ToPsirt.FNEox.FilterExpand.id"));
		Thread.sleep(2000);
		clickByXpath(prop.getProperty("ToPsirt.FNFnNameColumn.Xpath"));
		enterByXpath(prop.getProperty("ToPsirt.FNFnNameColumn.Xpath"),titleText);
		Thread.sleep(2000);
	    String actualFNTitle=driver.findElement(By.xpath(prop.getProperty("ToPsirt.FNFnNameCell.Xpath"))).getText();
        if(actualFNTitle.contains(titleText)){
        	System.out.println("In PSIRT page, FN Title: "+actualFNTitle+" successfully verified in PSIRT tab");
        }
        else{
        	Assert.fail("FAILED:In PSIRT page, FN Title: "+actualFNTitle+" not successfully verified in PSIRT tab");
        }
		return this;
	}
	
	public PiPsirtPage ScaleSleep(int msec) throws InterruptedException{
		TestLogger.info("Waiting :"+msec+" sec for PSIRT Job to Complete..");
		Thread.sleep(msec);
		return this;
		
		
	}
}


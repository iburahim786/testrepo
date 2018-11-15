package com.cisco.ui.test.compliance.pages;

import java.awt.Robot;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.AssertThrows;
import org.testng.Assert;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentTest;
import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.test.tea.log.TestLogger;
import com.cisco.ui.test.compliance.exception.ComplianceAutoException;
import com.cisco.ui.test.compliance.wrappers.utils.OpentapsWrappers;
import com.cisco.ui.test.compliance.wrappers.utils.PageNavigation;

public class CompleJobDashboardPage extends OpentapsWrappers {
	
	
	public CompleJobDashboardPage(RemoteWebDriver driver, ExtentTest test){
		this.driver= driver;
		this.test= test;
		PageNavigation.NavigateToPage("Administration", "Job Dashboard");
		
	}
	public CompleJobDashboardPage ModuleJobstable(String JobCat, String JobMod, String JbName) throws InterruptedException{
	   Thread.sleep(5000);
//	   WebDriverWait wait = new WebDriverWait(driver, 20);
//	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(prop.getProperty("ToJob.JobCate.ClassName"))));
//	   String CompJobTitle=driver.findElementByClassName(prop.getProperty("ToJob.JobCate.ClassName")).getText();
//	   System.out.println(CompJobTitle);
//	   if (!CompJobTitle.equalsIgnoreCase("Compliance Jobs")){
//		 Thread.sleep(3000);
		 JobCatNavigation(JobCat, JobMod);
//	   }
//	   else {
//		   System.out.println(JobCat+"==>"+JobCat+ " is Naviagted Already");
//	   }
		clickByXpath(prop.getProperty("ToJob.JobNameSearch.Xpath"));
		enterByXpath(prop.getProperty("ToJob.JobNameSearch.Xpath"), JbName);
		return this;
	}
	
	public CompleJobDashboardPage jobHelpIconValidation(Dataset ds) throws InterruptedException{
	 Thread.sleep(3000);
	  if (driver.findElementsByXPath(prop.getProperty("ToJob.HelpIcon.Xpath")).size()>0){
		clickByXpath(prop.getProperty("ToJob.HelpIcon.Xpath"));
		Thread.sleep(7000);
		String pwindow = driver.getWindowHandle();
		String JobPageTitle=driver.getTitle();
		System.out.println("Parent Window Title is : "+JobPageTitle);
		Set<String> s1=driver.getWindowHandles();
		Iterator<String> I1= s1.iterator();
		while(I1.hasNext())
		{
		    String cwindow=I1.next();
			Thread.sleep(2000);
			if(!pwindow.equals(cwindow))
			{
			driver.switchTo().window(cwindow);
			String wintitle=driver.switchTo().window(cwindow).getTitle();
			System.out.println("Child Window Title is: "+ wintitle);
			if (wintitle.equalsIgnoreCase(ds.getAsString("HelpWindowTitle"))){
				System.out.println("Job Dashaboard Help Icon is Validated Successfully");
			}
			else {
				Assert.fail("Failed:Job Dashaboard Help Icon is not Validated Successfully");
			}
			driver.close();
			}
	   }
		driver.switchTo().window(pwindow);
	  }
	  else {
		  Assert.fail("Failed:Job Dashaboard Help Icon element is not Available/Clicable");
	  }
		return this;

		
	}
	
	public void JobCatNavigation(String JobCat, String JobMod) {
		// TODO Auto-generated method stub
		try{
//		   if (verifyTextContainsByClass(prop.getProperty("ToJob.JobCate.ClassName"), JobMod)){
//			   System.out.println(""+JobMod+"Already Navigated");
//		   }
//		   else{
		    String xpathValueJobCat= "//span[text()='"+JobCat+"']";
			driver.findElement(By.xpath(xpathValueJobCat)).click();
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(prop.getProperty("ToJob.JobCate.ClassName"))));
			if (verifyTextContainsByClass(prop.getProperty("ToJob.JobCate.ClassName"), JobCat)){
			     System.out.println(""+JobCat+" Navigated Successfully");
				 String xpathValueModuleJob= "//span[text()='"+JobMod+"']";
			     driver.findElement(By.xpath(xpathValueModuleJob)).click();
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(prop.getProperty("ToJob.JobCate.ClassName"))));
				 if(verifyTextContainsByClass(prop.getProperty("ToJob.JobCate.ClassName"), JobMod)){
		         reportStep("The element : "+xpathValueJobCat+" and "+xpathValueModuleJob+" are clicked.", "PASS");
				 }
				 else{
					 Assert.fail("The element : "+xpathValueJobCat+" and "+xpathValueModuleJob+"  are not clicked.");
				 }
			}
				 else{
					 Assert.fail("The element : "+xpathValueJobCat+" is not clicked."); 
				 }
//		} 
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

}
    public CompleJobDashboardPage JobStatus(String Status) throws InterruptedException{
    	Thread.sleep(10000);
		for(int i=1; i<=40; i++){
			String runstatus = getTextByXpath(prop.getProperty("ToJob.Cstatus.Xpath"));
			String nostatus= getTextByXpath(prop.getProperty("ToJob.NodataAvailable.Xpath"));
			if(runstatus.equalsIgnoreCase("Running")){
				clickByXpath(prop.getProperty("ToJob.MainTableRefresh.Xpath"));
				Thread.sleep(10000);
			}
			else if(!runstatus.equalsIgnoreCase("Running") && !nostatus.equalsIgnoreCase("No data is available"))
			{
				Thread.sleep(3000);
				String jobstatus= getTextByXpath(prop.getProperty("ToJob.ClickJobStatusLink.Xpath"));
				if(!jobstatus.equalsIgnoreCase(Status)){
//					System.out.println("Running Job is completed and in'"+ jobstatus +"' state,there is mismatch in the described results");
				    Assert.fail("FAILED: Running Job is completed and in'"+jobstatus+"' state,there is mismatch in the described results");
					break;
				}
				else if(runstatus.equalsIgnoreCase("Partial-Success")){
					System.out.println("Running Job is completed and in '"+ jobstatus +"' state ");
					Thread.sleep(3000);
					clickByXpath(prop.getProperty("ToJob.ClickJobStatusLink.Xpath"));
					break;
				}
				else{
					System.out.println("Running Job is completed and in '"+ jobstatus +"' state ");
					Thread.sleep(3000);
					clickByXpath(prop.getProperty("ToJob.ClickJobStatusLink.Xpath"));
					break;
				}
			}
			else if(nostatus.equalsIgnoreCase("No data is available")){
//				System.out.println("Please check the Job Name or might be job not created properly, hence it showing '"+ nostatus+"'");
				Assert.fail("Please check the Job Name or might be job not created properly, hence it showing '"+ nostatus+"'");
				break;
			}
			else if(runstatus.equalsIgnoreCase("Scheduled")){
				clickByXpath(prop.getProperty("ToJob.MainTableRefresh.Xpath"));
				Thread.sleep(3000);
			}
			i=i+1;
		}
    	return this;
    }
    public CompleJobDashboardPage ScaleSleep(int msec) throws InterruptedException{
		TestLogger.info("Waiting "+msec+" for PSIRT Job to Complete..");
		Thread.sleep(msec);
		return this;
    }
    
    public CompleJobDashboardPage VioSumQuickFilDataEnt(int column,String data) throws Exception{
    	Thread.sleep(2000);
    	String Column="//div[@class='table-head-node-container']/descendant::input["+column+"]";
    	clickByXpath(Column);
    	enterByXpath(Column, data);
    	Thread.sleep(2000);
    	return this;
    }
     public CompleJobDashboardPage RunFixjobAllviolation(Dataset ds,String FixJobName) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
//    	 Thread.sleep(5000);
//    	 clickByClassName(prop.getProperty("ToJob.SelectAllViolation.ClassName"));
    	 WebDriverWait wait = new WebDriverWait(driver, 30);
    	 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(prop.getProperty("ToJob.VioDetailsSelectAll.Xpath"))))).click();
    	 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	 Thread.sleep(3000);
    	 Actions action = new Actions(driver);
    	 action.moveToElement(driver.findElement(By.id(prop.getProperty("ToJob.VioDetailsNext.id")))).click().build().perform();
//    	 clickById(prop.getProperty("ToJob.VioDetailsNext.id"));
    	 clickByClassName(prop.getProperty("ToJob.FixJobPolicyExpand.ClassName"));
    	 clickByXpath(prop.getProperty("ToJob.SelectAllFViolation.Xpath"));
    	 clickById(prop.getProperty("ToJob.EnterAllFixInput.Id"));
    	 Thread.sleep(2000);
    	 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(prop.getProperty("ToJob.FixInputValue1.Xpath"))))).click();
    	 enterByXpath(prop.getProperty("ToJob.FixInputValue1.Xpath"),ds.getAsString("FixInput1"));
    	 enterByXpath(prop.getProperty("ToJob.FixInputValue2.Xpath"),ds.getAsString("FixInput2"));
    	 clickById(prop.getProperty("ToJob.FixSaveBtn.Id"));
    	 clickById(prop.getProperty("ToJob.VioDetailsNext.id"));
    	 enterByXpath(prop.getProperty("ToJob.FixJobName.Xpath"),FixJobName);
    	 clickById(prop.getProperty("ToJob.ScheduleFixJob.id"));	 
		return this;
     }
     
     public CompleJobDashboardPage RunFixjobAllvioWithCopyRunEnabled(Dataset ds,String FixJobName) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
//    	 Thread.sleep(5000);
//    	 clickByClassName(prop.getProperty("ToJob.SelectAllViolation.ClassName"));
    	 WebDriverWait wait = new WebDriverWait(driver, 30);
    	 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(prop.getProperty("ToJob.VioDetailsSelectAll.Xpath"))))).click();
    	 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	 Thread.sleep(5000);
    	 clickByXpath("//span[@class='TaskTitle' and contains(text(),'Fix Input')]");
//    	 clickById(prop.getProperty("ToJob.VioDetailsNext.id"));
    	 Thread.sleep(10000);
    	 if(driver.findElements(By.id(prop.getProperty("ToJob.CopyRunStartup.Id"))).size()>0){
    		System.out.println("Save Startup Config option is Present");
    		clickById(prop.getProperty("ToJob.CopyRunStartup.Id"));
    		clickByName(prop.getProperty("ToJob.CopyRunStartCheckBox.Name"));
    			System.out.println("Save Startup Config Faeture is Selected Successfully..");
    	 }
    	 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className(prop.getProperty("ToJob.FixJobPolicyExpand.ClassName"))))).click();
//    	 clickByClassName(prop.getProperty("ToJob.FixJobPolicyExpand.ClassName"));
    	 Thread.sleep(3000);
    	 clickByXpath(prop.getProperty("ToJob.SelectAllFViolation.Xpath"));
    	 Thread.sleep(5000);
    	 clickById(prop.getProperty("ToJob.EnterAllFixInput.Id"));
    	 Thread.sleep(10000);
    	 enterByXpath(prop.getProperty("ToJob.FixInputValue1.Xpath"),ds.getAsString("FixInput1"));
    	 Thread.sleep(5000);
    	 enterByXpath(prop.getProperty("ToJob.FixInputValue2.Xpath"),ds.getAsString("FixInput2"));
    	 clickById(prop.getProperty("ToJob.FixSaveBtn.Id"));
    	 Thread.sleep(5000);
    	 clickById(prop.getProperty("ToJob.VioDetailsNext.id"));
    	 enterByXpath(prop.getProperty("ToJob.FixJobName.Xpath"),FixJobName);
    	 clickById(prop.getProperty("ToJob.ScheduleFixJob.id"));
    	 Thread.sleep(10000);
		return this;
     }
     public CompleJobDashboardPage PolicyCountValidation(Dataset ds) throws InterruptedException{
    	Thread.sleep(5000);
    	clickByXpath(prop.getProperty("ToJob.VioDetailsSelectAll.Xpath"));
    	Thread.sleep(5000);
    	verifyTextByXpath(prop.getProperty("ToJob.TotalCountVioDetails.Xpath"), ds.getAsString("SelCount"));
		return this;
    	 
     }
     
     public CompleJobDashboardPage JobEditFunctionality(Dataset ds) throws InterruptedException{
    	Thread.sleep(3000);
    	String rowEle="(//table[@class='row-table']/tbody/tr)";
 		List<WebElement> row=driver.findElements(By.xpath(rowEle));
 		int rowsize= row.size();
 		System.out.println("Job dashboard Row Size is :" + rowsize);
 		String colEle="(//div[@class='cellHeaderWrapper'])";
    		List<WebElement> Col=driver.findElements(By.xpath(colEle));
    		int colsize= Col.size();
    		System.out.println("Job dashboard Column Size is :"+ colsize);
    		for(int i=1;i<=rowsize;i++){
//    			System.out.println(driver.findElement(By.xpath(rowEle+"["+i+"]")).getText());
    			WebElement LastRunStatusValue = driver.findElement(By.xpath(("(//table[@class='row-table']/tbody/tr)["+i+"]/td[5]/div")));
//    			System.out.println("Status is: "+LastRunStatusValue.getText());
    			if (LastRunStatusValue.getText().contains("Failure")){
    				WebElement JobTypeValue = driver.findElement(By.xpath(("(//table[@class='row-table']/tbody/tr)["+i+"]/td[3]/div")));
//    				System.out.println("Job Type is: "+ JobTypeValue.getText());
    				if(JobTypeValue.getText().contains("Compliance")){
    					WebElement JobNameValue = driver.findElement(By.xpath(("(//table[@class='row-table']/tbody/tr)["+i+"]/td[2]/div/a")));
    				    System.out.println("Job Name is :"+ JobNameValue.getText());
    				    WebElement JobCheckBox = driver.findElement(By.xpath(("(//table[@class='row-table']/tbody/tr)["+i+"]/td[1]/div")));
    				    JobCheckBox.click();
    				    break;
    				}
    				else {
    					System.out.println("Job Type is: "+ JobTypeValue.getText());;
    				}
    			}
    			else{
    				System.out.println("Status is: "+LastRunStatusValue.getText());
    			}
    		}
    		Thread.sleep(3000);
    		if (driver.findElement(By.xpath(prop.getProperty("ToJob.EditJobFunction.Xpath"))).getAttribute("aria-disabled").equals("false")){
    			Assert.fail("FAILED: Edit Job link showing enabled");
    		}
    		else {
    			System.out.println("Edit Job link button is disabled and working fine");
    		}
		 return this;
     }
     
     public CompleJobDashboardPage locationColumnValidation(Dataset ds) throws InterruptedException{
    	String location =driver.findElement(By.xpath(prop.getProperty("ToJob.LocationColumn.Xpath"))).getText();
    	System.out.println(location);
    	 try
    	    {
    	        location=location.replaceAll("\\s+","");
    		    Double.parseDouble(location);
    	        System.out.println("Device location Column Validated with pure Integer Value");
    	    } catch (NumberFormatException ex)
    	    {
    	        Assert.fail("FAILED: Check the Configuration/Data loading issue is present");
    	    }
		return this;
    	 
     }
     
     public CompleJobDashboardPage SelectedJobDelete(String JobName) throws Exception{
    	 ModuleJobstable("User Jobs","Compliance Jobs",JobName);
    	 Thread.sleep(3000);
    	 WebElement JobCheckBox = driver.findElement(By.xpath(("(//table[@class='row-table']/tbody/tr)[1]/td[1]/div")));
		 JobCheckBox.click();
		 Thread.sleep(3000);
		 clickByXpath(prop.getProperty("ToJob.SearchSelectDeleteJob.Xpath"));
		 Thread.sleep(3000);
		 final Actions builder = new Actions(driver);
		 builder.sendKeys(Keys.ENTER).perform();
    	 return this;
     }
//     public CompleJobDashboardPage FixJobRunAll(Dataset ds) throws InterruptedException{
//    	 WebDriverWait wait = new WebDriverWait(driver, 20);
//    	 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(prop.getProperty("ToJob.VioDetailsSelectAll.Xpath")))));
//         clickByXpath(prop.getProperty("ToJob.VioDetailsSelectAll.Xpath"));
//    	 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id(prop.getProperty("")))))
//    	 return this;
//     }
     
     public CompleJobDashboardPage CopyRunStartPopOverValidation(String devIp,String Status) throws Exception{
    	Thread.sleep(7000);
//    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	WebElement CopyRunIbutton= driver.findElement(By.xpath(prop.getProperty("ToJob.CopyRunStartPopOver.Xpath")));
    	Actions action = new Actions(driver);
    	action.moveToElement(CopyRunIbutton).click().build().perform();
//    	clickByXpath(prop.getProperty("ToJob.CopyRunStartPopOver.Xpath"));
    	Thread.sleep(2000);
      for(int i=0;i<=10;i++){
//    	  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(prop.getProperty("ToJob.CopyRunStartPopTable.Xpath")))));
    	  if(driver.findElements(By.xpath(prop.getProperty("ToJob.CopyRunStartPopTable.Xpath"))).size()>0){
    		  System.out.println("POP over is now live");
    		  break;
    	  }
    	  else {
    		  System.out.println("Loop--> "+i);
    		  clickById("wcsuishell_node_id_commonPageToolbarItem_refresh");
    		  Thread.sleep(3000);
    		  clickByXpath(prop.getProperty("ToJob.CopyRunStartPopOver.Xpath"));
    	  }
    	  
      }
//    	enterByXpath(prop.getProperty("ToJob.CopyRunStartPopIpColmn.Xpath"), devIp);
//    if((driver.findElements(By.xpath("ToJob.CopyRunStartPopIpColmn1.Xpath")).size()>0) || 
//    		driver.findElements(By.xpath("ToJob.CopyRunStartPopIpColmn.Xpath")).size()>0){
//    	try {
//    		clickByXpath(prop.getProperty("ToJob.CopyRunStartPopIpColmn1.Xpath"));
//    		Thread.sleep(2000);
//			driver.findElement(By.xpath(prop.getProperty("ToJob.CopyRunStartPopIpColmn1.Xpath"))).sendKeys(devIp);
//			System.out.println("Ip entered using Xpath Alt attribute Successfully");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//			clickByXpath(prop.getProperty("ToJob.CopyRunStartPopIpColmn.Xpath"));
//    		Thread.sleep(2000);
//			driver.findElement(By.xpath(prop.getProperty("ToJob.CopyRunStartPopIpColmn.Xpath"))).sendKeys(devIp);
//			System.out.println("Ip entered using Xpath table Attribute Successfully");
//		}
    	Thread.sleep(3000);
    	if(driver.findElement(By.xpath(prop.getProperty("ToJob.CopyRunStartPopTable.Xpath"))).getText().contains(Status)){
    		System.out.println("Copy Running to Start up Message is validated successfully..");
    	}
    	else {
    		Assert.fail("FAILED:Copy Running to Start up Message is not validated successfully..");
    	}
    	Thread.sleep(3000);
//     }
		return this;
     }
}
package com.cisco.ui.test.compliance.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.tools.ant.taskdefs.WaitFor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentTest;
import com.thoughtworks.selenium.webdriven.commands.KeyEvent;
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.test.tea.log.TestLogger;
import com.cisco.ui.test.compliance.wrappers.utils.OpentapsWrappers;
import com.cisco.ui.test.compliance.wrappers.utils.PageNavigation;

public class ComplePolicyPage extends OpentapsWrappers {
	
	public ComplePolicyPage(RemoteWebDriver driver,ExtentTest test) {
		// TODO Auto-generated constructor stub
		this.driver= driver;
		this.test= test;
		
		PageNavigation.NavigateToPage("Configuration", "Policies");
//		clickByXpath(prop.getProperty("FromHome.ClickMenu.Xpath"));
//		WebElement config = driver.findElementByLinkText("Configuration");
//		Actions action= new Actions(driver);
//		action.moveToElement(config).perform();
//		clickByLink("Policies");
	}
	public ComplePolicyPage PolicyCreation(Dataset ds,String PolicyName) throws InterruptedException{
		new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("ToPolicy.Create.Xpath"))));
		clickByXpath(prop.getProperty("ToPolicy.Create.Xpath"));
		WebElement CreatePolicyDialog=driver.findElementById("createpolicyDialog_title");
		new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(CreatePolicyDialog));
		Thread.sleep(2000);
		enterById(prop.getProperty("ToPolicy.PolicyName.Id"), PolicyName);
		enterById(prop.getProperty("ToPolicy.PolicyDesc.Id"), ds.getAsString("PolDesc"));
		clickByXpath(prop.getProperty("ToPolicy.PolicySave.Xpath"));
		Thread.sleep(3000);
		return this;
	}
	
	public ComplePolicyPage CheckPolicyPresence(Dataset ds) throws InterruptedException{
		 clickByXpath(prop.getProperty("ToPolicy.PolicySearchAll.Xpath"));
	      enterByXpath(prop.getProperty("ToPolicy.PolicySearchAll.Xpath"), ds.getAsString("policyName"));
	      Thread.sleep(10000);
	      List<WebElement> PolicyCount=driver.findElementsByXPath(prop.getProperty("ToPolicy.PolicyCount.Xpath"));
	    if (PolicyCount.size()>0)
	    	TestLogger.info("Added policy is present");
	    else {
	         Assert.fail("FAILED: Added policy is not present");
	    }
		return this;
	}
	
	public ComplePolicyPage PolicyRuleCreate(String dataset,int[] platforms) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
		   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		   clickById(prop.getProperty("ToPolicy.RuleCreate.Id"));
		   enterById(prop.getProperty("ToPolicy.RuleTitle.Id"), HashValue(dataset, "RuleTitle"));
//		   enterById(prop.getProperty("ToPolicy.RuleDesc.Id"),HashValue(dataset, "RuleDes"));
//		   enterById(prop.getProperty("ToPolicy.RuleImpact.Id"),HashValue(dataset, "RuleImpa"));
//		   enterById(prop.getProperty("ToPolicy.RuleFix.id"), HashValue(dataset, "RuleFix"));
		   clickById(prop.getProperty("ToPolicy.RuleNext.Id"));
		   PlatformSelection(platforms);
		   clickById(prop.getProperty("ToPolicy.PlatformNext.Id"));
//		   RuleInputSelectExe(HashValue(dataset,"RinputReq"),HashValue(dataset, "RInput-Name"),HashValue(dataset, "Scope-Type"),HashValue(dataset, "Data-Type"),HashValue(dataset,"RinputReqCheck"));
//		   RuleInputSelectExe(HashValue(dataset,"RinputReq"),HashValue(dataset, "RInput-Name1"),HashValue(dataset, "Scope-Type1"),HashValue(dataset, "Data-Type1"),HashValue(dataset,"RinputReqCheck1"));
		   clickById(prop.getProperty("ToPolicy.RuleInputNext.Id"));
		   System.out.println(HashValue(dataset, "CondScopeValue"));
		   AddCondtion(HashValue(dataset, "CondScopeValue"),null,"yes",HashValue(dataset,"BlockStart"), null, HashValue(dataset, "Operator"),HashValue(dataset,"OpValue"));
		   AddAction(null, HashValue(dataset, "SelcetActMat"),HashValue(dataset, "SelcetActDMat"),HashValue(dataset, "Severity"),HashValue(dataset, "VioMsgtype"),HashValue(dataset, "VioMessage"),HashValue(dataset, "FixCliValue"));
		  return this;
	}
	
	public ComplePolicyPage SnmpFixSuccess(String dataset,int[] platforms) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
		   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		   clickById(prop.getProperty("ToPolicy.RuleCreate.Id"));
		   enterById(prop.getProperty("ToPolicy.RuleTitle.Id"), HashValue(dataset, "RuleTitle"));
		   clickById(prop.getProperty("ToPolicy.RuleNext.Id"));
		   PlatformSelection(platforms);
		   clickById(prop.getProperty("ToPolicy.PlatformNext.Id"));
//		   RuleInputSelectExe(HashValue(dataset,"RinputReqYesNull"),HashValue(dataset, "RInput-Name"),HashValue(dataset, "Scope-Type"),HashValue(dataset, "Data-Type"),HashValue(dataset,"RinputReqCheckYesNull"));
//		   RuleInputSelectExe(HashValue(dataset,"RinputReqYesNull"),HashValue(dataset, "RInput-Name1"),HashValue(dataset, "Scope-Type1"),HashValue(dataset, "Data-Type1"),HashValue(dataset,"RinputReqCheckYesNull1"));
		   clickById(prop.getProperty("ToPolicy.RuleInputNext.Id"));
		   AddCondtion(HashValue(dataset, "CondScopeValue"),null,null,null, null, HashValue(dataset, "Operator"),HashValue(dataset,"OpValue"));
		   AddAction(null, HashValue(dataset, "SelcetActMat"),HashValue(dataset, "SelcetActDMat"),HashValue(dataset, "Severity"),HashValue(dataset, "VioMsgtype"),HashValue(dataset, "VioMessage"),HashValue(dataset, "FixCliValue"));
		   AddCondtion(HashValue(dataset, "CondScopeValue"),null,null,null, null, HashValue(dataset, "Operator1"),HashValue(dataset,"OpValue1"));
		   AddAction(null, HashValue(dataset, "SelcetActMat1"),HashValue(dataset, "SelcetActDMat1"),HashValue(dataset, "Severity1"),HashValue(dataset, "VioMsgtype1"),HashValue(dataset, "VioMessage1"),HashValue(dataset, "FixCliValue1"));
		   clickById(prop.getProperty("ToPolicy.PolicyFinish.Id"));
		  return this;
	}
		public ComplePolicyPage PlatformSelection(int[] platforms ) throws InterruptedException  {
			
		   List<WebElement> Devplatforms = driver.findElementsByXPath("//div[@class='dijitCheckBox']");
		   List<WebElement> PlatformName = driver.findElementsByXPath("//div[@class='dijitCheckBox']/ancestor::td/following-sibling::td[1]/div");
		   int dc =Devplatforms.size();
		   for (int platform : platforms){
			  if(platform<=dc-1){
			   Devplatforms.get(platform).click();
			   System.out.println(PlatformName.get(platform).getText());
			   Thread.sleep(3000);
			  }
			  else{
				  System.out.println("Plaform count:"+ platform + "is exceeded");
			  }
		   }
		   return this;
		
	}
       public ComplePolicyPage RuleInputSelectExe(String RuleInputTitle,String Scope,String DataType,String InReq) throws InterruptedException{
                 clickById(prop.getProperty("ToPolicy.RuleAdd.Id"));
                 Thread.sleep(2000);
                 enterById(prop.getProperty("ToPolicy.RInputTitle.Id"), RuleInputTitle);
                 enterById(prop.getProperty("ToPolicy.RInputId.Id"), "_"+RuleInputTitle);
                 clickById(prop.getProperty("ToPolicy.RuleInputScope.Id"));
                 String ScopeType = "//td[@class='dijitReset dijitMenuItemLabel' and text()='"+Scope+"']";
                 clickByXpath(ScopeType);
                 Thread.sleep(3000);
                 clickById(prop.getProperty("ToPolicy.RuleInputDataType.Id"));
                 String DType ="//td[@class='dijitReset dijitMenuItemLabel' and text()='"+DataType+"']";
                 clickByXpath(DType);
                 Thread.sleep(3000);
                 if(InReq != null)
                	 clickById(prop.getProperty("ToPolicy.InputReq.Id"));
                 Thread.sleep(3000);
                if (DataType.equalsIgnoreCase("String")){
                 clickById(prop.getProperty("ToPolicy.Str.Maxlength"));
                 enterById(prop.getProperty("ToPolicy.Str.Maxlength"), "10");
                 Thread.sleep(3000);
                }else if(DataType.equalsIgnoreCase("Integer")){
                	clickById(prop.getProperty("ToPolicy.MinInput"));
                	enterById(prop.getProperty("ToPolicy.MinInput"),"2");
                	Thread.sleep(3000);
                	clickById(prop.getProperty("ToPolicy.MaxInput"));
                	enterById(prop.getProperty("ToPolicy.MaxInput"), "10");
                }
                Thread.sleep(3000);
                 clickByXpath(prop.getProperty("ToPolicy.RuleInputOk.Xpath"));
		return this;
       }
       
       public ComplePolicyPage AddCondtion(String CondScopeValue,String DeviceProp, String ParserBlock,String blkStart, String blkEnd, String Operator, String value) throws InterruptedException{ 
    	   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	   clickById(prop.getProperty("ToPolicy.ConActAdd.Id"));
    	   Thread.sleep(3000);
    	   dropdownXpath(prop.getProperty("ToPolicy.Conscopedet.Id"), CondScopeValue);
    	   if(CondScopeValue.equalsIgnoreCase("Configuration")|| CondScopeValue.equalsIgnoreCase("Device Command Outputs") || CondScopeValue.equalsIgnoreCase("Previously Matched Blocks")){
    		   if(ParserBlock != null){
    			   clickById(prop.getProperty("ToPolicy.ParserSelect.Id"));
    			   enterById(prop.getProperty("ToPolicy.BlockStart.Id"),blkStart);
    			   enterById(prop.getProperty("ToPolicy.BlockEnd.Id"), blkEnd);
    			   Thread.sleep(3000);
    		   }
    		   else{
    			   System.out.println("Block Parser opertion is not defined");
    		   }
    	   }
    	   else if(CondScopeValue.contains("Device Properties")){
    		   dropdownXpath(prop.getProperty("ToPolicy.DeviceProp.id"),DeviceProp);
    		   System.out.println("Block Parser opertion is not available");
    	   }
    	  dropdownXpath(prop.getProperty("ToPolicy.CondMatchCri.Operator.Id"),Operator);
    	  enterById(prop.getProperty("ToPolicy.EnterValue.Id"), value);
    	  Thread.sleep(2000);
		return this;
       }
   public ComplePolicyPage AddAction(String ActionMatch, String MSelAction,String DMSelAction, String MSeverity,String MViomsgtype, String MViomsg,String MFixcmd) throws InterruptedException{
    	 clickByXpath(prop.getProperty("ToPolicy.ActionDetails.Xpath"));
//    	 Select Match Action  : 
    	  if(ActionMatch != null){
    	  dropdownXpath(prop.getProperty("ToPolicy.SelectAction.Id"),MSelAction);
    	  if(MSelAction.equalsIgnoreCase("Raise a Violation") || MSelAction.equalsIgnoreCase("Raise a Violation and Continue")){
    		  dropdownXpath(prop.getProperty("ToPolicy.SelectSeverity.Id"), MSeverity);
    		  dropdownXpath(prop.getProperty("ToPolicy.SelectVioMessage.Id"), MViomsgtype);
    		  if(MViomsgtype=="User defined Violation Message"){
    			  enterById(prop.getProperty("ToPolicy.EnterVioMessage.Id"),MViomsg);
    			  enterById(prop.getProperty("ToPolicy.EnterFixCmd.Id"), MFixcmd);
    		  }
    		  else{
    			  System.out.println("user selected "+MViomsgtype+" , Hence No need enter Violation messsage and Fix CLI Command");
    		  }
    		  
    		  dropdownXPathIdDM(prop.getProperty("ToPolicy.DMSelectAction.Id"),DMSelAction);
    	  }else{
    		  System.out.println("user selected "+MSelAction+" , Hence No need enter anything Further");
    	  }
    	  
    	  }
//  Doesnt Select Match Action:
    	  else {
    	  Thread.sleep(3000);
          dropdownXpath(prop.getProperty("ToPolicy.SelectAction.Id"),MSelAction);
          Thread.sleep(3000);
          dropdownXPathIdDM(prop.getProperty("ToPolicy.DMSelectAction.Id"),DMSelAction);
    	  if(DMSelAction.equalsIgnoreCase("Raise a Violation") || DMSelAction.equalsIgnoreCase("Raise a Violation and Continue")){
    		  scrollintoViewId(prop.getProperty("ToPolicy.FixClILable.Id"));
    		  dropdownXpath(prop.getProperty("ToPolicy.DMSelectSeverity.Id"),MSeverity);
    		  dropdownXpath(prop.getProperty("ToPolicy.DMSelectVioMessage.Id"), MViomsgtype);
    		  if(MViomsgtype.equalsIgnoreCase("User defined Violation Message")){
    			  enterById(prop.getProperty("ToPolicy.DMEnterVioMessage.Id"),MViomsg);
    			  enterById(prop.getProperty("ToPolicy.DMEnterFixCmd.Id"), MFixcmd);
    		  }
    		  else{
    			  System.out.println("user selected "+MViomsgtype+" , Hence No need enter Violation messsage and Fix CLI Command");
    		  }
    	  }else {
    		  System.out.println("user selected "+MSelAction+" , Hence No need enter anything Further");
    	  }    	   
       }
    	  clickByXpath(prop.getProperty("ToPolicy.ConAndActionOk.Xpath"));
		return this;
       
    }
   
   public ComplePolicyPage PolicyImport(Dataset ds) throws InterruptedException, Exception{
	     
	      Screen screen = new Screen();
		     
	      Pattern img1= new Pattern("C:\\Compliance\\InputImage\\NewOpen.PNG");
//	      Pattern img3= new Pattern("C:\\Compliance\\InputImage\\fileOpen.PNG");
	      Pattern img2= new Pattern("C:\\Compliance\\InputImage\\OpenNew.PNG");
	      
	      Thread.sleep(50000);
	      clickByXpath(prop.getProperty("ToPolicy.PolicySearchAll.Xpath"));
	      enterByXpath(prop.getProperty("ToPolicy.PolicySearchAll.Xpath"), ds.getAsString("policyName"));
	      Thread.sleep(10000);
	      List<WebElement> PolicyCount=driver.findElementsByXPath(prop.getProperty("ToPolicy.PolicyCount.Xpath"));
	    if (PolicyCount.size()>0){
	    	TestLogger.info("Policy is already added, No need to Import");
	    
	    }else {
		      clickByXpath(prop.getProperty("ToPolicy.PolicyImport.Xpath"));
		      Thread.sleep(3000);
		      clickByXpath(prop.getProperty("ToPolicy.ChoosePolicy.Xpath"));
	      
		    try {  
	          screen.wait(img1,20);
	          screen.setAutoWaitTimeout(20);
	          screen.click(img1);
	          screen.setAutoWaitTimeout(20);
		      screen.type(img1, "C:\\Compliance\\InputFile\\SNMP-Failure.xml");
		      screen.wait(img1,25);
		      screen.click(img2);
		      TestLogger.debug("Policy Imported using Sikuli tool");
		    }catch (Exception FindFailed){
		         Runtime.getRuntime().exec("C:\\Compliance\\InputFile\\policyuploadA.exe");
		         TestLogger.debug("Policy Imported using AUTOIT tool");
		    }	    
		      Thread.sleep(3000);
		      clickByXpath(prop.getProperty("ToPolicy.Import.Xpath"));
		      Thread.sleep(5000);
		      List<WebElement> UploadSuccess=driver.findElements(By.xpath("//span[contains(text(),'Upload Complete')]"));
		      List<WebElement> UploadFailed=driver.findElements(By.xpath("//span[contains(text(),'Upload failed')]"));
		      
		      if (UploadSuccess.size()>0){
		    	  TestLogger.debug("Upload Is success, Now You can use the Policy in Compliance Profile");
		      }
		      else if(UploadFailed.size()>0){
		    	  TestLogger.debug(" Policy XML might having some issue, Check the policy !!! ");
		    	  Assert.fail("FAILED Due to Policy XML Having some issue");
		      }
		      
		     clickByXpath(prop.getProperty("ToPolicy.Close.Xpath"));
		    //Close:div[@id='importPolicyDialog']/div[3]/div/child::span[2]/descendant::span[5]
		
			    } 
   return this;
   }

   public ComplePolicyPage PolicyImportRobot(Dataset ds) throws InterruptedException, Exception{
	   Thread.sleep(3000);
	   clickByXpath(prop.getProperty("ToPolicy.PolicySearchAll.Xpath"));
	      enterByXpath(prop.getProperty("ToPolicy.PolicySearchAll.Xpath"), ds.getAsString("policyName"));
	      Thread.sleep(10000);
	      List<WebElement> PolicyCount=driver.findElementsByXPath(prop.getProperty("ToPolicy.PolicyCount.Xpath"));
	    if (PolicyCount.size()>0){
	    	TestLogger.info("Policy is already added, No need to Import");
	    
	    }else {
		      clickByXpath(prop.getProperty("ToPolicy.PolicyImport.Xpath"));
		      Thread.sleep(3000);
		      clickByXpath(prop.getProperty("ToPolicy.ChoosePolicy.Xpath"));
		      Robot robot = new Robot();
		      robot.setAutoDelay(3000);
		      
		      StringSelection stringselect = new StringSelection("C:\\Compliance\\InputFile\\SNMP-Failure.xml");
		      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselect, null);
		      TestLogger.info("Policy location successfully Copied");
		      
		      
		      robot.setAutoDelay(5000);
		      
		      robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
		      robot.keyPress(java.awt.event.KeyEvent.VK_V);
		      TestLogger.info("Policy location successfully Pasted");
		      
		      robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
		      robot.keyRelease(java.awt.event.KeyEvent.VK_V);
		      
		      robot.setAutoDelay(5000);
		      
		      robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		      TestLogger.info("Policy location successfully entered");
		      robot.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
		      
		      robot.setAutoDelay(5000);
		      
		      clickByXpath(prop.getProperty("ToPolicy.Import.Xpath"));
		      Thread.sleep(5000);
		      List<WebElement> UploadSuccess=driver.findElements(By.xpath("//span[contains(text(),'Upload Complete')]"));
		      List<WebElement> UploadFailed=driver.findElements(By.xpath("//span[contains(text(),'Upload failed')]"));
		      
		      if (UploadSuccess.size()>0){
		    	  TestLogger.debug("Upload Is success, Now You can use the Policy in Compliance Profile");
		      }
		      else if(UploadFailed.size()>0){
		    	  TestLogger.debug(" Policy XML might having some issue, Check the policy !!! ");
		    	  Assert.fail("FAILED Due to Policy XML Having some issue");
		      }
		      else {
		    	  Assert.fail("FAILED, Policy is not imported Successfully");
		      }
		    	  
		      
		     clickByXpath(prop.getProperty("ToPolicy.Close.Xpath"));
	    }
		      	   
	return this;
	   
   }

   public ComplePolicyPage InvalidInputValidation(Dataset ds,String PolicyName,int[] platforms) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
	   PolicyCreation(ds,PolicyName);
	   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	   clickById(prop.getProperty("ToPolicy.RuleCreate.Id"));
	   enterById(prop.getProperty("ToPolicy.RuleTitle.Id"), ds.getAsString("RuleTitle"));
	   clickById(prop.getProperty("ToPolicy.RuleNext.Id"));
	   PlatformSelection(platforms);
	   clickById(prop.getProperty("ToPolicy.PlatformNext.Id"));
	   RuleInputSelectExe(ds.getAsString("RuleInputTitle"),ds.getAsString("Scope"),ds.getAsString("DataType"),ds.getAsString("InReq"));
	   Thread.sleep(3000);
	   RuleInputSelectExe(ds.getAsString("RuleInputTitle1"),ds.getAsString("Scope1"),ds.getAsString("DataType1"),ds.getAsString("InReq1"));
	   clickById(prop.getProperty("ToPolicy.RuleInputNext.Id"));
	   AddCondtion(ds.getAsString("CondScopeValue"),null,null,null, null, ds.getAsString("Operator"),ds.getAsString("OpValue"));
	   AddAction(null, ds.getAsString("SelcetActMat"),ds.getAsString("SelcetActDMat"),ds.getAsString("Severity"),ds.getAsString("VioMsgtype"),ds.getAsString("VioMessage"),null);
	   clickById(prop.getProperty("ToPolicy.PolicyFinish.Id"));
	  return this;
	   
   }
   
   public ComplePolicyPage PolicyImportCommon(Dataset ds) throws InterruptedException, Exception{
	      Thread.sleep(5000);
	      clickByXpath(prop.getProperty("ToPolicy.PolicySearchAll.Xpath"));
	      enterByXpath(prop.getProperty("ToPolicy.PolicySearchAll.Xpath"), ds.getAsString("policyName"));
	      Thread.sleep(10000);
	      List<WebElement> PolicyCount=driver.findElementsByXPath(prop.getProperty("ToPolicy.PolicyCount.Xpath"));
	    if (PolicyCount.size()>0){
	    	TestLogger.info("Policy is already added, No need to Import");
//	    	driver.findElementByXPath(prop.getProperty("ToPolicy.PolicyCount.Xpath")).click();
	    
	    }else {
		      clickByXpath(prop.getProperty("ToPolicy.PolicyImport.Xpath"));
		      Thread.sleep(3000);
		      clickByXpath(prop.getProperty("ToPolicy.ChoosePolicy.Xpath"));
	          Thread.sleep(5000);
		      Runtime.getRuntime().exec(ds.getAsString("exeLocation"));
		      TestLogger.debug("Policy Imported using AUTOIT tool");    
		      Thread.sleep(3000);
		      clickByXpath(prop.getProperty("ToPolicy.Import.Xpath"));
		      Thread.sleep(5000);
		      List<WebElement> UploadSuccess=driver.findElements(By.xpath("//span[contains(text(),'Upload Complete')]"));
		      List<WebElement> UploadFailed=driver.findElements(By.xpath("//span[contains(text(),'Upload failed')]"));
		      
		      if (UploadSuccess.size()>0){
		    	  TestLogger.debug("Upload Is success, Now You can use the Policy in Compliance Profile");
		      }
		      else if(UploadFailed.size()>0){
		    	  TestLogger.debug(" Policy XML might having some issue, Check the policy !!! ");
		    	  Assert.fail("FAILED Due to Policy XML Having some issue");
		      }
		      
		     clickByXpath(prop.getProperty("ToPolicy.Close.Xpath"));
		    //Close:div[@id='importPolicyDialog']/div[3]/div/child::span[2]/descendant::span[5]
	    }
    return this;
  }
   
   public static void policyFilterCheck(String PolicyName) throws InterruptedException{
	      Thread.sleep(5000);
	      driver.findElementByXPath(prop.getProperty("ToPolicy.PolicySearchAll.Xpath")).click();
	      driver.findElementByXPath(prop.getProperty("ToPolicy.PolicySearchAll.Xpath")).sendKeys(PolicyName);
	      Thread.sleep(10000);
	      List<WebElement> PolicyCount=driver.findElementsByXPath(prop.getProperty("ToPolicy.PolicyCount.Xpath"));
	    if (PolicyCount.size()>0){
	    	TestLogger.info("Policy is present, Now you can Start your testing");
        }
	    else {
	    	TestLogger.info("Added/Imported Policy is not present, Please check");
	    }
   }
   public static void policySelectionAfterFilter(String PolicyName){
	   String TitleName=driver.findElementByXPath(prop.getProperty("ToPolicy.PolicyTitleinRuleTable.Xpath")).getText();
	   System.out.println(TitleName);
	  if(!TitleName.contains(PolicyName)){
		  driver.findElementByXPath(prop.getProperty("ToPolicy.PolicyCount.Xpath")).click();
		  System.out.println("Policy is selected properly");
	  }
	  else{
		  System.out.println("Policy is already selected");
	  }
   }
   public ComplePolicyPage ruleTableQfilterValidation(Dataset ds) throws InterruptedException{
	   WebDriverWait wait=new WebDriverWait(driver, 20);
	   policySelectionAfterFilter(ds.getAsString("policyName"));
	   String titleElement="//div[@title='MultiRulePolicy']";
	   System.out.println(driver.findElementByXPath(titleElement).getText());
	   List<WebElement> titleSize=driver.findElementsByXPath(titleElement);
	   System.out.println("Size is: " + titleSize.size());
	   if (titleSize.size()>0){
		  clickByXpath(prop.getProperty("ToPolicy.FilterAlllink.Xpath"));
		  WebElement qfilter= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("ToPolicy.Qfilter.Xpath"))));
		  qfilter.click();
		  WebElement rtitle= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("ToPolicy.Rtitle.Xpath"))));
		  rtitle.click();
		  rtitle.sendKeys("One");
		  Thread.sleep(5000);
		  String titleColmn="//div[@class='table-container']/descendant::td[2]/div";
		  String descriColmn="//div[@class='table-container']/descendant::td[3]/div";
		  List<WebElement> RowSize=driver.findElementsByXPath(titleColmn);
		  System.out.println("table size is :"+ RowSize.size());
		  if(RowSize.size()>=1){
			 verifyTextByXpath(titleColmn, "One");
			 verifyTextByXpath(descriColmn,"Red");
			 System.out.println("Quick filter Positive Scenario Verified Successfully");
			 rtitle.click();
			 rtitle.clear();
			 rtitle.sendKeys("asdasdasd");
			 Thread.sleep(5000);
			 List<WebElement> RowSize1=driver.findElementsByXPath(titleColmn);
			 System.out.println("table size is :"+ RowSize1.size());
			 if(RowSize1.size()<=1){
				 System.out.println("Quick filter Negative Scenario is Verified Successfully");
				 clickByXpath(prop.getProperty("ToPolicy.FilterToggleCApplied.Xpath"));
			 }
			 else{
				  Assert.fail("FAILED:Quick filter Negative Scenario is not Verified Successfully");
			  }
		  }
		  else{
			  Assert.fail("FAILED:Quick filter Positive Scenario is not Verified Successfully ");
		  }
	   }
		  else{
			  Assert.fail("FAILED: Quick filter is not working");
		  } 
	return this;
	   
   }
   public ComplePolicyPage ruleTableAfilterValidation(Dataset ds) throws InterruptedException{
	   WebDriverWait wait=new WebDriverWait(driver, 20);
	   String titleElement="//div[@title='MultiRulePolicy']";
	   System.out.println(driver.findElementByXPath(titleElement).getText());
	   List<WebElement> titleSize=driver.findElementsByXPath(titleElement);
	   System.out.println("Size is: " + titleSize.size());
	   if (titleSize.size()>0){
			  clickByXpath(prop.getProperty("ToPolicy.FilterAlllink.Xpath"));
			  WebElement afilter= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("ToPolicy.Afilter.Xpath"))));
			  afilter.click();
			  clickByXpath("(//span[text()='Select Filter'])[1]");
			  WebElement TitleFilter= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("Topolicy.TitleFilter.Xpath"))));
			  TitleFilter.click();
			  Thread.sleep(3000);
			  enterByXpath(prop.getProperty("ToPolicy.TitleValue.Xpath"),"Four");
			  clickByXpath(prop.getProperty("ToPolicy.AddFiltercolumn.Xpath"));
			  Thread.sleep(3000);
			  clickByXpath("(//span[text()='Select Filter'])[1]");
			  WebElement DescriptionFilter= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("Topolicy.DescriptionFilter.Xpath"))));
			  DescriptionFilter.click();
			  Thread.sleep(3000);
			  enterByXpath(prop.getProperty("ToPolicy.DescriptionValue.Xpath"),"Black");
			  Thread.sleep(3000);
			  clickByXpath(prop.getProperty("ToPolicy.adfilterOK.Xpath"));
			  Thread.sleep(3000);
			  String titleColmn="//div[@class='table-container']/descendant::td[2]/div";
			  String descriColmn="//div[@class='table-container']/descendant::td[3]/div";
			  List<WebElement> RowSize=driver.findElementsByXPath(titleColmn);
			  System.out.println("coulmn size is : " +RowSize.size());
			  if(RowSize.size()>=1){
				  verifyTextByXpath(titleColmn, "Four");
				  verifyTextByXpath(descriColmn, "Black");
				  System.out.println("Advance filter Positive Scenario is Verified Successfully");
				  clickByXpath(prop.getProperty("ToPolicy.FilterToggleEApplied.Xpath"));
				  driver.findElementByXPath(prop.getProperty("ToPolicy.DescriptionValue.Xpath")).click();
				  driver.findElementByXPath(prop.getProperty("ToPolicy.DescriptionValue.Xpath")).clear();
				  enterByXpath(prop.getProperty("ToPolicy.DescriptionValue.Xpath"),"Red");
				  clickByXpath(prop.getProperty("ToPolicy.adfilterOK.Xpath"));
				  Thread.sleep(5000);
				  List<WebElement> RowSize1=driver.findElementsByXPath(titleColmn);
				  System.out.println("coulmn size is : " +RowSize1.size());
				  if(RowSize1.size()<=1){
					     System.out.println("Advanced filter Negative Scenario is Verified Successfully");
				  }
				  else{
					  Assert.fail("FAILED:Advanced filter Negative Scenario is not Verified Successfully");
				  }
			  }
			  else{
				  Assert.fail("FAILED:Advance filter Positive Scenario is not Verified Successfully ");
			  } 
			  
	   }
		  else{
			  Assert.fail("FAILED: Advanced filter is not working");
		  } 
		  return this;
}
 
   public ComplePolicyPage PolicyDeviceSettingValidation(Dataset ds,String PolicyName,int[] platforms) throws InterruptedException{
	   PolicyCreation(ds,PolicyName);
	   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	   clickById(prop.getProperty("ToPolicy.RuleCreate.Id"));
	   enterById(prop.getProperty("ToPolicy.RuleTitle.Id"), ds.getAsString("RuleTitle"));
	   clickById(prop.getProperty("ToPolicy.RuleNext.Id"));
	   PlatformSelection(platforms);
	   clickById(prop.getProperty("ToPolicy.PlatformNext.Id"));
	   clickById(prop.getProperty("ToPolicy.RuleInputNext.Id"));
	   AddCondtion(ds.getAsString("CondScopeValue"),ds.getAsString("devprop"),null,null, null, ds.getAsString("Operator"),ds.getAsString("OpValue"));
	   AddAction(null, ds.getAsString("SelcetActMat"),ds.getAsString("SelcetActDMat"),ds.getAsString("Severity"),ds.getAsString("VioMsgtype"),null,null);
	   clickById(prop.getProperty("ToPolicy.PolicyFinish.Id"));
	   Thread.sleep(5000);
	   clickByXpath(prop.getProperty("ToPsirt.PageRefresh.Xpath"));
	   Thread.sleep(5000);
	   policyFilterCheck(PolicyName);
	   policySelectionAfterFilter(PolicyName);
       clickById(prop.getProperty("ToPolicy.RuleEdit.Id"));
       Thread.sleep(2000);
       clickById(prop.getProperty("ToPolicy.RuleNext.Id"));
       Thread.sleep(2000);
       clickById(prop.getProperty("ToPolicy.PlatformNext.Id"));
       Thread.sleep(2000);
       clickById(prop.getProperty("ToPolicy.RuleInputNext.Id"));
       Thread.sleep(3000);
       AddCondtion(ds.getAsString("CondScopeValue1"),null,null,null, null, ds.getAsString("Operator"),ds.getAsString("OpValue"));
//     AddAction(null, ds.getAsString("SelcetActMat"),ds.getAsString("SelcetActDMat"),ds.getAsString("Severity"),ds.getAsString("VioMsgtype"),null,null);
       final Actions builder = new Actions(driver);
	   builder.sendKeys(Keys.ESCAPE).perform();
	   Thread.sleep(3000);
	   clickById(prop.getProperty("ToPolicy.CondiEdit.Id"));
	   Thread.sleep(3000);
	   WebElement devicePropValue=driver.findElementByXPath(prop.getProperty("ToPolicy.DevicePropValue.Xpath"));
	   new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(devicePropValue));
	   System.out.println(devicePropValue.getText());
	   if (devicePropValue.getText().equalsIgnoreCase("IP Address")){
		   System.out.println("Device properties selected value is retained in policy and Working fine");
		   builder.sendKeys(Keys.ESCAPE).perform();
		   Thread.sleep(3000);
		   clickById(prop.getProperty("ToPolicy.PolicyCancel.Xpath"));
	   }
	   else{
		   Assert.fail("FAILED:Device properties selected value is not retailed in policy ");
	   }
	return this;
	   
   }
   
}
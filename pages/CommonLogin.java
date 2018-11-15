package com.cisco.ui.test.compliance.pages;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STSourceType;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.ui.test.compliance.wrappers.utils.OpentapsWrappers;
import com.cisco.ui.test.compliance.wrappers.utils.PageNavigation;

public class CommonLogin extends OpentapsWrappers{
	
	public CommonLogin(RemoteWebDriver driver,Dataset ds){
		this.driver = driver;
		this.ds = ds;
	}
		
    public CommonLogin GmailLogin(String Uname, String Passwd) throws InterruptedException{
    	clickByXpath(prop.getProperty("ToCommon.Gmail.SignIn.Xpath"));
    	Thread.sleep(3000);
    	String sText = driver.findElementById(prop.getProperty("ToCommon.Gmail.HeadingText.Id")).getText();

        if (sText.equalsIgnoreCase("Choose an account")){
        clickByXpath(prop.getProperty("ToCommon.Gmail.UserAnthr.Xpath"));
        }
        else {
        	System.out.println("Sign In Page is visible, Hence no need to select Use another account..");

        }
        Thread.sleep(3000);
        driver.findElement(By.id(prop.getProperty("ToCommon.Gmail.UserID.Id"))).clear();
        enterById(prop.getProperty("ToCommon.Gmail.UserID.Id"),Uname);
		clickById(prop.getProperty("ToCommon.Gmail.userNext.id"));
		Thread.sleep(3000);
		driver.findElement(By.name(prop.getProperty("ToCommon.Gmail.Passwd.Name"))).clear();
		enterByName(prop.getProperty("ToCommon.Gmail.Passwd.Name"), Passwd);
		clickById(prop.getProperty("ToCommon.Gmail.PasswdNext.Id"));
    	return this;
    
	}
    
    public CommonLogin GmailDataValidation(Dataset ds) throws Exception{
       Thread.sleep(10000);
       String MailSubject =null;
       String inbxlbl=driver.findElement(By.xpath(prop.getProperty("ToCommon.Gmail.Inboxlabel.Xpath"))).getAttribute("aria-label");
       if(inbxlbl.contains("unread")){
    	  String[] Inbxsplt=inbxlbl.split(" ");
    	  int mailCount=Integer.parseInt(Inbxsplt[1]);
    	  if (mailCount>=1){
    		 int Subsize= driver.findElements(By.xpath(prop.getProperty("ToCommon.Gmail.MailSubject.Xpath"))).size();
    		 System.out.println("Subject Size is: " +Subsize);
    	  for(int i=1;i<=Subsize;i++){
		    MailSubject=driver.findElement(By.xpath("(//span[@class='bqe'])["+i+"]")).getText();
		    System.out.println("Mail subject is "+MailSubject);
		    System.out.println("Desired subject is"+ds.getAsString("mailSubject"));
		    if(MailSubject.contains(ds.getAsString("mailSubject"))){
		    	driver.findElement(By.xpath("(//span[@class='bqe'])["+i+"]")).click();
		    	break;
		    }
		    else
		    	System.out.println("Desired subject is not there in the row");
    	  }
    		if(MailSubject.contains(ds.getAsString("mailSubject"))){
//    		 clickByXpath(prop.getProperty("ToCommon.Gmail.MailSubject.Xpath"));
    		 Thread.sleep(5000);
    		 String JobStatus=driver.findElement(By.xpath(prop.getProperty("ToCommon.Gmail.MailContent.Xpath"))).getText();
    		   if((JobStatus.contains("PAS Profiling Job")) || (JobStatus.contains("Compliance Audit Job"))||(JobStatus.contains("Compliance Fix Job"))){
    			  if((JobStatus.contains("Success")) ||(JobStatus.contains("Failure"))) {
    				 System.out.println("Job Mail content verified Successfully");
    				 Thread.sleep(2000);
    				 clickByXpath(prop.getProperty("ToCommon.Gmail.Inboxlabel.Xpath"));
    			 }
    			 else
    				 Assert.fail("FAILED: Job status is not verified Successfully");
    		 }
    		 else 
    			 Assert.fail("FAILED: Job Type not verified Successfully");
    	 }
    		else
    			Assert.fail("FAILED: Mail Subject is not verified Successfully");
    	  }
    	  else
    		  Assert.fail("FAILED: Inbox doesn't has atleast one unread mail");
       }
       else{
    	   Assert.fail("FAILED: Mail not yet received, please check.. ");
       }
    	
    	return this;
    }
    
    public CommonLogin MailNewFeatureValidation(Dataset ds,String JobName,String JobType,String LStatus,String ProfName,String Status) throws Exception{
    	Thread.sleep(10000);
        String inbxlbl=driver.findElement(By.xpath(prop.getProperty("ToCommon.Gmail.Inboxlabel.Xpath"))).getAttribute("aria-label");
        if(inbxlbl.contains("unread")){
      	  String[] Inbxsplt=inbxlbl.split(" ");
      	   int mailCount=Integer.parseInt(Inbxsplt[1]);
      	   if (mailCount>=1){
//      		   enterById(prop.getProperty("ToCommon.Gmail.MailSearchText.Xpath"),JobName);
//      		   Thread.sleep(2000);
//      		   clickById(prop.getProperty("ToCommon.Gmail.MailSearchBtn.Xpath"));
      		  clickByXpath("//button[@class='gb_xf']");
//      		  clickByXpath("//button[@aria-label='Advanced search options']");
      		  Thread.sleep(5000);
      		  int size = driver.findElementsByXPath("(//span[@class='w-Pv'])").size();
      		  clickByXpath("(//div[@class='w-Nw boo'][4])/descendant::input");
      		  Thread.sleep(3000);
      		  enterByXpath("(//div[@class='w-Nw boo'][4])/descendant::input", JobName);
      		  Thread.sleep(3000);
      		  clickByXpath("(//div[text()='Search'])[2]");
      		  Thread.sleep(5000);
//      	  int Subsize= driver.findElements(By.xpath(prop.getProperty("ToCommon.Gmail.MailSubject.Xpath"))).size();
//    		  System.out.println("Subject Size is : " +Subsize);
//    		  String MailSubject=driver.findElement(By.xpath(prop.getProperty("ToCommon.Gmail.MailSubject.Xpath"))).getText();
//    		  System.out.println("Mail Subject is : "+MailSubject);
//    		  if(MailSubject.contains(ds.getAsString("mailSubject"))){
//    		   clickByXpath(prop.getProperty("ToCommon.Gmail.MailSubject.Xpath"));
      		 MailSubjectValidation(ds.getAsString("mailSubject"), true,true);
    		 Thread.sleep(2000);
    		 String JobStatus=driver.findElement(By.xpath(prop.getProperty("ToCommon.Gmail.MailContent.Xpath"))).getText();
      		 System.out.println(JobStatus);
      		 boolean a= JobStatus.contains("Job Type        :"+JobType);
      		 boolean b= JobStatus.contains("Job Name        :"+JobName);
      		 boolean c= JobStatus.contains("Status          :"+Status);
      		 boolean d= JobStatus.contains("LastRunStatus   :"+LStatus);
      		 boolean e= JobStatus.contains("PI HostName     :"+ds.getAsString("SerHostName"));
      		 boolean f= JobStatus.contains("Policy Profile Name     :"+ProfName);
      		 boolean g= JobStatus.contains("Total Device Count");
      		 boolean h= JobStatus.contains("Audited Device Count");
      		 boolean i= JobStatus.contains("Non-Audited Device Count");
      		 boolean j= JobStatus.contains("PI host IP      :"+ds.getAsString("ServerIp"));
      		 boolean k= JobStatus.contains("pageId=compliance_audit_policy_profile_output_page");
      		 boolean l= JobStatus.contains("com_cisco_ifm_ui_web_page_job_dashboard_detail_view");
      		 Thread.sleep(5000);
      		 if(JobType.contains("Compliance Audit Job")){
      			  if(a && b && c && d && e && f && g && h && i && j && k && l) {
      				 System.out.println("Job Mail content verified Successfully");
      				 Thread.sleep(2000);
      				 clickByXpath(prop.getProperty("ToCommon.Gmail.Inboxlabel.Xpath"));
      			  }
      			  else
      				 Assert.fail("FAILED: Audit Job mail content is not verified Successfully,Results are--> "+a+" "+b+" "+c+" "+d+" "+e+" "+f+" "+g+" "+h+" "+i+" "+j+" "+k+" "+l);
      		 }
      		 
      		 else if(JobType.contains("Compliance Fix Job")){
      			 if(a && b && c && d && e && j && l) {
      				 System.out.println("Job Mail content verified Successfully");
      				 Thread.sleep(2000);
      				 clickByXpath(prop.getProperty("ToCommon.Gmail.Inboxlabel.Xpath"));
      			  }
      			  else
      				 Assert.fail("FAILED: Fix Job mail content is not verified Successfully,Results are--> "+a+" "+b+" "+c+" "+d+" "+e+" "+j+" "+l); 
      		 }
      		 
      		 else if(JobType.contains("PAS Profiling Job")){
      			 if(a && c && d && e) {
      				 System.out.println("Job Mail content verified Successfully");
      				 Thread.sleep(2000);
      				 clickByXpath(prop.getProperty("ToCommon.Gmail.Inboxlabel.Xpath"));
      			  }
      			  else
      				 Assert.fail("FAILED: PAS Job mail content is not verified Successfully"); 
      		 }
      		  
      		 else 
      			 Assert.fail("FAILED: Job Type not verified Successfully");
      	 }
      		else
      			Assert.fail("FAILED: Mail Subject is not verified Successfully");
      	  }
      	  else
      		  Assert.fail("FAILED: Inbox doesn't has atleast one unread mail");
//         }
//         else
//      	   Assert.fail("FAILED: Mail not yet received, please check.. ");
      	
      	return this;
      }
    
    public CommonLogin GmailSelectAllMailDelete() throws Exception{
    	Thread.sleep(3000);
    	clickByXpath(prop.getProperty("ToCommon.Gmail.Inboxlabel.Xpath"));
    	Thread.sleep(3000);
    	clickByXpath(prop.getProperty("ToCommon.Gmail.SelectAllChkbx.Xpath"));
    	Thread.sleep(2000);
    	clickByXpath(prop.getProperty("ToCommon.Gmail.mailDelete.Xpath"));
    	Thread.sleep(2000);
        int chkboxSize=driver.findElements(By.xpath(prop.getProperty("ToCommon.Gmail.SelectAllChkbx.Xpath"))).size();
        if(chkboxSize>1){
        	Assert.fail("FAIL: Mail not deleted Successfully..");
        }
        else {
        	System.out.println("Mail deleted Successfully");
        }
		return this;
    	
    }
    
    public CommonLogin GmailSignOut() throws Exception{
    	clickByXpath(prop.getProperty("ToCommon.Gmail.ProfileSetting.Xpath"));
    	Thread.sleep(2000);
//    	clickByXpath(prop.getProperty("ToCommon.Gmail.SignOut.Xpath"));
    	clickByLink(prop.getProperty("ToCommon.Gmail.SignOut.LinkText"));
        Thread.sleep(5000);
    	return this;
    }
    
    public CommonLogin MailSubjectValidation(String mailSubject,boolean SubjectClick,boolean title) throws Exception{
//    	String MailSubjectElement = "(//td[@class='xY a4W']/descendant::b)";
    	int Subsize= driver.findElements(By.xpath(prop.getProperty("ToCommon.Gmail.MailSubjectRed.Xpath"))).size();
    	if (Subsize>0){
    		System.out.println("More than one mails are available");
    		for (int i=1;i<=Subsize;i++){
    		   String mailSubjectText;
    		   if(title){
    			 mailSubjectText=driver.findElement(By.xpath("(//td[@class='xY a4W']/descendant::span[1])["+i+"]")).getText();
       			 System.out.println(i+"."+mailSubjectText);
    		   }
    		   else {
    			 mailSubjectText=driver.findElement(By.xpath("(//td[@class='xY a4W']/descendant::span[2])["+i+"]")).getText();
       			System.out.println(i+"."+mailSubjectText);
    		   }
    			System.out.println(mailSubject);
    			  if(mailSubjectText.contains(mailSubject)){
    				  System.out.println("Required string is available in Searched Mail Subject");
    				  if(SubjectClick){
    				   driver.findElement(By.xpath("(//td[@class='xY a4W']/descendant::span[1])["+i+"]")).click();
    				  }
    				  break;
    			  }
    			  else if(i==Subsize){
    				  Assert.fail("FAILED: Searched/Required Mail Subject is not present in the results");
    			  }
    		}
    	}
    	else
    		Assert.fail("FAILED: No Mails are present in Inbox/searched results");
    	return this;
    }

    public CommonLogin MailSearchNdSelect(String mailSub,String JobName) throws Exception{
         Thread.sleep(3000);
//    	 enterById(prop.getProperty("ToCommon.Gmail.MailSearchText.Id"),JobName);
//    	 Thread.sleep(2000);
//		 clickById(prop.getProperty("ToCommon.Gmail.MailSearchBtn.Id"));
//		 Thread.sleep(5000);
 		  clickByXpath("//button[@class='gb_xf']");
		  Thread.sleep(5000);
		  clickByXpath("(//div[@class='w-Nw boo'][4])/descendant::input");
		  Thread.sleep(3000);
		  enterByXpath("(//div[@class='w-Nw boo'][4])/descendant::input", JobName);
		  Thread.sleep(3000);
		  clickByXpath("(//div[text()='Search'])[2]");
		  Thread.sleep(5000);
		  MailSubjectValidation(mailSub,true,false);
		return this; 
    }
    public CommonLogin HyperLinkValidationProfile(Dataset ds,String PageTilte,boolean Delete, boolean scenario,String JobName) throws Exception{
		WebDriverWait wait = new WebDriverWait(driver, 20);
     	wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(prop.getProperty("ToCommon.Gmail.AuditProfileLink.Xpath"))))).click();
     	Thread.sleep(7000); 
     	String pwindow = driver.getWindowHandle();
		 String GmailPageTitle=driver.getTitle();
		 System.out.println("Parent Window GMAIL page Title is : "+GmailPageTitle);
		 Set<String> s1=driver.getWindowHandles();
		 Iterator<String> I1= s1.iterator();
		 while(I1.hasNext()){
		    String cwindow=I1.next();
			Thread.sleep(2000);
//			String Chdwind=driver.getTitle();
			if(!pwindow.equals(cwindow)){
				driver.switchTo().window(cwindow);
				String wintitle=driver.switchTo().window(cwindow).getTitle();
				System.out.println("Child Window Prime Server Title is: "+ wintitle);
				if (wintitle.contains(ds.getAsString("LoginPageTitle"))){
					System.out.println("Server Yet to be login Since Old Sessions are not available");
					new PrimeLogin(driver,ds)
			 		.PiLogin(UserName,Password);
			 		PageNavigation.waitUntilLoginCompleted(driver);
					Thread.sleep(10000);
				}
				else {
					System.out.println("No Need to Login Since Already Old session is available");
					PageNavigation.waitUntilLoginCompleted(driver);
					Thread.sleep(10000);
				}
			}
		 }
				String wintitle1=driver.getTitle();
			if(scenario){
				if(wintitle1.contains(PageTilte)){
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(prop.getProperty("ToProfile.RuleTilteText.Id")))));
					String RuleText= getTextById(prop.getProperty("ToProfile.RuleTilteText.Id"));
					System.out.println(RuleText);
					if(RuleText.contains(ds.getAsString("PolicyName"))){
					  System.out.println(PageTilte+ "Validated Successfully from Profile HyperLink");
					  if (Delete){
						  clickByXpath(prop.getProperty("ToProfile.ProfileDelete.Xpath"));
						  wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(prop.getProperty("ToProfile.ProfileDeleteYes.Xpath"))))).click();
						  System.out.println("Is Toaster Message Verified : "+PageNavigation.toasterMsgVerification("Deleted Successfully.!!!"));
					  }
					}
				}
			    else {
			    	System.err.println("There is Mismatch the require page("+PageTilte+") and Navigated Page("+wintitle1+")");
			    	Assert.fail("FAILED, PAGE MISMATCH OCCUR");
			    }
			  }
		   else{
				if(wintitle1.contains(PageTilte)){
					String infoText=wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(prop.getProperty("ToProfile.ProfileNotPresentAlert.Xpath"))))).getText();
				    System.out.println(infoText);
				  if(infoText.contains("Policy profile does not exist (or) Deleted profile cannot be redirected")){
					  final Actions builder = new Actions(driver);
					  builder.sendKeys(Keys.ESCAPE).perform();
					  System.out.println("POP up Message unknowledged Successfully");
				  }
				  else{
					  Assert.fail("FAILED: POP up is not showing for deleted profile");
				  }
				   
				}
				else {
					Assert.fail("FAILED, POP up not showing for deleted Profile");
				}
			  }
		    driver.close();
		    driver.switchTo().window(pwindow);
		    Thread.sleep(2000);
		    String GmailPageTitle1=driver.getTitle();
		   if(GmailPageTitle1.contains("Gmail")){
			   System.out.println("Parent Window Naviagted Successfully");
		   }
		 return this;
    }

    public CommonLogin HyperLinkValidationJob(Dataset ds,String PageTilte,boolean Delete, boolean scenario,String JobName) throws Exception{
		 WebDriverWait wait = new WebDriverWait(driver, 20);
 		 wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(prop.getProperty("ToCommon.Gmail.AuditJobLink.Xpath"))))).click();
		 String pwindow = driver.getWindowHandle();
		 String GmailPageTitle=driver.getTitle();
		 System.out.println("Parent Window GMAIL page Title is : "+GmailPageTitle);
		 Set<String> s1=driver.getWindowHandles();
		 Iterator<String> I1= s1.iterator();
		 while(I1.hasNext()){
		    String cwindow=I1.next();
			Thread.sleep(2000);
			if(!pwindow.equals(cwindow)){
				driver.switchTo().window(cwindow);
				String wintitle=driver.switchTo().window(cwindow).getTitle();
				System.out.println("Child Window Prime Server Title is: "+ wintitle);
				if (wintitle.contains(ds.getAsString("LoginPageTitle"))){
					System.out.println("Server Yet to be login Since Old Sessions are not available");
					new PrimeLogin(driver,ds)
			 		.PiLogin(UserName,Password);
			 		PageNavigation.waitUntilLoginCompleted(driver);
				}
				else {
					System.out.println("No Need to Login Since Already Old session is available");
					PageNavigation.waitUntilLoginCompleted(driver);
				}
			}
		   }
				String wintitle1=driver.getTitle();
			if(scenario){
			    if(wintitle1.contains(PageTilte)){
			    	String JobTableText=wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className(prop.getProperty("ToJob.JobTableText.Class"))))).getText();
			    	if(JobTableText.contains(ds.getAsString("Status"))){
			    		 System.out.println(PageTilte+ "Validated Successfully from Job HyperLink");
			    		 clickByXpath(prop.getProperty("ToJob.BreadScr.JoBDashboard.Xpath")); 
			    		 new CompleJobDashboardPage(driver, test)
			    		 .SelectedJobDelete(JobName);
			    		 System.out.println("Is Toaster Message Verified : "+PageNavigation.toasterMsgVerification(" Selected jobs deleted successfully."));
			    	}
			     }
			    else {
			    	System.err.println("There is Mismatch the require page("+PageTilte+") and Navigated Page("+wintitle1+")");
			    	Assert.fail("FAILED, PAGE MISMATCH OCCUR");
			    }
			  }
		   else{
				if(wintitle1.contains(PageTilte)){
					String JobTableText=wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className(prop.getProperty("ToJob.JobTableText.Class"))))).getText();
			    	if(JobTableText.contains("No data is available")){
			    		 System.out.println(PageTilte+ " Page and Deleted Job details Validated Successfully from Job HyperLink");
			    	}
			    	else {
						Assert.fail("FAILED:Deleted Jobs Details May Present");
					}
				}
				else {
					System.err.println("There is Mismatch the require page("+PageTilte+") and Navigated Page("+wintitle1+")");
			    	Assert.fail("FAILED, PAGE MISMATCH OCCUR");
				}
			 }
		    driver.close();
		    driver.switchTo().window(pwindow);
		    Thread.sleep(2000);
		    String GmailPageTitle1=driver.getTitle();
		   if(GmailPageTitle1.contains("Gmail")){
			   System.out.println("Parent Window Naviagted Successfully");
		   }
		 return this;
    }
}

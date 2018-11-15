package com.cisco.ui.test.compliance.test;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.ui.test.compliance.pages.CommonLogin;
import com.cisco.ui.test.compliance.pages.ComplProfile;
import com.cisco.ui.test.compliance.pages.CompleJobDashboardPage;
import com.cisco.ui.test.compliance.pages.ComplePolicyPage;
import com.cisco.ui.test.compliance.pages.PiPsirtPage;
import com.cisco.ui.test.compliance.pages.PrimeInventoryPage;
import com.cisco.ui.test.compliance.pages.PrimeLogin;
import com.cisco.ui.test.compliance.wrappers.utils.OpentapsWrappers;
import com.cisco.ui.test.compliance.wrappers.utils.SSHloginUtil;
import com.cisco.ui.test.compliance.wrappers.utils.PageNavigation;


public class ComplianceTest3 extends OpentapsWrappers {
   public String AuditProfName,AuditProfName1,AuditJobName,FixJobName,AuditJobName1;
   
   @Parameters("browser")
	@BeforeClass
	public void setValues(String browser) throws InterruptedException {
        browserName = browser;
        invokeApp(browserName,false);
 		new PrimeLogin(driver,ds)
 		.PiLogin(UserName,Password);
 		PageNavigation.waitUntilLoginCompleted(driver);
	}  
	@Test(groups = {"Sanity"}, description = "Test Check the Compliance Policies Import Functionality ",dataProvider = "TEATest.DataProvider", dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 0)
	public void CompleEnableFunctionality(final Dataset ds) throws Exception {
		Thread.sleep(3000);
		PageNavigation.ComplianceEnable(driver,ds);
		invokeApp(browserName,false);
 		new PrimeLogin(driver,ds)
 		.PiLogin(UserName,Password);
 		PageNavigation.waitUntilLoginCompleted(driver);
		PageNavigation.MailserverSetting(driver, ds);
		PageNavigation.JobMailNotification(driver, ds);
 		
	}
	@Test(groups = {"Sanity"}, description = "Test Check the Compliance Policies Import Functionality ",dataProvider = "TEATest.DataProvider", dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 1)
	public void ComplianceEmailFeature(final Dataset ds) throws Exception {
		AuditProfName = UniqueTitle(ds.getAsString("ProfName"));
		AuditProfName1 = UniqueTitle(ds.getAsString("ProfName1"));
		AuditJobName = UniqueTitle(ds.getAsString("AuditJobName"));
		AuditJobName1 = UniqueTitle(ds.getAsString("AudSchdJobName"));
		FixJobName = UniqueTitle(ds.getAsString("FixJobName"));
		
//		AuditProfName = "MailFeature20181017075341";
//		AuditProfName1 = "MailScheduled20181017075341";
//		AuditJobName = "MailAuditJob20181017075341";
//		AuditJobName1 = "MailAudSchJob20181017075341";
//		FixJobName = "MailFixJob20181017075341";

		new PrimeInventoryPage(driver, test)
		.NoDeviceAdd(ds.getAsString("deviceid"));
		new ComplePolicyPage(driver,test)
		.PolicyImportCommon(ds);
		Thread.sleep(3000);
		new ComplProfile(driver, test)
		.CreateProfile(ds,AuditProfName)
		.PolicyCusPolMap(ds,ds.getAsString("policyName"))
		.RunJobwithsingleDevice(ds, AuditJobName,ds.getAsString("deviceid"),ds.getAsString("Recurrence"))
		.CreateProfile(ds,AuditProfName1)
		.PolicyCusPolMap(ds,ds.getAsString("policyName"))
		.RunJobwithsingleDevice(ds, AuditJobName1,ds.getAsString("deviceid"),ds.getAsString("Recurrence1"));
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",AuditJobName)
		.JobStatus("Failure")
		.RunFixjobAllviolation(ds, FixJobName);
		driver.quit();
		invokeAppCommon(browserName,ds.getAsString("url"));
		new CommonLogin(driver, ds)
		.GmailLogin(ds.getAsString("usernameid"),ds.getAsString("password"))
		.MailNewFeatureValidation(ds,AuditJobName,ds.getAsString("JobType"),"Failure",AuditProfName,"Completed")
		.MailNewFeatureValidation(ds,FixJobName,ds.getAsString("JobType1"),"Success",AuditProfName,"Completed")
		.MailNewFeatureValidation(ds,AuditJobName1,ds.getAsString("JobType"),"Failure",AuditProfName1,"Scheduled");
	}
	
	@Test(groups = {"Sanity"},dependsOnMethods={"ComplianceEmailFeature"},description = "Test Check the Compliance Policies Import Functionality ",dataProvider = "TEATest.DataProvider", dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 2)
	public void ComplianceHyperLinkValidation(final Dataset ds) throws Exception {
		new CommonLogin(driver, ds)
		.MailSearchNdSelect(AuditProfName,AuditJobName)
		.HyperLinkValidationProfile(ds,ds.getAsString("profilePageTitle"),true, true, AuditJobName)
		.HyperLinkValidationJob(ds,ds.getAsString("JobPageTitle"),true, true, AuditJobName)
		.MailSearchNdSelect("Compliance Fix Job",FixJobName)
		.HyperLinkValidationJob(ds,ds.getAsString("JobPageTitle"),true, true, FixJobName);
	}
	
	@Test(groups = {"Sanity"},dependsOnMethods={"ComplianceEmailFeature"},description = "Test Check the Compliance Policies Import Functionality ",dataProvider = "TEATest.DataProvider", dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 3)
	public void ComplianceHyperLinkValidationAfterDelete(final Dataset ds) throws Exception {
		new CommonLogin(driver, ds)
		.MailSearchNdSelect(AuditProfName,AuditJobName)
		.HyperLinkValidationProfile(ds,ds.getAsString("profilePageTitle"),false,false,AuditJobName)
		.HyperLinkValidationJob(ds,ds.getAsString("JobPageTitle"),false,false, AuditJobName)
		.MailSearchNdSelect("Compliance Fix Job",FixJobName)
		.HyperLinkValidationJob(ds,ds.getAsString("JobPageTitle"),false,false, FixJobName)
		.GmailSelectAllMailDelete()
		.GmailSignOut();
	}
}
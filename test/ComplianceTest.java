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


public class ComplianceTest extends OpentapsWrappers {
	
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
	@Test(groups = {"Sanity"}, description = "Test Check the Compliance Policies Import Functionality ",dataProvider = "TEATest.DataProvider", dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 0)
	public void policyImportFunctionality(final Dataset ds) throws Exception {
		Thread.sleep(30000);
		new ComplePolicyPage(driver,test)
		.PolicyImport(ds);
	}
	
	@Test(groups = {"Sanity"}, description = "Test Check the Invalid Ruleinputs in the policy",dataProvider = "TEATest.DataProvider", dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 1)
	public void RuleInputNotAllowInvalidValues(final Dataset ds) throws Exception {
		int[] perform = {0,1,2,6};
		// 0-Cisco device, 1-IOS,2-XR,3-XE,4-NX.5-WLC,6-ASA
	    String PolicyName=UniqueTitle(ds.getAsString("PolicyName"));
	    String ProName = UniqueTitle(ds.getAsString("ProfName"));
		new ComplePolicyPage(driver,test)
		.InvalidInputValidation(ds,PolicyName,perform);
		Thread.sleep(3000);
		new ComplProfile(driver, test)
		.CreateProfile(ds,ProName)
		.PolicyCusPolMap(ds,PolicyName)
		.ValidateRuleInputInvalidValues(ds,ProName);
		
	}
	
	@Test(groups = {"Sanity"}, description = "Test Check the policy import with Double Quote",dataProvider = "TEATest.DataProvider", dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 2)
	public void policyImportWithDoubleQuotes(final Dataset ds) throws Exception {
		new ComplePolicyPage(driver,test)
		.PolicyImportCommon(ds);
	}
	
	@Test(groups = {"Sanity"}, description = "Test Check the policy import with Double Quote",dataProvider = "TEATest.DataProvider", dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 3)
	public void jobHelpIconValidation(final Dataset ds) throws Exception {
		new CompleJobDashboardPage(driver,test)
		.jobHelpIconValidation(ds);
	}
	@Test(groups={"Sanity"},description="Test check the Regular Expression results with and without Brackets",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 4)
	public void regExwithAndWithoutBrackets(final Dataset ds) throws Exception{
		Thread.sleep(5000);
		String ProName = UniqueTitle(ds.getAsString("ProfName"));
		String JobName = UniqueTitle(ds.getAsString("AJobName"));
		new PrimeInventoryPage(driver, test)
		.NoDeviceAdd(ds.getAsString("deviceid"));
		new ComplePolicyPage(driver,test)
		.PolicyImportCommon(ds);
		Thread.sleep(3000);
		new ComplProfile(driver, test)
		.CreateProfile(ds,ProName)
		.PolicyCusPolMap(ds,ds.getAsString("policyName"))
		.RunJobwithsingleDevice(ds, JobName,ds.getAsString("deviceid"),ds.getAsString("Recurrence"));
		Thread.sleep(3000);
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",JobName)
		.JobStatus("Failure")
		.PolicyCountValidation(ds)
		.locationColumnValidation(ds);
	}
}
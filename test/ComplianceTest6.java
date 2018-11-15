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


public class ComplianceTest6 extends OpentapsWrappers {
	public String PrimProName,PrimJobName,SecProName,SecJobName,PrimLastRunStatus,SecLastRunStatus ;
	
	
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
 		new PrimeInventoryPage(driver, test)
		.DeviceBulkImport(ds)
	    .DeviceSearch(ds.getAsString("deviceid-IOS1"))
	    .DeviceStatusCheck(ds.getAsString("deviceid-IOS2"));
	}
	
	@Test(groups={"Sanity"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 1)
	public void ComplianceDataPopulationOnPrimary(final Dataset ds) throws Exception {
		PrimProName = UniqueTitle(ds.getAsString("ProfName"));
		PrimJobName = UniqueTitle(ds.getAsString("AJobName"));
		new ComplePolicyPage(driver,test)
		.PolicyImportCommon(ds);
		Thread.sleep(3000);
		new ComplProfile(driver, test)
		.CreateProfile(ds,PrimProName)
		.PolicyCusPolMap(ds,ds.getAsString("policyName"))
		.RunJobwithsingleDevice(ds, PrimJobName,ds.getAsString("deviceid"),ds.getAsString("Recurrence"));
		Thread.sleep(3000);
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",PrimJobName)
		.JobStatus("Failure");
		new PiPsirtPage(driver,test)
		.JobSchedule(ds)
		.PsirtJobStatus(ds);
		PrimLastRunStatus= PiPsirtPage.PsirtLastRunStatusDetails();
//		Primary Server Stop and Secondary server start
	}
	
	@Test(groups={"Sanity"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 1)
	public void CompliancePrimaryDataValidationOnSecondary(final Dataset ds) throws Exception {
		new ComplProfile(driver, test)
		.CheckProfilePresence(PrimProName);
		new ComplePolicyPage(driver,test)
		.CheckPolicyPresence(ds);
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",PrimJobName)
		.JobStatus("Failure");
		new PiPsirtPage(driver,test)
		.PsirtJobStatus(ds)
		.PsirtLastRunStatusValidation(PrimLastRunStatus);
		new PrimeInventoryPage(driver, test)
	    .SyncCompletedDevice(ds.getAsString("deviceid-IOS1"))
	    .SyncCompletedDevice(ds.getAsString("deviceid-IOS2"))
	    .SyncCompletedDevice(ds.getAsString("deviceid-IOS3"))
	    .SyncCompletedDevice(ds.getAsString("deviceid-IOS4"))
	    .SyncCompletedDevice(ds.getAsString("deviceid-IOS5"));
	}
	
	@Test(groups={"Sanity"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 1)
	public void ComplianceDataPopulationOnSecondary(final Dataset ds) throws Exception {
		SecProName = UniqueTitle(ds.getAsString("ProfName"));
		SecJobName = UniqueTitle(ds.getAsString("AJobName"));
		new ComplePolicyPage(driver,test)
		.PolicyImportCommon(ds);
		Thread.sleep(3000);
		new ComplProfile(driver, test)
		.CreateProfile(ds,SecProName)
		.PolicyCusPolMap(ds,ds.getAsString("policyName"))
		.RunJobwithsingleDevice(ds, SecJobName,ds.getAsString("deviceid"),ds.getAsString("Recurrence"));
		Thread.sleep(3000);
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",SecJobName)
		.JobStatus("Failure");
		new PiPsirtPage(driver,test)
		.JobSchedule(ds)
		.PsirtJobStatus(ds);
		SecLastRunStatus=PiPsirtPage.PsirtLastRunStatusDetails(); 
	}
	
	//Secondary stop Primary Start
	@Test(groups={"Sanity"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 1)
	public void ComplianceSecondaryDataValidationOnPrimary(final Dataset ds) throws Exception {
		new ComplProfile(driver, test)
		.CheckProfilePresence(SecProName);
		new ComplePolicyPage(driver,test)
		.CheckPolicyPresence(ds);
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",SecJobName)
		.JobStatus("Failure");
		new PiPsirtPage(driver,test)
		.PsirtJobStatus(ds)
		.PsirtLastRunStatusValidation(SecLastRunStatus);
	}
}
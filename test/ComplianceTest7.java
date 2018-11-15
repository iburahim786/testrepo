package com.cisco.ui.test.compliance.test;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.test.tea.log.TestLogger;
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


public class ComplianceTest7 extends OpentapsWrappers {
	public String PrimProName,PrimJobName,SecProName,SecJobName,PrimLastRunStatus,SecLastRunStatus,PrimFJobName1,PrimFJobName2 ;
	
	
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
	}
	
	@Test(groups={"Sanity"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 1)
	public void ComplianceScaleAuditPSIRTJob(final Dataset ds) throws Exception {
		PrimProName = UniqueTitle(ds.getAsString("ProfName"));
		PrimJobName = UniqueTitle(ds.getAsString("AJobName"));
		new ComplePolicyPage(driver,test)
		.PolicyImportCommon(ds);
		Thread.sleep(3000);
		new ComplProfile(driver, test)
		.CreateProfile(ds,PrimProName)
		.PolicyCusPolMap(ds,ds.getAsString("policyName"))
		.RunJobAllDevice(ds,PrimJobName,ds.getAsString("Recurrence"));
		new PiPsirtPage(driver,test)
		.JobSchedule(ds)
		.PsirtJobStatus(ds);
		System.out.println("Scale job is Completed");
	}
	
	@Test(groups={"Sanity"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 2)
	public void ComplianceScaleFixJob(final Dataset ds) throws Exception {
		PrimFJobName1 = UniqueTitle(ds.getAsString("FJobName1"));
		PrimFJobName2 = UniqueTitle(ds.getAsString("FJobName2"));
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",PrimJobName)
//		.ModuleJobstable("User Jobs","Compliance Jobs","scaleajob20180705073223")
		.JobStatus("Failure")
		.ScaleSleep(90000)
		.VioSumQuickFilDataEnt(7, "15.1.1.")
		.ScaleSleep(90000)
		.RunFixjobAllviolation(ds, PrimFJobName1)
		.ModuleJobstable("User Jobs","Compliance Jobs",PrimJobName)
//		.ModuleJobstable("User Jobs","Compliance Jobs","scaleajob20180705073223")
		.JobStatus("Failure")
		.ScaleSleep(90000)
		.VioSumQuickFilDataEnt(7, "15.1.2.")
		.ScaleSleep(90000)
		.RunFixjobAllviolation(ds, PrimFJobName2);
		PageNavigation.logout(driver, true);
		TestLogger.info("Waiting for 15 min to complete the jobs");
		Thread.sleep(900000);
		invokeApp(browserName,false);
	    new PrimeLogin(driver,ds)
	 	.PiLogin(UserName,Password);
	 	PageNavigation.waitUntilLoginCompleted(driver);
	 	new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",PrimFJobName1)
		.JobStatus("Success");
	 	new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",PrimFJobName2)
		.JobStatus("Success");
	}
}
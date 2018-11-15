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


public class ComplianceTest5 extends OpentapsWrappers {
   public String CopyRunAudiJobName;
	
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
	public void RbmlAndPasDatesUpdates(final Dataset ds) throws Exception {
		Thread.sleep(20000);
		 new PiPsirtPage(driver, test)
		  .PasRbmldatesVerification()
		  .PasRbmlDatesCrossVerify(ds);
	}
	
	@Test(groups={"Sanity"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 2)
	public void CaveatAndCveColumnValidation(final Dataset ds) throws Exception {
		Thread.sleep(20000);
		 new PiPsirtPage(driver, test)
		  .PresenceOfCVEnCaveatColumnndSorting(ds);
	}
  
	@Test(groups={"Sanity"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 3)
	public void CopyRuningSuccess(final Dataset ds) throws Exception {
		Thread.sleep(20000);
  	    CopyRunAudiJobName=UniqueTitle(ds.getAsString("AJobName"));
		String FixJobSuccName=UniqueTitle(ds.getAsString("FJobName"));
		String ProName = UniqueTitle(ds.getAsString("ProfName"));
		String[] DeviceIds={ds.getAsString("deviceid-IOS1"),ds.getAsString("deviceid-IOS2"),ds.getAsString("deviceid-ASA")};
		new PrimeInventoryPage(driver, test)
		.DeviceBulkImport(ds)
	    .DeviceSearch(ds.getAsString("deviceid-ASA"))
	    .DeviceStatusCheck(ds.getAsString("deviceid-IOS1"))
	    .DeviceStatusCheck(ds.getAsString("deviceid-IOS2"))
	    .DeviceStatusCheck(ds.getAsString("deviceid-ASA"));
		new ComplePolicyPage(driver,test)
		.PolicyImportCommon(ds);
		new ComplProfile(driver, test)
		.CreateProfile(ds,ProName)
		.PolicyCusPolMap(ds,ds.getAsString("policyName"))
		.RunJobwithmultiDevice(ds, CopyRunAudiJobName,DeviceIds,ds.getAsString("Recurrence"));
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",CopyRunAudiJobName)
		.JobStatus("Failure")
		.VioSumQuickFilDataEnt(5,ds.getAsString("RuleTitle"))
		.VioSumQuickFilDataEnt(7, ds.getAsString("IOS1-Name"))
		.RunFixjobAllvioWithCopyRunEnabled(ds, FixJobSuccName)
		.ModuleJobstable("User Jobs","Compliance Jobs",FixJobSuccName)
		.JobStatus("Success")
		.CopyRunStartPopOverValidation(deviceprop.getProperty(""+ds.getAsString("deviceid-IOS1")+".Ip_Dns"), ds.getAsString("StartupSuccessMsg"));
	}
	
   
	@Test(groups={"Sanity"},dependsOnMethods={"CopyRuningSuccess"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 4)
	public void CopyRuningFailure(final Dataset ds) throws Exception {
		Thread.sleep(20000);
//		CopyRunAudiJobName="CopyRunAudit20180605132941";
		String FixJobFailName=UniqueTitle(ds.getAsString("FJobName"));
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",CopyRunAudiJobName)
		.JobStatus("Failure")
		.VioSumQuickFilDataEnt(5,ds.getAsString("RuleTitle"))
		.VioSumQuickFilDataEnt(7,ds.getAsString("IOS1-Name"))
		.RunFixjobAllvioWithCopyRunEnabled(ds, FixJobFailName)
		.ModuleJobstable("User Jobs","Compliance Jobs",FixJobFailName)
		.JobStatus("Failure")
		.CopyRunStartPopOverValidation(deviceprop.getProperty(""+ds.getAsString("deviceid-IOS1")+".Ip_Dns"), ds.getAsString("StartupNoMsg"));
	}
	
	@Test(groups={"Sanity"},dependsOnMethods={"CopyRuningSuccess"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 5)
	public void CopyRuningPartSuccess(final Dataset ds) throws Exception {
		Thread.sleep(20000);
		String FixJobPartSuccName=UniqueTitle(ds.getAsString("FJobName"));
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",CopyRunAudiJobName)
		.JobStatus("Failure")
		.VioSumQuickFilDataEnt(5,ds.getAsString("RuleTitle"))
		.VioSumQuickFilDataEnt(7,ds.getAsString("IOS1-Name"))
		.RunFixjobAllvioWithCopyRunEnabled(ds, FixJobPartSuccName)
		.ModuleJobstable("User Jobs","Compliance Jobs",FixJobPartSuccName)
		.JobStatus("Partial-Success")
		.CopyRunStartPopOverValidation(deviceprop.getProperty(""+ds.getAsString("deviceid-IOS2")+".Ip_Dns"), ds.getAsString("StartupFailureMsg"));
	}
}
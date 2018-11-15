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


public class ComplianceTest1 extends OpentapsWrappers {
	
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
	@Test(groups={"Sanity"},description="Test check Rule table quick and advance filter",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 5)
	public void policyRuleQandAfilter(final Dataset ds) throws Exception{
		Thread.sleep(5000);
		new ComplePolicyPage(driver,test)
		.PolicyImportCommon(ds)
		.ruleTableQfilterValidation(ds)
		.ruleTableAfilterValidation(ds);
	
	}
	
	@Test(groups={"Sanity"},description="Test check WLC show Comments and Other details",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 6)
	public void wlcShowComments(final Dataset ds) throws Exception{
		Thread.sleep(10000);
		new PrimeInventoryPage(driver, test)
		.NoDeviceAdd(ds.getAsString("deviceid"));
		new PrimeInventoryPage(driver, test)
		.NoDeviceAdd(ds.getAsString("deviceid1"));
		new PiPsirtPage(driver,test)
		.JobSchedule(ds)
		.PsirtJobStatus(ds)
		.PsirtDNSAddedDeviceEOXValidation();
		SSHloginUtil.WlcCommandValidation();
	}
	
//	@Test(groups={"Sanity"},description="Test check PSIRT results-IOS version before and After changed in the device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 7)
//	public void DeviceImageOSversionInPsirt(final Dataset ds) throws Exception {
//		Thread.sleep(10000);
//		SSHloginUtil.executeSparoTelnetFileChange(ds,ds.getAsString("file2"));
//		Thread.sleep(3000);
//		SSHloginUtil.executeAddroute(ds.getAsString("NetworkId"),ds.getAsString("NetMask"),ds.getAsString("SaproIpaddress"));
//		new PrimeInventoryPage(driver, test)
//		.NoDeviceAdd(ds.getAsString("deviceid"));
//		new PiPsirtPage(driver,test)
//		.JobSchedule(ds)
//		.PsirtJobStatus(ds)
//		.PsirtSwEoxImageVerCheck(ds,"12.2(55)SE");
//		 SSHloginUtil.executeSparoTelnetFileChange(ds,ds.getAsString("file1"));
//		Thread.sleep(10000);
//	    new PrimeInventoryPage(driver, test)
//	    .SyncCompletedDevice(ds.getAsString("deviceid"));
//	    new PiPsirtPage(driver,test)
//	    .PsirtSwEoxImageVerCheck(ds,"12.2(55)SE")
//	    .JobSchedule(ds)
//		.PsirtJobStatus(ds)
//		.PsirtSwEoxImageVerCheck(ds,"12.2(58)SE2");
//		 SSHloginUtil.executeSparoTelnetFileChange(ds,ds.getAsString("file2"));
//	}
	@Test(groups={"Sanity"},description="Test check Prime Infrastructure 3.2-Compliance Engine finds non existing show output",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 7)
	public void ShowCmdsNonEsisting(final Dataset ds) throws Exception{
		Thread.sleep(20000);
		String ProName = UniqueTitle(ds.getAsString("ProfName"));
		String JobName = UniqueTitle(ds.getAsString("AJobName"));
		String JobName1 = UniqueTitle(ds.getAsString("AJobName1"));
		String[] cmd={"en","config terminal","interface po1","exit","interface po2","end","show etherchannel summary | i SD"};
		String[] cmd1={"en","config terminal","no interface po1","no interface po2","end","show etherchannel summary | i SD"};
	    String vcmd="SD";
	    String vcmd1="";
		new PrimeInventoryPage(driver, test)
		.NoDeviceAdd(ds.getAsString("deviceid"));
		Thread.sleep(3000);
		SSHloginUtil.DeviceConfig(cmd,vcmd,"Positive",ds.getAsString("deviceid"));
		Thread.sleep(3000);
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
		.PolicyCountValidation(ds);
		SSHloginUtil.DeviceConfig(cmd1, vcmd1,"Negative",ds.getAsString("deviceid"));
		Thread.sleep(3000);
		new ComplProfile(driver, test)
		.ProfileSelectOnExisting(ds, ProName)
		.RunJobwithsingleDevice(ds, JobName1,ds.getAsString("deviceid"),ds.getAsString("Recurrence"));
		Thread.sleep(3000);
		new CompleJobDashboardPage(driver, test)
		.ModuleJobstable("User Jobs","Compliance Jobs",JobName1)
		.JobStatus("Success");
	}
}
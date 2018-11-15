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


public class ComplianceTest4 extends OpentapsWrappers {
	
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
	public void DeviceImageOSversionInPsirt(final Dataset ds) throws Exception {
		Thread.sleep(20000);
		SSHloginUtil.executeSparoTelnetFileChange(ds,ds.getAsString("file2"));
		Thread.sleep(3000);
		SSHloginUtil.executeAddroute(ds.getAsString("NetworkId"),ds.getAsString("NetMask"),ds.getAsString("SaproIpaddress"));
		new PrimeInventoryPage(driver, test)
		.DeviceBulkImport(ds)
	    .DeviceSearch(ds.getAsString("deviceid-NxOS"))
	    .DeviceStatusCheck(ds.getAsString("deviceid-IOS"))
	    .DeviceStatusCheck(ds.getAsString("deviceid-XE"))
	    .DeviceStatusCheck(ds.getAsString("deviceid-ASA"))
	    .DeviceStatusCheck(ds.getAsString("deviceid-NxOS"))
	    .DeviceStatusCheck(ds.getAsString("deviceid-WLC"));
		new PiPsirtPage(driver,test)
		.JobSchedule(ds)
		.PsirtJobStatus(ds)
		.PsirtSwEoxImageVerCheck("IOS", ds.getAsString("deviceid-IOS"),"12.2(55)SE" )
		.PsirtSwEoxImageVerCheck("IOS-XE", ds.getAsString("deviceid-XE"), "15.6(1)S3")
		.PsirtSwEoxImageVerCheck("ASA", ds.getAsString("deviceid-ASA"), "9.1(2)")
		.PsirtSwEoxImageVerCheck("Nx-OS",ds.getAsString("deviceid-NxOS"),"6.1(3)")
		.PsirtSwEoxImageVerCheck("WLC",ds.getAsString("deviceid-WLC"),"8.0.120.0");
		 SSHloginUtil.executeSparoTelnetFileChange(ds,ds.getAsString("file1"));
		Thread.sleep(10000);
	    new PrimeInventoryPage(driver, test)
	    .SyncCompletedDevice(ds.getAsString("deviceid-IOS"))
	    .SyncCompletedDevice(ds.getAsString("deviceid-XE"))
	    .SyncCompletedDevice(ds.getAsString("deviceid-ASA"))
	    .SyncCompletedDevice(ds.getAsString("deviceid-NxOS"))
	    .SyncCompletedDevice(ds.getAsString("deviceid-WLC"));
	    new PiPsirtPage(driver,test)
		.PsirtSwEoxImageVerCheck("IOS", ds.getAsString("deviceid-IOS"),"12.2(55)SE")
		.PsirtSwEoxImageVerCheck("IOS-XE", ds.getAsString("deviceid-XE"), "15.6(1)S3")
		.PsirtSwEoxImageVerCheck("ASA", ds.getAsString("deviceid-ASA"), "9.1(2)")
		.PsirtSwEoxImageVerCheck("Nx-OS",ds.getAsString("deviceid-NxOS"),"6.1(3)")
		.PsirtSwEoxImageVerCheck("WLC",ds.getAsString("deviceid-WLC"),"8.0.120.0")
	    .JobSchedule(ds)
		.PsirtJobStatus(ds)
		.PsirtSwEoxImageVerCheck("IOS", ds.getAsString("deviceid-IOS"),"12.2(58)SE2")
		.PsirtSwEoxImageVerCheck("IOS-XE", ds.getAsString("deviceid-XE"), "15.1(1)S1")
		.PsirtSwEoxImageVerCheck("ASA", ds.getAsString("deviceid-ASA"), "9.6(2)")
		.PsirtSwEoxImageVerCheck("Nx-OS",ds.getAsString("deviceid-NxOS"),"8.1(1)")
		.PsirtSwEoxImageVerCheck("WLC",ds.getAsString("deviceid-WLC"),"8.0.140.0");
		 SSHloginUtil.executeSparoTelnetFileChange(ds,ds.getAsString("file2"));
	}
	
	@Test(groups={"Sanity"},description="Test check PSIRT results for similar device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 2)
	public void SimilarDevicePsirtValidation(final Dataset ds) throws Exception {
		new PiPsirtPage(driver,test)
		.SimilarDevicePSIRTdetailsValidation(ds.getAsString("IOSdevType"))
		.SimilarDevicePSIRTdetailsValidation(ds.getAsString("XEdevType"))
		.SimilarDevicePSIRTdetailsValidation(ds.getAsString("ASAdevType"))
		.SimilarDevicePSIRTdetailsValidation(ds.getAsString("NxOSdevType"))
		.SimilarDevicePSIRTdetailsValidation(ds.getAsString("WLCdevType"));
		
	}
	@Test(groups={"Sanity"},description="Test check PSIRT results for similar device",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 2)
	public void psirtEoxFnValidation(Dataset ds) throws Exception{
		new PiPsirtPage(driver,test)
		.psirtTitleValidation(ds.getAsString("PsirtTitle"))
		.HwEolValidation(ds.getAsString("EolTitle"))
		.SwEolValidation(ds.getAsString("EolTitle"))
		.FnValidation(ds.getAsString("FnTitle"));
	}
}
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


public class ComplianceTest2 extends OpentapsWrappers {
	
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
	@Test(groups={"Sanity"},description="Test check Compliance policies that collect data from the device properties setting don't retain their config",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 1)
	public void ComplPolicyDeviceSetting(final Dataset ds) throws Exception{
		int[] perform = {0,1,2};
		// 0-Cisco device, 1-IOS,2-XR,3-XE,4-NX.5-WLC,6-ASA
	    String PolicyName=UniqueTitle(ds.getAsString("PolicyName"));
		new ComplePolicyPage(driver,test)
		.PolicyDeviceSettingValidation(ds, PolicyName, perform);
	}
	
	@Test(groups={"Sanity"},description="Test check Include all compliance logs in log bundle",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 2)
	public void ComplIncludeLogBundleCheck(final Dataset ds) throws Exception{
		Thread.sleep(3000);
		String[] cmd={"cd /opt/CSCOlumos/compliance/bin && dir *.log | head -4"};
		SSHloginUtil.LogBundleUnderCompbin(cmd);
	}
	
	@Test(groups={"Sanity"},description="Test check  Edit Job functionality",dataProvider="TEATest.DataProvider",dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 3)
	public void ComplJobEditFUnction(final Dataset ds) throws Exception{
	  Thread.sleep(3000);
	  new CompleJobDashboardPage(driver, test)
	  .JobEditFunctionality(ds);
	}
	
	@Test(groups = {"Sanity"}, description = "Test Check the policy import with Double Quote",dataProvider = "TEATest.DataProvider", dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 4)
	public void PsirtHelpIconValidation(final Dataset ds) throws Exception {
		new PiPsirtPage(driver,test)
		.PsirtHelpIconValidation(ds);
	}
	
	@Test(groups = {"Sanity"}, description = "Test Check the policy import with Double Quote",dataProvider = "TEATest.DataProvider", dataProviderClass = com.cisco.test.tea.core.dataprovider.TestDataProvider.class, enabled = true, priority = 5)
	public void JobNotificationMail(final Dataset ds) throws Exception {
		driver.navigate().refresh();
		PageNavigation.waitUntilLoginCompleted(driver);
		PageNavigation.logout(driver,true);
		invokeAppCommon(browserName,ds.getAsString("url"));
		new CommonLogin(driver, ds)
		.GmailLogin(ds.getAsString("usernameid"),ds.getAsString("password"))
		.GmailDataValidation(ds)
		.GmailSelectAllMailDelete()
		.GmailSignOut();
	}
}
package com.cisco.ui.test.compliance.wrappers.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.cisco.test.tea.log.TestLogger;
import com.cisco.ui.test.compliance.pages.PiHomePage;
import com.cisco.ui.test.compliance.pages.PrimeLogin;
import com.cisco.ui.test.compliance.wrappers.utils.DataInputProvider;

public class OpentapsWrappers extends GenericWrappers {
	
    public String browserName;
	public String dataSheetName;
	
	@BeforeSuite
	public void beforeSuite(){

	}

	@BeforeTest
	public void beforeTest(){
		loadObjects();
		loadDeviceprop();
	}
	
	@BeforeMethod
	public void beforeMethod() {

	}
		
	@AfterSuite
	public void afterSuite(){

	}

	@AfterTest
	public void afterTest(){
		unloadObjects();
		unloadDeviceprop();
	}
	
	@AfterMethod (alwaysRun = true)
	public void takeScreenShotOnFailure(final ITestResult testResult) throws IOException {
		try {
			if (testResult.getStatus() == ITestResult.FAILURE) {
				TestLogger.debug(testResult.getStatus() + "");
				String testName=UniqueTitle(testResult.getName());
				FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE) , new File("target/screenshot/screenshot_"+testName+".jpg"));
				System.out.println("Taking Screentshot for Failed Test case"+ testResult.getName());
				driver.navigate().refresh();
				PageNavigation.waitUntilLoginCompleted(driver);
			}
			else {
				System.out.println("Testcases passed with desired results : "+ testResult.getName());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass(){
		PageNavigation.logout(driver,false);
		quitBrowser();
	}
	
	@DataProvider(name="fetchData")
	public Object[][] getData(){
		return DataInputProvider.getSheet(dataSheetName);		
	}	
	
	
}

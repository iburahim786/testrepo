package com.cisco.ui.test.compliance.wrappers.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;

import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.test.tea.log.TestLogger;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public abstract class Reporter {
	public ExtentTest test;
	public static ExtentReports extent;
	public String testCaseName, testDescription, category;
	public static RemoteWebDriver driver;

	
	public void reportStep(String desc, String status) {
      
		// Write if it is successful or failure or information
		if(status.toUpperCase().equals("PASS")){
			TestLogger.debug(desc + " " + status);
			
		}else if(status.toUpperCase().equals("FAIL")){
			TestLogger.debug(desc + " " + status);
			
		}else if(status.toUpperCase().equals("INFO")){
			TestLogger.debug(desc + " " + status);
		}
	}
	
//	public void takeScreenShotOnFailure(final ITestResult testResult){
//		try {
//			if (testResult.getStatus() == ITestResult.FAILURE) {
//				TestLogger.debug(testResult.getStatus() + "");
//				final File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//				FileUtils.copyFile(scrFile, new File("target/screenshot/screenshot_" + testResult.getName() + ".jpg"));
//			}
//		} catch (final Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	public static void takeScreenshot(final String testCaseId) {
//		final File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//		try {
//			FileUtils.copyFile(scrFile, new File("target/screenshot/screenshot_" + testCaseId + ".jpg"));
//		} catch (final IOException e) {
//			e.printStackTrace();
//		}
//	}
    
//	public abstract void takeSnap();
	

//	public ExtentReports startResult(){
//		extent = new ExtentReports("./reports/result.html", false);
//		extent.loadConfig(new File("./src/main/resources/extent-config.xml"));
//		return extent;
//	}
//
//	public ExtentTest startTestCase(String testCaseName, String testDescription){
//		test = extent.startTest(testCaseName, testDescription);
//		return test;
//	}
//
//	public void endResult(){		
//		extent.flush();
//	}
//
//	public void endTestcase(){
//		extent.endTest(test);
//	}

	}
	
	

package com.cisco.ui.test.compliance.wrappers.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.cisco.test.tea.log.TestLogger;

public class HATestParent {

	protected static WebDriver browser;

//	private final String PROPERTY_FILE_NAME = "com.cisco.test.tea.perperties";
//	private final String DEFAULT_PROPERTY_FILE_NAME = "server.perperties";
	public static final String RBML_XML_FILE = "C:/Compliance/RbmlXML/MetaData.xml";
	public static final String PAS_XML_FILE = "C:/Compliance/PasXML/MetaData.xml";
	private final String FTP_FIREFOX_DRIVER_FILE = "http://10.104.119.201/prime/geckodriver.exe";
	private final String FTP_CHROME_DRIVER_FILE = "http://10.104.119.201/prime/chromedriver.exe";



	private void completeInitialSetup() {
		try {
			File driverFile = new File(RBML_XML_FILE);
			if (!driverFile.exists()) {
				TestLogger.debug("Firefox Driver file not located.. Downloading.. ");
				FileUtils.copyURLToFile(new URL(FTP_FIREFOX_DRIVER_FILE), driverFile);
			}
			driverFile = new File(PAS_XML_FILE);
			if (!driverFile.exists()) {
				TestLogger.debug("Chrome Driver file not located.. Downloading.. ");
				FileUtils.copyURLToFile(new URL(FTP_CHROME_DRIVER_FILE), driverFile);
			}
		} catch (final MalformedURLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (final IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}

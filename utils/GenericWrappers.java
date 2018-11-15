package com.cisco.ui.test.compliance.wrappers.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.ui.test.compliance.exception.ComplianceAutoException;
import com.cisco.ui.test.compliance.wrappers.utils.Reporter;
import com.cisco.ui.test.compliance.exception.ComplianceAutoException;
import org.openqa.selenium.firefox.FirefoxDriver;



public class GenericWrappers extends Reporter implements Wrappers {

	public static RemoteWebDriver driver;
	protected static Properties prop;
	protected static Properties deviceprop;
	public String sUrl,primaryWindowHandle,sHubUrl,sHubPort,UserName,Password,location;
	public static String ipaddress;
	public static String SshUsername;
	public static String SshPassword;
    public ITestResult testResult;
    public Dataset ds;
	public GenericWrappers() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/properties/config.properties")));
			sHubUrl = prop.getProperty("HUB");
			sHubPort = prop.getProperty("PORT");
			sUrl = prop.getProperty("sURL");
			UserName=prop.getProperty("UserName");
			Password=prop.getProperty("Password");
			location=prop.getProperty("datasetXML");
			ipaddress=prop.getProperty("ipAddr");
			SshUsername=prop.getProperty("sshUserName");
			SshPassword= prop.getProperty("sshPassWord");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadObjects() {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/properties/object.properties")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void loadDeviceprop() {
		deviceprop = new Properties();
		try {
			deviceprop.load(new FileInputStream(new File("./src/main/resources/properties/device.properties")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void unloadObjects() {
		prop = null;
	}
	
	public void unloadDeviceprop(){
		deviceprop=null;
	}
	/**
	 * This method will launch the browser in local machine and maximize the browser and set the
	 * wait for 30 seconds and load the url
	 * @author MIBURAHI
	 * @param url - The url with http or https
	 * 
	 */
	public void invokeApp(String browser) {
		invokeApp(browser,false);
	}

	/**
	 * This method will launch the browser in grid node (if remote) and maximize the browser and set the
	 * wait for 30 seconds and load the url 
	 * @author MIBURAHI
	 * @param url - The url with http or https
	 * 
	 */
	public void invokeApp(String browser, boolean bRemote) {
		try {

			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(browser);
			dc.setPlatform(Platform.WINDOWS);

			// this is for grid run
			if(bRemote)
				driver = new RemoteWebDriver(new URL("http://"+sHubUrl+":"+sHubPort+"/wd/hub"), dc);
			   
			else{ // this is for local run
				if(browser.equalsIgnoreCase("chrome")){
					System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
					driver = new ChromeDriver();
				}else{
					System.setProperty("webdriver.firefox.marionette", "./drivers/geckodriver.exe");
					driver = new FirefoxDriver();
					
				}
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(sUrl);

			primaryWindowHandle = driver.getWindowHandle();		
			reportStep("The browser:" + browser + " launched successfully", "PASS");
			reportStep("Running on :"+sUrl, "PASS");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("The browser:" + browser + " could not be launched");
//			reportStep("The browser:" + browser + " could not be launched", "FAIL");
//			testResult.setStatus(ITestResult.FAILURE);
//			takeScreenShotOnFailure(testResult);
//			takeScreenshot("BrowserLaunchFailed");
			
		}
	}
	
	public void invokeAppCommon(String browser,String Url) {
		invokeAppOthers(browser,false,Url);
	}
	
	public void invokeAppOthers(String browser, boolean bRemote,String oUrl) {
		try {

			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(browser);
			dc.setPlatform(Platform.WINDOWS);

			// this is for grid run
			if(bRemote)
				driver = new RemoteWebDriver(new URL("http://"+sHubUrl+":"+sHubPort+"/wd/hub"), dc);
			   
			else{ // this is for local run
				if(browser.equalsIgnoreCase("chrome")){
					System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
//					System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
					driver = new ChromeDriver();
				}else{
//					System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
//					System.setProperty("webdriver.gecko.driver", "C://Users//miburahi//Desktop//Python-Automation//FF-52-3//geckodriver.exe");
					driver = new FirefoxDriver();
				}
			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(oUrl);

			primaryWindowHandle = driver.getWindowHandle();		
			reportStep("The browser:" + browser + " launched successfully", "PASS");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("The browser:" + browser + " could not be launched");
			
		}
	}

	/**
	 * This method will enter the value to the text field using id attribute to locate
	 * 
	 * @param idValue - id of the webelement
	 * @param data - The data to be sent to the webelement
	 * @author MIBURAHI
	 * @throws IOException 
	 * @throws COSVisitorException 
	 */
	public void enterById(String idValue, String data) {
		try {
			driver.findElement(By.id(idValue)).clear();
			driver.findElement(By.id(idValue)).sendKeys(data);	
			reportStep("The data: "+data+" entered successfully in field :"+idValue, "PASS");
		} catch (NoSuchElementException e) {
			Assert.fail("The data: "+ data +" could not be entered in the field :"+idValue);
//			reportStep("The data: "+data+" could not be entered in the field :"+idValue, "FAIL");
//			testResult.setStatus(ITestResult.FAILURE);
//			takeScreenShotOnFailure(testResult);
//			takeScreenshot("ServerFailtoLaunch");
		} 
		catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
//			reportStep("Unknown exception occured while entering "+data+" in the field :"+idValue, "FAIL");
//			takeScreenshot("ServerFailtoLaunch");
//			takeScreenShotOnFailure(testResult);
//		}
	}

	/**
	 * This method will enter the value to the text field using name attribute to locate
	 * 
	 * @param nameValue - name of the webelement
	 * @param data - The data to be sent to the webelement
	 * @author MIBURAHI
	 * @throws IOException 
	 * @throws COSVisitorException 
	 */
	public void enterByName(String nameValue, String data) {
		try {
			driver.findElement(By.name(nameValue)).clear();
			driver.findElement(By.name(nameValue)).sendKeys(data);	
			reportStep("The data: "+data+" entered successfully in field :"+nameValue, "PASS");

		}
		catch (NoSuchElementException e) {
		    Assert.fail("The data: "+data+" could not be entered in the field :"+nameValue);
		} 
		catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
//			reportStep("Unknown exception occured while entering "+data+" in the field :"+nameValue, "FAIL");
		}

	/**
	 * This method will enter the value to the text field using name attribute to locate
	 * 
	 * @param xpathValue - xpathValue of the webelement
	 * @param data - The data to be sent to the webelement
	 * @throws IOException 
	 * @throws COSVisitorException 
	 */
	public void enterByXpath(String xpathValue, String data) {
		try {
			driver.findElement(By.xpath(xpathValue)).clear();
			driver.findElement(By.xpath(xpathValue)).sendKeys(data);	
			reportStep("The data: "+data+" entered successfully in field :"+xpathValue, "PASS");
		} catch (NoSuchElementException e) {
		   Assert.fail("The data: "+data+" could not be entered in the field :"+xpathValue);
		} catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			reportStep("Unknown exception occured while entering "+data+" in the field :"+xpathValue, "FAIL");
		}

	}

	/**
	 * This method will verify the title of the browser 
	 * @param title - The expected title of the browser
	 * @author MIBURAHI
	 */
	public boolean verifyTitle(String title){
		boolean bReturn = false;
		try{
			if (driver.getTitle().equalsIgnoreCase(title)){
				reportStep("The title of the page matches with the value :"+title, "PASS");
				bReturn = true;
			}else
				reportStep("The title of the page:"+driver.getTitle()+" did not match with the value :"+title, "SUCCESS");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return bReturn;
	}

	/**
	 * This method will verify the given text matches in the element text
	 * @param xpath - The locator of the object in xpath
	 * @param text  - The text to be verified
	 * @author MIBURAHI
	 */
	public void verifyTextByXpath(String xpath, String text){
		try {
			String sText = driver.findElementByXPath(xpath).getText();
			if (sText.equalsIgnoreCase(text)){
				reportStep("The text: "+sText+" matches with the value :"+text, "PASS");
			}else{
				Assert.fail("The text: "+sText+" did not match with the value :"+text);
//				reportStep("The text: "+sText+" did not match with the value :"+text, "FAIL");
			}
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This method will verify the given text is available in the element text
	 * @param xpath - The locator of the object in xpath
	 * @param text  - The text to be verified
	 * @author MIBURAHI
	 */
	public boolean verifyTextContainsByXpath(String xpath, String text){
		boolean bReturn = false;
		try{
			String sText = driver.findElementByXPath(xpath).getText();
			if (sText.contains(text)){
				reportStep("The text: "+sText+" contains the value :"+text, "PASS");
				bReturn = true;
			}else{
				Assert.fail("The text: "+sText+" did not contain the value :"+text);
//				reportStep("The text: "+sText+" did not contain the value :"+text, "FAIL");
				bReturn = false;
			}
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return bReturn;
	}

	/**
	 * This method will verify the given text is available in the element text
	 * @param id - The locator of the object in id
	 * @param text  - The text to be verified
	 * @author MIBURAHI
	 */
	public void verifyTextById(String id, String text) {
		try{
			String sText = driver.findElementById(id).getText();
			if (sText.equalsIgnoreCase(text)){
				reportStep("The text: "+sText+" matches with the value :"+text, "PASS");
			}else{
				Assert.fail("The text: "+sText+" did not match with the value :"+text);
//				reportStep("The text: "+sText+" did not match with the value :"+text, "FAIL");
			}
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This method will verify the given text is available in the element text
	 * @param id - The locator of the object in id
	 * @param text  - The text to be verified
	 * @author MIBURAHI
	 */
	public void verifyTextContainsById(String id, String text) {
		try{
			String sText = driver.findElementById(id).getText();
			if (sText.contains(text)){
				reportStep("The text: "+sText+" contains the value :"+text, "PASS");
			}else{
				Assert.fail("The text: "+sText+" did not contain the value :"+text);
//				reportStep("The text: "+sText+" did not contain the value :"+text, "FAIL");
			}
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This method will close all the browsers
	 * @author MIBURAHI
	 */
	public void quitBrowser() {
		try {
			driver.quit();
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * This method will click the element using id as locator
	 * @param id  The id (locator) of the element to be clicked
	 * @author MIBURAHI
	 */
	public void clickById(String id) {
		try{
			driver.findElement(By.id(id)).click();
			reportStep("The element with id: "+id+" is clicked.", "PASS");

		} catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			reportStep("The element with id: "+id+" could not be clicked.", "FAIL");
		}
		
	}

	/**
	 * This method will click the element using id as locator
	 * @param id  The id (locator) of the element to be clicked
	 * @author MIBURAHI
	 */
	public void clickByClassName(String classVal) {
		try{
			driver.findElement(By.className(classVal)).click();
			reportStep("The element with class Name: "+classVal+" is clicked.", "PASS");
		} catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	/**
	 * This method will click the element using name as locator
	 * @param name  The name (locator) of the element to be clicked
	 * @author MIBURAHI
	 */
	public void clickByName(String name) {
		try{
			driver.findElement(By.name(name)).click();
			reportStep("The element with name: "+name+" is clicked.", "PASS");
		} catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	/**
	 * This method will click the element using link name as locator
	 * @param name  The link name (locator) of the element to be clicked
	 * @author MIBURAHI
	 */
	public void clickByLink(String name) {
		try{
			driver.findElement(By.linkText(name)).click();
			reportStep("The element with link name: "+name+" is clicked.", "PASS");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This method will click the element using xpath as locator
	 * @param xpathVal  The xpath (locator) of the element to be clicked
	 * @author MIBURAHI
	 */
	public void clickByXpath(String xpathVal) {
		try{
			driver.findElement(By.xpath(xpathVal)).click();
			reportStep("The element : "+xpathVal+" is clicked.", "PASS");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * This method will mouse over on the element using xpath as locator
	 * @param xpathVal  The xpath (locator) of the element to be moused over
	 * @author MIBURAHI
	 */
	public void mouseOverByXpath(String xpathVal) {
		try{
			new Actions(driver).moveToElement(driver.findElement(By.xpath(xpathVal))).build().perform();
			reportStep("The mouse over by xpath : "+xpathVal+" is performed.", "PASS");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This method will mouse over on the element using link name as locator
	 * @param xpathVal  The link name (locator) of the element to be moused over
	 * @author MIBURAHI
	 */
	public void mouseOverByLinkText(String linkName) {
		try{
			new Actions(driver).moveToElement(driver.findElement(By.linkText(linkName))).build().perform();
			reportStep("The mouse over by link : "+linkName+" is performed.", "PASS");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
			
	}

	/**
	 * This method will return the text of the element using xpath as locator
	 * @param xpathVal  The xpath (locator) of the element
	 * @author MIBURAHI
	 */
	public String getTextByXpath(String xpathVal){
		String bReturn = "";
		try{
			return driver.findElement(By.xpath(xpathVal)).getText();
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		 
		return bReturn;
		
	}
	public String getTextByClass(String Class){
		String bReturn = "";
		try{
			return driver.findElement(By.className(Class)).getText();
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        return bReturn;
	}
	/**
	 * This method will return the text of the element using id as locator
	 * @param xpathVal  The id (locator) of the element
	 * @author MIBURAHI
	 */
	public String getTextById(String idVal) {
		String bReturn = "";
		try{
			return driver.findElementById(idVal).getText();
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return bReturn;
	}


	/**
	 * This method will select the drop down value using id as locator
	 * @param id The id (locator) of the drop down element
	 * @param value The value to be selected (visibletext) from the dropdown 
	 * @author MIBURAHI
	 */
	public void selectVisibileTextById(String id, String value) {
		try{
			new Select(driver.findElement(By.id(id))).selectByVisibleText(value);;
			reportStep("The element with id: "+id+" is selected with value :"+value, "PASS");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}



	public void selectVisibileTextByXPath(String xpath, String value) {
		try{
			new Select(driver.findElement(By.xpath(xpath))).selectByVisibleText(value);;
			reportStep("The element with xpath: "+xpath+" is selected with value :"+value, "PASS");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void selectIndexById(String id, String value) {
		try{
			new Select(driver.findElement(By.id(id))).selectByIndex(Integer.parseInt(value));;
			reportStep("The element with id: "+id+" is selected with index :"+value, "PASS");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void switchToParentWindow() {
		try {
			Set<String> winHandles = driver.getWindowHandles();
			for (String wHandle : winHandles) {
				driver.switchTo().window(wHandle);
				break;
			}
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void switchToLastWindow() {
		try {
			Set<String> winHandles = driver.getWindowHandles();
			for (String wHandle : winHandles) {
				driver.switchTo().window(wHandle);
			}
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			Assert.fail("The alert could not be found.");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	

	public String getAlertText() {		
		String text = null;
		try {
			driver.switchTo().alert().dismiss();
		} catch (NoAlertPresentException e) {
			Assert.fail("The alert could not be found.");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return text;

	}

	public void dismissAlert() {
		try {
			driver.switchTo().alert().dismiss();
		} catch (NoAlertPresentException e) {
			Assert.fail("The alert could not be found.");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void takeSnap(){
//		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
//		String testcaseID = ds.getID();
//		String testcaseName=ds.getName();
//		try {
//			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+testcaseName+testcaseID+".jpg"));
//		} catch (WebDriverException e) {
//			reportStep("The browser has been closed.", "FAIL");
//		} catch (IOException e) {
//			reportStep("The snapshot could not be taken", "WARN");
//		}
	}
	
	/**
	 * This method will verify the given text is available in the element text
	 * @param id - The locator of the object in class
	 * @param text  - The text to be verified
	 * @author Sundar - JuztJellyGuava
	 */
	public boolean verifyTextContainsByClass(String classVal, String text) {
		boolean bReturn = false;
		try{
			String sText = driver.findElementByClassName(classVal).getText();
			if (sText.contains(text)){
				reportStep("The text: "+sText+" contains the value :"+text, "PASS");
				bReturn=true;
			}else{
				Assert.fail("The text: "+sText+" did not contain the value :"+text);
				bReturn=false;
			}
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return bReturn;
	}
	
	public boolean verifyTextContainsByBooXpath(String XpathVal, String text) {
		boolean bReturn = false;
		try{
			String sText = driver.findElementByXPath(XpathVal).getText();
			if (sText.contains(text)){
				reportStep("The text: "+sText+" contains the value :"+text, "PASS");
				bReturn=true;
			}else{
				Assert.fail("The text: "+sText+" did not contain the value :"+text);
				bReturn=false;
			}
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return bReturn;
	}
	
	/**
	 * This method will click the element using xpath as locator (without taking any snapshot)
	 * @param xpathVal  The xpath (locator) of the element to be clicked
	 * @author Sundar - JuztJellyGuava
	 */
	public void clickByXpathNoSnap(String xpathVal) {
		try{
			driver.findElement(By.xpath(xpathVal)).click();
		} catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * This method will click the element using link name as locator(No Snap)
	 * @param name  The link name (locator) of the element to be clicked
	 * @author Sundar - JuztJellyGuava
	 */
	public void clickByLinkNoSnap(String name) {
		try{
			driver.findElement(By.linkText(name)).click();
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * This method will drag and drop the element using id
	 * @param name  The Id (locator) of the element to be clicked
	 * @author Sundar - JuztJellyGuava
	 */
	public void dragAnddrop(String id1, String id2) {
		try {
			new Actions(driver).dragAndDrop(driver.findElement(By.id(id1)), driver.findElement(By.id(id1))).build().perform();
			reportStep("The Drag and Drop by Id: " +id1+" and "+id2+ " is performed", "PASS");
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * This method will right click the element using link text
	 * @param name  The link text (locator) of the element to be clicked
	 * @author Sundar - JuztJellyGuava
	 */
	public void rightClick(String linkName, String id2) {
		try {
			new Actions(driver).contextClick(driver.findElement(By.linkText(linkName))).perform();
			reportStep("The Right click by Link Text: " +linkName+ " is performed", "PASS");
		} catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public boolean WarpopEnabledXapth(String Xpath){
		boolean bReturn = false;
		try{
			if(driver.findElementsByXPath(Xpath).size()!= 0){
				driver.findElementByXPath(Xpath).click();
				reportStep("Home page warning Pop up is displayed and closed successfully", "PASS");
				bReturn = true;
			}else{
				reportStep("Home page warning Pop up is not displayed", "PASS");
			}
		}catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return bReturn;
	}

	@Override
	public void clickByXpathwithuinput(String Polcat) {
		// TODO Auto-generated method stub
		try{
		    String xpathValue1= "//span[text()='" +Polcat+ "']/ancestor::span/preceding::span[2]";
		    scrollintoViewXpath(xpathValue1);
			driver.findElement(By.xpath(xpathValue1)).click();
			reportStep("The element : "+xpathValue1+" is clicked.", "PASS");
		} catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public void clickByXpathwithuinput1(String Policy) {
		// TODO Auto-generated method stub
		try{
		     String xpathValue1= "//span[text()='"+Policy+"']/preceding-sibling::div[2]";
		     scrollintoViewXpath(xpathValue1);
			driver.findElement(By.xpath(xpathValue1)).click();
			reportStep("The element : "+xpathValue1+" is clicked.", "PASS");
		} catch (Exception e) {
			try {
				throw new ComplianceAutoException(e.getMessage());
			} catch (ComplianceAutoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
//		public void clickByXpathJobTable(String JobCat, String JobMod) {
//			// TODO Auto-generated method stub
//			try{
////			   if (verifyTextContainsByClass(prop.getProperty("ToJob.JobCate.ClassName"), JobMod)){
////				   System.out.println(""+JobMod+"Already Navigated");
////			   }
////			   else{
//			    String xpathValueJobCat= "//span[text()='"+JobCat+"']";
//				driver.findElement(By.xpath(xpathValueJobCat)).click();
//				WebDriverWait wait = new WebDriverWait(driver, 10);
//				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(prop.getProperty("ToJob.JobCate.ClassName"))));
//				if (verifyTextContainsByClass(prop.getProperty("ToJob.JobCate.ClassName"), JobCat)){
//				     System.out.println(""+JobCat+" Navigated Successfully");
//					 String xpathValueModuleJob= "//span[text()='"+JobMod+"']";
//				     driver.findElement(By.xpath(xpathValueModuleJob)).click();
//					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(prop.getProperty("ToJob.JobCate.ClassName"))));
//					 if(verifyTextContainsByClass(prop.getProperty("ToJob.JobCate.ClassName"), JobMod)){
//			         reportStep("The element : "+xpathValueJobCat+" and "+xpathValueModuleJob+" are clicked.", "PASS");
//					 }
//					 else{
//						 Assert.fail("The element : "+xpathValueJobCat+" and "+xpathValueModuleJob+"  are not clicked.");
//					 }
//				}
//					 else{
//						 Assert.fail("The element : "+xpathValueJobCat+" is not clicked."); 
//					 }
////			} 
//			}catch (Exception e) {
//				try {
//					throw new ComplianceAutoException(e.getMessage());
//				} catch (ComplianceAutoException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//
//}

	   @Override
		public String UniqueTitle(String TitleName){
			
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		 		 Date date = new Date();
		 		 String random_number = dateFormat.format(date);
		 		 String TName =TitleName+random_number;
		 		return TName;

			
		}

	@Override
	public void dropdownXpath(String SelecId, String SelectValue) {
		// TODO Auto-generated method stub
	   driver.findElement(By.id(SelecId)).click();
	   try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 	   String UserInput="//td[@class='dijitReset dijitMenuItemLabel' and text()='"+SelectValue+"']";
 	   driver.findElementByXPath(UserInput).click();
	}

	@Override
	public void dropdownXPathIdDM(String SelecId, String SelectValue) {
		// TODO Auto-generated method stub
		
		  driver.findElement(By.id(SelecId)).click();
		   try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	   String UserInput="(//td[@class='dijitReset dijitMenuItemLabel' and text()='"+SelectValue+"'])[2]";
	 	   driver.findElementByXPath(UserInput).click();
		
	}

	@Override
	public void scrollintoViewId(String Id) {
		// TODO Auto-generated method stub
		JavascriptExecutor je = (JavascriptExecutor)driver;
		
		WebElement ele =driver.findElementById(Id);
		
		je.executeScript("arguments[0].scrollIntoView(true);", ele);
		
	}
	
	@Override
	public void scrollintoViewXpath(String Xpath) {
		// TODO Auto-generated method stub
		JavascriptExecutor je = (JavascriptExecutor)driver;
		
		WebElement ele =driver.findElementByXPath(Xpath);
		
		je.executeScript("arguments[0].scrollIntoView(true);", ele);
		
	}
	
	@Override
	public String HashValue (String Heading, String Child)throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(location);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName(Heading);
		Node nNode = nList.item(0);
		Element eElement = (Element) nNode;
		String Value= eElement.getElementsByTagName(Child).item(0).getTextContent();
		return Value;
}

}

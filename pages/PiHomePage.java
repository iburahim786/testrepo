package com.cisco.ui.test.compliance.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.test.tea.log.TestLogger;
import com.cisco.ui.test.compliance.wrappers.utils.OpentapsWrappers;
import com.cisco.ui.test.compliance.wrappers.utils.PageNavigation;

public class PiHomePage extends OpentapsWrappers {
	
	public PiHomePage(RemoteWebDriver driver, Dataset ds) {
		this.driver = driver;
		this.ds = ds;
	}
//	public PiHomePage HandleWarningPopUp() throws InterruptedException{
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("FromHome.WarPopUp.Xpath"))));
//		WarpopEnabledXapth(prop.getProperty("FromHome.WarPopUp.Xpath"));
//		Thread.sleep(3000);
//		clickByXpath(prop.getProperty("FromHome.Lowspace.Xpath"));
//		if(driver.findElementByXPath("//span[text()='OK'])[1]").isDisplayed()){
//			  clickByXpath(prop.getProperty("FromHome.WarPopUp.Xpath"));
//			  System.out.println("Licence warning Pop up is displayed and closed successfully");
//			  }
//			  else
//			  {
//				  System.out.println("Licence warning Pop up is not displayed");
//		  }
//		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("FromHome.WarPopUp.Xpath"))));
//		 WarpopEnabledXapth(prop.getProperty("FromHome.WarPopUp.Xpath"));

//		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("FromHome.DontShowPopUp.Xpath"))));
//		 WarpopEnabledXapth(prop.getProperty("FromHome.DontShowPopUp.Xpath"));
//         return this;
//	}
	
	public PiHomePage clearTelemetryDialog(final WebDriver driver) {
		try {
			if (driver.findElements(By.id("telemetryDialog")).size() > 0 && driver.findElement(By.id("telemetryDialog")).isDisplayed()) {
				final Actions builder = new Actions(driver);
				builder.sendKeys(Keys.ESCAPE).perform();
				TestLogger.debug("Cleared Telemetry dialog..");
			}
		} catch (final Exception e) {
			TestLogger.debug(e.getMessage());
		}
		return this;

	}

	public PiHomePage clearLicenseAlert(final WebDriver driver) {
		try {
			if (driver.findElements(By.id("telemetryDialog")).size() > 0 && driver.findElement(By.id("telemetryDialog")).isDisplayed()) {
				final Actions builder = new Actions(driver);
				builder.sendKeys(Keys.ESCAPE).perform();
				TestLogger.debug("Cleared Telemetry dialog..");
			}
			if (driver.findElements(By.id("warningLoginDialog")).size() > 0 && driver.findElement(By.id("warningLoginDialog")).isDisplayed()) {
				/*final Actions builder = new Actions(driver);
				builder.sendKeys(Keys.ESCAPE).perform();*/
				//Clicking ok button instead of pressing Escape key of license warning
				driver.findElement(By.xpath("//*[@id=\"xwt_widget_form_TextButtonGroup_2\"]/span[2]/span")).click();
				TestLogger.debug("Cleared License Warning alert..");
			}
		} catch (final Exception e) {
			TestLogger.debug(e.getMessage());
		}
		return this;

	}
	
	public PiHomePage clearBackupFailsDialog(final WebDriver driver) {
		try {
			if (driver.findElements(By.id("telemetryDialog")).size() > 0 && driver.findElement(By.id("telemetryDialog")).isDisplayed()) {
				final Actions builder = new Actions(driver);
				builder.sendKeys(Keys.ESCAPE).perform();
				TestLogger.debug("Cleared Telemetry dialog..");
			}
			if (driver.findElements(By.id("warningLoginDialog")).size() > 0 && driver.findElement(By.id("warningLoginDialog")).isDisplayed()) {
			/*	final Actions builder = new Actions(driver);
				builder.sendKeys(Keys.ESCAPE).perform();*/
				//Clicking ok button instead of pressing Escape key of license warning
				driver.findElement(By.xpath("//*[@id=\"xwt_widget_form_TextButtonGroup_2\"]/span[2]/span")).click();
				TestLogger.debug("Cleared License Warning alert..");
			}
			if (driver.findElements(By.id("backupFailsDialog")).size() > 0 && driver.findElement(By.id("backupFailsDialog")).isDisplayed()) {
				final Actions builder = new Actions(driver);
				builder.sendKeys(Keys.ESCAPE).perform();
				TestLogger.debug("Cleared BackupFailsDialog Warning alert..");
			}
		} catch (final Exception e) {
			TestLogger.debug(e.getMessage());
		}
		return this;
	}
	
	public PiHomePage clearTutorialWindow(final WebDriver driver) {
		try {
			if (driver.findElements(By.id("telemetryDialog")).size() > 0 && driver.findElement(By.id("telemetryDialog")).isDisplayed()) {
				final Actions builder = new Actions(driver);
				builder.sendKeys(Keys.ESCAPE).perform();
				TestLogger.debug("Cleared Telemetry dialog..");
			}
			if (driver.findElements(By.id("warningLoginDialog")).size() > 0 && driver.findElement(By.id("warningLoginDialog")).isDisplayed()) {
				/*final Actions builder = new Actions(driver);
				builder.sendKeys(Keys.ESCAPE).perform();*/
				//Clicking ok button instead of pressing Escape key of license warning
				driver.findElement(By.xpath("//*[@id=\"xwt_widget_form_TextButtonGroup_2\"]/span[2]/span")).click();
				TestLogger.debug("Cleared License Warning alert..");
			}
			if (driver.findElements(By.id("backupFailsDialog")).size() > 0 && driver.findElement(By.id("backupFailsDialog")).isDisplayed()) {
				final Actions builder = new Actions(driver);
				builder.sendKeys(Keys.ESCAPE).perform();
				TestLogger.debug("Cleared BackupFailsDialog Warning alert..");
			}
			if (driver.findElements(By.cssSelector(".shellTutorialOverlay")).size() > 0 && driver.findElement(By.cssSelector(".shellTutorialOverlay")).isDisplayed()) {
				TestLogger.debug("Clearing Tutorial window");
				driver.findElement(By.cssSelector(".shellTutorialOverlay")).findElement(By.cssSelector(".dijitReset.dijitInline.dijitButtonNode")).click();
				TestLogger.debug("Cleared Tutorial window");
			}
		} catch (final Exception e) {
			TestLogger.debug(e.getMessage());
		}
		return this;
	}
	
	public PiHomePage waitUntilLoginCompleted(final WebDriver driver) throws InterruptedException {
		boolean stillLoading = true;
		int timout = 0;
		while (stillLoading) {
			timout++;
			TestLogger.debug("Page loading.. Waiting for page elements..");
			Thread.sleep(1000);
			if (driver.findElements(By.id("warningLoginDialog")).size() > 0) {
				stillLoading = false;
				TestLogger.debug("Page Loading completed and licence waring present");
			}
			if (driver.findElements(By.id("telemetryDialog")).size() > 0) {
				stillLoading = false;
				TestLogger.debug("Page Loading completed and licence Agreement dialog present");
			}
			if (driver.findElements(By.linkText("searchDialog")).size() > 0) {
				stillLoading = false;
				TestLogger.debug("Page Loading completed licence waring not present");
			}
			TestLogger.debug("Searching for Okay got it button");
			if(driver.findElements(By.cssSelector(".shellTutorialOverlay")).size() > 0 && driver.findElement(By.cssSelector(".shellTutorialOverlay")).isDisplayed()) {
				stillLoading = false;
				TestLogger.debug("Page Loading completed and Okay got it button dialog present.");
			}
			if (timout == 30) {
				TestLogger.debug("Expected elements not found,So doing Page refresh at secs :  " + timout);
				driver.navigate().refresh();
			}
			if (timout > 60) {
				TestLogger.debug("Page not loading in 60 seconds.. time out");
				break;
			}
		}
		return this;
	}

}

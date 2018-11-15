package com.cisco.ui.test.compliance.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.ui.test.compliance.wrappers.utils.OpentapsWrappers;

public class PrimeLogin extends OpentapsWrappers{
	
	public PrimeLogin(RemoteWebDriver driver,Dataset ds){
		this.driver = driver;
		this.ds = ds;
	}
		
    public PiHomePage PiLogin(String Uname, String Passwd) throws InterruptedException{
    	enterById(prop.getProperty("PrimeLogin.EnterUname.Id"), Uname);
    	enterById(prop.getProperty("PrimeLogin.EnterPass.Id"), Passwd);
    	Thread.sleep(1000);
    	clickById(prop.getProperty("PrimeLogin.ClickLogin.Id"));
    	return new PiHomePage(driver, ds);
    
	}

}

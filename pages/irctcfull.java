 package com.cisco.ui.test.compliance.pages;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class irctcfull {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
//	    ChromeDriver driver=new ChromeDriver();
		System.setProperty("webdriver.gecko.driver", "C://Users//miburahi//Desktop//Python-Automation//FF-52-3");
		FirefoxDriver driver= new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("https://www.google.com");
		System.out.println("printed");
	}

}

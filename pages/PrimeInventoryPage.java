package com.cisco.ui.test.compliance.pages;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentTest;

import junit.framework.Assert;

import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.test.tea.log.TestLogger;
import com.cisco.ui.test.compliance.wrappers.utils.IPAddressValidator;
import com.cisco.ui.test.compliance.wrappers.utils.OpentapsWrappers;
import com.cisco.ui.test.compliance.wrappers.utils.PageNavigation;

public class PrimeInventoryPage extends OpentapsWrappers {
	
	public PrimeInventoryPage (RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		this.driver = driver;
		this.test = test;
//		PageNavigation.NavigateToPage("Inventory","Network Devices");
		clickByXpath(prop.getProperty("FromHome.ClickMenu.Xpath"));
		WebElement config = driver.findElementByLinkText("Configuration");
		Actions action= new Actions(driver);
		action.moveToElement(config).build().perform();
		Thread.sleep(3000);
		clickById("wcsuishell_node_id_configure_wired_wireless_devices");
	} 
	
	public PrimeInventoryPage DeviceStatusCheck(String Did) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
		IpDnsCoumn_add_remove();
		Thread.sleep(5000);
		enterByXpath(prop.getProperty("ToInven.IpDnsColumn.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
		Thread.sleep(7000);
		String Vstate =driver.findElementByClassName(prop.getProperty("ToInven.IpDnsVerify.Class")).getText();
		Thread.sleep(5000);
		if(Vstate.contains(deviceprop.getProperty(Did+".Ip_Dns"))) {
			String InvenStatus= driver.findElementByXPath(prop.getProperty("ToInven.InvenStatus.Xpath")).getText();
			DeviceInvenStatus(InvenStatus);
		}
		 else{
			System.out.println("Device already added using DNS name, Please check");	
			}
		return this;
	}
	
	public PrimeInventoryPage NoDeviceAdd(String Did) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
		System.out.println(deviceprop.getProperty(""+Did+".Ip_Dns"));
		IpDnsCoumn_add_remove();
		Thread.sleep(5000);
		enterByXpath(prop.getProperty("ToInven.IpDnsColumn.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
		Thread.sleep(7000);
		String Vstate =driver.findElementByClassName(prop.getProperty("ToInven.IpDnsVerify.Class")).getText();
		if (Vstate.contains("No data is available")){
			DeviceAdd(Did);
			Vstate =driver.findElementByClassName(prop.getProperty("ToInven.IpDnsVerify.Class")).getText();
			if(!Vstate.contains("No data is available")){
			String InvenStatus= driver.findElementByXPath(prop.getProperty("ToInven.InvenStatus.Xpath")).getText();
			DeviceInvenStatus(InvenStatus);
		    }
		}
		else {
			DeviceStatusCheck(Did);
		}
		return this;
	}
	
	public PrimeInventoryPage SynchronizingStatus(String IStatus) throws InterruptedException{
		System.out.println("Device in "+IStatus+", Need to wait for some time to device get into Manage");
		int i=0;
		while (IStatus.equalsIgnoreCase("Synchronizing")){
			clickByXpath(prop.getProperty("ToInven.InvenTableRef.Xpath"));
			Thread.sleep(5000);
			i=i+1;
			IStatus=driver.findElementByXPath(prop.getProperty("ToInven.InvenStatus.Xpath")).getText();
			System.out.println("Device retries:" +i+ " and Device in "+IStatus);
			Thread.sleep(5000);
			if(i>=40){
				Assert.fail("FAILED: Device is not get into Managed state, Since device is in "+IStatus+ "state");
				break;
			}
		}
		IStatus=driver.findElementByXPath(prop.getProperty("ToInven.InvenStatus.Xpath")).getText();
		if (IStatus.equalsIgnoreCase("Completed")) {
			System.out.println("Device is Added Successfully and In Completed state");
		}
		else{
			System.out.println("Currently device in: "+IStatus+ ",Please check the device Credential/Server");
			Assert.fail("FAILED: Device is not get into Managed state, Since device is in "+IStatus+ "state");
		}
		return this;
	}
	
	public PrimeInventoryPage DeviceAddInit(String Aistate) throws InterruptedException{
		System.out.println("Device in Add Initiate, Need to wait for some time to device getinto Manage");
		int i=0;
		while (Aistate.equalsIgnoreCase("Add Initiated")){
			SyncDevice();
			i=i+1;
			Aistate =driver.findElementByXPath(prop.getProperty("ToInven.InvenStatus.Xpath")).getText();
			System.out.println("Device retries:" +i+ "Device in "+Aistate);
			Thread.sleep(5000);
			if(Aistate.equalsIgnoreCase("Synchronizing")){
				SynchronizingStatus(Aistate);
				break;
			}
			else if (i>=60){
				System.out.println("Currently device in: "+Aistate+ ",Please check the device Credential/Server");
				break;
			}
		}
		return this;
	}
	
	public PrimeInventoryPage DeviceAdd(String Did) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
		clickByXpath(prop.getProperty("ToInven.AddButton.Xpath"));
		clickByXpath(prop.getProperty("ToInven.DeviceAdd.Xpath"));
		Thread.sleep(3000);
		System.out.println("Ipaddress/DNS is : " + deviceprop.getProperty(""+Did+".Ip_Dns"));
	   if(IPAddressValidator.validateIpaddress(deviceprop.getProperty(""+Did+".Ip_Dns"))){
		enterByXpath(prop.getProperty("ToInven.IpAddress.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
	   }
	   else {
		   clickById(prop.getProperty("ToInven.DeviceAddDnsSelect.Id"));
		   Thread.sleep(2000);
		   enterById(prop.getProperty("ToInven.DeviceDns.Id"), deviceprop.getProperty(""+Did+".Ip_Dns"));
	   }
		Thread.sleep(3000);
		clickById(prop.getProperty("ToInven.SnmpColumn.Id"));
		Thread.sleep(3000);
		enterById(prop.getProperty("ToInven.Snmpread.Id"),deviceprop.getProperty(""+Did+".snmpro"));
		enterById(prop.getProperty("ToInven.SnmpreadConf.Id"),deviceprop.getProperty(""+Did+".snmpro"));
		enterById(prop.getProperty("ToInven.Snmprw.Id"),deviceprop.getProperty(""+Did+".snmprw"));
		enterById(prop.getProperty("ToInven.Snmprwconf.Id"),deviceprop.getProperty(""+Did+".snmprw"));
		Thread.sleep(3000);
		clickById(prop.getProperty("ToInven.TelnetSSHColumn.Id"));
		Thread.sleep(3000);
		enterById(prop.getProperty("ToInven.DeviceUsername.Id"),deviceprop.getProperty(""+Did+".Dusername"));
		enterById(prop.getProperty("ToInven.DevicePasswd.Id"),deviceprop.getProperty(""+Did+".DPasswd"));
		enterById(prop.getProperty("ToInven.DevicePasswdConf.Id"),deviceprop.getProperty(""+Did+".DPasswd"));
		enterById(prop.getProperty("ToInven.DeviceEpasswd.Id"),deviceprop.getProperty(""+Did+".DEPasswd"));
		enterById(prop.getProperty("ToInven.DeviceEpasswdConf.Id"),deviceprop.getProperty(""+Did+".DEPasswd"));
		Thread.sleep(3000);
		clickById(prop.getProperty("ToInven.DeviceAdd.Id"));
		Thread.sleep(5000);
		enterByXpath(prop.getProperty("ToInven.IpDnsColumn.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
		return this;
	}
	
	public PrimeInventoryPage SyncDevice() throws InterruptedException{
		clickByClassName(prop.getProperty("ToInven.DeviceSelect.Class"));
		Thread.sleep(2000);
		clickById(prop.getProperty("ToInven.SyncButton.Id"));
		Thread.sleep(2000);
		clickByXpath(prop.getProperty("ToInven.SyncYes.Xpath"));
		Thread.sleep(2000);
		clickByXpath(prop.getProperty("ToInven.InvenTableRef.Xpath"));
		Thread.sleep(5000);
		SynchronizingStatus("Synchronizing");
		return this;
	}
	
	public PrimeInventoryPage SyncCompletedDevice(String Did) throws Exception{
		IpDnsCoumn_add_remove();
		Thread.sleep(5000);
		System.out.println("Ip address is : "+deviceprop.getProperty(""+Did+".Ip_Dns"));
		enterByXpath(prop.getProperty("ToInven.IpDnsColumn.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
		Thread.sleep(7000);
		String Vstate =driver.findElementByClassName(prop.getProperty("ToInven.IpDnsVerify.Class")).getText();
		Thread.sleep(5000);
		if(Vstate.contains(deviceprop.getProperty(Did+".Ip_Dns"))) {
			String InvenStatus= driver.findElementByXPath(prop.getProperty("ToInven.InvenStatus.Xpath")).getText();
			if (InvenStatus.contains("Completed")){
				SyncDevice();
			}
			else if(InvenStatus.contains("Synchronizing")) {
				TestLogger.info("Device is currently synchronizing state , Please wait for one min");
				Thread.sleep(60000);
				SyncCompletedDevice(Did);
			}
		}
		return this;
		
	}
	
	public PrimeInventoryPage IpDnsCoumn_add_remove() throws InterruptedException{
		Thread.sleep(10000);
		String InvenHead = driver.findElementByClassName(prop.getProperty("ToInven.InVenTableHeader.Class")).getText();
		if(!InvenHead.contains("IP Address/DNS")){
		clickByXpath(prop.getProperty("ToInven.Setting.Xpath"));
		WebElement Columns= driver.findElementByXPath(prop.getProperty("ToInven.Column.Xpath"));
		Actions MosColumn= new Actions(driver);
		MosColumn.moveToElement(Columns).build().perform();
		Thread.sleep(3000);
		clickByXpath(prop.getProperty("ToInven.IpDnsSelect.Xpath"));
		Thread.sleep(3000);
		clickByXpath(prop.getProperty("ToInven.ColumnClose.Xpath"));
		}
		else{
			System.out.println("Already IP Address/DNS column added" );
		}
		return this;
	}
	
	public PrimeInventoryPage DeviceInvenStatus(String CInvenStatus) throws InterruptedException{
		System.out.println("Currently device in: "+CInvenStatus);
		if (CInvenStatus.equalsIgnoreCase("Completed")) 
			System.out.println("Device is Added and In Completed state");
		
		else if (CInvenStatus.equalsIgnoreCase("Synchronizing")){
			SynchronizingStatus(CInvenStatus);
		}
		else if (CInvenStatus.equalsIgnoreCase("Add Initiated")){
			DeviceAddInit(CInvenStatus);
		}
		else{
			SyncDevice();
			CInvenStatus=driver.findElementByXPath(prop.getProperty("ToInven.InvenStatus.Xpath")).getText();
			if(!CInvenStatus.equalsIgnoreCase("Completed")){
			System.out.println("Currently device in: "+CInvenStatus+ ",Please check the device Credential/Server");
			}
		}
		return this;
	}
	public PrimeInventoryPage DelUnManageDevice(String Did) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
		IpDnsCoumn_add_remove();
		Thread.sleep(5000);
		enterByXpath(prop.getProperty("ToInven.IpDnsColumn.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
		Thread.sleep(7000);
		String Vstate =driver.findElementByClassName(prop.getProperty("ToInven.IpDnsVerify.Class")).getText();
		Thread.sleep(5000);
		System.out.println(Vstate);
		if(Vstate.contains(deviceprop.getProperty(""+Did+".Ip_Dns"))) {
			String InvenStatus= driver.findElementByXPath(prop.getProperty("ToInven.InvenStatus.Xpath")).getText();
			if(!InvenStatus.equalsIgnoreCase("Completed")){
				System.out.println("Device in "+InvenStatus+ ", Hence Going to delete the device.");
				DeleteFuntion(Did);
			}
			else{
				System.out.println("Device already in Completed state, Hence No Need to delete the device");
			}
		}
		return this;
	}

    public PrimeInventoryPage DeleteFuntion(String Did) throws InterruptedException, ParserConfigurationException, SAXException, IOException{
    	enterByXpath(prop.getProperty("ToInven.IpDnsColumn.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
    	Thread.sleep(5000);
		clickByClassName(prop.getProperty("ToInven.DeviceSelect.Class"));
		Thread.sleep(3000);
		clickById(prop.getProperty("ToInven.DeviceDelete.Id"));
		Thread.sleep(3000);
		clickByXpath(prop.getProperty("ToInven.DeviceDeleteYes.Xpath"));
		Thread.sleep(3000);
		if(driver.findElementsByClassName(prop.getProperty("ToInven.DeviceAPpopUpMsg.Class")).size()!= 0){
			String PopMsg =driver.findElementByClassName(prop.getProperty("ToInven.DeviceAPpopUpMsg.Class")).getText();
			System.out.println(PopMsg);
			clickByXpath(prop.getProperty("ToInven.DeviceDeleteYes.Xpath"));
		}
		else{
			System.out.println("Corresponding device is not a NGWLC/WLC device, So no need to Delete Associated APs");
		}
		Thread.sleep(3000);
		String Dstate =driver.findElementByClassName(prop.getProperty("ToInven.IpDnsVerify.Class")).getText();
		while(!Dstate.contains("No data is available")){
			int i=0;
			clickByXpath(prop.getProperty("ToInven.InvenTableRef.Xpath"));
			Thread.sleep(5000);
			String DStatus=driver.findElementByXPath(prop.getProperty("ToInven.InvenStatus.Xpath")).getText();
			System.out.println("Device retries:" +i+ " and Device in "+DStatus);
			if (i>=20){
				break;
			}
			i=i+1;
		}
		String Dstate1 =driver.findElementByClassName(prop.getProperty("ToInven.IpDnsVerify.Class")).getText();
		System.out.println("Currently table has "+Dstate1+ ", Hence the Device deleted Successfully");
		return this;
	}
    
    public PrimeInventoryPage DeviceBulkImport(Dataset ds) throws Exception{
    	Thread.sleep(10000);
    	String location= "C:\\Compliance\\InputFile\\";
    	clickByXpath(prop.getProperty("ToInven.AddButton.Xpath"));
    	Thread.sleep(3000);
    	clickByXpath(prop.getProperty("ToInven.DeviceAddBulkImport.Xpath"));
		Thread.sleep(3000);
		WebElement fileInput = driver.findElement(By.name(prop.getProperty("ToInven.FileInput.Name")));
		fileInput.sendKeys(location+ds.getAsString("DeviceCsvFileName"));
        Thread.sleep(3000);
        clickByXpath(prop.getProperty("ToInven.DeviceFileImport.Xpath"));
        Thread.sleep(2000);
//        String Status= PageNavigation.toasterMsgVerification("successfully created");
//        System.out.println("Device import Job verified Successfully ?: "+ Status);
//        if (Status.equalsIgnoreCase("no"))
//        Assert.fail("FAILED: Device Import verification Failed");
    	return this;
    }
    
    public PrimeInventoryPage DeviceSearch(String Did) throws Exception{
    	System.out.println(deviceprop.getProperty(""+Did+".Ip_Dns"));
		IpDnsCoumn_add_remove();
		Thread.sleep(5000);
		enterByXpath(prop.getProperty("ToInven.IpDnsColumn.Xpath"),deviceprop.getProperty(""+Did+".Ip_Dns"));
		Thread.sleep(7000);
		String Vstate =driver.findElementByClassName(prop.getProperty("ToInven.IpDnsVerify.Class")).getText();
		int i=0;
		while (Vstate.contains("No data is available")){
			clickByXpath(prop.getProperty("ToInven.InvenTableRef.Xpath"));
			Thread.sleep(5000);
			String VstateAfterRefresh =driver.findElementByClassName(prop.getProperty("ToInven.IpDnsVerify.Class")).getText();
			System.out.println("Device retries:" +i+ " and Table Value is:  "+VstateAfterRefresh);
			Thread.sleep(5000);
			if(i>=40){
				Assert.fail("FAILED: Device is not present, Since table value is "+VstateAfterRefresh);
				break;
			}
			Vstate=VstateAfterRefresh;
			i=i+1;
		}
    	return this;
    }
}
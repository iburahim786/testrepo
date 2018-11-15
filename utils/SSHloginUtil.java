package com.cisco.ui.test.compliance.wrappers.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.cisco.test.tea.core.dataprovider.Dataset;
import com.cisco.test.tea.io.IOConnection;
import com.cisco.test.tea.io.SSHConnection;
import com.cisco.test.tea.io.utilities.IOUtilities;
import com.cisco.test.tea.log.TestLogger;

import junit.framework.Assert;



public class SSHloginUtil extends OpentapsWrappers {
	
	public static String DataBasePassword() throws InterruptedException {
		String[] pwdCommand = {"service iptables stop","/opt/CSCOlumos/bin/getDatabaseParams.sh"};
        String responsePwd = SSHloginUtil.executeCommand(ipaddress,SshUsername, SshPassword,pwdCommand);
        TestLogger.debug("Step : password is :"+responsePwd);
		return responsePwd;
	}
	
	public static String executeCommand(final String ipAddress, final String userName, final String password, final String[] sshcommand) throws InterruptedException{
		TestLogger.debug("Command going to execute on server : " + Arrays.toString(sshcommand));
		String output = "";
		final long startTime = System.currentTimeMillis();
//		try {
//			final IOConnection connection = IOConnectionUtil.getInstance(ipAddress, userName, password);
//			output = IOUtilities.runOnConnection(connection, sshcommand, 40 * 60 * 1000); // 40 minutes
//			IOConnectionUtil.closeConnection();
//		} catch (final Exception e) {
//			TestLogger.debug(e.getMessage() + " error came.. waiting for 10 mins.. then will proceed..");
//			Thread.sleep(10 * 60 * 1000);
			try {
				final IOConnection connection = IOConnectionUtil.getInstance(ipAddress, userName, password);
				for (final String command : sshcommand) {
					output = IOUtilities.runOnConnection(connection, new String[] { command },2*60*1000 ); // 2 minutes
					Thread.sleep(3000);
				}
				IOConnectionUtil.closeConnection();
			} catch (final Exception e2) {
				e2.printStackTrace();
			}
//		}
		final long presentTime = System.currentTimeMillis();
		TestLogger.info("Time taken by executeCommand in seconds : " +(presentTime - startTime)/1000+", approximately in minutes : "+(presentTime - startTime)/(1*60*1000));
		return output;
		}
//	}
	
	public static String connectToDataBaseAndExecuteQuery(String ipAddress, String query, String pwd) throws Exception{
        Connection con = null;
        Statement stmt = null;
        ResultSet rSet = null;
        String result = null;
        try {
              Class.forName("oracle.jdbc.driver.OracleDriver");
              TestLogger.debug("Step : Driver Loaded and going to establish connection.");
              con = DriverManager.getConnection("jdbc:oracle:thin:@//"+ipAddress+":1522/wcs", "wcsdba", pwd);
              TestLogger.debug("Step : Creating the stmt.");
              stmt = con.createStatement();
              TestLogger.debug("Step : about to execute the query");
              rSet = stmt.executeQuery(query);
              while(rSet.next()){
                    result = rSet.getString(1);
              }
        } catch (Exception e) {
              TestLogger.debug("Exception while executing the databse query : ");
              e.printStackTrace();
        }finally{
              try {
                    stmt.close();
                    con.close();
              } catch (SQLException e) {
                    TestLogger.debug("Exception while closing the connection : ");
                    e.printStackTrace();
              }
        }
        return result;
  }
	public static void WlcCommandValidation() throws Exception{
		
		String DataBasePwd=DataBasePassword();
		String query="select count(*) from ComplianceShowCommand where owningEntityId like '%10.197.72.89%'";
		String WlcCmdResults = connectToDataBaseAndExecuteQuery(ipaddress,query,DataBasePwd);
		System.out.println(WlcCmdResults);
		if(WlcCmdResults=="16"){
			System.out.println("Wlc show commands are working fine");
		}
	}
	
	public static void DeviceConfig(String[] Command,String Vcmd,String Scenario,String Did) throws InterruptedException{
		String push=SSHloginUtil.executeCommandWithEnable(deviceprop.getProperty(""+Did+".Ip_Dns"),deviceprop.getProperty(""+Did+".Dusername"), deviceprop.getProperty(""+Did+".DPasswd"),Command);
		System.out.println(push);
	   if(Scenario=="Positive"){
		 if(push.contains(Vcmd)){
			System.out.println("Device Configuration Positive scenario Successfully Verified,PASSED");
		 }
	   }
	  else if(Scenario=="Negative"){
		  System.out.println("Device Configuration Negative scenario Successfully Verified,PASSED");  
	  }
	}
	
	public static String executeCommandWithEnable(final String ipAddress, final String userName, final String password, final String[] sshcommand) throws InterruptedException{
		TestLogger.debug("Command going to execute on server : " + Arrays.toString(sshcommand));
		String output = "";
		final long startTime = System.currentTimeMillis();
		try {
			final IOConnection connection = IOConnectionUtil.getInstance(ipAddress, userName, password);
			for (final String command : sshcommand) {
				output = IOUtilities.runOnConnection(connection, new String[] { command }, 5 * 60 * 1000); // 5 minutes
				Thread.sleep(3000);
				}
				IOConnectionUtil.closeConnection();
			} catch (final Exception e2) {
				e2.printStackTrace();
			}
		final long presentTime = System.currentTimeMillis();
		TestLogger.info("Time taken by executeCommand in seconds : " +(presentTime - startTime)/1000+", approximately in minutes : "+(presentTime - startTime)/(1*60*1000));
		return output;
	}
	public static void LogBundleUnderCompbin(String[] Command) throws InterruptedException{
		String LogsDetails=executeCommandWithEnable(ipaddress,SshUsername, SshPassword,Command);
		System.out.println(LogsDetails);
		if(LogsDetails.contains("No such file or directory")){
			System.out.println("There is no log files are included in under /opt/CSCOlumos/compliance/bin ");
		}
		else{
			Assert.fail("FAILED:log files are included in under /opt/CSCOlumos/compliance/bin");
		}
		
	}
	
	public static void executeSparoTelnetFileChange(Dataset ds,String File) throws Exception{
//		String location = "/opt/saent/work/Lumos_IR/CAT3650_IFM_WCS/tel/";
		String location = "/opt/saent/work/Lumos_IR/Compliance/scripts/";
		String [] Command= {"sh "+location+File};
		String SaproIpaddress= ds.getAsString("SaproIpaddress");
		String SaproSshUsername=ds.getAsString("SaproSshUsername");
		String SaproSshPassword=ds.getAsString("SaproSshPassword");
		String LogsDetails=executeCommandWithEnable(SaproIpaddress,SaproSshUsername, SaproSshPassword,Command);
//		LogsDetails.contains("Loaded 3560 device telnet file Successfully")&
		if(!(LogsDetails.contains("Error"))){
			System.out.println("Successfully 3560 Image has been changed, Please do the device Sync");
		}
		else {
		Assert.fail("FAILED: Unable to Push the telnet file");
		}
	}
	
	public static void executeAddroute(String netid,String netmask, String SparoServerIp) throws Exception{
		String[] Command= {"route add -net "+netid+" netmask "+netmask+" gw "+SparoServerIp};
		String LogsDetails=executeCommandWithEnable(ipaddress,SshUsername, SshPassword,Command);
		if(LogsDetails.equalsIgnoreCase("")){
			System.out.println("Route added Successfully");
		}
		else if(LogsDetails.contains("File exists")){
			System.out.println("Route Already added");
		}
		else {
			Assert.fail("FAILED:Route added Successfully");
		}
	}
	
	public static void UnzipRbmlBundlefile(Dataset ds) throws Exception{
		String location = "/opt/CSCOlumos/compliance/bin/ZIP_DATA/PROCESSED/";
		String BundleName="";
		String [] Command= {"ls "+location+ds.getAsString("File")};
		String LogsDetails=executeCommandWithEnable(ipaddress,SshUsername, SshPassword,Command);
		if(LogsDetails.contains("No such file or directory")){
			TestLogger.info("There is no "+ds.getAsString("File")+" are included in under "+location);
			String [] Command1= {"ls "+location};
			BundleName=executeCommandWithEnable(ipaddress,SshUsername, SshPassword,Command1);
			System.out.println("RBML bundle Name is "+BundleName);
			String [] Command2= {"cd "+location,"unzip "+BundleName};
			String UnZipResults=executeCommandWithEnable(ipaddress,SshUsername, SshPassword,Command2);
			if(UnZipResults.contains(ds.getAsString("File"))){
				System.out.println(BundleName+" is Unzipped Successfully");
			}
			else {
				Assert.fail("FAILED:"+BundleName+" is not Unzipped Successfully");
			}
		}
		else if(LogsDetails.contains(location+ds.getAsString("File"))){
			System.out.println(BundleName+" is already Unzipped Successfully");
		}
		else {
			Assert.fail("FAILED:Unable to Execute the command");
		}
	}
	
	

}

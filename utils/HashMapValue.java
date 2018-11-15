package com.cisco.ui.test.compliance.wrappers.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class HashMapValue {
//	public static Document doc;
	
	public static void main(String Heading, String Child)throws ParserConfigurationException, SAXException, IOException {
		String location = "C:/Selenium/stafflist.xml";
		File fXmlFile = new File(location);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
//		HashMap< String,String> Child = new HashMap< String,String>();
//		HashMap<String,HashMap< String,String>> Parent = new HashMap <String,HashMap< String,String>>();
//		Parent.put("Testcase-1", Child);
//		HashMap<String,String> P1= Parent.get("Testcase-1");
//		System.out.println(P1.get("PolicyName"));
//		String Heading, String Child
//	}
//	public void insertValue(){
		doc.getDocumentElement().normalize();
//		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName(Heading);
		Node nNode = nList.item(0);
		Element eElement = (Element) nNode;
		String Value= eElement.getElementsByTagName(Child).item(0).getTextContent();
		System.out.println(Value);
    }
	
	public static String XmlRead(String Heading, String Child,String File)throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(File);
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
	
    public static String UnixDateConverter(long UnixValue){
    	// convert seconds to milliseconds
    	Date date = new java.util.Date(UnixValue*1000L); 
    	// the format of your date
    	SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd yyyy"); 
    	// give a timezone reference for formatting (see comment at the bottom)
    	sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+05:30")); 
    	String formattedDate = sdf.format(date);
    	System.out.println(formattedDate);
		return formattedDate;
    	
    }
    
    public static void getFileFromPIServer(final String ip, final String username, final String pwd, final String inputFilePath, final String outputFileName,final String File) throws Exception {
        final String host = ip;
        final String password = pwd;
        final String user = username;
        final JSch jsch = new JSch();
        final FileReader reader = null;
        final BufferedReader buffer = null;
        try {
               final Session session = jsch.getSession(user, host);
               final java.util.Properties config = new java.util.Properties();
               config.put("StrictHostKeyChecking", "no");
               session.setConfig(config);
               session.setPassword(password);
               session.connect();
               final Channel channel = session.openChannel("sftp");
               channel.connect();
               final ChannelSftp sftpChannel = (ChannelSftp) channel;
               sftpChannel.get(inputFilePath+File, outputFileName);
               sftpChannel.exit();
               session.disconnect();
        } catch (final JSchException e) {
               e.printStackTrace();
        } catch (final Exception e) {
               e.printStackTrace();
        } finally {
               try {
                     if (reader != null)
                            reader.close();
                     if (buffer != null)
                            buffer.close();
               } catch (final Exception e) {
                     System.out.println("Exception:" + e);
               }
        }
 }

}
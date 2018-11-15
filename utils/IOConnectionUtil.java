package com.cisco.ui.test.compliance.wrappers.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Pattern;

import com.cisco.test.tea.common.poolmanager.exceptions.ThresholdExceededException;
import com.cisco.test.tea.io.IOConnection;
import com.cisco.test.tea.io.LoggingIOConnection;
import com.cisco.test.tea.io.RegexConnection;
import com.cisco.test.tea.io.SSHConnection;

public class IOConnectionUtil {

	private static IOConnection connection = null;

	private IOConnectionUtil() {

	}

	public static IOConnection getInstance(final String ipAddress, final String userName, final String password)  {
		if (connection == null) {
			try {
				connection = getIOConnection(ipAddress, userName, password);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	private static IOConnection getIOConnection(final String ipAddress, final String userName, final String password) throws ThresholdExceededException, IOException {
		final IOConnection connection = getIOConnectionPool(ipAddress, userName, password);
		final RegexConnection rc = new RegexConnection(connection);
		final Pattern promptPattern = Pattern.compile("#" + ".?", Pattern.CASE_INSENSITIVE);
		rc.readUntilRegex(promptPattern, IOConnection.CONNECTION_LOGIN_TIMEOUT);
		return connection;
	}

	private static IOConnection getIOConnectionPool(final String ipAddress, final String userName, final String password) throws ThresholdExceededException, IOException {
		final InetAddress host = InetAddress.getByName(ipAddress);
		return new LoggingIOConnection(new SSHConnection(host, userName, password, "#", "Linux"), "The following messages have been written as part of the connection interaction:");
	}

	public static void closeConnection() {
		try {
			connection.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		connection = null;

	}
	
	public static IOConnection getInstanceEpwd(final String ipAddress, final String userName, final String password)  {
		  
		if (connection==null) {
			try {
				connection = getIOConnectionEnable(ipAddress, userName, password);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	private static IOConnection getIOConnectionEnable(final String ipAddress, final String userName, final String password) throws ThresholdExceededException, IOException {
		final IOConnection connection = getIOConnectionPoolEnable(ipAddress, userName, password);
		final RegexConnection rc = new RegexConnection(connection);
		final Pattern promptPattern = Pattern.compile(">" + ".?", Pattern.CASE_INSENSITIVE);
		rc.readUntilRegex(promptPattern, IOConnection.CONNECTION_LOGIN_TIMEOUT);
		return connection;
	}

	private static IOConnection getIOConnectionPoolEnable(final String ipAddress, final String userName, final String password) throws ThresholdExceededException, IOException {
		final InetAddress host = InetAddress.getByName(ipAddress);
		return new LoggingIOConnection(new SSHConnection(host, userName, password, ">", "Linux"), "The following messages have been written as part of the connection interaction:");
		
	}
}

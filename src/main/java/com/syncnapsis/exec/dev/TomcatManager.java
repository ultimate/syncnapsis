package com.syncnapsis.exec.dev;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class TomcatManager extends DevUtil
{
	private static String tomcatPath;
	private static int tomcatPort;
	private static String tomcatURL;
	private static boolean tomcatRunning;
	// TODO PEU rename
	private static boolean registered_ctx_peu;
	private static boolean tomcatInformationAvailable;

	// TODO PEU rename
	private static String tomcatApplicationName = "peu";

	public static void main(String[] args)
	{
		// convert args zu lower-case-list
		List<String> argsList = new ArrayList<String>(args.length);
		for(String arg : args)
		{
			argsList.add(arg.toLowerCase());
		}

		try
		{
			if(argsList.contains("-info") || argsList.contains("-stop") || argsList.contains("-start") || argsList.contains("-restart"))
			{
				executeTomcatInfo();
			}
			if(argsList.contains("-stop") || argsList.contains("-restart"))
			{
				executeTomcatStop();
			}
			if(argsList.contains("-start") || argsList.contains("-restart"))
			{
				executeTomcatStart();
			}
		}
		catch(Exception e)
		{
			printLine("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void executeTomcatInfo() throws Exception
	{
		getTomcatInformation();
	}

	public static void executeTomcatStop() throws Exception
	{
		executeTomcatService("stop");
	}

	public static void executeTomcatStart() throws Exception
	{
		executeTomcatService("start");
	}

	public static void executeTomcatService(String mode) throws IOException
	{
		int exitCode = executeExe("net " + mode + " tomcat6", false, false, false);
		if(exitCode == 2)
		{
			if(mode.equalsIgnoreCase("start"))
				printLine("Tomcat already running.");
			else if(mode.equalsIgnoreCase("stop"))
				printLine("Tomcat not running.");
		}
	}

//	public static void executeTomcatManager(String task, String application) throws Exception
//	{
//		String page = loadURL(new URL(getTomcatURL() + "manager/" + task + "?path=/" + application), null, "admin", "");
//		if(page == null)
//			throw new Exception("Error connecting. Maybe tomcat not running.");
//		if(!page.contains("OK"))
//			throw new Exception("Page not valid!");
//	}
//
//	public static void executeTomcatList(String application) throws Exception
//	{
//		String list = loadURL(new URL(getTomcatURL() + "manager/list"), null, "admin", "");
//		if(list == null)
//			throw new Exception("Error connecting. Maybe tomcat not running.");
//		if(!list.contains(application + ":running"))
//			throw new Exception("Page not valid!");
//	}

	public static String getTomcatURL() throws IOException
	{
		try
		{			
			getTomcatInformation();
			return tomcatURL;
		}
		catch(Exception e)
		{			
			return "http://localhost:8080/";
		}
	}

	public static int getTomcatPort() throws IOException
	{
		try
		{			
			getTomcatInformation();
			return tomcatPort;
		}
		catch(Exception e)
		{			
			return 8080;
		}
	}

	public static boolean getTomcatRunning() throws Exception
	{
		getTomcatInformation();
		return tomcatRunning;
	}

	public static String getTomcatPath() throws Exception
	{
		getTomcatInformation();
		return tomcatPath;
	}

	public static void getTomcatInformation() throws Exception
	{
		boolean success = true;
		// TODO PEU rename
		File ctx_peu = null;
		if(!tomcatInformationAvailable)
		{
			String wmic = "wmic SERVICE WHERE Name='Tomcat6' get Name,PathName,Started,State\n";
			int exitCode = executeExe(wmic, false, false, true);
			if(exitCode == 0)
			{
				int startIndexPath = outBuffer.indexOf("Tomcat6") + "Tomcat6".length() + 2;
				int endIndexPath = outBuffer.indexOf("//RS//Tomcat6") - 1 - "bin\\tomcat6.exe".length();
				int startIndexRunning = endIndexPath + "//RS//Tomcat6".length() + "bin\\tomcat6.exe".length() + 3;
				int endIndexRunning = startIndexRunning + 4;
				tomcatPath = outBuffer.substring(startIndexPath, endIndexPath);
				tomcatRunning = (outBuffer.substring(startIndexRunning, endIndexRunning)).equalsIgnoreCase("true");

				File serverXMLFile = new File(tomcatPath + "conf\\server.xml");
				StringBuilder sb = new StringBuilder();
				FileInputStream fis = new FileInputStream(serverXMLFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				int curr = bis.read();
				while(curr != -1)
				{
					sb.append((char) curr);
					curr = bis.read();
				}
				bis.close();
				fis.close();
				String serverXML = sb.toString();

				int startIndexPort = 0;
				int endIndexPort = 0;
				int startIndexProtocol = 0;
				int endIndexProtocol = 0;
				String protocol;
				while(serverXML.indexOf("Connector", startIndexPort) != -1)
				{
					startIndexPort = serverXML.indexOf("<Connector", startIndexPort) + "<Connector".length();
					startIndexProtocol = serverXML.indexOf("protocol=\"", startIndexPort) + "protocol=\"".length();
					endIndexProtocol = serverXML.indexOf("\"", startIndexProtocol);
					protocol = serverXML.substring(startIndexProtocol, endIndexProtocol);
					if(protocol.toLowerCase().startsWith("http") && !isComment(serverXML, startIndexPort))
					{
						startIndexPort = serverXML.indexOf("port=\"", startIndexPort) + "port=\"".length();
						endIndexPort = serverXML.indexOf("\"", startIndexPort);
						tomcatPort = Integer.parseInt(serverXML.substring(startIndexPort, endIndexPort));
					}

				}
				tomcatURL = "http://localhost:" + tomcatPort + "/";

				ctx_peu = new File(tomcatPath + "\\conf\\Catalina\\localhost\\" + tomcatApplicationName + ".xml");

				registered_ctx_peu = ctx_peu.exists();

				printLine(tomcatPath);
				printLine(tomcatURL);
				printLine("Running? " + tomcatRunning);

				tomcatInformationAvailable = true;
			}
			else
			{
				tomcatInformationAvailable = false;
				throw new Exception("Could not retrieve Tomcat Information!");
			}
		}
		int ctxCount = 0;
		int ctxCreateCount = 0;
		boolean create = false;
		if(tomcatInformationAvailable)
		{
			if(!registered_ctx_peu)
			{
				create = createTomcatContext(ctx_peu, getDocBase().getAbsolutePath());
				if(create)
					ctxCreateCount++;
				success = success && create;
			}
			else
				ctxCount++;
			printLine(ctxCount + "/1 Context files found");
			printLine(ctxCreateCount + " Context files created");
		}
		if(!(tomcatInformationAvailable && success))
			throw new Exception("Error getting Tomcat Info!");
	}

	public static boolean createTomcatContext(File file, String docBase)
	{
		String content = "<Context docBase=\"" + docBase.replace("/", "\\") + "\" />";
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			for(int i = 0; i < content.length(); i++)
				bos.write(content.charAt(i));
			bos.flush();
			fos.flush();
			bos.close();
			fos.close();
			return true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}

package com.syncnapsis.exec.dev;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DevUtil
{
	/**
	 * Logger-Instanz
	 */
	protected static final Logger logger = LoggerFactory.getLogger(DevUtil.class);

	public static final String		mainProjectDir	= "../universe-main/";

	public static final String		target			= "universe-main/target/webapp";

	protected static String			outBuffer;
	protected static String			errBuffer;

	private static BufferedWriter	out				= new BufferedWriter(new OutputStreamWriter(System.out));

	public static void copyConfigurationFilesFromMainProject() throws IOException
	{
		// Copy-Configuration-Files from main-project
		copyFileFromMainProject("applicationContext-dao.xml");
		copyFileFromMainProject("applicationContext-service.xml");
		copyFileFromMainProject("hibernate.cfg.xml");
		copyFileFromMainProject("jdbc.properties");
	}

	private static void copyFileFromMainProject(String name) throws IOException
	{
		copyFile(new File(mainProjectDir + "target/classes/" + name), new File("target/classes/" + name));
	}

	private static void copyFile(File in, File out) throws IOException
	{
		FileInputStream fis = new FileInputStream(in);
		BufferedInputStream bis = new BufferedInputStream(fis);

		FileOutputStream fos = new FileOutputStream(out);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		int c;
		while((c = bis.read()) != -1)
			bos.write(c);

		bos.flush();
		fos.flush();

		bos.close();
		fos.close();

		bis.close();
		fis.close();
	}

	public static String formatNumberString(Number n, int minLength)
	{
		return formatString("" + n, minLength, ' ', true);
	}

	public static String formatString(String s, int minLength, char fillup, boolean begin)
	{
		while(s.length() < minLength)
		{
			if(begin)
				s = fillup + s;
			else
				s = s + fillup;
		}
		return s;
	}

	public static void printLine(String line)
	{
		try
		{
			out.write(line);
			out.write('\n');
			out.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static int executeExe(String command, boolean output, boolean erroroutput, boolean getOutput) throws IOException
	{
		Process p = Runtime.getRuntime().exec(command);
		ExecThread pOut = new ExecThread(p.getInputStream(), output, "OUTPUT", getOutput);
		ExecThread pErr = new ExecThread(p.getErrorStream(), erroroutput, "ERROR ", getOutput);
		pOut.start();
		pErr.start();
		try
		{
			pOut.join();
			pErr.join();
		}
		catch(InterruptedException e)
		{
		}
		if(getOutput)
		{
			errBuffer = pErr.allOutputs.toString();
			outBuffer = pOut.allOutputs.toString();
			// printLine(pOut.allOutputs.toString());
		}
		try
		{
			return p.waitFor();
		}
		catch(InterruptedException e)
		{
			throw new Error("Task interrupted");
		}
	}

	public static boolean isComment(String xml, int index)
	{
		String xmlSub = xml.substring(0, index);
		boolean comment = false;
		int startIndexComment = xmlSub.lastIndexOf("<!--");
		int endIndexComment = xmlSub.lastIndexOf("-->");
		if((startIndexComment < endIndexComment) || (startIndexComment == endIndexComment))
		{
			comment = false;
		}
		else
		{
			comment = true;
		}
		return comment;
	}

	public static String loadURL(URL url, String parameter, final String user, final String password) throws IOException
	{
		try
		{
			if(user != null && password != null)
			{
				Authenticator.setDefault(new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(user, password.toCharArray());
					}
				});
			}
			StringBuilder site = new StringBuilder();
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			connection.setRequestMethod("GET");

			if(parameter != null && !parameter.equals(""))
			{
				PrintWriter out = new PrintWriter(connection.getOutputStream());
				out.print(parameter);
				out.close();
			}

			connection.connect();
			InputStream is = connection.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			int curr = bis.read();
			while(curr != -1)
			{
				site.append((char) curr);
				curr = bis.read();
			}
			bis.close();
			is.close();
			return site.toString();
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static File getDocBase()
	{
		File base = new File("").getAbsoluteFile().getParentFile();
		int index = 0;
		while((index = target.indexOf("..", index)) != -1)
		{
			base = base.getParentFile();
			index++;
		}
		return new File(base.getAbsolutePath() + "/" + target);
	}

	private static class ExecThread extends Thread
	{
		private BufferedReader	reader;
		private boolean			output;
		private String			type;
		private boolean			getOutput;
		private StringBuilder	allOutputs;

		private ExecThread(InputStream s, boolean output, String type, boolean getOutput)
		{
			this.reader = new BufferedReader(new InputStreamReader(s));
			this.output = output;
			this.type = type;
			this.getOutput = getOutput;
			allOutputs = new StringBuilder();
		}

		public void run()
		{
			try
			{
				String line = null;
				while((line = reader.readLine()) != null)
				{
					if(getOutput)
					{
						allOutputs.append(line);
						allOutputs.append('\n');
					}
					if(output)
						printLine("-->  " + type + " > " + line);
				}
			}
			catch(IOException ioe)
			{
			}
		}
	}
}

package com.syncnapsis.exec.dev;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class ResourceBundleProcessor extends TomcatManager
{
	public static final String	RESOURCE_BUNDLE_PREFIX	= "i3-label";
	public static final String	RESOURCE_BUNDLE_SUFFIX	= ".properties";
	
	public static final String  RESOURCE_VALUE_START = " = ";
	public static final String  RESOURCE_VALUE_LINEBREAK = "\\r\\n";

	public static void main(String[] args)
	{
		File docBase = getDocBase();
		File classes = new File(docBase.getAbsolutePath() + "/WEB-INF/classes");
		
		for(File file: classes.listFiles())
		{
			if(file.getName().startsWith(RESOURCE_BUNDLE_PREFIX) && file.getName().endsWith(RESOURCE_BUNDLE_SUFFIX))
			{
				try
				{
					processResourceBundleFile(file);
				}
				catch(IOException e)
				{
					logger.error("could not process file: " + file.getName());
					e.printStackTrace();
				}
			}
		}
	}

	private static void processResourceBundleFile(File file) throws IOException
	{
		logger.info("processing file: " + file.getName());
		
		File tmp = new File(System.currentTimeMillis() + ".tmp");
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));
		
		String line;
		int start, end;
		
		while((line = br.readLine()) != null)
		{
			if(!line.contains(RESOURCE_VALUE_LINEBREAK))
			{
				bw.write(line);
				bw.newLine();
			}
			else
			{
				start = 0;
				end = line.indexOf(RESOURCE_VALUE_START)+RESOURCE_VALUE_START.length();
				
				// key schreiben mit "{"
				bw.write(line.substring(start, end));
				bw.write("{");
				bw.newLine();
				
				start = end;
				// value in mehreren Zeilen schreiben
				while((end = line.indexOf(RESOURCE_VALUE_LINEBREAK, start+1)) > 0)
				{					
					bw.write(line.substring(start, end));
					bw.newLine();
					start = end+RESOURCE_VALUE_LINEBREAK.length();
				}					
				bw.write(line.substring(start));				
				bw.newLine();
				
				// ende von "{" schreiben
				bw.write("}");
				bw.newLine();
			}
		}
		
		bw.flush();
		bw.close();
		br.close();
		
		file.delete();
		tmp.renameTo(file);
	}
}

/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.utils.dev;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceBundleProcessor
{
	public static final String			RESOURCE_BUNDLE_PREFIX_DEFAULT	= "i3-label";
	public static final String			RESOURCE_BUNDLE_SUFFIX_DEFAULT	= ".properties";

	public static final String			RESOURCE_VALUE_START			= " = ";
	public static final String			RESOURCE_VALUE_LINEBREAK		= "\\r\\n";

	protected transient final Logger	logger							= LoggerFactory.getLogger(getClass());

	protected File						classesDir;
	
	protected String					resourceBundlePrefix;

	protected String					resourceBundleSuffix;

	public ResourceBundleProcessor(File classesDir)
	{
		this(classesDir, RESOURCE_BUNDLE_PREFIX_DEFAULT, RESOURCE_BUNDLE_SUFFIX_DEFAULT);
	}

	public ResourceBundleProcessor(File classesDir, String resourceBundlePrefix, String resourceBundleSuffix)
	{
		super();
		this.classesDir = classesDir;
		this.resourceBundlePrefix = resourceBundlePrefix;
		this.resourceBundleSuffix = resourceBundleSuffix;
	}

	public File getClassesDir()
	{
		return classesDir;
	}

	public String getResourceBundlePrefix()
	{
		return resourceBundlePrefix;
	}

	public String getResourceBundleSuffix()
	{
		return resourceBundleSuffix;
	}

	public void processResourceBundles()
	{
		int processed = 0;
		int matching = 0;
		for(File file : classesDir.listFiles())
		{
			if(file.getName().startsWith(resourceBundlePrefix) && file.getName().endsWith(resourceBundleSuffix))
			{
				matching++;
				try
				{
					logger.info("processing file: " + file.getName());
					processResourceBundle(file);
					processed++;
				}
				catch(IOException e)
				{
					logger.error("could not process file: " + file.getName());
					e.printStackTrace();
				}
			}
		}
		logger.info("files processed: " + processed + " (of " + matching + " matching)");
	}

	public void processResourceBundle(File file) throws IOException
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
				end = line.indexOf(RESOURCE_VALUE_START) + RESOURCE_VALUE_START.length();

				// key schreiben mit "{"
				bw.write(line.substring(start, end));
				bw.write("{");
				bw.newLine();

				start = end;
				// value in mehreren Zeilen schreiben
				while((end = line.indexOf(RESOURCE_VALUE_LINEBREAK, start + 1)) > 0)
				{
					bw.write(line.substring(start, end));
					bw.newLine();
					start = end + RESOURCE_VALUE_LINEBREAK.length();
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
	
	public static void main(String[] args)
	{
		ResourceBundleProcessor p = null;
		if(args.length == 1)
			p = new ResourceBundleProcessor(new File(args[0]));
		else if(args.length == 3)
			p = new ResourceBundleProcessor(new File(args[2]));
		else
		{
			System.out.println("usage:");
			System.out.println("  java -cp [cp] ResourceBundleProcessor [classesDir]");
			System.out.println("or");
			System.out.println("  java -cp [cp] ResourceBundleProcessor [classesDir] [rbPrefix] [rbSuffix]");
			System.exit(0);
		}
		p.processResourceBundles();			
	}
}

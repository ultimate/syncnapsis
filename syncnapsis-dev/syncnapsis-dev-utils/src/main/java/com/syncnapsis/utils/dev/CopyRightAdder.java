/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.StringUtil;

public class CopyRightAdder extends CodeScanner
{
	private static final int			STATUS_COPY_RIGHT_NOT_FOUND	= 2;
	private static final int			STATUS_COPY_RIGHT_ADDED		= 1;
	private static final int			STATUS_COPY_RIGHT_FOUND		= 0;
	private static final int			STATUS_UNKNOWN_EXTENSION	= -1;
	private static final int			STATUS_DIRECTORY			= -2;

	private static final String			COPY_RIGHT_IDENTIFIER		= "Syncnapsis Framework - Copyright (c)";

	private Map<String, CopyRightInfo>	copyRightInfoMap;

	private boolean						analyzeOnly;

	public CopyRightAdder(File projectDir, File templateDir, boolean analyzeOnly)
	{
		super(projectDir);

		this.analyzeOnly = analyzeOnly;

		copyRightInfoMap = new HashMap<String, CopyRightAdder.CopyRightInfo>();

		System.out.println("Reading templates...");
		try
		{
			copyRightInfoMap.put(".java", new CopyRightInfo("java", 0, templateDir));
			copyRightInfoMap.put(".js", new CopyRightInfo("java", 0, templateDir));
			copyRightInfoMap.put(".properties", new CopyRightInfo("properties", 0, templateDir));
			copyRightInfoMap.put(".xml", new CopyRightInfo("xml", 1, templateDir));
			copyRightInfoMap.put(".dtd", new CopyRightInfo("xml", 1, templateDir));
			copyRightInfoMap.put(".html", new CopyRightInfo("xml", 1, templateDir));
			copyRightInfoMap.put(".bat", new CopyRightInfo("bat", 0, templateDir));
			copyRightInfoMap.put(".sh", new CopyRightInfo("sh", 0, templateDir));
		}
		catch(IOException e)
		{
			System.out.println("ERROR: Could not read templates: " + e.getMessage());
			return;
		}
	}

	public void addCopyRight()
	{

		Map<Integer, Integer> statusCount = processFiles();

		int copyRightAdded = statusCount.containsKey(STATUS_COPY_RIGHT_ADDED) ? statusCount.get(STATUS_COPY_RIGHT_ADDED) : 0;
		int copyRightNotFound = statusCount.containsKey(STATUS_COPY_RIGHT_NOT_FOUND) ? statusCount.get(STATUS_COPY_RIGHT_NOT_FOUND) : 0;
		int copyRightFound = statusCount.containsKey(STATUS_COPY_RIGHT_FOUND) ? statusCount.get(STATUS_COPY_RIGHT_FOUND) : 0;
		int unknownExtension = statusCount.containsKey(STATUS_UNKNOWN_EXTENSION) ? statusCount.get(STATUS_UNKNOWN_EXTENSION) : 0;
		int directories = statusCount.containsKey(STATUS_DIRECTORY) ? statusCount.get(STATUS_DIRECTORY) : 0;

		System.out.println("Summary:");
		System.out.println("  copy right found:  	" + StringUtil.fillup("" + copyRightFound, 5, ' ', true));
		System.out.println("  copy right not found: " + StringUtil.fillup("" + copyRightNotFound, 5, ' ', true));
		System.out.println("  copy right added:  	" + StringUtil.fillup("" + copyRightAdded, 5, ' ', true));
		System.out.println("  unknown extension: 	" + StringUtil.fillup("" + unknownExtension, 5, ' ', true));
		System.out.println("  directories:       	" + StringUtil.fillup("" + directories, 5, ' ', true));
		System.out.println("  ------------------------");
		int total = copyRightFound + copyRightAdded + unknownExtension + directories;
		System.out.println("  total:             " + StringUtil.fillup("" + total, 5, ' ', true));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.dev.CodeScanner#processFile(java.io.File)
	 */
	@Override
	public int processFile(File file) throws IOException
	{
		if(file.isDirectory())
			return STATUS_DIRECTORY;

		String extension = FileUtil.getExtension(file);
		String content = FileUtil.readFile(file);

		CopyRightInfo copyRightInfo = copyRightInfoMap.get(extension);
		if(copyRightInfo == null)
			return STATUS_UNKNOWN_EXTENSION;

		if(content.contains(COPY_RIGHT_IDENTIFIER))
			return STATUS_COPY_RIGHT_FOUND;

		if(analyzeOnly)
			return STATUS_COPY_RIGHT_NOT_FOUND;

		StringTokenizer st = new StringTokenizer(content, "\r\n", true);

		StringBuilder contentWithCopyRight = new StringBuilder();

		int lines = 0;
		String token;
		while(st.hasMoreTokens())
		{
			if(lines == copyRightInfo.getCopyRightLine())
			{
				contentWithCopyRight.append(copyRightInfo.getCopyRightSection());
				contentWithCopyRight.append("\n");
				lines++;
			}
			else
			{
				token = st.nextToken();
				contentWithCopyRight.append(token);
				if(token.equals("\n"))
					lines++;
			}
		}

		FileUtil.writeFile(file, contentWithCopyRight.toString());

		return STATUS_COPY_RIGHT_ADDED;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.dev.CodeScanner#getStatusLabel(int)
	 */
	@Override
	public String getStatusLabel(int statusCode)
	{
		switch(statusCode)
		{
			case STATUS_COPY_RIGHT_ADDED:
				return "ADDED";
			case STATUS_COPY_RIGHT_FOUND:
				return "FOUND";
			case STATUS_COPY_RIGHT_NOT_FOUND:
				return "NOT FOUND";
			case STATUS_UNKNOWN_EXTENSION:
				return "UNKNOWN EXTENSION";
			case STATUS_DIRECTORY:
				return "DIRECTORY";
		}
		return "UNKNOWN RESULT";
	}

	public static void main(String[] args)
	{
		File projectDir = null;
		File templateDir = null;
		boolean analyzeOnly = false;
		for(String arg : args)
		{
			if(arg.equals("-h"))
			{
				System.out.println("usage: java -cp %CP% CodeGenerator [-p=<projectDir>] [-t=<templateDir>] [-e=[overwrite|skip]]");
				System.out.println("  -h                    print this help");
				System.out.println("  -p=<projectDir>       path to the project directory (if not specified current directory will be used)");
				System.out.println("  -t=<templateDir>      path to the template directory (if not specified project directory will be used)");
				System.out.println("  -a                    analyze files only, do not add copyright");
				System.exit(0);
			}
			else if(arg.startsWith("-p="))
			{
				String dir = arg.substring(3);
				if(dir.startsWith("\"") && dir.endsWith("\""))
					dir = dir.substring(1, dir.length() - 1);
				projectDir = new File(arg.substring(3));
			}
			else if(arg.startsWith("-t="))
			{
				String dir = arg.substring(3);
				if(dir.startsWith("\"") && dir.endsWith("\""))
					dir = dir.substring(1, dir.length() - 1);
				templateDir = new File(arg.substring(3));
			}
			else if(arg.equals("-a"))
			{
				analyzeOnly = true;
			}
		}

		if(projectDir == null)
			projectDir = new File("");
		if(templateDir == null)
			templateDir = projectDir;

		System.out.println("Adding Copy Right Info...");
		System.out.println("  project:       " + projectDir.getPath());
		System.out.println("  templates:     " + templateDir.getPath());
		System.out.println("  analyze only:  " + analyzeOnly);

		CopyRightAdder a = new CopyRightAdder(projectDir, templateDir, analyzeOnly);

		a.addCopyRight();
	}

	private class CopyRightInfo
	{
		private String	copyRightSection;
		private int		copyRightLine;

		public CopyRightInfo(String templateName, int copyRightLine, File templateDir) throws IOException
		{
			super();
			String templateFile = templateDir.getAbsolutePath() + "\\" + templateName + ".template";
			System.out.println("  " + templateName + " : " + templateFile);
			this.copyRightSection = FileUtil.readFile(new File(templateFile));
			this.copyRightLine = copyRightLine;
		}

		public String getCopyRightSection()
		{
			return copyRightSection;
		}

		public int getCopyRightLine()
		{
			return copyRightLine;
		}
	}

}

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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.StringUtil;

public class CopyRightAdder
{
	private static final int	STATUS_COPY_RIGHT_ADDED		= 1;
	private static final int	STATUS_COPY_RIGHT_FOUND		= 0;
	private static final int	STATUS_UNKNOWN_EXTENSION	= -1;
	private static final int	STATUS_DIRECTORY			= -2;

	private static final String	COPY_RIGHT_IDENTIFIER		= "Syncnapsis Framework - Copyright (c)";

	public static void main(String[] args)
	{
		File projectDir = null;
		File templateDir = null;
		for(String arg : args)
		{
			if(arg.equals("-h"))
			{
				System.out.println("usage: java -cp %CP% CodeGenerator [-p=<projectDir>] [-t=<templateDir>] [-e=[overwrite|skip]]");
				System.out.println("  -h                    print this help");
				System.out.println("  -p=<projectDir>       path to the project directory (if not specified current directory will be used)");
				System.out.println("  -t=<templateDir>      path to the template directory (if not specified project directory will be used)");
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
		}

		if(projectDir == null)
			projectDir = new File("");
		if(templateDir == null)
			templateDir = projectDir;

		addCopyRight(projectDir, templateDir);
	}

	public static void addCopyRight(File projectDir, File templateDir)
	{
		System.out.println("Adding Copy Right Info...");
		System.out.println("  project:       " + projectDir.getPath());
		System.out.println("  templates:     " + templateDir.getPath());

		int copyRightAdded = 0;
		int copyRightFound = 0;
		int unknownExtension = 0;
		int directories = 0;

		Map<String, CopyRightInfo> copyRightInfoMap = new HashMap<String, CopyRightAdder.CopyRightInfo>();

		System.out.println("Reading templates...");
		try
		{
			copyRightInfoMap.put(".java", new CopyRightInfo("java", 0, templateDir));
			copyRightInfoMap.put(".js", new CopyRightInfo("java", 0, templateDir));
			copyRightInfoMap.put(".properties", new CopyRightInfo("properties", 0, templateDir));
			copyRightInfoMap.put(".xml", new CopyRightInfo("xml", 1, templateDir));
			copyRightInfoMap.put(".dtd", new CopyRightInfo("xml", 0, templateDir));
			copyRightInfoMap.put(".bat", new CopyRightInfo("bat", 0, templateDir));
		}
		catch(IOException e)
		{
			System.out.println("ERROR: Could not read templates: " + e.getMessage());
			return;
		}

		System.out.println("Getting file-list...");
		List<File> files = FileUtil.listFilesIncludingSubfoldersAsList(projectDir.getAbsoluteFile(), new CopyRightFileFilter());
		System.out.println("  " + files.size() + " files found!");
		System.out.print("  Continue (Y/N)? ");

		int c;
		while(true)
		{
			try
			{
				c = System.in.read();
			}
			catch(IOException e)
			{
				System.out.println("could not read key input...");
				continue;
			}

			if(((char) c) == 'Y' || ((char) c) == 'y')
				break;
			else if(((char) c) == 'N' || ((char) c) == 'n')
			{
				return;
			}
		}

		System.out.println("Processing files...");

		int status;
		for(File file : files)
		{
			try
			{
				System.out.print("  " + file.getPath() + " ... ");

				status = processFile(file, copyRightInfoMap);

				if(status == STATUS_COPY_RIGHT_ADDED)
				{
					System.out.println("ADDED");
					copyRightAdded++;
				}
				else if(status == STATUS_COPY_RIGHT_FOUND)
				{
					System.out.println("FOUND");
					copyRightFound++;
				}
				else if(status == STATUS_UNKNOWN_EXTENSION)
				{
					System.out.println("UNKNOWN EXTENSION");
					unknownExtension++;
				}
				else if(status == STATUS_DIRECTORY)
				{
					System.out.println("");
					directories++;
				}
			}
			catch(IOException e)
			{
				System.out.println("ERROR");
			}
		}

		System.out.println("Summary:");
		System.out.println("  copy right found:  " + StringUtil.fillup("" + copyRightFound, 5, ' ', true));
		System.out.println("  copy right added:  " + StringUtil.fillup("" + copyRightAdded, 5, ' ', true));
		System.out.println("  unknown extension: " + StringUtil.fillup("" + unknownExtension, 5, ' ', true));
		System.out.println("  directories:       " + StringUtil.fillup("" + directories, 5, ' ', true));
		System.out.println("  ------------------------");
		int total = copyRightFound + copyRightAdded + unknownExtension + directories;
		System.out.println("  total:             " + StringUtil.fillup("" + total, 5, ' ', true));
	}

	public static int processFile(File file, Map<String, CopyRightInfo> copyRightInfoMap) throws IOException
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

		StringTokenizer st = new StringTokenizer(content, "\r\n");

		StringBuilder contentWithCopyRight = new StringBuilder();

		int lines = 0;
		while(st.hasMoreTokens())
		{
			if(lines == copyRightInfo.getCopyRightLine())
			{
				contentWithCopyRight.append(copyRightInfo.getCopyRightSection());
				contentWithCopyRight.append("\n");
				lines++;
			}
			contentWithCopyRight.append(st.nextToken());
			contentWithCopyRight.append("\n");
			lines++;
		}

		FileUtil.writeFile(file, contentWithCopyRight.toString());

		return STATUS_COPY_RIGHT_ADDED;
	}

	private static class CopyRightInfo
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

	/**
	 * FileFilter that only accepts files for which copy right info can be added.
	 * 
	 * @author ultimate
	 * 
	 */
	private static class CopyRightFileFilter implements FileFilter
	{
		public CopyRightFileFilter()
		{
		}

		/*
		 * (non-Javadoc)
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		@Override
		public boolean accept(File f)
		{
			if(f.isDirectory())
			{
				if(f.getName().substring(f.getName().lastIndexOf("\\") + 1).startsWith("."))
					return false;
				if(f.getName().endsWith("target"))
					return false;
				return true;
			}
			if(f.isFile())
			{
				if(f.getName().substring(f.getName().lastIndexOf("\\") + 1).startsWith("."))
					return false;
				return true;
			}
			return false;
		}
	}
}

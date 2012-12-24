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

import com.syncnapsis.utils.FileUtil;

/**
 * Abstract base for dev-utils that scan the code files for processing them.
 * 
 * @author ultimate
 * 
 */
public abstract class CodeScanner
{
	protected File	projectDir;

	public CodeScanner(File projectDir)
	{
		this.projectDir = projectDir;
	}

	/**
	 * List all code files in the current project (using a FileFilter)
	 * 
	 * @param projectDir - the project directory
	 * @return the list of code files
	 */
	protected List<File> getCodeFiles()
	{
		return FileUtil.listFilesIncludingSubfoldersAsList(projectDir.getAbsoluteFile(), new CodeFileFilter());
	}
	
	public abstract String getStatusLabel(int statusCode);

	public Map<Integer, Integer> processFiles()
	{
		Map<Integer, Integer> statusCount = new HashMap<Integer, Integer>();
		
		System.out.println("Getting file-list...");
		List<File> files = getCodeFiles();
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
				return statusCount;
			}
		}
		
		System.out.println("Processing files...");

		int status;
		for(File file : files)
		{
			try
			{
				System.out.print("  " + file.getPath() + " ... ");

				status = processFile(file);

				System.out.println(getStatusLabel(status));
				
				if(statusCount.containsKey(status))
					statusCount.put(status, statusCount.get(status)+1);
				else
					statusCount.put(status, 1);
			}
			catch(IOException e)
			{
				System.out.println("ERROR");
			}
		}
		
		return statusCount;
	}

	protected abstract int processFile(File file) throws IOException;

	/**
	 * FileFilter that only accepts files which may contain code.
	 * 
	 * @author ultimate
	 * 
	 */
	protected class CodeFileFilter implements FileFilter
	{
		public CodeFileFilter()
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
				if(f.getName().endsWith("format-java.xml"))
					return false;
				if(f.getName().endsWith("format-js.xml"))
					return false;
				if(f.getName().contains("find-bugs"))
					return false;
				return true;
			}
			return false;
		}
	}
}

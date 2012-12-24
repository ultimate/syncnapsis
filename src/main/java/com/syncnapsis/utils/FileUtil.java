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
package com.syncnapsis.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util-Klasse für nützliche Funktionen auf File-Ebene.
 * 
 * @author ultimate
 */
public abstract class FileUtil
{
	/**
	 * Logger-Instanz
	 */
	protected static final Logger	logger	= LoggerFactory.getLogger(FileUtil.class);

	/**
	 * Listet alle Dateien auf, die sich innerhalb eines Ordners oder einem seiner Subordner
	 * befinden.
	 * Das Ergebnis wird in eine File-Array konvertiert.
	 * 
	 * @see FileUtil#listFilesIncludingSubfoldersAsList(File)
	 * @param file - der Ordner
	 * @return die Liste der Dateien
	 */
	public static File[] listFilesIncludingSubfolders(File file)
	{
		return listFilesIncludingSubfolders(file, null);
	}
	
	/**
	 * Listet alle Dateien auf, die sich innerhalb eines Ordners oder einem seiner Subordner
	 * befinden.
	 * Das Ergebnis wird in eine File-Array konvertiert.
	 * 
	 * @see FileUtil#listFilesIncludingSubfoldersAsList(File)
	 * @param file - der Ordner
	 * @param filter - the FileFilter to use
	 * @return die Liste der Dateien
	 */
	public static File[] listFilesIncludingSubfolders(File file, FileFilter filter)
	{
		List<File> files = listFilesIncludingSubfoldersAsList(file, filter);
		return files.toArray(new File[files.size()]);
	}

	/**
	 * Listet alle Dateien auf, die sich innerhalb eines Ordners oder einem seiner Subordner
	 * befinden.
	 * Das Ergebnis wird in eine String-Array konvertiert.
	 * 
	 * @see FileUtil#listFilesIncludingSubfoldersAsList(File)
	 * @param file - der Ordner
	 * @return die Liste der Dateien
	 */
	public static String[] listFilesIncludingSubfoldersAsString(File file)
	{
		return listFilesIncludingSubfoldersAsString(file, null);
	}

	/**
	 * Listet alle Dateien auf, die sich innerhalb eines Ordners oder einem seiner Subordner
	 * befinden.
	 * Das Ergebnis wird in eine String-Array konvertiert.
	 * 
	 * @see FileUtil#listFilesIncludingSubfoldersAsList(File)
	 * @param file - der Ordner
	 * @param filter - the FileFilter to use
	 * @return die Liste der Dateien
	 */
	public static String[] listFilesIncludingSubfoldersAsString(File file, FileFilter filter)
	{
		File[] files = listFilesIncludingSubfolders(file);
		String[] fileNames = new String[files.length];

		for(int i = 0; i < files.length; i++)
			fileNames[i] = files[i].getAbsolutePath().substring(file.getAbsolutePath().length() + 1);

		return fileNames;
	}

	/**
	 * Listet alle Dateien auf, die sich innerhalb eines Ordners oder einem seiner Subordner
	 * befinden.
	 * 
	 * @param file - der Ordner
	 * @return die Liste der Dateien
	 */
	public static List<File> listFilesIncludingSubfoldersAsList(File file)
	{
		return listFilesIncludingSubfoldersAsList(file, null);
	}

	/**
	 * Listet alle Dateien auf, die sich innerhalb eines Ordners oder einem seiner Subordner
	 * befinden.
	 * 
	 * @param file - der Ordner
	 * @param filter - the FileFilter to use
	 * @return die Liste der Dateien
	 */
	public static List<File> listFilesIncludingSubfoldersAsList(File file, FileFilter filter)
	{
		List<File> subFiles = new ArrayList<File>();

		if(file.listFiles(filter) != null)
			subFiles.addAll(Arrays.asList(file.listFiles(filter)));

		List<File> subSubFiles = new ArrayList<File>();

		for(File subFile : subFiles)
		{
			subSubFiles.add(subFile);
			if(subFile.isDirectory())
			{
				subSubFiles.addAll(listFilesIncludingSubfoldersAsList(subFile, filter));
			}
		}

		return subSubFiles;
	}

	/**
	 * Kopiert eine Datei von einer Stelle an eine andere
	 * 
	 * @param source - die Ursprungsdatei
	 * @param target - die Zieldatei
	 * @throws IOException wenn ein Fehler auftritt
	 */
	public static void copyFile(File source, File target) throws IOException
	{
		logger.debug("copying file: " + source + " --> " + target);
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(source));
		BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(target));
		try
		{
			byte[] buf = new byte[1024];
			int i = 0;
			while((i = fis.read(buf)) != -1)
			{
				fos.write(buf, 0, i);
			}
			fos.flush();
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			if(fis != null)
				fis.close();
			if(fos != null)
				fos.close();
		}
	}

	/**
	 * Read a file into a String
	 * 
	 * @param source - the file to read
	 * @return the file content
	 * @throws IOException - if reading the file fails
	 */
	public static String readFile(File source) throws IOException
	{
		logger.debug("reading file: " + source);
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(source));
		StringBuffer sb = new StringBuffer();
		try
		{
			int c;
			while((c = fis.read()) != -1)
			{
				sb.append((char) c);
			}
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			if(fis != null)
				fis.close();
		}
		return sb.toString();
	}

	/**
	 * Write the content of a String to a file
	 * 
	 * @param target - the file to write to
	 * @param content - the content to write
	 * @throws IOException - if writing the file fails
	 */
	public static void writeFile(File target, String content) throws IOException
	{
		logger.debug("writing file: " + target);
		if(!target.getAbsoluteFile().getParentFile().exists())
			target.getAbsoluteFile().getParentFile().mkdirs();
		BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(target));
		try
		{
			byte[] bytes = content.getBytes();
			fos.write(bytes);
			fos.flush();
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			if(fos != null)
				fos.close();
		}
	}

	/**
	 * Get the extension of a file
	 * 
	 * @param file - the file
	 * @return the file extension
	 */
	public static String getExtension(File file)
	{
		if(file.isDirectory())
			return null;
		int dotIndex = file.getName().lastIndexOf(".");
		if(dotIndex >= 0)
			return file.getName().substring(dotIndex);
		return null;
	}
}

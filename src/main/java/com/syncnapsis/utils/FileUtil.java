package com.syncnapsis.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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
	protected static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
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
		List<File> files = listFilesIncludingSubfoldersAsList(file);
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
		List<File> subFiles = new ArrayList<File>();

		if(file.listFiles() != null)
			subFiles.addAll(Arrays.asList(file.listFiles()));

		List<File> subSubFiles = new ArrayList<File>();

		for(File subFile : subFiles)
		{
			if(subFile.isDirectory())
			{
				subSubFiles.addAll(listFilesIncludingSubfoldersAsList(subFile));
			}
			subSubFiles.add(subFile);
		}

		return subSubFiles;
	}

	/**
	 * Kopiert eine Datei von einer Stelle an eine andere
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

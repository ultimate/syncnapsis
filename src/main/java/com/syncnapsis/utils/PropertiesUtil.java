package com.syncnapsis.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Util-Klasse für das Laden von *.properties-Dateien.
 * 
 * @author ultimate
 */
public class PropertiesUtil
{
	/**
	 * Lädt eine *.properties-Datei.
	 * 
	 * @param file - die Datei
	 * @return das Properties-Objekt
	 * @throws IOException - wenn die Datei ungültig ist
	 */
	public static Properties loadProperties(File file) throws IOException
	{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		Properties properties = new Properties();
		properties.load(bis);
		bis.close();
		return properties;
	}
}

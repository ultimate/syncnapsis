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
package com.syncnapsis.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility-Class for loading {@link Properties}
 * 
 * @author ultimate
 */
public class PropertiesUtil
{
	/**
	 * Load a *.properties-File by specifying the location as a {@link File}.
	 * 
	 * @param file - the file
	 * @return the Properties-Object
	 * @throws IOException if reading the file fails
	 */
	public static Properties loadProperties(File file) throws IOException
	{
		return loadProperties(new FileInputStream(file));
	}

	/**
	 * Load a *.properties-File by specifying the location as a String. The Properties will then be
	 * loaded via the ClassLoader as a resource.
	 * 
	 * @param name - the name of the properties file
	 * @return the Properties-Object
	 * @throws IOException if reading the file fails
	 */
	public static Properties loadProperties(String name) throws IOException
	{
		return loadProperties(PropertiesUtil.class.getClassLoader().getResourceAsStream(name));
	}

	/**
	 * Load a *.properties-File from the given {@link InputStream}.<br>
	 * The input stream will be closed automatically after loading
	 * 
	 * @param is - the {@link InputStream}
	 * @return the Properties-Object
	 * @throws IOException if reading the stream fails
	 */
	public static Properties loadProperties(InputStream is) throws IOException
	{
		BufferedInputStream bis = new BufferedInputStream(is);
		Properties properties = new Properties();
		properties.load(bis);
		bis.close();
		return properties;
	}

	/**
	 * Get an int property.
	 * 
	 * @param properties - the properties to get the property from
	 * @param key - the key to get the value for
	 * @return the value for the key as an int
	 */
	public static int getInt(Properties properties, String key)
	{
		String s = getProperty(properties, key);
		if(s == null)
			return 0;
		return Integer.parseInt(s);
	}

	/**
	 * Get a String property.
	 * 
	 * @param properties - the properties to get the property from
	 * @param key - the key to get the value for
	 * @return the value for the key as an String
	 */
	public static String getString(Properties properties, String key)
	{
		return getProperty(properties, key);
	}

	/**
	 * Get a boolean property.
	 * 
	 * @param properties - the properties to get the property from
	 * @param key - the key to get the value for
	 * @return the value for the key as an boolean
	 */
	public static boolean getBoolean(Properties properties, String key)
	{
		return "true".equalsIgnoreCase(getProperty(properties, key));
	}

	/**
	 * Get a property for the node configuration.<br>
	 * Simple shortcut to <code>properties.getProperty(key)</code> for future extensibility.
	 * 
	 * @param properties - the properties to get the property from
	 * @param key - the property key
	 * @return the property value
	 */
	public static String getProperty(Properties properties, String key)
	{
		return properties.getProperty(key);
	}
}

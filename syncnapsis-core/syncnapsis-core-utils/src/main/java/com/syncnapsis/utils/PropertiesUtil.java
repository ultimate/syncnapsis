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
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		Properties properties = new Properties();
		properties.load(bis);
		bis.close();
		return properties;
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
		BufferedInputStream bis = new BufferedInputStream(PropertiesUtil.class.getClassLoader().getResourceAsStream(name));
		Properties properties = new Properties();
		properties.load(bis);
		bis.close();
		return properties;
	}
}

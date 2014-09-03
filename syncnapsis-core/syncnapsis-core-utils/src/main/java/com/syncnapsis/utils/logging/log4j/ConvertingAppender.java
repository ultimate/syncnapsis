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
package com.syncnapsis.utils.logging.log4j;

/**
 * Interface for Appender using LoggingEventConverters
 * 
 * @author ultimate
 */
public interface ConvertingAppender
{
	/**
	 * The Class of the Converter to use
	 * 
	 * @param className - the Class
	 * @throws ClassNotFoundException - if Class.forName(..) fails
	 * @throws IllegalAccessException - if class.newInstance(..) fails
	 * @throws InstantiationException - if class.newInstance(..) fails
	 */
	public void setConverterClass(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException;

	/**
	 * The Class of the Converter to use
	 * 
	 * @return the className
	 */
	public String getConverterClass();
}

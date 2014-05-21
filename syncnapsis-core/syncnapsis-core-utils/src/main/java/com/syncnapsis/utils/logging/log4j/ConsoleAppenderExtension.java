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
package com.syncnapsis.utils.logging.log4j;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * ConvertingAppender-Extension of ConsoleAppender
 * 
 * @author ultimate
 */
public class ConsoleAppenderExtension extends ConsoleAppender implements ConvertingAppender
{
	private LoggingEventConverter	converter;

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.logging.ConvertingAppender#setConverterClass(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setConverterClass(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		converter = ((Class<LoggingEventConverter>) Class.forName(className)).newInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.logging.log4j.ConvertingAppender#getConverterClass()
	 */
	@Override
	public String getConverterClass()
	{
		return converter == null ? null : converter.getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.log4j.AppenderSkeleton#doAppend(org.apache.log4j.spi.LoggingEvent)
	 */
	@Override
	public void doAppend(LoggingEvent e0)
	{
		for(LoggingEvent e2 : converter.convert(e0))
			super.doAppend(e2);
	}
}

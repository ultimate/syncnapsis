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

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.spi.LoggingEvent;

/**
 * A log4j-Appender that splits multi-line-messages to single lines and logs each one separately.
 * 
 * @author ultimate
 */
public class MultipleLineSplittingConverter implements LoggingEventConverter
{
	/**
	 * List of chars that act as a line separator. They are used to tokenize multi-line messages.
	 */
	private static String	separator	= "\n\r" + System.getProperty("line.separator");

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.logging.LoggingEventConverter#convert(org.apache.log4j.spi.LoggingEvent)
	 */
	@Override
	public List<LoggingEvent> convert(LoggingEvent e0)
	{
		List<LoggingEvent> converted = new LinkedList<LoggingEvent>();
		String msg = e0.getMessage() == null ? null : e0.getMessage().toString();
		boolean splitRequired = false;
		if(msg != null)
		{
			for(int i = 0; i < separator.length(); i++)
			{
				splitRequired = msg.contains(separator.substring(i, i + 1)) || splitRequired;
			}
		}
		if(splitRequired)
		{
			StringTokenizer st = new StringTokenizer(msg, separator);
			LoggingEvent e2;
			while(st.hasMoreTokens())
			{
				e2 = new LoggingEvent(e0.fqnOfCategoryClass, e0.getLogger(), e0.getTimeStamp(), e0.getLevel(), st.nextToken(), e0.getThreadName(),
						st.hasMoreTokens() ? null : e0.getThrowableInformation(), e0.getNDC(), e0.getLocationInformation(), e0.getProperties());
				converted.add(e2);
			}
		}
		else
		{
			converted.add(e0);
		}
		return converted;
	}
}

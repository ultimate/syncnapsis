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

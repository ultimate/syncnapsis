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

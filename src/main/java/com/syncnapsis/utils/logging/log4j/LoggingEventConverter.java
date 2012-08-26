package com.syncnapsis.utils.logging.log4j;

import java.util.List;

import org.apache.log4j.spi.LoggingEvent;

public interface LoggingEventConverter
{
	public List<LoggingEvent> convert(LoggingEvent event);
}

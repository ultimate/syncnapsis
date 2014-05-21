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

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * Class testing ConvertingAppender.
 * 
 * @author ultimate
 */
@TestCoversClasses({ ConvertingAppender.class, ConsoleAppenderExtension.class, DailyRollingFileAppenderExtension.class })
@TestExcludesMethods({ "get*", "set*", "doAppend" })
public class ConvertingAppenderTest extends LoggerTestCase
{
	public void testGetLog4jProperties()
	{
		org.apache.log4j.Logger log4jLogger = org.apache.log4j.Logger.getLogger(getClass());

		log4jLogger.debug("1\n2");
		log4jLogger.debug("1\n2", new Exception());
		
		String appenderName = "console";

		Appender appender = log4jLogger.getAppender(appenderName);
		if(appender == null)
			appender = log4jLogger.getLoggerRepository().getRootLogger().getAppender(appenderName);
		
		assertNotNull(appender);
		assertTrue(appender instanceof ConsoleAppender);
		assertTrue(appender instanceof ConsoleAppenderExtension);
		assertEquals(MultipleLineSplittingConverter.class.getName(), ((ConsoleAppenderExtension) appender).getConverterClass());
	}
}

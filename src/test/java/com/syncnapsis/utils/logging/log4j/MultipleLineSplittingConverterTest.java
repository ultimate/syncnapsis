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

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ LoggingEventConverter.class, MultipleLineSplittingConverter.class })
public class MultipleLineSplittingConverterTest extends BaseSpringContextTestCase
{
	public void testConvert() throws Exception
	{
		logger.debug("testing Event converting");

		MultipleLineSplittingConverter converter = new MultipleLineSplittingConverter();

		LoggingEvent e0;
		List<LoggingEvent> converted;

		String fqnOfCategoryClass = "Logger";
		Logger log4jLogger = Logger.getLogger(getClass());
		long timeStamp = timeProvider.get();
		Level level = Level.DEBUG;
		String msg;
		String threadName = "a-test-thread";
		ThrowableInformation throwable = new ThrowableInformation(new Exception());
		String ndc = "ndc or so what?!";
		LocationInfo info = new LocationInfo(new RuntimeException(), "this test");
		Properties properties = new Properties();

		msg = "single-line-message";
		e0 = new LoggingEvent(fqnOfCategoryClass, log4jLogger, timeStamp, level, msg, threadName, throwable, ndc, info, properties);

		converted = converter.convert(e0);
		assertEquals(1, converted.size());
		assertTrue(converted.get(0) == e0);

		String msg0 = "multi";
		String msg1 = "line";
		String msg2 = "message";

		msg = msg0 + "\n" + msg1 + "\n\n" + msg2;
		e0 = new LoggingEvent(fqnOfCategoryClass, log4jLogger, timeStamp, level, msg, threadName, throwable, ndc, info, properties);

		converted = converter.convert(e0);
		assertEquals(3, converted.size());
		assertEquals(msg0, converted.get(0).getMessage());
		assertEquals(msg1, converted.get(1).getMessage());
		assertEquals(msg2, converted.get(2).getMessage());
		assertNull(converted.get(0).getThrowableInformation());
		assertNull(converted.get(1).getThrowableInformation());
		assertNotNull(converted.get(2).getThrowableInformation());
		assertEquals(throwable, converted.get(2).getThrowableInformation());
	}
}

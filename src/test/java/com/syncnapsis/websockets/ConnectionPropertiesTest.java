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
package com.syncnapsis.websockets;

import java.util.Properties;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class ConnectionPropertiesTest extends LoggerTestCase
{
	@TestCoversMethods({"get*", "set*"})
	public void testGetAndSet() throws Exception
	{
		ConnectionProperties cp = new ConnectionProperties();
		getAndSetTest(cp, "bufferSize", int.class, int.class, 123);
		getAndSetTest(cp, "maxIdleTime", int.class, int.class, 123);
		getAndSetTest(cp, "maxTextMessageSize", int.class, int.class, 123);
		getAndSetTest(cp, "maxBinaryMessageSize", int.class, int.class, 123);
	}

	public void testFromProperties() throws Exception
	{
		int bufferSize = 123;
		int maxIdleTime = 234;
		int maxTextMessageSize = 345;
		int maxBinaryMessageSize = 456;
		
		Properties p = new Properties();
		p.setProperty(ConnectionProperties.KEY_BUFFER_SIZE, "" + bufferSize);
		p.setProperty(ConnectionProperties.KEY_MAX_IDLE_TIME, "" + maxIdleTime);
		p.setProperty(ConnectionProperties.KEY_MAX_TEXT_MESSAGE_SIZE, "" + maxTextMessageSize);
		p.setProperty(ConnectionProperties.KEY_MAX_BINARY_MESSAGE_SIZE, "" + maxBinaryMessageSize);
		
		ConnectionProperties cp = new ConnectionProperties(p);
		
		assertEquals(bufferSize, cp.getBufferSize());
		assertEquals(maxIdleTime, cp.getMaxIdleTime());
		assertEquals(maxTextMessageSize, cp.getMaxTextMessageSize());
		assertEquals(maxBinaryMessageSize, cp.getMaxBinaryMessageSize());
	}
}
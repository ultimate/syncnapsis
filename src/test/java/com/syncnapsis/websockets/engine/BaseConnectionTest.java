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
package com.syncnapsis.websockets.engine;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.mock.MockProtocol;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.websockets.Message;
import com.syncnapsis.websockets.Protocol;
import com.syncnapsis.websockets.Service;
import com.syncnapsis.websockets.WebSocketManager;
import com.syncnapsis.websockets.service.EchoService;

public class BaseConnectionTest extends LoggerTestCase
{
	@TestCoversMethods({ "get*", "set*" })
	public void testGetAndSet() throws Exception
	{
		DummyConnection c = new DummyConnection();

		// simple properties
		getAndSetTest(c, "manager", WebSocketManager.class, WebSocketManager.class, new WebSocketManager());
		getAndSetTest(c, "service", Service.class, Service.class, new EchoService());
		getAndSetTest(c, "protocol", Protocol.class, Protocol.class, new MockProtocol());
		getAndSetTest(c, "subprotocol", String.class, String.class, "abc");
		getAndSetTest(c, "id", String.class, String.class, "1a2b3c");
		getAndSetTest(c, "allowFrameFragmentation", boolean.class, boolean.class, true);
		getAndSetTest(c, "session", HttpSession.class, HttpSession.class, new MockHttpSession());

		// attribute map
		String key = "aKey";
		String value = "aValue";
		c.setAttribute(key, value);
		assertEquals(value, c.getAttribute(key));
	}

	private class DummyConnection extends BaseConnection
	{
		@Override
		public void sendMessage(Message message) throws IOException
		{
			// not needed here
		}

		@Override
		public void sendMessage(byte[] data, int offset, int length) throws IOException
		{
			// not needed here
		}

		@Override
		public void sendMessage(String data) throws IOException
		{
			// not needed here
		}

		@Override
		public void sendFrame(byte flags, byte opCode, byte[] data, int offset, int length) throws IOException
		{
			// not needed here
		}

		@Override
		public void sendControl(byte controlCode, byte[] data, int offset, int length) throws IOException
		{
			// not needed here
		}

		@Override
		public boolean isOpen()
		{
			// not needed here
			return false;
		}

		@Override
		public void close(int closeCode, String message)
		{
			// not needed here
		}

		@Override
		public void close()
		{
			// not needed here
		}
	};
}
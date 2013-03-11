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
package com.syncnapsis.websockets.engine.http;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.websockets.Message;

/**
 * Test for {@link HttpConnection}
 * 
 * @author ultimate
 */
public class HttpConnectionTest extends LoggerTestCase
{
	@TestCoversMethods({"send*", "addMessage", "*MessageBuffer"})
	public void testSendMessage() throws Exception
	{
		String subprotocol = "my_protocol";
		HttpConnection connection = new HttpConnection(subprotocol);
		connection.setProtocol(new HttpProtocol());

		assertEquals(0, connection.getMessageBuffer().size());
		
		String message1 = "message#1";
		String message2 = "message#2";
		String message3 = "message#3";
		String control  = "message#4";
		String frame    = "message#5";

		connection.sendMessage(new Message(message1, connection.getProtocol()));
		connection.sendMessage(message2);
		connection.sendMessage(message3.getBytes(), 0, message3.getBytes().length);
		connection.sendControl(connection.getProtocol().pingOpCode(), control.getBytes(), 0, control.getBytes().length);
		connection.sendFrame(connection.getProtocol().binaryOpCode(), connection.getProtocol().pingOpCode(), frame.getBytes(), 0, frame.getBytes().length);
		
		assertEquals(5, connection.getMessageBuffer().size());
		
		assertEquals(message1, connection.getMessageBuffer().get(0).getDataString());
		assertEquals(message2, connection.getMessageBuffer().get(1).getDataString());
		assertEquals(message3, connection.getMessageBuffer().get(2).getDataString());
		assertEquals(control, connection.getMessageBuffer().get(3).getDataString());
		assertEquals(frame, connection.getMessageBuffer().get(4).getDataString());
		
		// TODO no test yet
		fail("no test yet");
	}

	@TestCoversMethods({ "isOpen", "close" })
	public void testOpenClose() throws Exception
	{
		String subprotocol = "my_protocol";
		HttpConnection connection = new HttpConnection(subprotocol);
		connection.setProtocol(new HttpProtocol());

		assertEquals(subprotocol, connection.getSubprotocol());

		assertTrue(connection.isOpen());
		assertEquals(0, connection.getMessageBuffer().size());

		connection.close();
		assertEquals(1, connection.getMessageBuffer().size());
		assertFalse(connection.isOpen());
	}
}
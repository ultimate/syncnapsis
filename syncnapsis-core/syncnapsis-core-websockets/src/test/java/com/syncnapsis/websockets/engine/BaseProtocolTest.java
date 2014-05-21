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

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class BaseProtocolTest extends LoggerTestCase
{
	private BaseProtocol	protocol;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		protocol = new BaseProtocol();
	}

	@TestCoversMethods({ "textOpCode", "isText" })
	public void testTextOpCode()
	{
		byte opCode = protocol.textOpCode();

		assertEquals(true, protocol.isText(opCode));
		assertEquals(false, protocol.isBinary(opCode));
		assertEquals(false, protocol.isControl(opCode));
		assertEquals(false, protocol.isContinuation(opCode));
		assertEquals(false, protocol.isClose(opCode));
		assertEquals(false, protocol.isPing(opCode));
		assertEquals(false, protocol.isPong(opCode));
	}

	@TestCoversMethods({ "binaryOpCode", "isBinary" })
	public void testBinaryOpCode()
	{
		byte opCode = protocol.binaryOpCode();

		assertEquals(false, protocol.isText(opCode));
		assertEquals(true, protocol.isBinary(opCode));
		assertEquals(false, protocol.isControl(opCode));
		assertEquals(false, protocol.isContinuation(opCode));
		assertEquals(false, protocol.isClose(opCode));
		assertEquals(false, protocol.isPing(opCode));
		assertEquals(false, protocol.isPong(opCode));
	}

	@TestCoversMethods({ "continuationOpCode", "isContinuation" })
	public void testContinuationOpCode()
	{
		byte opCode = protocol.continuationOpCode();

		assertEquals(false, protocol.isText(opCode));
		assertEquals(false, protocol.isBinary(opCode));
		assertEquals(false, protocol.isControl(opCode));
		assertEquals(true, protocol.isContinuation(opCode));
		assertEquals(false, protocol.isClose(opCode));
		assertEquals(false, protocol.isPing(opCode));
		assertEquals(false, protocol.isPong(opCode));
	}

	@TestCoversMethods({ "closeOpCode", "isClose", "isControl" })
	public void testCloseOpCode()
	{
		byte opCode = protocol.closeOpCode();

		assertEquals(false, protocol.isText(opCode));
		assertEquals(false, protocol.isBinary(opCode));
		assertEquals(false, protocol.isControl(opCode));
		assertEquals(false, protocol.isContinuation(opCode));
		assertEquals(true, protocol.isClose(opCode));
		assertEquals(false, protocol.isPing(opCode));
		assertEquals(false, protocol.isPong(opCode));
	}

	@TestCoversMethods({ "pingOpCode", "isPing", "isControl" })
	public void testPingOpCode()
	{
		byte opCode = protocol.pingOpCode();

		assertEquals(false, protocol.isText(opCode));
		assertEquals(false, protocol.isBinary(opCode));
		assertEquals(true, protocol.isControl(opCode));
		assertEquals(false, protocol.isContinuation(opCode));
		assertEquals(false, protocol.isClose(opCode));
		assertEquals(true, protocol.isPing(opCode));
		assertEquals(false, protocol.isPong(opCode));
	}

	@TestCoversMethods({ "pongOpCode", "isPong", "isControl" })
	public void testPongOpCode()
	{
		byte opCode = protocol.pongOpCode();

		assertEquals(false, protocol.isText(opCode));
		assertEquals(false, protocol.isBinary(opCode));
		assertEquals(true, protocol.isControl(opCode));
		assertEquals(false, protocol.isContinuation(opCode));
		assertEquals(false, protocol.isClose(opCode));
		assertEquals(false, protocol.isPing(opCode));
		assertEquals(true, protocol.isPong(opCode));
	}

	@TestCoversMethods({ "isMessageComplete", "finMask" })
	public void testIsMessageComplete()
	{
		String[] masks = { "00000001", "00000010", "00000100", "00001000", "00010000", "00100000", "01000000", "10000000" };
		assertEquals(false, protocol.isMessageComplete((byte) Integer.parseInt("00000000", 2)));
		assertEquals(true, protocol.isMessageComplete((byte) Integer.parseInt("11111111", 2)));

		byte mask;
		for(String maskS : masks)
		{
			mask = (byte) Integer.parseInt(maskS, 2);
			assertEquals(protocol.finMask() == mask, protocol.isMessageComplete(mask));
		}
	}
}
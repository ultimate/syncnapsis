/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
package com.syncnapsis.utils;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class ByteUtilTest extends LoggerTestCase
{
	@TestCoversMethods({ "toByteArray", "toInt" })
	public void testFromAndToInt()
	{
		for(int data = -100; data <= 100; data++)
		{
			assertEquals(data, ByteUtil.toInt(ByteUtil.toByteArray(data)));
		}
	}

	@TestCoversMethods({ "toByteArray", "toLong" })
	public void testFromAndToLong()
	{
		for(int data = -100; data <= 100; data++)
		{
			assertEquals((long) data, ByteUtil.toLong(ByteUtil.toByteArray((long) data)));
		}
	}

	@TestCoversMethods({ "toByteArray", "toFloat" })
	public void testFromAndToFloat()
	{
		for(int data = -100; data <= 100; data++)
		{
			assertEquals((float) data, ByteUtil.toFloat(ByteUtil.toByteArray((float) data)));
		}
	}

	@TestCoversMethods({ "toByteArray", "toDouble" })
	public void testFromAndToDouble()
	{
		for(int data = -100; data <= 100; data++)
		{
			assertEquals((double) data, ByteUtil.toDouble(ByteUtil.toByteArray((double) data)));
		}
	}

	@TestCoversMethods({ "toByteArray", "toBoolean" })
	public void testFromAndToBoolean()
	{
		assertEquals(false, ByteUtil.toBoolean(ByteUtil.toByteArray(false)));
		assertEquals(true, ByteUtil.toBoolean(ByteUtil.toByteArray(true)));
	}
}

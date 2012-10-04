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

package com.syncnapsis.utils;

import java.util.HashMap;
import java.util.Map;

import com.syncnapsis.tests.LoggerTestCase;

public class MapUtilTest extends LoggerTestCase
{

	public void testGetLong() throws Exception
	{
		Map<String, Object> map = createTestMap();
		
		assertEquals(new Long(1), MapUtil.getLong(map, "int_string", false));
		assertEquals(new Long(1), MapUtil.getLong(map, "int_string", true));
		
		assertEquals(new Long(1), MapUtil.getLong(map, "dec_string", false));
		assertEquals(new Long(1), MapUtil.getLong(map, "dec_string", true));
		
		assertEquals(new Long(1), MapUtil.getLong(map, "int", false));
		assertEquals(new Long(1), MapUtil.getLong(map, "int", true));
		
		assertEquals(new Long(1), MapUtil.getLong(map, "dec", false));
		assertEquals(new Long(1), MapUtil.getLong(map, "dec", true));
		
		assertEquals(new Long(0), MapUtil.getLong(map, "null", false));
		assertEquals(null, MapUtil.getLong(map, "null", true));
		
		assertEquals(new Long(0), MapUtil.getLong(map, "nan", false));
		assertEquals(null, MapUtil.getLong(map, "nan", true));
	}

	public void testGetInteger() throws Exception
	{
		Map<String, Object> map = createTestMap();
		
		assertEquals(new Integer(1), MapUtil.getInteger(map, "int_string", false));
		assertEquals(new Integer(1), MapUtil.getInteger(map, "int_string", true));
		
		assertEquals(new Integer(1), MapUtil.getInteger(map, "dec_string", false));
		assertEquals(new Integer(1), MapUtil.getInteger(map, "dec_string", true));
		
		assertEquals(new Integer(1), MapUtil.getInteger(map, "int", false));
		assertEquals(new Integer(1), MapUtil.getInteger(map, "int", true));
		
		assertEquals(new Integer(1), MapUtil.getInteger(map, "dec", false));
		assertEquals(new Integer(1), MapUtil.getInteger(map, "dec", true));
		
		assertEquals(new Integer(0), MapUtil.getInteger(map, "null", false));
		assertEquals(null, MapUtil.getInteger(map, "null", true));
		
		assertEquals(new Integer(0), MapUtil.getInteger(map, "nan", false));
		assertEquals(null, MapUtil.getInteger(map, "nan", true));
	}

	public void testGetShort() throws Exception
	{
		Map<String, Object> map = createTestMap();
		
		assertEquals(new Short((short)1), MapUtil.getShort(map, "int_string", false));
		assertEquals(new Short((short)1), MapUtil.getShort(map, "int_string", true));
		
		assertEquals(new Short((short)1), MapUtil.getShort(map, "dec_string", false));
		assertEquals(new Short((short)1), MapUtil.getShort(map, "dec_string", true));
		
		assertEquals(new Short((short)1), MapUtil.getShort(map, "int", false));
		assertEquals(new Short((short)1), MapUtil.getShort(map, "int", true));
		
		assertEquals(new Short((short)1), MapUtil.getShort(map, "dec", false));
		assertEquals(new Short((short)1), MapUtil.getShort(map, "dec", true));
		
		assertEquals(new Short((short)0), MapUtil.getShort(map, "null", false));
		assertEquals(null, MapUtil.getShort(map, "null", true));
		
		assertEquals(new Short((short)0), MapUtil.getShort(map, "nan", false));
		assertEquals(null, MapUtil.getShort(map, "nan", true));
	}

	public void testGetByte() throws Exception
	{
		Map<String, Object> map = createTestMap();
		
		assertEquals(new Byte((byte)1), MapUtil.getByte(map, "int_string", false));
		assertEquals(new Byte((byte)1), MapUtil.getByte(map, "int_string", true));
		
		assertEquals(new Byte((byte)1), MapUtil.getByte(map, "dec_string", false));
		assertEquals(new Byte((byte)1), MapUtil.getByte(map, "dec_string", true));
		
		assertEquals(new Byte((byte)1), MapUtil.getByte(map, "int", false));
		assertEquals(new Byte((byte)1), MapUtil.getByte(map, "int", true));
		
		assertEquals(new Byte((byte)1), MapUtil.getByte(map, "dec", false));
		assertEquals(new Byte((byte)1), MapUtil.getByte(map, "dec", true));
		
		assertEquals(new Byte((byte)0), MapUtil.getByte(map, "null", false));
		assertEquals(null, MapUtil.getByte(map, "null", true));
		
		assertEquals(new Byte((byte)0), MapUtil.getByte(map, "nan", false));
		assertEquals(null, MapUtil.getByte(map, "nan", true));
	}

	public void testGetDouble() throws Exception
	{
		Map<String, Object> map = createTestMap();
		
		assertEquals(new Double(1), MapUtil.getDouble(map, "int_string", false, false));
		assertEquals(new Double(1), MapUtil.getDouble(map, "int_string", false, true));
		assertEquals(new Double(1), MapUtil.getDouble(map, "int_string", true, false));
		assertEquals(new Double(1), MapUtil.getDouble(map, "int_string", true, true));

		assertEquals(new Double(1), MapUtil.getDouble(map, "dec_string", false, false));
		assertEquals(new Double(1), MapUtil.getDouble(map, "dec_string", false, true));
		assertEquals(new Double(1), MapUtil.getDouble(map, "dec_string", true, false));
		assertEquals(new Double(1), MapUtil.getDouble(map, "dec_string", true, true));

		assertEquals(new Double(1), MapUtil.getDouble(map, "int", false, false));
		assertEquals(new Double(1), MapUtil.getDouble(map, "int", false, true));
		assertEquals(new Double(1), MapUtil.getDouble(map, "int", true, false));
		assertEquals(new Double(1), MapUtil.getDouble(map, "int", true, true));

		assertEquals(new Double(1), MapUtil.getDouble(map, "dec", false, false));
		assertEquals(new Double(1), MapUtil.getDouble(map, "dec", false, true));
		assertEquals(new Double(1), MapUtil.getDouble(map, "dec", true, false));
		assertEquals(new Double(1), MapUtil.getDouble(map, "dec", true, true));

		assertEquals(new Double(0), MapUtil.getDouble(map, "null", false, false));
		assertEquals(Double.NaN, MapUtil.getDouble(map, "null", false, true));
		assertEquals(null, MapUtil.getDouble(map, "null", true, false));
		assertEquals(null, MapUtil.getDouble(map, "null", true, true));
		
		assertEquals(new Double(0), MapUtil.getDouble(map, "nan", false, false));
		assertEquals(Double.NaN, MapUtil.getDouble(map, "nan", false, true));
		assertEquals(null, MapUtil.getDouble(map, "nan", true, false));
		assertEquals(null, MapUtil.getDouble(map, "nan", true, true));
	}

	public void testGetFloat() throws Exception
	{
		Map<String, Object> map = createTestMap();
		
		assertEquals(new Float(1), MapUtil.getFloat(map, "int_string", false, false));
		assertEquals(new Float(1), MapUtil.getFloat(map, "int_string", false, true));
		assertEquals(new Float(1), MapUtil.getFloat(map, "int_string", true, false));
		assertEquals(new Float(1), MapUtil.getFloat(map, "int_string", true, true));

		assertEquals(new Float(1), MapUtil.getFloat(map, "dec_string", false, false));
		assertEquals(new Float(1), MapUtil.getFloat(map, "dec_string", false, true));
		assertEquals(new Float(1), MapUtil.getFloat(map, "dec_string", true, false));
		assertEquals(new Float(1), MapUtil.getFloat(map, "dec_string", true, true));

		assertEquals(new Float(1), MapUtil.getFloat(map, "int", false, false));
		assertEquals(new Float(1), MapUtil.getFloat(map, "int", false, true));
		assertEquals(new Float(1), MapUtil.getFloat(map, "int", true, false));
		assertEquals(new Float(1), MapUtil.getFloat(map, "int", true, true));

		assertEquals(new Float(1), MapUtil.getFloat(map, "dec", false, false));
		assertEquals(new Float(1), MapUtil.getFloat(map, "dec", false, true));
		assertEquals(new Float(1), MapUtil.getFloat(map, "dec", true, false));
		assertEquals(new Float(1), MapUtil.getFloat(map, "dec", true, true));

		assertEquals(new Float(0), MapUtil.getFloat(map, "null", false, false));
		assertEquals(Float.NaN, MapUtil.getFloat(map, "null", false, true));
		assertEquals(null, MapUtil.getFloat(map, "null", true, false));
		assertEquals(null, MapUtil.getFloat(map, "null", true, true));
		
		assertEquals(new Float(0), MapUtil.getFloat(map, "nan", false, false));
		assertEquals(Float.NaN, MapUtil.getFloat(map, "nan", false, true));
		assertEquals(null, MapUtil.getFloat(map, "nan", true, false));
		assertEquals(null, MapUtil.getFloat(map, "nan", true, true));
	}

	private Map<String, Object> createTestMap()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("int_string", "1");
		map.put("dec_string", "1.0");
		map.put("int", 1);
		map.put("dec", 1.0);
		map.put("null", null);
		map.put("nan", "aaa");
		return map;
	}
}

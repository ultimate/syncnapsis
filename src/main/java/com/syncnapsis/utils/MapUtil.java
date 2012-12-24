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
package com.syncnapsis.utils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Utility Class allowing comfortable access to Maps and their values.
 * 
 * @author ultimate
 */
public abstract class MapUtil
{
	/**
	 * Get a Long-Value from a Map.
	 * The value represented by the key will be checked for the correct type and parsed if
	 * necessary (and possible).
	 * 
	 * @param map - the Map to lookup the value
	 * @param key - the key for the value
	 * @param nullAllowed - if null should be returned, when no value is found (otherwise 0)
	 * @return the Long value
	 */
	public static <K> Long getLong(Map<K, ?> map, K key, boolean nullAllowed)
	{
		Object value = map.get(key);
		if(value instanceof Long)
			return (Long) value;
		if(value instanceof Number)
			return ((Number) value).longValue();
		if(value instanceof String)
		{
			try
			{
				return new BigDecimal((String) value).longValue();
			}
			catch(NumberFormatException e)
			{
				// ignore
			}
		}
		if(nullAllowed)
			return null;
		return 0L;
	}

	/**
	 * Get a Integer-Value from a Map.
	 * The value represented by the key will be checked for the correct type and parsed if
	 * necessary (and possible).
	 * 
	 * @param map - the Map to lookup the value
	 * @param key - the key for the value
	 * @param nullAllowed - if null should be returned, when no value is found (otherwise 0)
	 * @return the Integer value
	 */
	public static <K> Integer getInteger(Map<K, ?> map, K key, boolean nullAllowed)
	{
		Object value = map.get(key);
		if(value instanceof Integer)
			return (Integer) value;
		if(value instanceof Number)
			return ((Number) value).intValue();
		if(value instanceof String)
		{
			try
			{
				return new BigDecimal((String) value).intValue();
			}
			catch(NumberFormatException e)
			{
				// ignore
			}
		}
		if(nullAllowed)
			return null;
		return 0;
	}

	/**
	 * Get a Short-Value from a Map.
	 * The value represented by the key will be checked for the correct type and parsed if
	 * necessary (and possible).
	 * 
	 * @param map - the Map to lookup the value
	 * @param key - the key for the value
	 * @param nullAllowed - if null should be returned, when no value is found (otherwise 0)
	 * @return the Short value
	 */
	public static <K> Short getShort(Map<K, ?> map, K key, boolean nullAllowed)
	{
		Object value = map.get(key);
		if(value instanceof Short)
			return (Short) value;
		if(value instanceof Number)
			return ((Number) value).shortValue();
		if(value instanceof String)
		{
			try
			{
				return new BigDecimal((String) value).shortValue();
			}
			catch(NumberFormatException e)
			{
				// ignore
			}
		}
		if(nullAllowed)
			return null;
		return 0;
	}

	/**
	 * Get a Byte-Value from a Map.
	 * The value represented by the key will be checked for the correct type and parsed if
	 * necessary (and possible).
	 * 
	 * @param map - the Map to lookup the value
	 * @param key - the key for the value
	 * @param nullAllowed - if null should be returned, when no value is found (otherwise 0)
	 * @return the Byte value
	 */
	public static <K> Byte getByte(Map<K, ?> map, K key, boolean nullAllowed)
	{
		Object value = map.get(key);
		if(value instanceof Byte)
			return (Byte) value;
		if(value instanceof Number)
			return ((Number) value).byteValue();
		if(value instanceof String)
		{
			try
			{
				return new BigDecimal((String) value).byteValue();
			}
			catch(NumberFormatException e)
			{
				// ignore
			}
		}
		if(nullAllowed)
			return null;
		return 0;
	}

	/**
	 * Get a Double-Value from a Map.
	 * The value represented by the key will be checked for the correct type and parsed if
	 * necessary (and possible).
	 * 
	 * @param map - the Map to lookup the value
	 * @param key - the key for the value
	 * @param nullAllowed - if null should be returned, when no value is found (otherwise 0.0)
	 * @param nanAllowed - if nan should be returned, when no value is found (otherwise 0.0)
	 * @return the Double value
	 */
	public static <K> Double getDouble(Map<K, ?> map, K key, boolean nullAllowed, boolean nanAllowed)
	{
		Object value = map.get(key);
		if(value instanceof Double)
			return (Double) value;
		if(value instanceof Number)
			return ((Number) value).doubleValue();
		if(value instanceof String)
		{
			try
			{
				return new BigDecimal((String) value).doubleValue();
			}
			catch(NumberFormatException e)
			{
				// ignore
			}
		}
		if(nullAllowed)
			return null;
		if(nanAllowed)
			return Double.NaN;
		return 0.0;
	}

	/**
	 * Get a Float-Value from a Map.
	 * The value represented by the key will be checked for the correct type and parsed if
	 * necessary (and possible).
	 * 
	 * @param map - the Map to lookup the value
	 * @param key - the key for the value
	 * @param nullAllowed - if null should be returned, when no value is found (otherwise 0.0)
	 * @param nanAllowed - if nan should be returned, when no value is found (otherwise 0.0)
	 * @return the Float value
	 */
	public static <K> Float getFloat(Map<K, ?> map, K key, boolean nullAllowed, boolean nanAllowed)
	{
		Object value = map.get(key);
		if(value instanceof Float)
			return (Float) value;
		if(value instanceof Number)
			return ((Number) value).floatValue();
		if(value instanceof String)
		{
			try
			{
				return new BigDecimal((String) value).floatValue();
			}
			catch(NumberFormatException e)
			{
				// ignore
			}
		}
		if(nullAllowed)
			return null;
		if(nanAllowed)
			return Float.NaN;
		return 0.0F;
	}
}

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
package com.syncnapsis.utils.math;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({ "*Value", "hashCode", "equals" })
public class RomanTest extends LoggerTestCase
{
	public void testParseRoman()
	{
		assertEquals(1, Roman.parseRoman("I"));
		assertEquals(2, Roman.parseRoman("II"));
		assertEquals(3, Roman.parseRoman("III"));
		assertEquals(4, Roman.parseRoman("IV"));
		assertEquals(5, Roman.parseRoman("V"));
		assertEquals(6, Roman.parseRoman("VI"));
		assertEquals(7, Roman.parseRoman("VII"));
		assertEquals(8, Roman.parseRoman("VIII"));
		assertEquals(9, Roman.parseRoman("IX"));
		assertEquals(10, Roman.parseRoman("X"));
		assertEquals(49, Roman.parseRoman("XLIX"));
		assertEquals(1999, Roman.parseRoman("MCMXCIX"));
	}

	public void testParseRoman_invalid()
	{
		try
		{
			Roman.parseRoman("A");
			fail("expected exception not occurred");
		}
		catch(NumberFormatException e)
		{
			assertNotNull(e);
		}
	}

	public void testParseFromToString()
	{
		for(int i = 1; i <= 2000; i++)
		{
			assertEquals(i, Roman.parseRoman(Roman.toString(i)));
		}
	}

	public void testToString()
	{
		assertEquals("I", Roman.toString(1));
		assertEquals("II", Roman.toString(2));
		assertEquals("III", Roman.toString(3));
		assertEquals("IV", Roman.toString(4));
		assertEquals("V", Roman.toString(5));
		assertEquals("VI", Roman.toString(6));
		assertEquals("VII", Roman.toString(7));
		assertEquals("VIII", Roman.toString(8));
		assertEquals("IX", Roman.toString(9));
		assertEquals("X", Roman.toString(10));
		assertEquals("XLIX", Roman.toString(49));
		assertEquals("MCMXCIX", Roman.toString(1999));
	}

	public void testToString_invalid()
	{
		try
		{
			Roman.toString(0);
			fail("expected exception not occurred");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}
}

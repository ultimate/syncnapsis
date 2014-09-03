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
package com.syncnapsis.utils.reflections;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.utils.reflections.MethodComparator;

public class MethodComparatorTest extends LoggerTestCase
{
	public void testCompare() throws Exception
	{
		ObjectToCompare objectToCompareA = new ObjectToCompare("a");
		ObjectToCompare objectToCompareB = new ObjectToCompare("b");
		ObjectToCompare objectToCompareC = new ObjectToCompare("c");
		
		assertEquals(-1, 	new MethodComparator<ObjectToCompare>("getName", +1).compare(objectToCompareA, objectToCompareB));
		assertEquals(+1, 	new MethodComparator<ObjectToCompare>("getName", +1).compare(objectToCompareB, objectToCompareA));
		assertEquals(+1, 	new MethodComparator<ObjectToCompare>("getName", -1).compare(objectToCompareA, objectToCompareB));
		assertEquals(-1, 	new MethodComparator<ObjectToCompare>("getName", -1).compare(objectToCompareB, objectToCompareA));
		assertEquals(-2, 	new MethodComparator<ObjectToCompare>("getName", +2).compare(objectToCompareA, objectToCompareB));
		assertEquals(+2, 	new MethodComparator<ObjectToCompare>("getName", +2).compare(objectToCompareB, objectToCompareA));
		assertEquals(+2, 	new MethodComparator<ObjectToCompare>("getName", -2).compare(objectToCompareA, objectToCompareB));
		assertEquals(-2, 	new MethodComparator<ObjectToCompare>("getName", -2).compare(objectToCompareB, objectToCompareA));
		
		assertEquals(-2, 	new MethodComparator<ObjectToCompare>("getName", +1).compare(objectToCompareA, objectToCompareC));
		assertEquals(+2, 	new MethodComparator<ObjectToCompare>("getName", +1).compare(objectToCompareC, objectToCompareA));
		assertEquals(+2, 	new MethodComparator<ObjectToCompare>("getName", -1).compare(objectToCompareA, objectToCompareC));
		assertEquals(-2, 	new MethodComparator<ObjectToCompare>("getName", -1).compare(objectToCompareC, objectToCompareA));
		assertEquals(-4, 	new MethodComparator<ObjectToCompare>("getName", +2).compare(objectToCompareA, objectToCompareC));
		assertEquals(+4, 	new MethodComparator<ObjectToCompare>("getName", +2).compare(objectToCompareC, objectToCompareA));
		assertEquals(+4, 	new MethodComparator<ObjectToCompare>("getName", -2).compare(objectToCompareA, objectToCompareC));
		assertEquals(-4, 	new MethodComparator<ObjectToCompare>("getName", -2).compare(objectToCompareC, objectToCompareA));
		
		assertEquals(-1, 	new MethodComparator<ObjectToCompare>("getName", +1).compare(objectToCompareB, objectToCompareC));
		assertEquals(+1, 	new MethodComparator<ObjectToCompare>("getName", +1).compare(objectToCompareC, objectToCompareB));
		assertEquals(+1, 	new MethodComparator<ObjectToCompare>("getName", -1).compare(objectToCompareB, objectToCompareC));
		assertEquals(-1, 	new MethodComparator<ObjectToCompare>("getName", -1).compare(objectToCompareC, objectToCompareB));
		assertEquals(-2, 	new MethodComparator<ObjectToCompare>("getName", +2).compare(objectToCompareB, objectToCompareC));
		assertEquals(+2, 	new MethodComparator<ObjectToCompare>("getName", +2).compare(objectToCompareC, objectToCompareB));
		assertEquals(+2, 	new MethodComparator<ObjectToCompare>("getName", -2).compare(objectToCompareB, objectToCompareC));
		assertEquals(-2, 	new MethodComparator<ObjectToCompare>("getName", -2).compare(objectToCompareC, objectToCompareB));
		
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", +1).compare(objectToCompareA, objectToCompareA));
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", -1).compare(objectToCompareA, objectToCompareA));
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", +2).compare(objectToCompareA, objectToCompareA));
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", -2).compare(objectToCompareA, objectToCompareA));
		
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", +1).compare(objectToCompareB, objectToCompareB));
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", -1).compare(objectToCompareB, objectToCompareB));
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", +2).compare(objectToCompareB, objectToCompareB));
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", -2).compare(objectToCompareB, objectToCompareB));
		
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", +1).compare(objectToCompareC, objectToCompareC));
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", -1).compare(objectToCompareC, objectToCompareC));
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", +2).compare(objectToCompareC, objectToCompareC));
		assertEquals(0, 	new MethodComparator<ObjectToCompare>("getName", -2).compare(objectToCompareC, objectToCompareC));
		
		try
		{
			new MethodComparator<ObjectToCompare>("getName", 0);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		try
		{
			new MethodComparator<ObjectToCompare>("getName", 1).compare(null, new ObjectToCompare("x"));
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
			assertTrue(e.getMessage().startsWith("o1"));
		}
		try
		{
			new MethodComparator<ObjectToCompare>("getName", 1).compare(new ObjectToCompare("x"), null);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
			assertTrue(e.getMessage().startsWith("o2"));
		}
	}
	
	public class ObjectToCompare
	{
		private String name;
		
		public ObjectToCompare(String name)
		{
			this.name = name;
		}
		
		public String getName()
		{
			return name;
		}
	}
}

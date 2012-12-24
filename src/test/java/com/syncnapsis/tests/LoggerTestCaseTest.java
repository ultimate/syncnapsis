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
package com.syncnapsis.tests;

import junit.framework.AssertionFailedError;

public class LoggerTestCaseTest extends LoggerTestCase
{
	public void testAssertEquals() throws Exception
	{
		Integer[] a1 = new Integer[] { 1, 2, 3 };
		Integer[] a2 = a1;
		Integer[] a3 = new Integer[] { 1, 2, 3 };
		Integer[] a4 = new Integer[] { 4, 5, 6 };
		Integer[] a5 = new Integer[] { 1, 2 };
		Integer[] a6 = new Integer[] { 3, 2, 1 };
		Object[] ao = new Object[] { 1, 2, 3 };
		String[] as = new String[] { "1", "2", "3" };
		
		assertEquals(a1, a2);
		assertEquals(a1, a3);
		assertEquals(a1, ao);
		failEquals(a1, a4);
		failEquals(a1, a5);
		failEquals(a1, a6);
		failEquals(a4, ao);
		failEquals(a1, as);
		failEquals(a3, as);
		failEquals(ao, as);
	}
	
	private <T1, T2> void failEquals(T1[] a1, T2[] a2) throws Exception
	{
		try
		{
			assertEquals(a1, a2);
		}
		catch(AssertionFailedError e)
		{
			// expected
			logger.debug("expected Error", e);
			return;
		}
		fail("expected Exception not occurred!");
	}
}

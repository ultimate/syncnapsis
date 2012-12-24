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
package com.syncnapsis.utils.collections;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class LimitedQueueTest extends LoggerTestCase
{
	@TestCoversMethods("*")
	public void testQueue() throws Exception
	{
		LimitedQueue<String> q = new LimitedQueue<String>(3);
		
		q.add("a");
		assertEquals("a", q.peek());
		q.add("b");
		assertEquals("a", q.peek());
		q.add("c");
		assertEquals("a", q.peek());
		q.add("d");
		assertEquals("b", q.peek());
		q.remove();
		assertEquals("c", q.peek());
	}
}

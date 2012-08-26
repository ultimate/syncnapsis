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

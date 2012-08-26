package com.syncnapsis.providers;

import com.syncnapsis.providers.impl.ThreadLocalSessionProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({ Provider.class, ThreadLocalProvider.class, ThreadLocalSessionProvider.class, SessionProvider.class })
public class ThreadLocalProviderTest extends LoggerTestCase
{
	@TestCoversMethods("*")
	public void testProvider() throws Exception
	{
		final Provider<Integer> p = new ThreadLocalProvider<Integer>();

		final int val1 = 111;
		final int val2 = 222;

		p.set(val1);

		assertEquals(val1, (int) p.get());

		TestThread<Integer> t = new TestThread<Integer>(p, val2);
		
		t.start();
		t.join();

		assertEquals(val1, (int) p.get());
		assertEquals(val2, (int) t.val);
	}

	public class TestThread<T> extends Thread
	{
		Provider<T>	p;
		public T	val;

		public TestThread(Provider<T> p, T val)
		{
			this.p = p;
			this.val = val;
		}

		public void run()
		{
			p.set(val);
			val = null;
			val = p.get();
		}
	};
}

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
package com.syncnapsis.providers.impl;

import com.syncnapsis.providers.Provider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.engine.http.HttpConnection;

public class ThreadLocalConnectionProviderTest extends LoggerTestCase
{
	@TestCoversMethods("*")
	public void testProvider() throws Exception
	{
		final ThreadLocalConnectionProvider p = new ThreadLocalConnectionProvider();

		final Connection val1 = new HttpConnection("a");
		final Connection val2 = new HttpConnection("b");

		p.set(val1);

		assertEquals(val1, p.get());

		TestThread<Connection> t = new TestThread<Connection>(p, val2);
		
		t.start();
		t.join();

		assertEquals(val1, p.get());
		assertEquals(val2, t.val);
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

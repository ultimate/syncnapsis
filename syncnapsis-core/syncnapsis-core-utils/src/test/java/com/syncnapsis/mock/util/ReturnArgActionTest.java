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
package com.syncnapsis.mock.util;

import org.jmock.Expectations;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 * 
 */
@TestExcludesMethods("describeTo")
public class ReturnArgActionTest extends LoggerTestCase
{
	public void testInvoke()
	{
		final SingleArg s = mockContext.mock(SingleArg.class);
		final MultipleArg m = mockContext.mock(MultipleArg.class);

		Object o0 = new Object();
		Object o1 = new Object();
		Object o2 = new Object();

		mockContext.checking(new Expectations() {
			{
				allowing(s).returnArg(with(any(Object.class)));
				will(new ReturnArgAction());
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(m).returnArg(with(any(Object.class)), with(any(Object.class)), with(any(Object.class)));
				will(onConsecutiveCalls(new ReturnArgAction(0), new ReturnArgAction(1), new ReturnArgAction(2)));
			}
		});
		
		assertSame(o0, s.returnArg(o0));
		assertSame(o1, s.returnArg(o1));
		assertSame(o2, s.returnArg(o2));

		assertSame(o0, m.returnArg(o0, o1, o2));
		assertSame(o1, m.returnArg(o0, o1, o2));
		assertSame(o2, m.returnArg(o0, o1, o2));
	}

	private interface SingleArg
	{
		public Object returnArg(Object arg);
	}

	private interface MultipleArg
	{
		public Object returnArg(Object arg0, Object arg1, Object arg2);
	}
}

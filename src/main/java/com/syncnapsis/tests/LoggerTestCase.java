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

import java.lang.reflect.Method;
import java.util.Arrays;

import junit.framework.TestCase;

import org.jmock.Mockery;
import com.syncnapsis.utils.ReflectionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LoggerTestCase extends TestCase
{
	protected Logger	logger		= LoggerFactory.getLogger(getClass());
	protected Mockery	mockContext	= new Mockery();
	
	@Override 
	protected void setUp() throws Exception
	{
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	protected class MethodCall
	{
		private String		method;
		private Object		out;
		private Object[]	in;

		public MethodCall(String method, Object out, Object... in)
		{
			super();
			this.method = method;
			this.out = out;
			this.in = in;
		}

		public String getMethod()
		{
			return method;
		}

		public Object getOut()
		{
			return out;
		}

		public Object[] getIn()
		{
			return in;
		}
	}

	protected Method getMethod(Class<?> cls, MethodCall call)
	{
		return ReflectionsUtil.findMethod(cls, call.getMethod(), call.getIn());
	}

	/**
	 * Asserts that two arrays are equal. If they are not
	 * an AssertionFailedError is thrown.
	 */
	public static <T> void assertEquals(String message, T[] expected, T[] actual)
	{
		if(expected == null && actual == null)
			return;
		if(expected != null && expected.equals(actual))
			return;
		if(Arrays.equals(expected, actual))
			return;
		failNotEquals(message, Arrays.asList(expected), Arrays.asList(actual));
	}

	/**
	 * Asserts that two arrays are equal. If they are not
	 * an AssertionFailedError is thrown.
	 */
	public static <T> void assertEquals(T[] expected, T[] actual)
	{
		assertEquals(null, expected, actual);
	}
}

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
import java.lang.reflect.Type;
import java.util.Arrays;

import junit.framework.TestCase;

import org.jmock.Mockery;
import com.syncnapsis.utils.ReflectionsUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base for all tests containing useful test support:
 * <ul>
 * <li>({@link Mockery})</li>
 * <li>({@link Logger})</li>
 * <li>additional assertions</li>
 * <li>MethodCall support</li>
 * </ul>
 * 
 * @author ultimate
 * 
 */
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

	/**
	 * A generic reflection based test for fields with getters and setters.<br>
	 * The test will find the setter and getter method, invoke them, and check wether the result
	 * matches the given argument.<br>
	 * This way copy-and-paste errors within the fields used can be found...
	 * 
	 * @param o - the object to use
	 * @param fieldName - the field name
	 * @param type - the type of the field
	 * @param genericType - the generic type of the field
	 * @param aValue - a valid value for the field
	 * @throws Exception - if the test fails
	 */
	public <V> void getAndSetTest(Object o, String fieldName, Class<V> type, Type genericType, V aValue) throws Exception
	{
		Method getter = ReflectionsUtil.getGetter(o.getClass(), fieldName, type, genericType);
		Method setter = ReflectionsUtil.getSetter(o.getClass(), fieldName, type);

		assertNotNull(getter);
		assertNotNull(setter);

		setter.invoke(o, aValue);

		assertEquals(aValue, ReflectionsUtil.getField(o, fieldName));

		assertEquals(aValue, getter.invoke(o));
	}
}

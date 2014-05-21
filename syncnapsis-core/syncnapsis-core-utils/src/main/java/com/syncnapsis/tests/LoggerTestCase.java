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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import junit.framework.TestCase;

import org.jmock.Mockery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.mock.util.CombinedImposteriser;
import com.syncnapsis.utils.ReflectionsUtil;

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
 */
public abstract class LoggerTestCase extends TestCase
{
	/**
	 * Logger-Instance
	 */
	protected Logger					logger		= LoggerFactory.getLogger(getClass());
	/**
	 * Mockery for creating mocks
	 */
	protected Mockery					mockContext	= new Mockery();
	/**
	 * A SimpleDateFormat usable for parsing dates held in a ThreadLocale for thread safety
	 */
	protected ThreadLocal<DateFormat>	dateFormat	= new ThreadLocal<DateFormat>() {
														/*
														 * (non-Javadoc)
														 * @see java.lang.ThreadLocal#initialValue()
														 */
														@Override
														protected DateFormat initialValue()
														{
															return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
														}
													};

	/**
	 * Default Constructor.<br>
	 * (will initialize the Mockery)
	 */
	public LoggerTestCase()
	{
		mockContext.setImposteriser(CombinedImposteriser.INSTANCE);
	}

	/*
	 * (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	/**
	 * Class representing a method call.
	 * 
	 * @author ultimate
	 */
	protected class MethodCall
	{
		/**
		 * The name of the method called
		 */
		private String		method;
		/**
		 * The expected return value
		 */
		private Object		out;
		/**
		 * The arguments passed
		 */
		private Object[]	in;

		/**
		 * 
		 * @param method - the name of the method called
		 * @param out - the expected return value
		 * @param in - the arguments passed
		 */
		public MethodCall(String method, Object out, Object... in)
		{
			super();
			this.method = method;
			this.out = out;
			this.in = in;
		}

		/**
		 * The name of the method called
		 * 
		 * @return method
		 */
		public String getMethod()
		{
			return method;
		}

		/**
		 * The expected return value
		 * 
		 * @return
		 */
		public Object getOut()
		{
			return out;
		}

		/**
		 * The arguments passed
		 * 
		 * @return in
		 */
		public Object[] getIn()
		{
			return in;
		}
	}

	/**
	 * Find a Method using a MethodCall Object.<br>
	 * 
	 * @see ReflectionsUtil#findMethod(Class, String, Object...)
	 * @param cls - the class containing the method
	 * @param call - the MethodCall defining the method
	 * @return the Method Object
	 */
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
	 * @param aValue - a valid value for the field
	 * @throws Exception - if the test fails
	 */
	protected <V> void getAndSetTest(Object o, String fieldName, Class<V> type, V aValue) throws Exception
	{
		getAndSetTest(o, fieldName, type, type, aValue);
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
	protected <V> void getAndSetTest(Object o, String fieldName, Class<V> type, Type genericType, V aValue) throws Exception
	{
		Method getter = ReflectionsUtil.getGetter(o.getClass(), fieldName, type, genericType);
		Method setter = ReflectionsUtil.getSetter(o.getClass(), fieldName, type);

		assertNotNull(getter);
		assertNotNull(setter);

		setter.invoke(o, aValue);

		assertEquals(aValue, ReflectionsUtil.getField(o, fieldName));

		assertEquals(aValue, getter.invoke(o));
	}

	protected void printDistribution(int[] array, int scaleY, int scaleX)
	{
		StringBuilder graph = new StringBuilder();
		StringBuilder line;
		boolean appended = true;
		int row = 0;
		int val = 0;
		do
		{
			appended = false;
			line = new StringBuilder();

			for(int i = 0; i < array.length; i++)
			{
				val += array[i];
				if(i % scaleX == 0)
				{
					if(val > row)
					{
						line.append("I");
						appended = true;
					}
					else
					{
						line.append(" ");
					}
					val = 0;
				}
			}
			line.append("\n");
			graph.insert(0, line);
			row += scaleY;
		} while(appended);
		System.out.println(graph);
	}

	protected String arrayPrint(int[] arr)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < arr.length; i++)
		{
			if(i > 0)
				sb.append(", ");
			sb.append(arr[i]);
		}
		sb.append("]");
		return sb.toString();
	}

	protected boolean arrayEquals(int[] a1, int[] a2)
	{
		if(a1.length != a2.length)
			return false;
		for(int i = 0; i < a1.length; i++)
			if(a1[i] != a2[i])
				return false;
		return true;
	}

	protected String arrayPrint(long[] arr)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < arr.length; i++)
		{
			if(i > 0)
				sb.append(", ");
			sb.append(arr[i]);
		}
		sb.append("]");
		return sb.toString();
	}

	protected boolean arrayEquals(long[] a1, long[] a2)
	{
		if(a1.length != a2.length)
			return false;
		for(int i = 0; i < a1.length; i++)
			if(a1[i] != a2[i])
				return false;
		return true;
	}
}

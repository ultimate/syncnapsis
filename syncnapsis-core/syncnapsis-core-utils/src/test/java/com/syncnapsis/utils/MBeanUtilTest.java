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
package com.syncnapsis.utils;

import javax.management.ObjectInstance;

import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class MBeanUtilTest extends BaseSpringContextTestCase
{
	@TestCoversMethods({ "registerMBean", "getMBeanServer" })
	public void testRegisterMBean() throws Exception
	{
		assertNotNull(MBeanUtil.getMBeanServer());
		int countBefore = MBeanUtil.getMBeanServer().getMBeanCount();

		ObjectInstance oi = MBeanUtil.registerMBean(new TestObject());

		assertNotNull(oi);
		assertNotNull(MBeanUtil.getMBeanServer().getMBeanInfo(oi.getObjectName()));

		assertEquals(countBefore + 1, (int) MBeanUtil.getMBeanServer().getMBeanCount());

		oi = null;
		oi = MBeanUtil.registerMBean(this);
		assertNull(oi);
	}

	public void testGetDomain()
	{
		Object o2 = new Object() {
			@SuppressWarnings("unused")
			public void doSomething()
			{
			}
		};
		Object o3 = new TestObject();
		Object o4 = new TestObject.TestObject2();

		String packageName = getClass().getPackage().getName();

		assertEquals(packageName, MBeanUtil.getDomain(this));
		assertEquals(packageName, MBeanUtil.getDomain(o2));
		assertEquals(packageName, MBeanUtil.getDomain(o3));
		assertEquals(packageName, MBeanUtil.getDomain(o4));
	}

	public void testGetType()
	{
		Object o2 = new Object() {
			@SuppressWarnings("unused")
			public void doSomething()
			{
			}
		};
		Object o3 = new TestObject();
		Object o4 = new TestObject.TestObject2();

		assertEquals(getClass().getSimpleName(), MBeanUtil.getType(this));
		assertEquals(getClass().getSimpleName() + "$2", MBeanUtil.getType(o2));
		assertEquals(getClass().getSimpleName() + "$TestObject", MBeanUtil.getType(o3));
		assertEquals(getClass().getSimpleName() + "$TestObject$TestObject2", MBeanUtil.getType(o4));
	}

	public void testGetName()
	{
		Object o2 = new Object() {
			@SuppressWarnings("unused")
			public void doSomething()
			{
			}
		};
		Object o3 = new TestObject();
		Object o4 = new TestObject.TestObject2();

		String thisName = MBeanUtil.getName(this);
		String contextName = thisName.substring(0, thisName.lastIndexOf("@") + 1);
		assertTrue(MBeanUtil.getName(o2).startsWith(contextName));
		assertEquals(contextName + Integer.toHexString(o2.hashCode()), MBeanUtil.getName(o2));
		assertTrue(MBeanUtil.getName(o3).startsWith(contextName));
		assertEquals(contextName + Integer.toHexString(o3.hashCode()), MBeanUtil.getName(o3));
		assertTrue(MBeanUtil.getName(o4).startsWith(contextName));
		assertEquals(contextName + Integer.toHexString(o4.hashCode()), MBeanUtil.getName(o4));
	}

	public static class TestObject implements TestObjectMBean
	{
		public void doSomething()
		{

		}

		public static class TestObject2
		{

		}
	}

	public static interface TestObjectMBean
	{
		public void doSomething();
	}
}

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

import java.io.File;
import java.util.List;

import com.syncnapsis.mock.MockLogger;
import com.syncnapsis.tests.LoggerTestCase;
import org.slf4j.Logger;

public class ClassUtilTest extends LoggerTestCase
{
	private final String packageName = "com.syncnapsis";
	
	public void testFindClasses() throws Exception
	{
		Class<Logger> superClass = Logger.class;

		File classesDir = new File("target/classes/" + packageName.replace(".", "/"));
		List<Class<Logger>> classes = ClassUtil.findClasses(classesDir.toURI().toURL(), packageName, superClass);

		assertTrue(classes.size() > 0);

		assertTrue(classes.contains(MockLogger.class));

		for(Class<?> c : classes)
		{
			assertTrue(superClass.isAssignableFrom(c));
		}
	}

	public void testIsSubclass() throws Exception
	{
		assertTrue(ClassUtil.isSubclass(Logger.class, MockLogger.class));
		assertFalse(ClassUtil.isSubclass(MockLogger.class, Logger.class));
	}
	
	public void testIsNumber() throws Exception
	{
		assertTrue(ClassUtil.isNumber(Integer.class));
		assertTrue(ClassUtil.isNumber(int.class));
		assertTrue(ClassUtil.isNumber(Long.class));
		assertTrue(ClassUtil.isNumber(long.class));
		assertTrue(ClassUtil.isNumber(Double.class));
		assertTrue(ClassUtil.isNumber(double.class));
		assertTrue(ClassUtil.isNumber(Float.class));
		assertTrue(ClassUtil.isNumber(float.class));
		assertTrue(ClassUtil.isNumber(Byte.class));
		assertTrue(ClassUtil.isNumber(byte.class));
		assertTrue(ClassUtil.isNumber(Short.class));
		assertTrue(ClassUtil.isNumber(short.class));
		assertFalse(ClassUtil.isNumber(Boolean.class));
		assertFalse(ClassUtil.isNumber(boolean.class));
		assertFalse(ClassUtil.isNumber(Character.class));
		assertFalse(ClassUtil.isNumber(char.class));
		assertFalse(ClassUtil.isNumber(String.class));
		assertFalse(ClassUtil.isNumber(Object.class));
	}

	public void testGetClassFromPath() throws Exception
	{
		File classesDir = new File(packageName.replace(".", "/") + "/mock/MockLogger.class");

		assertEquals(MockLogger.class, ClassUtil.getClassFromPath(classesDir.getPath()));
	}
}

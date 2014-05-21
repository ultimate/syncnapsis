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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import junit.framework.AssertionFailedError;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.utils.ClassUtil;

public class ValidationTestCase extends LoggerTestCase
{
	@SuppressWarnings("rawtypes")
	public void testHibernateStringColumns() throws Exception
	{
		logger.debug("testHibernateStringColumns");
		boolean fail = false;
		List<Class<BaseObject>> hibernateModelClasses = ClassUtil.findClasses("com.syncnapsis.data.model", BaseObject.class);

		for(Class<BaseObject> cls : hibernateModelClasses)
		{
			Method[] methods = cls.getDeclaredMethods();
			for(Method m : methods)
			{
				try
				{
					if(!m.getName().startsWith("get"))
						continue;
					if(m.isAnnotationPresent(Transient.class))
						continue;
					if(m.getReturnType().equals(String.class) || m.getReturnType().isEnum())
					{
						assertTrue(m.isAnnotationPresent(Column.class));
						Column cl = m.getAnnotation(Column.class);
						assertTrue(cl.length() != 255);
						logger.info("  " + cls.getName() + "." + m.getName() + " -> OK");
					}
				}
				catch(AssertionFailedError e)
				{
					fail = true;
					logger.warn("  " + cls.getName() + "." + m.getName() + " -> INVALID MAPPING");
				}
			}
		}

		if(fail)
			fail("Found invalid hibernate mapping!");
	}
}

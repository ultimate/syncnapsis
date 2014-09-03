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
package com.syncnapsis.security;

import java.util.List;

import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.reflections.Field;

@TestExcludesMethods({ "set*", "get*", "afterPropertiesSet" })
public class BaseApplicationManagerTest extends BaseSpringContextTestCase
{
	private BaseApplicationManager securityManager;
	
	public void testBaseApplicationManagerClone() throws Exception
	{
		BaseApplicationManager clone = new BaseApplicationManager(securityManager);
		
		List<Field> fields = ReflectionsUtil.findDefaultFields(clone.getClass());
		
		assertTrue(fields.size() > 0);
		for(Field f: fields)
		{
			if(f.getName().equals("beanName"))
				continue;
			assertSame(f.get(securityManager), f.get(clone));
		}
	}
}

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
package com.syncnapsis.security;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import com.syncnapsis.security.accesscontrol.FieldAccessController;
import com.syncnapsis.security.accesscontrol.MethodAccessController;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.reflections.Field;

@TestExcludesMethods({"*Provider", "afterPropertiesSet"})
public class SecurityManagerTest extends LoggerTestCase
{
	@TestCoversMethods({"*AccessController", "*AccessControllers", "getAvailableAccessControllerTypes"})
	public void testAccessControllerManagement() throws Exception
	{
		SecurityManager securityManager = new SecurityManager();
		
		AccessController<?>[] controllers = new AccessController<?>[] {
				new FieldAccessController(),
				new MethodAccessController()
		};
		
		securityManager.setAccessControllers(Arrays.asList(controllers));
		
		assertNotNull(securityManager.getAccessController(Field.class));
		assertEquals(controllers[0], securityManager.getAccessController(Field.class));
		
		assertNotNull(securityManager.getAccessController(Method.class));
		assertEquals(controllers[1], securityManager.getAccessController(Method.class));
		
		Set<Class<?>> types = securityManager.getAvailableAccessControllerTypes();
		assertEquals(2, types.size());
		assertTrue(types.contains(Field.class));
		assertTrue(types.contains(Method.class));
	}
}

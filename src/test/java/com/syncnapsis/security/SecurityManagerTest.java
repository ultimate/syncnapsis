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

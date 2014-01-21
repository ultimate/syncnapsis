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
package com.syncnapsis.security.accesscontrol;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.security.AccessController;
import com.syncnapsis.security.AccessRule;
import com.syncnapsis.security.AnnotationAccessControllerTest;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestCoversClasses({ MethodAccessController.class, AnnotationAccessController.class })
@TestExcludesMethods({ "*DefaultAccessible", "getTargetClass" })
public class MethodAccessControllerTest extends LoggerTestCase
{
	public void testIsAccessible() throws Exception
	{
		MethodAccessController controller = new MethodAccessController(new BaseAccessRule());
		
		Object owner = new Object();
		Object other = new Object();

		AnnotatedMethods target = new AnnotatedMethods(owner);

		controller.setDefaultAccessibleOf(AccessRule.ANYROLE);
		
		controller.setDefaultAccessibleBy(AccessRule.ANYBODY);
		logger.debug("---default set to ANYBODY---");
		accessibleTest(controller, target, owner, "doAsOwner", "doAsAnybody", "doAsDefault");
		accessibleTest(controller, target, other, "doAsAnybody", "doAsDefault");

		controller.setDefaultAccessibleBy(AccessRule.OWNER);
		logger.debug("---default set to OWNER---");
		accessibleTest(controller, target, owner, "doAsOwner", "doAsAnybody", "doAsDefault");
		accessibleTest(controller, target, other, "doAsAnybody");

		controller.setDefaultAccessibleBy(AccessRule.NOBODY);
		logger.debug("---default set to NOBODY---");
		accessibleTest(controller, target, owner, "doAsOwner", "doAsAnybody");
		accessibleTest(controller, target, other, "doAsAnybody");
	}

	private void accessibleTest(MethodAccessController ac, Object target, Object authority, String... expectedAccessibleMethods)
	{
		List<String> accessibleMethods = new ArrayList<String>();
		for(Method m : target.getClass().getMethods())
		{
			if(ac.isAccessible(m, AccessController.INVOKE, target, authority))
				accessibleMethods.add(m.getName());
		}
		for(String expectedMethod : expectedAccessibleMethods)
		{
			assertTrue(expectedMethod + " is not accessible", accessibleMethods.contains(expectedMethod));
		}
	}

	public void testGetAnnotation() throws Exception
	{
		final int onMethod = 123;

		Object annotated = new Object() {
			@Accessible(by = onMethod)
			public int method()
			{
				return 0;
			}
		};

		Method method = annotated.getClass().getMethod("method");

		MethodAccessController controller = new MethodAccessController(new BaseAccessRule());

		assertNotNull(controller.getAnnotation(method, AccessController.INVOKE));
		assertEquals(onMethod, controller.getAnnotation(method, AccessController.INVOKE).by());

		assertNull(controller.getAnnotation(method, 999));
	}

	public void testGetDefaultAccessibleBy() throws Exception
	{
		MethodAccessController controller = new MethodAccessController(new BaseAccessRule());
		controller.setDefaultAccessibleBy(123);

		assertEquals(123, controller.getDefaultAccessibleBy(AccessController.INVOKE));
		assertEquals(0, controller.getDefaultAccessibleBy(999));
	}

	public void testGetDefaultAccessibleOf() throws Exception
	{
		MethodAccessController controller = new MethodAccessController(new BaseAccessRule());
		controller.setDefaultAccessibleOf(123);

		assertEquals(123, controller.getDefaultAccessibleOf(AccessController.INVOKE));
		assertEquals(0, controller.getDefaultAccessibleOf(999));
	}

	public static class AnnotatedMethods extends AnnotationAccessControllerTest.Target
	{
		public AnnotatedMethods(List<Object> owners)
		{
			super(owners);
		}

		public AnnotatedMethods(Object owner)
		{
			super(owner);
		}

		@Accessible(by=AccessRule.NOBODY)
		public void doAsNobody()
		{
		}

		@Accessible(by=AccessRule.OWNER)
		public void doAsOwner()
		{
		}

		@Accessible(by=AccessRule.ALLY)
		public void doAsAlly()
		{
		}

		@Accessible(by=AccessRule.FRIEND)
		public void doAsFriend()
		{
		}

		@Accessible(by=AccessRule.ENEMY)
		public void doAsEnemy()
		{
		}

		@Accessible(by = AccessRule.ANYBODY)
		public void doAsAnybody()
		{
		}

		// no annotation
		public void doAsDefault()
		{
		}
	}
}

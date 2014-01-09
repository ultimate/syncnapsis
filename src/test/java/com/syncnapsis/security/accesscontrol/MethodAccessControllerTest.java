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
import com.syncnapsis.security.AccessControllerTest;
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
		MethodAccessController controller = new MethodAccessController();
		
		Object owner = new Object();
		Object other = new Object();

		AnnotatedMethods target = new AnnotatedMethods(owner);

		controller.setDefaultAccessible(Accessible.ANYBODY);
		accessibleTest(controller, target, owner, "doAsOwner", "doAsAnybody", "doAsDefault");
		accessibleTest(controller, target, other, "doAsAnybody", "doAsDefault");

		controller.setDefaultAccessible(Accessible.OWNER);
		accessibleTest(controller, target, owner, "doAsOwner", "doAsAnybody", "doAsDefault");
		accessibleTest(controller, target, other, "doAsAnybody");

		controller.setDefaultAccessible(Accessible.NOBODY);
		accessibleTest(controller, target, owner, "doAsOwner", "doAsAnybody");
		accessibleTest(controller, target, other, "doAsAnybody");
	}

	private void accessibleTest(MethodAccessController ac, Object target, Object authority, String... expectedAccessibleMethods)
	{
		List<String> accessibleMethods = new ArrayList<String>();
		for(Method m: target.getClass().getMethods())
		{
			if(ac.isAccessible(target, m, AccessController.INVOKE, authority))
				accessibleMethods.add(m.getName());
		}
		for(String expectedMethod : expectedAccessibleMethods)
		{
			assertTrue(expectedMethod + " is not accessible", accessibleMethods.contains(expectedMethod));
		}
	}

	public void testGetAccessibleAnnotation() throws Exception
	{
		final int onMethod = 123;

		Object annotated = new Object() {
			@Accessible(onMethod)
			public int method()
			{
				return 0;
			}
		};

		Method method = annotated.getClass().getMethod("method");

		MethodAccessController controller = new MethodAccessController();

		assertNotNull(controller.getAccessibleAnnotation(method));
		assertEquals(onMethod, controller.getAccessibleAnnotation(method).value());
	}
	
	public static class AnnotatedMethods extends AccessControllerTest.Target
	{
		public AnnotatedMethods(List<Object> owners)
		{
			super(owners);
		}

		public AnnotatedMethods(Object owner)
		{
			super(owner);
		}

		@Accessible(Accessible.NOBODY)
		public void doAsNobody()
		{
		}

		@Accessible(Accessible.OWNER)
		public void doAsOwner()
		{
		}

		@Accessible(Accessible.ALLY)
		public void doAsAlly()
		{
		}

		@Accessible(Accessible.FRIEND)
		public void doAsFriend()
		{
		}

		@Accessible(Accessible.ENEMY)
		public void doAsEnemy()
		{
		}

		@Accessible(Accessible.ANYBODY)
		public void doAsAnybody()
		{
		}

		// no annotation
		public void doAsDefault()
		{
		}
	}
}

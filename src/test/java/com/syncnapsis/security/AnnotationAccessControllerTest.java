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
import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.security.accesscontrol.AnnotationAccessController;
import com.syncnapsis.security.accesscontrol.BaseAccessRule;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestCoversClasses({ AnnotationAccessController.class, AccessController.class })
@TestExcludesMethods({ "getTargetClass", "getRule", "getAnnotation", "getDefault*" })
public class AnnotationAccessControllerTest extends LoggerTestCase
{
	@TestCoversMethods("isAccessible")
	public void testIsAccessible_forTarget() throws Exception
	{
		AnnotationAccessController<Method> ac = new DummyAnnotationAccessController();

		Object owner = new Object();
		Object other = new Object();

		Target t = new Target(owner);

		Method byAnybody = t.getClass().getMethod("byAnybody");
		Method byOwner = t.getClass().getMethod("byOwner");
		Method byNobody = t.getClass().getMethod("byNobody");
		
		assertNotNull(byAnybody);
		assertNotNull(byOwner);
		assertNotNull(byNobody);

		assertTrue(ac.isAccessible(byAnybody, 0, t, owner));
		assertTrue(ac.isAccessible(byAnybody, 0, t, owner, other));
		assertTrue(ac.isAccessible(byAnybody, 0, t, other));
		assertTrue(ac.isAccessible(byAnybody, 0, t));

		assertTrue(ac.isAccessible(byOwner, 0, t, owner));
		assertTrue(ac.isAccessible(byOwner, 0, t, owner, other));
		assertFalse(ac.isAccessible(byOwner, 0, t, other));
		assertFalse(ac.isAccessible(byOwner, 0, t));

		assertFalse(ac.isAccessible(byNobody, 0, t, owner));
		assertFalse(ac.isAccessible(byNobody, 0, t, owner, other));
		assertFalse(ac.isAccessible(byNobody, 0, t, other));
		assertFalse(ac.isAccessible(byNobody, 0, t));
	}

	@TestCoversMethods("isAccessible")
	public void testIsAccessible_forAnnotation() throws Exception
	{
		AnnotationAccessController<Method> ac = new DummyAnnotationAccessController();

		Object owner = new Object();
		Object other = new Object();

		Target t = new Target(owner);

		Accessible byAnybody = t.getClass().getMethod("byAnybody").getAnnotation(Accessible.class);
		Accessible byOwner = t.getClass().getMethod("byOwner").getAnnotation(Accessible.class);
		Accessible byNobody = t.getClass().getMethod("byNobody").getAnnotation(Accessible.class);
		
		assertNotNull(byAnybody);
		assertNotNull(byOwner);
		assertNotNull(byNobody);

		assertTrue(ac.isAccessible(t, byAnybody, 0, 0, owner));
		assertTrue(ac.isAccessible(t, byAnybody, 0, 0, owner, other));
		assertTrue(ac.isAccessible(t, byAnybody, 0, 0, other));
		assertTrue(ac.isAccessible(t, byAnybody, 0, 0));

		assertTrue(ac.isAccessible(t, byOwner, 0, 0, owner));
		assertTrue(ac.isAccessible(t, byOwner, 0, 0, owner, other));
		assertFalse(ac.isAccessible(t, byOwner, 0, 0, other));
		assertFalse(ac.isAccessible(t, byOwner, 0, 0));

		assertFalse(ac.isAccessible(t, byNobody, 0, 0, owner));
		assertFalse(ac.isAccessible(t, byNobody, 0, 0, owner, other));
		assertFalse(ac.isAccessible(t, byNobody, 0, 0, other));
		assertFalse(ac.isAccessible(t, byNobody, 0, 0));
	}

	public static class DummyAnnotationAccessController extends AnnotationAccessController<Method>
	{

		public DummyAnnotationAccessController()
		{
			super(Method.class, new BaseAccessRule());
		}

		@Override
		public Accessible getAnnotation(Method target, int operation)
		{
			return target.getAnnotation(Accessible.class);
		}

		@Override
		public int getDefaultAccessibleBy(int operation)
		{
			return AccessRule.ANYBODY;
		}

		@Override
		public int getDefaultAccessibleOf(int operation)
		{
			return AccessRule.ANYROLE;
		}
	}

	public static class Target implements Ownable<Object>
	{
		private List<Object>	owners;

		public Target(Object owner)
		{
			this(new ArrayList<Object>(1));
			this.owners.add(owner);
		}

		public Target(List<Object> owners)
		{
			super();
			this.owners = owners;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.security.Ownable#getOwners()
		 */
		@Override
		public List<Object> getOwners()
		{
			return owners;
		}

		@Accessible(by = AccessRule.ANYBODY)
		public void byAnybody()
		{

		}

		@Accessible(by = AccessRule.OWNER)
		public void byOwner()
		{

		}

		@Accessible(by = AccessRule.NOBODY)
		public void byNobody()
		{

		}
	}
}

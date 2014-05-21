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

import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.security.AccessController;
import com.syncnapsis.security.AccessRule;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.reflections.Field;

@TestExcludesMethods({ "getTargetClass" })
public class FieldAccessControllerTest extends LoggerTestCase
{
	public void testIsAccessible() throws Exception
	{
		FieldAccessController controller = new FieldAccessController(new BaseAccessRule());

		Object owner = new Object();
		Object other = new Object();

		AnnotatedByField targetF = new AnnotatedByField(owner);
		AnnotatedByMethod targetM = new AnnotatedByMethod(owner);

		controller.setDefaultReadableOf(AccessRule.ANYROLE);
		controller.setDefaultWritableOf(AccessRule.ANYROLE);

		controller.setDefaultReadableBy(AccessRule.ANYBODY);
		controller.setDefaultWritableBy(AccessRule.ANYBODY);
		logger.debug("---default set to ANYBODY---");
		accessibleTest(controller, targetF, owner, "byOwner", "byAnybody", "byDefault");
		accessibleTest(controller, targetM, owner, "byOwner", "byAnybody", "byDefault");
		accessibleTest(controller, targetF, other, "byAnybody", "byDefault");
		accessibleTest(controller, targetM, other, "byAnybody", "byDefault");

		controller.setDefaultReadableBy(AccessRule.OWNER);
		controller.setDefaultWritableBy(AccessRule.OWNER);
		logger.debug("---default set to OWNER---");
		accessibleTest(controller, targetF, owner, "byOwner", "byAnybody", "byDefault");
		accessibleTest(controller, targetM, owner, "byOwner", "byAnybody", "byDefault");
		accessibleTest(controller, targetF, other, "byAnybody");
		accessibleTest(controller, targetM, other, "byAnybody");

		controller.setDefaultReadableBy(AccessRule.NOBODY);
		controller.setDefaultWritableBy(AccessRule.NOBODY);
		logger.debug("---default set to NOBODY---");
		accessibleTest(controller, targetF, owner, "byOwner", "byAnybody");
		accessibleTest(controller, targetM, owner, "byOwner", "byAnybody");
		accessibleTest(controller, targetF, other, "byAnybody");
		accessibleTest(controller, targetM, other, "byAnybody");
	}

	private void accessibleTest(FieldAccessController ac, Object target, Object authority, String... expectedAccessibleFields)
	{
		List<String> readableFields = new ArrayList<String>();
		List<String> writableFields = new ArrayList<String>();
		List<Field> fields = ReflectionsUtil.findDefaultFields(target.getClass());
		for(Field f : fields)
		{
			if(ac.isAccessible(f, AccessController.READ, target, authority))
				readableFields.add(f.getName());
			if(ac.isAccessible(f, AccessController.WRITE, target, authority))
				writableFields.add(f.getName());
		}
		logger.debug("readable fields: " + readableFields);
		logger.debug("writable fields: " + writableFields);
		for(String expectedField : expectedAccessibleFields)
		{
			assertTrue(expectedField + " is not readable", readableFields.contains(expectedField));
			assertTrue(expectedField + " is not writable", writableFields.contains(expectedField));
		}
	}

	@SuppressWarnings("unused")
	public void testGetAnnotation() throws Exception
	{
		final int onField = 123;
		final int onGetter = 456;
		final int onSetter = 789;

		Object oField = new Object() {
			@Accessible(by = onField)
			private int	x;

			public int getX()
			{
				return x;
			}

			public void setX(int x)
			{
				this.x = x;
			}
		};
		Object oGetter = new Object() {
			private int	x;

			@Accessible(by = onGetter)
			public int getX()
			{
				return x;
			}

			public void setX(int x)
			{
				this.x = x;
			}
		};
		Object oSetter = new Object() {
			private int	x;

			public int getX()
			{
				return x;
			}

			@Accessible(by = onSetter)
			public void setX(int x)
			{
				this.x = x;
			}
		};

		com.syncnapsis.utils.reflections.Field oFieldField = ReflectionsUtil.findFields(oField.getClass()).get(0);
		com.syncnapsis.utils.reflections.Field oGetterField = ReflectionsUtil.findFields(oGetter.getClass()).get(0);
		com.syncnapsis.utils.reflections.Field oSetterField = ReflectionsUtil.findFields(oSetter.getClass()).get(0);

		FieldAccessController controller;

		// getAnnotation (READ)

		controller = new FieldAccessController(new BaseAccessRule());

		assertNotNull(controller.getAnnotation(oFieldField, AccessController.READ));
		assertEquals(onField, controller.getAnnotation(oFieldField, AccessController.READ).by());

		assertNotNull(controller.getAnnotation(oGetterField, AccessController.READ));
		assertEquals(onGetter, controller.getAnnotation(oGetterField, AccessController.READ).by());

		assertNull(controller.getAnnotation(oSetterField, AccessController.READ));

		// getAnnotation (WRITE)

		controller = new FieldAccessController(new BaseAccessRule());

		assertNotNull(controller.getAnnotation(oFieldField, AccessController.WRITE));
		assertEquals(onField, controller.getAnnotation(oFieldField, AccessController.WRITE).by());

		assertNull(controller.getAnnotation(oGetterField, AccessController.WRITE));

		assertNotNull(controller.getAnnotation(oSetterField, AccessController.WRITE));
		assertEquals(onSetter, controller.getAnnotation(oSetterField, AccessController.WRITE).by());

		// getAnnotation (INVALID)

		assertNotNull(controller.getAnnotation(oFieldField, 999));
		assertNull(controller.getAnnotation(oGetterField, 999));
		assertNull(controller.getAnnotation(oSetterField, 999));
	}

	@TestCoversMethods({"*etDefaultReadableBy", "*etDefaultWritableBy", "getDefaultAccessibleBy"})
	public void testGetDefaultAccessibleBy() throws Exception
	{
		FieldAccessController controller = new FieldAccessController(new BaseAccessRule());
		controller.setDefaultReadableBy(123);
		controller.setDefaultWritableBy(456);

		assertEquals(123, controller.getDefaultReadableBy());
		assertEquals(456, controller.getDefaultWritableBy());

		assertEquals(123, controller.getDefaultAccessibleBy(AccessController.READ));
		assertEquals(456, controller.getDefaultAccessibleBy(AccessController.WRITE));
		assertEquals(0, controller.getDefaultAccessibleBy(999));
	}

	@TestCoversMethods({"*etDefaultReadableOf", "*etDefaultWritableOf", "getDefaultAccessibleOf"})
	public void testGetDefaultAccessibleOf() throws Exception
	{
		FieldAccessController controller = new FieldAccessController(new BaseAccessRule());
		controller.setDefaultReadableOf(123);
		controller.setDefaultWritableOf(456);

		assertEquals(123, controller.getDefaultReadableOf());
		assertEquals(456, controller.getDefaultWritableOf());

		assertEquals(123, controller.getDefaultAccessibleOf(AccessController.READ));
		assertEquals(456, controller.getDefaultAccessibleOf(AccessController.WRITE));
		assertEquals(0, controller.getDefaultAccessibleOf(999));
	}

	public static class AnnotatedByMethod extends AnnotationAccessControllerTest.Target
	{
		private int	byNobody;
		private int	byOwner;
		private int	byAlly;
		private int	byFriend;
		private int	byEnemy;
		private int	byAnybody;
		private int	byDefault;

		public AnnotatedByMethod(List<Object> owners)
		{
			super(owners);
		}

		public AnnotatedByMethod(Object owner)
		{
			super(owner);
		}

		@Accessible(by = AccessRule.NOBODY)
		public int getByNobody()
		{
			return byNobody;
		}

		@Accessible(by = AccessRule.NOBODY)
		public void setByNobody(int byNobody)
		{
			this.byNobody = byNobody;
		}

		@Accessible(by = AccessRule.OWNER)
		public int getByOwner()
		{
			return byOwner;
		}

		@Accessible(by = AccessRule.OWNER)
		public void setByOwner(int byOwner)
		{
			this.byOwner = byOwner;
		}

		@Accessible(by = AccessRule.ALLY)
		public int getByAlly()
		{
			return byAlly;
		}

		@Accessible(by = AccessRule.ALLY)
		public void setByAlly(int byAlly)
		{
			this.byAlly = byAlly;
		}

		@Accessible(by = AccessRule.FRIEND)
		public int getByFriend()
		{
			return byFriend;
		}

		@Accessible(by = AccessRule.FRIEND)
		public void setByFriend(int byFriend)
		{
			this.byFriend = byFriend;
		}

		@Accessible(by = AccessRule.ENEMY)
		public int getByEnemy()
		{
			return byEnemy;
		}

		@Accessible(by = AccessRule.ENEMY)
		public void setByEnemy(int byEnemy)
		{
			this.byEnemy = byEnemy;
		}

		@Accessible(by = AccessRule.ANYBODY)
		public int getByAnybody()
		{
			return byAnybody;
		}

		@Accessible(by = AccessRule.ANYBODY)
		public void setByAnybody(int byAnybody)
		{
			this.byAnybody = byAnybody;
		}

		// no annotation
		public int getByDefault()
		{
			return byDefault;
		}

		// no annotation
		public void setByDefault(int byDefault)
		{
			this.byDefault = byDefault;
		}
	}

	public static class AnnotatedByField extends AnnotationAccessControllerTest.Target
	{
		@Accessible(by = AccessRule.NOBODY)
		private int	byNobody;
		@Accessible(by = AccessRule.OWNER)
		private int	byOwner;
		@Accessible(by = AccessRule.ALLY)
		private int	byAlly;
		@Accessible(by = AccessRule.NOBODY)
		private int	byFriend;
		@Accessible(by = AccessRule.ENEMY)
		private int	byEnemy;
		@Accessible(by = AccessRule.ANYBODY)
		private int	byAnybody;
		// no annotation
		private int	byDefault;

		public AnnotatedByField(List<Object> owners)
		{
			super(owners);
		}

		public AnnotatedByField(Object owner)
		{
			super(owner);
		}

		public int getByNobody()
		{
			return byNobody;
		}

		public void setByNobody(int byNobody)
		{
			this.byNobody = byNobody;
		}

		public int getByOwner()
		{
			return byOwner;
		}

		public void setByOwner(int byOwner)
		{
			this.byOwner = byOwner;
		}

		public int getByAlly()
		{
			return byAlly;
		}

		public void setByAlly(int byAlly)
		{
			this.byAlly = byAlly;
		}

		public int getByFriend()
		{
			return byFriend;
		}

		public void setByFriend(int byFriend)
		{
			this.byFriend = byFriend;
		}

		public int getByEnemy()
		{
			return byEnemy;
		}

		public void setByEnemy(int byEnemy)
		{
			this.byEnemy = byEnemy;
		}

		public int getByAnybody()
		{
			return byAnybody;
		}

		public void setByAnybody(int byAnybody)
		{
			this.byAnybody = byAnybody;
		}

		public int getByDefault()
		{
			return byDefault;
		}

		public void setByDefault(int byDefault)
		{
			this.byDefault = byDefault;
		}
	}
}

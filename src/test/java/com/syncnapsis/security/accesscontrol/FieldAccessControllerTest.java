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
import com.syncnapsis.security.AccessControllerTest;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.reflections.Field;

@TestExcludesMethods({ "*DefaultReadable", "*DefaultWritable", "getTargetClass" })
public class FieldAccessControllerTest extends LoggerTestCase
{
	public void testIsAccessible() throws Exception
	{
		FieldAccessController controller = new FieldAccessController();

		Object owner = new Object();
		Object other = new Object();

		AnnotatedByField targetF = new AnnotatedByField(owner);
		AnnotatedByMethod targetM = new AnnotatedByMethod(owner);

		controller.setDefaultReadable(Accessible.ANYBODY);
		controller.setDefaultWritable(Accessible.ANYBODY);
		accessibleTest(controller, targetF, owner, "byOwner", "byAnybody", "byDefault");
		accessibleTest(controller, targetM, owner, "byOwner", "byAnybody", "byDefault");
		accessibleTest(controller, targetF, other, "byAnybody", "byDefault");
		accessibleTest(controller, targetM, other, "byAnybody", "byDefault");

		controller.setDefaultReadable(Accessible.OWNER);
		controller.setDefaultWritable(Accessible.OWNER);
		accessibleTest(controller, targetF, owner, "byOwner", "byAnybody", "byDefault");
		accessibleTest(controller, targetM, owner, "byOwner", "byAnybody", "byDefault");
		accessibleTest(controller, targetF, other, "byAnybody");
		accessibleTest(controller, targetM, other, "byAnybody");

		controller.setDefaultReadable(Accessible.NOBODY);
		controller.setDefaultWritable(Accessible.NOBODY);
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
			if(ac.isAccessible(target, f, AccessController.READ, authority))
				readableFields.add(f.getName());
			if(ac.isAccessible(target, f, AccessController.WRITE, authority))
				writableFields.add(f.getName());
		}
		for(String expectedField : expectedAccessibleFields)
		{
			assertTrue(expectedField + " is not readable", readableFields.contains(expectedField));
			assertTrue(expectedField + " is not writable", writableFields.contains(expectedField));
		}
	}

	@SuppressWarnings("unused")
	@TestCoversMethods({ "getReadableAnnotation", "getWritableAnnotation" })
	public void testGetAccessibleAnnotation() throws Exception
	{
		final int onField = 123;
		final int onGetter = 456;
		final int onSetter = 789;

		Object oField = new Object() {
			@Accessible(onField)
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

			@Accessible(onGetter)
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

			@Accessible(onSetter)
			public void setX(int x)
			{
				this.x = x;
			}
		};

		com.syncnapsis.utils.reflections.Field oFieldField = ReflectionsUtil.findFields(oField.getClass()).get(0);
		com.syncnapsis.utils.reflections.Field oGetterField = ReflectionsUtil.findFields(oGetter.getClass()).get(0);
		com.syncnapsis.utils.reflections.Field oSetterField = ReflectionsUtil.findFields(oSetter.getClass()).get(0);

		FieldAccessController controller;

		// getReadableAnnotation

		controller = new FieldAccessController();

		assertNotNull(controller.getReadableAnnotation(oFieldField));
		assertEquals(onField, controller.getReadableAnnotation(oFieldField).value());

		assertNotNull(controller.getReadableAnnotation(oGetterField));
		assertEquals(onGetter, controller.getReadableAnnotation(oGetterField).value());

		assertNull(controller.getReadableAnnotation(oSetterField));

		// getWritableAnnotation

		controller = new FieldAccessController();

		assertNotNull(controller.getWritableAnnotation(oFieldField));
		assertEquals(onField, controller.getWritableAnnotation(oFieldField).value());

		assertNull(controller.getWritableAnnotation(oGetterField));

		assertNotNull(controller.getWritableAnnotation(oSetterField));
		assertEquals(onSetter, controller.getWritableAnnotation(oSetterField).value());
	}

	public static class AnnotatedByMethod extends AccessControllerTest.Target
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

		@Accessible(Accessible.NOBODY)
		public int getByNobody()
		{
			return byNobody;
		}

		@Accessible(Accessible.NOBODY)
		public void setByNobody(int byNobody)
		{
			this.byNobody = byNobody;
		}

		@Accessible(Accessible.OWNER)
		public int getByOwner()
		{
			return byOwner;
		}

		@Accessible(Accessible.OWNER)
		public void setByOwner(int byOwner)
		{
			this.byOwner = byOwner;
		}

		@Accessible(Accessible.ALLY)
		public int getByAlly()
		{
			return byAlly;
		}

		@Accessible(Accessible.ALLY)
		public void setByAlly(int byAlly)
		{
			this.byAlly = byAlly;
		}

		@Accessible(Accessible.FRIEND)
		public int getByFriend()
		{
			return byFriend;
		}

		@Accessible(Accessible.FRIEND)
		public void setByFriend(int byFriend)
		{
			this.byFriend = byFriend;
		}

		@Accessible(Accessible.ENEMY)
		public int getByEnemy()
		{
			return byEnemy;
		}

		@Accessible(Accessible.ENEMY)
		public void setByEnemy(int byEnemy)
		{
			this.byEnemy = byEnemy;
		}

		@Accessible(Accessible.ANYBODY)
		public int getByAnybody()
		{
			return byAnybody;
		}

		@Accessible(Accessible.ANYBODY)
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

	public static class AnnotatedByField extends AccessControllerTest.Target
	{
		@Accessible(Accessible.NOBODY)
		private int	byNobody;
		@Accessible(Accessible.OWNER)
		private int	byOwner;
		@Accessible(Accessible.ALLY)
		private int	byAlly;
		@Accessible(Accessible.NOBODY)
		private int	byFriend;
		@Accessible(Accessible.ENEMY)
		private int	byEnemy;
		@Accessible(Accessible.ANYBODY)
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

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

import java.util.List;

import com.syncnapsis.security.AccessController;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.security.annotations.Authority;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ReflectionsUtil;

@TestExcludesMethods({ "*DefaultReadable", "*DefaultWritable", "getTargetClass" })
public class FieldAccessControllerTest extends LoggerTestCase
{
	public void testIsReadable() throws Exception
	{
		FieldAccessController controller = new FieldAccessController();

		List<com.syncnapsis.utils.reflections.Field> fields = ReflectionsUtil.findFields(POJO1.class);
		assertEquals(2, fields.size());

		com.syncnapsis.utils.reflections.Field xField = fields.get(0);
		assertEquals("x", xField.getName());
		com.syncnapsis.utils.reflections.Field yField = fields.get(1);
		assertEquals("y", yField.getName());

		// defaultReadable = true

		assertTrue(controller.isReadable(xField, "v1"));
		assertTrue(controller.isReadable(xField, 2));
		assertTrue(controller.isReadable(xField, "v1", 2));

		assertTrue(controller.isReadable(xField, "v1", 1));
		assertTrue(controller.isReadable(xField, 2, "v2"));

		assertTrue(controller.isReadable(xField, 1));
		assertTrue(controller.isReadable(xField, "v2"));
		assertTrue(controller.isReadable(xField, 1, "v2"));

		assertTrue(controller.isReadable(xField, (Object[]) null));
		assertTrue(controller.isReadable(xField, new Object[] {}));

		// defaultReadable = false

		assertTrue(controller.isReadable(yField, "v1"));
		assertTrue(controller.isReadable(yField, 2));
		assertTrue(controller.isReadable(yField, "v1", 2));

		assertTrue(controller.isReadable(yField, "v1", 1));
		assertTrue(controller.isReadable(yField, 2, "v2"));

		assertFalse(controller.isReadable(yField, 1));
		assertFalse(controller.isReadable(yField, "v2"));
		assertFalse(controller.isReadable(yField, 1, "v2"));

		assertFalse(controller.isReadable(yField, (Object[]) null));
		assertFalse(controller.isReadable(yField, new Object[] {}));
	}

	public void testIsWritable() throws Exception
	{
		FieldAccessController controller = new FieldAccessController();

		List<com.syncnapsis.utils.reflections.Field> fields = ReflectionsUtil.findFields(POJO1.class);
		assertEquals(2, fields.size());

		com.syncnapsis.utils.reflections.Field xField = fields.get(0);
		assertEquals("x", xField.getName());
		com.syncnapsis.utils.reflections.Field yField = fields.get(1);
		assertEquals("y", yField.getName());

		// defaultWritable = true

		assertTrue(controller.isWritable(xField, "v1"));
		assertTrue(controller.isWritable(xField, 2));
		assertTrue(controller.isWritable(xField, "v1", 2));

		assertTrue(controller.isWritable(xField, "v1", 1));
		assertTrue(controller.isWritable(xField, 2, "v2"));

		assertTrue(controller.isWritable(xField, 1));
		assertTrue(controller.isWritable(xField, "v2"));
		assertTrue(controller.isWritable(xField, 1, "v2"));

		assertTrue(controller.isWritable(xField, (Object[]) null));
		assertTrue(controller.isWritable(xField, new Object[] {}));

		// defaultWritable = false

		assertTrue(controller.isWritable(yField, "v1"));
		assertTrue(controller.isWritable(yField, 2));
		assertTrue(controller.isWritable(yField, "v1", 2));

		assertTrue(controller.isWritable(yField, "v1", 1));
		assertTrue(controller.isWritable(yField, 2, "v2"));

		assertFalse(controller.isWritable(yField, 1));
		assertFalse(controller.isWritable(yField, "v2"));
		assertFalse(controller.isWritable(yField, 1, "v2"));

		assertFalse(controller.isWritable(yField, (Object[]) null));
		assertFalse(controller.isWritable(yField, new Object[] {}));
	}
	public void testIsAccessible_read() throws Exception
	{
		FieldAccessController controller = new FieldAccessController();
		
		List<com.syncnapsis.utils.reflections.Field> fields = ReflectionsUtil.findFields(POJO1.class);
		assertEquals(2, fields.size());
		
		com.syncnapsis.utils.reflections.Field xField = fields.get(0);
		assertEquals("x", xField.getName());
		com.syncnapsis.utils.reflections.Field yField = fields.get(1);
		assertEquals("y", yField.getName());
		
		// defaultReadable = true
		
		assertTrue(controller.isAccessible(xField, AccessController.READ, "v1"));
		assertTrue(controller.isAccessible(xField, AccessController.READ, 2));
		assertTrue(controller.isAccessible(xField, AccessController.READ, "v1", 2));
		
		assertTrue(controller.isAccessible(xField, AccessController.READ, "v1", 1));
		assertTrue(controller.isAccessible(xField, AccessController.READ, 2, "v2"));
		
		assertTrue(controller.isAccessible(xField, AccessController.READ, 1));
		assertTrue(controller.isAccessible(xField, AccessController.READ, "v2"));
		assertTrue(controller.isAccessible(xField, AccessController.READ, 1, "v2"));
		
		assertTrue(controller.isAccessible(xField, AccessController.READ, (Object[]) null));
		assertTrue(controller.isAccessible(xField, AccessController.READ, new Object[] {}));
		
		// defaultReadable = false
		
		assertTrue(controller.isAccessible(yField, AccessController.READ, "v1"));
		assertTrue(controller.isAccessible(yField, AccessController.READ, 2));
		assertTrue(controller.isAccessible(yField, AccessController.READ, "v1", 2));
		
		assertTrue(controller.isAccessible(yField, AccessController.READ, "v1", 1));
		assertTrue(controller.isAccessible(yField, AccessController.READ, 2, "v2"));
		
		assertFalse(controller.isAccessible(yField, AccessController.READ, 1));
		assertFalse(controller.isAccessible(yField, AccessController.READ, "v2"));
		assertFalse(controller.isAccessible(yField, AccessController.READ, 1, "v2"));
		
		assertFalse(controller.isAccessible(yField, AccessController.READ, (Object[]) null));
		assertFalse(controller.isAccessible(yField, AccessController.READ, new Object[] {}));
	}
	
	public void testIsAccessible_write() throws Exception
	{
		FieldAccessController controller = new FieldAccessController();
		
		List<com.syncnapsis.utils.reflections.Field> fields = ReflectionsUtil.findFields(POJO1.class);
		assertEquals(2, fields.size());
		
		com.syncnapsis.utils.reflections.Field xField = fields.get(0);
		assertEquals("x", xField.getName());
		com.syncnapsis.utils.reflections.Field yField = fields.get(1);
		assertEquals("y", yField.getName());
		
		// defaultWritable = true
		
		assertTrue(controller.isAccessible(xField, AccessController.WRITE, "v1"));
		assertTrue(controller.isAccessible(xField, AccessController.WRITE, 2));
		assertTrue(controller.isAccessible(xField, AccessController.WRITE, "v1", 2));
		
		assertTrue(controller.isAccessible(xField, AccessController.WRITE, "v1", 1));
		assertTrue(controller.isAccessible(xField, AccessController.WRITE, 2, "v2"));
		
		assertTrue(controller.isAccessible(xField, AccessController.WRITE, 1));
		assertTrue(controller.isAccessible(xField, AccessController.WRITE, "v2"));
		assertTrue(controller.isAccessible(xField, AccessController.WRITE, 1, "v2"));
		
		assertTrue(controller.isAccessible(xField, AccessController.WRITE, (Object[]) null));
		assertTrue(controller.isAccessible(xField, AccessController.WRITE, new Object[] {}));
		
		// defaultWritable = false
		
		assertTrue(controller.isAccessible(yField, AccessController.WRITE, "v1"));
		assertTrue(controller.isAccessible(yField, AccessController.WRITE, 2));
		assertTrue(controller.isAccessible(yField, AccessController.WRITE, "v1", 2));
		
		assertTrue(controller.isAccessible(yField, AccessController.WRITE, "v1", 1));
		assertTrue(controller.isAccessible(yField, AccessController.WRITE, 2, "v2"));
		
		assertFalse(controller.isAccessible(yField, AccessController.WRITE, 1));
		assertFalse(controller.isAccessible(yField, AccessController.WRITE, "v2"));
		assertFalse(controller.isAccessible(yField, AccessController.WRITE, 1, "v2"));
		
		assertFalse(controller.isAccessible(yField, AccessController.WRITE, (Object[]) null));
		assertFalse(controller.isAccessible(yField, AccessController.WRITE, new Object[] {}));
	}

	@SuppressWarnings("unused")
	@TestCoversMethods({ "getReadableAnnotation", "getWritableAnnotation" })
	public void testGetAccessibleAnnotation() throws Exception
	{
		Object oField = new Object() {
			@Accessible(accessible = { @Authority(name = "onField") })
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

			@Accessible(accessible = { @Authority(name = "onGetter") })
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

			@Accessible(accessible = { @Authority(name = "onSetter") })
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
		assertEquals("onField", controller.getReadableAnnotation(oFieldField).accessible()[0].name());

		assertNotNull(controller.getReadableAnnotation(oGetterField));
		assertEquals("onGetter", controller.getReadableAnnotation(oGetterField).accessible()[0].name());

		assertNull(controller.getReadableAnnotation(oSetterField));

		// getWritableAnnotation

		controller = new FieldAccessController();

		assertNotNull(controller.getWritableAnnotation(oFieldField));
		assertEquals("onField", controller.getWritableAnnotation(oFieldField).accessible()[0].name());

		assertNull(controller.getWritableAnnotation(oGetterField));

		assertNotNull(controller.getWritableAnnotation(oSetterField));
		assertEquals("onSetter", controller.getWritableAnnotation(oSetterField).accessible()[0].name());
	}
	
	public void testIsAccessible() throws Exception
	{

	}

	public static class POJO1
	{
		private int		x;

		private String	y;

		public POJO1()
		{

		}

		public POJO1(int x, String y)
		{
			super();
			this.x = x;
			this.y = y;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L) })
		public int getX()
		{
			return x;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L) }, defaultAccessible = false)
		public String getY()
		{
			return y;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L) })
		public void setX(int x)
		{
			this.x = x;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L) }, defaultAccessible = false)
		public void setY(String y)
		{
			this.y = y;
		}

		@Override
		public String toString()
		{
			return "POJO1 [x=" + x + ", y=" + y + "]";
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + ((y == null) ? 0 : y.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			POJO1 other = (POJO1) obj;
			if(x != other.x)
				return false;
			if(y == null)
			{
				if(other.y != null)
					return false;
			}
			else if(!y.equals(other.y))
				return false;
			return true;
		}
	}

}

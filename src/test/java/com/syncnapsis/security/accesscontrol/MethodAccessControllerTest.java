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

import java.io.Serializable;
import java.lang.reflect.Method;

import com.syncnapsis.data.model.base.Identifiable;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.security.annotations.Authority;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestCoversClasses({MethodAccessController.class, AnnotationAccessController.class})
@TestExcludesMethods({"*DefaultAccessible", "getTargetClass"})
public class MethodAccessControllerTest extends LoggerTestCase
{
	public void testContainsAuthority() throws Exception
	{
		AnnotationAccessController<Object> controller = new AnnotationAccessController<Object>() {
			@Override
			public Class<Object> getTargetClass()
			{
				return Object.class;
			}

			@Override
			public boolean isAccessible(Object target, int operation, Object... authorities)
			{
				return false;
			}
		};

		Authority[] annotationAuthorities = POJO1.class.getMethod("getX").getAnnotation(Accessible.class).accessible();

		// valid values only (single argument)
		assertTrue(controller.containsAuthority(annotationAuthorities, "v1"));
		assertTrue(controller.containsAuthority(annotationAuthorities, 2));
		assertTrue(controller.containsAuthority(annotationAuthorities, "3"));
		assertTrue(controller.containsAuthority(annotationAuthorities, new Role<String>("v1")));
		assertTrue(controller.containsAuthority(annotationAuthorities, new Role<Long>(2L)));
		assertTrue(controller.containsAuthority(annotationAuthorities, new Role<String>("3")));
		assertTrue(controller.containsAuthority(annotationAuthorities, new Role<Long>(3L)));

		// valid values only (multiple arguments)
		assertTrue(controller.containsAuthority(annotationAuthorities, "v1", new Role<String>("v1")));
		assertTrue(controller.containsAuthority(annotationAuthorities, 2, new Role<Long>(2L)));
		assertTrue(controller.containsAuthority(annotationAuthorities, "3", new Role<String>("3"), new Role<Long>(3L)));
		
		// valid and invalid arguments (mixed)
		assertTrue(controller.containsAuthority(annotationAuthorities, 4, "v1"));
		assertTrue(controller.containsAuthority(annotationAuthorities, "4", 2));
		assertTrue(controller.containsAuthority(annotationAuthorities, new Role<String>("4"), "3"));
		assertTrue(controller.containsAuthority(annotationAuthorities, new Role<Long>(4L), new Role<String>("v1")));
		assertTrue(controller.containsAuthority(annotationAuthorities, 4, new Role<Long>(2L)));
		assertTrue(controller.containsAuthority(annotationAuthorities, "4", new Role<String>("3")));
		assertTrue(controller.containsAuthority(annotationAuthorities, 4L, new Role<Long>(3L)));

		// invalid values only (single argument)
		assertFalse(controller.containsAuthority(annotationAuthorities, 4));
		assertFalse(controller.containsAuthority(annotationAuthorities, "4"));
		assertFalse(controller.containsAuthority(annotationAuthorities, new Role<String>("4")));
		assertFalse(controller.containsAuthority(annotationAuthorities, new Role<Long>(4L)));

		// invalid values only (multiple arguments)
		assertFalse(controller.containsAuthority(annotationAuthorities, "4", new Role<String>("4"), new Role<Long>(4L)));
		
		// null arguments 
		assertFalse(controller.containsAuthority(annotationAuthorities, (Object[]) null));
		assertFalse(controller.containsAuthority(annotationAuthorities, new Object[] {}));
	}
	
	public void testIsAccessible() throws Exception
	{
		MethodAccessController controller = new MethodAccessController();
		
		Method method = POJO1.class.getMethod("getY");
		// valid values only (single argument)
		assertTrue(controller.isAccessible(method, "v1"));
		assertTrue(controller.isAccessible(method, new Object[]{2}));
		assertTrue(controller.isAccessible(method, "3"));
		assertTrue(controller.isAccessible(method, new Role<String>("v1")));
		assertTrue(controller.isAccessible(method, new Role<Long>(2L)));
		assertTrue(controller.isAccessible(method, new Role<String>("3")));
		assertTrue(controller.isAccessible(method, new Role<Long>(3L)));

		// valid values only (multiple arguments)
		assertTrue(controller.isAccessible(method, "v1", new Role<String>("v1")));
		assertTrue(controller.isAccessible(method, new Role<Long>(2L), 2));
		assertTrue(controller.isAccessible(method, "3", new Role<String>("3"), new Role<Long>(3L)));
		
		// valid and invalid arguments (mixed)
		assertTrue(controller.isAccessible(method, "v1", 4));
		assertTrue(controller.isAccessible(method, "4", 2));
		assertTrue(controller.isAccessible(method, new Role<String>("4"), "3"));
		assertTrue(controller.isAccessible(method, new Role<Long>(4L), new Role<String>("v1")));
		assertTrue(controller.isAccessible(method, new Role<Long>(2L), 4));
		assertTrue(controller.isAccessible(method, "4", new Role<String>("3")));
		assertTrue(controller.isAccessible(method, 4L, new Role<Long>(3L)));

		// invalid values only (single argument)
		assertFalse(controller.isAccessible(method, new Object[]{4}));
		assertFalse(controller.isAccessible(method, "4"));
		assertFalse(controller.isAccessible(method, new Role<String>("4")));
		assertFalse(controller.isAccessible(method, new Role<Long>(4L)));

		// invalid values only (multiple arguments)
		assertFalse(controller.isAccessible(method, "4", new Role<String>("4"), new Role<Long>(4L)));
		
		// null arguments 
		assertFalse(controller.isAccessible(method, (Object[]) null));
		assertFalse(controller.isAccessible(method, new Object[] {}));
	}
	
	public void testGetAccessibleAnnotation() throws Exception
	{
		Object annotated = new Object() {
			@Accessible(accessible = { @Authority(name = "onMethod") })
			public int method()
			{
				return 0;
			}
		};
		
		Method method = annotated.getClass().getMethod("method");

		MethodAccessController controller = new MethodAccessController();
		
		assertNotNull(controller.getAccessibleAnnotation(method));
		assertEquals("onMethod", controller.getAccessibleAnnotation(method).accessible()[0].name());
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

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L), @Authority(name = "3") })
		public int getX()
		{
			return x;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L), @Authority(name = "3") }, defaultAccessible = false)
		public String getY()
		{
			return y;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L), @Authority(name = "3") })
		public void setX(int x)
		{
			this.x = x;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L), @Authority(name = "3") }, defaultAccessible = false)
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

	public static class Role<PK extends Serializable> implements Identifiable<PK>
	{
		private PK	id;

		public Role(PK id)
		{
			super();
			this.id = id;
		}

		public PK getId()
		{
			return id;
		}

		public void setId(PK id)
		{
			this.id = id;
		}
	}
}

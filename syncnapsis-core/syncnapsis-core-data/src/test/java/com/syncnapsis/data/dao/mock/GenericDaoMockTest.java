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
package com.syncnapsis.data.dao.mock;

import java.util.Arrays;
import java.util.List;

import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

/**
 * @author ultimate
 * 
 */
public class GenericDaoMockTest extends LoggerTestCase
{
	@TestCoversMethods({ "save", "get", "delete", "getAll", "remove", "exists", "getByIdList" })
	public void testCRUD() throws Exception
	{
		GenericDaoMock<Entity, Long> genericDao = new GenericDaoMock<Entity, Long>(Entity.class);

		final long id = 1;

		assertNull(genericDao.get(id));

		Entity e = new Entity();
		e.setName("a name");

		genericDao.save(e);

		assertNotNull(e.getId());
		assertEquals(1L, (long) e.getId());

		Entity result = genericDao.get(id);
		assertEquals(result, e);
		assertSame(result, e);

		Entity e2 = new Entity();
		e2.setName("two");
		genericDao.save(e2);
		Entity e3 = new Entity();
		e3.setName("three");
		genericDao.save(e3);
		Entity e4 = new Entity();
		e4.setName("four");
		genericDao.save(e4);
		Entity e5 = new Entity();
		e5.setName("five");
		genericDao.save(e5);

		assertEquals(5, genericDao.getAll().size());

		assertNotNull(genericDao.get(e4.getId()));
		genericDao.remove(e4);
		assertNull(genericDao.get(e4.getId()));

		assertEquals(4, genericDao.getAll().size());
		
		assertTrue(genericDao.exists(3L));
		assertFalse(genericDao.exists(4L));
		assertFalse(genericDao.exists(999L));
		
		List<Entity> results = genericDao.getByIdList(Arrays.asList(new Long[] {3L, 99L, 1L, 70L}));
		assertEquals(4, results.size());
		assertEquals(e3, results.get(0));
		assertNull(results.get(1));
		assertEquals(e, results.get(2));
		assertNull(results.get(3));
	}

	@TestCoversMethods({ "save", "get", "delete", "getAll", "remove", "exists", "getByIdList" })
	public void testCRUDActivatable() throws Exception
	{
		GenericDaoMock<EntityA, Long> genericDao = new GenericDaoMock<EntityA, Long>(EntityA.class);

		final long id = 1;

		assertNull(genericDao.get(id));

		EntityA e = new EntityA();
		e.setName("a name");

		genericDao.save(e);

		assertNotNull(e.getId());
		assertEquals(1L, (long) e.getId());

		EntityA result = genericDao.get(id);
		assertEquals(result, e);
		assertSame(result, e);

		EntityA e2 = new EntityA();
		e2.setName("two");
		genericDao.save(e2);
		EntityA e3 = new EntityA();
		e3.setName("three");
		genericDao.save(e3);
		EntityA e4 = new EntityA();
		e4.setName("four");
		genericDao.save(e4);
		EntityA e5 = new EntityA();
		e5.setName("five");
		genericDao.save(e5);

		assertEquals(0, genericDao.getAll().size());
		assertEquals(0, genericDao.getAll(true).size());
		assertEquals(5, genericDao.getAll(false).size());

		e2.setActivated(true);
		e4.setActivated(true);

		assertEquals(2, genericDao.getAll(true).size());
		assertEquals(5, genericDao.getAll(false).size());
		
		try
		{
			genericDao.delete(e4);
			fail("expected exception not occurred!");
		}
		catch(UnsupportedOperationException ex)
		{
			assertNotNull(ex);
		}

		assertNotNull(genericDao.get(e4.getId()));
		genericDao.remove(e4);
		assertNotNull(genericDao.get(e4.getId()));
		assertFalse(e4.isActivated());

		assertEquals(1, genericDao.getAll(true).size());
		assertEquals(5, genericDao.getAll(false).size());
		
		assertTrue(genericDao.exists(3L));
		assertTrue(genericDao.exists(4L));
		assertFalse(genericDao.exists(999L));
		
		List<EntityA> results = genericDao.getByIdList(Arrays.asList(new Long[] {3L, 99L, 1L, 70L}));
		assertEquals(4, results.size());
		assertEquals(e3, results.get(0));
		assertNull(results.get(1));
		assertEquals(e, results.get(2));
		assertNull(results.get(3));
	}

	public static class Entity extends BaseObject<Long>
	{
		private String	name;

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	}

	public static class EntityA extends ActivatableInstance<Long>
	{
		private String	name;

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	}
}

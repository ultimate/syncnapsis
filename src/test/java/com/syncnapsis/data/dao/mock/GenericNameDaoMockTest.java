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

import java.util.List;

import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.tests.LoggerTestCase;

/**
 * @author ultimate
 * 
 */
public class GenericNameDaoMockTest extends LoggerTestCase
{
	private GenericNameDaoMock<Entity, Long>	genericNameDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		genericNameDao = new GenericNameDaoMock<Entity, Long>(Entity.class, "name");

		Entity e1 = new Entity();
		e1.setName("one");
		e1.setActivated(true);
		genericNameDao.save(e1);

		Entity e2 = new Entity();
		e2.setName("two");
		e2.setActivated(true);
		genericNameDao.save(e2);

		Entity e3 = new Entity();
		e3.setName("three");
		e3.setActivated(false);
		genericNameDao.save(e3);

		Entity e4 = new Entity();
		e4.setName("four");
		e4.setActivated(false);
		genericNameDao.save(e4);

		Entity e5 = new Entity();
		e5.setName("five");
		e5.setActivated(true);
		genericNameDao.save(e5);
	}

	public void testGetOrderedByName() throws Exception
	{
		List<Entity> result;

		result = genericNameDao.getOrderedByName(true);
		assertEquals(3, result.size());
		assertEquals("five", result.get(0).getName());
		assertEquals("one", result.get(1).getName());
		assertEquals("two", result.get(2).getName());

		result = genericNameDao.getOrderedByName(false);
		assertEquals(5, result.size());
		assertEquals("five", result.get(0).getName());
		assertEquals("four", result.get(1).getName());
		assertEquals("one", result.get(2).getName());
		assertEquals("three", result.get(3).getName());
		assertEquals("two", result.get(4).getName());
	}

	public void testGetByPrefix() throws Exception
	{
		List<Entity> result;

		String[] prefixes = new String[] { "f", "fo", "t", "tw" };

		for(String prefix : prefixes)
		{
			for(int rows = 0; rows < 5; rows++)
			{
				result = genericNameDao.getByPrefix(prefix, rows, true);
				assertTrue(result.size() <= rows);
				for(Entity e : result)
					assertTrue(e.getName().startsWith(prefix));

				result = genericNameDao.getByPrefix(prefix, rows, true);
				assertTrue(result.size() <= rows);
				for(Entity e : result)
					assertTrue(e.getName().startsWith(prefix));
			}
		}
	}

	public void testGetByName() throws Exception
	{
		assertNotNull(genericNameDao.getByName("one"));
		assertNotNull(genericNameDao.getByName("ONE"));
		assertNotNull(genericNameDao.getByName("oNe"));
		assertNotNull(genericNameDao.getByName("two"));
		assertNull(genericNameDao.getByName("zero"));
	}

	public void testIsNameAvailable() throws Exception
	{
		assertFalse(genericNameDao.isNameAvailable("one"));
		assertFalse(genericNameDao.isNameAvailable("ONE"));
		assertFalse(genericNameDao.isNameAvailable("oNe"));
		assertFalse(genericNameDao.isNameAvailable("two"));
		assertTrue(genericNameDao.isNameAvailable("zero"));
	}

	public void testGetName() throws Exception
	{
		Entity e = new Entity();
		e.setName("a name");
		
		assertSame(e.getName(), genericNameDao.getName(e));
	}

	public static class Entity extends ActivatableInstance<Long>
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

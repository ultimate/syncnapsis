/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 * 
 */
@TestExcludesMethods("nextId")
public class UniversalDaoMockTest extends LoggerTestCase
{
	private UniversalDaoMock	universalDao	= new UniversalDaoMock();

	@TestCoversMethods({ "save", "get", "delete", "getAll" })
	public void testCRUD() throws Exception
	{
		final long id = 1;

		assertNull(universalDao.get(Entity.class, id));

		Entity e = new Entity();
		e.setName("a name");

		universalDao.save(e);

		assertNotNull(e.getId());
		assertEquals(1L, (long) e.getId());

		Entity result = universalDao.get(Entity.class, id);
		assertEquals(result, e);
		assertSame(result, e);

		Entity e2 = new Entity();
		e2.setName("two");
		universalDao.save(e2);
		Entity e3 = new Entity();
		e3.setName("three");
		universalDao.save(e3);
		Entity e4 = new Entity();
		e4.setName("four");
		universalDao.save(e4);
		Entity e5 = new Entity();
		e5.setName("five");
		universalDao.save(e5);

		assertEquals(5, universalDao.getAll(Entity.class).size());

		assertNotNull(universalDao.get(Entity.class, e4.getId()));
		universalDao.delete(e4);
		assertNull(universalDao.get(Entity.class, e4.getId()));
		

		assertEquals(4, universalDao.getAll(Entity.class).size());
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

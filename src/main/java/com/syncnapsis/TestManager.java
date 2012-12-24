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
package com.syncnapsis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.syncnapsis.data.service.UniversalManager;

public class TestManager extends TestServer implements UniversalManager
{
	private Map<Integer, TestEntity>	entities	= new HashMap<Integer, TestEntity>();
	
	public TestManager()
	{
		entities.put(1, new TestEntity(1, "one", "value1"));
		entities.put(2, new TestEntity(2, "two", "value2"));
		entities.put(3, new TestEntity(3, "three", "value3"));
		entities.put(4, new TestEntity(4, "four", "value4"));
		entities.put(5, new TestEntity(5, "five", "value5"));
	}

	@Override
	public <T> T get(Class<? extends T> clazz, Serializable id)
	{
		if(clazz == TestEntity.class)
			return (T) entities.get(id);
		return null;
	}

	@Override
	public <T> List<T> getAll(Class<? extends T> clazz)
	{
		if(clazz == TestEntity.class)
		{
			List<TestEntity> list = new ArrayList<TestEntity>();
			list.addAll(entities.values());
			return (List<T>) list;
		}
		return null;
	}

	@Override
	public <T> T save(T o)
	{
		if(o != null && o.getClass() == TestEntity.class)
			return (T) entities.put(((TestEntity) o).getId(), (TestEntity) o);
		return o;
	}

	@Override
	public void delete(Object o)
	{
		if(o != null && o.getClass() == TestEntity.class)
			entities.remove(((TestEntity) o).getId());
	}
}

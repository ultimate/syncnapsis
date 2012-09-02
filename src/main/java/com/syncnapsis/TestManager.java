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

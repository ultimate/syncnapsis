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
package com.syncnapsis.tests;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import com.syncnapsis.data.dao.GenericDao;
import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public abstract class GenericDaoTestCase<T extends BaseObject<PK>, PK extends Serializable> extends BaseDaoTestCase
{
	protected GenericDao<T, PK> genericDao;
	protected T entity;
	protected String entityProperty;
	protected Object entityPropertyValue;
	protected PK existingEntityId;
	protected PK badEntityId;

	public void setGenericDao(GenericDao<T, PK> genericDao)
	{
		this.genericDao = genericDao;
	}

	public void setEntity(T entity)
	{
		this.entity = entity;
	}

	public void setEntityProperty(String entityProperty)
	{
		this.entityProperty = entityProperty;
	}

	public void setEntityPropertyValue(Object entityPropertyValue)
	{
		this.entityPropertyValue = entityPropertyValue;
	}

	public void setExistingEntityId(PK existingEntityId)
	{
		this.existingEntityId = existingEntityId;
	}

	public void setBadEntityId(PK badEntityId)
	{
		this.badEntityId = badEntityId;
	}

	@SuppressWarnings("rawtypes")
	@TestCoversMethods({"get", "save", "remove", "delete", "saveOrUpdate", "overwriteId" })
	public void testCRUD() throws Exception
	{
		logger.debug("testing CRUD...");
		
		String setter = "set" + Character.toUpperCase(entityProperty.charAt(0)) + entityProperty.substring(1);
		String getter = "get" + Character.toUpperCase(entityProperty.charAt(0)) + entityProperty.substring(1);
		if(entityPropertyValue.getClass().equals(boolean.class) || entityPropertyValue.getClass().equals(Boolean.class))
		{
			getter = "is" + getter.substring(3);
		}
		
		// create
		entity = genericDao.save(entity);
		assertNotNull(entity.getId());

		// retrieve
		T entity2 = genericDao.get(entity.getId());
		assertNotNull(entity2);
		assertEquals(entity, entity2);

		// update
		Method setterMethod = getMethod(entity2.getClass(), new MethodCall(setter, void.class, entityPropertyValue));
		assertNotNull(setterMethod);
		setterMethod.invoke(entity2, entityPropertyValue);
		genericDao.save(entity2);

		entity2 = genericDao.get(entity2.getId());
		assertEquals(entityPropertyValue, entity2.getClass().getMethod(getter).invoke(entity2));

		// delete
		if(entity instanceof ActivatableInstance)
		{
			assertEquals("deactivated", genericDao.remove(entity2));
			entity2 = genericDao.get(entity2.getId());
			assertFalse(((ActivatableInstance) entity2).isActivated());
		}
		else
		{
			assertEquals("deleted", genericDao.remove(entity2));
			try
			{
				genericDao.get(entity2.getId());
				fail("Expected Exception not occurred");
			}
			catch(ObjectNotFoundException e)
			{
				assertNotNull(e);
			}
		}
	}

	public void testExists() throws Exception
	{
		logger.debug("testing exists...");
		logger.debug(entity.getClass() + " [" + existingEntityId + "] exists? -> " + genericDao.exists(existingEntityId));
		assertTrue(genericDao.exists(existingEntityId));
		logger.debug(entity.getClass() + " [" + badEntityId + "] exists? -> " + genericDao.exists(badEntityId));
		assertFalse(genericDao.exists(badEntityId));
	}

	@SuppressWarnings("unchecked")
	public void testGetByIdList() throws Exception
	{
		logger.debug("testing getByIdList...");

		Object[] idArray = { existingEntityId, badEntityId, existingEntityId };
		
		List<PK> idList = new ArrayList<PK>();
		
		for(Object i : idArray)
		{
			idList.add((PK) i);
		}
		
		List<T> result = genericDao.getByIdList(idList);

		assertEquals(idArray.length, result.size());
		assertNotNull(result.get(0));
		assertNull(result.get(1));
		assertNotNull(result.get(2));
	}

	@SuppressWarnings("rawtypes")
	public void testGetAll() throws Exception
	{
		logger.debug("testing getAll...");

		List<T> result;

		logger.debug("...testing getAll(false)...");
		result = genericDao.getAll(false);
		assertNotNull(result);
		logger.debug(result.size() + " entities found");
		assertTrue(result.size() > 0);
		int oldSize = result.size();
		int newSize = oldSize;
		
		T entity2 = result.get(0);

		if(entity instanceof ActivatableInstance)
		{
			((ActivatableInstance) entity2).setActivated(false);
			entity2 = genericDao.save(entity2);
			newSize--;
		}

		logger.debug("...testing getAll(true)...");
		result = genericDao.getAll(true);
		assertNotNull(result);
		logger.debug(result.size() + " entities found");
		assertTrue(result.size() == newSize);

		if(entity instanceof ActivatableInstance)
		{
			((ActivatableInstance) entity2).setActivated(true);
			entity2 = genericDao.save(entity2);
		}
	}
}
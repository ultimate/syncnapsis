package com.syncnapsis.tests;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.dao.GenericNameDao;
import com.syncnapsis.data.model.base.BaseObject;

public abstract class GenericNameDaoTestCase<T extends BaseObject<PK>, PK extends Serializable> extends GenericDaoTestCase<T, PK>
{
	protected GenericNameDao<T, PK> genericNameDao;
	protected String existingEntityName;
	
	public void setGenericNameDao(GenericNameDao<T, PK> genericNameDao)
	{
		this.genericNameDao = genericNameDao;
		this.setGenericDao(genericNameDao);
	}

	public void setExistingEntityName(String existingEntityName)
	{
		this.existingEntityName = existingEntityName;
	}

	public void testGetOrderedByName() throws Exception
	{
		List<T> result = genericNameDao.getOrderedByName(true);
		assertNotNull(result);
		assertTrue(result.size() > 0);
	}
	
	public void testGetByPrefix() throws Exception
	{
		List<T> result = genericNameDao.getByPrefix(existingEntityName.substring(0,1), -1, true);
		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

	public void testGetByName() throws Exception
	{
		assertNotNull(genericNameDao.getByName(existingEntityName));
	}
}
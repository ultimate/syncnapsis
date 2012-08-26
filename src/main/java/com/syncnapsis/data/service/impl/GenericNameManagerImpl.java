package com.syncnapsis.data.service.impl;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.dao.GenericNameDao;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.GenericNameManager;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manager-Implementierung für den generischen Zugriff auf beliebige
 * Modell-Klassen über GenericDao.
 * 
 * @author ultimate
 */
@Transactional
public class GenericNameManagerImpl<T extends BaseObject<PK>, PK extends Serializable> extends GenericManagerImpl<T, PK> implements GenericNameManager<T, PK>
{
	/**
	 * GenericNameDao für den Datenbankzugriff.
	 */
	protected GenericNameDao<T, PK>	genericNameDao;

	/**
	 * Standard Constructor, der die DAOs speichert.
	 * 
	 * @param genericDao - GenericDao für den Datenbankzugriff
	 */
	public GenericNameManagerImpl(final GenericNameDao<T, PK> genericNameDao)
	{
		super(genericNameDao);
		this.genericNameDao = genericNameDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.GenericNameManager#getByName(java.lang.String)
	 */
	@Override
	public T getByName(String name)
	{
		return genericNameDao.getByName(name);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.GenericNameManager#getOrderedByName()
	 */
	@Override
	public List<T> getOrderedByName()
	{
		return this.getOrderedByName(true);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.GenericNameManager#getOrderedByName(boolean)
	 */
	@Override
	public List<T> getOrderedByName(boolean returnOnlyActivated)
	{
		return genericNameDao.getOrderedByName(returnOnlyActivated);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.GenericNameManager#getByPrefix(java.lang.String, int, boolean)
	 */
	@Override
	public List<T> getByPrefix(String prefix, int nRows, boolean returnOnlyActivated)
	{
		return genericNameDao.getByPrefix(prefix, nRows, returnOnlyActivated);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.GenericNameManager#isNameAvailable(java.lang.String)
	 */
	@Override
	public boolean isNameAvailable(String name)
	{
		return genericNameDao.isNameAvailable(name);
	}
}

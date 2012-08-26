package com.syncnapsis.data.service.impl;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.dao.UniversalDao;
import com.syncnapsis.data.service.UniversalManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Universelle Manager-Implementierung für den Zugriff auf beliebige Klassen
 * 
 * @author ultimate
 */
public class UniversalManagerImpl implements UniversalManager
{
	/**
	 * Logger-Instanz für die Benutzung in allen Subklassen.
	 */
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * UniversalDao für den Datenbankzugriff.
	 */
	protected UniversalDao	universalDao;

	/**
	 * Standard Constructor, der die DAOs speichert.
	 * 
	 * @param universalDao - UniversalDao für den Datenbankzugriff
	 */
	public UniversalManagerImpl(final UniversalDao universalDao)
	{
		this.universalDao = universalDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UniversalManager#get(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T get(Class<? extends T> clazz, Serializable id)
	{
		return universalDao.get(clazz, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UniversalManager#getAll(java.lang.Class)
	 */
	@Override
	public <T> List<T> getAll(Class<? extends T> clazz)
	{
		return universalDao.getAll(clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UniversalManager#save(java.lang.Object)
	 */
	@Override
	public <T> T save(T o)
	{
		return universalDao.save(o);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UniversalManager#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object o)
	{
		universalDao.delete(o);
	}
}

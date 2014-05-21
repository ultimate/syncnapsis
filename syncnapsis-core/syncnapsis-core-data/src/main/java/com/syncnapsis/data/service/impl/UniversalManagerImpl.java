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
package com.syncnapsis.data.service.impl;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.dao.UniversalDao;
import com.syncnapsis.data.service.UniversalManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Universelle Manager-Implementierung für den Zugriff auf beliebige Klassen
 * 
 * @author ultimate
 */
@Transactional
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

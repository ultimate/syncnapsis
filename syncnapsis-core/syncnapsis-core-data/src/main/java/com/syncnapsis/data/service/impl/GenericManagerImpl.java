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
package com.syncnapsis.data.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.syncnapsis.data.dao.GenericDao;
import com.syncnapsis.data.model.base.Identifiable;
import com.syncnapsis.data.service.GenericManager;
import com.syncnapsis.utils.spring.Bean;

/**
 * Manager-Implementierung f�r den generischen Zugriff auf beliebige
 * Modell-Klassen �ber GenericDao.
 * 
 * @author ultimate
 */
@Transactional
public class GenericManagerImpl<T extends Identifiable<PK>, PK extends Serializable> extends Bean implements GenericManager<T, PK>
{
	/**
	 * Logger-Instanz f�r die Benutzung in allen Subklassen.
	 */
	protected final Logger		logger	= LoggerFactory.getLogger(getClass());
	/**
	 * GenericDao f�r den Datenbankzugriff.
	 */
	protected GenericDao<T, PK>	genericDao;

	/**
	 * Standard Constructor, der die DAOs speichert.
	 * 
	 * @param genericDao - GenericDao f�r den Datenbankzugriff
	 */
	public GenericManagerImpl(final GenericDao<T, PK> genericDao)
	{
		// super(genericDao);
		this.genericDao = genericDao;
	}

	/*
	 * (non-Javadoc)
	 * @see org.com.syncnapsis.GenericManager#getAll()
	 */
	@Override
	public List<T> getAll()
	{
		return genericDao.getAll();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.com.syncnapsisericManager#getAll(boolean)
	 */
	@Override
	public List<T> getAll(boolean returnOnlyActivated)
	{
		return genericDao.getAll(returnOnlyActivated);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.servcom.syncnapsisManager#get(java.io.Serializable)
	 */
	@Override
	public T get(PK id)
	{
		return genericDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.com.syncnapsisger#getByIdList(java.util.List)
	 */
	@Override
	public List<T> getByIdList(List<PK> idList)
	{
		return genericDao.getByIdList(idList);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.Genecom.syncnapsisexists(java.io.Serializable)
	 */
	@Override
	public boolean exists(PK id)
	{
		return genericDao.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.service.GenericMcom.syncnapsis(com.syncnapsis.model.base.BaseObject
	 */
	@Override
	public T save(T object)
	{
		return genericDao.save(object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.service.GenericManager#com.syncnapsis.model.base.BaseObject
	 */
	@Override
	public String remove(T object)
	{
		return genericDao.remove(object);
	}
}

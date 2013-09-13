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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.syncnapsis.data.dao.GenericDao;
import com.syncnapsis.data.dao.hibernate.GenericDaoHibernate;
import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.data.model.base.Identifiable;

/**
 * @author ultimate
 * 
 */
public class GenericDaoMock<T extends Identifiable<PK>, PK extends Serializable> extends UniversalDaoMock implements GenericDao<T, PK>
{
	/**
	 * The model class
	 */
	protected Class<? extends T>	persistentClass;
	/**
	 * Enable true DELETE for {@link ActivatableInstance}s via
	 * {@link GenericDaoHibernate#delete(Object)}
	 */
	protected boolean				deleteEnabled;
	/**
	 * Is the persistent class ActivatableInstance?
	 */
	protected boolean				activatable;

	/**
	 * Default constructor requiring the persistent class
	 * 
	 * @param persistentClass - the persistent class
	 */
	public GenericDaoMock(final Class<? extends T> persistentClass)
	{
		this(persistentClass, false);
	}

	/**
	 * Create a new DAO-Instance for the givne model class
	 * 
	 * @param persistentClass - the model class
	 * @param deleteEnabled - enable true DELETE for {@link ActivatableInstance}s via
	 *            {@link GenericDaoHibernate#delete(Object)}
	 */
	public GenericDaoMock(final Class<? extends T> persistentClass, boolean deleteEnabled)
	{
		this.persistentClass = persistentClass;
		this.deleteEnabled = deleteEnabled;

		T inst = null;
		try
		{
			inst = this.persistentClass.newInstance();
		}
		catch(Exception e)
		{
			logger.error("oh oh...");
			e.printStackTrace();
		}
		this.activatable = (inst instanceof ActivatableInstance);
		if(!activatable)
			this.deleteEnabled = true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericDao#getAll()
	 */
	@Override
	public List<T> getAll()
	{
		return getAll(true);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.com.syncnapsisDao#getAll(boolean)
	 */
	@Override
	public List<T> getAll(boolean returnOnlyActivated)
	{
		List<T> results = getAll(this.persistentClass);
		if(results == null)
			return new LinkedList<T>();
		if(this.activatable && returnOnlyActivated)
		{
			List<T> copy = new ArrayList<T>(results);
			for(T t : copy)
			{
				if(!((ActivatableInstance<PK>) t).isActivated())
					results.remove(t);
			}
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericDao#get(java.io.Serializable)
	 */
	@Override
	public T get(PK id)
	{
		return get(persistentClass, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericDao#getByIdList(java.util.List)
	 */
	@Override
	public List<T> getByIdList(List<PK> idList)
	{
		List<T> results = getAll(this.persistentClass);
		if(results == null)
			return new LinkedList<T>();
		List<T> copy = new ArrayList<T>(results);
		for(T t : copy)
		{
			if(!idList.contains(t.getId()))
				results.remove(t);
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericDao#exists(java.io.Serializable)
	 */
	@Override
	public boolean exists(PK id)
	{
		return get(id) != null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericDao#save(com.syncnapsis.data.model.base.Identifiable)
	 */
	@Override
	public T save(T object)
	{
		return (T) super.save(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.GenericDao#remove(orcom.syncnapsis.base.BaseObject)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String remove(T object)
	{
		if(this.activatable)
		{
			((ActivatableInstance) object).setActivated(false);
			super.save(object);
			return "deactivated";
		}
		else
		{
			super.delete(object);
			return "deleted";
		}
	}

	/**
	 * This method only supports DELETE non-{@link ActivatableInstance}s or for
	 * {@link ActivatableInstance}s if {@link GenericDaoHibernate#isDeleteEnabled()} is true.
	 */
	@Override
	public void delete(T o)
	{
		if(!this.deleteEnabled)
			throw new UnsupportedOperationException("delete(...) is not supported for GenericDaoHibernate. Use remove(...) instead!");
		else
			super.delete(o);
	}
}

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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.data.dao.UniversalDao;
import com.syncnapsis.data.model.base.Identifiable;

/**
 * @author ultimate
 * 
 */
public class UniversalDaoMock implements UniversalDao
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger				logger		= LoggerFactory.getLogger(getClass());

	protected Map<Class<?>, Map<Serializable, ?>>	db			= new HashMap<Class<?>, Map<Serializable, ?>>();
	private long									sequence	= 1;

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UniversalDao#get(java.lang.Class, java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<? extends T> clazz, Serializable id)
	{
		if(db.containsKey(clazz))
			return (T) db.get(clazz).get(id);
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UniversalDao#getAll(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getAll(Class<? extends T> clazz)
	{
		if(db.containsKey(clazz))
			return new ArrayList<T>((Collection<? extends T>) db.get(clazz).values());
		else
			return new ArrayList<T>();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UniversalDao#save(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T save(T o)
	{
		if(o == null)
			throw new IllegalArgumentException("entity must not be null!");
		if(!db.containsKey(o.getClass()))
			db.put(o.getClass(), new HashMap<Serializable, T>());
		long id = nextId();
		((Map<Serializable, T>) db.get(o.getClass())).put(id, o);
		if(o instanceof Identifiable)
			((Identifiable<Serializable>) o).setId(id);
		return o;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UniversalDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object o)
	{
		if(o == null)
			throw new IllegalArgumentException("entity must not be null!");
		if(!db.containsKey(o.getClass()))
			return;
		db.get(o.getClass()).values().remove(o);
	}

	/**
	 * Get the next id from the sequence
	 * 
	 * @return the id
	 */
	protected synchronized long nextId()
	{
		return sequence++;
	}
}

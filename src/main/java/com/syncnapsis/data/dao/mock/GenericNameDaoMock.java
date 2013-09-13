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
import java.util.List;

import com.syncnapsis.data.dao.GenericNameDao;
import com.syncnapsis.data.dao.hibernate.GenericDaoHibernate;
import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.data.model.base.Identifiable;

/**
 * @author ultimate
 * 
 */
public class GenericNameDaoMock<T extends Identifiable<PK>, PK extends Serializable> extends GenericDaoMock<T, PK> implements GenericNameDao<T, PK>
{
	/**
	 * The Field to sort by
	 */
	protected String	nameField;

	/**
	 * Create a new DAO for the given persistent class and name field
	 * 
	 * @param persistentClass - the persistent class
	 * @param nameField - the name field to sort by
	 */
	public GenericNameDaoMock(final Class<T> persistentClass, String nameField)
	{
		super(persistentClass);
		this.nameField = nameField;
	}

	/**
	 * Create a new DAO for the given persistent class and name field
	 * 
	 * @param persistentClass - the persistent class
	 * @param nameField - the name field to sort by
	 * @param deleteEnabled - Enable true DELETE for {@link ActivatableInstance}s via
	 *            {@link GenericDaoHibernate#delete(Object)}
	 */
	public GenericNameDaoMock(final Class<T> persistentClass, String nameField, boolean deleteEnabled)
	{
		super(persistentClass, deleteEnabled);
		this.nameField = nameField;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericNameDao#getOrderedByName(boolean)
	 */
	@Override
	public List<T> getOrderedByName(boolean returnOnlyActivated)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericNameDao#getByPrefix(java.lang.String, int, boolean)
	 */
	@Override
	public List<T> getByPrefix(String prefix, int nRows, boolean returnOnlyActivated)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericNameDao#getByName(java.lang.String)
	 */
	@Override
	public T getByName(String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericNameDao#isNameAvailable(java.lang.String)
	 */
	@Override
	public boolean isNameAvailable(String name)
	{
		// TODO Auto-generated method stub
		return false;
	}

}

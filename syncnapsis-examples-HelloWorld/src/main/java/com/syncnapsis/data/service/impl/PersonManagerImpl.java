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

import java.util.List;

import com.syncnapsis.data.dao.PersonDao;
import com.syncnapsis.data.model.Person;
import com.syncnapsis.data.service.PersonManager;
import com.syncnapsis.data.service.impl.GenericNameManagerImpl;

/**
 * A simple PersonManager-Impl
 * 
 * @author ultimate
 */
public class PersonManagerImpl extends GenericNameManagerImpl<Person, Long> implements PersonManager
{
	/**
	 * The PersonDao to user
	 */
	private PersonDao	personDao;

	/**
	 * Standard Constructor
	 * 
	 * @param personDao - the PersonDao to use
	 */
	public PersonManagerImpl(PersonDao personDao)
	{
		super(personDao);
		this.personDao = personDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PersonManager#getInRang(java.lang.Long, java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public List<Person> getInRange(Long minId, Long maxId, String orderBy)
	{
		return personDao.getInRange(minId, maxId, orderBy);
	}
}

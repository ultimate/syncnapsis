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

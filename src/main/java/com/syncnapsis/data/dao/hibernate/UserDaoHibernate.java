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
package com.syncnapsis.data.dao.hibernate;

import org.hibernate.exception.ConstraintViolationException;

import com.syncnapsis.data.dao.UserDao;
import com.syncnapsis.data.model.User;
import com.syncnapsis.exceptions.UserExistsException;
import com.syncnapsis.exceptions.UserNotFoundException;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf User
 * 
 * @author ultimate
 */
public class UserDaoHibernate extends GenericNameDaoHibernate<User, Long> implements UserDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse User
	 */
	public UserDaoHibernate()
	{
		super(User.class, "username");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.dao.hibernate.GenericDaoHibernate#save(com.syncnapsis.data.model.base
	 * .BaseObject)
	 */
	@Override
	public User save(User user)
	{
		logger.debug("saving user: " + user.getUsername() + " [" + user.getId() + "]");
		try
		{
			user = super.save(user);
			currentSession().flush();
		}
		catch(ConstraintViolationException e)
		{
			throw new UserExistsException("User already exists by name or e-Mail: " + e.getMessage());
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.hibernate.GenericNameDaoHibernate#getByName(java.lang.String)
	 */
	@Override
	public User getByName(String name)
	{
		User user = super.getByName(name);
		if(user == null)
			throw new UserNotFoundException(name);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UserDao#getByEmail(java.lang.String)
	 */
	@Override
	public User getByEmail(String email) throws UserNotFoundException
	{
		User user = (User) createQuery("from User where lower(email)=lower(?) and activated=true", email).uniqueResult();
		if(user == null)
			throw new UserNotFoundException(email);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UserDao#isEmailRegistered(java.lang.String)
	 */
	@Override
	public boolean isEmailRegistered(String email)
	{
		try
		{
			User u = this.getByEmail(email);
			return (u != null);
		}
		catch(UserNotFoundException e)
		{
			return false;
		}
	}
}

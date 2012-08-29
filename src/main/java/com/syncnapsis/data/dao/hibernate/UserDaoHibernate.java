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
	 * @see com.syncnapsis.data.dao.hibernate.GenericDaoHibernate#save(com.syncnapsis.data.model.base.BaseObject)
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
}

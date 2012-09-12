package com.syncnapsis.data.dao.hibernate;

import org.hibernate.exception.ConstraintViolationException;
import com.syncnapsis.data.dao.UserRoleDao;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.exceptions.UserRoleExistsException;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf UserRole
 * 
 * @author ultimate
 */
public class UserRoleDaoHibernate extends GenericNameDaoHibernate<UserRole, Long> implements UserRoleDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse UserRole
	 */
	public UserRoleDaoHibernate()
	{
		super(UserRole.class, "rolename");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.hibernate.GenericDaoHibernate#save(com.syncnapsis.data.model.base.BaseObject)
	 */
	@Override
	public UserRole save(UserRole userRole)
	{
		try
		{
			userRole = super.save(userRole);
			currentSession().flush();
		}
		catch(ConstraintViolationException e)
		{
			e.printStackTrace();
			throw new UserRoleExistsException("UserRole already exists by rolename: " + e.getMessage());
		}
		return userRole;
	}
}

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

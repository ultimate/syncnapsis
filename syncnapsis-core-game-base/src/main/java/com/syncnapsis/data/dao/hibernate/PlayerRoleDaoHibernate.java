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

import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import com.syncnapsis.data.dao.PlayerRoleDao;
import com.syncnapsis.data.model.PlayerRole;
import com.syncnapsis.exceptions.PlayerRoleExistsException;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class PlayerRoleDaoHibernate extends GenericNameDaoHibernate<PlayerRole, Long> implements PlayerRoleDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse UserRole
	 */
	@Deprecated
	public PlayerRoleDaoHibernate()
	{
		super(PlayerRole.class, "rolename");
	}

	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse UserRole and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public PlayerRoleDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, PlayerRole.class, "rolename");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.dao.hibernate.GenericDaoHibernate#save(com.syncnapsis.data.model.base
	 * .BaseObject)
	 */
	@Override
	public PlayerRole save(PlayerRole userRole)
	{
		try
		{
			userRole = super.save(userRole);
			currentSession().flush();
		}
		catch(ConstraintViolationException e)
		{
			e.printStackTrace();
			throw new PlayerRoleExistsException("UserRole already exists by rolename: " + e.getMessage());
		}
		return userRole;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.PlayerRoleDao#getByMask(int)
	 */
	@Override
	public PlayerRole getByMask(int mask)
	{
		return (PlayerRole) createQuery("from PlayerRole where mask=?", mask).uniqueResult();
	}
}

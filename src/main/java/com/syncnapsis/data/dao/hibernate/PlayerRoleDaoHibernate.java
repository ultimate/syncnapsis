package com.syncnapsis.data.dao.hibernate;

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
	public PlayerRoleDaoHibernate()
	{
		super(PlayerRole.class, "rolename");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.hibernate.GenericDaoHibernate#save(com.syncnapsis.data.model.base.BaseObject)
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
}

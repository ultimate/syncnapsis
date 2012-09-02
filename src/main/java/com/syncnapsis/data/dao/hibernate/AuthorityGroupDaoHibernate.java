package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.AuthorityGroupDao;
import com.syncnapsis.data.model.AuthorityGroup;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AuthorityGroup.
 * 
 * @author ultimate
 */
public class AuthorityGroupDaoHibernate extends GenericNameDaoHibernate<AuthorityGroup, Long> implements AuthorityGroupDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse AuthorityGroup
	 */
	public AuthorityGroupDaoHibernate()
	{
		super(AuthorityGroup.class, "name");
	}

}

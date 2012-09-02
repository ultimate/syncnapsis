package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.AuthorityDao;
import com.syncnapsis.data.model.Authority;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf Authority.
 * 
 * @author ultimate
 */
public class AuthorityDaoHibernate extends GenericNameDaoHibernate<Authority, Long> implements AuthorityDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse Authority
	 */
	public AuthorityDaoHibernate()
	{
		super(Authority.class, "name");
	}

}

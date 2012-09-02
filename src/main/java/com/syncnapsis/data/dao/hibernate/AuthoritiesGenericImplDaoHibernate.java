package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.AuthoritiesGenericImplDao;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AuthoritiesGenericImpl.
 * 
 * @author ultimate
 */
public class AuthoritiesGenericImplDaoHibernate extends GenericDaoHibernate<AuthoritiesGenericImpl, Long> implements AuthoritiesGenericImplDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse AuthoritiesGenericImpl
	 */
	public AuthoritiesGenericImplDaoHibernate()
	{
		super(AuthoritiesGenericImpl.class);
	}

}

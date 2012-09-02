package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.EmpireEmpireContactDao;
import com.syncnapsis.data.model.contacts.EmpireEmpireContact;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf EmpireEmpireContact
 * 
 * @author ultimate
 */
public class EmpireEmpireContactDaoHibernate extends GenericDaoHibernate<EmpireEmpireContact, Long> implements EmpireEmpireContactDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse EmpireEmpireContact
	 */
	public EmpireEmpireContactDaoHibernate()
	{
		super(EmpireEmpireContact.class);
	}
}

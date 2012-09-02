package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.EmpireAllianceContactDao;
import com.syncnapsis.data.model.contacts.EmpireAllianceContact;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf EmpireAllianceContact
 * 
 * @author ultimate
 */
public class EmpireAllianceContactDaoHibernate extends GenericDaoHibernate<EmpireAllianceContact, Long> implements EmpireAllianceContactDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse EmpireAllianceContact
	 */
	public EmpireAllianceContactDaoHibernate()
	{
		super(EmpireAllianceContact.class);
	}
}

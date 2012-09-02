package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.AllianceAllianceContactDao;
import com.syncnapsis.data.model.contacts.AllianceAllianceContact;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContact
 * 
 * @author ultimate
 */
public class AllianceAllianceContactDaoHibernate extends GenericDaoHibernate<AllianceAllianceContact, Long> implements AllianceAllianceContactDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse AllianceAllianceContact
	 */
	public AllianceAllianceContactDaoHibernate()
	{
		super(AllianceAllianceContact.class);
	}
}

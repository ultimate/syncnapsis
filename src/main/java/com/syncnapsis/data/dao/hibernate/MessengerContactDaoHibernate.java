package com.syncnapsis.data.dao.hibernate;

import java.util.List;

import com.syncnapsis.data.dao.MessengerContactDao;
import com.syncnapsis.data.model.MessengerContact;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class MessengerContactDaoHibernate extends GenericDaoHibernate<MessengerContact, Long> implements MessengerContactDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse MessengerContact
	 */
	public MessengerContactDaoHibernate()
	{
		super(MessengerContact.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.MessengerContactDao#getByUser(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MessengerContact> getByUser(Long userId)
	{
		return createQuery("from MessengerContact mc where mc.user.id=?", userId).list();
	}
}

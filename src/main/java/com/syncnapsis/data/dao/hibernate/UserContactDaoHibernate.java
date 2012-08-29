package com.syncnapsis.data.dao.hibernate;

import java.util.List;

import com.syncnapsis.data.dao.UserContactDao;
import com.syncnapsis.data.model.UserContact;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class UserContactDaoHibernate extends GenericDaoHibernate<UserContact, Long> implements UserContactDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse UserContact
	 */
	public UserContactDaoHibernate()
	{
		super(UserContact.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UserContactDao#getByUser(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserContact> getByUser(Long userID)
	{
		return createQuery("from UserContact where user1.id = ? or user2.id = ?", userID, userID).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UserContactDao#getUserContact(java.lang.Long, java.lang.Long)
	 */
	@Override
	public UserContact getUserContact(Long userId1, Long userId2)
	{
		return (UserContact) createQuery("from UserContact where (user1.id = ? and user2.id = ?) or (user2.id = ? and user1.id = ?)" , userId1, userId2, userId1, userId2).uniqueResult();
	}
}

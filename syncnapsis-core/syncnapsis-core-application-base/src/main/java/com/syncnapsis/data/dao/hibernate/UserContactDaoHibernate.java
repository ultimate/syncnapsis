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

import java.util.List;

import org.hibernate.SessionFactory;

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
	@Deprecated
	public UserContactDaoHibernate()
	{
		super(UserContact.class);
	}

	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse UserContact and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public UserContactDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, UserContact.class);
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
		return (UserContact) createQuery("from UserContact where (user1.id = ? and user2.id = ?) or (user2.id = ? and user1.id = ?)", userId1,
				userId2, userId1, userId2).uniqueResult();
	}
}

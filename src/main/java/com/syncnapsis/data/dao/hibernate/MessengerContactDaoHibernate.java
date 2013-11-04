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

import com.syncnapsis.data.dao.MessengerContactDao;
import com.syncnapsis.data.model.MessengerContact;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf MessengerContact
 * 
 * @author ultimate
 */
public class MessengerContactDaoHibernate extends GenericDaoHibernate<MessengerContact, Long> implements MessengerContactDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse MessengerContact
	 */
	@Deprecated
	public MessengerContactDaoHibernate()
	{
		super(MessengerContact.class);
	}

	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse MessengerContact and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public MessengerContactDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, MessengerContact.class);
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

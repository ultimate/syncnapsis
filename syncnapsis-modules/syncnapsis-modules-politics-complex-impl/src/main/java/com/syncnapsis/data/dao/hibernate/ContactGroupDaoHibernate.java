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

import com.syncnapsis.data.dao.ContactGroupDao;
import com.syncnapsis.data.model.ContactGroup;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf ContactGroup
 * 
 * @author ultimate
 */
public class ContactGroupDaoHibernate extends GenericDaoHibernate<ContactGroup, Long> implements ContactGroupDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse ContactGroup
	 */
	@Deprecated
	public ContactGroupDaoHibernate()
	{
		super(ContactGroup.class);
	}

	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse ContactGroup and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public ContactGroupDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, ContactGroup.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.ContactGroupDao#getByAlliance(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContactGroup> getByAlliance(Long allianceId)
	{
		return createQuery("from ContactGroup where ownerAlliance.id = ?", allianceId).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.ContactGroupDao#getByEmpire(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContactGroup> getByEmpire(Long empireId)
	{
		return createQuery("from ContactGroup where ownerEmpire.id = ?", empireId).list();
	}
}

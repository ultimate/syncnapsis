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

import org.hibernate.SessionFactory;

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
	@Deprecated
	public AllianceAllianceContactDaoHibernate()
	{
		super(AllianceAllianceContact.class);
	}

	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse AllianceAllianceContact and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public AllianceAllianceContactDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, AllianceAllianceContact.class);
	}
}

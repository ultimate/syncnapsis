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
	@Deprecated
	public EmpireEmpireContactDaoHibernate()
	{
		super(EmpireEmpireContact.class);
	}

	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse EmpireEmpireContact and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public EmpireEmpireContactDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, EmpireEmpireContact.class);
	}
}

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

import com.syncnapsis.data.dao.ParameterDao;
import com.syncnapsis.data.model.Parameter;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class ParameterDaoHibernate extends GenericNameDaoHibernate<Parameter, Long> implements ParameterDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse Parameter und der Option idOverwrite = true
	 */
	@Deprecated
	public ParameterDaoHibernate()
	{
		super(Parameter.class, "name");
	}

	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse Parameter und der Option idOverwrite = true and the given
	 * SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public ParameterDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, Parameter.class, "name");
	}
}

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

import com.syncnapsis.data.dao.SolarSystemInfrastructureDao;
import com.syncnapsis.data.model.SolarSystemInfrastructure;

/**
 * Dao-Implementation for Hibernate for access to SolarSystemInfrastructure
 * 
 * @author ultimate
 */
public class SolarSystemInfrastructureDaoHibernate extends GenericDaoHibernate<SolarSystemInfrastructure, Long> implements
		SolarSystemInfrastructureDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class SolarSystemInfrastructure
	 */
	@Deprecated
	public SolarSystemInfrastructureDaoHibernate()
	{
		super(SolarSystemInfrastructure.class);
	}

	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class SolarSystemInfrastructure and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public SolarSystemInfrastructureDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, SolarSystemInfrastructure.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.SolarSystemInfrastructureDao#getByMatch(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolarSystemInfrastructure> getByMatch(long matchId)
	{
		return createQuery("from SolarSystemInfrastructure i where i.match.id=?", matchId).list();
	}
}

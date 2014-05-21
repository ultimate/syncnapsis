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

import com.syncnapsis.data.dao.SolarSystemDao;
import com.syncnapsis.data.model.SolarSystem;

/**
 * Dao-Implementation for Hibernate for access to SolarSystem
 * 
 * @author ultimate
 */
public class SolarSystemDaoHibernate extends GenericDaoHibernate<SolarSystem, Long> implements SolarSystemDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericNameDaoHibernate
	 * with the model-Class SolarSystem and the specified name-property
	 */
	@Deprecated
	public SolarSystemDaoHibernate()
	{
		super(SolarSystem.class);
	}

	/**
	 * Create a new DAO-Instance using the super-class GenericNameDaoHibernate
	 * with the model-Class SolarSystem and the specified name-property and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public SolarSystemDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, SolarSystem.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.SolarSystemDao#getByGalaxy(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolarSystem> getByGalaxy(long galaxyId)
	{
		return createQuery("from SolarSystem s where s.galaxy.id=?", galaxyId).list();
	}
}

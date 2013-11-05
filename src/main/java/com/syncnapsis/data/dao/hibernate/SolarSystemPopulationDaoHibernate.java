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

import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.SolarSystemPopulation;

/**
 * Dao-Implementation for Hibernate for access to SolarSystemPopulation
 * 
 * @author ultimate
 */
public class SolarSystemPopulationDaoHibernate extends GenericDaoHibernate<SolarSystemPopulation, Long> implements SolarSystemPopulationDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class SolarSystemPopulation
	 */
	@Deprecated
	public SolarSystemPopulationDaoHibernate()
	{
		super(SolarSystemPopulation.class);
	}

	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class SolarSystemPopulation and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public SolarSystemPopulationDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, SolarSystemPopulation.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.SolarSystemPopulationDao#getByParticipant(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolarSystemPopulation> getByParticipant(long participantId)
	{
		return createQuery("from SolarSystemPopulation p where p.participant.id=?", participantId).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.SolarSystemPopulationDao#getByMatch(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolarSystemPopulation> getByMatch(long matchId)
	{
		return createQuery("from SolarSystemPopulation p where p.infrastructure.match.id=?", matchId).list();
	}
}

package com.syncnapsis.data.dao.hibernate;

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
	public SolarSystemPopulationDaoHibernate()
	{
		super(SolarSystemPopulation.class);
	}
}

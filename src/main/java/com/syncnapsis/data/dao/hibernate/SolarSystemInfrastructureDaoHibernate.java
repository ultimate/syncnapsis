package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.SolarSystemInfrastructureDao;
import com.syncnapsis.data.model.SolarSystemInfrastructure;

/**
 * Dao-Implementation for Hibernate for access to SolarSystemInfrastructure
 * 
 * @author ultimate
 */
public class SolarSystemInfrastructureDaoHibernate extends GenericDaoHibernate<SolarSystemInfrastructure, Long> implements SolarSystemInfrastructureDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class SolarSystemInfrastructure
	 */
	public SolarSystemInfrastructureDaoHibernate()
	{
		super(SolarSystemInfrastructure.class);
	}
}

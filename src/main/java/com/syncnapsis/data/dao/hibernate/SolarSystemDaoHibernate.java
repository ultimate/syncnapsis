package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.SolarSystemDao;
import com.syncnapsis.data.model.SolarSystem;

/**
 * Dao-Implementation for Hibernate for access to SolarSystem
 * 
 * @author ultimate
 */
public class SolarSystemDaoHibernate extends GenericNameDaoHibernate<SolarSystem, Long> implements SolarSystemDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericNameDaoHibernate
	 * with the model-Class SolarSystem and the specified name-property
	 */
	public SolarSystemDaoHibernate()
	{
		super(SolarSystem.class, "name");
	}
}

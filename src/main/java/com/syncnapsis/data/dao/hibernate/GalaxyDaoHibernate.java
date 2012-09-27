package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.GalaxyDao;
import com.syncnapsis.data.model.Galaxy;

/**
 * Dao-Implementation for Hibernate for access to Galaxy
 * 
 * @author ultimate
 */
public class GalaxyDaoHibernate extends GenericNameDaoHibernate<Galaxy, Long> implements GalaxyDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericNameDaoHibernate
	 * with the model-Class Galaxy and the specified name-property
	 */
	public GalaxyDaoHibernate()
	{
		super(Galaxy.class, "name");
	}
}

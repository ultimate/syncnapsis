package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.SolarSystemInfrastructureDao;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;

/**
 * Manager-Implementation for access to SolarSystemInfrastructure.
 * 
 * @author ultimate
 */
public class SolarSystemInfrastructureManagerImpl extends GenericManagerImpl<SolarSystemInfrastructure, Long> implements SolarSystemInfrastructureManager
{
	/**
	 * SolarSystemInfrastructureDao for database access
	 */
	protected SolarSystemInfrastructureDao			solarSystemInfrastructureDao;

	/**
	 * Standard Constructor
	 * 
	 * @param solarSystemInfrastructureDao - SolarSystemInfrastructureDao for database access
	 */
	public SolarSystemInfrastructureManagerImpl(SolarSystemInfrastructureDao solarSystemInfrastructureDao)
	{
		super(solarSystemInfrastructureDao);
		this.solarSystemInfrastructureDao = solarSystemInfrastructureDao;
	}
}

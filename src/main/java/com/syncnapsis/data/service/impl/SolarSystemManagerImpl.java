package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.SolarSystemDao;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.service.SolarSystemManager;

/**
 * Manager-Implementation for access to SolarSystem.
 * 
 * @author ultimate
 */
public class SolarSystemManagerImpl extends GenericNameManagerImpl<SolarSystem, Long> implements SolarSystemManager
{
	/**
	 * SolarSystemDao for database access
	 */
	protected SolarSystemDao			solarSystemDao;

	/**
	 * Standard Constructor
	 * 
	 * @param solarSystemDao - SolarSystemDao for database access
	 */
	public SolarSystemManagerImpl(SolarSystemDao solarSystemDao)
	{
		super(solarSystemDao);
		this.solarSystemDao = solarSystemDao;
	}
}

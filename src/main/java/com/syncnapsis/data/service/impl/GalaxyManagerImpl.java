package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.GalaxyDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.service.GalaxyManager;

/**
 * Manager-Implementation for access to Galaxy.
 * 
 * @author ultimate
 */
public class GalaxyManagerImpl extends GenericNameManagerImpl<Galaxy, Long> implements GalaxyManager
{
	/**
	 * GalaxyDao for database access
	 */
	protected GalaxyDao			galaxyDao;

	/**
	 * Standard Constructor
	 * 
	 * @param galaxyDao - GalaxyDao for database access
	 */
	public GalaxyManagerImpl(GalaxyDao galaxyDao)
	{
		super(galaxyDao);
		this.galaxyDao = galaxyDao;
	}
}

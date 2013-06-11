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
package com.syncnapsis.data.service.impl;

import java.util.List;

import com.syncnapsis.data.dao.SolarSystemInfrastructureDao;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;

/**
 * Manager-Implementation for access to SolarSystemInfrastructure.
 * 
 * @author ultimate
 */
public class SolarSystemInfrastructureManagerImpl extends GenericManagerImpl<SolarSystemInfrastructure, Long> implements
		SolarSystemInfrastructureManager
{
	/**
	 * SolarSystemInfrastructureDao for database access
	 */
	protected SolarSystemInfrastructureDao	solarSystemInfrastructureDao;

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

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.SolarSystemInfrastructureManager#getByMatch(long)
	 */
	@Override
	public List<SolarSystemInfrastructure> getByMatch(long matchId)
	{
		return solarSystemInfrastructureDao.getByMatch(matchId);
	}
}

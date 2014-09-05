/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.dao.GalaxyDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.data.service.SolarSystemManager;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.utils.HibernateUtil;
import com.syncnapsis.utils.data.ExtendedRandom;

/**
 * Manager-Implementation for access to Galaxy.
 * 
 * @author ultimate
 */
public class GalaxyManagerImpl extends GenericNameManagerImpl<Galaxy, Long> implements GalaxyManager, InitializingBean
{
	/**
	 * GalaxyDao for database access
	 */
	protected GalaxyDao				galaxyDao;

	/**
	 * The SolarSystemManager
	 */
	protected SolarSystemManager	solarSystemManager;

	protected Calculator			calculator;

	/**
	 * The SecurityManager (BaseGameManager)
	 */
	protected BaseGameManager		securityManager;

	/**
	 * Standard Constructor
	 * 
	 * @param galaxyDao - GalaxyDao for database access
	 * @param solarSystemManager - the SolarSystemManager
	 */
	public GalaxyManagerImpl(GalaxyDao galaxyDao, SolarSystemManager solarSystemManager)
	{
		super(galaxyDao);
		this.galaxyDao = galaxyDao;
		this.solarSystemManager = solarSystemManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(calculator, "calculator must not be null!");
		Assert.notNull(securityManager, "securityManager must not be null!");
	}

	/**
	 * The universe conquenst {@link Calculator}
	 * 
	 * @return calculator
	 */
	public Calculator getCalculator()
	{
		return calculator;
	}

	/**
	 * The universe conquenst {@link Calculator}
	 * 
	 * @param calculator - the Calculator
	 */
	public void setCalculator(Calculator calculator)
	{
		this.calculator = calculator;
	}

	/**
	 * The SecurityManager (BaseGameManager)
	 * 
	 * @return securityManager
	 */
	public BaseGameManager getSecurityManager()
	{
		return securityManager;
	}

	/**
	 * The SecurityManager (BaseGameManager)
	 * 
	 * @param securityManager - the SecurityManager
	 */
	public void setSecurityManager(BaseGameManager securityManager)
	{
		this.securityManager = securityManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.GalaxyManager#getByCreator(long)
	 */
	@Override
	public List<Galaxy> getByCreator(long playerId)
	{
		return galaxyDao.getByCreator(playerId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.GalaxyManager#create(java.lang.String, java.util.List,
	 * java.util.List, java.lang.Long)
	 */
	@Override
	public Galaxy create(String name, List<Vector.Integer> systemCoords, List<String> systemNames, Long seed)
	{
		return create(name, systemCoords, systemNames, seed, calculator.calculateSize(systemCoords));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.GalaxyManager#create(java.lang.Object)
	 */
	@Override
	public Galaxy create(Object configuration)
	{
		// TODO implement createGalaxy
		throw new UnsupportedOperationException("not implemented yet");
	}

	/**
	 * Create a Galaxy as described by {@link GalaxyManager#create(String, List, List, Long)} but
	 * taking a precalculated size for the Galaxy. This way sizes can be "beautified" to categories
	 * (e. g. 945 will be rounded to 1000) since we can guarantee precalculated sizes will be
	 * maintained and sizes forwarded to the sector generation algorithm will be maintained.
	 * 
	 * @param name - the name for the Galaxy
	 * @param systemCoords - the list containing all the systemCoords for the SolarSystems
	 * @param systemNames - the list containing possible systemNames for the SolarSystems (optional)
	 * @param seed - the seed used for the random generator. If null a random seed will be used
	 * @param size - the size of the Galaxy
	 * @return the newly created Galaxy
	 */
	protected Galaxy create(String name, List<Vector.Integer> systemCoords, List<String> systemNames, Long seed, Vector.Integer size)
	{
		Assert.notNull(name, "name must not be null!");
		Assert.notNull(size, "size must not be null!");

		long now = securityManager.getTimeProvider().get();
		if(seed == null)
			seed = now;

		Galaxy galaxy = new Galaxy();
		galaxy.setActivated(true);
		galaxy.setCreationDate(new Date(now));
		galaxy.setCreator(securityManager.getPlayerProvider().get());
		galaxy.setName(name);
		galaxy.setSeed(seed);
		galaxy.setSize(size);
		galaxy.setMaxGap(calculator.calculateMaxGap(systemCoords));

		galaxy = save(galaxy);

		ExtendedRandom random = new ExtendedRandom(seed);

		for(int s = 0; s < systemCoords.size(); s++)
		{
			solarSystemManager.create(galaxy, systemCoords, systemNames, s, random);
		}

		// flush the session and persist the systems
		HibernateUtil.currentSession().flush();

		return get(galaxy.getId());
	}
}

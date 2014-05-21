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

import org.springframework.util.Assert;

import com.syncnapsis.data.dao.SolarSystemDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.SolarSystemManager;
import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.utils.data.Generator;
import com.syncnapsis.utils.math.Roman;

/**
 * Manager-Implementation for access to SolarSystem.
 * 
 * @author ultimate
 */
public class SolarSystemManagerImpl extends GenericManagerImpl<SolarSystem, Long> implements SolarSystemManager
{
	/**
	 * SolarSystemDao for database access
	 */
	protected SolarSystemDao	solarSystemDao;

	/**
	 * A generator for system names
	 */
	protected Generator<String>	nameGenerator;

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

	/**
	 * A generator for system names
	 * 
	 * @return nameGenerator
	 */
	public Generator<String> getNameGenerator()
	{
		return nameGenerator;
	}

	/**
	 * A generator for system names
	 * 
	 * @param nameGenerator
	 */
	public void setNameGenerator(Generator<String> nameGenerator)
	{
		this.nameGenerator = nameGenerator;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.SolarSystemManager#getByGalaxy(long)
	 */
	@Override
	public List<SolarSystem> getByGalaxy(long galaxyId)
	{
		return solarSystemDao.getByGalaxy(galaxyId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.SolarSystemManager#create(com.syncnapsis.data.model.Galaxy,
	 * com.syncnapsis.data.model.help.Vector.Integer, java.lang.String)
	 */
	@Override
	public SolarSystem create(Galaxy galaxy, Vector.Integer coords, String name)
	{
		SolarSystem solarSystem = new SolarSystem();
		solarSystem.setActivated(true);
		solarSystem.setCoords(coords);
		solarSystem.setGalaxy(galaxy);
		solarSystem.setName(name);
		return save(solarSystem);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.SolarSystemManager#create(com.syncnapsis.data.model.Galaxy,
	 * java.util.List, java.util.List, int, com.syncnapsis.utils.data.ExtendedRandom)
	 */
	@Override
	public SolarSystem create(Galaxy galaxy, List<Vector.Integer> systemCoords, List<String> systemNames, int index, ExtendedRandom random)
	{
		Assert.notNull(random, "random must not be null!");
		
		String name = getName(systemCoords, systemNames, index, random);

		return create(galaxy, systemCoords.get(index), name);
	}

	/**
	 * Get the name for a SolarSystem from the given name list depending on the total list of
	 * coordinates and the index of the SolarSystem within this list in the following way:
	 * <ul>
	 * <li><code>systemNames == null || systemNames.size() == 0</code> : random systemNames will be
	 * generated</li>
	 * <li><code>systemNames.size() >= systemCoords.size()</code> : name for systemCoords[i] =
	 * systemNames[i]</li>
	 * <li><code>systemNames.size() < systemCoords.size()</code> : name for systemCoords[i] =
	 * systemNames[i%systemNames.size()] + counter if necessary (e.g. "example I", "example II",
	 * ...)</li>
	 * </ul>
	 * 
	 * @param systemCoords - the list containing all the systemCoords for the SolarSystems
	 * @param systemNames - the list containing possible systemNames for the SolarSystems (optional)
	 * @param index - the index of the SolarSystem to generate within the systemCoords
	 * @param random - the random number generator used to generate more parameters if required
	 * @return the name or null if there neither a list of names nor a name generator
	 */
	protected String getName(List<Vector.Integer> systemCoords, List<String> systemNames, int index, ExtendedRandom random)
	{
		String name;
		if(systemNames == null || systemNames.size() == 0)
		{
			if(nameGenerator != null)
			{
				Assert.notNull(random, "random must not be null when using the name generator");
				name = nameGenerator.generate(random, (Object[]) null);
			}
			else
			{
				name = null;
			}
		}
		else
		{
			int i = index % systemNames.size();
			int j = index / systemNames.size();
			int k = systemCoords.size() % systemNames.size();
			int l = systemCoords.size() / systemNames.size();
			name = systemNames.get(i);
			if((l > 0 && (j > 0 || i < k)) || l > 1)
			{
				name += " " + Roman.toString(j+1);
			}
		}
		return name;
	}
}

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

import java.util.Date;
import java.util.List;

import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.utils.data.ExtendedRandom;

/**
 * Manager-Implementation for access to SolarSystemPopulation.
 * 
 * @author ultimate
 */
public class SolarSystemPopulationManagerImpl extends GenericManagerImpl<SolarSystemPopulation, Long> implements SolarSystemPopulationManager
{
	/**
	 * SolarSystemPopulationDao for database access
	 */
	protected SolarSystemPopulationDao	solarSystemPopulationDao;

	/**
	 * Standard Constructor
	 * 
	 * @param solarSystemPopulationDao - SolarSystemPopulationDao for database access
	 */
	public SolarSystemPopulationManagerImpl(SolarSystemPopulationDao solarSystemPopulationDao)
	{
		super(solarSystemPopulationDao);
		this.solarSystemPopulationDao = solarSystemPopulationDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.SolarSystemPopulationManager#getByParticipant(long)
	 */
	@Override
	public List<SolarSystemPopulation> getByParticipant(long participantId)
	{
		return solarSystemPopulationDao.getByParticipant(participantId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.SolarSystemPopulationManager#getByMatch(long)
	 */
	@Override
	public List<SolarSystemPopulation> getByMatch(long matchId)
	{
		return solarSystemPopulationDao.getByMatch(matchId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#selectStartSystem(com.syncnapsis
	 * .data.model.SolarSystemInfrastructure, int)
	 */
	@Override
	public SolarSystemPopulation selectStartSystem(SolarSystemInfrastructure infrastructure, int population)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#randomSelectStartSystems(com.syncnapsis
	 * .data.model.Participant, com.syncnapsis.utils.data.ExtendedRandom)
	 */
	@Override
	public List<SolarSystemPopulation> randomSelectStartSystems(Participant participant, ExtendedRandom random)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#spinoff(com.syncnapsis.data.model
	 * .SolarSystemPopulation, com.syncnapsis.data.model.SolarSystemInfrastructure, java.util.Date,
	 * int, com.syncnapsis.enums.EnumPopulationPriority,
	 * com.syncnapsis.enums.EnumPopulationPriority)
	 */
	@Override
	public SolarSystemPopulation spinoff(SolarSystemPopulation origin, SolarSystemInfrastructure targetInfrastructure, Date targetArrivalDate,
			int population, EnumPopulationPriority attackPriority, EnumPopulationPriority buildPriority)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#resettle(com.syncnapsis.data.model
	 * .SolarSystemPopulation, com.syncnapsis.data.model.SolarSystemInfrastructure, java.util.Date)
	 */
	@Override
	public SolarSystemPopulation resettle(SolarSystemPopulation origin, SolarSystemInfrastructure targetInfrastructure, Date targetArrivalDate)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#destroy(com.syncnapsis.data.model
	 * .SolarSystemPopulation, com.syncnapsis.enums.EnumDestructionType, java.util.Date)
	 */
	@Override
	public SolarSystemPopulation destroy(SolarSystemPopulation population, EnumDestructionType destructionType, Date destructionDate)
	{
		population.setActivated(false);
		population.setDestructionDate(destructionDate);
		population.setDestructionType(destructionType);
		
		return save(population);
	}
}

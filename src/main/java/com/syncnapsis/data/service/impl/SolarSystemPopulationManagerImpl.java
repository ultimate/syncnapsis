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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.security.accesscontrol.SolarSystemPopulationAccessController;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.utils.MathUtil;
import com.syncnapsis.utils.data.ExtendedRandom;

/**
 * Manager-Implementation for access to SolarSystemPopulation.
 * 
 * @author ultimate
 */
public class SolarSystemPopulationManagerImpl extends GenericManagerImpl<SolarSystemPopulation, Long> implements SolarSystemPopulationManager,
		InitializingBean
{
	/**
	 * SolarSystemPopulationDao for database access
	 */
	protected SolarSystemPopulationDao	solarSystemPopulationDao;

	/**
	 * The ParameterManager
	 */
	protected ParameterManager			parameterManager;

	/**
	 * The universe conquenst {@link Calculator}
	 */
	protected Calculator				calculator;

	/**
	 * The SecurityManager
	 */
	protected BaseGameManager			securityManager;

	/**
	 * Standard Constructor
	 * 
	 * @param solarSystemPopulationDao - SolarSystemPopulationDao for database access
	 * @param parameterManager - the ParameterManager
	 */
	public SolarSystemPopulationManagerImpl(SolarSystemPopulationDao solarSystemPopulationDao, ParameterManager parameterManager)
	{
		super(solarSystemPopulationDao);
		this.solarSystemPopulationDao = solarSystemPopulationDao;
		this.parameterManager = parameterManager;
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

	/**
	 * Shortcut for
	 * <code>securityManager.getAccessController(Match.class).isAccessibe(population, operation, securityManager.getPlayerProvider().get())</code>
	 * 
	 * @see SolarSystemPopulationAccessController
	 * @return true or false
	 */
	protected boolean isAccessible(SolarSystemPopulation population, int operation)
	{
		return securityManager.getAccessController(SolarSystemPopulation.class).isAccessible(population, operation,
				securityManager.getPlayerProvider().get());
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
	 * .data.model.SolarSystemInfrastructure, long)
	 */
	@Override
	public SolarSystemPopulation selectStartSystem(SolarSystemInfrastructure infrastructure, long population)
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
	 * .SolarSystemPopulation, com.syncnapsis.data.model.SolarSystemInfrastructure, int, long,
	 * com.syncnapsis.enums.EnumPopulationPriority, com.syncnapsis.enums.EnumPopulationPriority)
	 */
	@Override
	public SolarSystemPopulation spinoff(SolarSystemPopulation origin, SolarSystemInfrastructure targetInfrastructure, int travelSpeed,
			long population, EnumPopulationPriority attackPriority, EnumPopulationPriority buildPriority)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#resettle(com.syncnapsis.data.model
	 * .SolarSystemPopulation, com.syncnapsis.data.model.SolarSystemInfrastructure, int, boolean)
	 */
	@Override
	public SolarSystemPopulation resettle(SolarSystemPopulation origin, SolarSystemInfrastructure targetInfrastructure, int travelSpeed,
			boolean exodus)
	{
		Date now = new Date(securityManager.getTimeProvider().get());

		if(exodus)
		{
			// check if exodus is allowed (only if home population)
			SolarSystemPopulation homePopulation = getHomePopulation(origin.getInfrastructure());
			if(homePopulation != null && !origin.getId().equals(homePopulation.getId()))
				throw new IllegalArgumentException("exodus only allowed for home population!");
		}

		double maxDist = calculator.calculateMaxTravelDistance(origin, origin.getPopulation(), exodus);
		double dist = MathUtil.distance(origin.getInfrastructure().getSolarSystem().getCoords(), targetInfrastructure.getSolarSystem().getCoords());

		if(dist > maxDist)
			throw new IllegalArgumentException("travel exceeds max distance!");

		origin.setDestructionDate(now);
		origin.setDestructionType(EnumDestructionType.givenUp);

		save(origin);

		SolarSystemPopulation resettled = new SolarSystemPopulation();
		resettled.setActivated(true);
		resettled.setAttackPriority(origin.getAttackPriority());
		resettled.setBuildPriority(origin.getBuildPriority());
		resettled.setDestructionDate(null);
		resettled.setDestructionType(null);
		resettled.setInfrastructure(targetInfrastructure);
		resettled.setOrigin(origin);
		resettled.setOriginationDate(now);
		resettled.setParticipant(origin.getParticipant());
		resettled.setPopulation(origin.getPopulation());
		resettled.setStoredInfrastructure(exodus ? origin.getInfrastructure().getInfrastructure() / 2 : 0);
		resettled.setTravelProgress(0.0);
		resettled.setTravelProgressDate(now);
		resettled.setTravelSpeed(travelSpeed);

		return updateTravelSpeed(resettled, travelSpeed); // will perform save
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

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#merge(com.syncnapsis.data.model.
	 * SolarSystemInfrastructure)
	 */
	@Override
	public List<SolarSystemPopulation> merge(SolarSystemInfrastructure infrastructure)
	{
		List<SolarSystemPopulation> populations = infrastructure.getPopulations();
		
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#updateTravelSpeed(com.syncnapsis
	 * .data.model.SolarSystemPopulation, int)
	 */
	@Override
	public SolarSystemPopulation updateTravelSpeed(SolarSystemPopulation population, int travelSpeed)
	{
		Date now = new Date(securityManager.getTimeProvider().get());
		return updateTravelSpeed(population, travelSpeed, now);
	}

	/**
	 * Internal method being called within
	 * {@link SolarSystemPopulationManagerImpl#updateTravelSpeed(SolarSystemPopulation, int)} and
	 * {@link SolarSystemPopulationManagerImpl#resettle(SolarSystemPopulation, SolarSystemInfrastructure, int, boolean)}
	 * 
	 * @param population - the population to update
	 * @param travelSpeed - the new speed to set
	 * @param speedChangeDate - the date the speed was changed
	 * @return the updated population entity
	 */
	protected SolarSystemPopulation updateTravelSpeed(SolarSystemPopulation population, int travelSpeed, Date speedChangeDate)
	{
		double newProgress;
		if(speedChangeDate.after(population.getColonizationDate()))
		{
			newProgress = 1.0;
			speedChangeDate = population.getColonizationDate();
			travelSpeed = 0;
		}
		else
		{
			long travelTime = calculator.calculateTravelTime(population.getOrigin().getInfrastructure(), population.getInfrastructure(), travelSpeed);
			long timeTraveled = speedChangeDate.getTime() - population.getTravelProgressDate().getTime();
			double percentTraveled = timeTraveled / (double) travelTime;
			newProgress = population.getTravelProgress() + percentTraveled;

			// set new colonizationDate
			Date colonizationDate = new Date((long) (speedChangeDate.getTime() + (1 - newProgress) * travelTime));
			population.setColonizationDate(colonizationDate);
		}

		population.setTravelProgress(newProgress);
		population.setTravelProgressDate(speedChangeDate);
		population.setTravelSpeed(travelSpeed);

		return save(population);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#getHomePopulation(com.syncnapsis
	 * .data.model.SolarSystemInfrastructure)
	 */
	@Override
	public SolarSystemPopulation getHomePopulation(SolarSystemInfrastructure infrastructure)
	{
		Date now = new Date(securityManager.getTimeProvider().get());

		SolarSystemPopulation homePop = null;
		for(SolarSystemPopulation p : infrastructure.getPopulations())
		{
			if(!p.isActivated())
				continue;
			if(p.getColonizationDate().after(now))
				continue;
			if(homePop == null || p.getColonizationDate().before(homePop.getColonizationDate()))
				homePop = p;
		}

		return homePop;
	}
}

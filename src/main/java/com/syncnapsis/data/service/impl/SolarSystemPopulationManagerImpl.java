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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.security.accesscontrol.SolarSystemPopulationAccessController;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.utils.MathUtil;
import com.syncnapsis.utils.StringUtil;
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
	protected SolarSystemPopulationDao			solarSystemPopulationDao;

	/**
	 * The SolarSystemInfrastructureManager
	 */
	protected SolarSystemInfrastructureManager	solarSystemInfrastructureManager;

	/**
	 * The universe conquenst {@link Calculator}
	 */
	protected Calculator						calculator;

	/**
	 * The SecurityManager
	 */
	protected BaseGameManager					securityManager;

	/**
	 * Standard Constructor
	 * 
	 * @param solarSystemPopulationDao - SolarSystemPopulationDao for database access
	 * @param solarSystemInfrastructureManager - the SolarSystemInfrastructureManager
	 */
	public SolarSystemPopulationManagerImpl(SolarSystemPopulationDao solarSystemPopulationDao,
			SolarSystemInfrastructureManager solarSystemInfrastructureManager)
	{
		super(solarSystemPopulationDao);
		this.solarSystemPopulationDao = solarSystemPopulationDao;
		this.solarSystemInfrastructureManager = solarSystemInfrastructureManager;
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
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#spinoff(com.syncnapsis.data.model
	 * .SolarSystemPopulation, com.syncnapsis.data.model.SolarSystemInfrastructure, int, long,
	 * com.syncnapsis.enums.EnumPopulationPriority, com.syncnapsis.enums.EnumPopulationPriority)
	 */
	@Override
	public SolarSystemPopulation spinoff(SolarSystemPopulation origin, SolarSystemInfrastructure targetInfrastructure, int travelSpeed,
			long population, EnumPopulationPriority attackPriority, EnumPopulationPriority buildPriority)
	{
		return spinoff(origin, targetInfrastructure, travelSpeed, population, attackPriority, buildPriority, false);
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
		return spinoff(origin, targetInfrastructure, travelSpeed, origin.getPopulation(), null, null, exodus);
	}

	/**
	 * Internal method being called within
	 * {@link SolarSystemPopulationManagerImpl#spinoff(SolarSystemPopulation, SolarSystemInfrastructure, int, long, EnumPopulationPriority, EnumPopulationPriority)}
	 * and
	 * {@link SolarSystemPopulationManagerImpl#resettle(SolarSystemPopulation, SolarSystemInfrastructure, int, boolean)}
	 * 
	 * 
	 * @param origin - the origin population
	 * @param targetInfrastructure - the infrastruture to travel to
	 * @param travelSpeed - the speed to travel with (in percent)
	 * @param population - the population for the spinoff
	 * @param attackPriority - the attack priority (if null the origins priority will be used)
	 * @param buildPriority - the build priority (if null the origins priority will be used)
	 * @param exodus - perform exodus on the origin system
	 * @return the newly created SolarSystemPopulation entity
	 */
	protected SolarSystemPopulation spinoff(SolarSystemPopulation origin, SolarSystemInfrastructure targetInfrastructure, int travelSpeed,
			long population, EnumPopulationPriority attackPriority, EnumPopulationPriority buildPriority, boolean exodus)
	{
		Date now = new Date(securityManager.getTimeProvider().get());

		if(exodus)
		{
			if(population != origin.getPopulation())
				throw new IllegalArgumentException("exodus requires all population to be moved away!");

			// check if exodus is allowed (only if home population)
			SolarSystemPopulation homePopulation = getHomePopulation(origin.getInfrastructure(), now);
			if(!origin.getId().equals(homePopulation.getId()))
				throw new IllegalArgumentException("exodus only allowed for home population!");
		}

		double maxDist = calculator.calculateMaxTravelDistance(origin, origin.getPopulation(), exodus);
		double dist = MathUtil.distance(origin.getInfrastructure().getSolarSystem().getCoords(), targetInfrastructure.getSolarSystem().getCoords());

		if(dist > maxDist)
			throw new IllegalArgumentException("travel exceeds max distance!");

		SolarSystemPopulation spinoff = new SolarSystemPopulation();
		spinoff.setActivated(true);
		spinoff.setAttackPriority(attackPriority != null ? attackPriority : origin.getAttackPriority());
		spinoff.setBuildPriority(buildPriority != null ? buildPriority : origin.getBuildPriority());
		spinoff.setDestructionDate(null);
		spinoff.setDestructionType(null);
		spinoff.setInfrastructure(targetInfrastructure);
		spinoff.setLastUpdateDate(now);
		spinoff.setOrigin(origin);
		spinoff.setOriginationDate(now);
		spinoff.setParticipant(origin.getParticipant());
		spinoff.setPopulation(population);
		spinoff.setTravelProgress(0.0);
		spinoff.setTravelProgressDate(now);
		spinoff.setTravelSpeed(travelSpeed);

		if(exodus)
		{
			spinoff.setStoredInfrastructure(origin.getInfrastructure().getInfrastructure() / 2);
			origin.getInfrastructure().setInfrastructure(0);
			solarSystemInfrastructureManager.save(origin.getInfrastructure());
		}

		origin.setLastUpdateDate(now);
		origin.setPopulation(origin.getPopulation() - population);
		logger.debug("origin to be saved = " + StringUtil.toString(origin, 0));
		if(origin.getPopulation() <= 0)
			destroy(origin, EnumDestructionType.givenUp, now);
		save(origin);

		return updateTravelSpeed(spinoff, travelSpeed, now); // will perform save
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
		population.setLastUpdateDate(destructionDate);

		return population;
		// return save(population);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#merge(com.syncnapsis.data.model.
	 * SolarSystemInfrastructure, java.util.Date)
	 */
	@Override
	public List<SolarSystemPopulation> merge(SolarSystemInfrastructure infrastructure, Date time)
	{
		List<SolarSystemPopulation> populations = new ArrayList<SolarSystemPopulation>(infrastructure.getPopulations().size());
		for(SolarSystemPopulation p : infrastructure.getPopulations())
		{
			if(!p.isPresent(time))
				continue;
			populations.add(p);
		}

		// sort by colonization date
		Collections.sort(populations, SolarSystemPopulation.BY_COLONIZATIONDATE);

		// traverse list backwards
		// for each traversed entry look for the oldest population for the same participant and
		// merge the entry into that population
		SolarSystemPopulation newerPop, olderPop;
		for(int i = populations.size() - 1; i >= 0; i--)
		{
			newerPop = populations.get(i);
			olderPop = null;

			for(int j = 0; j < i; j++)
			{
				if(populations.get(j).getParticipant().getId().equals(newerPop.getParticipant().getId()))
				{
					olderPop = populations.get(j);
					break;
				}
			}

			if(newerPop.getStoredInfrastructure() != 0)
			{
				infrastructure.setInfrastructure(Math.max(infrastructure.getInfrastructure(), newerPop.getStoredInfrastructure()));
				newerPop.setStoredInfrastructure(0);
			}

			if(olderPop != null)
			{
				// update the population value
				olderPop.setPopulation(olderPop.getPopulation() + newerPop.getPopulation());
				olderPop.setModified(true);

				// remove the newer population
				newerPop.setPopulation(0);
				newerPop.setModified(true);
				// destroy (no save)
				destroy(newerPop, EnumDestructionType.merged, newerPop.getColonizationDate());
			}
			// else
			// {
			// newerPop.setLastUpdateDate(now);
			// }

			// // save newerPop only, olderPop will be saved later during iteration
			// // save even if no changes have been made in this iteration, since changes may have
			// // been made before.
			// save(newerPop);
		}

		// infrastructure has not been changed
		// infrastructure.setLastUpdateDate(now);
		// infrastructure = solarSystemInfrastructureManager.save(infrastructure);

		return infrastructure.getPopulations();
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
		if(population.getColonizationDate() != null && !speedChangeDate.before(population.getColonizationDate()))
		{
			newProgress = 1.0;
			speedChangeDate = population.getColonizationDate();
			travelSpeed = 0;
		}
		else
		{
			long travelTime = calculator.calculateTravelTime(population.getOrigin().getInfrastructure(), population.getInfrastructure(),
					population.getTravelSpeed());
			long timeTraveled = speedChangeDate.getTime() - population.getTravelProgressDate().getTime();
			double percentTraveled = timeTraveled / (double) travelTime;
			newProgress = population.getTravelProgress() + percentTraveled;

			// set new colonizationDate
			Date colonizationDate = new Date((long) (speedChangeDate.getTime() + (1 - newProgress) * travelTime));
			population.setColonizationDate(colonizationDate);
		}

		population.setLastUpdateDate(speedChangeDate);
		population.setTravelProgress(newProgress);
		population.setTravelProgressDate(speedChangeDate);
		population.setTravelSpeed(travelSpeed);

		logger.debug("saving          =" + StringUtil.toString(population, 0));
		return save(population);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#getHomePopulation(com.syncnapsis
	 * .data.model.SolarSystemInfrastructure, java.util.Date)
	 */
	@Override
	public SolarSystemPopulation getHomePopulation(SolarSystemInfrastructure infrastructure, Date time)
	{
		SolarSystemPopulation homePop = null;
		for(SolarSystemPopulation p : infrastructure.getPopulations())
		{
			if(!p.isPresent(time))
				continue;
			if(homePop == null || p.getColonizationDate().before(homePop.getColonizationDate()))
				homePop = p;
		}

		return homePop;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#selectStartSystem(com.syncnapsis
	 * .data.model.Participant, com.syncnapsis.data.model.SolarSystemInfrastructure, long)
	 */
	@Override
	public SolarSystemPopulation selectStartSystem(Participant participant, SolarSystemInfrastructure infrastructure, long population)
	{
		SolarSystemPopulation pop = null;
		for(SolarSystemPopulation p : infrastructure.getPopulations())
		{
			if(p.getParticipant().getId().equals(participant.getId()))
			{
				pop = p;
				break;
			}
		}

		if(population == 0)
		{
			if(pop != null)
				solarSystemPopulationDao.delete(pop);
			return null;
		}

		if(pop == null)
		{
			if(population == 0)
				return null;
			pop = new SolarSystemPopulation();
			pop.setActivated(true);
			pop.setAttackPriority(EnumPopulationPriority.balanced);
			pop.setBuildPriority(EnumPopulationPriority.balanced);
			pop.setColonizationDate(null);
			pop.setDestructionDate(null);
			pop.setDestructionType(null);
			pop.setInfrastructure(infrastructure);
			pop.setLastUpdateDate(null);
			pop.setOrigin(null);
			pop.setOriginationDate(null);
			pop.setParticipant(participant);
			pop.setStoredInfrastructure(0);
			pop.setTravelProgress(0.0);
			pop.setTravelProgressDate(null);
			pop.setTravelSpeed(0);
		}
		pop.setPopulation(population);
		return save(pop);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.SolarSystemPopulationManager#simulate(com.syncnapsis.data.model
	 * .SolarSystemInfrastructure, com.syncnapsis.utils.data.ExtendedRandom)
	 */
	@Override
	public List<SolarSystemPopulation> simulate(SolarSystemInfrastructure infrastructure, ExtendedRandom random)
	{
		// get the current time
		Date now = new Date(securityManager.getTimeProvider().get());
		// merge populations before calculating deltas
		merge(infrastructure, now);
		// determine the home population and set it temporarily for the infrastructure
		infrastructure.setHomePopulation(getHomePopulation(infrastructure, now));
		// calculate the deltas
		List<SolarSystemPopulation> populationsPresent = calculator.calculateDeltas(infrastructure, random, now);
		// save updated populations
		for(SolarSystemPopulation pop : populationsPresent)
		{
			if(pop.isModified())
			{
				if(pop.getPopulation() == 0)
					destroy(pop, EnumDestructionType.destroyed, now);
				pop = save(pop);
				pop.setDelta(0.0);
				pop.setModified(false);
			}
		}
		// save infrastructure
		infrastructure = solarSystemInfrastructureManager.save(infrastructure);
		infrastructure.setDelta(0.0);
		infrastructure.setModified(false);

		// return populations
		return infrastructure.getPopulations();
	}
}

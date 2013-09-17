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

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.service.ParameterManager;
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
	 * The ParameterManager
	 */
	protected ParameterManager					parameterManager;

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
	 * @param parameterManager - the ParameterManager
	 */
	public SolarSystemPopulationManagerImpl(SolarSystemPopulationDao solarSystemPopulationDao,
			SolarSystemInfrastructureManager solarSystemInfrastructureManager, ParameterManager parameterManager)
	{
		super(solarSystemPopulationDao);
		this.solarSystemPopulationDao = solarSystemPopulationDao;
		this.solarSystemInfrastructureManager = solarSystemInfrastructureManager;
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
			SolarSystemPopulation homePopulation = getHomePopulation(origin.getInfrastructure());
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
	 * SolarSystemInfrastructure)
	 */
	@Override
	public List<SolarSystemPopulation> merge(SolarSystemInfrastructure infrastructure)
	{
		Date now = new Date(securityManager.getTimeProvider().get());

		List<SolarSystemPopulation> populations = new ArrayList<SolarSystemPopulation>(infrastructure.getPopulations().size());
		for(SolarSystemPopulation p : infrastructure.getPopulations())
		{
			if(p.isActivated() && p.getColonizationDate().before(now))
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

				// remove the newer population
				newerPop.setPopulation(0);
				// destroy (no save)
				destroy(newerPop, EnumDestructionType.merged, newerPop.getColonizationDate());
			}
			// else
			// {
			// newerPop.setLastUpdateDate(now);
			// }

			// save newerPop only, olderPop will be saved later during iteration
			// save even if no changes have been made in this iteration, since changes may have
			// been made before.
			save(newerPop);
		}

		// infrastructure.setLastUpdateDate(now);
		infrastructure = solarSystemInfrastructureManager.save(infrastructure);

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
		SolarSystemPopulation homePopulation = getHomePopulation(infrastructure);

		long now = securityManager.getTimeProvider().get();

		double speedFactor = calculator.getSpeedFactor(infrastructure.getMatch().getSpeed());
		long maxPopulation = calculator.getMaxPopulation(infrastructure);

		double prio_full = parameterManager.getDouble(UniverseConquestConstants.PARAM_PRIORITY_FULL);
		double prio_high = parameterManager.getDouble(UniverseConquestConstants.PARAM_PRIORITY_HIGH);
		double prio_medium = parameterManager.getDouble(UniverseConquestConstants.PARAM_PRIORITY_MEDIUM);
		double prio_low = parameterManager.getDouble(UniverseConquestConstants.PARAM_PRIORITY_LOW);
		double prio_none = parameterManager.getDouble(UniverseConquestConstants.PARAM_PRIORITY_NONE);
		double randomization_attack = parameterManager.getDouble(UniverseConquestConstants.PARAM_FACTOR_ATTACK_RANDOMIZE);
		double randomization_build = parameterManager.getDouble(UniverseConquestConstants.PARAM_FACTOR_BUILD_RANDOMIZE);
		long norm_tick = parameterManager.getLong(UniverseConquestConstants.PARAM_NORM_TICK_LENGTH);

		double[] deltaPop = new double[infrastructure.getPopulations().size()];
		double deltaInf = 0;
		long totalPopulation = 0;
		long otherPopulation;
		double b, a, aPop;
		long tick, tick2;
		SolarSystemPopulation pop, pop2;

		// first loop
		// - determine total population
		for(int i = 0; i < infrastructure.getPopulations().size(); i++)
		{
			pop = infrastructure.getPopulations().get(i);
			if(pop.getColonizationDate().getTime() <= now)
				totalPopulation += pop.getPopulation();
		}
		// second loop
		// - determine build and attack strengths
		// - calculate anti attack (current pop is attacker; attack strength is splitted)
		for(int i = 0; i < infrastructure.getPopulations().size(); i++)
		{
			pop = infrastructure.getPopulations().get(i);
			tick = now - pop.getLastUpdateDate().getTime();
			if(pop.getPopulation() > 0 && pop.getColonizationDate().getTime() <= now)
			{
				b = calculator.calculateBuildStrength(speedFactor, pop.getPopulation(), maxPopulation);
				b *= tick / norm_tick; // weight strength by tick-length
				if(randomization_build > 0) // add random variation
					b *= random.nextGaussian(1 - randomization_build, 1 + randomization_build);
				a = calculator.calculateAttackStrength(speedFactor, pop.getPopulation());
				a *= tick / norm_tick; // weight strength by tick-length
				if(randomization_attack > 0) // add random variation
					a *= random.nextGaussian(1 - randomization_attack, 1 + randomization_attack);
				if(pop == homePopulation)
				{
					b *= calculator.calculateInfrastructureBuildInfluence(pop.getPopulation(), infrastructure.getInfrastructure());
					// split build strength by priority
					switch(pop.getBuildPriority())
					{
						case infrastructure:
							deltaPop[i] += b * prio_low;
							deltaInf += b * prio_high;
							break;
						case population:
							deltaPop[i] += b * prio_high;
							deltaInf += b * prio_low;
							break;
						case balanced:
						default:
							deltaPop[i] += b * prio_medium;
							deltaInf += b * prio_medium;
							break;

					}
					// split attack strength by priority (pop only if home)
					aPop = a * prio_full;
					deltaInf += a * prio_none;
				}
				else
				{
					// no build if not home
					deltaPop[i] += b * prio_none;
					deltaInf += b * prio_none;
					// split attack strength by priority
					switch(pop.getAttackPriority())
					{
						case infrastructure:
							aPop = b * prio_low;
							deltaInf += b * prio_high;
							break;
						case population:
							aPop = b * prio_high;
							deltaInf += b * prio_low;
							break;
						case balanced:
						default:
							aPop = b * prio_medium;
							deltaInf += b * prio_medium;
							break;
					}
				}
				// distribute attack strength on other populations
				// (weighted by their amount of population)
				otherPopulation = totalPopulation - pop.getPopulation();
				for(int j = 0; j < infrastructure.getPopulations().size(); j++)
				{
					if(j != i)
					{
						pop2 = infrastructure.getPopulations().get(j);
						tick2 = now - pop2.getLastUpdateDate().getTime();
						deltaPop[j] -= ((double) pop2.getPopulation() / otherPopulation) * aPop * Math.min(tick, tick2) / norm_tick;
					}
				}
			}
		}
		// third loop
		// - apply delta
		for(int i = 0; i < infrastructure.getPopulations().size(); i++)
		{
			pop = infrastructure.getPopulations().get(i);
			logger.debug("population " + pop.getId() + " delta = " + deltaPop[i]);
			pop.setPopulation(pop.getPopulation() + Math.round(deltaPop[i]));
		}
		// infrastructure update
		infrastructure.setInfrastructure(infrastructure.getInfrastructure() + Math.round(deltaInf));

		// return modified list
		return infrastructure.getPopulations();
	}
}

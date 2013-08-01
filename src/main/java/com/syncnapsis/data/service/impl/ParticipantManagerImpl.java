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
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.sun.org.apache.bcel.internal.generic.BALOAD;
import com.syncnapsis.data.dao.ParticipantDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.service.EmpireManager;
import com.syncnapsis.data.service.ParticipantManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumJoinType;
import com.syncnapsis.enums.EnumMatchState;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.security.accesscontrol.MatchAccessController;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.utils.data.ExtendedRandom;

/**
 * Manager-Implementation for access to Participant.
 * 
 * @author ultimate
 */
public class ParticipantManagerImpl extends GenericManagerImpl<Participant, Long> implements ParticipantManager, InitializingBean
{
	/**
	 * ParticipantDao for database access
	 */
	protected ParticipantDao				participantDao;

	/**
	 * The EmpireManager
	 */
	protected EmpireManager					empireManager;
	/**
	 * The SolarSystemPopulationManager
	 */
	protected SolarSystemPopulationManager	solarSystemPopulationManager;

	/**
	 * The universe-conquest {@link Calculator}
	 */
	protected Calculator					calculator;

	/**
	 * The SecurityManager
	 */
	protected BaseGameManager				securityManager;

	/**
	 * Standard Constructor
	 * 
	 * @param participantDao - ParticipantDao for database access
	 * @param empireManager - the EmpireManager
	 * @param solarSystemPopulationManager - the SolarSystemPopulationManager
	 */
	public ParticipantManagerImpl(ParticipantDao participantDao, EmpireManager empireManager,
			SolarSystemPopulationManager solarSystemPopulationManager)
	{
		super(participantDao);
		this.empireManager = empireManager;
		this.participantDao = participantDao;
		this.solarSystemPopulationManager = solarSystemPopulationManager;
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
	 * <code>securityManager.getAccessController(Match.class).isAccessibe(match, operation, securityManager.getPlayerProvider().get())</code>
	 * 
	 * @see MatchAccessController
	 * @return true or false
	 */
	protected boolean isAccessible(Match match, int operation)
	{
		return securityManager.getAccessController(Match.class).isAccessible(match, operation, securityManager.getPlayerProvider().get(),
				securityManager.getEmpireProvider().get());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.ParticipantManager#joinMatch(com.syncnapsis.data.model.Match)
	 */
	@Override
	public Participant joinMatch(Match match)
	{
		// security-check
		Assert.isTrue(isAccessible(match, MatchAccessController.OPERATION_JOIN));

		Empire empire = securityManager.getEmpireProvider().get();

		Assert.notNull(empire, "current empire must not be null!");

		// check the match state and join type
		switch(match.getState())
		{
			case planned:
				if(match.getPlannedJoinType() == EnumJoinType.joiningEnabled)
					break;
				// joining ist not allowed at this state -> return participant if existing
				return getByMatchAndEmpire(match.getId(), empire.getId());
			case active:
				if(match.getStartedJoinType() == EnumJoinType.joiningEnabled)
					break;
			case canceled:
			case finished:
				// joining ist not allowed at this state -> return participant if existing
				return getByMatchAndEmpire(match.getId(), empire.getId());
		}

		return addParticipant(match, empire);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.ParticipantManager#leaveMatch(com.syncnapsis.data.model.Match)
	 */
	@Override
	public boolean leaveMatch(Match match)
	{
		// security-check
		Assert.isTrue(isAccessible(match, MatchAccessController.OPERATION_JOIN));

		Empire empire = securityManager.getEmpireProvider().get();

		Assert.notNull(empire, "current empire must not be null!");

		return removeParticipant(match, empire);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.ParticipantManager#addParticipant(com.syncnapsis.data.model.Match
	 * , long)
	 */
	@Override
	public Participant addParticipant(Match match, long empireId)
	{
		// security-check
		Assert.isTrue(isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT));

		// check the match state and join type
		switch(match.getState())
		{
			case planned:
				if(match.getPlannedJoinType() != EnumJoinType.none)
					break;
				// joining ist not allowed at this state -> return participant if existing
				return getByMatchAndEmpire(match.getId(), empireId);
			case active:
				if(match.getStartedJoinType() != EnumJoinType.none)
					break;
			case canceled:
			case finished:
				// joining ist not allowed at this state -> return participant if existing
				return getByMatchAndEmpire(match.getId(), empireId);
		}

		return addParticipant(match, empireManager.get(empireId));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.ParticipantManager#removeParticipant(com.syncnapsis.data.model
	 * .Match, long)
	 */
	@Override
	public boolean removeParticipant(Match match, long empireId)
	{
		// security-check
		Assert.isTrue(isAccessible(match, MatchAccessController.OPERATION_REMOVE_PARTICIPANT));

		return removeParticipant(match, empireManager.get(empireId));
	}

	/**
	 * Internal method for adding participants to a match to be called from
	 * {@link ParticipantManagerImpl#addParticipant(Match, long)} and
	 * {@link ParticipantManagerImpl#joinMatch(Match)}
	 * 
	 * @param match - the match to add the participant to
	 * @param empire - the empire to add as an participant
	 * @return the (maybe newly created) Participant associating the Empire with the Match
	 */
	protected Participant addParticipant(Match match, Empire empire)
	{
		Participant participant = getByMatchAndEmpire(match.getId(), empire.getId());

		if(participant != null && participant.isActivated())
			return participant; // already participating
		else if(match.getState() == EnumMatchState.canceled || match.getState() == EnumMatchState.finished)
			return null; // adding participants not allowed anymore
		else if(match.getParticipantsMax() > 0 && match.getParticipants().size() >= match.getParticipantsMax())
			return null; // the match is 'full'

		Date now = new Date(securityManager.getTimeProvider().get());

		if(participant == null)
			participant = new Participant();

		participant.setActivated(true);
		participant.setDestructionDate(null);
		participant.setDestructionType(null);
		participant.setMatch(match);
		participant.setEmpire(empire);
		participant.setJoinedDate(now);
		participant.setRank(match.getParticipants().size());
		participant.setRankFinal(false);
		participant.setRankValue(0);
		participant.setStartSystemsSelected(0);

		if(participant.getPopulations() != null)
		{
			// participant has selected start systems earlier, but has left -> reset them
			for(SolarSystemPopulation population : participant.getPopulations())
			{
				population.setDestructionDate(null);
				population.setDestructionType(null);
			}
		}

		return save(participant);
	}

	/**
	 * Internal method for removing participants to a match to be called from
	 * {@link ParticipantManagerImpl#removeParticipant(Match, long)} and
	 * {@link ParticipantManagerImpl#joinMatch(Match)}
	 * 
	 * @param match - the match to remove the participant to
	 * @param empire - the empire to remove as an participant
	 * @return true if removing was successful, false otherwise
	 */
	protected boolean removeParticipant(Match match, Empire empire)
	{
		Participant participant = getByMatchAndEmpire(match.getId(), empire.getId());

		if(participant == null)
			return false;

		Date now = new Date(securityManager.getTimeProvider().get());

		if(match.getState() == EnumMatchState.canceled || match.getState() == EnumMatchState.finished)
		{
			return false;
		}
		else if(match.getState() == EnumMatchState.planned)
		{
			// remove the participant "completely"
			participant.setActivated(false);
		}

		EnumDestructionType destructionType;
		if(empire.getPlayer().equals(securityManager.getPlayerProvider().get()))
		{
			if(match.getState() == EnumMatchState.planned)
				destructionType = EnumDestructionType.left;
			else
				destructionType = EnumDestructionType.givenUp;
		}
		else
		{
			destructionType = EnumDestructionType.removed;
		}

		// destroy will perform the save
		destroy(participant, destructionType, now);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.ParticipantManager#destroy(com.syncnapsis.data.model.Participant)
	 */
	@Override
	public Participant destroy(Participant participant, EnumDestructionType destructionType, Date destructionDate)
	{
		// destroy all remaining populations
		for(SolarSystemPopulation population : participant.getPopulations())
		{
			if(population.isActivated())
				solarSystemPopulationManager.destroy(population, destructionType, destructionDate);
		}

		participant.setDestructionType(destructionType);
		participant.setDestructionDate(destructionDate);
		// participant.setRankFinal(true); // rank will be finalized on next rank update

		return save(participant);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ParticipantManager#getByMatch(long)
	 */
	@Override
	public List<Participant> getByMatch(long matchId)
	{
		return participantDao.getByMatch(matchId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ParticipantManager#getByMatchAndEmpire(long, long)
	 */
	@Override
	public Participant getByMatchAndEmpire(long matchId, long empireId)
	{
		return participantDao.getByMatchAndEmpire(matchId, empireId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.ParticipantManager#startParticipating(com.syncnapsis.data.model
	 * .Participant, java.util.Date)
	 */
	@Override
	public Participant startParticipating(Participant participant, Date participationDate)
	{
		if(participant.getPopulations().size() < participant.getMatch().getStartSystemCount())
			throw new IllegalArgumentException("not all start systems selected!");
		long pop = 0;
		for(SolarSystemPopulation population : participant.getPopulations())
		{
			pop += population.getPopulation();
		}
		if(pop < participant.getMatch().getStartPopulation())
			throw new IllegalArgumentException("not all start population assigned!");

		// mark populations with start date
		for(SolarSystemPopulation population : participant.getPopulations())
		{
			population.setColonizationDate(participationDate);
			solarSystemPopulationManager.save(population);
		}

		return participant;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.ParticipantManager#selectStartSystem(com.syncnapsis.data.model
	 * .SolarSystemInfrastructure, long)
	 */
	@Override
	public SolarSystemPopulation selectStartSystem(SolarSystemInfrastructure infrastructure, long population)
	{
		Empire empire = securityManager.getEmpireProvider().get();
		if(empire == null)
			throw new IllegalArgumentException("current Empire must not be null!");
		Match match = infrastructure.getMatch();
		for(Participant p : match.getParticipants())
		{
			if(p.getEmpire().getId().equals(empire.getId()))
				return solarSystemPopulationManager.selectStartSystem(p, infrastructure, population);
		}
		throw new IllegalArgumentException("current Empire does not participate in the specified match!");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.ParticipantManager#randomSelectStartSystems(com.syncnapsis.data
	 * .model.Participant, com.syncnapsis.utils.data.ExtendedRandom)
	 */
	@Override
	public List<SolarSystemPopulation> randomSelectStartSystems(Participant participant, ExtendedRandom random)
	{
		int startSystems = participant.getMatch().getStartSystemCount();
		List<SolarSystemPopulation> populations = new ArrayList<SolarSystemPopulation>(startSystems);
		populations.addAll(participant.getPopulations());

		long selectedPop = 0;
		for(SolarSystemPopulation population : participant.getPopulations())
		{
			selectedPop += population.getPopulation();
		}

		long remainingPop = participant.getMatch().getStartPopulation() - selectedPop;
		long pop;

		SolarSystem system;
		SolarSystemPopulation population;
		SolarSystemInfrastructure infrastructure;
		while(populations.size() < startSystems)
		{
			// select a random system / infrastructure
			infrastructure = ; 
			// select a random amount of remaining pop
			// reduce remaining pop
			
			pop = random.nextGaussian(min, max);
			pop = Math.max(pop, calculator.getMaxPopulation(infrastructure));
				

			population = solarSystemPopulationManager.selectStartSystem(participant, infrastructure, pop);
			populations.add(population);
		}

		// add the remaining pop arbitrary
		if(remainingPop != 0)
		{

		}

		// update the participant
		participant.setStartSystemsSelected(startSystems);
		save(participant);

		return populations;
	}
}

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

import com.syncnapsis.data.dao.MatchDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.data.service.ParticipantManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumJoinType;
import com.syncnapsis.enums.EnumStartCondition;
import com.syncnapsis.enums.EnumVictoryCondition;
import com.syncnapsis.security.BaseGameManager;

/**
 * Manager-Implementation for access to Match.
 * 
 * @author ultimate
 */
public class MatchManagerImpl extends GenericNameManagerImpl<Match, Long> implements MatchManager, InitializingBean
{
	/**
	 * MatchDao for database access
	 */
	protected MatchDao						matchDao;

	/**
	 * The GalaxyManager
	 */
	protected GalaxyManager					galaxyManager;
	/**
	 * The ParticipantManager
	 */
	protected ParticipantManager			participantManager;
	/**
	 * The SolarSystemPopulationManager
	 */
	protected SolarSystemPopulationManager	solarSystemPopulationManager;

	/**
	 * The SecurityManager
	 */
	protected BaseGameManager				securityManager;

	/**
	 * Standard Constructor
	 * 
	 * @param matchDao - MatchDao for database access
	 * @param galaxyManager - the GalaxyManager
	 * @param participantManager - the ParticipantManager
	 * @param solarSystemPopulationManager - the SolarSystemPopulationManager
	 */
	public MatchManagerImpl(MatchDao matchDao, GalaxyManager galaxyManager, ParticipantManager participantManager,
			SolarSystemPopulationManager solarSystemPopulationManager)
	{
		super(matchDao);
		this.matchDao = matchDao;
		this.galaxyManager = galaxyManager;
		this.participantManager = participantManager;
		this.solarSystemPopulationManager = solarSystemPopulationManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(securityManager, "securityManager must not be null!");
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
	 * @see com.syncnapsis.data.service.MatchManager#getByCreator(long, boolean, boolean, boolean,
	 * boolean)
	 */
	@Override
	public List<Match> getByCreator(long creatorId, boolean planned, boolean active, boolean finished, boolean canceled)
	{
		Date referenceDate = new Date(securityManager.getTimeProvider().get());
		return matchDao.getByCreator(creatorId, planned, active, finished, canceled, referenceDate);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MatchManager#getByPlayer(long, boolean, boolean, boolean,
	 * boolean)
	 */
	@Override
	public List<Match> getByPlayer(long playerId, boolean planned, boolean active, boolean finished, boolean canceled)
	{
		Date referenceDate = new Date(securityManager.getTimeProvider().get());
		return matchDao.getByPlayer(playerId, planned, active, finished, canceled, referenceDate);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MatchManager#getByGalaxy(long, boolean, boolean, boolean,
	 * boolean)
	 */
	@Override
	public List<Match> getByGalaxy(long galaxyId, boolean planned, boolean active, boolean finished, boolean canceled)
	{
		Date referenceDate = new Date(securityManager.getTimeProvider().get());
		return matchDao.getByGalaxy(galaxyId, planned, active, finished, canceled, referenceDate);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MatchManager#createMatch(java.lang.String, long, int,
	 * java.lang.Long, com.syncnapsis.enums.EnumStartCondition, java.util.Date, boolean, int, int,
	 * com.syncnapsis.enums.EnumVictoryCondition, int, int, int, java.util.List,
	 * com.syncnapsis.enums.EnumJoinType, com.syncnapsis.enums.EnumJoinType)
	 */
	@Override
	public Match createMatch(String title, long galaxyId, int speed, Long seed, EnumStartCondition startCondition, Date startDate,
			boolean startSystemSelectionEnabled, int startSystemCount, int startPopulation, EnumVictoryCondition victoryCondition,
			int victoryParameter, int participantsMax, int participantsMin, List<Long> participantIds, EnumJoinType plannedJoinType,
			EnumJoinType startedJoinType)
	{
		Galaxy galaxy = galaxyManager.get(galaxyId);
		Assert.notNull(galaxy, "galaxy with ID " + galaxyId + " not found!");

		Match match = new Match();
		match.setActivated(true);
		match.setCreationDate(new Date(securityManager.getTimeProvider().get()));
		match.setCreator(securityManager.getPlayerProvider().get());
		match.setFinishedDate(null);
		match.setGalaxy(galaxy);
		// match.setParticipants(participants);
		match.setParticipantsMax(participantsMax);
		match.setParticipantsMin(participantsMin);
		match.setPlannedJoinType(plannedJoinType);
		match.setSeed(seed);
		match.setSpeed(speed);
		match.setStartCondition(startCondition);
		match.setStartDate(startDate);
		match.setStartedJoinType(startedJoinType);
		match.setStartSystemCount(startSystemCount);
		match.setStartSystemSelectionEnabled(startSystemSelectionEnabled);
		match.setTitle(title);
		match.setVictoryCondition(victoryCondition);

		match = save(match);

		// TODO create infrastructures

		// add participants
		for(long playerId : participantIds)
		{
			if(!participantManager.addParticipant(match, playerId))
				logger.warn("could not add player " + playerId + " to match " + match.getId());
		}

		// flush?

		return startMatchIfNecessary(get(match.getId()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MatchManager#startMatch(com.syncnapsis.data.model.Match)
	 */
	@Override
	public Match startMatch(Match match)
	{
		Player current = securityManager.getPlayerProvider().get();
		Assert.isTrue(match.getCreator().equals(current), "current player " + current.getId()
				+ " is not allowed to start the match created by player " + match.getCreator().getId());
		return performStartMatch(match);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.MatchManager#startMatchIfNecessary(com.syncnapsis.data.model.
	 * Match)
	 */
	@Override
	public Match startMatchIfNecessary(Match match)
	{
		Date now = new Date(securityManager.getTimeProvider().get());

		switch(match.getStartCondition())
		{
			case manually:
				break;
			case maxParticipantsReached:
				if(match.getParticipants().size() < match.getParticipantsMax())
					break;
			case immediately:
				return performStartMatch(match);
			case plannedAndMinParticipantsReached:
				if(match.getParticipants().size() < match.getParticipantsMin())
					break;
			case planned:
				if(match.getStartDate().after(now))
					break;
				return performStartMatch(match);
		}
		return match;
	}

	/**
	 * Same as {@link MatchManager#startMatch(long)} just taking the match directly instead of the
	 * ID. Used from {@link MatchManagerImpl#startMatch(long)} and
	 * {@link MatchManagerImpl#startMatchIfNecessary(long)}
	 * 
	 * @see MatchManager#startMatch(long)
	 * @param match - the match to start
	 * @return the match entity (for convenience)
	 */
	protected Match performStartMatch(Match match)
	{
		Date startDate = match.getStartDate();
		if(startDate == null)
		{
			startDate = new Date(securityManager.getTimeProvider().get());
			match.setStartDate(startDate);
		}

		List<SolarSystemPopulation> populations;
		for(Participant p : match.getParticipants())
		{
			// assign start populations
			populations = solarSystemPopulationManager.randomSelectStartSystems(p);
			// mark populations with start date
			for(SolarSystemPopulation population : populations)
			{
				population.setColonizationDate(startDate);
				solarSystemPopulationManager.save(population);
			}
		}

		// TODO assign rivals (vendetta)

		return match;
	}

	/**
	 * Return the number of rivals to assign in vedetta mode for the given number of total
	 * participants.<br>
	 * 
	 * @param participants - the number of participants
	 * @return the number of rivals
	 */
	protected int getNumberOfRivals(int participants, EnumVictoryCondition condition)
	{
		return (participants + 2) / 3;
		// 1 > 0 > 0 / 0
		// 2 > 1 > 1 / 1
		// 3 > 2 > 1 / 1
		// 4 > 3 > 1 / 2
		// 5 > 4 > 2 / 2
		// 6 > 5 > 2 / 2
		// 7 > 6 > 2 / 3
		// 8 > 7 > 3 / 3
		// 9 > 8 > 3 / 3
		// 10 > 9 > 3 / 4
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MatchManager#cancelMatch(com.syncnapsis.data.model.Match)
	 */
	@Override
	public boolean cancelMatch(Match match)
	{
		Assert.isTrue(match.getS)
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MatchManager#finishMatch(com.syncnapsis.data.model.Match)
	 */
	@Override
	public Match finishMatch(Match match)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

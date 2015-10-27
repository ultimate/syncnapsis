package com.syncnapsis.universe;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumMatchState;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.utils.concurrent.Worker;
import com.syncnapsis.utils.constants.ConstantLoader;
import com.syncnapsis.utils.data.ExtendedRandom;

/**
 * {@link Worker} implementation for the universe-conquest game.<br>
 * The {@link Worker} will run in a separate Thread and will continuously perform the the necessary
 * step-wise population calculations for the game using the {@link Calculator}.<br>
 * <br>
 * This worker will start itself after properties are set and after the underlying
 * {@link ConstantLoader} has finished loading the constants. If no {@link ConstantLoader} is set it
 * will start immediately.
 * 
 * @author ultimate
 */
public class UniverseWorker extends Worker implements InitializingBean, DisposableBean
{
	/**
	 * The {@link ConstantLoader} to wait for before start working
	 */
	protected ConstantLoader<?>				constantLoader;

	/**
	 * The {@link MatchManager} used to access the list of active matches
	 */
	protected MatchManager					matchManager;
	/**
	 * The {@link SolarSystemPopulationManager} used for simulating
	 */
	protected SolarSystemPopulationManager	solarSystemPopulationManager;

	/**
	 * The random number generator used
	 */
	protected ExtendedRandom				random;

	/**
	 * The interval at which to update the match ranks (measured in periods/ticks).<br>
	 * Default is 1
	 */
	protected int							rankUpdateInterval		= 1;
	/**
	 * The interval at which to update the match systems (measured in periods/ticks).<br>
	 * Default is 1
	 */
	protected int							systemUpdateInterval	= 1;
	/**
	 * The interval at which to update the match movements (measured in periods/ticks).<br>
	 * Default is 1
	 */
	protected int							movementUpdateInterval	= 1;

	/**
	 * Standard Constructor
	 * 
	 * @param interval - the interval for executing the calculations
	 * @param timeProvider - the {@link TimeProvider}
	 * @param calculator - the {@link Calculator}
	 */
	public UniverseWorker(long interval, TimeProvider timeProvider)
	{
		super(interval, timeProvider);
	}

	/**
	 * The {@link ConstantLoader} to wait for before start working
	 * 
	 * @return constantLoader
	 */
	public ConstantLoader<?> getConstantLoader()
	{
		return constantLoader;
	}

	/**
	 * The {@link ConstantLoader} to wait for before start working
	 * 
	 * @param constantLoader - the Constant Loader
	 */
	public void setConstantLoader(ConstantLoader<?> constantLoader)
	{
		this.constantLoader = constantLoader;
	}

	/**
	 * The {@link MatchManager} used to access the list of active matches
	 * 
	 * @return matchManager
	 */
	public MatchManager getMatchManager()
	{
		return matchManager;
	}

	/**
	 * The {@link MatchManager} used to access the list of active matches
	 * 
	 * @param matchManager - the MatchManager
	 */
	public void setMatchManager(MatchManager matchManager)
	{
		this.matchManager = matchManager;
	}

	/**
	 * The {@link SolarSystemPopulationManager} used for simulating
	 * 
	 * @return solarSystemPopulationManager
	 */
	public SolarSystemPopulationManager getSolarSystemPopulationManager()
	{
		return solarSystemPopulationManager;
	}

	/**
	 * The {@link SolarSystemPopulationManager} used for simulating
	 * 
	 * @param solarSystemPopulationManager - the SolarSystemPopulationManager
	 */
	public void setSolarSystemPopulationManager(SolarSystemPopulationManager solarSystemPopulationManager)
	{
		this.solarSystemPopulationManager = solarSystemPopulationManager;
	}

	/**
	 * The random number generator used
	 * 
	 * @return random
	 */
	public ExtendedRandom getRandom()
	{
		return random;
	}

	/**
	 * The random number generator used
	 * 
	 * @param random - the random
	 */
	public void setRandom(ExtendedRandom random)
	{
		this.random = random;
	}

	/**
	 * The interval at which to update the match ranks (measured in periods/ticks).<br>
	 * Default is 1
	 * 
	 * @return rankUpdateInterval
	 */
	public int getRankUpdateInterval()
	{
		return rankUpdateInterval;
	}

	/**
	 * The interval at which to update the match ranks (measured in periods/ticks).<br>
	 * Default is 1
	 * 
	 * @param rankUpdateInterval - the interval
	 */
	public void setRankUpdateInterval(int rankUpdateInterval)
	{
		this.rankUpdateInterval = rankUpdateInterval;
	}

	/**
	 * The interval at which to update the match systems (measured in periods/ticks).<br>
	 * Default is 1
	 * 
	 * @return systemUpdateInterval
	 */
	public int getSystemUpdateInterval()
	{
		return systemUpdateInterval;
	}

	/**
	 * The interval at which to update the match systems (measured in periods/ticks).<br>
	 * Default is 1
	 * 
	 * @param systemUpdateInterval - the interval
	 */
	public void setSystemUpdateInterval(int systemUpdateInterval)
	{
		this.systemUpdateInterval = systemUpdateInterval;
	}

	/**
	 * The interval at which to update the match movements (measured in periods/ticks).<br>
	 * Default is 1
	 * 
	 * @return movementUpdateInterval
	 */
	public int getMovementUpdateInterval()
	{
		return movementUpdateInterval;
	}

	/**
	 * The interval at which to update the match movements (measured in periods/ticks).<br>
	 * Default is 1
	 * 
	 * @param movementUpdateInterval - the interval
	 */
	public void setMovementUpdateInterval(int movementUpdateInterval)
	{
		this.movementUpdateInterval = movementUpdateInterval;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(solarSystemPopulationManager, "solarSystemPopulationManager must not be null!");
		if(this.random == null)
			this.random = new ExtendedRandom();
		if(this.constantLoader != null)
			this.constantLoader.await();
		this.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception
	{
		this.stop();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.Worker#work(long)
	 */
	@Override
	public void work(long executionId) throws Throwable
	{
		logger.info("UniverseWorker running: #" + executionId);

		// check which channels to update in this execution
		boolean updateRanks = (executionId % rankUpdateInterval == 0);
		boolean updateSystems = (executionId % systemUpdateInterval == 0);
		boolean updateMovements = (executionId % movementUpdateInterval == 0);

		// fetch all active matches
		List<Match> matches = matchManager.getByState(EnumMatchState.active);

		for(Match match : matches)
		{
			// simulate population changes for all infrastructures
			for(SolarSystemInfrastructure infrastructure : match.getInfrastructures())
			{
				solarSystemPopulationManager.simulate(infrastructure, random);
			}
			// update rankings
			match = matchManager.updateRanking(match);
			// push match updates
			matchManager.updateChannels(match, updateRanks, updateSystems, updateMovements);
		}
	}
}

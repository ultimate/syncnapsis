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
package com.syncnapsis.experimental;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.data.service.impl.SolarSystemPopulationManagerImpl;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.exceptions.DeserializationException;
import com.syncnapsis.exceptions.SerializationException;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.utils.serialization.Serializer;
import com.syncnapsis.websockets.engine.ServletEngine;

/**
 * @author ultimate
 * 
 */
public class SimulationServlet extends ServletEngine
{
	public static final String					PARAM_PARAMETERS		= "params";
	public static final String					PARAM_SCENARIO			= "scenario";
	public static final String					PARAM_SEED				= "seed";
	public static final String					P_DURATION				= "duration";
	public static final String					P_TICKLENGTH			= "ticklength";
	public static final String					P_RANDOMIZE_ATTACK		= "randomizeAttack";
	public static final String					P_RANDOMIZE_BUILD		= "randomizeBuild";
	public static final String					P_SPEED					= "speed";
	public static final String					RESULT_TIME				= "time";
	public static final String					RESULT_INFRASTRUCTURE	= "infrastructure";
	public static final String					RESULT_PARTICIPANT_I	= "participant_";

	protected SolarSystemPopulationDao			solarSystemPopulationDao;
	protected SolarSystemInfrastructureManager	solarSystemInfrastructureManager;
	protected Calculator						calculator;

	protected Serializer<String>				serializer;
	// protected Mapper mapper;

	protected SolarSystemPopulationManager		mockSolarSystemPopulationManager;
	protected BaseGameManager					securityManager;
	protected TimeProvider						timeProvider;

	public Serializer<String> getSerializer()
	{
		return serializer;
	}

	public void setSerializer(Serializer<String> serializer)
	{
		this.serializer = serializer;
	}

	public Calculator getCalculator()
	{
		return calculator;
	}

	public void setCalculator(Calculator calculator)
	{
		this.calculator = calculator;
	}

	public SolarSystemPopulationDao getSolarSystemPopulationDao()
	{
		return solarSystemPopulationDao;
	}

	public void setSolarSystemPopulationDao(SolarSystemPopulationDao solarSystemPopulationDao)
	{
		this.solarSystemPopulationDao = solarSystemPopulationDao;
	}

	public SolarSystemInfrastructureManager getSolarSystemInfrastructureManager()
	{
		return solarSystemInfrastructureManager;
	}

	public void setSolarSystemInfrastructureManager(SolarSystemInfrastructureManager solarSystemInfrastructureManager)
	{
		this.solarSystemInfrastructureManager = solarSystemInfrastructureManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.engine.BaseWebEngine#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(serializer, "serializer must not be null!");
		Assert.notNull(calculator, "calculator must not be null!");
		Assert.notNull(solarSystemPopulationDao, "solarSystemPopulationDao must not be null!");
		Assert.notNull(solarSystemInfrastructureManager, "solarSystemInfrastructureManager must not be null!");

		timeProvider = new MockTimeProvider();
		securityManager = new BaseGameManager();
		securityManager.setTimeProvider(timeProvider);

		mockSolarSystemPopulationManager = new SolarSystemPopulationManagerImpl(solarSystemPopulationDao, solarSystemInfrastructureManager);
		((SolarSystemPopulationManagerImpl) mockSolarSystemPopulationManager).setCalculator(calculator);
		((SolarSystemPopulationManagerImpl) mockSolarSystemPopulationManager).setSecurityManager(securityManager);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse)
	 */
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
	{
		if(!(req instanceof HttpServletRequest))
			throw new ServletException("can only handle HttpServletRequest");
		if(!(res instanceof HttpServletResponse))
			throw new ServletException("can only handle HttpServletResponse");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		try
		{
			Object[] authorities = new Object[0];
			Long seed;
			if(request.getParameter(PARAM_SEED) != null)
				seed = Long.parseLong(request.getParameter(PARAM_SEED));
			else
				seed = System.currentTimeMillis();
			Map<String, Object> parameters = serializer.deserialize(request.getParameter(PARAM_PARAMETERS), new HashMap<String, Object>(),
					authorities);
			SolarSystemInfrastructure scenario = serializer.deserialize(request.getParameter(PARAM_SCENARIO), SolarSystemInfrastructure.class,
					authorities);

			ExtendedRandom random = new ExtendedRandom(seed);

			int speed = Integer.parseInt((String) parameters.get(P_SPEED));
			long duration = Long.parseLong((String) parameters.get(P_DURATION));
			long tickLength = Long.parseLong((String) parameters.get(P_TICKLENGTH));
			double randomizeAttack = Double.parseDouble((String) parameters.get(P_RANDOMIZE_ATTACK));
			double randomizeBuild = Double.parseDouble((String) parameters.get(P_RANDOMIZE_BUILD));

			scenario.setMatch(new Match());
			scenario.getMatch().setSpeed(speed);

			UniverseConquestConstants.PARAM_FACTOR_ATTACK_RANDOMIZE.define("" + randomizeAttack);
			UniverseConquestConstants.PARAM_FACTOR_BUILD_RANDOMIZE.define("" + randomizeBuild);

			Map<String, Long[]> result = simulate(duration, tickLength, scenario, random);

			String resultString = serializer.serialize(result, new Object[0]);
			logger.debug("simulation complete...");
			logger.debug(resultString);

			response.getOutputStream().write(resultString.getBytes());
			response.setStatus(HttpServletResponse.SC_OK);
		}
		catch(DeserializationException e)
		{
			throw new IOException(e);
		}
		catch(SerializationException e)
		{
			throw new IOException(e);
		}
	}

	public Map<String, Long[]> simulate(long duration, long tickLength, SolarSystemInfrastructure scenario, ExtendedRandom random)
	{
		Map<String, Long[]> result = new HashMap<String, Long[]>();
		long tick = 0;
		Date tickDate;
		int ticks = (int) (duration / tickLength + 1);

		result.put(RESULT_TIME, new Long[ticks]);
		result.put(RESULT_INFRASTRUCTURE, new Long[ticks]);

		Map<Long, Integer> populationCount = new HashMap<Long, Integer>();

		// prepare populations
		for(SolarSystemPopulation pop : scenario.getPopulations())
		{
			if(!populationCount.containsKey(pop.getParticipant().getId()))
				populationCount.put(pop.getParticipant().getId(), 1);
			else
				populationCount.put(pop.getParticipant().getId(), populationCount.get(pop.getParticipant().getId()) + 1);
			pop.setId(pop.getParticipant().getId() * 100 + populationCount.get(pop.getParticipant().getId()));
			pop.setActivated(true);
			pop.setLastUpdateDate(new Date(tick));
			if(pop.getAttackPriority() == null)
				pop.setAttackPriority(EnumPopulationPriority.balanced);
			if(pop.getBuildPriority() == null)
				pop.setBuildPriority(EnumPopulationPriority.balanced);
			// set others ?
		}

		// simulate
		String partKey;
		for(int i = 0; i < ticks; i++)
		{
			timeProvider.set(tick);

			result.get(RESULT_TIME)[i] = tick;
			result.get(RESULT_INFRASTRUCTURE)[i] = scenario.getInfrastructure();
			for(SolarSystemPopulation pop : scenario.getPopulations())
			{
				partKey = RESULT_PARTICIPANT_I + pop.getParticipant().getId();

				if(!result.containsKey(partKey))
				{
					result.put(partKey, new Long[ticks]);
					for(int j = 0; j < ticks; j++)
					{
						result.get(partKey)[j] = 0L;
					}
				}
				if(pop.getColonizationDate().getTime() <= tick)
				{
					result.get(partKey)[i] += pop.getPopulation();
				}
			}

			// do the operations contained in SolarSystemPopulationManager#simulate
			// but without the save
			tickDate = new Date(tick);
			mockSolarSystemPopulationManager.merge(scenario, tickDate);
			scenario.setHomePopulation(mockSolarSystemPopulationManager.getHomePopulation(scenario, tickDate));
			calculator.calculateDeltas(scenario, random, tickDate);
			
			tick += tickLength;
		}

		return result;
	}
}

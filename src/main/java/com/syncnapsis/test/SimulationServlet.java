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
package com.syncnapsis.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.data.service.impl.SolarSystemPopulationManagerImpl;
import com.syncnapsis.exceptions.DeserializationException;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.universe.CalculatorImpl;
import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.utils.serialization.Serializer;
import com.syncnapsis.websockets.engine.ServletEngine;

/**
 * @author ultimate
 * 
 */
public class SimulationServlet extends ServletEngine
{
	public static final String					PARAM_PARAMETERS	= "params";
	public static final String					PARAM_SCENARIO		= "scenario";
	public static final String					PARAM_SEED			= "seed";
	public static final String					P_DURATION			= "duration";
	public static final String					P_TICKLENGTH		= "ticklength";
	public static final String					P_RANDOMIZE_ATTACK	= "randomizeAttack";
	public static final String					P_RANDOMIZE_BUILD	= "randomizeBuild";

	protected Serializer<String>				serializer;
	protected SolarSystemPopulationDao			solarSystemPopulationDao;
	protected SolarSystemInfrastructureManager	solarSystemInfrastructureManager;
	protected ParameterManager					parameterManager;

	protected ParameterManager					mockParameterManager;
	protected Calculator						calculator;
	protected SolarSystemPopulationManager		solarSystemPopulationManager;

	public Serializer<String> getSerializer()
	{
		return serializer;
	}

	public void setSerializer(Serializer<String> serializer)
	{
		this.serializer = serializer;
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
		Assert.notNull(solarSystemPopulationDao, "solarSystemPopulationDao must not be null!");
		Assert.notNull(solarSystemInfrastructureManager, "solarSystemInfrastructureManager must not be null!");

		mockParameterManager = new MockParameterManager();
		calculator = new CalculatorImpl(mockParameterManager);
		solarSystemPopulationManager = new SolarSystemPopulationManagerImpl(solarSystemPopulationDao, solarSystemInfrastructureManager,
				mockParameterManager);
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
		// TODO Auto-generated method stub

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

			long duration = (Long) parameters.get(P_DURATION);
			long tickLength = (Long) parameters.get(P_TICKLENGTH);
			double randomizeAttack = (Double) parameters.get(P_RANDOMIZE_ATTACK);
			double randomizeBuild = (Double) parameters.get(P_RANDOMIZE_BUILD);

			long tick = 0;
			List<SolarSystemPopulation> populations;
			do
			{
				populations = solarSystemPopulationManager.simulate(scenario, random);
				tick += tickLength;
			} while(tick < duration);
		}
		catch(DeserializationException e)
		{
			throw new IOException(e);
		}
	}
}

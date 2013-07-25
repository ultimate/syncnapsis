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
package com.syncnapsis.universe;

import java.util.LinkedList;
import java.util.List;

import org.jmock.Expectations;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.tests.LoggerTestCase;

/**
 * @author ultimate
 */
public class CalculatorImplTest extends LoggerTestCase
{
	private ParameterManager	parameterManager;
	private CalculatorImpl		calculator;

	private static final long	popMax	= 1000000000000L;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		parameterManager = mockContext.mock(ParameterManager.class);
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX);
				will(returnValue(1000));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX);
				will(returnValue(1000));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR);
				will(returnValue(1000000));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getDouble(UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR);
				will(returnValue(10.0));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getDouble(UniverseConquestConstants.PARAM_TRAVEL_EXODUS_FACTOR);
				will(returnValue(20.0));
			}
		});

		calculator = new CalculatorImpl(parameterManager);
	}

	public void testGetStandardTravelDistance() throws Exception
	{
		Galaxy galaxy = new Galaxy();
		galaxy.setMaxGap(1234);

		assertEquals(galaxy.getMaxGap(), calculator.getStandardTravelDistance(galaxy));
	}

	public void testCalculateMaxTravelDistance() throws Exception
	{
		SolarSystemPopulation origin = new SolarSystemPopulation();
		origin.setInfrastructure(new SolarSystemInfrastructure());
		origin.getInfrastructure().setSolarSystem(new SolarSystem());
		origin.getInfrastructure().getSolarSystem().setGalaxy(new Galaxy());

		// gap = 1
		origin.getInfrastructure().getSolarSystem().getGalaxy().setMaxGap(1);

		origin.getInfrastructure().setInfrastructure(1);
		assertEquals(0.0, calculator.calculateMaxTravelDistance(origin, popMax, false));
		assertEquals(5.0, calculator.calculateMaxTravelDistance(origin, 1, false));
		assertEquals(0.0, calculator.calculateMaxTravelDistance(origin, popMax, true));
		assertEquals(10.0, calculator.calculateMaxTravelDistance(origin, 1, true));

		origin.getInfrastructure().setInfrastructure(popMax);
		assertEquals(10.0, calculator.calculateMaxTravelDistance(origin, 1, false));
		assertEquals(5.0, calculator.calculateMaxTravelDistance(origin, popMax, false));
		assertEquals(20.0, calculator.calculateMaxTravelDistance(origin, 1, true));
		assertEquals(10.0, calculator.calculateMaxTravelDistance(origin, popMax, true));

		// other gap
		int gap = 1234;
		origin.getInfrastructure().getSolarSystem().getGalaxy().setMaxGap(gap);

		origin.getInfrastructure().setInfrastructure(1);
		assertEquals(gap * 0.0, calculator.calculateMaxTravelDistance(origin, popMax, false));
		assertEquals(gap * 5.0, calculator.calculateMaxTravelDistance(origin, 1, false));
		assertEquals(gap * 0.0, calculator.calculateMaxTravelDistance(origin, popMax, true));
		assertEquals(gap * 10.0, calculator.calculateMaxTravelDistance(origin, 1, true));

		origin.getInfrastructure().setInfrastructure(popMax);
		assertEquals(gap * 10.0, calculator.calculateMaxTravelDistance(origin, 1, false));
		assertEquals(gap * 5.0, calculator.calculateMaxTravelDistance(origin, popMax, false));
		assertEquals(gap * 20.0, calculator.calculateMaxTravelDistance(origin, 1, true));
		assertEquals(gap * 10.0, calculator.calculateMaxTravelDistance(origin, popMax, true));
	}

	public void testCalculateMaxMovablePopulation() throws Exception
	{
		SolarSystemPopulation origin = new SolarSystemPopulation();
		origin.setInfrastructure(new SolarSystemInfrastructure());
		origin.getInfrastructure().setSolarSystem(new SolarSystem());
		origin.getInfrastructure().getSolarSystem().setGalaxy(new Galaxy());
		origin.setPopulation(popMax);

		// gap = 1
		origin.getInfrastructure().getSolarSystem().getGalaxy().setMaxGap(1);

		origin.getInfrastructure().setInfrastructure(1);
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 5));
		assertEquals(0, calculator.calculateMaxMovablePopulation(origin, 10));

		origin.getInfrastructure().setInfrastructure(popMax);
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 5));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 10));

		// other gap
		int gap = 1234;
		origin.getInfrastructure().getSolarSystem().getGalaxy().setMaxGap(gap);

		origin.getInfrastructure().setInfrastructure(1);
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 5*gap));
		assertEquals(0, calculator.calculateMaxMovablePopulation(origin, 10*gap));

		origin.getInfrastructure().setInfrastructure(popMax);
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 5*gap));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 10*gap));
		
		// other population
		origin.setPopulation(123456789);
		
		origin.getInfrastructure().setInfrastructure(1);
		assertEquals(origin.getPopulation(), calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 5*gap));
		assertEquals(0, calculator.calculateMaxMovablePopulation(origin, 10*gap));
		
		origin.getInfrastructure().setInfrastructure(popMax);
		assertEquals(origin.getPopulation(), calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(origin.getPopulation(), calculator.calculateMaxMovablePopulation(origin, 5*gap));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 10*gap));
	}

	public void testCalculateTravelTime() throws Exception
	{
		fail("unimplemented");
	}

	public void testGetMaxPopulationExponent() throws Exception
	{
		assertEquals(12.0, calculator.getMaxPopulationExponent());
	}

	public void testCalculateSize() throws Exception
	{
		// example size...
		int xSize = 110;
		int ySize = 220;
		int zSize = 50;

		List<Vector.Integer> coords = new LinkedList<Vector.Integer>();
		coords.add(new Vector.Integer(-34, -98, -78)); // extreme corner #1
		coords.add(new Vector.Integer(68, 117, -29)); // extreme corner #2

		Vector.Integer size = calculator.calculateSize(coords);
		assertEquals(xSize, (int) size.getX());
		assertEquals(ySize, (int) size.getY());
		assertEquals(zSize, (int) size.getZ());
	}

	public void testCalculateMaxGap() throws Exception
	{
		List<Vector.Integer> coords = new LinkedList<Vector.Integer>();
		coords.add(new Vector.Integer(100, 0, 100));
		coords.add(new Vector.Integer(-100, 0, 100));

		assertEquals(200, calculator.calculateMaxGap(coords));

		coords.add(new Vector.Integer(100, 0, 0));

		assertEquals(200, calculator.calculateMaxGap(coords));

		coords.add(new Vector.Integer(1000, 0, 0));

		assertEquals(900, calculator.calculateMaxGap(coords));

		coords.add(new Vector.Integer(1000000000, 0, 0)); // test there is no integer overflow

		assertEquals(999999000, calculator.calculateMaxGap(coords));

	}
}

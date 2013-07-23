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

import org.jmock.Expectations;

import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.utils.data.ExtendedRandom;

@TestCoversClasses({ SolarSystemPopulationManager.class, SolarSystemPopulationManagerImpl.class })
public class SolarSystemPopulationManagerImplTest extends
		GenericManagerImplTestCase<SolarSystemPopulation, Long, SolarSystemPopulationManager, SolarSystemPopulationDao>
{
	private ParameterManager	parameterManager;
	private final long			referenceTime	= 1234;

	private ExtendedRandom		random			= new ExtendedRandom();

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new SolarSystemPopulation());
		setDaoClass(SolarSystemPopulationDao.class);
		setMockDao(mockContext.mock(SolarSystemPopulationDao.class));
		setMockManager(new SolarSystemPopulationManagerImpl(mockDao, parameterManager));
	}

	public void testGetByMatch() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByMatch", new ArrayList<SolarSystemPopulation>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetByParticipant() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByParticipant", new ArrayList<SolarSystemPopulation>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testSelectStartSystem() throws Exception
	{
		fail("unimplemented");
	}

	public void testRandomSelectStartSystems() throws Exception
	{
		fail("unimplemented");
	}

	public void testSpinoff() throws Exception
	{
		fail("unimplemented");
	}

	public void testResettle() throws Exception
	{
		fail("unimplemented");
	}

	public void testDestroy() throws Exception
	{
		final Date destructionDate = new Date(referenceTime);
		final EnumDestructionType destructionType = EnumDestructionType.destroyed;

		final SolarSystemPopulation in = new SolarSystemPopulation();
		in.setActivated(true);
		in.setDestructionDate(null);
		in.setDestructionType(null);

		final SolarSystemPopulation out = new SolarSystemPopulation();
		out.setActivated(false);
		out.setDestructionDate(destructionDate);
		out.setDestructionType(destructionType);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(out);
				will(returnValue(out));
			}
		});
		SolarSystemPopulation result = mockManager.destroy(in, destructionType, destructionDate);
		mockContext.assertIsSatisfied();
		assertEquals(out, result);
	}

	public void testMerge() throws Exception
	{
		fail("unimplemented");
	}

	public void testUpdateTravelSpeed() throws Exception
	{
		// don't test public updateTravelSpeed just test proctected version
		fail("unimplemented");
	}

	private void speedTest(long timeToTravel, long timeToArrival, int travelSpeed, int travelDistance, double expectedProgress) throws Exception
	{
		final SolarSystemPopulation origin = getPopulation(0, 0, 0);
		final SolarSystemPopulation population = getPopulation(travelDistance, 0, 0);
		population.setOrigin(origin);

		long now = 1234;
		double startProgress = random.nextDouble(0, 1 - expectedProgress);
		Date speedChangeDate = new Date(now + timeToTravel);
		int newSpeed = random.nextInt(10, 100);

		population.setColonizationDate(new Date(now + timeToArrival));
		population.setTravelProgress(startProgress);
		population.setTravelProgressDate(new Date(now));
		population.setTravelSpeed(travelSpeed);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(population);
				will(returnValue(population));
			}
		});

		((SolarSystemPopulationManagerImpl) mockManager).updateTravelSpeed(population, newSpeed, speedChangeDate);

		mockContext.assertIsSatisfied();

		if(timeToTravel < timeToArrival)
		{
			assertEquals(startProgress + expectedProgress, population.getTravelProgress());
			assertEquals(speedChangeDate, population.getTravelProgressDate());
			assertEquals(newSpeed, population.getTravelSpeed());
		}
		else
		{
			assertEquals(1.0, population.getTravelProgress());
			assertEquals(population.getColonizationDate(), population.getTravelProgressDate());
			assertEquals(0, population.getTravelSpeed());
		}
	}

	private SolarSystemPopulation getPopulation(int x, int y, int z)
	{
		SolarSystemPopulation population = new SolarSystemPopulation();
		population.setInfrastructure(new SolarSystemInfrastructure());
		population.getInfrastructure().setSolarSystem(new SolarSystem());
		population.getInfrastructure().getSolarSystem().setCoords(new Vector.Integer(x, y, z));
		return population;
	}
}

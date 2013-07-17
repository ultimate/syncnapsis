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
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ SolarSystemPopulationManager.class, SolarSystemPopulationManagerImpl.class })
public class SolarSystemPopulationManagerImplTest extends
		GenericManagerImplTestCase<SolarSystemPopulation, Long, SolarSystemPopulationManager, SolarSystemPopulationDao>
{
	private final long	referenceTime	= 1234;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new SolarSystemPopulation());
		setDaoClass(SolarSystemPopulationDao.class);
		setMockDao(mockContext.mock(SolarSystemPopulationDao.class));
		setMockManager(new SolarSystemPopulationManagerImpl(mockDao));
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
}

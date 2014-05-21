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

import com.syncnapsis.data.dao.PinboardMessageDao;
import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.data.service.PinboardMessageManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { PinboardMessageManager.class, PinboardMessageManagerImpl.class })
public class PinboardMessageManagerImplTest extends GenericManagerImplTestCase<PinboardMessage, Long, PinboardMessageManager, PinboardMessageDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new PinboardMessage());
		setDaoClass(PinboardMessageDao.class);
		setMockDao(mockContext.mock(PinboardMessageDao.class));
		setMockManager(new PinboardMessageManagerImpl(mockDao));
	}
	
	public void testGetByPinboard() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByPinboard", new ArrayList<PinboardMessage>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);

		managerCall = new MethodCall("getByPinboard", new ArrayList<PinboardMessage>(), 1L, 10);
		daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}

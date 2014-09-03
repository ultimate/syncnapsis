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
package com.syncnapsis.data.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;

import com.syncnapsis.data.dao.BlackListEntryDao;
import com.syncnapsis.data.model.BlackList;
import com.syncnapsis.data.model.BlackListEntry;
import com.syncnapsis.data.service.BlackListEntryManager;
import com.syncnapsis.data.service.BlackListManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ BlackListEntryManager.class, BlackListEntryManagerImpl.class })
public class BlackListEntryManagerImplTest extends GenericManagerImplTestCase<BlackListEntry, Long, BlackListEntryManager, BlackListEntryDao>
{
	private BlackListManager	blackListManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new BlackListEntry());
		setDaoClass(BlackListEntryDao.class);
		setMockDao(mockContext.mock(BlackListEntryDao.class));
		setMockManager(new BlackListEntryManagerImpl(mockDao, blackListManager));
	}

	public void testGetByBlackList() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByBlackList", new ArrayList<BlackListEntry>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetValuesByBlackListName() throws Exception
	{
		MethodCall managerCall = new MethodCall("getValuesByBlackListName", new ArrayList<String>(), "listname");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testAddValuesToBlackList() throws Exception
	{
		final String blackListName = "name";
		
		BlackList blackList = blackListManager.getByName(blackListName);
		
		final List<String> valuesExisting = Arrays.asList(new String[] {
				"bad",
				"evil"
		});
		final List<String> valuesToAdd = Arrays.asList(new String[] {
				"very bad",
				"worse",
				"blub"
		});
		
		// value0 should not be added, since it is already excluded via "bad"
		
		final BlackListEntry e1 = new BlackListEntry();
		e1.setBlackList(blackList);
		e1.setValue(valuesToAdd.get(1));

		final BlackListEntry e2 = new BlackListEntry();
		e2.setBlackList(blackList);
		e2.setValue(valuesToAdd.get(2));
		
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getValuesByBlackListName(blackListName);
				will(returnValue(valuesExisting));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(e1);
				will(returnValue(e1));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(e2);
				will(returnValue(e2));
			}
		});
		
		int added = mockManager.addValuesToBlackList(blackListName, valuesToAdd);
		
		assertEquals(2, added);
		mockContext.assertIsSatisfied();
	}
}

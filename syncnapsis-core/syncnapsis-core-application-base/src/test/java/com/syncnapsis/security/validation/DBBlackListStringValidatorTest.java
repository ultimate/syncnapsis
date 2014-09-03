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
package com.syncnapsis.security.validation;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;

import com.syncnapsis.data.model.BlackList;
import com.syncnapsis.data.service.BlackListEntryManager;
import com.syncnapsis.data.service.BlackListManager;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({"setBlackList", "setStrict", "afterPropertiesSet"})
public class DBBlackListStringValidatorTest extends BaseSpringContextTestCase
{
	private BlackListManager blackListManager;
	private BlackListEntryManager blackListEntryManager;
	
	@TestCoversMethods({"*etBlackListManager", "*etBlackListEntryManager", "*etBlackListName"})
	public void testGetAndSet() throws Exception
	{
		DBBlackListStringValidator v = new DBBlackListStringValidator();
		getAndSetTest(v, "blackListManager", BlackListManager.class, blackListManager);
		getAndSetTest(v, "blackListEntryManager", BlackListEntryManager.class, blackListEntryManager);
		getAndSetTest(v, "blackListName", String.class, "aname");
	}
	
	@TestCoversMethods({"getBlackList", "isStrict"})
	public void testBlackList() throws Exception
	{
		final BlackListManager blackListManager = mockContext.mock(BlackListManager.class);
		final BlackListEntryManager blackListEntryManager = mockContext.mock(BlackListEntryManager.class);
		
		final String name = "alistname";
		final List<String> list = new ArrayList<String>();
		final boolean strict = true;
		
		final BlackList blackList = new BlackList();
		blackList.setName(name);
		blackList.setStrict(strict);
		
		DBBlackListStringValidator v = new DBBlackListStringValidator();
		v.setBlackListEntryManager(blackListEntryManager);
		v.setBlackListManager(blackListManager);
		v.setBlackListName(name);
	
		mockContext.checking(new Expectations() {
			{
				oneOf(blackListManager).getByName(name);
				will(returnValue(blackList));
			}
		});
		assertEquals(strict, v.isStrict());
		mockContext.assertIsSatisfied();
		
		mockContext.checking(new Expectations() {
			{
				oneOf(blackListEntryManager).getValuesByBlackListName(name);
				will(returnValue(list));
			}
		});
		assertEquals(list, v.getBlackList());
		mockContext.assertIsSatisfied();
	}
}

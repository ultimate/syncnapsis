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
import java.util.LinkedList;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.dao.EmpireDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRole;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.EmpireManager;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestCoversClasses({ EmpireManager.class, EmpireManagerImpl.class })
@TestExcludesMethods({ "*etSecurityManager", "afterPropertiesSet" })
public class EmpireManagerImplTest extends GenericNameManagerImplTestCase<Empire, Long, EmpireManager, EmpireDao>
{
	private EmpireManager	empireManager;
	private BaseGameManager	securityManager;

	private long			referenceTime	= 1234;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Empire());
		setDaoClass(EmpireDao.class);
		setMockDao(mockContext.mock(EmpireDao.class));
		setMockManager(new EmpireManagerImpl(mockDao));

		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		securityManager.setTimeProvider(new MockTimeProvider(referenceTime));

		((EmpireManagerImpl) mockManager).setSecurityManager(securityManager);
	}

	public void testGetByPlayer() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByPlayer", new ArrayList<BaseObject<?>>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testIsNameValid() throws Exception
	{
		assertFalse(empireManager.isNameValid(null));
		assertTrue(empireManager.isNameValid("goodguy"));
		assertFalse(empireManager.isNameValid("badguy"));
	}

	public void testCreate() throws Exception
	{
		Player player = new Player();
		player.setId(-1L);
		player.setRole(new PlayerRole());
		player.getRole().setId(-1L);
		player.getRole().setMaxEmpires(1);
		player.setEmpires(new LinkedList<Empire>());
		
		securityManager.getSessionProvider().set(new MockHttpSession());
		securityManager.getPlayerProvider().set(player);
		
		final String fullName = "Test-Empire";
		final String shortName = "TE";
		final String description = "a descriptive text...";
		final String imageURL = "http://www.syncnapsis.com/img/test.png";
		final Date foundationDate = new Date(referenceTime);
		final Date dissolutionDate = null;
		final String primaryColor = "#AABBCC";
		final String secondaryColor = "#DDEEFF";
		
		final Empire empire = new Empire();
		empire.setActivated(true);
		empire.setDescription(description);
		empire.setDissolutionDate(dissolutionDate);
		empire.setFoundationDate(foundationDate);
		empire.setFullName(fullName);
		empire.setImageURL(imageURL);
		empire.setPlayer(player);
		empire.setPrimaryColor(primaryColor);
		empire.setSecondaryColor(secondaryColor);
		empire.setShortName(shortName);
		
		Empire result;
		
		// creating the first empire
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(empire);
				will(returnValue(empire));
			}
		});
		result = mockManager.create(fullName, shortName, description, imageURL, primaryColor, secondaryColor);
		mockContext.assertIsSatisfied();
		assertNotNull(result);
		assertEquals(empire, result);
		
		// add the empire to the players empire-list (in tests only)
		player.getEmpires().add(result);
		
		// creating a second empire (but max is 1)
		try
		{
			mockManager.create(fullName, shortName, description, imageURL, primaryColor, secondaryColor);
			fail("expected exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}
}

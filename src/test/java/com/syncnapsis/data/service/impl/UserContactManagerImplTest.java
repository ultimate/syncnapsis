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

import com.syncnapsis.data.dao.UserContactDao;
import com.syncnapsis.data.model.UserContact;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.UserContactManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.exceptions.UserContactExistsException;
import com.syncnapsis.exceptions.UserSelectionInvalidException;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { UserContactManager.class, UserContactManagerImpl.class })
public class UserContactManagerImplTest extends GenericManagerImplTestCase<UserContact, Long, UserContactManager, UserContactDao>
{
	private UserManager			userManager;

	private UserContactManager	userContactManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new UserContact());
		setDaoClass(UserContactDao.class);
		setMockDao(mockContext.mock(UserContactDao.class));
		setMockManager(new UserContactManagerImpl(mockDao, userManager));
	}

	public void testGetByUser() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByUser", new ArrayList<BaseObject<?>>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetUserContact() throws Exception
	{
		MethodCall managerCall = new MethodCall("getUserContact", new UserContact(), 1L, 2L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testAddUserContact() throws Exception
	{
		Long userId1 = userManager.getByName("user1").getId();
		Long userId2 = userManager.getByName("user2").getId();
		Long userId3 = userManager.getByName("admin").getId();
		
		try
		{
			// same user
			userContactManager.addUserContact(userId1, userId1);
			fail("expected exception not occurred");
		}
		catch(UserSelectionInvalidException e)
		{
			assertNotNull(e);
		}		
		try
		{
			// contact already exists
			userContactManager.addUserContact(userId1, userId2);
			fail("expected exception not occurred");
		}
		catch(UserContactExistsException e)
		{
			assertNotNull(e);
		}
		
		UserContact userContact = userContactManager.addUserContact(userId2, userId3);
		assertNotNull(userContact);
		assertEquals(userId2, userContact.getUser1().getId());
		assertEquals(userId3, userContact.getUser2().getId());
		
		userContactManager.remove(userContact);
		
		assertFalse(userContactManager.exists(userContact.getId()));
	}

	public void testApproveUserContact() throws Exception
	{
		UserContact userContact = userContactManager.addUserContact(userManager.getByName("admin").getId(), userManager.getByName("user1").getId());
		
		assertTrue(userContact.isApprovedByUser1());
		assertFalse(userContact.isApprovedByUser2());
		
		userContact = userContactManager.approveUserContact(userContact.getId());

		assertTrue(userContact.isApprovedByUser1());
		assertTrue(userContact.isApprovedByUser2());
		
		userContactManager.remove(userContact);
		
		assertFalse(userContactManager.exists(userContact.getId()));
	}
}

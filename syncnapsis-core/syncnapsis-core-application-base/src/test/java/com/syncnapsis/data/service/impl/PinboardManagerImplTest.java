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

import java.util.Date;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.dao.PinboardDao;
import com.syncnapsis.data.model.Pinboard;
import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.PinboardManager;
import com.syncnapsis.data.service.PinboardMessageManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.security.BaseApplicationManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestCoversClasses({ PinboardManager.class, PinboardManagerImpl.class })
@TestExcludesMethods({ "*etSecurityManager", "afterPropertiesSet" })
public class PinboardManagerImplTest extends GenericNameManagerImplTestCase<Pinboard, Long, PinboardManager, PinboardDao>
{
	private PinboardMessageManager	pinboardMessageManager;
	private SessionProvider			sessionProvider;
	private UserProvider			userProvider;
	private UserManager				userManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Pinboard());
		setDaoClass(PinboardDao.class);
		setMockDao(mockContext.mock(PinboardDao.class));
		setMockManager(new PinboardManagerImpl(mockDao, pinboardMessageManager));
	}

	public void testPostMessage() throws Exception
	{
		final PinboardMessageManager mockPinboardMessageManager = mockContext.mock(PinboardMessageManager.class);
		final BaseApplicationManager securityManager = new BaseApplicationManager();
		securityManager.setTimeProvider(new MockTimeProvider(123456));
		securityManager.setUserProvider(userProvider);
		
		User postingUser = userManager.getAll().get(0);
		User other = userManager.getAll().get(1);
		
		sessionProvider.set(new MockHttpSession());
		userProvider.set(postingUser);
		
		PinboardManagerImpl pinboardManagerImpl = new PinboardManagerImpl(mockDao, mockPinboardMessageManager);
		pinboardManagerImpl.setSecurityManager(securityManager);
		
		final Long pinboardId = 1L;
		final String title = "title";
		final String content = "content";
		final int messageId = 12;
		
		final Pinboard pinboard = new Pinboard(); // just a dummy
		pinboard.setId(pinboardId);
		
		final PinboardMessage message = new PinboardMessage();
		message.setActivated(true);
		message.setContent(content);
		message.setCreationDate(new Date(securityManager.getTimeProvider().get()));
		message.setCreator(postingUser);
		message.setMessageId(messageId+1);
		message.setPinboard(pinboard);	
		message.setTitle(title);
		
		// posting user is pinboard creator (locked and hidden make no difference)
		{
			pinboard.setCreator(postingUser);
			pinboard.setLocked(true);
			pinboard.setHidden(true);
			
			mockContext.checking(new Expectations() {
				{
					oneOf(mockPinboardMessageManager).save(message);
					will(returnValue(message));
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(mockPinboardMessageManager).getLatestMessageId(pinboardId);
					will(returnValue(messageId));
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(mockDao).get(pinboardId);
					will(returnValue(pinboard));
				}
			});
			
			PinboardMessage result = pinboardManagerImpl.postMessage(pinboardId, title, content);
			mockContext.assertIsSatisfied();
			assertEquals(result, message);
		}
		// posting user is NOT pinboard creator and pinboard is LOCKED
		{
			pinboard.setCreator(other);
			pinboard.setLocked(true);
			pinboard.setHidden(false);
			
			mockContext.checking(new Expectations() {
				{
					oneOf(mockDao).get(pinboardId);
					will(returnValue(pinboard));
				}
			});
			
			PinboardMessage result = pinboardManagerImpl.postMessage(pinboardId, title, content);
			mockContext.assertIsSatisfied();
			assertNull(result);
		}
		// posting user is NOT pinboard creator and pinboard is HIDDEN
		{
			pinboard.setCreator(other);
			pinboard.setLocked(false);
			pinboard.setHidden(true);
			
			mockContext.checking(new Expectations() {
				{
					oneOf(mockDao).get(pinboardId);
					will(returnValue(pinboard));
				}
			});
			
			PinboardMessage result = pinboardManagerImpl.postMessage(pinboardId, title, content);
			mockContext.assertIsSatisfied();
			assertNull(result);
		}
		
		// board is neither locked nor hidden
		{
			pinboard.setCreator(other);
			pinboard.setLocked(false);
			pinboard.setHidden(false);
			
			mockContext.checking(new Expectations() {
				{
					oneOf(mockPinboardMessageManager).save(message);
					will(returnValue(message));
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(mockPinboardMessageManager).getLatestMessageId(pinboardId);
					will(returnValue(messageId));
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(mockDao).get(pinboardId);
					will(returnValue(pinboard));
				}
			});
			
			PinboardMessage result = pinboardManagerImpl.postMessage(pinboardId, title, content);
			mockContext.assertIsSatisfied();
			assertEquals(result, message);
		}
		
	}
	
	public void testCheckPostPermission() throws Exception
	{
		User creator = userManager.getAll().get(0);
		User other = userManager.getAll().get(1);

		Pinboard pinboard = new Pinboard();
		pinboard.setCreator(creator);
		
		// check different cases for creator
		{
			pinboard.setHidden(false);
			pinboard.setLocked(false);
			assertTrue(mockManager.checkPostPermission(pinboard, creator));

			pinboard.setHidden(false);
			pinboard.setLocked(true);
			assertTrue(mockManager.checkPostPermission(pinboard, creator));
			
			pinboard.setHidden(true);
			pinboard.setLocked(false);
			assertTrue(mockManager.checkPostPermission(pinboard, creator));
			
			pinboard.setHidden(true);
			pinboard.setLocked(true);
			assertTrue(mockManager.checkPostPermission(pinboard, creator));
		}
		
		// check different cases for other user
		{
			pinboard.setHidden(false);
			pinboard.setLocked(false);
			assertTrue(mockManager.checkPostPermission(pinboard, other));
			
			pinboard.setHidden(false);
			pinboard.setLocked(true);
			assertFalse(mockManager.checkPostPermission(pinboard, other));
			
			pinboard.setHidden(true);
			pinboard.setLocked(false);
			assertFalse(mockManager.checkPostPermission(pinboard, other));
			
			pinboard.setHidden(true);
			pinboard.setLocked(true);
			assertFalse(mockManager.checkPostPermission(pinboard, other));
		}
		
		// check different cases for no user
		{
			pinboard.setHidden(false);
			pinboard.setLocked(false);
			assertTrue(mockManager.checkPostPermission(pinboard, null));
			
			pinboard.setHidden(false);
			pinboard.setLocked(true);
			assertFalse(mockManager.checkPostPermission(pinboard, null));
			
			pinboard.setHidden(true);
			pinboard.setLocked(false);
			assertFalse(mockManager.checkPostPermission(pinboard, null));
			
			pinboard.setHidden(true);
			pinboard.setLocked(true);
			assertFalse(mockManager.checkPostPermission(pinboard, null));
		}
	}
	
	public void testCheckReadPermission() throws Exception
	{
		User creator = userManager.getAll().get(0);
		User other = userManager.getAll().get(1);
		
		Pinboard pinboard = new Pinboard();
		pinboard.setCreator(creator);
		
		// check different cases for creator
		{
			pinboard.setHidden(false);
			pinboard.setLocked(false);
			assertTrue(mockManager.checkReadPermission(pinboard, creator));
			
			pinboard.setHidden(false);
			pinboard.setLocked(true);
			assertTrue(mockManager.checkReadPermission(pinboard, creator));
			
			pinboard.setHidden(true);
			pinboard.setLocked(false);
			assertTrue(mockManager.checkReadPermission(pinboard, creator));
			
			pinboard.setHidden(true);
			pinboard.setLocked(true);
			assertTrue(mockManager.checkReadPermission(pinboard, creator));
		}
		
		// check different cases for other user
		{
			pinboard.setHidden(false);
			pinboard.setLocked(false);
			assertTrue(mockManager.checkReadPermission(pinboard, other));
			
			pinboard.setHidden(false);
			pinboard.setLocked(true);
			assertTrue(mockManager.checkReadPermission(pinboard, other));
			
			pinboard.setHidden(true);
			pinboard.setLocked(false);
			assertFalse(mockManager.checkReadPermission(pinboard, other));
			
			pinboard.setHidden(true);
			pinboard.setLocked(true);
			assertFalse(mockManager.checkReadPermission(pinboard, other));
		}
		
		// check different cases for no user
		{
			pinboard.setHidden(false);
			pinboard.setLocked(false);
			assertTrue(mockManager.checkReadPermission(pinboard, null));
			
			pinboard.setHidden(false);
			pinboard.setLocked(true);
			assertTrue(mockManager.checkReadPermission(pinboard, null));
			
			pinboard.setHidden(true);
			pinboard.setLocked(false);
			assertFalse(mockManager.checkReadPermission(pinboard, null));
			
			pinboard.setHidden(true);
			pinboard.setLocked(true);
			assertFalse(mockManager.checkReadPermission(pinboard, null));
		}
	}
}

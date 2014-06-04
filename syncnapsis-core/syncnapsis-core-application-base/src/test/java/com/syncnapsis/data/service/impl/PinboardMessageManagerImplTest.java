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
import java.util.List;

import org.jmock.Expectations;

import com.syncnapsis.data.dao.PinboardMessageDao;
import com.syncnapsis.data.model.Pinboard;
import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.PinboardMessageManager;
import com.syncnapsis.security.BaseApplicationManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({ PinboardMessageManager.class, PinboardMessageManagerImpl.class })
public class PinboardMessageManagerImplTest extends GenericManagerImplTestCase<PinboardMessage, Long, PinboardMessageManager, PinboardMessageDao>
{
	private BaseApplicationManager	securityManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new PinboardMessage());
		setDaoClass(PinboardMessageDao.class);
		setMockDao(mockContext.mock(PinboardMessageDao.class));
		setMockManager(new PinboardMessageManagerImpl(mockDao));
	}

	@TestCoversMethods("getByPinboard")
	public void testGetByPinboard_id() throws Exception
	{
		final User current = new User();
		current.setId(123L);
		final User other = new User();
		other.setId(456L);
		
		securityManager.getUserProvider().set(current);
		((PinboardMessageManagerImpl) mockManager).setSecurityManager(securityManager);

		final long pinboardId = 1L;

		final Pinboard pinboard = new Pinboard();
		pinboard.setId(pinboardId);

		final ArrayList<PinboardMessage> messages = new ArrayList<PinboardMessage>();

		mockContext.checking(new Expectations() {
			{
				allowing(mockDao).getByPinboard(pinboardId);
				will(returnValue(messages));
			}
		});

		List<PinboardMessage> result;

		// no messages (other + hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(true);

			result = mockManager.getByPinboard(pinboardId);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		// no messages (current + hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(true);
			result = mockManager.getByPinboard(pinboardId);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		// no messages (other + not hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		// no messages (current + not hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		
		PinboardMessage message = new PinboardMessage();
		message.setPinboard(pinboard);
		messages.add(message);

		// with messages (other + hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(true);
			result = mockManager.getByPinboard(pinboardId);
			assertNotNull(result);
			assertEquals(0, result.size());
			assertNotSame(messages, result);
		}
		// with messages (current + hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(true);
			result = mockManager.getByPinboard(pinboardId);
			assertNotNull(result);
			assertEquals(messages.size(), result.size());
			assertSame(messages, result);
		}
		// with messages (other + not hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId);
			assertNotNull(result);
			assertEquals(messages.size(), result.size());
			assertSame(messages, result);
		}
		// with messages (current + not hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId);
			assertNotNull(result);
			assertEquals(messages.size(), result.size());
			assertSame(messages, result);
		}
	}

	@TestCoversMethods("getByPinboard")
	public void testGetByPinboard_id_count() throws Exception
	{
		final User current = new User();
		current.setId(123L);
		final User other = new User();
		other.setId(456L);
		
		securityManager.getUserProvider().set(current);
		((PinboardMessageManagerImpl) mockManager).setSecurityManager(securityManager);
	
		final long pinboardId = 1L;
		final int count = 1;
	
		final Pinboard pinboard = new Pinboard();
		pinboard.setId(pinboardId);
	
		final ArrayList<PinboardMessage> messages = new ArrayList<PinboardMessage>();
	
		mockContext.checking(new Expectations() {
			{
				allowing(mockDao).getByPinboard(pinboardId, count);
				will(returnValue(messages));
			}
		});
	
		List<PinboardMessage> result;
	
		// no messages (other + hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(true);
	
			result = mockManager.getByPinboard(pinboardId, count);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		// no messages (current + hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(true);
			result = mockManager.getByPinboard(pinboardId, count);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		// no messages (other + not hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId, count);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		// no messages (current + not hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId, count);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		
		PinboardMessage message = new PinboardMessage();
		message.setPinboard(pinboard);
		messages.add(message);
	
		// with messages (other + hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(true);
			result = mockManager.getByPinboard(pinboardId, count);
			assertNotNull(result);
			assertEquals(0, result.size());
			assertNotSame(messages, result);
		}
		// with messages (current + hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(true);
			result = mockManager.getByPinboard(pinboardId, count);
			assertNotNull(result);
			assertEquals(messages.size(), result.size());
			assertSame(messages, result);
		}
		// with messages (other + not hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId, count);
			assertNotNull(result);
			assertEquals(messages.size(), result.size());
			assertSame(messages, result);
		}
		// with messages (current + not hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId, count);
			assertNotNull(result);
			assertEquals(messages.size(), result.size());
			assertSame(messages, result);
		}
	}

	@TestCoversMethods("getByPinboard")
	public void testGetByPinboard_id_from_to() throws Exception
	{
		final User current = new User();
		current.setId(123L);
		final User other = new User();
		other.setId(456L);
		
		securityManager.getUserProvider().set(current);
		((PinboardMessageManagerImpl) mockManager).setSecurityManager(securityManager);
	
		final long pinboardId = 1L;
		final int from = 1;
		final int to = 2;
	
		final Pinboard pinboard = new Pinboard();
		pinboard.setId(pinboardId);
	
		final ArrayList<PinboardMessage> messages = new ArrayList<PinboardMessage>();
	
		mockContext.checking(new Expectations() {
			{
				allowing(mockDao).getByPinboard(pinboardId, from, to);
				will(returnValue(messages));
			}
		});
	
		List<PinboardMessage> result;
	
		// no messages (other + hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(true);
	
			result = mockManager.getByPinboard(pinboardId, from, to);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		// no messages (current + hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(true);
			result = mockManager.getByPinboard(pinboardId, from, to);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		// no messages (other + not hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId, from, to);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		// no messages (current + not hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId, from, to);
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		
		PinboardMessage message = new PinboardMessage();
		message.setPinboard(pinboard);
		messages.add(message);
	
		// with messages (other + hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(true);
			result = mockManager.getByPinboard(pinboardId, from, to);
			assertNotNull(result);
			assertEquals(0, result.size());
			assertNotSame(messages, result);
		}
		// with messages (current + hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(true);
			result = mockManager.getByPinboard(pinboardId, from, to);
			assertNotNull(result);
			assertEquals(messages.size(), result.size());
			assertSame(messages, result);
		}
		// with messages (other + not hidden)
		{
			pinboard.setCreator(other);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId, from, to);
			assertNotNull(result);
			assertEquals(messages.size(), result.size());
			assertSame(messages, result);
		}
		// with messages (current + not hidden)
		{
			pinboard.setCreator(current);
			pinboard.setHidden(false);
			result = mockManager.getByPinboard(pinboardId, from, to);
			assertNotNull(result);
			assertEquals(messages.size(), result.size());
			assertSame(messages, result);
		}
	}

	public void testGetLatestMessageId() throws Exception
	{
		MethodCall managerCall = new MethodCall("getLatestMessageId", 3, 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}

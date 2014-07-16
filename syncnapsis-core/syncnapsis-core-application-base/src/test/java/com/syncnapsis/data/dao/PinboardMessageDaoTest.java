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
package com.syncnapsis.data.dao;

import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.data.dao.hibernate.PinboardMessageDaoHibernate;
import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ PinboardMessageDao.class, PinboardMessageDaoHibernate.class })
public class PinboardMessageDaoTest extends GenericDaoTestCase<PinboardMessage, Long>
{
	private PinboardMessageDao	pinboardMessageDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = pinboardMessageDao.getAll().get(0).getId();

		PinboardMessage pinboardMessage = new PinboardMessage();
		// set individual properties here

		setEntity(pinboardMessage);

		setEntityProperty("content");
		setEntityPropertyValue("the message content...");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(pinboardMessageDao);
	}

	public void testGetByPinboard() throws Exception
	{
		List<PinboardMessage> allMessages = pinboardMessageDao.getAll();

		List<Long> pinboards = new ArrayList<Long>();
		for(PinboardMessage m : allMessages)
		{
			if(!pinboards.contains(m.getPinboard().getId()))
				pinboards.add(m.getPinboard().getId());
		}

		assertTrue(pinboards.size() > 0);

		List<PinboardMessage> boardMessages;
		List<PinboardMessage> someBoardMessages;
		final int count = 2;
		int totalMessages = 0;
		int fromMessageId, toMessageId;

		for(Long pinboardId : pinboards)
		{
			// all board messages - getByPinboard(pinboardId)
			boardMessages = pinboardMessageDao.getByPinboard(pinboardId);
			totalMessages += boardMessages.size();

			for(int i = 1; i < boardMessages.size(); i++)
			{
				// check sort order
				assertEquals(boardMessages.get(i).getMessageId(), boardMessages.get(i - 1).getMessageId() - 1);
				if(i == boardMessages.size())
					assertEquals(1, boardMessages.get(i).getMessageId());
			}

			assertTrue(boardMessages.size() < allMessages.size());

			toMessageId = boardMessages.get(0).getMessageId();
			fromMessageId = boardMessages.get(boardMessages.size() - 1).getMessageId();

			// some board messages - getByPinboard(pinboardId, count)
			someBoardMessages = pinboardMessageDao.getByPinboard(pinboardId, count);

			if(boardMessages.size() > count)
				assertEquals(count, someBoardMessages.size());
			else
				assertEquals(boardMessages.size(), someBoardMessages.size());

			// selected board messages - getByPinboard(pinboardId, fromMessageId, toMessageId)
			someBoardMessages = pinboardMessageDao.getByPinboard(pinboardId, fromMessageId + 1, toMessageId - 1);

			assertEquals(boardMessages.size() - 2, someBoardMessages.size());
			if(someBoardMessages.size() > 0)
			{
				assertEquals(toMessageId - 1, someBoardMessages.get(0).getMessageId());
				assertEquals(fromMessageId + 1, someBoardMessages.get(someBoardMessages.size() - 1).getMessageId());
			}
		}

		assertEquals(allMessages.size(), totalMessages);
	}

	public void testGetLatestMessageId() throws Exception
	{
		// valid pinboard
		long pinboardId = 1L;

		List<PinboardMessage> messages = pinboardMessageDao.getByPinboard(pinboardId);
		int latestMessageId = messages.get(0).getMessageId(); // first message is the newest message

		assertEquals(latestMessageId, pinboardMessageDao.getLatestMessageId(pinboardId));

		// invalid pinboard
		assertEquals(0, pinboardMessageDao.getLatestMessageId(987L));
	}

	// insert individual Tests here
}

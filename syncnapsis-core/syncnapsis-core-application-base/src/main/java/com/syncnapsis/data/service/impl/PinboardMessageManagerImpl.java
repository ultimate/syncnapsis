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

import java.util.List;

import com.syncnapsis.data.dao.PinboardMessageDao;
import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.data.service.PinboardMessageManager;

/**
 * Manager-Implementation for access to PinboardMessage.
 * 
 * @author ultimate
 */
public class PinboardMessageManagerImpl extends GenericManagerImpl<PinboardMessage, Long> implements PinboardMessageManager
{
	/**
	 * PinboardMessageDao for database access
	 */
	protected PinboardMessageDao	pinboardMessageDao;

	/**
	 * Standard Constructor
	 * 
	 * @param pinboardMessageDao - PinboardMessageDao for database access
	 */
	public PinboardMessageManagerImpl(PinboardMessageDao pinboardMessageDao)
	{
		super(pinboardMessageDao);
		this.pinboardMessageDao = pinboardMessageDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PinboardMessageManager#getByPinboard(java.lang.Long)
	 */
	@Override
	public List<PinboardMessage> getByPinboard(Long pinboardId)
	{
		return pinboardMessageDao.getByPinboard(pinboardId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PinboardMessageManager#getByPinboard(java.lang.Long, int)
	 */
	@Override
	public List<PinboardMessage> getByPinboard(Long pinboardId, int count)
	{
		return pinboardMessageDao.getByPinboard(pinboardId, count);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PinboardMessageManager#getByPinboard(java.lang.Long, int, int)
	 */
	@Override
	public List<PinboardMessage> getByPinboard(Long pinboardId, int fromMessageId, int toMessageId)
	{
		return pinboardMessageDao.getByPinboard(pinboardId, fromMessageId, toMessageId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PinboardMessageManager#getLatestMessageId(java.lang.Long)
	 */
	@Override
	public int getLatestMessageId(Long pinboardId)
	{
		return pinboardMessageDao.getLatestMessageId(pinboardId);
	}
}

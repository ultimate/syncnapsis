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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.dao.PinboardMessageDao;
import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.PinboardMessageManager;
import com.syncnapsis.security.BaseApplicationManager;

/**
 * Manager-Implementation for access to PinboardMessage.
 * 
 * @author ultimate
 */
public class PinboardMessageManagerImpl extends GenericManagerImpl<PinboardMessage, Long> implements PinboardMessageManager, InitializingBean
{
	/**
	 * PinboardMessageDao for database access
	 */
	protected PinboardMessageDao		pinboardMessageDao;

	/**
	 * The SecurityManager (BaseApplicationManager)
	 */
	protected BaseApplicationManager	securityManager;

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
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(securityManager, "securityManager must not be null!");
	}

	/**
	 * The SecurityManager (BaseApplicationManager)
	 * 
	 * @return securityManager
	 */
	public BaseApplicationManager getSecurityManager()
	{
		return securityManager;
	}

	/**
	 * The SecurityManager (BaseApplicationManager)
	 * 
	 * @param securityManager - the SecurityManager
	 */
	public void setSecurityManager(BaseApplicationManager securityManager)
	{
		this.securityManager = securityManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PinboardMessageManager#getByPinboard(java.lang.Long)
	 */
	@Override
	public List<PinboardMessage> getByPinboard(Long pinboardId)
	{
		List<PinboardMessage> messages = pinboardMessageDao.getByPinboard(pinboardId);
		// check for hidden pinboard
		if(messages.size() > 0 && messages.get(0).getPinboard().isHidden())
		{
			// check for the current user
			User creator = messages.get(0).getPinboard().getCreator();
			User current = securityManager.getUserProvider().get();
			if(current == null || !creator.getId().equals(current.getId()))
				return new ArrayList<PinboardMessage>(0);
		}
		return messages;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PinboardMessageManager#getByPinboard(java.lang.Long, int)
	 */
	@Override
	public List<PinboardMessage> getByPinboard(Long pinboardId, int count)
	{
		List<PinboardMessage> messages = pinboardMessageDao.getByPinboard(pinboardId, count);
		// check for hidden pinboard
		if(messages.size() > 0 && messages.get(0).getPinboard().isHidden())
		{
			// check for the current user
			User creator = messages.get(0).getPinboard().getCreator();
			User current = securityManager.getUserProvider().get();
			if(current == null || !creator.getId().equals(current.getId()))
				return new ArrayList<PinboardMessage>(0);
		}
		return messages;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PinboardMessageManager#getByPinboard(java.lang.Long, int,
	 * int)
	 */
	@Override
	public List<PinboardMessage> getByPinboard(Long pinboardId, int fromMessageId, int toMessageId)
	{
		List<PinboardMessage> messages = pinboardMessageDao.getByPinboard(pinboardId, fromMessageId, toMessageId);
		// check for hidden pinboard
		if(messages.size() > 0 && messages.get(0).getPinboard().isHidden())
		{
			// check for the current user
			User creator = messages.get(0).getPinboard().getCreator();
			User current = securityManager.getUserProvider().get();
			if(current == null || !creator.getId().equals(current.getId()))
				return new ArrayList<PinboardMessage>(0);
		}
		return messages;
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

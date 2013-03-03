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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.dao.PinboardDao;
import com.syncnapsis.data.model.Pinboard;
import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.PinboardManager;
import com.syncnapsis.data.service.PinboardMessageManager;
import com.syncnapsis.security.BaseApplicationManager;

/**
 * Manager-Implementation for access to Pinboard.
 * 
 * @author ultimate
 */
public class PinboardManagerImpl extends GenericNameManagerImpl<Pinboard, Long> implements PinboardManager, InitializingBean
{
	/**
	 * PinboardDao for database access
	 */
	protected PinboardDao				pinboardDao;

	/**
	 * The PinboardMessageManager
	 */
	protected PinboardMessageManager	pinboardMessageManager;

	/**
	 * The SecurityManager (BaseApplicationManager)
	 */
	protected BaseApplicationManager	securityManager;

	/**
	 * Standard Constructor
	 * 
	 * @param pinboardDao - PinboardDao for database access
	 * @param pinboardMessageManager - the PinboardMessageManager
	 */
	public PinboardManagerImpl(PinboardDao pinboardDao, PinboardMessageManager pinboardMessageManager)
	{
		super(pinboardDao);
		this.pinboardDao = pinboardDao;
		this.pinboardMessageManager = pinboardMessageManager;
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
	 * @see com.syncnapsis.data.service.PinboardManager#postMessage(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public PinboardMessage postMessage(Long boardId, String title, String message)
	{
		// get additional post information
		Pinboard pinboard = get(boardId);
		User creator = securityManager.getUserProvider().get();
		
		// create and save the message
		PinboardMessage pinboardMessage = new PinboardMessage();
		pinboardMessage.setActivated(true);
		pinboardMessage.setContent(message);
		pinboardMessage.setCreationDate(new Date(securityManager.getTimeProvider().get()));
		pinboardMessage.setCreator(creator);
		pinboardMessage.setPinboard(pinboard);
		pinboardMessage.setTitle(title);
		
		return pinboardMessageManager.save(pinboardMessage);
	}
}

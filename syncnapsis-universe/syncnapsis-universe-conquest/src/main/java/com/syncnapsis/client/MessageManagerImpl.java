/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.Assert;

import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.data.service.PinboardManager;
import com.syncnapsis.data.service.PinboardMessageManager;
import com.syncnapsis.websockets.Connection;

/**
 * {@link MessageManager} implementation
 * 
 * @author ultimate
 */
public class MessageManagerImpl extends BaseClientManager implements MessageManager
{
	/**
	 * The PinboardManager used to access Pinboard related data
	 */
	protected PinboardManager			pinboardManager;
	/**
	 * The PinboardMessageManager used to access PinboardMessage related data
	 */
	protected PinboardMessageManager	pinboardMessageManager;

	/**
	 * Default Constructor
	 */
	public MessageManagerImpl()
	{
		super();
	}

	/**
	 * The PinboardManager used to access pinboard related data
	 * 
	 * @return pinboardManager
	 */
	public PinboardManager getPinboardManager()
	{
		return pinboardManager;
	}

	/**
	 * The PinboardManager used to access pinboard related data
	 * 
	 * @param pinboardManager - the PinboardManager
	 */
	public void setPinboardManager(PinboardManager pinboardManager)
	{
		this.pinboardManager = pinboardManager;
	}

	/**
	 * The PinboardMessageManager used to access pinboardMessage related data
	 * 
	 * @return pinboardMessageManager
	 */
	public PinboardMessageManager getPinboardMessageManager()
	{
		return pinboardMessageManager;
	}

	/**
	 * The PinboardMessageManager used to access pinboardMessage related data
	 * 
	 * @param pinboardMessageManager - the PinboardMessageManager
	 */
	public void setPinboardMessageManager(PinboardMessageManager pinboardMessageManager)
	{
		this.pinboardMessageManager = pinboardMessageManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.BaseClientManager#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(pinboardManager, "pinboardManager must not be null!");
		Assert.notNull(pinboardMessageManager, "pinboardMessageManager must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.MessageManager#postPinboardMessage(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void postPinboardMessage(Long pinboardId, String title, String message)
	{
		PinboardMessage pinboardMessage = pinboardManager.postMessage(pinboardId, title, message);
		
		if(pinboardMessage == null)
			return; // no post permission

		List<PinboardMessage> messages = new ArrayList<PinboardMessage>(1);
		messages.add(pinboardMessage);

		Collection<Connection> connections = getConnections();

		for(Connection connection : connections)
		{
			// check read permission
			if(!pinboardManager.checkReadPermission(pinboardMessage.getPinboard(), getConnectionUser(connection)))
				continue; // no read permission
			((MessageManager) getClientInstance(getBeanName(), connection)).updatePinboard(pinboardId, messages);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.MessageManager#updatePinboard(java.lang.Long, java.util.List)
	 */
	@Override
	public void updatePinboard(Long pinboardId, List<PinboardMessage> messages)
	{
		// nothing to do on the server
		// this is the stub for client method invocation
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.MessageManager#requestPinboardUpdate(java.lang.Long, int)
	 */
	@Override
	public void requestPinboardUpdate(Long pinboardId, int messageCount)
	{
		List<PinboardMessage> messages = pinboardMessageManager.getByPinboard(pinboardId, messageCount);
		((MessageManager) getClientInstance(getBeanName(), getConnectionProvider().get())).updatePinboard(pinboardId, messages);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.MessageManager#requestPinboardUpdate(java.lang.Long, int, int)
	 */
	@Override
	public void requestPinboardUpdate(Long pinboardId, int fromMessageId, int toMessageId)
	{
		List<PinboardMessage> messages = pinboardMessageManager.getByPinboard(pinboardId, fromMessageId, toMessageId);
		((MessageManager) getClientInstance(getBeanName(), getConnectionProvider().get())).updatePinboard(pinboardId, messages);
	}
}

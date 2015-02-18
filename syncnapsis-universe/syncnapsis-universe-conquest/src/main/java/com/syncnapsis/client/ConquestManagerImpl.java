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
package com.syncnapsis.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.Assert;

import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.websockets.Connection;

/**
 * {@link ConquestManager} implementation
 * 
 * @author ultimate
 */
public class ConquestManagerImpl extends BaseClientManager implements ConquestManager
{
	/**
	 * The MatchManager
	 */
	protected MatchManager			matchManager;

	protected Map<String, Channel>	channels;

	/**
	 * Default constructor
	 */
	public ConquestManagerImpl()
	{
		super();
		channels = new TreeMap<String, Channel>();
	}

	/**
	 * The MatchManager
	 * 
	 * @return matchManager
	 */
	public MatchManager getMatchManager()
	{
		return matchManager;
	}

	/**
	 * The MatchManager
	 * 
	 * @param matchManager - the MatchManager
	 */
	public void setMatchManager(MatchManager matchManager)
	{
		this.matchManager = matchManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.BaseClientManager#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(matchManager, "matchManager must not be null!");
	}

	@Override
	public void subscribe(String channel)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void unsubscribe(String channel)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUnderSubscription(String channel)
	{
		// ((MessageManager) getClientInstance(getBeanName(),
		// getConnectionProvider().get())).updatePinboard(pinboardId, messages);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getSubscribedChannels()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getLastValue(String channel)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#update(java.lang.String, java.lang.Object)
	 */
	@Override
	public void update(String channel, Object value)
	{
		if(!hasChannel(channel))
			throw new IllegalArgumentException("no such channel!");
		
		Channel c = channels.get(channel);
		
		for(Connection con: c.getSubscriptions())
			pushUpdate(c, con);
	}
	
	protected void pushUpdate(Channel channel, Connection connection)
	{
		// TODO
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#getChannels()
	 */
	@Override
	public Collection<String> getChannels()
	{
		return channels.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#createChannel(java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean createChannel(String channel, Object initialValue)
	{
		if(hasChannel(channel))
			return false;

		channels.put(channel, new Channel(channel, initialValue));
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#hasChannel(java.lang.String)
	 */
	@Override
	public boolean hasChannel(String channel)
	{
		return channels.containsKey(channel);
	}

	/**
	 * POJO representing a subscription channel
	 * 
	 * @author ultimate
	 */
	protected class Channel
	{
		/**
		 * The name of the channel
		 */
		private String				name;
		/**
		 * The current value for the channel
		 */
		private Object				value;
		/**
		 * The list of subscribed connections for this channel
		 */
		private List<Connection>	subscriptions;

		/**
		 * Create a new channel with the given name and initial value
		 * 
		 * @param name - the name of the channel
		 * @param initialValue - the initial value for the channel
		 */
		public Channel(String name, Object initialValue)
		{
			this.name = name;
			this.value = initialValue;
			this.subscriptions = new ArrayList<Connection>();
		}

		/**
		 * The name of the channel
		 * 
		 * @return name
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * The current value for the channel
		 * 
		 * @return value
		 */
		public Object getValue()
		{
			return value;
		}

		/**
		 * The list of subscribed connections for this channel
		 * 
		 * @return subscriptions
		 */
		public List<Connection> getSubscriptions()
		{
			return subscriptions;
		}
	}
}

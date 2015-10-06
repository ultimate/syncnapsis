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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.util.Assert;

import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.help.Order;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.websockets.Connection;

/**
 * {@link ConquestManager} implementation
 * 
 * @author ultimate
 */
public class ConquestManagerImpl extends BaseClientManager implements ConquestManager
{
	/**
	 * The {@link SolarSystemPopulationManager}
	 */
	protected SolarSystemPopulationManager		solarSystemPopulationManager;
	/**
	 * The {@link SolarSystemInfrastructureManager}
	 */
	protected SolarSystemInfrastructureManager	solarSystemInfrastructureManager;

	/**
	 * The internal list of {@link Channel}s ordered by their channel name
	 */
	protected Map<String, Channel>				channels;

	/**
	 * Default constructor
	 */
	public ConquestManagerImpl()
	{
		super();
		channels = new TreeMap<String, Channel>();
	}

	/**
	 * The {@link SolarSystemPopulationManager}
	 * 
	 * @return solarSystemPopulationManager
	 */
	public SolarSystemPopulationManager getSolarSystemPopulationManager()
	{
		return solarSystemPopulationManager;
	}

	/**
	 * The {@link SolarSystemPopulationManager}
	 * 
	 * @param solarSystemPopulationManager - the manager
	 */
	public void setSolarSystemPopulationManager(SolarSystemPopulationManager solarSystemPopulationManager)
	{
		this.solarSystemPopulationManager = solarSystemPopulationManager;
	}

	/**
	 * The {@link SolarSystemInfrastructureManager}
	 * 
	 * @return solarSystemInfrastructureManager
	 */
	public SolarSystemInfrastructureManager getSolarSystemInfrastructureManager()
	{
		return solarSystemInfrastructureManager;
	}

	/**
	 * The {@link SolarSystemInfrastructureManager}
	 * 
	 * @param solarSystemInfrastructureManager - the manager
	 */
	public void setSolarSystemInfrastructureManager(SolarSystemInfrastructureManager solarSystemInfrastructureManager)
	{
		this.solarSystemInfrastructureManager = solarSystemInfrastructureManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.BaseClientManager#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet()
	{
		Assert.notNull(solarSystemPopulationManager, "solarSystemPopulationManager must not be null!");
		Assert.notNull(solarSystemInfrastructureManager, "solarSystemInfrastructureManager must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#subscribe(java.lang.String)
	 */
	@Override
	public boolean subscribe(String channel)
	{
		Connection con = getConnectionProvider().get();
		Channel c = channels.get(channel);
		if(c == null)
			return false;
		synchronized(c)
		{
			if(!c.getSubscriptions().contains(con))
			{
				try
				{
					pushUpdate(c, con);
				}
				catch(IOException e)
				{
					logger.error("could not push update to connection " + con.getId() + ". Maybe it was closed in the meantime.", e);
				}
				return c.getSubscriptions().add(con);
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#unsubscribe(java.lang.String)
	 */
	@Override
	public boolean unsubscribe(String channel)
	{
		Connection con = getConnectionProvider().get();
		Channel c = channels.get(channel);
		if(c == null)
			return false;
		synchronized(c)
		{
			if(c.getSubscriptions().contains(con))
				return c.getSubscriptions().remove(con);
			else
				return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#isUnderSubscription(java.lang.String)
	 */
	@Override
	public boolean isUnderSubscription(String channel)
	{
		Connection con = getConnectionProvider().get();
		Channel c = channels.get(channel);
		if(c == null)
			return false;
		synchronized(c)
		{
			return c.getSubscriptions().contains(con);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#getSubscribedChannels()
	 */
	@Override
	public List<String> getSubscribedChannels()
	{
		Connection con = connectionProvider.get();
		List<String> channelNames = new ArrayList<String>(channels.size());
		for(Entry<String, Channel> e : channels.entrySet())
		{
			if(e.getValue().getSubscriptions().contains(con))
				channelNames.add(e.getKey());
		}
		return channelNames;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#getLastValue(java.lang.String)
	 */
	@Override
	public Object getLastValue(String channel)
	{
		Channel c = channels.get(channel);
		if(c == null)
			return null;
		return c.getValue();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#update(java.lang.String, java.lang.Object)
	 */
	@Override
	public void update(String channel, Object value)
	{
		Channel c = channels.get(channel);
		if(c == null)
			throw new IllegalArgumentException("no such channel!");
		synchronized(c)
		{
			c.setValue(value);

			List<Connection> invalidConnections = new LinkedList<Connection>();
			for(Connection con : c.getSubscriptions())
			{
				if(con.isOpen())
				{
					try
					{
						pushUpdate(c, con);
					}
					catch(IOException e)
					{
						logger.warn("could not push update to connection " + con.getId() + ". Maybe it was closed in the meantime.", e);
						invalidConnections.add(con);
					}
				}
				else
				{
					invalidConnections.add(con);
				}
			}
			c.getSubscriptions().removeAll(invalidConnections);
		}
	}

	/**
	 * Helper method that pushes an update for the given {@link Channel} to the given
	 * {@link Connection}
	 * 
	 * @param channel - the channel to push the update for
	 * @param connection - the connection to push the update to
	 * @throws IOException - if sending the update to the client fails
	 */
	protected void pushUpdate(Channel channel, Connection connection) throws IOException
	{
		((ConquestManager) getClientInstance(getBeanName(), connection)).update(channel.getName(), channel.getValue());
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#getChannels()
	 */
	@Override
	public Collection<String> getChannels()
	{
		synchronized(channels)
		{
			return channels.keySet();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#createChannel(java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean createChannel(String channel, Object initialValue)
	{
		synchronized(channels)
		{
			if(channels.containsKey(channel))
				return false;

			channels.put(channel, new Channel(channel, initialValue));
		}
		return true;
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
		 * The current value for the channel
		 * 
		 * @param value - the new current value
		 */
		public void setValue(Object value)
		{
			this.value = value;
		}

		/**
		 * The list of subscribed connections for this channel (as an unmodifiable copy)
		 * 
		 * @return subscriptions
		 */
		public List<Connection> getSubscriptions()
		{
			return subscriptions;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.ConquestManager#sendTroops(java.util.List)
	 */
	public int sendTroops(List<Order> orders)
	{
		int ordersPerformed = 0;
		SolarSystemPopulation origin;
		SolarSystemInfrastructure target;
		@SuppressWarnings("unused")
		SolarSystemPopulation result;
		for(Order o : orders)
		{
			origin = solarSystemPopulationManager.get(o.getOriginId());
			target = solarSystemInfrastructureManager.get(o.getTargetId());
			try
			{
				if(o.isExodus())
					result = solarSystemPopulationManager.resettle(origin, target, o.getTravelSpeed(), true, o.getAttackPriority(),
							o.getBuildPriority());
				else
					result = solarSystemPopulationManager.spinoff(origin, target, o.getTravelSpeed(), o.getPopulation(), o.getAttackPriority(),
							o.getBuildPriority());
				ordersPerformed++;
			}
			catch(IllegalArgumentException e)
			{
				logger.warn("invalid troop order: " + o);
			}
		}
		return ordersPerformed;
	}
}

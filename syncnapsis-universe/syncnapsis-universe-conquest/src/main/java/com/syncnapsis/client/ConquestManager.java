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

import java.util.Collection;
import java.util.List;

import com.syncnapsis.data.model.help.Order;

/**
 * Interface for a ConquestManager offering universe-conquest game related operations.<br>
 * This manager contains support for subscription service with purpose for live updating of the
 * clients in the context of the matches. It therefore provides functions for
 * subscribing/unsubscribing to channels and updating and pushing values for those channels.
 * 
 * @author ultimate
 */
public interface ConquestManager
{
	/**
	 * Subscribe the calling client to the given subscription channel.
	 * 
	 * @param channel - the name of the subscription channel
	 * @return true if subscribing was successful, false otherwise (e.g. if channel does not exist)
	 */
	public boolean subscribe(String channel);

	/**
	 * Subscribe the calling client from the given subscription channel.
	 * 
	 * @param channel - the name of the subscription channel
	 * @return true if unsubscribing was successful, false otherwise (e.g. if channel does not
	 *         exist)
	 */
	public boolean unsubscribe(String channel);

	/**
	 * Check whether the calling client has subscribed to the given channel.
	 * 
	 * @param channel - the name of the subscription channel
	 * @return true or false
	 */
	public boolean isUnderSubscription(String channel);

	/**
	 * Get the list of subscribed channels for the calling client.
	 * 
	 * @return the list of channel names
	 */
	public List<String> getSubscribedChannels();

	/**
	 * Get the last value for the given subscription channel.
	 * 
	 * @param channel - the name of the subscription channel
	 * @return the last value for the subscription channel
	 */
	public Object getLastValue(String channel);

	/**
	 * Push the given new value for the given subscription channel to all subscribed clients
	 * (server-instance) or to the connection of a specific client (client-instance)
	 * 
	 * @param channel - the name of the subscription channel
	 * @param value - the new value for the subscription channel
	 */
	public void update(String channel, Object value);

	/**
	 * Get the list of available channels
	 * 
	 * @return the list of channel names
	 */
	public Collection<String> getChannels();

	/**
	 * Create a new subscription channel with the given name and an initial value.
	 * 
	 * @param channel - the name of the subscription channel
	 * @param initialValue - the initial value for the subscription channel
	 * @return true if creating the channel was successful, false otherwise (e.g. if the channel
	 *         already exists)
	 */
	public boolean createChannel(String channel, Object initialValue);
	
	/**
	 * Simplified interface for sending multiple populations at once
	 * 
	 * @param orders - the list of {@link Order}s
	 * @return the number of successfully performed orders
	 */
	public int sendTroops(List<Order> orders);
}

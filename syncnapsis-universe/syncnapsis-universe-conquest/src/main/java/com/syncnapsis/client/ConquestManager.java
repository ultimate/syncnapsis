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

/**
 * Interface for a ConquestManager offering universe-conquest game related operations.
 * 
 * @author ultimate
 */
public interface ConquestManager
{
	/**
	 * Subscribe the calling client to the given subscription channel and argument.
	 * 
	 * @param channel - the name of the subscription channel
	 * @param argument - the argument for the subscription channel
	 */
	public void subscribe(String channel, Object argument);

	/**
	 * Subscribe the calling client from the given subscription channel and argument.
	 * 
	 * @param channel - the name of the subscription channel
	 * @param argument - the argument for the subscription channel
	 */
	public void unsubscribe(String channel, Object argument);

	/**
	 * Check whether the calling client has subscribed to the given channel and argument.
	 * 
	 * @param channel - the name of the subscription channel
	 * @param argument - the argument for the subscription channel
	 * @return true or false
	 */
	public boolean isUnderSubscription(String channel, Object argument);

	/**
	 * Get the last value for the subscription channel and the given argument.
	 * 
	 * @param channel - the name of the subscription channel
	 * @param argument - the argument for the subscription channel
	 * @return the last value for the subscription channel
	 */
	public Object getLastValue(String channel, Object argument);

	/**
	 * Push the given new value for the subscription channel and the given argument to all
	 * subscribed clients.
	 * 
	 * @param channel - the name of the subscription channel
	 * @param argument - the argument for the subscription channel
	 * @param value - the new value for the subscription channel
	 */
	public void update(String channel, Object argument, Object value);
}

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
package com.syncnapsis.data.service;

import com.syncnapsis.data.model.Pinboard;
import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.data.model.User;

/**
 * Manager-Interface for access to Pinboard.
 * 
 * @author ultimate
 */
public interface PinboardManager extends GenericNameManager<Pinboard, Long>
{
	/**
	 * Add a message to the pinboard given by id.<br>
	 * The user posting will be determined from the session and must have rights to post to the
	 * board either bei being its owner or because the pinboard is not locked.
	 * 
	 * @param boardId - the id of the pinboard to add the message to
	 * @param title - the title of the message (optional)
	 * @param message - the message content to add
	 * @return the message object created or null if the user is not allowed to post messages
	 */
	public PinboardMessage postMessage(Long pinboardId, String title, String message);

	/**
	 * Check whether the given user is allowed to post messages on the given pinboard
	 * 
	 * @param pinboard - the pinboard
	 * @param user - the user to check
	 * @return true or false
	 */
	public boolean checkPostPermission(Pinboard pinboard, User user);

	/**
	 * Check whether the given user is allowed to read messages on the given pinboard
	 * 
	 * @param pinboard - the pinboard
	 * @param user - the user to check
	 * @return true or false
	 */
	public boolean checkReadPermission(Pinboard pinboard, User user);
}

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

import java.util.List;

import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.data.service.PinboardManager;

/**
 * Interface for a MessageManager offering message related operations.
 * 
 * @author ultimate
 */
public interface MessageManager
{
	/**
	 * Add a message to the pinboard given by id.<br>
	 * 
	 * @see PinboardManager#postMessage(Long, String, String)
	 * @param boardId - the id of the pinboard to add the message to
	 * @param title - the title of the message (optional)
	 * @param message - the message content to add
	 */
	public void postPinboardMessage(Long pinboardId, String title, String message);

	/**
	 * Update the pinboard given by id on the client side with the given list of messages.<br>
	 * The list of message contains new messages and/or old messages. Therefore the client has to
	 * perform a check for duplicate messages.
	 * 
	 * @param pinboardId - the id of the pinboard to update
	 * @param messages - the list of messages for the pinboard
	 */
	public void updatePinboard(Long pinboardId, List<PinboardMessage> messages);

	/**
	 * Request an update for the pinboard given by id.<br>
	 * The update will contain the newest messages available within the given range for the number
	 * of messages.
	 * 
	 * @param pinboardId - the id of the pinboard to update
	 * @param messageCount - the amount of messages to get
	 */
	public void requestPinboardUpdate(Long pinboardId, int messageCount);

	/**
	 * Request an update for the pinboard given by id.<br>
	 * The update will contain the newest messages available within the given range for the number
	 * of messages.
	 * 
	 * @param pinboardId - the id of the pinboard to update
	 * @param fromMessageId - the first message id to get (inclusive)
	 * @param toMessageId - the last message id to get (inclusive)
	 */
	public void requestPinboardUpdate(Long pinboardId, int fromMessageId, int toMessageId);
}

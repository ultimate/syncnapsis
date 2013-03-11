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
package com.syncnapsis.data.service;

import com.syncnapsis.data.model.Pinboard;
import com.syncnapsis.data.model.PinboardMessage;

/**
 * Manager-Interface for access to Pinboard.
 * 
 * @author ultimate
 */
public interface PinboardManager extends GenericNameManager<Pinboard, Long>
{
	/**
	 * Add a message to the pinboard given by id.<br>
	 * The user posting will be determined from the session.
	 * 
	 * @param boardId - the id of the pinboard to add the message to
	 * @param title - the title of the message (optional)
	 * @param message - the message content to add
	 * @return the message object created
	 */
	public PinboardMessage postMessage(Long pinboardId, String title, String message);
}

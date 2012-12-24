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
package com.syncnapsis.data.dao;

import com.syncnapsis.data.model.Player;
import com.syncnapsis.exceptions.UserNotFoundException;

/**
 * Dao-Interface für den Zugriff auf Player
 * 
 * @author ultimate
 */
public interface PlayerDao extends GenericDao<Player, Long>
{
	/**
	 * Get the Player belonging to a User
	 * 
	 * @param userId - the User ID
	 * @return the Player
	 */
	public Player getByUser(Long userId);

	/**
	 * Get the Player belonging to a username
	 * 
	 * @param username - the username
	 * @return the Player
	 */
	public Player getByUsername(String username) throws UserNotFoundException;
}

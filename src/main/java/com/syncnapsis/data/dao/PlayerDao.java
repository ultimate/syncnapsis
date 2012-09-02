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

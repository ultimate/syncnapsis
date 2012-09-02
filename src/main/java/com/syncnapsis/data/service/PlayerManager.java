package com.syncnapsis.data.service;

import com.syncnapsis.data.model.Player;
import com.syncnapsis.exceptions.PlayerSelectionInvalidException;
import com.syncnapsis.exceptions.PlayerSittingExistsException;
import com.syncnapsis.exceptions.PlayerSittingNotPossibleException;
import com.syncnapsis.exceptions.UserNotFoundException;

/**
 * Manager-Interface für den Zugriff auf Player.
 * 
 * @author ultimate
 */
public interface PlayerManager extends GenericManager<Player, Long>
{
	/**
	 * Perform the login for a Player via the corresponding User.
	 * 
	 * @see UserManager#login(String, String)
	 * @param username - the username
	 * @param password - the password
	 * @return the player just logged in
	 * @throws UserNotFoundException - if the login failed (wrong username or password)
	 */
	public Player login(String username, String password);

	/**
	 * Perform the logout for the current Player via the corresponding User.
	 * 
	 * @see UserManager#logout()
	 * @return if the logout has successfully been performed
	 */
	public boolean logout();

	/**
	 * Perform the registration process for a new Player.<br>
	 * Performs the registration of a new User first.<br>
	 * 
	 * @see UserManager#register(String, String, String, String)
	 * @param username - the username to register
	 * @param email - the email used for registration
	 * @param password - the password used
	 * @param passwordConfirm - the confirmation of the password
	 * @return the new Player
	 */
	public Player register(String username, String email, String password, String passwordConfirm);

	/**
	 * Get the Player belonging to a User
	 * 
	 * @param userId - the Player ID
	 * @return the Player
	 */
	public Player getByUser(Long userId);

	/**
	 * Get the Player belonging to a username
	 * 
	 * @param username - the username
	 * @return the Player
	 */
	public Player getByUsername(String username);

	/**
	 * Fügt eine Sittingverbindung zwischen zwei Benutzern hinzu.
	 * 
	 * @param playerSittedId - der gesittete Benutzer
	 * @param playerSitterId - der sittende Benutzer
	 * @return der gesittete Benutzer
	 */
	public Player addSitter(Long playerSittedId, Long playerSitterId) throws PlayerSelectionInvalidException, PlayerSittingExistsException,
			PlayerSittingNotPossibleException;

	/**
	 * Löscht eine Sittingverbindung zwischen zwei Benutzern.
	 * 
	 * @param playerSittedId - der gesittete Benutzer
	 * @param playerSitterId - der sittende Benutzer
	 * @return der gesittete Benutzer
	 */
	public Player removeSitter(Long playerSittedId, Long playerSitterId);

	/**
	 * Prüft, ob ein Sitter noch freie Plätze für Gesittete hat oder nicht
	 * 
	 * @param player - der
	 * @return true oder false
	 */
	public boolean checkSitter(Player player);

	/**
	 * Prüft, ob ein Gesitteter noch freie Plätze für Sitter hat oder nicht
	 * 
	 * @param player - der
	 * @return true oder false
	 */
	public boolean checkSitted(Player player);
}

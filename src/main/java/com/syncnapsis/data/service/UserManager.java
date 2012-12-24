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

import com.syncnapsis.data.model.User;
import com.syncnapsis.exceptions.UserNotFoundException;

/**
 * Manager-Interface für den Zugriff auf User.
 * 
 * @author ultimate
 */
public interface UserManager extends GenericNameManager<User, Long>
{
	/**
	 * Load a User by it's email address
	 * 
	 * @param email - the user's email
	 * @return the user
	 * @throws UserNotFoundException - if an invalid email is passed
	 */
	public User getByEmail(String email) throws UserNotFoundException;

	/**
	 * Check wether there is a user registered with the given email.
	 * 
	 * @param email - the email to check
	 * @return true or false
	 */
	public boolean isEmailRegistered(String email);

	/**
	 * Perform the login for a user.<br>
	 * The password will be encrypted and checked against the database entry.<br>
	 * If the password matches the session will be initialized with the specified user and the user
	 * object will be returned.
	 * 
	 * @param username - the username
	 * @param password - the password
	 * @return the user just logged in
	 * @throws UserNotFoundException - if the login failed (wrong username or password)
	 */
	public User login(String username, String password);

	/**
	 * Perform the logout for the current user.<br>
	 * All user data will be removed from the session. Only the last username may be stored for
	 * relogin purposes.
	 * 
	 * @return if the logout has successfully been performed
	 */
	public boolean logout();

	/**
	 * Perform the registration process for a new user.<br>
	 * User must provide a not-used username (username must be checked within this procedure), an
	 * email-address and a password (including identical confirmation).<br>
	 * Depending on the implementation the following additional operations may be performed:<br>
	 * <ul>
	 * <li>email-Validation</li>
	 * <li>checking for bad usernames (e.g. discriminating, potential mixups, ...)</li>
	 * </ul>
	 * 
	 * @param username - the username to register
	 * @param email - the email used for registration
	 * @param password - the password used
	 * @param passwordConfirm - the confirmation of the password
	 * @return the new User
	 */
	public User register(String username, String email, String password, String passwordConfirm);
}

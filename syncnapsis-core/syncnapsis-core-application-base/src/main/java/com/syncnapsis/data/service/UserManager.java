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
import com.syncnapsis.exceptions.UserRegistrationFailedException;
import com.syncnapsis.exceptions.UserUpdateFailedException;

/**
 * Manager-Interface f�r den Zugriff auf User.
 * 
 * @author ultimate
 */
public interface UserManager extends GenericNameManager<User, Long>
{
	/**
	 * The method name of "verifyRegistration" for RPC
	 */
	public static final String	RPC_VERIFY_REGISTRATION		= "verifyRegistration";
	/**
	 * The method name of "verifyMailAddress" for RPC
	 */
	public static final String	RPC_VERIFY_MAIL_ADDRESS		= "verifyMailAddress";
	/**
	 * The method name of "performPasswordReset" for RPC
	 */
	public static final String	RPC_PERFORM_PASSWORD_RESET	= "performPasswordReset";

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
	 * Check wether the given email is valid
	 * 
	 * @param email - the email to check
	 * @return true or false
	 */
	public boolean isEmailValid(String email);

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
	 * Get the user currently logged in.
	 * 
	 * @return the user logged in
	 */
	public User getCurrent();

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
	 * @throws UserRegistrationFailedException if registration failed
	 */
	public User register(String username, String email, String password, String passwordConfirm) throws UserRegistrationFailedException;

	/**
	 * Change the email address of the current user.<br>
	 * This method will not necessarily update the email address instantly but may send an email to
	 * the new address for verification.
	 * 
	 * @param email - the new email to set
	 * @return true if a verification is required, false otherwise
	 */
	public boolean updateMailAddress(String email);

	/**
	 * Verify the registration of a user.<br>
	 * This will updated the user status and/or role
	 * 
	 * @param userId - the user whose registration to verify
	 * @return a message representing the result of the operation for action output
	 */
	public String verifyRegistration(Long userId);

	/**
	 * Verify a new email set for a user.<br>
	 * This will finally updated the users email address to the requested value.
	 * 
	 * @param userId - the user whose email address to update
	 * @param email - the new email to set
	 * @return a message representing the result of the operation for action output
	 */
	public String verifyMailAddress(Long userId, String email);
	
	/**
	 * Set the password for the current user
	 * @param oldPassword - the old password
	 * @param newPassword - the new password
	 * @param newPasswordConfirm - the confirmation of the new password
	 * @return
	 */
	public boolean setPassword(String oldPassword, String newPassword, String newPasswordConfirm) throws UserUpdateFailedException;

	/**
	 * Request a password reset for the given user.<br>
	 * The given email is checked against the user's email for security reasons.<br>
	 * 
	 * @param username - the user whose password to reset
	 * @param email - the email of the user for validation
	 * @return true if a confirmation is required, false otherwise
	 */
	public boolean requestPasswordReset(String username, String email);

	/**
	 * Perform the password reset for the given user.<br>
	 * This will generate a random password and send a mail to the user with the new password.
	 * 
	 * @param userId - the user whose password to reset
	 * @return a message representing the result of the operation for action output
	 */
	public String performPasswordReset(Long userId);
}

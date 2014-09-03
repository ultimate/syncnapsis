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
package com.syncnapsis.data.dao;

import com.syncnapsis.data.model.User;
import com.syncnapsis.exceptions.UserNotFoundException;

/**
 * Dao-Interface for access to User
 * 
 * @author ultimate
 */
public interface UserDao extends GenericNameDao<User, Long>
{
	/**
	 * Load a User by it's email address
	 * @param email - the user's email
	 * @return the user
	 * @throws UserNotFoundException - if an invalid email is passed
	 */
	public User getByEmail(String email) throws UserNotFoundException;
	
	/**
	 * Check wether there is a user registered with the given email.
	 * @param email - the email to check
	 * @return true or false
	 */
	public boolean isEmailRegistered(String email);
}

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

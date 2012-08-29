package com.syncnapsis.providers.impl;

import com.syncnapsis.constants.BaseApplicationConstants;
import com.syncnapsis.data.model.User;
import com.syncnapsis.providers.SessionBasedProvider;
import com.syncnapsis.providers.UserProvider;

/**
 * Extension of SessionBasedProvider for the current User.
 * 
 * @author ultimate
 */
public class SessionBasedUserProvider extends SessionBasedProvider<User> implements UserProvider
{
	/**
	 * Default-Constructor configuring SessionBasedProvider with
	 * {@link BaseApplicationConstants#SESSION_USER_KEY}.
	 */
	public SessionBasedUserProvider()
	{
		super(BaseApplicationConstants.SESSION_USER_KEY);
	}
}

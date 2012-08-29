package com.syncnapsis.providers.impl;

import com.syncnapsis.constants.BaseApplicationConstants;
import com.syncnapsis.data.model.User;
import com.syncnapsis.providers.AuthorityProvider;
import com.syncnapsis.providers.UserProvider;
import org.springframework.util.Assert;

/**
 * Basic application AuthorityProvider gathering the authority-information from the current user
 * 
 * @author ultimate
 */
public class UserRoleBasedAuthorityProvider implements AuthorityProvider
{
	private UserProvider	userProvider;

	/**
	 * Default-Constructor configuring SessionBasedProvider with
	 * {@link BaseApplicationConstants#SESSION_USER_KEY}.
	 */
	public UserRoleBasedAuthorityProvider(UserProvider userProvider)
	{
		Assert.notNull(userProvider, "userProvider must not be null!");
		this.userProvider = userProvider;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#get()
	 */
	@Override
	public Object[] get()
	{
		User user = userProvider.get();
		if(user != null)
		{
			return new Object[] { user, user.getRole(), user.getRole().getRolename() };
		}
		else
		{
			return new Object[] {};
		}
	}

	/**
	 * Operation not supported!
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void set(Object[] t) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("setting authorities is not permitted");
	}
}

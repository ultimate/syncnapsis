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
package com.syncnapsis.providers.impl;

import com.syncnapsis.constants.ApplicationBaseConstants;
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
	 * {@link ApplicationBaseConstants#SESSION_USER_KEY}.
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

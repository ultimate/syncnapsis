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
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.User;
import com.syncnapsis.providers.AuthorityProvider;
import com.syncnapsis.providers.PlayerProvider;
import org.springframework.util.Assert;

/**
 * Basic application AuthorityProvider gathering the authority-information from the current player
 * 
 * @author ultimate
 */
public class PlayerRoleBasedAuthorityProvider implements AuthorityProvider
{
	private PlayerProvider	playerProvider;

	/**
	 * Default-Constructor configuring SessionBasedProvider with
	 * {@link ApplicationBaseConstants#SESSION_USER_KEY}.
	 */
	public PlayerRoleBasedAuthorityProvider(PlayerProvider playerProvider)
	{
		Assert.notNull(playerProvider, "playerProvider must not be null!");
		this.playerProvider = playerProvider;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#get()
	 */
	@Override
	public Object[] get()
	{
		Player player = playerProvider.get();
		if(player != null)
		{
			User user = player.getUser();
			return new Object[] { player, player.getRole(), player.getRole().getRolename(), user, user.getRole(), user.getRole().getRolename() };
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

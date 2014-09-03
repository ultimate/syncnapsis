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
package com.syncnapsis.providers.impl;

import org.springframework.util.Assert;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.User;
import com.syncnapsis.providers.AuthorityProvider;
import com.syncnapsis.providers.EmpireProvider;
import com.syncnapsis.providers.PlayerProvider;

/**
 * Basic application AuthorityProvider gathering the authority-information from the current player
 * and the current empire.<br>
 * In order to support sitting in future versions the current player ist NOT obtained from the
 * current empire but from a separated {@link PlayerProvider}.
 * 
 * @author ultimate
 */
public class PlayerRoleBasedAuthorityProvider implements AuthorityProvider
{
	/**
	 * The PlayerProvider
	 */
	private PlayerProvider	playerProvider;
	/**
	 * The EmpireProvider
	 */
	private EmpireProvider	empireProvider;

	/**
	 * Default-Constructor configuring SessionBasedProvider with
	 * {@link ApplicationBaseConstants#SESSION_USER_KEY}.
	 * 
	 * @param playerProvider - the PlayerProvider
	 * @param empireProvider - the EmpireProvider
	 */
	public PlayerRoleBasedAuthorityProvider(PlayerProvider playerProvider, EmpireProvider empireProvider)
	{
		Assert.notNull(playerProvider, "playerProvider must not be null!");
		Assert.notNull(playerProvider, "empireProvider must not be null!");
		this.playerProvider = playerProvider;
		this.empireProvider = empireProvider;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#get()
	 */
	@Override
	public Object[] get()
	{
		Empire empire = empireProvider.get();
		Player player = playerProvider.get();
		if(player != null)
		{
			User user = player.getUser();
			return new Object[] { empire, player, player.getRole(), user, user.getRole() };
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

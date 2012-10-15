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

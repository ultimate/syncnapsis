package com.syncnapsis.providers.impl;

import com.syncnapsis.constants.BaseGameConstants;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.providers.PlayerProvider;
import com.syncnapsis.providers.SessionBasedProvider;

/**
 * Extension of SessionBasedProvider for the current Player.
 * 
 * @author ultimate
 */
public class SessionBasedPlayerProvider extends SessionBasedProvider<Player> implements PlayerProvider
{
	/**
	 * Default-Constructor configuring SessionBasedProvider with
	 * {@link BaseGameConstants#SESSION_PLAYER_KEY}.
	 */
	public SessionBasedPlayerProvider()
	{
		super(BaseGameConstants.SESSION_PLAYER_KEY);
	}
}

package com.syncnapsis.security;

import com.syncnapsis.providers.PlayerProvider;
import org.springframework.util.Assert;

/**
 * Extension of {@link BaseApplicationManager} for additional functionality in games.
 * 
 * @author ultimate
 */
public class BaseGameManager extends BaseApplicationManager
{
	/**
	 * The PlayerProvider
	 */
	protected PlayerProvider	playerProvider;

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.SecurityManager#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(playerProvider, "playerProvider must not be null!");
	}

	/**
	 * The PlayerProvider
	 * 
	 * @return playerProvider
	 */
	public PlayerProvider getPlayerProvider()
	{
		return playerProvider;
	}

	/**
	 * The PlayerProvider
	 * 
	 * @param playerProvider - The PlayerProvider
	 */
	public void setPlayerProvider(PlayerProvider playerProvider)
	{
		this.playerProvider = playerProvider;
	}
}

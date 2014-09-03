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
package com.syncnapsis.data.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.dao.PlayerDao;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.data.service.PlayerRoleManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.exceptions.PlayerRegistrationFailedException;
import com.syncnapsis.exceptions.PlayerSelectionInvalidException;
import com.syncnapsis.exceptions.PlayerSittingExistsException;
import com.syncnapsis.exceptions.PlayerSittingNotPossibleException;
import com.syncnapsis.exceptions.UserRegistrationFailedException;
import com.syncnapsis.security.BaseGameManager;

/**
 * Manager-Implementierung f�r den Zugriff auf Player.
 * 
 * @author ultimate
 */
public class PlayerManagerImpl extends GenericManagerImpl<Player, Long> implements PlayerManager, InitializingBean
{
	/**
	 * The PlayerDao for Database access
	 */
	private PlayerDao			playerDao;
	/**
	 * The PlayerRoleManager
	 */
	private PlayerRoleManager	playerRoleManager;
	/**
	 * The UserManager
	 */
	private UserManager			userManager;
	/**
	 * The SecurityManager (BaseGameManager)
	 */
	private BaseGameManager		securityManager;

	/**
	 * Standard Constructor
	 * 
	 * @param playerDao - the PlayerDao for Database access
	 * @param playerRoleManager - the PlayerRoleManager
	 * @param userManager - the UserManager
	 */
	public PlayerManagerImpl(PlayerDao playerDao, PlayerRoleManager playerRoleManager, UserManager userManager)
	{
		super(playerDao);
		this.playerDao = playerDao;
		this.playerRoleManager = playerRoleManager;
		this.userManager = userManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(securityManager, "securityManager must not be null!");
	}

	/**
	 * The SecurityManager (BaseGameManager)
	 * 
	 * @return securityManager
	 */
	public BaseGameManager getSecurityManager()
	{
		return securityManager;
	}

	/**
	 * The SecurityManager (BaseGameManager)
	 * 
	 * @param securityManager - the SecurityManager
	 */
	public void setSecurityManager(BaseGameManager securityManager)
	{
		this.securityManager = securityManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PlayerManager#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Player login(String username, String password)
	{
		User user = userManager.login(username, password);
		if(user == null)
			return null;
		Player player = getByUsername(username);
		if(player != null)
		{
			securityManager.getPlayerProvider().set(player);
			securityManager.getEmpireProvider().set(player.getCurrentEmpire());
			return player;
		}
		else
		{
			userManager.logout();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PlayerManager#logout()
	 */
	@Override
	public boolean logout()
	{
		securityManager.getPlayerProvider().set(null);
		return userManager.logout();
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PlayerManager#getCurrent()
	 */
	@Override
	public Player getCurrent()
	{
		return securityManager.getPlayerProvider().get();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PlayerManager#register(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Player register(String username, String email, String password, String passwordConfirm) throws PlayerRegistrationFailedException
	{
		User user;
		try
		{
			user = userManager.register(username, email, password, passwordConfirm);
		}
		catch(UserRegistrationFailedException e)
		{
			throw new PlayerRegistrationFailedException(e.getMessage());
		}

		Player player = new Player();
		player.setAccountStatus(user.getAccountStatus());
		player.setAccountStatusExpireDate(user.getAccountStatusExpireDate());
		player.setActivated(true);
		// player.setCurrentEmpire(null);
		player.setRole(playerRoleManager.getByMask(GameBaseConstants.ROLE_NORMAL_PLAYER));
		player.setRoleExpireDate(user.getRoleExpireDate());
		player.setUser(user);

		player = save(player);

		return player;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PlayerManager#getByUser(java.lang.Long)
	 */
	@Override
	public Player getByUser(Long playerId)
	{
		return playerDao.getByUser(playerId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PlayerManager#getByUsername(java.lang.String)
	 */
	@Override
	public Player getByUsername(String username)
	{
		return playerDao.getByUsername(username);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PlayerManager#addSitter(java.lang.Long, java.lang.Long)
	 */
	@Override
	public Player addSitter(Long playerSittedId, Long playerSitterId)
	{
		if(playerSittedId.equals(playerSitterId))
			throw new PlayerSelectionInvalidException("sitted equals sitter");

		Player sitted = get(playerSittedId);
		Player sitter = get(playerSitterId);

		for(Player sitter2 : sitted.getSitters())
		{
			if(sitter2.getId().equals(sitter.getId()))
				throw new PlayerSittingExistsException("Sitting relation already exists!", sitter);
		}

		if(!checkSitter(sitter))
			throw new PlayerSittingNotPossibleException("sitter has too much sitted", sitter);
		if(!checkSitted(sitted))
			throw new PlayerSittingNotPossibleException("sitted has too much sitters", sitted);

		sitted.getSitters().add(sitter);
		return save(sitted);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PlayerManager#removeSitter(java.lang.Long, java.lang.Long)
	 */
	@Override
	public Player removeSitter(Long playerSittedId, Long playerSitterId)
	{
		Player sitted = get(playerSittedId);
		Player sitter = get(playerSitterId);
		sitted.getSitters().remove(sitter);
		return save(sitted);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PlayerManager#checkSitter(com.syncnapsis.data.model.Player)
	 */
	@Override
	public boolean checkSitter(Player sitter)
	{
		return sitter.getRole().getMaxSitted() < 0 || sitter.getSitted().size() < sitter.getRole().getMaxSitted();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.PlayerManager#checkSitted(com.syncnapsis.data.model.Player)
	 */
	@Override
	public boolean checkSitted(Player sitted)
	{
		return sitted.getRole().getMaxSitters() < 0 || sitted.getSitters().size() < sitted.getRole().getMaxSitters();
	}
}

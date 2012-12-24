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
package com.syncnapsis.utils.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.EmpireManager;
import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.data.service.PlayerRoleManager;
import com.syncnapsis.enums.EnumAccountStatus;

public class GameBaseDataGenerator extends ApplicationBaseDataGenerator implements InitializingBean
{	
	protected EmpireManager			empireManager;
	protected PlayerManager			playerManager;
	protected PlayerRoleManager		playerRoleManager;

	protected Map<String, Empire>	empires	= new TreeMap<String, Empire>();
	protected Map<String, Player>	players	= new TreeMap<String, Player>();
	
	public EmpireManager getEmpireManager()
	{
		return empireManager;
	}

	public PlayerManager getPlayerManager()
	{
		return playerManager;
	}

	public PlayerRoleManager getPlayerRoleManager()
	{
		return playerRoleManager;
	}

	public void setEmpireManager(EmpireManager empireManager)
	{
		this.empireManager = empireManager;
	}

	public void setPlayerManager(PlayerManager playerManager)
	{
		this.playerManager = playerManager;
	}

	public void setPlayerRoleManager(PlayerRoleManager playerRoleManager)
	{
		this.playerRoleManager = playerRoleManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();

		Assert.notNull(empireManager, "empireManager must not be null!");
		Assert.notNull(playerManager, "playerManager must not be null!");
		Assert.notNull(playerRoleManager, "playerRoleManager must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.DataGenerator#generateDummyData(int)
	 */
	@Override
	public void generateDummyData(int amount)
	{
		String nameSource = DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER + DefaultData.STRING_ASCII_NUMBERS;

		Player player;
		Empire empire;
		String name;
		for(int i = 0; i < amount; i++)
		{
			name = RandomData.randomString(RandomData.randomInt(3, 10), nameSource);

			player = getOrCreatePlayer(name, ApplicationBaseConstants.ROLE_NORMAL_USER, GameBaseConstants.ROLE_NORMAL_PLAYER);
			logger.debug("created user #" + i + ": " + player.getUser().getUsername() + " [" + player.getUser().getId() + "]");

			empire = getOrCreateEmpire(name, player);
			logger.debug("created empire: " + empire.getShortName() + " [" + empire.getId() + "]");
			player.setCurrentEmpire(empire);
			player = playerManager.save(player);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.DataGenerator#generateTestData(java.util.Properties)
	 */
	@Override
	public void generateTestData(Properties properties)
	{
		Assert.notNull(properties, "properties must not be null");

		int dummyUsers = Integer.parseInt(properties.getProperty("dummyData.amount"));
		logger.debug("creating dummy players: amount = " + dummyUsers);
		generateDummyData(dummyUsers);
	}

	public Player createPlayer(String name, String userrolename, String playerrolename)
	{
		User user = getOrCreateUser(name, userrolename);

		if(playerrolename == null)
			playerrolename = GameBaseConstants.ROLE_NORMAL_PLAYER;

		Player player = new Player();
		player.setAccountStatus(EnumAccountStatus.active);
		player.setAccountStatusExpireDate(null);
		player.setCurrentEmpire(null);
		player.setEmpires(new ArrayList<Empire>());
		player.setRole(playerRoleManager.getByName(playerrolename));
		player.setRoleExpireDate(null);
		player.setSitted(new ArrayList<Player>());
		player.setSitters(new ArrayList<Player>());
		player.setUser(user);

		player = playerManager.save(player);

		return player;
	}

	public Empire createEmpire(String name, Player player)
	{
		Empire empire = new Empire();
		empire.setActivated(true);
		empire.setDescription(RandomData.randomString(RandomData.randomInt(20, 2000), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS));
		empire.setFullName("full_" + name);
		empire.setId(null);
		empire.setImageURL(null);
		empire.setShortName(name);
		empire.setPlayer(player);
		empire.setPrimaryColor("#FF0000");
		empire.setSecondaryColor("#FF0000");
		empire.setVersion(null);

		empire = empireManager.save(empire);
		
		return empire;
	}

	public Player getOrCreatePlayer(String playername, String playerrole, String userrole)
	{
		if(!players.containsKey(playername))
			players.put(playername, createPlayer(playername, playerrole, userrole));
		return players.get(playername);
	}

	public Empire getOrCreateEmpire(String empirename, Player player)
	{
		if(!empires.containsKey(empirename))
			empires.put(empirename, createEmpire(empirename, player));
		return empires.get(empirename);
	}
}

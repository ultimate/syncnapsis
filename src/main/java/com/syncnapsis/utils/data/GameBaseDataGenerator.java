package com.syncnapsis.utils.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.constants.BaseApplicationConstants;
import com.syncnapsis.constants.BaseGameConstants;
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

			player = getOrCreatePlayer(name, BaseApplicationConstants.ROLE_NORMAL_USER, BaseGameConstants.ROLE_NORMAL_PLAYER);
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

	/**
	 * Create a user with the given name and role
	 * 
	 * @param name
	 * @param rolename
	 * @return
	 */
	public Player createPlayer(String name, String userrolename, String playerrolename)
	{
		User user = getOrCreateUser(name, userrolename);

		if(playerrolename == null)
			playerrolename = BaseGameConstants.ROLE_NORMAL_PLAYER;

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

		/*
		 * PlayerRank playerRank = createRankSimple(PlayerRank.class, player, 0);
		 * playerRank = (PlayerRank) playerRankManager.save(playerRank);
		 */

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
		empire.setVersion(null);

		empire = empireManager.save(empire);
		/*
		if(contactAuthoritiesMap != null)
		{
			ContactGroup contactGroup;
			for(Entry<String, Map<String, Boolean>> e : contactAuthoritiesMap.entrySet())
			{
				contactGroup = new ContactGroup();
				contactGroup.setAllianceContacts(null);
				contactGroup.setDefaultVisible(true);
				contactGroup.setDescription(e.getKey());
				contactGroup.setEmpireContacts(null);
				contactGroup.setName(e.getKey());
				contactGroup.setOwnerAlliance(null);
				contactGroup.setOwnerEmpire(empire);

				contactGroup = contactGroupManager.save(contactGroup);
			}
		}
		*/

		// TODO colonies & blocks

		// TODO später durch echte Berechnung ersetzen
		/*
		int points = (int) (level * 100 * (Math.random() / 5 + 1));
		EmpireRank empireRank = createRankSimple(EmpireRank.class, empire, points);
		empireRank = (EmpireRank) empireRankManager.save(empireRank);

		PlayerRank playerRank = (PlayerRank) playerRankManager.getByEntity(player.getId());
		playerRank.setNumberOfEmpires(playerRank.getNumberOfEmpires() + 1);
		playerRank.setEconomy(playerRank.getEconomy() + empireRank.getEconomy());
		playerRank.setMilitary(playerRank.getMilitary() + empireRank.getMilitary());
		playerRank.setScience(playerRank.getScience() + empireRank.getScience());
		playerRank.setTotal(playerRank.getTotal() + empireRank.getTotal());
		playerRank.setAverageEconomy(playerRank.getEconomy() / playerRank.getNumberOfEmpires());
		playerRank.setAverageMilitary(playerRank.getMilitary() / playerRank.getNumberOfEmpires());
		playerRank.setAverageScience(playerRank.getScience() / playerRank.getNumberOfEmpires());
		playerRank.setAverageTotal(playerRank.getTotal() / playerRank.getNumberOfEmpires());
		playerRank = (PlayerRank) playerRankManager.save(playerRank);
		*/
		
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

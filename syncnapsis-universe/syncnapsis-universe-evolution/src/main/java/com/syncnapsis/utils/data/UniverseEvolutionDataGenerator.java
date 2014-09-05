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
package com.syncnapsis.utils.data;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.util.Assert;

import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.EmpireRank;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRank;
import com.syncnapsis.data.service.ContactGroupManager;
import com.syncnapsis.data.service.EmpireRankManager;
import com.syncnapsis.data.service.PlayerRankManager;

public class UniverseEvolutionDataGenerator extends GameBaseDataGenerator
{
	protected AuthorityGenericImplDataGenerator	authorityDataGenerator;
	protected PoliticsComplexImplDataGenerator	politicsDataGenerator;
	protected StatsBaseImplDataGenerator		statsDataGenerator;

	protected ContactGroupManager				contactGroupManager;
	protected EmpireRankManager					empireRankManager;
	protected PlayerRankManager					playerRankManager;

	public AuthorityGenericImplDataGenerator getAuthorityDataGenerator()
	{
		return authorityDataGenerator;
	}

	public void setAuthorityDataGenerator(AuthorityGenericImplDataGenerator authorityDataGenerator)
	{
		this.authorityDataGenerator = authorityDataGenerator;
	}

	public PoliticsComplexImplDataGenerator getPoliticsDataGenerator()
	{
		return politicsDataGenerator;
	}

	public void setPoliticsDataGenerator(PoliticsComplexImplDataGenerator politicsDataGenerator)
	{
		this.politicsDataGenerator = politicsDataGenerator;
	}

	public StatsBaseImplDataGenerator getStatsDataGenerator()
	{
		return statsDataGenerator;
	}

	public void setStatsDataGenerator(StatsBaseImplDataGenerator statsDataGenerator)
	{
		this.statsDataGenerator = statsDataGenerator;
	}

	public ContactGroupManager getContactGroupManager()
	{
		return contactGroupManager;
	}

	public void setContactGroupManager(ContactGroupManager contactGroupManager)
	{
		this.contactGroupManager = contactGroupManager;
	}

	public EmpireRankManager getEmpireRankManager()
	{
		return empireRankManager;
	}

	public void setEmpireRankManager(EmpireRankManager empireRankManager)
	{
		this.empireRankManager = empireRankManager;
	}

	public PlayerRankManager getPlayerRankManager()
	{
		return playerRankManager;
	}

	public void setPlayerRankManager(PlayerRankManager playerRankManager)
	{
		this.playerRankManager = playerRankManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();

		Assert.notNull(authorityDataGenerator, "authorityDataGenerator must not be null!");
		Assert.notNull(politicsDataGenerator, "politicsDataGenerator must not be null!");
		Assert.notNull(statsDataGenerator, "statsDataGenerator must not be null!");

		Assert.notNull(contactGroupManager, "contactGroupManager must not be null!");
		Assert.notNull(empireRankManager, "empireRankManager must not be null!");
		Assert.notNull(playerRankManager, "playerRankManager must not be null!");
	}

	@Override
	public void generateDummyData(int amount)
	{
		super.generateDummyData(amount);
	}

	@Override
	public void generateTestData(Properties properties)
	{
		Assert.notNull(properties, "properties must not be null");

		logger.debug("creating players and diplomacy from excel");
		try
		{
			UniverseEvolutionExcelParser.createDataFromExcel(properties);
		}
		catch(Exception e)
		{
			logger.error("could not create data from Excel", e);
		}
	}

	@Override
	public Player createPlayer(String name, String userrolename, String playerrolename)
	{
		Player player = super.createPlayer(name, userrolename, playerrolename);

		PlayerRank playerRank = statsDataGenerator.createRankSimple(PlayerRank.class, player, 0);
		playerRank = (PlayerRank) playerRankManager.save(playerRank);

		return player;
	}

	@Override
	public Empire createEmpire(String name, Player player)
	{
		return createEmpire(name, player, 1, 1, 0, 0, 0, Integer.MAX_VALUE, 0, politicsDataGenerator.getDefaultContactAuthorities());
	}

	public Empire createEmpire(String name, Player player, int blocks, int colonies, int x, int y, int z, int r, int level,
			Map<String, Map<String, Boolean>> contactAuthoritiesMap)
	{
		Empire empire = super.createEmpire(name, player);

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

		// TODO colonies & blocks

		// TODO sp�ter durch echte Berechnung ersetzen
		int points = (int) (level * 100 * (Math.random() / 5 + 1));
		EmpireRank empireRank = statsDataGenerator.createRankSimple(EmpireRank.class, empire, points);
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

		return empire;
	}

	public Empire getOrCreateEmpire(String empirename, Player player, int blocks, int colonies, int x, int y, int z, int r, int level)
	{
		if(!empires.containsKey(empirename))
			empires.put(empirename,
					createEmpire(empirename, player, blocks, colonies, x, y, z, r, level, politicsDataGenerator.getDefaultContactAuthorities()));
		return empires.get(empirename);
	}
}

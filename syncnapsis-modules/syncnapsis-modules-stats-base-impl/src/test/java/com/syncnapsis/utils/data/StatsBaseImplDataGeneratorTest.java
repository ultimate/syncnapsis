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

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.EmpireRank;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRank;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ApplicationContextUtil;

@TestExcludesMethods({ "afterPropertiesSet", "generate*", "set*", "get*" })
public class StatsBaseImplDataGeneratorTest extends BaseDaoTestCase
{
	private StatsBaseImplDataGenerator	gen	= new StatsBaseImplDataGenerator();

	@Override
	public void setUp() throws Exception
	{
		super.setUp();

		ApplicationContextUtil.autowire(applicationContext, gen);
		gen.setProjectDirectory(new File(".").getAbsolutePath());
		gen.afterPropertiesSet();
	}

	public void testCreateRank() throws Exception
	{
		Map<String, Integer> points = new HashMap<String, Integer>();
		points.put("numberOfEmpires", 2);
		points.put("economy", 100);
		points.put("averageEconomy", 50);
		points.put("military", 150);
		points.put("averageMilitary", 75);
		points.put("science", 50);
		points.put("averageScience", 25);
		points.put("total", 300);
		points.put("averageTotal", 150);
		
		Player player = new Player();

		PlayerRank rank = gen.createRank(PlayerRank.class, player, points);
		
		assertNotNull(rank);
		assertSame(player, rank.getEntity());
		
		for(Entry<String, Integer> e: points.entrySet())
		{
			assertEquals((int) e.getValue(), rank.getPoints(e.getKey()));
		}
	}

	public void testCreateRankSimple() throws Exception
	{
		Empire empire = new Empire();
		
		int totalExpected = 500;
		EmpireRank rank = gen.createRankSimple(EmpireRank.class, empire, totalExpected);
		
		assertNotNull(rank);
		assertSame(empire, rank.getEntity());

		int total = 0;
		total += rank.getEconomy();
		total += rank.getMilitary();
		total += rank.getScience();
		
		assertEquals(totalExpected, total);
		assertEquals(totalExpected, rank.getTotal());
	}
}

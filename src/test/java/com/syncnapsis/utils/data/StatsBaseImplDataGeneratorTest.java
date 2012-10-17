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

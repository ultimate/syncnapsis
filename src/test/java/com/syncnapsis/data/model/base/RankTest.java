package com.syncnapsis.data.model.base;

import com.syncnapsis.data.model.annotations.RankCriterion;
import com.syncnapsis.tests.LoggerTestCase;

public class RankTest extends LoggerTestCase
{
	public void testReadCriterions() throws Exception
	{
		TestRank r = new TestRank();
		
		String defaultCategory = RankCriterion.defaultCategory;
		
		assertEquals(9, r.getAllCriterions().size());
		
		assertEquals(3, r.getCategories().size());
		assertTrue(r.getCategories().contains(defaultCategory));
		assertTrue(r.getCategories().contains("military"));
		assertTrue(r.getCategories().contains("economics"));
		assertFalse(r.getCategories().contains("science"));
	
		assertEquals("total", r.getPrimaryCriterion(defaultCategory));
		
		assertEquals(4, r.getAvailableCriterions(defaultCategory).size());
		assertEquals("total", r.getAvailableCriterions(defaultCategory).get(0));
		assertEquals("military", r.getAvailableCriterions(defaultCategory).get(1));
		assertEquals("economics", r.getAvailableCriterions(defaultCategory).get(2));
		assertEquals("science", r.getAvailableCriterions(defaultCategory).get(3));
		
		assertEquals(2, r.getAvailableCriterions("military").size());
		assertEquals("offense_military", r.getAvailableCriterions("military").get(0));
		assertEquals("defense_military", r.getAvailableCriterions("military").get(1));

		assertEquals(3, r.getAvailableCriterions("economics").size());
		assertEquals("trade_economics", r.getAvailableCriterions("economics").get(0));
		assertEquals("production_economics", r.getAvailableCriterions("economics").get(1));
		assertEquals("storage_economics", r.getAvailableCriterions("economics").get(2));

		try
		{
			r.getAvailableCriterions("science");
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(e.getMessage().startsWith("Invalid category"));
		}
	}
	
	private class TestBaseObject extends BaseObject<Long>
	{
		
	}
	
	private class TestRank extends Rank<TestBaseObject>
	{
		@RankCriterion(primary = true)
		private int total;
		@RankCriterion
		private int military;
		@RankCriterion(category = "military")
		private int offense_military;
		@RankCriterion(category = "military")
		private int defense_military;
		@RankCriterion
		private int economics;
		@RankCriterion(category = "economics")
		private int trade_economics;
		@RankCriterion(category = "economics")
		private int production_economics;
		@RankCriterion(category = "economics")
		private int storage_economics;
		@RankCriterion
		private int science;
	}
}

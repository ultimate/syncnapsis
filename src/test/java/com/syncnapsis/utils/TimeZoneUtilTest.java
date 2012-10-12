package com.syncnapsis.utils;

import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class TimeZoneUtilTest extends BaseSpringContextTestCase
{
	@TestCoversMethods({"initialize", "getTimeZone", "getRegions", "getIdsByRegions"})
	public void testTimeZoneUtil() throws Exception
	{
		logger.debug("testing TimeZoneUtil...");
		
		assertNotNull(TimeZoneUtil.getRegions());
		assertEquals(17, TimeZoneUtil.getRegions().size());
		String lastRegion = "";
		for(String currentRegion : TimeZoneUtil.getRegions())
		{
			logger.debug("checking timezone-region... " + currentRegion);
			// sortierung der regionen prüfen
			assertTrue(currentRegion.compareToIgnoreCase(lastRegion) > 0);

			assertNotNull(TimeZoneUtil.getIdsByRegions(currentRegion));
			assertTrue(TimeZoneUtil.getIdsByRegions(currentRegion).size() > 0);
			String lastId = "";
			for(String currentId : TimeZoneUtil.getIdsByRegions(currentRegion))
			{
				// sortierung der ids der region prüfen
				assertTrue(currentId.compareToIgnoreCase(lastId) > 0);
			}
			logger.debug("-> timezone contains " + TimeZoneUtil.getIdsByRegions(currentRegion).size() + " ids");
		}
	}
	
	public void testGetDefaultID() throws Exception
	{
		assertNotNull(TimeZoneUtil.getDefaultID());
		assertNotNull(TimeZoneUtil.getTimeZone(TimeZoneUtil.getDefaultID()));
	}
}

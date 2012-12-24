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

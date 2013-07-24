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
package com.syncnapsis.data.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 * 
 */
@TestExcludesMethods({ "get*", "set*", "is*", "hashCode", "equals" })
public class SolarSystemPopulationTest extends LoggerTestCase
{
	public void testSort_byRankValue() throws Exception
	{
		SolarSystemPopulation p1 = new SolarSystemPopulation();
		p1.setColonizationDate(new Date(20));
		SolarSystemPopulation p2 = new SolarSystemPopulation();
		p2.setColonizationDate(new Date(50));
		SolarSystemPopulation p3 = new SolarSystemPopulation();
		p3.setColonizationDate(new Date(80));

		List<SolarSystemPopulation> list = new ArrayList<SolarSystemPopulation>();
		list.add(p2);
		list.add(p3);
		list.add(p1);

		Collections.sort(list, SolarSystemPopulation.BY_COLONIZATIONDATE);

		assertSame(p1, list.get(0));
		assertSame(p2, list.get(1));
		assertSame(p3, list.get(2));
	}
}

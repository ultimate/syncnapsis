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
package com.syncnapsis.data.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 * 
 */
@TestExcludesMethods({ "get*", "set*", "is*", "hashCode", "equals" })
public class SolarSystemInfrastructureTest extends LoggerTestCase
{
	public void testSort_byId() throws Exception
	{
		// nulls last
		
		SolarSystemInfrastructure p1 = new SolarSystemInfrastructure();
		p1.setId(1L);
		SolarSystemInfrastructure p2 = new SolarSystemInfrastructure();
		p2.setId(2L);
		SolarSystemInfrastructure p3 = new SolarSystemInfrastructure();
		p3.setId(null);

		List<SolarSystemInfrastructure> list = new ArrayList<SolarSystemInfrastructure>();
		list.add(p2);
		list.add(p3);
		list.add(p1);

		Collections.sort(list, SolarSystemInfrastructure.BY_ID);
		
		assertSame(p1, list.get(0));
		assertSame(p2, list.get(1));
		assertSame(p3, list.get(2));
	}
}

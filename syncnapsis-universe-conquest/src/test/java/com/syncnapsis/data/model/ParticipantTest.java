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
import java.util.List;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 * 
 */
@TestExcludesMethods({ "get*", "set*", "is*", "hashCode", "equals" })
public class ParticipantTest extends LoggerTestCase
{
	public void testSort_byRankValue() throws Exception
	{
		Participant p1 = new Participant();
		p1.setRankValue(80);
		Participant p2 = new Participant();
		p2.setRankValue(50);
		Participant p3 = new Participant();
		p3.setRankValue(20);

		List<Participant> list = new ArrayList<Participant>();
		list.add(p2);
		list.add(p3);
		list.add(p1);

		Collections.sort(list, Participant.BY_RANKVALUE);

		assertSame(p1, list.get(0));
		assertSame(p2, list.get(1));
		assertSame(p3, list.get(2));
	}
	public void testSort_byEmpire() throws Exception
	{
		Participant p1 = new Participant();
		p1.setEmpire(new Empire());
		p1.getEmpire().setId(2L);
		Participant p2 = new Participant();
		p2.setEmpire(new Empire());
		p2.getEmpire().setId(10L);
		Participant p3 = new Participant();
		p3.setEmpire(new Empire());
		p3.getEmpire().setId(15L);

		List<Participant> list = new ArrayList<Participant>();
		list.add(p2);
		list.add(p3);
		list.add(p1);

		Collections.sort(list, Participant.BY_EMPIRE);

		assertSame(p1, list.get(0));
		assertSame(p2, list.get(1));
		assertSame(p3, list.get(2));
	}
}

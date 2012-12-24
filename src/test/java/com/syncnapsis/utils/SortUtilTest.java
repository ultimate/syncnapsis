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

import java.util.ArrayList;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class SortUtilTest extends LoggerTestCase
{
	@TestCoversMethods({"sortList*"})
	public void testSortUtil() throws Exception
	{
		logger.debug("testing SortUtil...");

		Integer[] is = new Integer[20];
		for(int i = 0; i < is.length; i++)
		{
			is[i] = new Integer(i);
		}

		ArrayList<Integer> iListSortedAsc = new ArrayList<Integer>();
		ArrayList<Integer> iListSortedDesc = new ArrayList<Integer>();
		ArrayList<Integer> iListUnsorted = new ArrayList<Integer>();

		for(int i = 0; i < is.length; i++)
			iListSortedAsc.add(is[i]);
		for(int i = is.length - 1; i >= 0; i--)
			iListSortedDesc.add(is[i]);
		for(int i = 0; i < is.length; i++)
			iListUnsorted.add((int) (Math.random() * iListUnsorted.size()), is[i]);

		logger.debug("Sortierte Liste aufsteigend - " + iListSortedAsc);
		logger.debug("Sortierte Liste absteigend  - " + iListSortedDesc);
		logger.debug("Gemischte Liste             - " + iListUnsorted);

		assertEquals(iListSortedAsc, SortUtil.sortListAscending(iListUnsorted, "intValue"));
		assertEquals(iListSortedAsc, SortUtil.sortList(iListUnsorted, "intValue", SortUtil.ASCENDING));
		assertEquals(iListSortedDesc, SortUtil.sortListDescending(iListUnsorted, "intValue"));
		assertEquals(iListSortedDesc, SortUtil.sortList(iListUnsorted, "intValue", SortUtil.DESCENDING));
	}
}

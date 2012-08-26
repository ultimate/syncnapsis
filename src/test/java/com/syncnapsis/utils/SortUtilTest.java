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

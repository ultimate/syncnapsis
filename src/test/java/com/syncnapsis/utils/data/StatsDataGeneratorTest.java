package com.syncnapsis.utils.data;

import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({"afterPropertiesSet", "generate*", "set*", "get*"})
public class StatsDataGeneratorTest extends BaseSpringContextTestCase
{
	public void testCreateRank() throws Exception
	{
		logger.info("nothing to test here yet: generator is abstract!");
	}
	
	public void testCreateRankSimple() throws Exception
	{
		logger.info("nothing to test here yet: generator is abstract!");
	}
}

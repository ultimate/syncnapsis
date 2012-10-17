package com.syncnapsis.utils.data;

import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({"afterPropertiesSet", "generate*", "set*", "get*"})
public class PoliticsDataGeneratorTest extends BaseSpringContextTestCase
{
	public void testCreateContact() throws Exception
	{
		logger.info("nothing to test here yet: generator is abstract!");
	}
}

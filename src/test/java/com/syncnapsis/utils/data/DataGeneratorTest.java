package com.syncnapsis.utils.data;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({"afterPropertiesSet", "initHibernate"})
public class DataGeneratorTest extends LoggerTestCase
{
	@TestCoversMethods("generate*")
	public void testGenerate() throws Exception
	{
		// TODO no test yet
	}
}

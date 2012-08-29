package com.syncnapsis.security;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({ "set*", "get*", "afterPropertiesSet" })
public class BaseApplicationManagerTest extends LoggerTestCase
{
	public void testBaseApplicationManager() throws Exception
	{
		// nothing to test yet
	}
}

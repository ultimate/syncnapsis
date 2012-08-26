package com.syncnapsis.providers.impl;

import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.providers.impl.SystemTimeProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({TimeProvider.class, SystemTimeProvider.class})
public class SystemTimeProviderTest extends LoggerTestCase
{
	@TestCoversMethods("*")
	public void testProvider()
	{
		logger.debug("testing getTime...");
	
		TimeProvider tp = new SystemTimeProvider();
		
		assertNotNull(tp.get());
		
		assertTrue(tp.get() - System.currentTimeMillis() < 1000);
	}
}

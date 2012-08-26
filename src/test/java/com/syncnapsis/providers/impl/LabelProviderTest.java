package com.syncnapsis.providers.impl;

import com.syncnapsis.providers.ParameterizedProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({LabelProvider.class, ParameterizedProvider.class})
public class LabelProviderTest extends LoggerTestCase
{
	@TestCoversMethods("*")
	public void testLabelProvider()
	{
		logger.warn("nothing done here");
	}
}

package com.syncnapsis;

import com.syncnapsis.tests.CoverageTestCase;

public class PoliticsApiCoverageTest extends CoverageTestCase
{
	@Override
	public void testCoverage() throws Exception
	{
		logger.debug("checking test-coverage...");
		super.testCoverage();
	}

	@Override
	protected String ignoreSpecialClass(Class<?> c)
	{
		if(c.isInterface())
			return "Interface";
		return super.ignoreSpecialClass(c);
	}
}

package com.syncnapsis;

import com.syncnapsis.tests.CoverageTestCase;

public class UniverseUtilsCoverageTest extends CoverageTestCase
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
		if(c.getPackage().getName().equals(this.getClass().getPackage().getName() + ".universe.galaxy.visualization"))
			return "Experimental-Tool";
		return super.ignoreSpecialClass(c);
	}
}

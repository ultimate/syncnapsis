package com.syncnapsis;

import com.syncnapsis.exceptions.ObjectNotFoundException;
import com.syncnapsis.tests.CoverageTestCase;

public class DataCoverageTest extends CoverageTestCase
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
		if(c.getName().startsWith("com.syncnapsis.data"))
			return "Data-Class --> Test in specific implementation!";
		if(c.equals(ObjectNotFoundException.class))
			return "Exception-Super-Type --> Test in specific implementation!";
		return super.ignoreSpecialClass(c);
	}
}

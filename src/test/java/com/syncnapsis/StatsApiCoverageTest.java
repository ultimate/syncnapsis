package com.syncnapsis;

import com.syncnapsis.data.dao.hibernate.GenericRankDaoHibernate;
import com.syncnapsis.data.service.impl.GenericRankManagerImpl;
import com.syncnapsis.tests.CoverageTestCase;

public class StatsApiCoverageTest extends CoverageTestCase
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
		if(c.isAnnotation())
			return "Annotation";
		if(c.equals(GenericRankDaoHibernate.class))
			return "Generic-Data-Class --> Test in specific implementation!";
		if(c.equals(GenericRankManagerImpl.class))
			return "Generic-Data-Class --> Test in specific implementation!";
		return super.ignoreSpecialClass(c);
	}
}

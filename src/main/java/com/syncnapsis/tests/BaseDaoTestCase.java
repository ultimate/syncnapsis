package com.syncnapsis.tests;

import org.hibernate.Transaction;

import com.syncnapsis.utils.HibernateUtil;

public abstract class BaseDaoTestCase extends BaseSpringContextTestCase
{
	private Transaction transaction = null;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		logger.debug("Begin transaction...");
		startTransaction();	
	}
	
	protected void startTransaction()
	{
		transaction = HibernateUtil.currentSession().beginTransaction();
	}
	
	protected void rollbackTransaction()
	{
		transaction.rollback();
		transaction = null;
	}

	@Override
	protected void tearDown() throws Exception
	{
		logger.debug("Rollback transaction...");
		rollbackTransaction();
		super.tearDown();
	}
}

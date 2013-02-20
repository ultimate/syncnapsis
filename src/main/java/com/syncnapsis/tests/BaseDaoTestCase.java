/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
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
		if(!HibernateUtil.isSessionBound())
			HibernateUtil.openBoundSession(false);
		transaction = HibernateUtil.currentSession().beginTransaction();
	}
	
	protected void rollbackTransaction()
	{
		transaction.rollback();
		transaction = null;
		HibernateUtil.closeBoundSession();
	}

	@Override
	protected void tearDown() throws Exception
	{
		logger.debug("Rollback transaction...");
		rollbackTransaction();
		super.tearDown();
	}
}

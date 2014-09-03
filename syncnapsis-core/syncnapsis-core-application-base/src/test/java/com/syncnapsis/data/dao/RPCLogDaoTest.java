/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
package com.syncnapsis.data.dao;

import com.syncnapsis.data.dao.hibernate.RPCLogDaoHibernate;
import com.syncnapsis.data.model.RPCLog;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({RPCLogDao.class, RPCLogDaoHibernate.class})
public class RPCLogDaoTest extends GenericDaoTestCase<RPCLog, Long>
{
	private RPCLogDao rpcLogDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		Long existingId = rpcLogDao.getAll().get(0).getId();
		
		RPCLog rpcLog = new RPCLog();
		// set individual properties here
		
		setEntity(rpcLog);
		
		setEntityProperty("result");
		setEntityPropertyValue("any result");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		
		setGenericDao(rpcLogDao);
	}
	
	// insert individual Tests here
}

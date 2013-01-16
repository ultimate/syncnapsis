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
package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.RPCLogDao;
import com.syncnapsis.data.model.RPCLog;
import com.syncnapsis.data.service.RPCLogManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { RPCLogManager.class, RPCLogManagerImpl.class })
public class RPCLogManagerImplTest extends GenericManagerImplTestCase<RPCLog, Long, RPCLogManager, RPCLogDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new RPCLog());
		setDaoClass(RPCLogDao.class);
		setMockDao(mockContext.mock(RPCLogDao.class));
		setMockManager(new RPCLogManagerImpl(mockDao));
	}
}

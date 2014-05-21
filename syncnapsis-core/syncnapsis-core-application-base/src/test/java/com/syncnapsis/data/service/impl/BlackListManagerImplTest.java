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

import com.syncnapsis.data.dao.BlackListDao;
import com.syncnapsis.data.model.BlackList;
import com.syncnapsis.data.service.BlackListManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { BlackListManager.class, BlackListManagerImpl.class })
public class BlackListManagerImplTest extends GenericNameManagerImplTestCase<BlackList, Long, BlackListManager, BlackListDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new BlackList());
		setDaoClass(BlackListDao.class);
		setMockDao(mockContext.mock(BlackListDao.class));
		setMockManager(new BlackListManagerImpl(mockDao));
	}
}

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

import com.syncnapsis.data.dao.EmpireRankDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.EmpireRank;
import com.syncnapsis.data.service.EmpireRankManager;
import com.syncnapsis.tests.BaseRankManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { EmpireRankManager.class, EmpireRankManagerImpl.class })
public class EmpireRankManagerImplTest extends BaseRankManagerImplTestCase<EmpireRank, Empire, Long, EmpireRankManager, EmpireRankDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new EmpireRank());
		setDaoClass(EmpireRankDao.class);
		setMockDao(mockContext.mock(EmpireRankDao.class));
		setMockManager(new EmpireRankManagerImpl(mockDao));
	}
}

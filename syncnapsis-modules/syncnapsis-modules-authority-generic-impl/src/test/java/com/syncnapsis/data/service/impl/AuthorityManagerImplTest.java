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
package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.AuthorityDao;
import com.syncnapsis.data.model.Authority;
import com.syncnapsis.data.service.AuthorityManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({AuthorityManager.class, AuthorityManagerImpl.class})
public class AuthorityManagerImplTest extends GenericNameManagerImplTestCase<Authority, Long, AuthorityManager, AuthorityDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Authority());
		setDaoClass(AuthorityDao.class);
		setMockDao(mockContext.mock(AuthorityDao.class));
		setMockManager(new AuthorityManagerImpl(mockDao));
	}
}

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

import com.syncnapsis.data.dao.GalaxyDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { GalaxyManager.class, GalaxyManagerImpl.class })
public class GalaxyManagerImplTest extends GenericNameManagerImplTestCase<Galaxy, Long, GalaxyManager, GalaxyDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Galaxy());
		setDaoClass(GalaxyDao.class);
		setMockDao(mockContext.mock(GalaxyDao.class));
		setMockManager(new GalaxyManagerImpl(mockDao));
	}
}

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

import com.syncnapsis.data.dao.AllianceAllianceContactDao;
import com.syncnapsis.data.model.contacts.AllianceAllianceContact;
import com.syncnapsis.data.service.AllianceAllianceContactManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({AllianceAllianceContactManager.class, AllianceAllianceContactManagerImpl.class})
public class AllianceAllianceContactManagerImplTest extends GenericManagerImplTestCase<AllianceAllianceContact, Long, AllianceAllianceContactManager, AllianceAllianceContactDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new AllianceAllianceContact());
		setDaoClass(AllianceAllianceContactDao.class);
		setMockDao(mockContext.mock(AllianceAllianceContactDao.class));
		setMockManager(new AllianceAllianceContactManagerImpl(mockDao));
	}
	
	public void testGetContact() throws Exception
	{
		// TODO test when implemented
	}
	
	public void testGetAuthoritiesForContact1() throws Exception
	{
		// TODO test when implemented
	}
	
	public void testGetAuthoritiesForContact2() throws Exception
	{
		// TODO test when implemented
	}
}

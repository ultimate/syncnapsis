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

import com.syncnapsis.data.dao.EmpireAllianceContactDao;
import com.syncnapsis.data.model.contacts.EmpireAllianceContact;
import com.syncnapsis.data.service.EmpireAllianceContactManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({EmpireAllianceContactManager.class, EmpireAllianceContactManagerImpl.class})
public class EmpireAllianceContactManagerImplTest extends GenericManagerImplTestCase<EmpireAllianceContact, Long, EmpireAllianceContactManager, EmpireAllianceContactDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new EmpireAllianceContact());
		setDaoClass(EmpireAllianceContactDao.class);
		setMockDao(mockContext.mock(EmpireAllianceContactDao.class));
		setMockManager(new EmpireAllianceContactManagerImpl(mockDao));
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

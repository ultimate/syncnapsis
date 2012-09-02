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

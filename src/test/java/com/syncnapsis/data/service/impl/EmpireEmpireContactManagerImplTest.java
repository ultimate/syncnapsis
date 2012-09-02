package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.EmpireEmpireContactDao;
import com.syncnapsis.data.model.contacts.EmpireEmpireContact;
import com.syncnapsis.data.service.EmpireEmpireContactManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({EmpireEmpireContactManager.class, EmpireEmpireContactManagerImpl.class})
public class EmpireEmpireContactManagerImplTest extends GenericManagerImplTestCase<EmpireEmpireContact, Long, EmpireEmpireContactManager, EmpireEmpireContactDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new EmpireEmpireContact());
		setDaoClass(EmpireEmpireContactDao.class);
		setMockDao(mockContext.mock(EmpireEmpireContactDao.class));
		setMockManager(new EmpireEmpireContactManagerImpl(mockDao));
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

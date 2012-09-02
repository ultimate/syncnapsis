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

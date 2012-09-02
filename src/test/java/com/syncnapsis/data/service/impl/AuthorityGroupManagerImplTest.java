package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.AuthorityGroupDao;
import com.syncnapsis.data.model.AuthorityGroup;
import com.syncnapsis.data.service.AuthorityGroupManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({AuthorityGroupManager.class, AuthorityGroupManagerImpl.class})
public class AuthorityGroupManagerImplTest extends GenericNameManagerImplTestCase<AuthorityGroup, Long, AuthorityGroupManager, AuthorityGroupDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new AuthorityGroup());
		setDaoClass(AuthorityGroupDao.class);
		setMockDao(mockContext.mock(AuthorityGroupDao.class));
		setMockManager(new AuthorityGroupManagerImpl(mockDao));
	}
}

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

package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.AuthoritiesGenericImplDao;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.service.AuthoritiesGenericImplManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({AuthoritiesGenericImplManager.class, AuthoritiesGenericImplManagerImpl.class})
public class AuthoritiesGenericImplManagerImplTest extends GenericManagerImplTestCase<AuthoritiesGenericImpl, Long, AuthoritiesGenericImplManager, AuthoritiesGenericImplDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new AuthoritiesGenericImpl());
		setDaoClass(AuthoritiesGenericImplDao.class);
		setMockDao(mockContext.mock(AuthoritiesGenericImplDao.class));
		setMockManager(new AuthoritiesGenericImplManagerImpl(mockDao));
	}
}

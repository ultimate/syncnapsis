package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.UserRoleDao;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.data.service.UserRoleManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { UserRoleManager.class, UserRoleManagerImpl.class })
public class UserRoleManagerImplTest extends GenericNameManagerImplTestCase<UserRole, Long, UserRoleManager, UserRoleDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new UserRole());
		setDaoClass(UserRoleDao.class);
		setMockDao(mockContext.mock(UserRoleDao.class));
		setMockManager(new UserRoleManagerImpl(mockDao));
	}
}

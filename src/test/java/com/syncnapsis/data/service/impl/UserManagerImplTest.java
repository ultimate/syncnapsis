package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.UserDao;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.data.service.UserRoleManager;
import com.syncnapsis.exceptions.UserNotFoundException;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestCoversClasses({ UserManager.class, UserManagerImpl.class })
@TestExcludesMethods({"*etSecurityManager", "afterPropertiesSet"})
public class UserManagerImplTest extends GenericNameManagerImplTestCase<User, Long, UserManager, UserDao>
{
	private UserManager	userManager;
	private UserRoleManager userRoleManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new User());
		setDaoClass(UserDao.class);
		setMockDao(mockContext.mock(UserDao.class));
		setMockManager(new UserManagerImpl(mockDao, userRoleManager));
	}

	public void testLogin() throws Exception
	{
		String name, pw;

		name = "user1";
		pw = name;
		assertEquals(userManager.getByName(name), userManager.login(name, pw));

		name = "user1";
		pw = "other";
		try
		{
			userManager.login(name, pw);
		}
		catch(UserNotFoundException e)
		{
			assertNotNull(e);
			assertTrue(e.getMessage().endsWith("[wrong password]"));
		}

		name = "other";
		pw = name;
		try
		{
			userManager.login(name, pw);
		}
		catch(UserNotFoundException e)
		{
			assertNotNull(e);
			assertFalse(e.getMessage().endsWith("[wrong password]"));
		}

		fail("not implemented"); // TODO write the test
	}
	
	public void testLogout() throws Exception
	{
		fail("not implemented"); // TODO write the test
	}
	
	public void testRegister() throws Exception
	{
		fail("not implemented"); // TODO write the test	
	}
}

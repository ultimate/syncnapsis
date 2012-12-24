/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.dao.UserDao;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.data.service.UserRoleManager;
import com.syncnapsis.exceptions.UserNotFoundException;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.security.BaseApplicationManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.StringUtil;

@TestCoversClasses({ UserManager.class, UserManagerImpl.class })
@TestExcludesMethods({ "*etSecurityManager", "afterPropertiesSet" })
public class UserManagerImplTest extends GenericNameManagerImplTestCase<User, Long, UserManager, UserDao>
{
	private SessionProvider	sessionProvider;
	private BaseApplicationManager	securityManager;
	private UserProvider	userProvider;
	private UserManager		userManager;
	private UserRoleManager	userRoleManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new User());
		setDaoClass(UserDao.class);
		setMockDao(mockContext.mock(UserDao.class));
		setMockManager(new UserManagerImpl(mockDao, userRoleManager));
	}

	@TestCoversMethods({ "login", "logout" })
	public void testLoginAndLogout() throws Exception
	{
		sessionProvider.set(new MockHttpSession());

		String name, pw;

		name = "user1";
		pw = name;
		assertEquals(userManager.getByName(name), userManager.login(name, pw));
		assertNotNull(userProvider.get());
		assertEquals(userManager.getByName(name), userProvider.get());
		
		assertTrue(userManager.logout());
		assertNull(userProvider.get());
	}

	public void testLoginInvalid() throws Exception
	{
		String name, pw;

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
	}

	public void testRegister() throws Exception
	{
		String username = "a_new_user";
		String email = "new@syncnapsis.com";
		String password = "a_password";
		
		User newUser = userManager.register(username, email, password, password);
		
		assertNotNull(newUser);
		assertNotNull(newUser.getId());
		assertEquals(username, newUser.getUsername());
		assertEquals(email, newUser.getEmail());
		assertEquals(StringUtil.encodePassword(password, securityManager.getEncryptionAlgorithm()), newUser.getPassword());
		
		assertNotNull(userManager.getByName(username));
	}

	public void testRegisterInvalid() throws Exception
	{
		String existingUserName = "user1";
		User existingUser = userManager.getByName(existingUserName);
		
		String username = "a_new_user";
		String email = "new@syncnapsis.com";
		String password = "a_password";
		
		assertNull(userManager.register(existingUser.getUsername(), email, password, password));
		assertNull(userManager.register(username, existingUser.getEmail(), password, password));
		assertNull(userManager.register(username, email, password, password.toUpperCase()));
	}
	
	public void testGetByEmail() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByEmail", new User(), "mail@example.com");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testIsEmailRegistered() throws Exception
	{
		MethodCall managerCall = new MethodCall("isEmailRegistered", true, "mail@example.com");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}

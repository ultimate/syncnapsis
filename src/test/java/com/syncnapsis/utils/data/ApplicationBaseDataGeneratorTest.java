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
package com.syncnapsis.utils.data;

import java.io.File;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserContact;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ApplicationContextUtil;

@TestExcludesMethods({ "get*", "set*", "generate*", "afterPropertiesSet" })
public class ApplicationBaseDataGeneratorTest extends BaseDaoTestCase
{
	protected ApplicationBaseDataGenerator	gen	= new ApplicationBaseDataGenerator();

	private UserManager						userManager;

	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		ApplicationContextUtil.autowire(applicationContext, gen);
		gen.setProjectDirectory(new File(".").getAbsolutePath());
		gen.afterPropertiesSet();
	}

	public void testCreateUser()
	{
		String name = "a new user";
		String rolename = ApplicationBaseConstants.ROLE_NORMAL_USER;

		User user = gen.createUser(name, rolename);

		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals(name, user.getUsername());
		assertNotNull(user.getRole());
		assertEquals(rolename, user.getRole().getRolename());
		assertNotNull(user.getMessengerContacts());
	}

	public void testCreateUserContact()
	{
		User user1 = userManager.getByName("user1");
		User user2 = userManager.getByName("user2");

		UserContact userContact = gen.createUserContact(user1, user2);

		assertNotNull(userContact);
		assertNotNull(userContact.getId());
		assertEquals(user1, userContact.getUser1());
		assertEquals(user2, userContact.getUser2());
	}
}

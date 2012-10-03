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

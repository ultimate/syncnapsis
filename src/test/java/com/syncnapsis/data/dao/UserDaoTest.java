package com.syncnapsis.data.dao;

import java.util.Date;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.dao.hibernate.UserDaoHibernate;
import com.syncnapsis.data.model.User;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.exceptions.UserExistsException;
import com.syncnapsis.exceptions.UserNotFoundException;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({ UserDao.class, UserDaoHibernate.class })
public class UserDaoTest extends GenericNameDaoTestCase<User, Long>
{
	private UserDao			userDao;
	private UserRoleDao		userRoleDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		String existingName = "user1";
		Long existingId = userDao.getByName(existingName).getId();

		User user = new User();
		user.setUsername("any name");
		user.setEmail("any@mail.com");
		user.setLastActiveDate(new Date(timeProvider.get()));
		user.setLocale(EnumLocale.getDefault());
		user.setPassword("pw");
		user.setRegistrationDate(new Date(timeProvider.get()));
		user.setRole(userRoleDao.getByName(ApplicationBaseConstants.ROLE_DEMO_USER));
		user.setTimeZoneID("tzid");

		setEntity(user);

		setEntityProperty("username");
		setEntityPropertyValue("any name2");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);

		setGenericNameDao(userDao);
	}

	@TestCoversMethods("save")
	public void testSaveInvalid() throws Exception
	{
		String oldUsername = null;
		String oldEmail = null;
		try
		{
			oldUsername = entity.getUsername();
			entity.setUsername(userDao.getAll().get(1).getUsername());
			userDao.save(entity);
			fail("Expected Exception not occurred!");
		}
		catch(UserExistsException e)
		{
			assertNotNull(e);
		}
		entity.setUsername(oldUsername);

		rollbackTransaction();
		startTransaction();

		try
		{
			oldEmail = entity.getEmail();
			entity.setEmail(userDao.getAll().get(1).getEmail());
			userDao.save(entity);
			fail("Expected Exception not occurred!");
		}
		catch(UserExistsException e)
		{
			assertNotNull(e);
		}
		entity.setEmail(oldEmail);
	}

	@TestCoversMethods("getByName")
	public void testGetByNameInvalid() throws Exception
	{
		try
		{
			userDao.getByName("notexistinguser");
			fail("Expected Exception not occurred");
		}
		catch(UserNotFoundException e)
		{
			assertNotNull(e);
		}
	}
}

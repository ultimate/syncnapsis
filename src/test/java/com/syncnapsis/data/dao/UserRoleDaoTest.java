package com.syncnapsis.data.dao;

import com.syncnapsis.constants.BaseApplicationConstants;
import com.syncnapsis.data.dao.hibernate.UserRoleDaoHibernate;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.exceptions.UserRoleExistsException;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({UserRoleDao.class, UserRoleDaoHibernate.class})
public class UserRoleDaoTest extends GenericNameDaoTestCase<UserRole, Long>
{
	private UserRoleDao userRoleDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		String existingName = BaseApplicationConstants.ROLE_DEMO_USER;
		Long existingId = userRoleDao.getByName(existingName).getId();
		
		UserRole userRole = new UserRole();
		userRole.setRolename("any name");
		
		setEntity(userRole);
		
		setEntityProperty("rolename");
		setEntityPropertyValue("any name2");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);
		
		setGenericNameDao(userRoleDao);
	}
	
	@TestCoversMethods("save")
	public void testSaveInvalid() throws Exception
	{
		try
		{
			entity.setRolename(userRoleDao.getAll().get(0).getRolename());
			userRoleDao.save(entity);
			fail("Expected Exception not occurred!");
		}
		catch(UserRoleExistsException e)
		{
			assertNotNull(e);
		}
	}
}

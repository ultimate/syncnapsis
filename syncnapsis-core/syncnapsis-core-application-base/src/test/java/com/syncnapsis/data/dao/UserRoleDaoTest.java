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
package com.syncnapsis.data.dao;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.dao.hibernate.UserRoleDaoHibernate;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.exceptions.UserRoleExistsException;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({ UserRoleDao.class, UserRoleDaoHibernate.class })
public class UserRoleDaoTest extends GenericNameDaoTestCase<UserRole, Long>
{
	private UserRoleDao	userRoleDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		String existingName = "DEMO_USER";
		Long existingId = userRoleDao.getByName(existingName).getId();

		UserRole userRole = new UserRole();
		userRole.setRolename("any name");
		userRole.setMask(65536);

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

	public void testGetByMask() throws Exception
	{
		maskTest(ApplicationBaseConstants.ROLE_ADMIN, "ADMIN");
		maskTest(ApplicationBaseConstants.ROLE_MODERATOR, "MODERATOR");
		maskTest(ApplicationBaseConstants.ROLE_NORMAL_USER, "NORMAL_USER");
		maskTest(ApplicationBaseConstants.ROLE_DEMO_USER, "DEMO_USER");
	}

	private void maskTest(int mask, String name)
	{
		UserRole role = userRoleDao.getByMask(mask);
		assertNotNull(role);
		assertEquals(mask, role.getMask());
		assertEquals(name, role.getRolename());
	}
}

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

import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.dao.hibernate.PlayerRoleDaoHibernate;
import com.syncnapsis.data.model.PlayerRole;
import com.syncnapsis.exceptions.PlayerRoleExistsException;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({PlayerRoleDao.class, PlayerRoleDaoHibernate.class})
public class PlayerRoleDaoTest extends GenericNameDaoTestCase<PlayerRole, Long>
{
	private PlayerRoleDao playerRoleDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		String existingName = "DEMO_PLAYER";
		Long existingId = playerRoleDao.getByName(existingName).getId();
		
		PlayerRole playerRole = new PlayerRole();
		playerRole.setRolename("any name");
		playerRole.setMask(65536);
		
		setEntity(playerRole);
		
		setEntityProperty("rolename");
		setEntityPropertyValue("any name2");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);
		
		setGenericNameDao(playerRoleDao);
	}
	
	@TestCoversMethods("save")
	public void testSaveInvalid() throws Exception
	{
		try
		{
			entity.setRolename(playerRoleDao.getAll().get(0).getRolename());
			playerRoleDao.save(entity);
			fail("Expected Exception not occurred!");
		}
		catch(PlayerRoleExistsException e)
		{
			assertNotNull(e);
		}
	}

	public void testGetByMask() throws Exception
	{
		maskTest(GameBaseConstants.ROLE_PREMIUM_PLAYER, "PREMIUM_PLAYER");
		maskTest(GameBaseConstants.ROLE_NORMAL_PLAYER, "NORMAL_PLAYER");
		maskTest(GameBaseConstants.ROLE_DEMO_PLAYER, "DEMO_PLAYER");
	}

	private void maskTest(int mask, String name)
	{
		PlayerRole role = playerRoleDao.getByMask(mask);
		assertNotNull(role);
		assertEquals(mask, role.getMask());
		assertEquals(name, role.getRolename());
	}
}

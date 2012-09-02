package com.syncnapsis.data.dao;

import com.syncnapsis.constants.BaseGameConstants;
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
		
		String existingName = BaseGameConstants.ROLE_DEMO_PLAYER;
		Long existingId = playerRoleDao.getByName(existingName).getId();
		
		PlayerRole playerRole = new PlayerRole();
		playerRole.setRolename("any name");
		
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
}

package com.syncnapsis.data.dao;

import java.sql.Date;

import com.syncnapsis.constants.BaseGameConstants;
import com.syncnapsis.data.dao.hibernate.PlayerDaoHibernate;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.User;
import com.syncnapsis.exceptions.UserNotFoundException;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({ PlayerDao.class, PlayerDaoHibernate.class })
public class PlayerDaoTest extends GenericDaoTestCase<Player, Long>
{
	private PlayerDao		playerDao;
	private PlayerRoleDao	playerRoleDao;
	private UserDao			userDao;

	private String			existingName	= "user1";

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = playerDao.getByUsername(existingName).getId();

		Player player = new Player();
		player.setRole(playerRoleDao.getByName(BaseGameConstants.ROLE_DEMO_PLAYER));

		setEntity(player);

		setEntityProperty("roleExpireDate");
		setEntityPropertyValue(new Date(12345));

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(playerDao);
	}

	public void testGetByUser() throws Exception
	{
		logger.debug("testing getByUsername valid...");
		User user= userDao.getByName(existingName);
		Player player = playerDao.getByUser(user.getId());

		assertNotNull(player);
		assertEquals(existingName, player.getUser().getUsername());
	}

	public void testGetByUserame() throws Exception
	{
		logger.debug("testing getByUsername valid...");
		Player player = playerDao.getByUsername(existingName);

		assertNotNull(player);
		assertEquals(existingName, player.getUser().getUsername());
	}

	@TestCoversMethods("getByUsername")
	public void testGetByUsernameInvalid() throws Exception
	{
		try
		{
			playerDao.getByUsername("notexistingplayer");
			fail("Expected Exception not occurred");
		}
		catch(UserNotFoundException e)
		{
			assertNotNull(e);
		}
	}
}

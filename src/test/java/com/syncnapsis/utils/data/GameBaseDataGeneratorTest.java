package com.syncnapsis.utils.data;

import java.io.File;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.User;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ApplicationContextUtil;

@TestExcludesMethods({ "get*", "set*", "generate*", "afterPropertiesSet" })
public class GameBaseDataGeneratorTest extends BaseDaoTestCase
{
	protected GameBaseDataGenerator	gen	= new GameBaseDataGenerator();

	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		
		logger.debug("userManager is " + ApplicationContextUtil.getBean("userManager"));
		
		ApplicationContextUtil.autowire(applicationContext, gen);
		gen.setProjectDirectory(new File(".").getAbsolutePath());
		gen.afterPropertiesSet();
	}

	public void testCreatePlayer() throws Exception
	{
		String userrolename = ApplicationBaseConstants.ROLE_NORMAL_USER;
		String playerrolename = GameBaseConstants.ROLE_NORMAL_PLAYER;
		String name = "new user";
		
		Player player = gen.createPlayer(name, userrolename, playerrolename);
		
		assertNotNull(player);
		assertNotNull(player.getId());
		assertNotNull(player.getRole());
		assertEquals(playerrolename, player.getRole().getRolename());
		
		User user = player.getUser();

		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals(name, user.getUsername());
		assertNotNull(user.getRole());
		assertEquals(userrolename, user.getRole().getRolename());
		assertNotNull(user.getMessengerContacts());
	}

	public void testCreateEmpire() throws Exception
	{
		// TODO
	}
}

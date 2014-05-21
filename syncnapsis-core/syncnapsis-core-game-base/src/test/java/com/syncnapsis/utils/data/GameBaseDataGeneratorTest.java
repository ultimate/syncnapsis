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
		int userrolemask = ApplicationBaseConstants.ROLE_NORMAL_USER;
		int playerrolemask = GameBaseConstants.ROLE_NORMAL_PLAYER;
		String name = "new user";
		
		Player player = gen.createPlayer(name, userrolemask, playerrolemask);
		
		assertNotNull(player);
		assertNotNull(player.getId());
		assertNotNull(player.getRole());
		assertEquals(playerrolemask, player.getRole().getMask());
		
		User user = player.getUser();

		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals(name, user.getUsername());
		assertNotNull(user.getRole());
		assertEquals(userrolemask, user.getRole().getMask());
		assertNotNull(user.getMessengerContacts());
	}

	public void testCreateEmpire() throws Exception
	{
		// TODO
	}
}

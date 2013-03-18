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
package com.syncnapsis.data.service.impl;

import java.util.ArrayList;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.dao.PlayerDao;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.data.service.PlayerRoleManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.exceptions.PlayerRegistrationFailedException;
import com.syncnapsis.exceptions.PlayerSelectionInvalidException;
import com.syncnapsis.exceptions.PlayerSittingExistsException;
import com.syncnapsis.exceptions.PlayerSittingNotPossibleException;
import com.syncnapsis.exceptions.UserNotFoundException;
import com.syncnapsis.providers.PlayerProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.HibernateUtil;
import com.syncnapsis.utils.StringUtil;

@TestCoversClasses({ PlayerManager.class, PlayerManagerImpl.class })
@TestExcludesMethods({ "*etSecurityManager", "afterPropertiesSet" })
public class PlayerManagerImplTest extends GenericManagerImplTestCase<Player, Long, PlayerManager, PlayerDao>
{
	private SessionProvider		sessionProvider;
	private BaseGameManager		securityManager;
	private PlayerProvider		playerProvider;
	private PlayerManager		playerManager;
	private PlayerRoleManager	playerRoleManager;
	private UserManager			userManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Player());
		setDaoClass(PlayerDao.class);
		setMockDao(mockContext.mock(PlayerDao.class));
		setMockManager(new PlayerManagerImpl(mockDao, playerRoleManager, userManager));
	}

	@TestCoversMethods({ "addSitter", "removeSitter" })
	public void testAddAndRemoveSitter() throws Exception
	{
		Long playerId1 = playerManager.getByUsername("user1").getId();
		Long playerId2 = playerManager.getByUsername("user2").getId();
		Long playerId3 = playerManager.getByUsername("admin").getId();

		try
		{
			playerManager.addSitter(playerId1, playerId1);
			fail("Expected exception not occurred!");
		}
		catch(PlayerSelectionInvalidException e)
		{
			assertNotNull(e);
		}
		try
		{
			playerManager.addSitter(playerId1, playerId3);
			fail("Expected exception not occurred!");
		}
		catch(PlayerSittingExistsException e)
		{
			assertNotNull(e);
			assertEquals(playerId3, e.getPlayer().getId());
		}
		try
		{
			playerManager.addSitter(playerId2, playerId1);
			fail("Expected exception not occurred!");
		}
		catch(PlayerSittingNotPossibleException e)
		{
			assertNotNull(e);
			assertEquals(playerId2, e.getPlayer().getId());
		}
		try
		{
			playerManager.addSitter(playerId1, playerId2);
			fail("Expected exception not occurred!");
		}
		catch(PlayerSittingNotPossibleException e)
		{
			assertNotNull(e);
			assertEquals(playerId2, e.getPlayer().getId());
		}

		Player player1 = playerManager.get(playerId3);
		Player player2 = playerManager.get(playerId1);
		int sittersBefore = player1.getSitters().size();
		int sittedBefore = player2.getSitted().size();

		// add sitter valid
		player1 = playerManager.addSitter(player1.getId(), player2.getId());
		assertNotNull(player1);
		assertEquals(playerId3, player1.getId());

		HibernateUtil.getInstance().getSessionFactory().getCurrentSession().flush();
		HibernateUtil.getInstance().getSessionFactory().getCurrentSession().refresh(player1);
		HibernateUtil.getInstance().getSessionFactory().getCurrentSession().refresh(player2);

		assertEquals(sittersBefore + 1, player1.getSitters().size());
		assertEquals(sittedBefore + 1, player2.getSitted().size());

		// remove sitter
		player1 = playerManager.removeSitter(player1.getId(), player2.getId());
		assertNotNull(player1);
		assertEquals(playerId3, player1.getId());

		HibernateUtil.getInstance().getSessionFactory().getCurrentSession().flush();
		HibernateUtil.getInstance().getSessionFactory().getCurrentSession().refresh(player1);
		HibernateUtil.getInstance().getSessionFactory().getCurrentSession().refresh(player2);

		assertEquals(sittersBefore, player1.getSitters().size());
		assertEquals(sittedBefore, player2.getSitted().size());
	}

	@TestCoversMethods({ "checkSitter", "checkSitted" })
	public void testCheckSitting() throws Exception
	{
		User dummyUser1 = new User();
		dummyUser1.setUsername("test_dummy1");
		User dummyUser2 = new User();
		dummyUser2.setUsername("test_dummy2");
		User dummyUser3 = new User();
		dummyUser3.setUsername("test_dummy3");

		Player dummy1 = new Player();
		dummy1.setUser(dummyUser1);
		Player dummy2 = new Player();
		dummy2.setUser(dummyUser2);
		Player dummy3 = new Player();
		dummy3.setUser(dummyUser3);

		Player player = new Player();
		player.setSitters(new ArrayList<Player>());
		player.setSitted(new ArrayList<Player>());

		// checkSitted betrifft liste der Sitter!!!

		// case 1 limited role
		player.setRole(playerRoleManager.getByName(GameBaseConstants.ROLE_NORMAL_PLAYER));
		// 1a 0/2 sitters
		assertTrue(playerManager.checkSitted(player));
		// 1b 1/2 sitters
		player.getSitters().add(dummy1);
		assertTrue(playerManager.checkSitted(player));
		// 1c 2/2 sitters
		player.getSitters().add(dummy2);
		assertFalse(playerManager.checkSitted(player));
		// 1d 3/2 sitters
		player.getSitters().add(dummy3);
		assertFalse(playerManager.checkSitted(player));
		// clear Sitter
		player.getSitters().clear();

		// case 2 unlimited role
		player.setRole(playerRoleManager.getByName(GameBaseConstants.ROLE_PREMIUM_PLAYER));
		// 1a 0/-1 sitters
		assertTrue(playerManager.checkSitted(player));
		// 2b 1/-1 sitters
		player.getSitters().add(dummy1);
		assertTrue(playerManager.checkSitted(player));
		// 2c 2/-1 sitters
		player.getSitters().add(dummy2);
		assertTrue(playerManager.checkSitted(player));
		// 2d 3/-1 sitters
		player.getSitters().add(dummy3);
		assertTrue(playerManager.checkSitted(player));
		// clear Sitter
		player.getSitters().clear();

		// checkSitter betrifft liste der Sitted!!!

		// case 1 limited role
		player.setRole(playerRoleManager.getByName(GameBaseConstants.ROLE_NORMAL_PLAYER));
		// 1a 0/2 sitted
		assertTrue(playerManager.checkSitter(player));
		// 1b 1/2 sitted
		player.getSitted().add(dummy1);
		assertTrue(playerManager.checkSitter(player));
		// 1c 2/2 sitted
		player.getSitted().add(dummy2);
		assertFalse(playerManager.checkSitter(player));
		// 1d 3/2 sitted
		player.getSitted().add(dummy3);
		assertFalse(playerManager.checkSitter(player));
		// clear Sitted
		player.getSitted().clear();

		// case 2 unlimited role
		player.setRole(playerRoleManager.getByName(GameBaseConstants.ROLE_PREMIUM_PLAYER));
		// 2a 0/-1 sitted
		assertTrue(playerManager.checkSitter(player));
		// 2b 1/-1 sitted
		player.getSitted().add(dummy1);
		assertTrue(playerManager.checkSitter(player));
		// 2c 2/-1 sitted
		player.getSitted().add(dummy2);
		assertTrue(playerManager.checkSitter(player));
		// 2d 3/-1 sitted
		player.getSitted().add(dummy3);
		assertTrue(playerManager.checkSitter(player));
		// clear Sitted
		player.getSitted().clear();
	}

	public void testGetByUser() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByUser", new Player(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetByUsername() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByUsername", new Player(), "user1");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	@TestCoversMethods({ "login", "logout" })
	public void testLoginAndLogout() throws Exception
	{
		sessionProvider.set(new MockHttpSession());

		String name, pw;

		name = "user1";
		pw = name;
		assertEquals(playerManager.getByUsername(name), playerManager.login(name, pw));
		assertNotNull(playerProvider.get());
		assertEquals(playerManager.getByUsername(name), playerProvider.get());

		assertTrue(playerManager.logout());
		assertNull(playerProvider.get());
	}

	public void testLoginInvalid() throws Exception
	{
		String name, pw;

		name = "user1";
		pw = "other";
		try
		{
			playerManager.login(name, pw);
		}
		catch(UserNotFoundException e)
		{
			assertNotNull(e);
			assertTrue(e.getMessage().endsWith("[wrong password]"));
		}

		name = "other";
		pw = name;
		try
		{
			playerManager.login(name, pw);
		}
		catch(UserNotFoundException e)
		{
			assertNotNull(e);
			assertFalse(e.getMessage().endsWith("[wrong password]"));
		}
	}

	public void testRegister() throws Exception
	{
		String username = "a_new_user";
		String email = "new@syncnapsis.com";
		String password = "a_password";

		Player newPlayer = playerManager.register(username, email, password, password);

		flush();

		assertNotNull(newPlayer);
		assertNotNull(newPlayer.getUser());
		assertEquals(username, newPlayer.getUser().getUsername());
		assertEquals(email, newPlayer.getUser().getEmail());
		assertEquals(StringUtil.encodePassword(password, securityManager.getEncryptionAlgorithm()), newPlayer.getUser().getPassword());

		assertNotNull(playerManager.getByUsername(username));
	}

	public void testRegisterInvalid() throws Exception
	{
		String existingUserName = "user1";
		User existingUser = userManager.getByName(existingUserName);

		String username = "a_new_user";
		String email = "new@syncnapsis.com";
		String password = "a_password";

		try
		{
			playerManager.register(existingUser.getUsername(), email, password, password);
			fail("expected Exception not occurred!");
		}
		catch(PlayerRegistrationFailedException e)
		{
			assertNotNull(e);
		}
		try
		{
			playerManager.register(username, existingUser.getEmail(), password, password);
			fail("expected Exception not occurred!");
		}
		catch(PlayerRegistrationFailedException e)
		{
			assertNotNull(e);
		}
		try
		{
			playerManager.register(username, email, password, password.toUpperCase());
			fail("expected Exception not occurred!");
		}
		catch(PlayerRegistrationFailedException e)
		{
			assertNotNull(e);
		}
	}
}

/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import java.util.Date;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;
import org.subethamail.wiser.Wiser;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.dao.UserDao;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.ActionManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.data.service.UserRoleManager;
import com.syncnapsis.exceptions.UserNotFoundException;
import com.syncnapsis.exceptions.UserRegistrationFailedException;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.security.BaseApplicationManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.HibernateUtil;
import com.syncnapsis.utils.ServletUtil;
import com.syncnapsis.websockets.service.rpc.RPCCall;
import com.syncnapsis.websockets.service.rpc.RPCHandler;

@TestCoversClasses({ UserManager.class, UserManagerImpl.class })
@TestExcludesMethods({ "*etSecurityManager", "afterPropertiesSet" })
public class UserManagerImplTest extends GenericNameManagerImplTestCase<User, Long, UserManager, UserDao>
{
	private SessionProvider			sessionProvider;
	private BaseApplicationManager	securityManager;
	private UserProvider			userProvider;
	private UserManager				userManager;
	private UserRoleManager			userRoleManager;
	private ActionManager			actionManager;

	private RPCHandler				rpcHandler;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new User());

		setDaoClass(UserDao.class);
		setMockDao(mockContext.mock(UserDao.class));
		setMockManager(new UserManagerImpl(mockDao, userRoleManager, actionManager) {
			public String getBeanName()
			{
				return "mockManager";
			}
		});

		((UserManagerImpl) mockManager).setSecurityManager(securityManager);
	}

	@TestCoversMethods({ "login", "logout" })
	public void testLoginAndLogout() throws Exception
	{
		sessionProvider.set(new MockHttpSession());

		String name, pw;

		name = "user1";
		pw = name;
		assertEquals(userManager.getByName(name), userManager.login(name, pw));
		assertNotNull(userProvider.get());
		assertEquals(userManager.getByName(name), userProvider.get());

		assertTrue(userManager.logout());
		assertNull(userProvider.get());
	}

	public void testLoginInvalid() throws Exception
	{
		String name, pw;

		name = "user1";
		pw = "other";
		try
		{
			userManager.login(name, pw);
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
			userManager.login(name, pw);
		}
		catch(UserNotFoundException e)
		{
			assertNotNull(e);
			assertFalse(e.getMessage().endsWith("[wrong password]"));
		}
	}

	public void testGetCurrent() throws Exception
	{
		sessionProvider.set(new MockHttpSession());

		User user = new User();

		userProvider.set(user);

		assertSame(user, userManager.getCurrent());
	}

	@TestCoversMethods({ "register", "verifyRegistration" })
	public void testRegister() throws Exception
	{
		String username = "a_new_user";
		String email = "new@syncnapsis.com";
		String password = "a_password";

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_ADDR, "localhost");
		session.setAttribute(ServletUtil.ATTRIBUTE_USER_AGENT, "testcase");
		sessionProvider.set(session);

		Wiser w = new Wiser();
		try
		{
			w.start();

			User newUser = userManager.register(username, email, password, password);

			assertNotNull(newUser);
			assertNotNull(newUser.getId());
			assertEquals(username, newUser.getUsername());
			assertEquals(email, newUser.getEmail());
			assertTrue(securityManager.validatePassword(password, newUser.getPassword()));
			assertNotNull(newUser.getAccountStatusExpireDate());
			assertTrue(newUser.getAccountStatusExpireDate().after(new Date(securityManager.getTimeProvider().get())));

			HibernateUtil.currentSession().flush();

			assertNotNull(userManager.getByName(username));

			assertEquals(1, w.getMessages().size());

			MimeMessage m = w.getMessages().get(0).getMimeMessage();
			assertEquals(1, m.getRecipients(RecipientType.TO).length);
			assertEquals(email, m.getRecipients(RecipientType.TO)[0].toString());

			String message = (String) m.getContent();
			int codeIndex = message.indexOf("/action/") + "/action/".length();
			String code = message.substring(codeIndex, codeIndex + ApplicationBaseConstants.PARAM_ACTION_CODE_LENGTH.asInt());
			logger.debug("'" + code + "'");
			assertNotNull(actionManager.getByCode(code));
			RPCCall rpcCall = actionManager.getRPCCall(code);
			assertNotNull(rpcCall);

			String result = (String) rpcHandler.doRPC(rpcCall, new Object[] {});
			assertEquals("ok", result);

			User user = userManager.get(newUser.getId());
			assertNotNull(user);
			assertNull(user.getAccountStatusExpireDate());
		}
		finally
		{
			w.stop();
		}
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
			userManager.register(existingUser.getUsername(), email, password, password);
			fail("expected Exception not occurred!");
		}
		catch(UserRegistrationFailedException e)
		{
			assertNotNull(e);
		}
		try
		{
			userManager.register(username, existingUser.getEmail(), password, password);
			fail("expected Exception not occurred!");
		}
		catch(UserRegistrationFailedException e)
		{
			assertNotNull(e);
		}
		try
		{
			userManager.register(username, email, password, password.toUpperCase());
			fail("expected Exception not occurred!");
		}
		catch(UserRegistrationFailedException e)
		{
			assertNotNull(e);
		}
	}

	public void testGetByEmail() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByEmail", new User(), "mail@example.com");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testIsEmailRegistered() throws Exception
	{
		MethodCall managerCall = new MethodCall("isEmailRegistered", true, "mail@example.com");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testIsEmailValid() throws Exception
	{
		assertFalse(userManager.isEmailValid(null));
		assertTrue(userManager.isEmailValid("a@example.com"));
		assertFalse(userManager.isEmailValid("spammer@example.com"));
	}

	public void testIsNameValid() throws Exception
	{
		assertFalse(userManager.isNameValid(null));
		assertTrue(userManager.isNameValid("goodguy"));
		assertFalse(userManager.isNameValid("badguy"));
	}

	@TestCoversMethods({ "updateMailAddress", "verifyMailAddress" })
	public void testUpdateMailAddress() throws Exception
	{
		User user = userManager.getByName("admin");

		String email = "mynewmailaddress@example.com";

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_ADDR, "localhost");
		session.setAttribute(ServletUtil.ATTRIBUTE_USER_AGENT, "testcase");
		sessionProvider.set(session);

		userProvider.set(user);

		Wiser w = new Wiser();
		try
		{
			w.start();

			String oldemail = user.getEmail();
			assertFalse(oldemail.equals(email));

			assertTrue(userManager.updateMailAddress(email));
			assertEquals(oldemail, user.getEmail());

			HibernateUtil.currentSession().flush();

			assertEquals(1, w.getMessages().size());

			MimeMessage m = w.getMessages().get(0).getMimeMessage();
			assertEquals(1, m.getRecipients(RecipientType.TO).length);
			assertEquals(email, m.getRecipients(RecipientType.TO)[0].toString());

			String message = (String) m.getContent();
			int codeIndex = message.indexOf("/action/") + "/action/".length();
			String code = message.substring(codeIndex, codeIndex + ApplicationBaseConstants.PARAM_ACTION_CODE_LENGTH.asInt());
			logger.debug("'" + code + "'");
			assertNotNull(actionManager.getByCode(code));
			RPCCall rpcCall = actionManager.getRPCCall(code);
			assertNotNull(rpcCall);
			assertEquals(2, rpcCall.getArgs().length);
			
			logger.debug(rpcCall.getObject());
			logger.debug(rpcCall.getMethod());
			logger.debug("" + rpcCall.getArgs()[0]);
			logger.debug("" + rpcCall.getArgs()[1]);

			String result = (String) rpcHandler.doRPC(rpcCall, new Object[] {});
			assertEquals("ok", result);

			// HibernateUtil.currentSession().flush();

			user = userManager.get(user.getId());
			assertEquals(email, user.getEmail());
		}
		finally
		{
			w.stop();
		}
	}

	public void testChangePassword() throws Exception
	{
		final User user = userManager.getByName("admin");

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_ADDR, "localhost");
		session.setAttribute(ServletUtil.ATTRIBUTE_USER_AGENT, "testcase");
		sessionProvider.set(session);

		userProvider.set(user);

		String oldHash = user.getPassword();

		String oldPassword = "admin";
		String newPassword = "abc123";

		assertTrue(securityManager.validatePassword(oldPassword, oldHash));
		assertFalse(securityManager.validatePassword(newPassword, oldHash));

		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(user);
				will(returnValue(user));
			}
		});

		mockManager.changePassword(oldPassword, newPassword, newPassword);

		String newHash = user.getPassword();

		assertNotNull(newHash);

		assertFalse(securityManager.validatePassword(oldPassword, newHash));
		assertTrue(securityManager.validatePassword(newPassword, newHash));

		assertFalse(oldHash.equals(newHash));
	}

	@TestCoversMethods({ "requestPasswordReset", "performPasswordReset" })
	public void testResetPassword() throws Exception
	{
		User user = userManager.getByName("admin");

		String oldHash = user.getPassword();
		String newHash;

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_ADDR, "localhost");
		session.setAttribute(ServletUtil.ATTRIBUTE_USER_AGENT, "testcase");
		sessionProvider.set(session);
		
		Wiser w = new Wiser();
		MimeMessage m;
		String message;
		try
		{
			w.start();

			assertTrue(userManager.requestPasswordReset(user.getUsername(), user.getEmail()));

			HibernateUtil.currentSession().flush();

			assertEquals(1, w.getMessages().size());

			m = w.getMessages().get(0).getMimeMessage();
			assertEquals(1, m.getRecipients(RecipientType.TO).length);
			assertEquals(user.getEmail(), m.getRecipients(RecipientType.TO)[0].toString());

			message = (String) m.getContent();
			int codeIndex = message.indexOf("/action/") + "/action/".length();
			String code = message.substring(codeIndex, codeIndex + ApplicationBaseConstants.PARAM_ACTION_CODE_LENGTH.asInt());
			logger.debug("'" + code + "'");
			assertNotNull(actionManager.getByCode(code));
			RPCCall rpcCall = actionManager.getRPCCall(code);
			assertNotNull(rpcCall);
			assertEquals(1, rpcCall.getArgs().length);

			logger.debug(rpcCall.getObject());
			logger.debug(rpcCall.getMethod());
			logger.debug("" + rpcCall.getArgs()[0]);

			String result = (String) rpcHandler.doRPC(rpcCall, new Object[] {});
			assertEquals("ok", result);

			// HibernateUtil.currentSession().flush();

			assertEquals(2, w.getMessages().size());

			m = w.getMessages().get(1).getMimeMessage();
			assertEquals(1, m.getRecipients(RecipientType.TO).length);
			assertEquals(user.getEmail(), m.getRecipients(RecipientType.TO)[0].toString());

			message = (String) m.getContent();
			int passwordIndex = message.indexOf(":\r\n") + ":\r\n".length();
			String newPassword = message.substring(passwordIndex, passwordIndex + ApplicationBaseConstants.PARAM_PASSWORD_GENERATED_LENGTH.asInt());
			logger.debug("'" + newPassword + "'");

			newHash = user.getPassword();

			assertFalse(oldHash.equals(newHash));
			assertTrue(securityManager.validatePassword(newPassword, newHash));
		}
		finally
		{
			w.stop();
		}
	}
}
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
package com.syncnapsis.utils.mail;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.subethamail.wiser.Wiser;

import com.syncnapsis.data.model.Action;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.security.BaseApplicationManager;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({ "checkMailer", "get" })
public class BaseApplicationMailerTest extends BaseSpringContextTestCase
{
	private BaseApplicationMailer	mailer;
	private BaseApplicationManager	securityManager;

	public void testAppCtxInitialization()
	{
		assertNotNull(mailer);
		assertEquals("en", mailer.getDefaultKey());
		assertEquals(2, mailer.getKeys().size());
		assertTrue(mailer.getKeys().contains("en"));
		assertTrue(mailer.getKeys().contains("de"));

		assertSame(mailer, securityManager.getMailer());
	}

	public void testSendVerifyRegistration() throws Exception
	{
		BaseApplicationMailer m = new BaseApplicationMailer("mail.properties");

		logger.debug("defaultKey=" + m.getDefaultKey());

		User user = new User();
		user.setUsername("new guy");
		user.setEmail("newguy@example.com");

		Action action = new Action();
		action.setCode("34ae8c3f0142aa3bc4");

		String subjectExpected = "Welcome to syncnapsis, " + user.getUsername();
		String textExpected = "Hello " + user.getUsername() + ",\n" + "thank you for your registration and welcome to syncnapsis.\n"
				+ "Please confirm your registration by clicking the following link:\n" + "http://www.syncnapsis.com/test/activate/"
				+ action.getCode() + "\n" + "Best regards";
		textExpected = textExpected.replace("\n", "\r\n"); // mailing replaced the line endings.

		String toExpected = user.getEmail();

		Wiser w = new Wiser();
		w.start();

		try
		{
			assertTrue(m.sendVerifyRegistration(user, action));

			assertEquals(1, w.getMessages().size());
			MimeMessage msg = w.getMessages().get(0).getMimeMessage();

			assertEquals(subjectExpected, msg.getSubject());
			assertEquals(textExpected, msg.getContent());
			assertEquals(1, msg.getAllRecipients().length);
			assertEquals(1, msg.getRecipients(RecipientType.TO).length);
			assertEquals(toExpected, ((InternetAddress) msg.getRecipients(RecipientType.TO)[0]).getAddress());
			assertEquals(1, msg.getFrom().length);
			assertEquals(m.getDefault().getGlobalProperty(Mailer.KEY_FROM), ((InternetAddress) msg.getFrom()[0]).getAddress());
		}
		finally
		{
			w.stop();
		}
	}

	public void testSendVerifyMailAddress() throws Exception
	{
		BaseApplicationMailer m = new BaseApplicationMailer(new File("target/test-classes/mail.properties"));

		String newEmail = "newMail@example.com";

		User user = new User();
		user.setUsername("new guy");
		user.setEmail("oldMail@example.com");

		Action action = new Action();
		action.setCode("34ae8c3f0142aa3bc4");

		String subjectExpected = "Verify your e-mail address";
		String textExpected = "Hello " + user.getUsername() + ",\n" + "your e-mail address has been changed in your profile settings.\n"
				+ "Please confirm these changes by clicking the following link:\n" + "http://www.syncnapsis.com/test/activate/" + action.getCode()
				+ "\n" + "Beste regards";
		textExpected = textExpected.replace("\n", "\r\n"); // mailing replaced the line endings.

		Wiser w = new Wiser();
		w.start();

		try
		{
			assertTrue(m.sendVerifyMailAddress(user, newEmail, action));

			assertEquals(1, w.getMessages().size());
			MimeMessage msg = w.getMessages().get(0).getMimeMessage();

			assertEquals(subjectExpected, msg.getSubject());
			assertEquals(textExpected, msg.getContent());
			assertEquals(1, msg.getAllRecipients().length);
			assertEquals(1, msg.getRecipients(RecipientType.TO).length);
			assertEquals(newEmail, ((InternetAddress) msg.getRecipients(RecipientType.TO)[0]).getAddress());
			assertEquals(1, msg.getFrom().length);
			assertEquals(m.getDefault().getGlobalProperty(Mailer.KEY_FROM), ((InternetAddress) msg.getFrom()[0]).getAddress());
		}
		finally
		{
			w.stop();
		}
	}

	public void testSendUserRoleChangedNotification() throws Exception
	{
		BaseApplicationMailer m = new BaseApplicationMailer(new File("target/test-classes/mail.properties"));

		User user = new User();
		user.setUsername("new guy");
		user.setEmail("newguy@example.com");
		user.setRoleExpireDate(new Date(1234));

		UserRole newRole = new UserRole();
		newRole.setRolename("newRole");
		user.setRole(newRole);

		UserRole oldRole = new UserRole();
		oldRole.setRolename("oldRole");

		String reason = "role expired";

		String subjectExpected = "User-Role updated";
		String textExpected = "Hello " + user.getUsername() + ",\n" + "your user-role has changed:\n" + "Reason: " + reason + "\n" + "Old role: "
				+ oldRole.getRolename() + "\n" + "New role: " + user.getRole().getRolename() + "\n" + "Expires: " + user.getRoleExpireDate() + "\n"
				+ "Best regards";
		textExpected = textExpected.replace("\n", "\r\n"); // mailing replaced the line endings.

		String toExpected = user.getEmail();

		Wiser w = new Wiser();
		w.start();

		try
		{
			assertTrue(m.sendUserRoleChangedNotification(user, oldRole, reason));

			assertEquals(1, w.getMessages().size());
			MimeMessage msg = w.getMessages().get(0).getMimeMessage();

			assertEquals(subjectExpected, msg.getSubject());
			assertEquals(textExpected, msg.getContent());
			assertEquals(1, msg.getAllRecipients().length);
			assertEquals(1, msg.getRecipients(RecipientType.TO).length);
			assertEquals(toExpected, ((InternetAddress) msg.getRecipients(RecipientType.TO)[0]).getAddress());
			assertEquals(1, msg.getFrom().length);
			assertEquals(m.getDefault().getGlobalProperty(Mailer.KEY_FROM), ((InternetAddress) msg.getFrom()[0]).getAddress());
		}
		finally
		{
			w.stop();
		}
	}

	public void testSendGeneralNotification() throws Exception
	{
		BaseApplicationMailer m = new BaseApplicationMailer(new File("target/test-classes/mail.properties"));

		String notificationSubject = "a subject";
		String notificationText = "a text for you: {user.username}";

		User user1 = new User();
		user1.setUsername("user1");
		user1.setEmail("user1@example.com");

		User user2 = new User();
		user2.setUsername("user2");
		user2.setEmail("user2@example.com");

		List<User> users = new LinkedList<User>();
		users.add(user1);
		users.add(user2);

		Wiser w = new Wiser();
		w.start();

		try
		{
			assertEquals(users.size(), m.sendGeneralNotification(users, notificationSubject, notificationText));

			assertEquals(users.size(), w.getMessages().size());
			MimeMessage msg;

			for(int i = 0; i < w.getMessages().size(); i++)
			{
				msg = w.getMessages().get(i).getMimeMessage();

				assertEquals(notificationSubject, msg.getSubject());
				assertEquals(notificationText.replace("{user.username}", users.get(i).getUsername()), msg.getContent());
				assertEquals(1, msg.getAllRecipients().length);
				assertEquals(1, msg.getRecipients(RecipientType.TO).length);
				assertEquals(users.get(i).getEmail(), ((InternetAddress) msg.getRecipients(RecipientType.TO)[0]).getAddress());
				assertEquals(1, msg.getFrom().length);
				assertEquals(m.getDefault().getGlobalProperty(Mailer.KEY_FROM), ((InternetAddress) msg.getFrom()[0]).getAddress());
			}
		}
		finally
		{
			w.stop();
		}
	}
}

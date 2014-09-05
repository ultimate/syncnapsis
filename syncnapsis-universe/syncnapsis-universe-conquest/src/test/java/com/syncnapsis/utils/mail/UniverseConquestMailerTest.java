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
package com.syncnapsis.utils.mail;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.subethamail.wiser.Wiser;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.User;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({ "checkMailer", "get" })
public class UniverseConquestMailerTest extends BaseSpringContextTestCase
{
	private UniverseConquestMailer	mailer;
	private BaseGameManager			securityManager;

	public void testAppCtxInitialization()
	{
		assertNotNull(mailer);
		assertEquals("en", mailer.getDefaultKey());
		assertEquals(2, mailer.getKeys().size());
		assertTrue(mailer.getKeys().contains("en"));
		assertTrue(mailer.getKeys().contains("de"));

		assertSame(mailer, securityManager.getMailer());
	}

	public void testSendMatchStartFailedNotification() throws Exception
	{
		UniverseConquestMailer m = new UniverseConquestMailer("mail.properties");

		User user = new User();
		user.setUsername("a user");
		user.setEmail("someguy@syncnapsis.com");

		Player creator = new Player();
		creator.setUser(user);

		Match match = new Match();
		match.setCreator(creator);
		match.setTitle("a new match");

		String reason = "min participants not reached";

		String subjectExpected = "Automatic match start failed";
		String textExpected = "Hello " + creator.getUser().getUsername() + ",\n" + "your planned match '" + match.getTitle()
				+ "' could not be started:\n" + "Reason: " + reason + "\n"
				+ "Please adjust the match settings and start the match manually.\nBest regards";
		textExpected = textExpected.replace("\n", "\r\n"); // mailing replaced the line endings.

		String toExpected = user.getEmail();

		Wiser w = new Wiser();
		w.start();

		try
		{
			assertTrue(m.sendMatchStartFailedNotification(match, reason));

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
}

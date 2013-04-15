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

import java.util.Date;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.subethamail.wiser.Wiser;

import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRole;
import com.syncnapsis.data.model.User;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({"checkMailer", "get"})
public class BaseGameMailerTest extends LoggerTestCase
{
	public void testSendPlayerRoleChangedNotification() throws Exception
	{
		BaseGameMailer m = new BaseGameMailer("mail.properties");

		User user = new User();
		user.setUsername("new guy");
		user.setEmail("newguy@example.com");
		user.setRoleExpireDate(new Date(1234));
		
		Player player = new Player();
		player.setRoleExpireDate(new Date(5678));
		player.setUser(user);

		PlayerRole newRole = new PlayerRole();
		newRole.setRolename("newRole");
		player.setRole(newRole);

		PlayerRole oldRole = new PlayerRole();
		oldRole.setRolename("oldRole");

		String reason = "role expired";

		String subjectExpected = "Player-Role updated";
		String textExpected = "Hello " + player.getUser().getUsername() + ",\n" + "your player-role has changed:\n" + "Reason: " + reason + "\n" + "Old role: "
				+ oldRole.getRolename() + "\n" + "New role: " + player.getRole().getRolename() + "\n" + "Expires: " + player.getRoleExpireDate() + "\n"
				+ "Best regards";
		textExpected = textExpected.replace("\n", "\r\n"); // mailing replaced the line endings.

		String toExpected = user.getEmail();

		Wiser w = new Wiser();
		w.start();

		try
		{
			assertTrue(m.sendPlayerRoleChangedNotification(player, oldRole, reason));

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



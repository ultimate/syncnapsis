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

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.subethamail.wiser.Wiser;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({ "*etMailContentType" })
public class MailerTest extends LoggerTestCase
{
	@TestCoversMethods({ "*etGlobalProperty", "*etProtocolProperty", "getProtocol", "getProperties" })
	public void testProperties() throws Exception
	{
		Mailer m;
		
		// load the properties by file
		m = new Mailer(new File("target/test-classes/mail.properties"));
		assertNotNull(m.getProperties());
		assertNotNull(m.getGlobalProperty(Mailer.KEY_DEBUG));

		// load the properties by name
		m = new Mailer("mail.properties");
		assertNotNull(m.getProperties());
		assertNotNull(m.getGlobalProperty(Mailer.KEY_DEBUG));

		String protocol = "myprotocol";

		// check a property defined for the protocol
		assertEquals("ahost", m.getProtocolProperty(Mailer.KEY_HOST, protocol));
		// check a property NOT defined for the protocol
		assertEquals("info@syncnapsis.com", m.getProtocolProperty(Mailer.KEY_USER, protocol));

		// set a new value for the protocol property
		m.setProtocolProperty(Mailer.KEY_USER, protocol, "auser");
		assertEquals("auser", m.getProtocolProperty(Mailer.KEY_USER, protocol));

		// set a new global property
		m.setGlobalProperty(Mailer.KEY_PORT, "" + 1234);
		assertEquals("1234", m.getGlobalProperty(Mailer.KEY_PORT));
	}

	public void testToAddresses() throws Exception
	{
		Mailer m = new Mailer(new File("target/test-classes/mail.properties"));

		String[] addrS = new String[] { "test@syncnapsis.com", "info@example.com" };

		assertNotNull(m.toAddresses(null));
		assertEquals(0, m.toAddresses(null).length);

		Address[] addrA = m.toAddresses(addrS);

		assertNotNull(addrA);
		assertEquals(addrS.length, addrA.length);

		for(int i = 0; i < addrS.length; i++)
		{
			assertTrue(addrA[i] instanceof InternetAddress);
			assertEquals(addrS[i], ((InternetAddress) addrA[i]).getAddress());
		}
	}

	public void testSend() throws Exception
	{
		Mailer m = new Mailer(new File("target/test-classes/mail.properties"));

		String subject = "a subject";
		String text = "a message text";
		String to = "test@example.com";
		String from = "register@syncnapsis.com";

		String[] toA = new String[] { "to@example.com", "to2@example.com" };
		String[] ccA = new String[] { "cc@example.com", "cc2@example.com" };
		String[] bccA = new String[] { "bcc@example.com" };

		// assure different froms
		assertTrue(!from.equals(m.getGlobalProperty(Mailer.KEY_FROM)));

		MimeMessage msg;

		Wiser w = new Wiser();
		w.start();
		try
		{
			assertEquals(0, w.getMessages().size());

			m.send(subject, text, to);

			assertEquals(1, w.getMessages().size());
			msg = w.getMessages().get(0).getMimeMessage();
			assertEquals(subject, msg.getSubject());
			assertEquals(text, msg.getContent());
			assertEquals(1, msg.getAllRecipients().length);
			assertEquals(1, msg.getRecipients(RecipientType.TO).length);
			assertEquals(to, ((InternetAddress) msg.getRecipients(RecipientType.TO)[0]).getAddress());
			assertEquals(1, msg.getFrom().length);
			assertEquals(m.getGlobalProperty(Mailer.KEY_FROM), ((InternetAddress) msg.getFrom()[0]).getAddress());
			
			w.getMessages().clear();
			
			m.send(subject, text, to, from);

			assertEquals(1, w.getMessages().size());
			msg = w.getMessages().get(0).getMimeMessage();
			assertEquals(subject, msg.getSubject());
			assertEquals(text, msg.getContent());
			assertEquals(1, msg.getAllRecipients().length);
			assertEquals(1, msg.getRecipients(RecipientType.TO).length);
			assertEquals(to, ((InternetAddress) msg.getRecipients(RecipientType.TO)[0]).getAddress());
			assertEquals(1, msg.getFrom().length);
			assertEquals(from, ((InternetAddress) msg.getFrom()[0]).getAddress());
			
			w.getMessages().clear();

			m.send(subject, text, toA, ccA, bccA, from);
			
			// don't really know why, but more messages are sent...
			int msgCount = toA.length + ccA.length + bccA.length;

			assertEquals(msgCount, w.getMessages().size());

			for(int i = 0; i < msgCount; i++)
			{
				msg = w.getMessages().get(i).getMimeMessage();
				assertEquals(subject, msg.getSubject());
				assertEquals(text, msg.getContent());

				// bcc recipients are not contained...
				assertEquals(toA.length + ccA.length, msg.getAllRecipients().length);
				assertEquals(toA.length, msg.getRecipients(RecipientType.TO).length);
				assertEquals(ccA.length, msg.getRecipients(RecipientType.CC).length);
				for(int i2 = 0; i2 < toA.length; i2++)
					assertEquals(toA[i2], ((InternetAddress) msg.getRecipients(RecipientType.TO)[i2]).getAddress());
				for(int i2 = 0; i2 < ccA.length; i2++)
					assertEquals(ccA[i2], ((InternetAddress) msg.getRecipients(RecipientType.CC)[i2]).getAddress());
				assertEquals(1, msg.getFrom().length);
				assertEquals(from, ((InternetAddress) msg.getFrom()[0]).getAddress());
			}
		}
		finally
		{
			w.stop();
		}
	}
}

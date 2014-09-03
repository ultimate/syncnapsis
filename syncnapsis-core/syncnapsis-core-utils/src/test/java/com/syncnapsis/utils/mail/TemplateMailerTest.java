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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.subethamail.wiser.Wiser;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.MessageUtil;

/**
 * @author ultimate
 */
public class TemplateMailerTest extends LoggerTestCase
{
	@TestCoversMethods({ "getSubject", "getText" })
	public void testGetTemplate() throws Exception
	{
		TemplateMailer m = new TemplateMailer("mail.properties");

		String template = "test";
		String subject = "hello {user}";
		String subject2 = "hello new guy";
		String text = "you are very welcome, {user}\nactivate: {code}";
		String text2 = "you are very welcome, new guy\nactivate: xyz123";

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("user", "new guy");
		values.put("code", "xyz123");

		m.subjects.put(template, subject);
		m.texts.put(template, text);

		assertEquals(subject, m.getSubject(template));
		assertEquals(text, m.getText(template));
		assertEquals(subject2, m.getSubject(template, values));
		assertEquals(text2, m.getText(template, values));
	}

	@TestCoversMethods({ "readTemplates", "getTemplateNames" })
	public void testReadTemplates() throws Exception
	{
		TemplateMailer m = new TemplateMailer("mail.properties");

		assertEquals(2, m.getTemplateNames().size());
		assertTrue(m.getTemplateNames().contains("register"));
		assertTrue(m.getTemplateNames().contains("notification"));

		String register_subject = "Welcome to syncnapsis, {user}";
		String register_text = "Hello {user},\n" + "thank you for your registration and welcome to syncnapsis.\n"
				+ "Please confirm your registration by clicking the following link:\n" + "http://www.syncnapsis.com/test/activate/{code}\n"
				+ "Best regards";
		String notification_subject = "Important notification";
		String notification_text = FileUtil.readFile(new File("src/test/resources/test.txt"));

		assertEquals(register_subject, m.getSubject("register"));
		assertEquals(register_text, m.getText("register"));
		assertEquals(notification_subject, m.getSubject("notification"));
		assertEquals(notification_text, m.getText("notification"));
	}

	public void testSend() throws Exception
	{
		TemplateMailer m = new TemplateMailer("mail.properties");

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("user", "new guy");
		values.put("code", "xyz123");

		String register_subject = "Welcome to syncnapsis, {user}";
		register_subject = MessageUtil.fromTemplate(register_subject, values);
		String register_text = "Hello {user},\n" + "thank you for your registration and welcome to syncnapsis.\n"
				+ "Please confirm your registration by clicking the following link:\n" + "http://www.syncnapsis.com/test/activate/{code}\n"
				+ "Best regards";
		register_text = MessageUtil.fromTemplate(register_text, values);
		register_text = register_text.replace("\n", "\r\n"); // mailing replaced the line endings.

		String to = "test@example.com";

		MimeMessage msg;

		Wiser w = new Wiser();
		w.start();

		try
		{
			assertEquals(0, w.getMessages().size());

			m.send("register", values, to);

			assertEquals(1, w.getMessages().size());
			msg = w.getMessages().get(0).getMimeMessage();
			assertEquals(register_subject, msg.getSubject());
			assertEquals(register_text, msg.getContent());
			assertEquals(1, msg.getAllRecipients().length);
			assertEquals(1, msg.getRecipients(RecipientType.TO).length);
			assertEquals(to, ((InternetAddress) msg.getRecipients(RecipientType.TO)[0]).getAddress());
			assertEquals(1, msg.getFrom().length);
			assertEquals(m.getGlobalProperty(Mailer.KEY_FROM), ((InternetAddress) msg.getFrom()[0]).getAddress());
		}
		finally
		{
			w.stop();
		}
	}
}

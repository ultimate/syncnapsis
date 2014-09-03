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

import java.io.IOException;
import java.util.Properties;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({ "*etDefaultKey", "getMailerClass", "checkMailer" })
public class MultiMailerTest extends LoggerTestCase
{
	private Properties			global;
	private Properties			individual;

	private static final String	de	= "mail.de.properties";
	private static final String	en	= "mail.en.properties";

	@Override
	public void setUp() throws Exception
	{
		super.setUp();

		if(global == null)
		{
			global = new Properties();
			global.setProperty(MultiMailer.KEY_DEFAULT, "en");
			global.setProperty(MultiMailer.KEY_PREFIX + "de", de);
			global.setProperty(MultiMailer.KEY_PREFIX + "en", en);

			individual = new Properties();
			individual.setProperty("prop", "value");
		}
	}

	@TestCoversMethods({ "createMailer*", "getMailerContructor*", "getMailerProperties", "get", "getDefault", "set", "getKeys" })
	public void testCreateMailers()
	{
		final ThreadLocal<Integer> count = new ThreadLocal<Integer>();
		count.set(0);

		MultiMailer<Mailer> m = new MultiMailer<Mailer>(Mailer.class, global) {

			@Override
			protected Properties getMailerProperties(String key, String configValue) throws IOException
			{
				count.set(count.get() + 1);
				if(key.equals("de"))
					assertEquals(de, configValue);
				else if(key.equals("en"))
					assertEquals(en, configValue);
				return individual;
			}
		};

		assertEquals(2, (int) count.get());

		// mailers set
		assertEquals("en", m.getDefaultKey());
		assertNotNull(m.get("de"));
		assertNotNull(m.get("en"));
		assertNotNull(m.getDefault());
		assertSame(m.get("en"), m.getDefault());

		// mailers not set - fall back to default
		assertNotNull(m.get("fr"));
		assertSame(m.getDefault(), m.get("fr"));

		// keys
		assertEquals(2, m.getKeys().size());
		assertTrue(m.getKeys().contains("en"));
		assertTrue(m.getKeys().contains("de"));
	}

	public void testAll() throws Exception
	{
		MultiMailer<Mailer> m = new MultiMailer<Mailer>(Mailer.class, global) {
			@Override
			protected Properties getMailerProperties(String key, String configValue) throws IOException
			{
				return individual;
			}
		};

		assertNotNull(m.all());

		String mailContentType = "text/html";

		assertNull(m.get("en").getMailContentType());
		assertNull(m.get("de").getMailContentType());
		// set default only:
		m.getDefault().setMailContentType(mailContentType);
		assertEquals(mailContentType, m.getDefault().getMailContentType());
		assertNull(m.get("de").getMailContentType());
		// all should return default value
		assertEquals(mailContentType, m.all().getMailContentType());
		// set all
		m.all().setMailContentType(mailContentType);
		assertEquals(mailContentType, m.get("en").getMailContentType());
		assertEquals(mailContentType, m.get("de").getMailContentType());

	}
}

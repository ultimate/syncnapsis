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
import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.util.Assert;

import com.syncnapsis.utils.PropertiesUtil;

/**
 * @author ultimate
 * 
 */
public class Mailer
{
	protected Properties	properties;

	public Mailer(File propertiesFile) throws IOException
	{
		this(PropertiesUtil.loadProperties(propertiesFile));
	}

	public Mailer(Properties properties)
	{
		Assert.notNull(properties, "properties must not be null!");
		this.properties = properties;
	}

	public void setFrom()
	{

	}

	public void send(String to, String subject, String text)
	{

	}

	public void send(String from, String to, String subject, String text)
	{
	}

	public void send(String from, String[] to, String[] cc, String[] bcc, String subject, String text, String type)
	{
		Session session = Session.getDefaultInstance(properties, null);
		MimeMessage msg = new MimeMessage(session);
//		Address[] toAddr = toAddresses(to);
//		Address[] ccAddr = toAddresses(cc);
//		Address[] bccAddr = toAddresses(bcc);
////		int recipients = (to != null ? to.length : 0) + (cc != null ? cc.length : cc) + (bcc != null ? bcc.length : bcc);
//		if(to != null)
//		{
//			for(String rec: to)
//				msg.addRecipient(RecipientType.TO, new InternetAddress(rec));
//		}
	}
	
	public Address[] toAddresses(String[] addr) throws AddressException
	{
		if(addr == null)
			return new Address[0];
		Address[] add = new Address[addr.length];
		for(int i = 0; i < addr.length; i++)
		{
			add[i] = new InternetAddress(addr[i]);
		}
		return add;
	}
}

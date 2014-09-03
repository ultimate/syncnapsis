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
import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.syncnapsis.utils.PropertiesUtil;

/**
 * Mailer offering basic support for e-mail-sending.<br>
 * <i>(maybe support for e-mail-receiving will come in future versions).</i><br>
 * <br>
 * To use this class a javax.mail compliant properties is required. After initialization different
 * variants of Mailer.send(...) may be used for message sending.
 * 
 * @author ultimate
 */
public class Mailer
{
	// @formatter:off
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_DEBUG = "mail.debug";
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_FROM = "mail.from";
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_MIME_ADDRESS_STRICT = "mail.mime.address.strict";
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_HOST = "mail.host";
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_STORE_PROTOCOL = "mail.store.protocol";
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_TRANSPORT_PROTOCOL = "mail.transport.protocol";
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_USER = "mail.user";
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_PASSWORD = "mail.password";
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_CLASS = "mail.class";
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_PORT = "mail.port";
	/**
	 * @see <a href="http://docs.oracle.com/javaee/6/api/javax/mail/package-summary.html">javax.mail package-summary</a>
	 */
	public static final String KEY_AUTH = "mail.auth";
	/**
	 * A placeholder within the properties keys
	 */
	private static final String KEY_PREFIX = "mail.";
	// @formatter:on

	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger					= LoggerFactory.getLogger(getClass());

	/**
	 * The javax.mail properties
	 */
	protected Properties				properties;

	/**
	 * An optional mail content type (e.g. "text/html")
	 */
	protected String					mailContentType;
	
	/**
	 * Construct a new Mailer by loading the Properties from the given name
	 * 
	 * @param propertiesFile - the properties-file name
	 * @throws IOException if loading the properties fails
	 */
	public Mailer(String propertiesFile) throws IOException
	{
		this(PropertiesUtil.loadProperties(propertiesFile));
	}

	/**
	 * Construct a new Mailer by loading the Properties from the given file
	 * 
	 * @param propertiesFile - the properties-file
	 * @throws IOException if loading the properties fails
	 */
	public Mailer(File propertiesFile) throws IOException
	{
		this(PropertiesUtil.loadProperties(propertiesFile));
	}

	/**
	 * Construct a new Mailer with the given properties
	 * 
	 * @param properties - the Properties
	 */
	public Mailer(Properties properties)
	{
		Assert.notNull(properties, "properties must not be null!");
		this.properties = properties;

		if("true".equals(properties.getProperty(KEY_DEBUG)))
			logger.info("javax.mail debugging is enabled!");

		// check properties content
		String protocol = getProtocol();
		if(protocol == null)
			logger.info("javax.mail no transport protocol specified!");

		if(getProtocolProperty(KEY_HOST, protocol) == null)
			logger.warn(KEY_FROM + " is null!");
		if(getProtocolProperty(KEY_FROM, protocol) == null)
			logger.warn(KEY_FROM + " is null!");
		if(getProtocolProperty(KEY_USER, protocol) == null)
			logger.warn(KEY_USER + " is null!");
		if(getProtocolProperty(KEY_PASSWORD, protocol) == null)
			logger.warn(KEY_PASSWORD + " is null!");
	}

	/**
	 * The javax.mail properties
	 * 
	 * @see Mailer#getGlobalProperty(String)
	 * @see Mailer#setGlobalProperty(String, String)
	 * @see Mailer#getProtocolProperty(String, String)
	 * @see Mailer#setProtocolProperty(String, String, String)
	 * 
	 * @return properties
	 */
	public Properties getProperties()
	{
		return properties;
	}

	/**
	 * An optional mail content type (e.g. "text/html")
	 * 
	 * @return mailContentType
	 */
	public String getMailContentType()
	{
		return mailContentType;
	}

	/**
	 * An optional mail content type (e.g. "text/html")
	 * 
	 * @param mailContentType - the content type
	 */
	public void setMailContentType(String mailContentType)
	{
		this.mailContentType = mailContentType;
	}

	/**
	 * Get the protocol as specified by "mail.transport.protocol" in the properties.
	 * 
	 * @return the protocol name
	 */
	public String getProtocol()
	{
		return getGlobalProperty(KEY_TRANSPORT_PROTOCOL);
	}

	/**
	 * /**
	 * Get a global property in the underlying properties (e.g. "mail.user")
	 * 
	 * @param key - the property key
	 * @return the property value
	 */
	public String getGlobalProperty(String key)
	{
		return properties.getProperty(key);
	}

	/**
	 * Get a global property in the underlying properties (e.g. "mail.user")
	 * 
	 * @param key - the property key
	 * @paramn value - the (new) property value
	 * @return the old property value
	 */
	public String setGlobalProperty(String key, String value)
	{
		return (String) properties.put(key, value);
	}

	/**
	 * Get a property specific property in the underlying properties (e.g.
	 * "mail.<i>protocol</i>.user")
	 * 
	 * @param key - the property key
	 * @param protocol - the protocol name
	 * @return the property value
	 */
	public String getProtocolProperty(String key, String protocol)
	{
		String protocolProperty = properties.getProperty(key.replace(KEY_PREFIX, KEY_PREFIX + protocol + "."));

		if(protocolProperty != null)
			return protocolProperty;

		return getGlobalProperty(key);
	}

	/**
	 * Set a property specific property in the underlying properties (e.g.
	 * "mail.<i>protocol</i>.user")
	 * 
	 * @param key - the property key
	 * @param protocol - the protocol name
	 * @param value - the (new) property value
	 * @return the old property value
	 */
	public String setProtocolProperty(String key, String protocol, String value)
	{
		return (String) properties.put(key.replace(key.replace(KEY_PREFIX, KEY_PREFIX + protocol + "."), protocol), value);
	}

	/**
	 * Send an e-mail to a single recipient.
	 * 
	 * @param subject - the mail subject as of {@link MimeMessage#setSubject(String)}
	 * @param text - the mail content as of {@link MimeMessage#setText(String, String)}
	 * @param to - the recipient
	 * @return wether the message has been send successfully
	 * @throws AddressException if parsing the address fails
	 */
	public boolean send(String subject, String text, String to) throws AddressException
	{
		return send(subject, text, to, null);
	}

	/**
	 * Send an e-mail to a single recipient.
	 * 
	 * @param subject - the mail subject as of {@link MimeMessage#setSubject(String)}
	 * @param text - the mail content as of {@link MimeMessage#setText(String, String)}
	 * @param to - the recipient
	 * @param from - an optional from address
	 * @return wether the message has been send successfully
	 * @throws AddressException if parsing the address fails
	 */
	public boolean send(String subject, String text, String to, String from) throws AddressException
	{
		return send(subject, text, new String[] { to }, null, null, from);
	}

	/**
	 * Send an e-mail with the specified parameters.<br>
	 * All other required information will be obtained from the mail-properties. If authetication is
	 * required it will automatically be done before sending.<br>
	 * <br>
	 * Some of the Address arrays may be null or empty but at least one address must be specified
	 * overall.
	 * 
	 * @see MimeMessage
	 * @param subject - the mail subject as of {@link MimeMessage#setSubject(String)}
	 * @param text - the mail content as of {@link MimeMessage#setText(String, String)}
	 * @param to - the recipients for TO (may be null or empty)
	 * @param cc - the recipients for CC (may be null or empty)
	 * @param bcc - the recipients for BCC (may be null or empty)
	 * @param from - an optional from address
	 * @return wether the message has been send successfully
	 * @throws AddressException if parsing the address fails
	 */
	public boolean send(String subject, String text, String[] to, String[] cc, String[] bcc, String from) throws AddressException
	{
		Session session = Session.getDefaultInstance(properties, null);

		try
		{
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(from != null ? from : properties.getProperty(KEY_FROM)));
			msg.setSubject(subject);
			msg.setText(text, getMailContentType());

			Address[] toAddr = toAddresses(to);
			Address[] ccAddr = toAddresses(cc);
			Address[] bccAddr = toAddresses(bcc);

			int recipients = toAddr.length + ccAddr.length + bccAddr.length;
			if(recipients == 0)
				throw new IllegalArgumentException("no recipients specified!");

			msg.addRecipients(RecipientType.TO, toAddr);
			msg.addRecipients(RecipientType.CC, ccAddr);
			msg.addRecipients(RecipientType.BCC, bccAddr);

			String protocol = getProtocol();
			if(!"true".equals(getProtocolProperty(KEY_AUTH, protocol)))
			{
				Transport.send(msg);
			}
			else
			{
				Transport t = session.getTransport(protocol);

				t.connect(getProtocolProperty(KEY_HOST, protocol), getProtocolProperty(KEY_USER, protocol),
						getProtocolProperty(KEY_PASSWORD, protocol));
				t.sendMessage(msg, msg.getAllRecipients());
			}
			logger.info("message send successfully");
			return true;
		}
		catch(MessagingException e)
		{
			logger.error("sending message failed", e);
			return false;
		}
	}

	/**
	 * Transform a String array of addresses to an Address array
	 * 
	 * @param addr - the String array
	 * @return the Address array
	 * @throws AddressException if creating an InternetAddress from String fails
	 */
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

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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.internet.AddressException;

import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.MessageUtil;

/**
 * {@link Mailer}-extension offering additional support for sending template based messages.<br>
 * <br>
 * Templates have to be defined in the following way inside the properties:
 * <ul>
 * <li>by defining subject and text<br>
 * <code>template.<i>templateName</i>.subject=foo<br>
 * template.<i>templateName</i>.text=bar</code></li>
 * <li>by defining subject and a file for the text<br>
 * <code>template.<i>templateName</i>.subject=foo<br>
 * template.<i>templateName</i>.file=bar.txt</code></li>
 * </ul>
 * 
 * @author ultimate
 */
public class TemplateMailer extends Mailer
{
	/**
	 * The prefix used for template definition in the properties:
	 * <code><u>template.</u><i>templateName</i>.property=foo</code>
	 */
	public static final String		KEY_TEMPLATE_PREFIX		= "template.";
	/**
	 * The property name used for defining the subject of a template in the properties
	 * <code>template.<i>templateName</i><u>.subject</u>=foo</code>
	 */
	public static final String		KEY_TEMPLATE_SUBJECT	= ".subject";
	/**
	 * The property name used for defining the text of a template in the properties
	 * <code>template.<i>templateName</i><u>.text</u>=foo</code>
	 */
	public static final String		KEY_TEMPLATE_TEXT		= ".text";
	/**
	 * The property name used for defining the file for a template in the properties
	 * <code>template.<i>templateName</i><u>.file</u>=foo</code>
	 */
	public static final String		KEY_TEMPLATE_FILE		= ".file";

	/**
	 * A Map holding all template subjects loaded.
	 */
	protected Map<String, String>	subjects;

	/**
	 * A Map holding all template texts loaded.
	 */
	protected Map<String, String>	texts;

	/**
	 * Construct a new Mailer by loading the Properties from the given name
	 * 
	 * @param propertiesFile - the properties-file name
	 * @throws IOException if loading the properties fails
	 */
	public TemplateMailer(String propertiesFile) throws IOException
	{
		super(propertiesFile);
		readTemplates();
	}

	/**
	 * Construct a new Mailer by loading the Properties from the given file
	 * 
	 * @param propertiesFile - the properties-file
	 * @throws IOException if loading the properties fails
	 */
	public TemplateMailer(File propertiesFile) throws IOException
	{
		super(propertiesFile);
		readTemplates();
	}

	/**
	 * Construct a new Mailer with the given properties
	 * 
	 * @param properties - the Properties
	 */
	public TemplateMailer(Properties properties)
	{
		super(properties);
		readTemplates();
	}

	/**
	 * Read the templates specified in the properties file.
	 */
	protected void readTemplates()
	{
		subjects = new HashMap<String, String>();
		texts = new HashMap<String, String>();

		String key;
		String templateName;

		// first look for all template names
		List<String> templateNames = new LinkedList<String>();
		for(Object keyO : properties.keySet())
		{
			key = (String) keyO;
			if(key.startsWith(KEY_TEMPLATE_PREFIX))
			{
				templateName = key.substring(KEY_TEMPLATE_PREFIX.length(), key.lastIndexOf('.'));
				if(!templateNames.contains(templateName))
					templateNames.add(templateName);
			}
		}

		// now add them
		String subject;
		String text;
		File file;
		for(String name : templateNames)
		{
			subject = properties.getProperty(KEY_TEMPLATE_PREFIX + name + KEY_TEMPLATE_SUBJECT);
			if(subject == null)
			{
				logger.warn("no subject defined for template '" + name + "'");
				subject = "";
			}
			text = properties.getProperty(KEY_TEMPLATE_PREFIX + name + KEY_TEMPLATE_TEXT);
			if(text == null)
			{
				text = properties.getProperty(KEY_TEMPLATE_PREFIX + name + KEY_TEMPLATE_FILE);
				if(text == null)
				{
					logger.warn("no text defined for template '" + name + "' - template will be ignored!");
					continue;
				}
				file = new File(text);
				if(!file.exists())
				{
					logger.warn("could not find text-file for template '" + name + "' - template will be ignored!");
					continue;
				}
				try
				{
					text = FileUtil.readFile(file);
				}
				catch(IOException e)
				{
					logger.error("could not read text-file for template '" + name + "' - template will be ignored!");
					continue;
				}
			}
			subjects.put(name, subject);
			texts.put(name, text);
		}
	}

	/**
	 * Get the list of template names (as an unmodifiable Set).
	 * 
	 * @return the template names
	 */
	public Set<String> getTemplateNames()
	{
		return Collections.unmodifiableSet(subjects.keySet());
	}

	/**
	 * Send a mail based on a template.<br>
	 * The template will be obtained from this Mailers configuration via
	 * {@link TemplateMailer#getSubject(String, Map)} and
	 * {@link TemplateMailer#getText(String, Map)}.<br>
	 * Some of the Address arrays may be null or empty but at least one address must be specified
	 * overall.
	 * 
	 * @param templateName - the name of the template
	 * @param values - the values to inlcude into the template
	 * @param to - a single recipient for TO
	 * @return wether the message has been send successfully
	 * @throws AddressException if parsing the address fails
	 */
	public boolean send(String templateName, Map<String, Object> values, String to) throws AddressException
	{
		return this.send(templateName, values, new String[] { to }, null, null, null);
	}

	/**
	 * Send a mail based on a template.<br>
	 * The template will be obtained from this Mailers configuration via
	 * {@link TemplateMailer#getSubject(String, Map)} and
	 * {@link TemplateMailer#getText(String, Map)}.<br>
	 * Some of the Address arrays may be null or empty but at least one address must be specified
	 * overall.
	 * 
	 * @param templateName - the name of the template
	 * @param values - the values to inlcude into the template
	 * @param to - a single recipient for TO
	 * @param from - an optional from address
	 * @return wether the message has been send successfully
	 * @throws AddressException if parsing the address fails
	 */
	public boolean send(String templateName, Map<String, Object> values, String to, String from) throws AddressException
	{
		return this.send(templateName, values, new String[] { to }, null, null, from);
	}

	/**
	 * Send a mail based on a template.<br>
	 * The template will be obtained from this Mailers configuration via
	 * {@link TemplateMailer#getSubject(String, Map)} and
	 * {@link TemplateMailer#getText(String, Map)}.<br>
	 * Some of the Address arrays may be null or empty but at least one address must be specified
	 * overall.
	 * 
	 * @param templateName - the name of the template
	 * @param values - the values to inlcude into the template
	 * @param to - the recipients for TO (may be null or empty)
	 * @param cc - the recipients for CC (may be null or empty)
	 * @param bcc - the recipients for BCC (may be null or empty)
	 * @return wether the message has been send successfully
	 * @throws AddressException if parsing the address fails
	 */
	public boolean send(String templateName, Map<String, Object> values, String[] to, String[] cc, String[] bcc) throws AddressException
	{
		return this.send(templateName, values, to, null, null, null);
	}

	/**
	 * Send a mail based on a template.<br>
	 * The template will be obtained from this Mailers configuration via
	 * {@link TemplateMailer#getSubject(String, Map)} and
	 * {@link TemplateMailer#getText(String, Map)}.<br>
	 * Some of the Address arrays may be null or empty but at least one address must be specified
	 * overall.
	 * 
	 * @param templateName - the name of the template
	 * @param values - the values to inlcude into the template
	 * @param to - the recipients for TO (may be null or empty)
	 * @param cc - the recipients for CC (may be null or empty)
	 * @param bcc - the recipients for BCC (may be null or empty)
	 * @param from - an optional from address
	 * @return wether the message has been send successfully
	 * @throws AddressException if parsing the address fails
	 */
	public boolean send(String templateName, Map<String, Object> values, String[] to, String[] cc, String[] bcc, String from) throws AddressException
	{
		return this.send(getSubject(templateName, values), getText(templateName, values), to, cc, bcc, from);
	}

	/**
	 * Get the subject for a template specified by it's name.<br>
	 * The subject will be filled with the given values before return.
	 * 
	 * @see MessageUtil#fromTemplate(String, Map)
	 * @param templateName - the name of the template
	 * @param values - the values to inlcude into the template (subject)
	 * @return the formatted subject
	 */
	protected String getSubject(String templateName, Map<String, Object> values)
	{
		return MessageUtil.fromTemplate(getSubject(templateName), values);
	}

	/**
	 * Get the subject for a template specified by it's name (without including any values).<br>
	 * 
	 * @param templateName - the name of the template
	 * @return the unformatted subject
	 */
	protected String getSubject(String templateName)
	{
		if(!subjects.containsKey(templateName))
			throw new IllegalArgumentException("template '" + templateName + "' not found");
		return subjects.get(templateName);
	}

	/**
	 * Get the textfor a template specified by it's name.<br>
	 * The text will be filled with the given values before return.
	 * 
	 * @see MessageUtil#fromTemplate(String, Map)
	 * @param templateName - the name of the template
	 * @param values - the values to inlcude into the template (text)
	 * @return the formatted text
	 */
	protected String getText(String templateName, Map<String, Object> values)
	{
		return MessageUtil.fromTemplate(getText(templateName), values);
	}

	/**
	 * Get the text for a template specified by it's name (without including any values).<br>
	 * 
	 * @param templateName - the name of the template
	 * @return the unformatted text
	 */
	protected String getText(String templateName)
	{
		if(!subjects.containsKey(templateName))
			throw new IllegalArgumentException("template '" + templateName + "' not found");
		return texts.get(templateName);
	}
}

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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.internet.AddressException;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.utils.MessageUtil;

/**
 * {@link BaseGameMailer} extension for Universe Conquest
 * 
 * @author ultimate
 */
public class UniverseConquestMailer extends BaseGameMailer
{
	/**
	 * Template name for {@link UniverseConquestMailer#sendMatchStartFailedNotification(Match, String)}
	 */
	public static final String		TEMPLATE_MATCH_START_FAILED	= "match.start.failed";
	/**
	 * An Array containing the names of all additionally required templates.
	 */
	public static final String[]	REQUIRED_TEMPLATES			= new String[] { TEMPLATE_MATCH_START_FAILED, };

	/**
	 * Construct a new Mailer by loading the Properties from the given file
	 * 
	 * @param propertiesFile - the properties-file
	 * @throws IOException if loading the properties fails
	 */
	public UniverseConquestMailer(File propertiesFile) throws IOException
	{
		super(propertiesFile);
	}

	/**
	 * Construct a new Mailer by loading the Properties from the given file
	 * 
	 * @param propertiesFile - the properties-file
	 * @throws IOException if loading the properties fails
	 */
	public UniverseConquestMailer(String propertiesFile) throws IOException
	{
		super(propertiesFile);
	}

	/**
	 * Construct a new Mailer with the given properties
	 * 
	 * @param properties - the Properties
	 */
	public UniverseConquestMailer(Properties properties)
	{
		super(properties);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.mail.BaseApplicationMailer#checkMailer(java.lang.String,
	 * com.syncnapsis.utils.mail.TemplateMailer)
	 */
	@Override
	protected boolean checkMailer(String key, TemplateMailer mailer)
	{
		boolean valid = super.checkMailer(key, mailer);

		// check if all requried templates are present
		Set<String> templates = mailer.getTemplateNames();
		for(String req : REQUIRED_TEMPLATES)
		{
			if(!templates.contains(req))
			{
				logger.error("required template '" + req + "' not found for mailer '" + key + "'");
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * Send a notification email if a planned match start has failed.<br>
	 * <br>
	 * The exact content will depend on the template defined in the mail.properties but support for
	 * including {@link Match} properties is given by default.
	 * Normally the template should include information about the role change like
	 * <ul>
	 * <li>the match name</li>
	 * <li>the planned match start date</li>
	 * </ul>
	 * <br>
	 * This method will do a generic lookup for all keys used in the template using
	 * {@link MessageUtil#extractValues(List, Object...)}.
	 * 
	 * @see MessageUtil#extractValues(List, Object...)
	 * @see MessageUtil#getUsedTemplateKeys(String)
	 * @see TemplateMailer#send(String, Map, String)
	 * @param match - the match that could not been started
	 * @param reason - the reason for the failure
	 * @return true if a message has been send, false otherwise
	 */
	public boolean sendMatchStartFailedNotification(Match match, String reason)
	{
		TemplateMailer m = get(match.getCreator());

		String template = TEMPLATE_MATCH_START_FAILED;
		List<String> keys = MessageUtil.getUsedTemplateKeys(m.getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(m.getSubject(template)));
		Map<String, Object> values = MessageUtil.extractValues(keys, match);
		values.put("reason", reason);
		// fill other values?
		try
		{
			return m.send(template, values, match.getCreator().getUser().getEmail());
		}
		catch(AddressException e)
		{
			logger.error("could not send mail to '" + e.getRef() + "'");
			return false;
		}
	}
}

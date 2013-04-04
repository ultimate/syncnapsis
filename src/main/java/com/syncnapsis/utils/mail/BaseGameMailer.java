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

import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRole;
import com.syncnapsis.utils.MessageUtil;

/**
 * BaseApplicationMailer extension offering support for sending standard game mails like
 * role change notifications etc.<br>
 * All templates required must be provided with the mail.properties. The keys for those templates
 * additionally to those required for {@link BaseApplicationMailer} are defined as constants within
 * the class and are listed in {@link BaseGameMailer#REQUIRED_TEMPLATES}:
 * <ul>
 * <li>{@link BaseGameMailer#TEMPLATE_PLAYERROLE_CHANGED}: "playerrole.changed"</li>
 * </ul>
 * 
 * @author ultimate
 */
public class BaseGameMailer extends BaseApplicationMailer
{
	/**
	 * Template name for {@link BaseGameMailer#sendPlayerRoleChangedNotification(Player)}
	 */
	public static final String		TEMPLATE_PLAYERROLE_CHANGED	= "playerrole.changed";
	/**
	 * An Array containing the names of all additionally required templates.
	 */
	public static final String[]	REQUIRED_TEMPLATES			= new String[] { TEMPLATE_PLAYERROLE_CHANGED, };

	/**
	 * Construct a new Mailer by loading the Properties from the given file
	 * 
	 * @param propertiesFile - the properties-file
	 * @throws IOException if loading the properties fails
	 */
	public BaseGameMailer(File propertiesFile) throws IOException
	{
		super(propertiesFile);
	}

	/**
	 * Construct a new Mailer with the given properties
	 * 
	 * @param properties - the Properties
	 */
	public BaseGameMailer(Properties properties)
	{
		super(properties);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.mail.BaseApplicationMailer#checkTemplates()
	 */
	@Override
	protected void checkTemplates()
	{
		super.checkTemplates();
		Set<String> templates = getTemplateNames();
		for(String req : REQUIRED_TEMPLATES)
		{
			if(!templates.contains(req))
				throw new IllegalArgumentException("required template '" + req + "' not found");
		}
	}

	/**
	 * Send a notification email if a players role has changed.<br>
	 * <br>
	 * The exact content will depend on the template defined in the mail.properties but support for
	 * including {@link Player} (and therefore {@link PlayerRole}) properties is given by default.
	 * Normally the template should include information about the role change like
	 * <ul>
	 * <li>the old role</li>
	 * <li>the new role</li>
	 * <li>the role expire date</li>
	 * </ul>
	 * <br>
	 * This method will do a generic lookup for all keys used in the template using
	 * {@link MessageUtil#extractValues(List, Object...)}.
	 * 
	 * @see MessageUtil#extractValues(List, Object...)
	 * @see MessageUtil#getUsedTemplateKeys(String)
	 * @see TemplateMailer#send(String, Map, String)
	 * @param player - the player who's role has changed (including the new role)
	 * @param oldRole - the old role of the player
	 * @param reason - the reason for the role change
	 * @return true if a message has been send, false otherwise
	 */
	public boolean sendPlayerRoleChangedNotification(Player player, PlayerRole oldRole, String reason)
	{
		String template = TEMPLATE_PLAYERROLE_CHANGED;
		List<String> keys = MessageUtil.getUsedTemplateKeys(getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(getSubject(template)));
		Map<String, Object> values = MessageUtil.extractValues(keys, player, oldRole);
		values.put("reason", reason);
		// fill other values?
		try
		{
			return send(template, values, player.getUser().getEmail());
		}
		catch(AddressException e)
		{
			logger.error("could not send mail to '" + e.getRef() + "'");
			return false;
		}
	}
}

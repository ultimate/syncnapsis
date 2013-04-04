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

import com.syncnapsis.data.model.Action;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.utils.MessageUtil;

/**
 * TemplateMailer extension offering support for sending standard application mails like
 * registration or email verifications etc.<br>
 * All templates required must be provided with the mail.properties. The keys for those templates
 * are defined as constants within the class and are listed in
 * {@link BaseApplicationMailer#REQUIRED_TEMPLATES}:
 * <ul>
 * <li>{@link BaseApplicationMailer#TEMPLATE_REGISTRATION_VERIFY}: "registration.verify"</li>
 * <li>{@link BaseApplicationMailer#TEMPLATE_MAIL_ADDRESS_VERIFY}: "mail.verify"</li>
 * <li>{@link BaseApplicationMailer#TEMPLATE_NOTIFICATION}: "notification"</li>
 * <li>{@link BaseApplicationMailer#TEMPLATE_USERROLE_CHANGED}: "userrole.changed"</li>
 * </ul>
 * 
 * @author ultimate
 */
public class BaseApplicationMailer extends TemplateMailer
{
	/**
	 * Template name for {@link BaseApplicationMailer#sendVerifyRegistration(User, Action)}
	 */
	public static final String		TEMPLATE_REGISTRATION_VERIFY	= "registration.verify";
	/**
	 * Template name for {@link BaseApplicationMailer#sendVerifyMailAddress(User, Action)}
	 */
	public static final String		TEMPLATE_MAIL_ADDRESS_VERIFY	= "mail.verify";
	/**
	 * Template name for {@link BaseApplicationMailer#sendGeneralNotification(List, String, String)}
	 */
	public static final String		TEMPLATE_NOTIFICATION			= "notification";
	/**
	 * Template name for {@link BaseApplicationMailer#sendUserRoleChangedNotification(User, UserRole, String)}
	 */
	public static final String		TEMPLATE_USERROLE_CHANGED		= "userrole.changed";
	/**
	 * An Array containing the names of all required templates.
	 */
	public static final String[]	REQUIRED_TEMPLATES				= new String[] { TEMPLATE_REGISTRATION_VERIFY, TEMPLATE_MAIL_ADDRESS_VERIFY,
			TEMPLATE_NOTIFICATION, TEMPLATE_USERROLE_CHANGED,		};

	/**
	 * Construct a new Mailer by loading the Properties from the given file
	 * 
	 * @param propertiesFile - the properties-file
	 * @throws IOException if loading the properties fails
	 */
	public BaseApplicationMailer(File propertiesFile) throws IOException
	{
		super(propertiesFile);
		checkTemplates();
	}

	/**
	 * Construct a new Mailer with the given properties
	 * 
	 * @param properties - the Properties
	 */
	public BaseApplicationMailer(Properties properties)
	{
		super(properties);
		checkTemplates();
	}

	/**
	 * Check the template configuration if all required templates are present.<br>
	 * Since this method is used in the constructors it will throw an IllegalArgumentException for
	 * the first required template not found and abort any further check.
	 * 
	 * @throws IllegalArgumentException if a required template is not found.
	 */
	protected void checkTemplates()
	{
		Set<String> templates = getTemplateNames();
		for(String req : REQUIRED_TEMPLATES)
		{
			if(!templates.contains(req))
				throw new IllegalArgumentException("required template '" + req + "' not found");
		}
	}

	/**
	 * Send a registration verification with a request for the user to validate his registration.<br>
	 * <br>
	 * The exact content will depend on the template defined in the mail.properties but support for
	 * including {@link User} and {@link Action} properties is given by default. The Action is
	 * representing the action that the newly registered user has to execute (via clicking a link)
	 * for activating his account.<br>
	 * <br>
	 * This method will do a generic lookup for all keys used in the template using
	 * {@link MessageUtil#extractValues(List, Object...)}.
	 * 
	 * @see MessageUtil#extractValues(List, Object...)
	 * @see MessageUtil#getUsedTemplateKeys(String)
	 * @see TemplateMailer#send(String, Map, String)
	 * @param user - the newly registered user
	 * @param action - the action for confirming registration
	 * @return true if a message has been send, false otherwise
	 */
	public boolean sendVerifyRegistration(User user, Action action)
	{
		String template = TEMPLATE_REGISTRATION_VERIFY;
		List<String> keys = MessageUtil.getUsedTemplateKeys(getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(getSubject(template)));
		Map<String, Object> values = MessageUtil.extractValues(keys, user, action);
		// fill other values?
		try
		{
			return send(template, values, user.getEmail());
		}
		catch(AddressException e)
		{
			logger.error("could not send mail to '" + e.getRef() + "'");
			return false;
		}
	}

	/**
	 * Send a email verification with the request for the user to validate his email.<br>
	 * <br>
	 * The exact content will depend on the template defined in the mail.properties but support for
	 * including {@link User} and {@link Action} properties is given by default. The Action is
	 * representing the action that the registered user has to execute (via clicking a link)
	 * to validate his newly entered email address.<br>
	 * <br>
	 * This method will do a generic lookup for all keys used in the template using
	 * {@link MessageUtil#extractValues(List, Object...)}.
	 * 
	 * @see MessageUtil#extractValues(List, Object...)
	 * @see MessageUtil#getUsedTemplateKeys(String)
	 * @see TemplateMailer#send(String, Map, String)
	 * @param user - the user that changed his email address
	 * @param action - the action for confirming the email address change
	 * @return true if a message has been send, false otherwise
	 */
	public boolean sendVerifyMailAddress(User user, Action action)
	{
		String template = TEMPLATE_MAIL_ADDRESS_VERIFY;
		List<String> keys = MessageUtil.getUsedTemplateKeys(getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(getSubject(template)));
		Map<String, Object> values = MessageUtil.extractValues(keys, user, action);
		// fill other values?
		try
		{
			return send(template, values, user.getEmail());
		}
		catch(AddressException e)
		{
			logger.error("could not send mail to '" + e.getRef() + "'");
			return false;
		}
	}

	/**
	 * Send a notification email if a user's role has changed.<br>
	 * <br>
	 * The exact content will depend on the template defined in the mail.properties but support for
	 * including {@link User} (and therefore {@link UserRole}) properties is given by default.
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
	 * @param user - the user who's role has changed (including the new role)
	 * @param oldRole - the old role of the user
	 * @param reason - the reason for the role change
	 * @return true if a message has been send, false otherwise
	 */
	public boolean sendUserRoleChangedNotification(User user, UserRole oldRole, String reason)
	{
		String template = TEMPLATE_USERROLE_CHANGED;
		List<String> keys = MessageUtil.getUsedTemplateKeys(getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(getSubject(template)));
		Map<String, Object> values = MessageUtil.extractValues(keys, user, oldRole);
		values.put("reason", reason);
		// fill other values?
		try
		{
			return send(template, values, user.getEmail());
		}
		catch(AddressException e)
		{
			logger.error("could not send mail to '" + e.getRef() + "'");
			return false;
		}
	}

	/**
	 * Send a notification email to all users given.<br>
	 * <br>
	 * The template usually only will define the general layout of the notification message and the
	 * real content will be defined by the parameters given. Nevertheless support for
	 * including {@link User} properties is given by default.<br>
	 * <br>
	 * This method will do a generic lookup for all keys used in the template using
	 * {@link MessageUtil#extractValues(List, Object...)} for each user to create individual
	 * messages. Additionally the mail will be sent to each user separately to avoid visibility of
	 * other recipients.
	 * 
	 * @see MessageUtil#extractValues(List, Object...)
	 * @see MessageUtil#getUsedTemplateKeys(String)
	 * @see TemplateMailer#send(String, Map, String)
	 * @param users - the list of users to send the message to
	 * @param notificationSubject - the subject to include into the message as {subject}
	 * @param notificationText - the text to include into the message as {text}
	 * @return true if a message has been send, false otherwise
	 */
	public int sendGeneralNotification(List<User> users, String notificationSubject, String notificationText)
	{
		String template = TEMPLATE_NOTIFICATION;
		List<String> keys = MessageUtil.getUsedTemplateKeys(getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(getSubject(template)));
		keys.addAll(MessageUtil.getUsedTemplateKeys(notificationSubject));
		keys.addAll(MessageUtil.getUsedTemplateKeys(notificationText));
		Map<String, Object> values;
		int messagesSent = 0;
		for(User user : users)
		{
			try
			{
				values = MessageUtil.extractValues(keys, user);
				values.put("subject", notificationSubject);
				values.put("text", notificationText);
				// fill other values?
				if(send(template, values, user.getEmail()))
					messagesSent++;
			}
			catch(AddressException e)
			{
				logger.error("could not send mail to '" + e.getRef() + "'");
			}
		}
		return messagesSent;
	}
}

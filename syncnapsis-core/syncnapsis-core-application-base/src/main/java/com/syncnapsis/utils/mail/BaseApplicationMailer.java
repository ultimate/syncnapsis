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
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.utils.MessageUtil;

/**
 * MultiMailer extension offering support for sending standard application mails like
 * registration or email verifications etc.<br>
 * <br>
 * Since this is a {@link MultiMailer} all send operations are done by underlying key associated
 * mailers by using {@link MultiMailer#get(String)}.<br>
 * <br>
 * All templates required must be provided for each of the underlying mailers in separate propertes
 * as specified by MultiMailer. Standard mail properties only have to be provided once in the
 * default properties this mailer is created from.<br>
 * <br>
 * The keys for the templates required are defined as constants within the class and are listed in
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
public class BaseApplicationMailer extends MultiMailer<TemplateMailer>
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
	 * Template name for
	 * {@link BaseApplicationMailer#sendUserRoleChangedNotification(User, UserRole, String)}
	 */
	public static final String		TEMPLATE_USERROLE_CHANGED		= "userrole.changed";
	/**
	 * Template name for {@link BaseApplicationMailer#sendVerifyPasswordReset(User, Action)}
	 */
	public static final String		TEMPLATE_PASSWORD_VERIFY		= "password.verify";
	/**
	 * Template name for {@link BaseApplicationMailer#sendPasswordResetted(User, String)}
	 */
	public static final String		TEMPLATE_PASSWORD_RESET			= "password.reset";

	/**
	 * An Array containing the names of all required templates.
	 */
	public static final String[]	REQUIRED_TEMPLATES				= new String[] { TEMPLATE_REGISTRATION_VERIFY, TEMPLATE_MAIL_ADDRESS_VERIFY,
			TEMPLATE_NOTIFICATION, TEMPLATE_USERROLE_CHANGED, TEMPLATE_PASSWORD_RESET, TEMPLATE_PASSWORD_VERIFY,		};

	/**
	 * Construct a new Mailer by loading the Properties from the given file
	 * 
	 * @param propertiesFile - the properties-file
	 * @throws IOException if loading the properties fails
	 */
	public BaseApplicationMailer(String propertiesFile) throws IOException
	{
		super(TemplateMailer.class, propertiesFile);
	}

	/**
	 * Construct a new Mailer by loading the Properties from the given file
	 * 
	 * @param propertiesFile - the properties-file
	 * @throws IOException if loading the properties fails
	 */
	public BaseApplicationMailer(File propertiesFile) throws IOException
	{
		super(TemplateMailer.class, propertiesFile);
	}

	/**
	 * Construct a new Mailer with the given properties
	 * 
	 * @param properties - the Properties
	 */
	public BaseApplicationMailer(Properties properties)
	{
		super(TemplateMailer.class, properties);
	}

	/**
	 * Check wether the given TemplateMailer contains all required templates
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
	 * Get the mailer associated with the given EnumLocale
	 * 
	 * @param locale - the locale to get the mailer for
	 * @return the Mailer
	 */
	public TemplateMailer get(EnumLocale locale)
	{
		if(locale == null)
			return get((String) null);
		return get(locale.toString().toLowerCase());
	}

	/**
	 * Get the mailer associated with the locale of the given user.<br>
	 * (shorthand for <code>get(user.getLocale())</code>)
	 * 
	 * @param user - the user to get the mailer for
	 * @return the Mailer
	 */
	public TemplateMailer get(User user)
	{
		if(user == null)
			return get((EnumLocale) null);
		return get(user.getLocale());
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
		TemplateMailer m = get(user);

		String template = TEMPLATE_REGISTRATION_VERIFY;
		List<String> keys = MessageUtil.getUsedTemplateKeys(m.getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(m.getSubject(template)));
		Map<String, Object> values = MessageUtil.extractValues(keys, user, action);
		// fill other values?
		try
		{
			return m.send(template, values, user.getEmail());
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
	 * @param newMailAddress - the new mail address set
	 * @param action - the action for confirming the email address change
	 * @return true if a message has been send, false otherwise
	 */
	public boolean sendVerifyMailAddress(User user, String newMailAddress, Action action)
	{
		TemplateMailer m = get(user);

		String template = TEMPLATE_MAIL_ADDRESS_VERIFY;
		List<String> keys = MessageUtil.getUsedTemplateKeys(m.getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(m.getSubject(template)));
		Map<String, Object> values = MessageUtil.extractValues(keys, user, action);
		values.put("newMailAddress", newMailAddress);
		// fill other values?
		try
		{
			return m.send(template, values, newMailAddress);
		}
		catch(AddressException e)
		{
			logger.error("could not send mail to '" + e.getRef() + "'");
			return false;
		}
	}

	/**
	 * Send a verification mail for resetting a user's password.<br>
	 * <br>
	 * The exact content will depend on the template defined in the mail.properties but support for
	 * including {@link User} and {@link Action} properties is given by default. The Action is
	 * representing the action that the user has to execute (via clicking a link) for resetting his
	 * password.<br>
	 * <br>
	 * This method will do a generic lookup for all keys used in the template using
	 * {@link MessageUtil#extractValues(List, Object...)}.
	 * 
	 * @see MessageUtil#extractValues(List, Object...)
	 * @see MessageUtil#getUsedTemplateKeys(String)
	 * @see TemplateMailer#send(String, Map, String)
	 * @param user - the user to change the password for
	 * @param action - the action for confirming password reset
	 * @return true if a message has been send, false otherwise
	 */
	public boolean sendVerifyPasswordReset(User user, Action action)
	{
		TemplateMailer m = get(user);

		String template = TEMPLATE_PASSWORD_VERIFY;
		List<String> keys = MessageUtil.getUsedTemplateKeys(m.getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(m.getSubject(template)));
		Map<String, Object> values = MessageUtil.extractValues(keys, user, action);
		// fill other values?
		try
		{
			return m.send(template, values, user.getEmail());
		}
		catch(AddressException e)
		{
			logger.error("could not send mail to '" + e.getRef() + "'");
			return false;
		}
	}

	/**
	 * Send a mail with the password that has been set for a user on requested reset.<br>
	 * <br>
	 * The exact content will depend on the template defined in the mail.properties but support for
	 * including {@link User} properties and the new password is given by default. <br>
	 * <br>
	 * This method will do a generic lookup for all keys used in the template using
	 * {@link MessageUtil#extractValues(List, Object...)}.
	 * 
	 * @see MessageUtil#extractValues(List, Object...)
	 * @see MessageUtil#getUsedTemplateKeys(String)
	 * @see TemplateMailer#send(String, Map, String)
	 * @param user - the user whose password was change
	 * @param newPassword - the new password set
	 * @return true if a message has been send, false otherwise
	 */
	public boolean sendPasswordResetted(User user, String newPassword)
	{
		TemplateMailer m = get(user);

		String template = TEMPLATE_PASSWORD_RESET;
		List<String> keys = MessageUtil.getUsedTemplateKeys(m.getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(m.getSubject(template)));
		Map<String, Object> values = MessageUtil.extractValues(keys, user);
		values.put("newPassword", newPassword);
		// fill other values?
		try
		{
			return m.send(template, values, user.getEmail());
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
		TemplateMailer m = get(user);

		String template = TEMPLATE_USERROLE_CHANGED;
		List<String> keys = MessageUtil.getUsedTemplateKeys(m.getText(template));
		keys.addAll(MessageUtil.getUsedTemplateKeys(m.getSubject(template)));
		Map<String, Object> values = MessageUtil.extractValues(keys, user, oldRole);
		values.put("reason", reason);
		// fill other values?
		try
		{
			return m.send(template, values, user.getEmail());
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
		int messagesSent = 0;
		TemplateMailer m;
		for(User user : users)
		{
			m = get(user);
			List<String> keys = MessageUtil.getUsedTemplateKeys(m.getText(template));
			keys.addAll(MessageUtil.getUsedTemplateKeys(m.getSubject(template)));
			keys.addAll(MessageUtil.getUsedTemplateKeys(notificationSubject));
			keys.addAll(MessageUtil.getUsedTemplateKeys(notificationText));
			Map<String, Object> values;
			values = MessageUtil.extractValues(keys, user);
			values.put("subject", notificationSubject);
			values.put("text", notificationText);
			try
			{
				// fill other values?
				if(m.send(template, values, user.getEmail()))
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

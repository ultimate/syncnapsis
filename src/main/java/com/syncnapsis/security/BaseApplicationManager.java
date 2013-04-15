/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.security;

import java.security.MessageDigest;

import org.springframework.util.Assert;

import com.syncnapsis.providers.LocaleProvider;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.utils.StringUtil;
import com.syncnapsis.utils.mail.BaseApplicationMailer;

/**
 * Extension of {@link SecurityManager} for additional functionality in applications.
 * 
 * @author ultimate
 */
public class BaseApplicationManager extends SecurityManager
{
	/**
	 * The default encryption algorithm used
	 * 
	 * @see StringUtil#encodePassword(String, String)
	 */
	public static final String		DEFAULT_ENCRYPTION_ALGORITHM	= "SHA";
	/**
	 * The UserProvider
	 */
	protected UserProvider			userProvider;
	/**
	 * The LocaleProvider
	 */
	protected LocaleProvider		localeProvider;

	/**
	 * The Validator for name validation
	 */
	protected Validator<String>		nameValidator;
	/**
	 * The Validator for email-address validation
	 */
	protected Validator<String>		emailValidator;

	/**
	 * The encryption algorithm used for password encryption
	 */
	protected String				encryptionAlgorithm;

	/**
	 * The BaseApplicationMailer used to send mails
	 */
	protected BaseApplicationMailer	mailer;

	/**
	 * Standard Constructor
	 */
	public BaseApplicationManager()
	{
		super();
	}

	/**
	 * Copy-Constructor
	 * 
	 * @param manager - the original BaseApplicationManager
	 */
	public BaseApplicationManager(BaseApplicationManager manager)
	{
		super(manager);
		this.userProvider = manager.userProvider;
		this.localeProvider = manager.localeProvider;
		this.encryptionAlgorithm = manager.encryptionAlgorithm;
		this.nameValidator = manager.nameValidator;
		this.emailValidator = manager.emailValidator;
		this.mailer = manager.mailer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.SecurityManager#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(userProvider, "userProvider must not be null!");
		Assert.notNull(localeProvider, "localeProvider must not be null!");
		if(this.nameValidator == null)
			logger.warn("nameValidator is null!");
		if(this.emailValidator == null)
			logger.warn("emailValidator is null!");
		if(this.mailer == null)
			logger.warn("mailer is null!");
		if(encryptionAlgorithm == null)
		{
			logger.warn("encryptionAlgorithm is null, using default: '" + DEFAULT_ENCRYPTION_ALGORITHM + "'");
			this.encryptionAlgorithm = DEFAULT_ENCRYPTION_ALGORITHM;
		}
	}

	/**
	 * The UserProvider
	 * 
	 * @return userProvider
	 */
	public UserProvider getUserProvider()
	{
		return userProvider;
	}

	/**
	 * The UserProvider
	 * 
	 * @param userProvider - the UserProvider
	 */
	public void setUserProvider(UserProvider userProvider)
	{
		this.userProvider = userProvider;
	}

	/**
	 * The LocaleProvider
	 * 
	 * @return localeProvider
	 */
	public LocaleProvider getLocaleProvider()
	{
		return localeProvider;
	}

	/**
	 * The LocaleProvider
	 * 
	 * @param localeProvider - the LocaleProvider
	 */
	public void setLocaleProvider(LocaleProvider localeProvider)
	{
		this.localeProvider = localeProvider;
	}

	/**
	 * The Validator for name validation
	 * 
	 * @return nameValidator
	 */
	public Validator<String> getNameValidator()
	{
		return nameValidator;
	}

	/**
	 * The Validator for name validation
	 * 
	 * @param nameValidator - the Validator
	 */
	public void setNameValidator(Validator<String> nameValidator)
	{
		this.nameValidator = nameValidator;
	}

	/**
	 * The Validator for email-address validation
	 * 
	 * @return emailValidator
	 */
	public Validator<String> getEmailValidator()
	{
		return emailValidator;
	}

	/**
	 * The Validator for email-address validation
	 * 
	 * @param emailValidator - the Validator
	 */
	public void setEmailValidator(Validator<String> emailValidator)
	{
		this.emailValidator = emailValidator;
	}

	/**
	 * The encryption algorithm used for password encryption
	 * 
	 * @see MessageDigest#getInstance(String)
	 * @return encryptionAlgorithm
	 */
	public String getEncryptionAlgorithm()
	{
		return encryptionAlgorithm;
	}

	/**
	 * The encryption algorithm used for password encryption
	 * 
	 * @see MessageDigest#getInstance(String)
	 * @param encryptionAlgorithm - the encryptionAlgorithm name
	 */
	public void setEncryptionAlgorithm(String encryptionAlgorithm)
	{
		this.encryptionAlgorithm = encryptionAlgorithm;
	}

	/**
	 * The BaseApplicationMailer used to send mails
	 * 
	 * @return mailer
	 */
	public BaseApplicationMailer getMailer()
	{
		return mailer;
	}

	/**
	 * The BaseApplicationMailer used to send mails
	 * 
	 * @param mailer - the BaseApplicationMailer
	 */
	public void setMailer(BaseApplicationMailer mailer)
	{
		this.mailer = mailer;
	}
}

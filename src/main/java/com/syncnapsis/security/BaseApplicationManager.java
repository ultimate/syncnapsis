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
package com.syncnapsis.security;

import java.security.MessageDigest;

import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.utils.StringUtil;
import org.springframework.util.Assert;

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
	public static final String	DEFAULT_ENCRYPTION_ALGORITHM	= "SHA";
	/**
	 * The UserProvider
	 */
	protected UserProvider		userProvider;
	/**
	 * The encryption algorithm used for password encryption
	 */
	protected String				encryptionAlgorithm;

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.SecurityManager#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(userProvider, "userProvider must not be null!");
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
}

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

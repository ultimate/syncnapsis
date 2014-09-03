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
package com.syncnapsis.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.exceptions.PasswordEncryptionException;
import com.syncnapsis.providers.AuthorityProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.utils.PasswordHashUtil;
import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.spring.Bean;

/**
 * This Class represents the central instance for managing Security-Questions especially controlling
 * accessibility. Although this Class usually is represented by a single shared instance throughout
 * the application multiple instances are allowed as well, if differing contexts have to be
 * realised.<br>
 * <br>
 * Accessibility is controller via AccessControllers that can be registered for this manager and the
 * requested Types and operations.<br>
 * <br>
 * Furthermore this manager may hold a AuthorityProvider that offers the currently available
 * authorities
 * depending on the context. Those authorities may be forwarded to the AccessControllers in order to
 * determine granted authorities. (e.g. during (de)-serialization)
 * 
 * @see AccessController
 * @author ultimate
 */
public class SecurityManager extends Bean implements InitializingBean
{
	/**
	 * Logger-Instance
	 */
	protected final Logger							logger	= LoggerFactory.getLogger(getClass());

	/**
	 * The Map of all associated AccessControllers
	 */
	protected Map<Class<?>, AccessController<?>>	accessControllers;

	/**
	 * The TimeProvider used to access the current time.<br>
	 * (Optional parameter depending on application)
	 */
	protected TimeProvider							timeProvider;

	/**
	 * The SessionProvider used to access the current Session.<br>
	 * (Optional parameter depending on application)
	 */
	protected SessionProvider						sessionProvider;

	/**
	 * The Provider used to determine the authorities. (e.g. for (de)-serialization)<br>
	 * (Optional parameter depending on application)
	 */
	protected AuthorityProvider						authorityProvider;

	/**
	 * Construct a new SecurityManager
	 */
	public SecurityManager()
	{
		this.accessControllers = new HashMap<Class<?>, AccessController<?>>();
	}

	/**
	 * Copy-Constructor
	 * 
	 * @param manager - the original SecurityManager
	 */
	public SecurityManager(SecurityManager manager)
	{
		this.accessControllers = new HashMap<Class<?>, AccessController<?>>(manager.accessControllers);
		this.timeProvider = manager.timeProvider;
		this.sessionProvider = manager.sessionProvider;
		this.authorityProvider = manager.authorityProvider;
	}

	/**
	 * Add a List of AccessControllers. Existing AccessControllers won't be removed!
	 * Especially used for initialization.
	 * 
	 * @param acs - the List of AccessControllers
	 */
	public void setAccessControllers(List<AccessController<?>> acs)
	{
		for(AccessController<?> ac : acs)
			this.addAccessController(ac);
	}

	/**
	 * Add a single AccessController. The AccessController will automatically be associated with
	 * it's target Class.
	 * 
	 * @param accessController - the AccessController to add
	 */
	@SuppressWarnings("unchecked")
	public <T> void addAccessController(AccessController<T> accessController)
	{
		Class<T> type = (Class<T>) ReflectionsUtil.getActualTypeArguments(accessController, AccessController.class)[0];
		if(type == Object.class)
			type = accessController.getTargetClass();
		addAccessController(type, accessController);
	}

	/**
	 * Add a single AccessController. The AccessController will be associated with the given type.
	 * 
	 * @param type - the type to associate the AccessController with
	 * @param accessController - the AccessController to add
	 */
	protected <T> void addAccessController(Class<? extends T> type, AccessController<T> accessController)
	{
		Assert.notNull(type);
		Assert.notNull(accessController);

		this.accessControllers.put(type, accessController);
	}

	/**
	 * Remove the AccessController associated with the given type
	 * 
	 * @param type - the type whichs AccessController to remove
	 * @return the AccessController that has been removed as specified by {@link Map#remove(Object)}
	 */
	@SuppressWarnings("unchecked")
	public <T> AccessController<T> removeAccessController(Class<? extends T> type)
	{
		return (AccessController<T>) this.accessControllers.remove(type);
	}

	/**
	 * Get the AccessController associated with the given type
	 * 
	 * @param type - the type whichs AccessController to retrieve
	 * @return the AccessController
	 */
	@SuppressWarnings("unchecked")
	public <T> AccessController<T> getAccessController(Class<? extends T> type)
	{
		return (AccessController<T>) this.accessControllers.get(type);
	}

	/**
	 * Get all types for which AccessControllers have been registered.
	 * (as specified by {@link Map#keySet()} as an unmodifiable Set).
	 * 
	 * @return the Set of types
	 */
	public Set<Class<?>> getAvailableAccessControllerTypes()
	{
		return Collections.unmodifiableSet(this.accessControllers.keySet());
	}

	/**
	 * The TimeProvider used to access the current time.<br>
	 * (Optional parameter depending on application)
	 * 
	 * @return timeProvider
	 */
	public TimeProvider getTimeProvider()
	{
		return timeProvider;
	}

	/**
	 * The TimeProvider used to access the current time.<br>
	 * (Optional parameter depending on application)
	 * 
	 * @param timeProvider - the TimeProvider
	 */
	public void setTimeProvider(TimeProvider timeProvider)
	{
		this.timeProvider = timeProvider;
	}

	/**
	 * The SessionProvider used to access the current Session.<br>
	 * (Optional parameter depending on application)
	 * 
	 * @return sessionProvider
	 */
	public SessionProvider getSessionProvider()
	{
		return sessionProvider;
	}

	/**
	 * The SessionProvider used to access the current Session.<br>
	 * (Optional parameter depending on application)
	 * 
	 * @param sessionProvider - the SessionProvider
	 */
	public void setSessionProvider(SessionProvider sessionProvider)
	{
		this.sessionProvider = sessionProvider;
	}

	/**
	 * The Provider used to determine the authorities. (e.g. for (de)-serialization)<br>
	 * (Optional parameter depending on application)
	 * 
	 * @return authorityProvider
	 */
	public AuthorityProvider getAuthorityProvider()
	{
		return authorityProvider;
	}

	/**
	 * The Provider used to determine the authorities. (e.g. for (de)-serialization)<br>
	 * (Optional parameter depending on application)
	 * 
	 * @param authorityProvider - the AuthorityProvider
	 */
	public void setAuthorityProvider(AuthorityProvider authorityProvider)
	{
		this.authorityProvider = authorityProvider;
	}

	/**
	 * Create the hash for a password
	 * 
	 * @see PasswordHashUtil#createHash(String)
	 * @param password - the password to hash
	 * @return the hash for the password
	 * @throws PasswordEncryptionException if hashing fails
	 */
	public String hashPassword(String password) throws PasswordEncryptionException
	{
		try
		{
			return PasswordHashUtil.createHash(password);
		}
		catch(NoSuchAlgorithmException e)
		{
			throw new PasswordEncryptionException("error creating password hash", e);
		}
		catch(InvalidKeySpecException e)
		{
			throw new PasswordEncryptionException("error creating password hash", e);
		}
	}

	/**
	 * Check wether a password is valid for the given hash.
	 * 
	 * @see PasswordHashUtil#validatePassword(String, String)
	 * @param password - the password to validate
	 * @param hash - the hash representing the encoded password
	 * @return true if the password is valid, false otherwise
	 * @throws PasswordEncryptionException if validating fails
	 */
	public boolean validatePassword(String password, String hash) throws PasswordEncryptionException
	{
		try
		{
			return PasswordHashUtil.validatePassword(password, hash);
		}
		catch(NoSuchAlgorithmException e)
		{
			throw new PasswordEncryptionException("error validating password", e);
		}
		catch(InvalidKeySpecException e)
		{
			throw new PasswordEncryptionException("error validating password", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		if(this.timeProvider == null)
			logger.warn("timeProvider is null!");
		if(this.sessionProvider == null)
			logger.warn("sessionProvider is null!");
		if(this.authorityProvider == null)
			logger.warn("authorityProvider is null!");
	}
}

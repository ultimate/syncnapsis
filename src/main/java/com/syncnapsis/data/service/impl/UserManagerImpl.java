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
package com.syncnapsis.data.service.impl;

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.dao.UserDao;
import com.syncnapsis.data.model.Action;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.ActionManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.data.service.UserRoleManager;
import com.syncnapsis.enums.EnumAccountStatus;
import com.syncnapsis.enums.EnumDateFormat;
import com.syncnapsis.enums.EnumGender;
import com.syncnapsis.exceptions.UserNotFoundException;
import com.syncnapsis.exceptions.UserRegistrationFailedException;
import com.syncnapsis.security.BaseApplicationManager;
import com.syncnapsis.utils.TimeZoneUtil;
import com.syncnapsis.websockets.service.rpc.RPCCall;

/**
 * Manager-Implementierung für den Zugriff auf User.
 * 
 * @author ultimate
 */
public class UserManagerImpl extends GenericNameManagerImpl<User, Long> implements UserManager, InitializingBean
{
	/**
	 * UserDao für den Datenbankzugriff
	 */
	protected UserDao					userDao;
	/**
	 * The UserRoleManager
	 */
	protected UserRoleManager			userRoleManager;
	/**
	 * The ActionManager
	 */
	protected ActionManager				actionManager;
	/**
	 * The SecurityManager (BaseApplicationManager)
	 */
	protected BaseApplicationManager	securityManager;

	/**
	 * Standard Constructor
	 * 
	 * @param userDao - UserDao für den Datenbankzugriff
	 * @param userRoleManager - the UserRoleManager
	 * @param actionManager - the ActionManager
	 */
	public UserManagerImpl(UserDao userDao, UserRoleManager userRoleManager, ActionManager actionManager)
	{
		super(userDao);
		this.userDao = userDao;
		this.userRoleManager = userRoleManager;
		this.actionManager = actionManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(securityManager, "securityManager must not be null!");
	}

	/**
	 * The SecurityManager (BaseApplicationManager)
	 * 
	 * @return securityManager
	 */
	public BaseApplicationManager getSecurityManager()
	{
		return securityManager;
	}

	/**
	 * The SecurityManager (BaseApplicationManager)
	 * 
	 * @param securityManager - the SecurityManager
	 */
	public void setSecurityManager(BaseApplicationManager securityManager)
	{
		this.securityManager = securityManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UserManager#checkUserLogin(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public User login(String username, String password)
	{
		User user = getByName(username);
		if(securityManager.validatePassword(password, user.getPassword()))
		{
			securityManager.getUserProvider().set(user);
			securityManager.getLocaleProvider().set(user.getLocale());
			// TODO additionally set username (for relogin)
			return user;
		}
		throw new UserNotFoundException(username + " [wrong password]");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UserManager#logout()
	 */
	@Override
	public boolean logout()
	{
		securityManager.getUserProvider().set(null);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UserManager#register(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public User register(String username, String email, String password, String passwordConfirm) throws UserRegistrationFailedException
	{
		if(!isNameAvailable(username))
			throw new UserRegistrationFailedException(ApplicationBaseConstants.ERROR_USERNAME_EXISTS);
		if(!isNameValid(username))
			throw new UserRegistrationFailedException(ApplicationBaseConstants.ERROR_USERNAME_INVALID);
		if(isEmailRegistered(email))
			throw new UserRegistrationFailedException(ApplicationBaseConstants.ERROR_EMAIL_EXISTS);
		if(!isEmailValid(email))
			throw new UserRegistrationFailedException(ApplicationBaseConstants.ERROR_EMAIL_INVALID);
		if(password == null)
			throw new UserRegistrationFailedException(ApplicationBaseConstants.ERROR_NO_PASSWORD);
		if(!password.equals(passwordConfirm))
			throw new UserRegistrationFailedException(ApplicationBaseConstants.ERROR_PASSWORD_MISMATCH);
		
		Date validDate = new Date(securityManager.getTimeProvider().get()
				+ ApplicationBaseConstants.PARAM_REGISTRATION_TIME_TO_VERIFY.asLong());

		User user = new User();
		user.setAccountStatus(EnumAccountStatus.valueOf(ApplicationBaseConstants.PARAM_REGISTRATION_STATUS_DEFAULT.asString()));
		user.setAccountStatusExpireDate(validDate);
		user.setActivated(true);
		// user.setBirthday(birthday);
		// user.setCity(city);
		user.setDateFormat(EnumDateFormat.getDefault());
		// user.setDeleteDate(deleteDate);
		// user.setDescription(description);
		user.setEmail(email);
		// user.setImageURL(imageURL);
		user.setLastActiveDate(new Date(securityManager.getTimeProvider().get()));
		user.setLocale(securityManager.getLocaleProvider().get());
		// user.setNickname(nickname);
		user.setPassword(securityManager.hashPassword(password));
		user.setRegistrationDate(new Date(securityManager.getTimeProvider().get()));
		user.setRole(userRoleManager.getByMask(ApplicationBaseConstants.ROLE_NORMAL_USER));
		user.setRoleExpireDate(null);
		user.setSessionTimeout(ApplicationBaseConstants.PARAM_SESSION_TIMEOUT_DEFAULT.asInt());
		user.setGender(EnumGender.unknown);
		user.setShowEmail(false);
		user.setTimeZoneID(TimeZoneUtil.getDefaultID()); // TODO get from session?
		// user.setTitle(title);
		user.setUsername(username);
		user.setUsingAdvancedMenu(false);
		user.setUsingInfiniteSession(false);
		user.setUsingTooltips(true);

		user = save(user);

		if(securityManager.getMailer() != null)
		{
			// email verification
			RPCCall rpcCall = new RPCCall(getBeanName(), RPC_VERIFY_REGISTRATION, new Object[] { user.getId() });
			Action action = actionManager.createAction(rpcCall, 1, null, validDate);
			securityManager.getMailer().sendVerifyRegistration(user, action);
		}

		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UserManager#getByEmail(java.lang.String)
	 */
	@Override
	public User getByEmail(String email) throws UserNotFoundException
	{
		return userDao.getByEmail(email);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UserManager#isEmailRegistered(java.lang.String)
	 */
	@Override
	public boolean isEmailRegistered(String email)
	{
		return userDao.isEmailRegistered(email);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.impl.GenericNameManagerImpl#isNameValid(java.lang.String)
	 */
	@Override
	public boolean isNameValid(String name)
	{
		if(name == null)
			return false;
		if(securityManager.getNameValidator() != null)
			return securityManager.getNameValidator().isValid(name);
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UserManager#isEmailValid(java.lang.String)
	 */
	@Override
	public boolean isEmailValid(String email)
	{
		if(email == null)
			return false;
		if(securityManager.getEmailValidator() != null)
			return securityManager.getEmailValidator().isValid(email);
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UserManager#updateMailAddress(java.lang.String)
	 */
	@Override
	public boolean updateMailAddress(String email)
	{
		User user = securityManager.getUserProvider().get();
		if(user == null)
			throw new IllegalStateException("no user found");
		if(securityManager.getMailer() != null)
		{
			logger.debug("mail address update requested for user " + user.getId() + ": " + email);

			RPCCall rpcCall = new RPCCall(getBeanName(), RPC_VERIFY_MAIL_ADDRESS, new Object[] { user.getId(), email });

			Date validDate = new Date(securityManager.getTimeProvider().get()
					+ ApplicationBaseConstants.PARAM_EMAIL_CHANGE_TIME_TO_VERIFY.asLong());
			
			Action action = actionManager.createAction(rpcCall, 1, null, validDate);

			securityManager.getMailer().sendVerifyMailAddress(user, email, action);
			
			return true;
		}
		else
		{
			verifyMailAddress(user.getId(), email);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UserManager#verifyRegistration(java.lang.Long)
	 */
	@Override
	public String verifyRegistration(Long userId)
	{
		User user = get(userId);
		user.setAccountStatusExpireDate(null);
		user.setAccountStatus(EnumAccountStatus.active);
		return "ok";
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.UserManager#verifyMailAddress(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public String verifyMailAddress(Long userId, String email)
	{
		User user = get(userId);
		user.setEmail(email);
		save(user);
		return "ok";
	}
}

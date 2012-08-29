package com.syncnapsis.data.service.impl;

import java.util.Date;

import com.syncnapsis.constants.BaseApplicationConstants;
import com.syncnapsis.data.dao.UserDao;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.data.service.UserRoleManager;
import com.syncnapsis.enums.EnumAccountStatus;
import com.syncnapsis.enums.EnumDateFormat;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.enums.EnumGender;
import com.syncnapsis.exceptions.UserNotFoundException;
import com.syncnapsis.security.BaseApplicationManager;
import com.syncnapsis.utils.StringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

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
	@SuppressWarnings("unused")
	private UserDao					userDao;
	/**
	 * The UserRoleManager
	 */
	private UserRoleManager			userRoleManager;
	/**
	 * The SecurityManager (BaseApplicationManager)
	 */
	private BaseApplicationManager	securityManager;

	/**
	 * Standard Constructor
	 * 
	 * @param userDao - UserDao für den Datenbankzugriff
	 * @param userRoleManager - the UserRoleManager
	 */
	public UserManagerImpl(UserDao userDao, UserRoleManager userRoleManager)
	{
		super(userDao);
		this.userDao = userDao;
		this.userRoleManager = userRoleManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(securityManager, "baseApplicationManager must not be null!");
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
		String pwEnc = StringUtil.encodePassword(password, securityManager.getEncryptionAlgorithm());
		User user = getByName(username);
		if(pwEnc.equals(user.getPassword()))
		{
			securityManager.getUserProvider().set(user);
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
	public User register(String username, String email, String password, String passwordConfirm)
	{
		if(!isNameAvailable(username))
			return null;
		if(password == null)
			return null;
		if(!password.equals(passwordConfirm))
			return null;
		// TODO throw Exception on bad username?
		// TODO throw Exception on wrong password?
		// TODO validate email

		User user = new User();
		user.setAccountStatus(EnumAccountStatus.active);
		// TODO use EnumAccountStatus.locked and email verification
		user.setAccountStatusExpireDate(null);
		user.setActivated(true);
		// user.setBirthday(birthday);
		// user.setCity(city);
		user.setDateFormat(EnumDateFormat.getDefault());
		// user.setDeleteDate(deleteDate);
		// user.setDescription(description);
		user.setEmail(email);
		// user.setImageURL(imageURL);
		// user.setLastActiveDate(lastActiveDate);
		user.setLocale(EnumLocale.getDefault()); // TODO get current locale from session
		// user.setNickname(nickname); // TODO = username?
		user.setPassword(StringUtil.encodePassword(password, securityManager.getEncryptionAlgorithm()));
		user.setRegistrationDate(new Date());
		user.setRole(userRoleManager.getByName(BaseApplicationConstants.ROLE_NORMAL_USER));
		user.setRoleExpireDate(null);
		user.setSessionTimeout(60); // TODO set default somewhere else?
		user.setGender(EnumGender.unknown);
		user.setShowEmail(false);
		// user.setTimeZoneID(timeZoneID); // TODO get from session?
		// user.setTitle(title);
		user.setUsername(username);
		user.setUsingAdvancedMenu(false);
		user.setUsingInfiniteSession(false);
		user.setUsingTooltips(true);

		user = save(user);

		return user;
	}
}

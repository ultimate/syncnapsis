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
package com.syncnapsis.utils.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.model.Messenger;
import com.syncnapsis.data.model.MessengerContact;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserContact;
import com.syncnapsis.data.model.base.Model;
import com.syncnapsis.data.service.MessengerContactManager;
import com.syncnapsis.data.service.MessengerManager;
import com.syncnapsis.data.service.UserContactManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.data.service.UserRoleManager;
import com.syncnapsis.enums.EnumAccountStatus;
import com.syncnapsis.enums.EnumDateFormat;
import com.syncnapsis.enums.EnumGender;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.security.BaseApplicationManager;

public class ApplicationBaseDataGenerator extends DataGenerator implements InitializingBean
{
	protected TimeProvider				timeProvider;
	protected BaseApplicationManager	securityManager;

	protected MessengerManager			messengerManager;
	protected MessengerContactManager	messengerContactManager;
	protected UserManager				userManager;
	protected UserContactManager		userContactManager;
	protected UserRoleManager			userRoleManager;

	protected List<Messenger>			messengers;
	protected Map<String, User>			users	= new TreeMap<String, User>();

	public TimeProvider getTimeProvider()
	{
		return timeProvider;
	}

	public void setTimeProvider(TimeProvider timeProvider)
	{
		this.timeProvider = timeProvider;
	}

	public BaseApplicationManager getSecurityManager()
	{
		return securityManager;
	}

	public void setSecurityManager(BaseApplicationManager securityManager)
	{
		this.securityManager = securityManager;
	}

	public MessengerManager getMessengerManager()
	{
		return messengerManager;
	}

	public void setMessengerManager(MessengerManager messengerManager)
	{
		this.messengerManager = messengerManager;
	}

	public MessengerContactManager getMessengerContactManager()
	{
		return messengerContactManager;
	}

	public void setMessengerContactManager(MessengerContactManager messengerContactManager)
	{
		this.messengerContactManager = messengerContactManager;
	}

	public UserManager getUserManager()
	{
		return userManager;
	}

	public void setUserManager(UserManager userManager)
	{
		this.userManager = userManager;
	}

	public UserContactManager getUserContactManager()
	{
		return userContactManager;
	}

	public void setUserContactManager(UserContactManager userContactManager)
	{
		this.userContactManager = userContactManager;
	}

	public UserRoleManager getUserRoleManager()
	{
		return userRoleManager;
	}

	public void setUserRoleManager(UserRoleManager userRoleManager)
	{
		this.userRoleManager = userRoleManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		// @formatter:off
		this.setExcludeTableList(new String[] { 
				"app_userrole", 
				"app_userrole_fallback", 
				"parameter", "action", 
				"messenger", 
				"news" 
				});
		// @formatter:on

		super.afterPropertiesSet();

		Assert.notNull(timeProvider, "timeProvider must not be null!");
		Assert.notNull(securityManager, "securityManager must not be null!");

		Assert.notNull(messengerManager, "messengerManager must not be null!");
		Assert.notNull(messengerContactManager, "messengerContactManager must not be null!");
		Assert.notNull(userManager, "userManager must not be null!");
		Assert.notNull(userContactManager, "userContactManager must not be null!");
		Assert.notNull(userRoleManager, "userRoleManager must not be null!");

		messengers = messengerManager.getAll();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.DataGenerator#generateDummyData(int)
	 */
	@Override
	public void generateDummyData(int amount)
	{
		String nameSource = DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER + DefaultData.STRING_ASCII_NUMBERS;

		User user;
		String name;
		for(int i = 0; i < amount; i++)
		{
			name = random.nextString(random.nextInt(3, 10), nameSource);

			user = getOrCreateUser(name, ApplicationBaseConstants.ROLE_NORMAL_USER);
			logger.debug("created user #" + i + ": " + user.getUsername() + " [" + user.getId() + "]");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.DataGenerator#generateTestData(java.util.Properties)
	 */
	@Override
	public void generateTestData(Properties properties)
	{
		Assert.notNull(properties, "properties must not be null");

		int dummyUsers = Integer.parseInt(properties.getProperty("dummyData.amount"));
		logger.debug("creating dummy players: amount = " + dummyUsers);
		generateDummyData(dummyUsers);
	}

	/**
	 * Create a user with the given name and role
	 * 
	 * @param name
	 * @param rolename
	 * @return
	 */
	public User createUser(String name, String rolename)
	{
		if(name == null)
			name = random.nextString(random.nextInt(3, 10), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS);

		if(rolename == null)
			rolename = ApplicationBaseConstants.ROLE_NORMAL_USER;

		User user = new User();
		user.setAccountStatus(EnumAccountStatus.active);
		user.setAccountStatusExpireDate(null);
		user.setActivated(true);
		user.setBirthday(random.nextDate(new Date(0), new Date(timeProvider.get())));
		user.setCity(random.nextString(random.nextInt(3, 20), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS));
		user.setDateFormat(EnumDateFormat.getDefault());
		user.setDeleteDate(null);
		user.setDescription(random.nextString(random.nextInt(20, 2000), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS));
		user.setEmail(random.nextEmail(Model.LENGTH_EMAIL));
		user.setId(null);
		user.setImageURL(null);
		user.setLastActiveDate(new Date(timeProvider.get()));
		user.setLocale(EnumLocale.getDefault());
		user.setMessengerContacts(new ArrayList<MessengerContact>());
		user.setNickname("nick_" + name);
		user.setPassword(securityManager.hashPassword(name));
		user.setRegistrationDate(new Date(timeProvider.get() - 3600000L));
		user.setRole(userRoleManager.getByName(rolename));
		user.setRoleExpireDate(null);
		user.setSessionTimeout(random.nextInt(30, 120 * 60));
		user.setGender(random.nextEnum(EnumGender.class));
		user.setShowEmail(random.nextBoolean(1, 9));
		user.setTimeZoneID(random.nextTimeZoneId());
		user.setTitle(random.nextString(random.nextInt(2, 20), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS));
		user.setUserContacts1(new ArrayList<UserContact>());
		user.setUserContacts2(new ArrayList<UserContact>());
		user.setUsername(name);
		user.setUsingAdvancedMenu(random.nextBoolean(1, 9));
		user.setUsingInfiniteSession(!name.startsWith("user1"));
		user.setUsingTooltips(random.nextBoolean(9, 1));
		user.setVersion(null);

		user = userManager.save(user);

		MessengerContact messengerContact;

		int prob = random.nextInt(0, messengers.size());
		for(Messenger messenger : messengers)
		{
			if(random.nextBoolean(prob, messengers.size() - prob))
			{
				messengerContact = new MessengerContact();
				if(random.nextBoolean(2, 8))
					messengerContact.setAddress(random.nextEmail(Model.LENGTH_EMAIL));
				else
					messengerContact.setAddress(random.nextString(random.nextInt(5, 20), DefaultData.STRING_EMAIL_COMPLETE_NO_DOT_NO_AT));
				messengerContact.setUser(user);
				messengerContact.setMessenger(messenger);
				messengerContact = messengerContactManager.save(messengerContact);
			}
		}

		// menuQuickLaunchItemManager.addQuickLaunchItem(user.getId(), "ACC_OVER", 0);

		return user;
	}

	public UserContact createUserContact(User user1, User user2)
	{
		UserContact userContact = new UserContact();
		userContact.setApprovedByUser1(true);
		userContact.setApprovedByUser2(true);
		userContact.setUser1(user1);
		userContact.setUser2(user2);
		return userContactManager.save(userContact);
	}

	public User getOrCreateUser(String name, String rolename)
	{
		if(!users.containsKey(name))
			users.put(name, createUser(name, rolename));
		return users.get(name);
	}
}

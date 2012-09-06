package com.syncnapsis.utils.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.constants.BaseApplicationConstants;
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
import com.syncnapsis.utils.StringUtil;

public class ApplicationBaseDataGenerator implements DataGenerator, InitializingBean
{
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	protected TimeProvider				timeProvider;

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
		Assert.notNull(timeProvider, "timeProvider must not be null!");

		Assert.notNull(messengerManager, "messengerManager must not be null!");
		Assert.notNull(messengerContactManager, "messengerContactManager must not be null!");
		Assert.notNull(userManager, "userManager must not be null!");
		Assert.notNull(userContactManager, "userContactManager must not be null!");
		Assert.notNull(userRoleManager, "userRoleManager must not be null!");
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
			name = RandomData.randomString(RandomData.randomInt(3, 10), nameSource);

			user = getOrCreateUser(name, BaseApplicationConstants.ROLE_NORMAL_USER);
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
			name = RandomData.randomString(RandomData.randomInt(3, 10), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS);

		if(rolename == null)
			rolename = BaseApplicationConstants.ROLE_NORMAL_USER;

		User user = new User();
		user.setAccountStatus(EnumAccountStatus.active);
		user.setAccountStatusExpireDate(null);
		user.setActivated(true);
		user.setBirthday(RandomData.randomDate(new Date(0), new Date(timeProvider.get())));
		user.setCity(RandomData.randomString(RandomData.randomInt(3, 20), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS));
		user.setDateFormat(EnumDateFormat.getDefault());
		user.setDeleteDate(null);
		user.setDescription(RandomData.randomString(RandomData.randomInt(20, 2000), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS));
		user.setEmail(RandomData.randomEmail(Model.LENGTH_EMAIL));
		user.setId(null);
		user.setImageURL(null);
		user.setLastActiveDate(new Date(timeProvider.get()));
		user.setLocale(EnumLocale.getDefault());
		user.setMessengerContacts(new ArrayList<MessengerContact>());
		user.setNickname("nick_" + name);
		user.setPassword(StringUtil.encodePassword(name, "SHA"));
		user.setRegistrationDate(new Date(timeProvider.get() - 3600000L));
		user.setRole(userRoleManager.getByName(rolename));
		user.setRoleExpireDate(null);
		user.setSessionTimeout(RandomData.randomInt(30, 120 * 60));
		user.setGender(RandomData.randomEnum(EnumGender.class));
		user.setShowEmail(RandomData.randomBoolean(1, 9));
		user.setTimeZoneID(RandomData.randomTimeZoneId());
		user.setTitle(RandomData.randomString(RandomData.randomInt(2, 20), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS));
		user.setUserContacts1(new ArrayList<UserContact>());
		user.setUserContacts2(new ArrayList<UserContact>());
		user.setUsername(name);
		user.setUsingAdvancedMenu(RandomData.randomBoolean(1, 9));
		user.setUsingInfiniteSession(!name.startsWith("user1"));
		user.setUsingTooltips(RandomData.randomBoolean(9, 1));
		user.setVersion(null);

		user = userManager.save(user);

		MessengerContact messengerContact;

		int prob = RandomData.randomInt(0, messengers.size());
		for(Messenger messenger : messengers)
		{
			if(RandomData.randomBoolean(prob, messengers.size() - prob))
			{
				messengerContact = new MessengerContact();
				if(RandomData.randomBoolean(2, 8))
					messengerContact.setAddress(RandomData.randomEmail(Model.LENGTH_EMAIL));
				else
					messengerContact.setAddress(RandomData.randomString(RandomData.randomInt(5, 20), DefaultData.STRING_EMAIL_COMPLETE_NO_DOT_NO_AT));
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

package com.syncnapsis.data.service.impl;

import java.util.List;

import com.syncnapsis.data.dao.MessengerContactDao;
import com.syncnapsis.data.model.MessengerContact;
import com.syncnapsis.data.service.MessengerContactManager;

/**
 * Manager-Implementierung für den Zugriff auf MessengerContact.
 * 
 * @author ultimate
 */
public class MessengerContactManagerImpl extends GenericManagerImpl<MessengerContact, Long> implements MessengerContactManager
{
	/**
	 * MessengerContactDao für den Datenbankzugriff
	 */
	private MessengerContactDao messengerContactDao;
	
	/**
	 * Standard-Constructor
	 * @param messengerContactDao - MessengerContactDao für den Datenbankzugriff
	 */
	public MessengerContactManagerImpl(MessengerContactDao messengerContactDao)
	{
		super(messengerContactDao);
		this.messengerContactDao = messengerContactDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MessengerContactManager#getByUser(java.lang.Long)
	 */
	@Override
	public List<MessengerContact> getByUser(Long userId)
	{
		return messengerContactDao.getByUser(userId);
	}
}

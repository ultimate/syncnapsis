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
	protected MessengerContactDao messengerContactDao;
	
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

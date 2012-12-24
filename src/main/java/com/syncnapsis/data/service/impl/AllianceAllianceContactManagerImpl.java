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

import com.syncnapsis.data.dao.AllianceAllianceContactDao;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.model.contacts.AllianceAllianceContact;
import com.syncnapsis.data.service.AllianceAllianceContactManager;

/**
 * Manager-Implementierung für den Zugriff auf AllianceAllianceContact.
 * 
 * @author ultimate
 */
public class AllianceAllianceContactManagerImpl extends GenericManagerImpl<AllianceAllianceContact, Long> implements AllianceAllianceContactManager
{
	/**
	 * AllianceAllianceContactDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private AllianceAllianceContactDao	allianceAllianceContactDao;

	/**
	 * Standard-Constructor
	 * @param allianceAllianceContactDao - AllianceAllianceContactDao für den Datenbankzugriff
	 */
	public AllianceAllianceContactManagerImpl(AllianceAllianceContactDao allianceAllianceContactDao)
	{
		super(allianceAllianceContactDao);
		this.allianceAllianceContactDao = allianceAllianceContactDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactManager#getContact(java.lang.Long, java.lang.Long)
	 */
	@Override
	public AllianceAllianceContact getContact(Long contactId1, Long contactId2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactManager#getAuthoritiesForContact1(java.lang.Long, java.lang.Long)
	 */
	@Override
	public AuthoritiesGenericImpl getAuthoritiesForContact1(Long contactId1, Long contactId2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactManager#getAuthoritiesForContact2(java.lang.Long, java.lang.Long)
	 */
	@Override
	public AuthoritiesGenericImpl getAuthoritiesForContact2(Long contactId1, Long contactId2)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

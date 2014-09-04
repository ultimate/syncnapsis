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
package com.syncnapsis.data.service.impl;

import java.util.List;

import com.syncnapsis.data.dao.ContactGroupDao;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.service.ContactGroupManager;

/**
 * Manager-Implementierung f�r den Zugriff auf ContactGroup.
 * 
 * @author ultimate
 */
public class ContactGroupManagerImpl extends GenericManagerImpl<ContactGroup, Long> implements ContactGroupManager
{
	/**
	 * ContactGroupDao f�r den Datenbankzugriff
	 */
	private ContactGroupDao	contactGroupDao;

	/**
	 * Standard-Constructor
	 * @param contactGroupDao - ContactGroupDao f�r den Datenbankzugriff
	 */
	public ContactGroupManagerImpl(ContactGroupDao contactGroupDao)
	{
		super(contactGroupDao);
		this.contactGroupDao = contactGroupDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactGroupManager#getByAlliance(java.lang.Long)
	 */
	@Override
	public List<ContactGroup> getByAlliance(Long allianceId)
	{
		return contactGroupDao.getByAlliance(allianceId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactGroupManager#getByEmpire(java.lang.Long)
	 */
	@Override
	public List<ContactGroup> getByEmpire(Long empireId)
	{
		return contactGroupDao.getByEmpire(empireId);
	}
}

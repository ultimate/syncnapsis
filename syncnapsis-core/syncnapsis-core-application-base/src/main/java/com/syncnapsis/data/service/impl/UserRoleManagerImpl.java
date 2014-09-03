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

import com.syncnapsis.data.dao.UserRoleDao;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.data.service.UserRoleManager;

/**
 * Manager-Implementierung f�r den Zugriff auf UserRole.
 * 
 * @author ultimate
 */
public class UserRoleManagerImpl extends GenericNameManagerImpl<UserRole, Long> implements UserRoleManager
{
	/**
	 * UserRoleDao f�r den Datenbankzugriff
	 */
	protected UserRoleDao			userRoleDao;

	/**
	 * Standard Constructor
	 * 
	 * @param userRoleDao - UserRoleDao f�r den Datenbankzugriff
	 */
	public UserRoleManagerImpl(UserRoleDao userRoleDao)
	{
		super(userRoleDao);
		this.userRoleDao = userRoleDao;
	}

	/* (non-Javadoc)
	 * @see com.syncnapsis.data.service.UserRoleManager#getByMask(int)
	 */
	@Override
	public UserRole getByMask(int mask)
	{
		return userRoleDao.getByMask(mask);
	}
}

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

import com.syncnapsis.data.dao.AuthoritiesGenericImplDao;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.service.AuthoritiesGenericImplManager;

/**
 * Manager-Implementierung für den Zugriff auf AuthoritiesGenericImpl.
 * 
 * @author ultimate
 */
public class AuthoritiesGenericImplManagerImpl extends GenericManagerImpl<AuthoritiesGenericImpl, Long> implements AuthoritiesGenericImplManager
{
	/**
	 * AuthoritiesGenericImplDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private AuthoritiesGenericImplDao authoritiesDao;

	/**
	 * Standard-Constructor, der die DAO speichert
	 * 
	 * @param authoritiesDao - die Dao
	 */
	public AuthoritiesGenericImplManagerImpl(AuthoritiesGenericImplDao authoritiesDao)
	{
		super(authoritiesDao);
		this.authoritiesDao = authoritiesDao;
	}
}

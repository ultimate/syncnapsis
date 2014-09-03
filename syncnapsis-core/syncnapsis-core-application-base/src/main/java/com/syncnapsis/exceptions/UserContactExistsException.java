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
package com.syncnapsis.exceptions;

import com.syncnapsis.data.model.UserContact;

/**
 * Exception, die geworfen wird, wenn ein UserContact erstellt werden soll, der bereits vorhanden
 * ist.
 * 
 * @author ultimate
 */
public class UserContactExistsException extends RuntimeException
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Der bereits vorhandene UserContact
	 */
	private UserContact			userContact;

	/**
	 * Erzeugt eine neue RuntimeException mit gegebener Nachricht
	 * 
	 * @param message - die Nachricht
	 * @param UserContact - der bereits vorhandene UserContact
	 */
	public UserContactExistsException(String message, UserContact userContact)
	{
		super(message);
		this.userContact = userContact;
	}

	/**
	 * Der bereits vorhandene UserContact
	 * 
	 * @return userContact
	 */
	public UserContact getUserContact()
	{
		return userContact;
	}
}

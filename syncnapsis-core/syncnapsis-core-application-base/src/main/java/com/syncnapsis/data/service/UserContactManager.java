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
package com.syncnapsis.data.service;

import java.util.List;

import com.syncnapsis.data.model.UserContact;
import com.syncnapsis.exceptions.UserContactExistsException;
import com.syncnapsis.exceptions.UserSelectionInvalidException;

/**
 * Manager-Interface für den Zugriff auf UserRole.
 * 
 * @author ultimate
 */
public interface UserContactManager extends GenericManager<UserContact, Long>
{
	/**
	 * Hole alle UserContacts zu einem User.
	 * 
	 * @param userId - die id des users
	 * @return die Liste der UserContacts
	 */
	public List<UserContact> getByUser(Long userId);

	/**
	 * Lädt einen UserContact anhand der beiden User. Die Reigenfolge der User muss nicht mit dem
	 * UserContact übereinstimmen.
	 * 
	 * @param userId1 - der erste User
	 * @param userId2 - der zweite User
	 * @return der UserContact, falls er existiert
	 */
	public UserContact getUserContact(Long userId1, Long userId2);

	/**
	 * Erstellt einen neuen UserContact, bei dem User1 bestätigt ist, User2 jedoch noch nicht.
	 * 
	 * @param userId1 - der erste User
	 * @param userId2 - der zweite User
	 * @return der erstellte UserContact
	 */
	public UserContact addUserContact(Long userId1, Long userId2) throws UserContactExistsException, UserSelectionInvalidException;

	/**
	 * Bestätigt einen UserContact. Da vom erstellenden bereits immer eine Bestätigung vorliegt
	 * braucht kein User übergeben werden. Es werden beide Flags auf true gesetzt.
	 * 
	 * @param userContactId
	 * @return der bestätigte UserContact
	 */
	public UserContact approveUserContact(Long userContactId);
}

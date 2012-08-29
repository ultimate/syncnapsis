package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.UserContact;

/**
 * Dao-Interface für den Zugriff auf UserContact
 * 
 * @author ultimate
 */
public interface UserContactDao extends GenericDao<UserContact, Long>
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
}

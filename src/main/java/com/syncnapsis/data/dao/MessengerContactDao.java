package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.MessengerContact;

/**
 * Dao-Interface für den Zugriff auf MessengerContact
 * 
 * @author ultimate
 */
public interface MessengerContactDao extends GenericDao<MessengerContact, Long>
{
	/**
	 * Lade eine Liste aller Messenger-Kontaktadressen zu einem Benutzer.
	 * 
	 * @param userId - die ID des Benutzers
	 * @return die Liste der Messenger-Kontaktadressen
	 */
	public List<MessengerContact> getByUser(Long userId);
}

package com.syncnapsis.data.service;

import java.util.List;

import com.syncnapsis.data.model.MessengerContact;

/**
 * Manager-Interface für den Zugriff auf MessengerContact
 * 
 * @author ultimate
 */
public interface MessengerContactManager extends GenericManager<MessengerContact, Long>
{
	/**
	 * Lade eine Liste aller Messenger-Kontaktadressen zu einem Benutzer.
	 * 
	 * @param userId - die ID des Benutzers
	 * @return die Liste der Messenger-Kontaktadressen
	 */
	public List<MessengerContact> getByUser(Long userId);
}

package com.syncnapsis.data.service;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Contact;
import com.syncnapsis.data.model.base.Authorities;

/**
 * Manager-Interface für den Zugriff auf Kontakt-Zuordnungen.
 * 
 * @author ultimate
 */
public interface ContactManager<C1 extends BaseObject<?>, C2 extends BaseObject<?>, A extends Authorities>
{
	/**
	 * Gibt die Kontaktzuordnung zwischen zwei Parteien zurück
	 * @param contactId1 - die erste Partei
	 * @param contactId2 - die zweite Partei
	 * @return die Kontaktzuordnung
	 */
	public Contact<C1, C2, A> getContact(Long contactId1, Long contactId2);
	
	/**
	 * Gibt die Kontaktrechte zurück, die Partei 1 in Bezug auf Partei 2 hat
	 * @param contactId1 - die erste Partei
	 * @param contactId2 - die zweite Partei
	 * @return die Kontaktrechte
	 */
	public A getAuthoritiesForContact1(Long contactId1, Long contactId2);
	
	/**
	 * Gibt die Kontaktrechte zurück, die Partei 2 in Bezug auf Partei 1 hat
	 * @param contactId1 - die erste Partei
	 * @param contactId2 - die zweite Partei
	 * @return die Kontaktrechte
	 */
	public A getAuthoritiesForContact2(Long contactId1, Long contactId2);
}

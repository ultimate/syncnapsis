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
package com.syncnapsis.data.service;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Contact;
import com.syncnapsis.data.model.base.Authorities;

/**
 * Manager-Interface f�r den Zugriff auf Kontakt-Zuordnungen.
 * 
 * @author ultimate
 */
public interface ContactManager<C1 extends BaseObject<?>, C2 extends BaseObject<?>, A extends Authorities>
{
	/**
	 * Gibt die Kontaktzuordnung zwischen zwei Parteien zur�ck
	 * @param contactId1 - die erste Partei
	 * @param contactId2 - die zweite Partei
	 * @return die Kontaktzuordnung
	 */
	public Contact<C1, C2, A> getContact(Long contactId1, Long contactId2);
	
	/**
	 * Gibt die Kontaktrechte zur�ck, die Partei 1 in Bezug auf Partei 2 hat
	 * @param contactId1 - die erste Partei
	 * @param contactId2 - die zweite Partei
	 * @return die Kontaktrechte
	 */
	public A getAuthoritiesForContact1(Long contactId1, Long contactId2);
	
	/**
	 * Gibt die Kontaktrechte zur�ck, die Partei 2 in Bezug auf Partei 1 hat
	 * @param contactId1 - die erste Partei
	 * @param contactId2 - die zweite Partei
	 * @return die Kontaktrechte
	 */
	public A getAuthoritiesForContact2(Long contactId1, Long contactId2);
}

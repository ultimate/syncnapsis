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

import java.util.List;

import com.syncnapsis.data.model.ContactGroup;

/**
 * Manager-Interface f�r den Zugriff auf ContactGroup
 * 
 * @author ultimate
 */
public interface ContactGroupManager extends GenericManager<ContactGroup, Long>
{
	/**
	 * Gibt eine Liste der Kontaktgruppen zu einem Imperium zur�ck
	 * @param empireId - das Imperium
	 * @return die Liste
	 */
	public List<ContactGroup> getByEmpire(Long empireId);
	
	/**
	 * Gibt eine Liste der Kontaktgruppen zu einer Allianz zur�ck
	 * @param allianceId - das Allianz
	 * @return die Liste
	 */
	public List<ContactGroup> getByAlliance(Long allianceId);
}

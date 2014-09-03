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

import com.syncnapsis.data.model.Empire;

/**
 * Manager-Interface f�r den Zugriff auf Empire.
 * 
 * @author ultimate
 */
public interface EmpireManager extends GenericNameManager<Empire, Long>
{
	/**
	 * Lade eine Liste aller Imperien zu einem Spieler.
	 * 
	 * @param userId - die ID des Spielers
	 * @return die Liste der Imperien
	 */
	public List<Empire> getByPlayer(Long playerId);

	/**
	 * Create a new Empire for the Player currently logged in.<br>
	 * This method will check wether the current Player is allowed to create another Empire or if
	 * his limit has been reached.
	 * 
	 * @param fullName - the full name for the empire
	 * @param shortName - the short name for the empire
	 * @param description - an optional description for the empire
	 * @param imageURL - an optional image url for the empire
	 * @param primaryColor - the primary color for visualising the empire
	 * @param secondaryColor - the secondary color for visualising the empire
	 * @return the newly created Empire
	 */
	public Empire create(String fullName, String shortName, String description, String imageURL, String primaryColor, String secondaryColor);
}

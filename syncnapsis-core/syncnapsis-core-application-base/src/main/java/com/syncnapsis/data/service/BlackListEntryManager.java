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

import com.syncnapsis.data.model.BlackListEntry;

/**
 * Manager-Interface for access to BlackListEntry.
 * 
 * @author ultimate
 */
public interface BlackListEntryManager  extends GenericManager<BlackListEntry, Long>
{
	/**
	 * Get all entries belonging to the given black-list
	 * 
	 * @param blackListId - the ID of the black-list
	 * @return the list of entries
	 */
	public List<BlackListEntry> getByBlackList(Long blackListId);

	/**
	 * Get all values for the given black-list.<br>
	 * Values will be returned as pure strings without their BlackListEntry
	 * 
	 * @param blackListName - the name of the black-list
	 * @return the list of values
	 */
	public List<String> getValuesByBlackListName(String blackListName);

	/**
	 * Add the given values as entries to the given black-list.<br>
	 * Entries will be checked for duplicates and the number of added entries will be returned.
	 * 
	 * @param blackListName - the name of the black-list
	 * @param values - the List of values
	 * @return the number of entries added
	 */
	public int addValuesToBlackList(String blackListName, List<String> values);
}

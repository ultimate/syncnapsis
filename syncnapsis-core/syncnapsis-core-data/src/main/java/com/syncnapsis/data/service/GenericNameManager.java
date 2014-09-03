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

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.model.base.Identifiable;

/**
 * Generic Manager, offering extended functionality for named entities.
 * 
 * @param <T> the Model Class
 * @param <PK> the primary key Class
 * @author ultimate
 */
public interface GenericNameManager<T extends Identifiable<PK>, PK extends Serializable> extends GenericManager<T, PK>
{
	/**
	 * Load a list of all entities ordered by name.<br>
	 * Will only return activated entities.
	 * 
	 * @see GenericNameManager#getOrderedByName(boolean)
	 * @return the list of entities
	 */
	public List<T> getOrderedByName();

	/**
	 * Load a list of all entities ordered by name
	 * 
	 * @param returnOnlyActivated - wether only to return activated entities
	 * @return the list of entities
	 */
	public List<T> getOrderedByName(boolean returnOnlyActivated);

	/**
	 * Load a list of all entities starting with the given prefex ordered by name
	 * 
	 * @param prefix - the prefix for the name
	 * @param nRows - the maximum amount of entities to return
	 * @param returnOnlyActivated - wether only to return activated entities
	 * @return the list of entities
	 */
	public List<T> getByPrefix(String prefix, int nRows, boolean returnOnlyActivated);

	/**
	 * Load an entity by it's name
	 * 
	 * @param name - the name
	 * @return the entity
	 */
	public T getByName(String name);

	/**
	 * Check wether a given name is still available for this model class
	 * 
	 * @param name - the name to check
	 * @return true or false
	 */
	public boolean isNameAvailable(String name);
	
	/**
	 * Check wether a given name is valid for this model class.<br>
	 * This method is supposed to be used to check wether the given name is valid according to
	 * specific rules.<br>
	 * By default (if no specific implementation is given) this method should default to true.<br>
	 * In specific implementations the following checks might be performed for example:<br>
	 * <ul>
	 * <li>length within a given range (min length, max length)</li>
	 * <li>used characters in given range (e.g. ascii only)</li>
	 * <li>required complexibility (e.g. mix of digits and letters)</li>
	 * <li>occurrence of forbidden substrings</li>
	 * <li>black- or white-lists</li>
	 * <li>...</li>
	 * </ul>
	 * 
	 * @param name - the name to check
	 * @return true or false
	 */
	public boolean isNameValid(String name);
}
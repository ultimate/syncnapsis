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
package com.syncnapsis.security;

import java.util.List;

/**
 * Interface for identifying ownable entities that are aware of their owner(s) and may return them
 * using {@link Ownable#getOwners()}
 * 
 * @author ultimate
 */
public interface Ownable<T>
{
	/**
	 * Get the owners for this entity
	 * 
	 * @return the list of owners
	 */
	public List<T> getOwners();
}

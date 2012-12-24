/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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
package com.syncnapsis.utils.serialization;

import java.util.Map;

/**
 * Interface for POJOs designed for Authority-dependant transformation to Maps. In most cases those Maps
 * are used for serialization or other data exchange mechanisms.
 * 
 * @see Serializer
 * @author ultimate
 * @param <M> - the Mapable Type
 */
public interface Mapable<M>
{
	/**
	 * Create a map representing this Object regarding the given authority.
	 * The authorities may be used to decide which properties to include or even be themselves capable of
	 * defining required rules.
	 * 
	 * @param authorities - the authorities to decide accessibility of the properties
	 * @return the map representation of this Object
	 */
	public Map<String, ?> toMap(Object... authorities);

	/**
	 * Merge the given map into the Object regarding the given authority.
	 * The authorities may be used to decide which properties to include or even be themselves capable of
	 * defining required rules.
	 * WARNING: The properties included in the may replace the values currently present in this
	 * Object.
	 * Obviously this Method can be used to fill new Instances with given Values, when an new empty
	 * Instance is created with an empty Constructor before.
	 * 
	 * @param map - the map representation of this Object
	 * @param authorities - the authorities to decide accessibility of the properties
	 * @return the instance itself for convenience
	 */
	public M fromMap(Map<String, ?> map, Object... authorities);
}

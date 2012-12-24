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

import com.syncnapsis.exceptions.DeserializationException;
import com.syncnapsis.exceptions.SerializationException;

/**
 * Interfacee for all (De)-Serializers defining standard Operations for serialization and
 * deserialization
 * 
 * @author ultimate
 * @param <F> - the Type of the Format that is used for (de)-serialization
 */
public interface Serializer<F>
{
	/**
	 * The Mapper used to transform entities to maps and vice versa.
	 * 
	 * @return mapper
	 */
	public Mapper getMapper();
	
	/**
	 * Serialize an entity regarding the given authorities.
	 * The authorities may be used to decide which properties to include or even be themselves capable of
	 * defining required rules.
	 * 
	 * @param entity - the entity to serialize
	 * @param authorities - the authorities to decide accessibility of the properties
	 * @return the serialized representation of the entity
	 * @throws SerializationException - if serialization fails
	 */
	public F serialize(Object entity, Object... authorities) throws SerializationException;

	/**
	 * Deserialize a serialized entity regarding the given authorities and merge the read properties into
	 * the given orinial entity.
	 * The authorities may be used to decide which properties to include or even be themselves capable of
	 * defining required rules.
	 * 
	 * @param serialization - the serialized representation of the entity
	 * @param entity - the original entity to merge the values into or null if new Object creation
	 *            is wanted
	 * @param authorities - the authorities to decide accessibility of the properties
	 * @return the modified or newly created entity
	 * @throws DeserializationException - if deserialization fails
	 * @throws NullPointerException - if entity is null
	 */
	public <T> T deserialize(F serialization, T entity, Object... authorities) throws DeserializationException, NullPointerException;

	/**
	 * Deserialize a serialized entity regarding the given authorities and merge the read properties into
	 * a newly created entity of the given type.
	 * The authorities may be used to decide which properties to include or even be themselves capable of
	 * defining required rules.
	 * 
	 * @param serialization - the serialized representation of the entity
	 * @param entityClass - the type of the entity to return
	 * @param authorities - the authorities to decide accessibility of the properties
	 * @return the modified or newly created entity
	 * @throws DeserializationException - if deserialization fails
	 * @throws NullPointerException - if entityClass is null
	 */
	public <T> T deserialize(F serialization, Class<T> entityClass, Object... authorities) throws DeserializationException;
}

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

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

import com.syncnapsis.security.SecurityManager;

/**
 * Interfacee defining standard Operations for transforming Objects to Maps an vice versa.
 * 
 * @author ultimate
 */
public interface Mapper
{
	/**
	 * The SecurityManager used to controll Field-Access
	 * 
	 * @return the SecurityManager
	 */
	public SecurityManager getSecurityManager();

	/**
	 * Prepare the given entity for serialization by recursively transforming it to a Map if
	 * necessary. Transformation of the entity is necessary if it is of no Wrapper-Type or String or
	 * Boolean. With other words all POJOs (including Collections and Arrays) will be prepared by
	 * preparing all child properties. If an entity is of Type Mapable
	 * {@link Mapable#toMap(Object...)} is used to prepare the entity.
	 * 
	 * @see Mapable#toMap(Object...)
	 * @param entity - the entity to prepare
	 * @param authorities - the authorities controlling visibility
	 * @return the prepared entity
	 */
	public Object prepare(Object entity, Object... authorities);

	/**
	 * Prepare all entities inside an array.
	 * 
	 * @see SerializationUtil#prepare(Object, Object...)
	 * @param array - the array whichs entities to prepare
	 * @param authorities - the authorities controlling visibility
	 * @return the new array
	 */
	public Object[] prepare(Object[] array, Object... authorities);

	/**
	 * Prepare all entities inside a collection.
	 * 
	 * @param collection - the collection whichs entities to prepare
	 * @param authorities - the authorities controlling visibility
	 * @return the new collection
	 */
	public Collection<Object> prepare(Collection<Object> collection, Object... authorities);

	/**
	 * Recursively transform the given entity to a Map.
	 * If entity or any of it's properties is of Type Mapable {@link Mapable#toMap(Object...)} is
	 * used.
	 * 
	 * @see Mapable#toMap(Object...)
	 * @param entity - the entity to transform into a Map
	 * @param authorities - the authorities controlling visibility
	 * @return the map
	 */
	public Map<String, Object> toMap(Object entity, Object... authorities);

	/**
	 * Merge the given prepared object into an entity after deserialization by recursively
	 * transforming it from a Map if necessary. Transformation of the entity is necessary if it is
	 * of no Wrapper-Type or String or Boolean. With other words all POJOs (including Collections
	 * and Arrays) will be merged and by merging all child properties. If an entity is of Type
	 * Mapable {@link Mapable#fromMap(Map, Object...)} is used to merge the entity.
	 * 
	 * @param entity - the entity to merge the prepared object into
	 * @param prepared - the prepared object or the map
	 * @param authorities - the authorities controlling visibility
	 * @return the merged entity
	 */
	public <T> T merge(T entity, Object prepared, Object... authorities);

	/**
	 * Merge the given prepared object to a given type after deserialization by recursively
	 * transforming it from a Map if necessary. Transformation of the entity is necessary if it is
	 * of no Wrapper-Type or String or Boolean. With other words all POJOs (including Collections
	 * and Arrays) will be merged and by merging all child properties. If an entity is of Type
	 * Mapable {@link Mapable#fromMap(Map, Object...)} is used to merge the entity.
	 * 
	 * @param type - the type to convert the prepared object to
	 * @param prepared - the prepared object or the map
	 * @param authorities - the authorities controlling visibility
	 * @return the merged entity
	 */
	public <T> T merge(Class<? extends T> type, Object prepared, Object... authorities);

	/**
	 * Merge the given prepared object into an entity after deserialization by recursively
	 * transforming it from a Map if necessary. Transformation of the entity is necessary if it is
	 * of no Wrapper-Type or String or Boolean. With other words all POJOs (including Collections
	 * and Arrays) will be merged and by merging all child properties. If an entity is of Type
	 * Mapable {@link Mapable#fromMap(Map, Object...)} is used to merge the entity.
	 * 
	 * @see Mapable#toMap(Object...)
	 * @param entity - the entity to prepare
	 * @param prepared - the prepared object to merge into the entity
	 * @param authorities - the authorities controlling visibility
	 * @return the prepared entity
	 */
	public <T> T merge(Type type, T entity, Object prepared, Object... authorities);

	/**
	 * Recursively transform the given map into the given entity.
	 * If entity or any of it's properties is of type Mapable
	 * {@link Mapable#fromMap(Map, Object...)} is used.
	 * 
	 * @see Mapable#fromMap(Map, Object...)
	 * @param map - the map to transform
	 * @param entity - the entity to manipulate (may be an empty entity but not null!)
	 * @param authorities - the authorities controlling visibility
	 * @return the manipulated entity
	 */
	public <T> T fromMap(T entity, Map<String, Object> map, Object... authorities);
}

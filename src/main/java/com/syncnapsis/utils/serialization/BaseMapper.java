/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.utils.serialization;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.syncnapsis.security.AccessController;
import com.syncnapsis.security.SecurityManager;
import com.syncnapsis.utils.ClassUtil;
import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.reflections.Field;
import com.syncnapsis.utils.reflections.FieldCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class BaseMapper implements Mapper, InitializingBean
{
	/**
	 * Logger-Instance
	 */
	protected final Logger		logger	= LoggerFactory.getLogger(getClass());

	/**
	 * The SecurityManager used to controll Field-Access
	 */
	protected SecurityManager	securityManager;

	/**
	 * Construct a new Mapper.
	 */
	public BaseMapper()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapper#getSecurityManager()
	 */
	@Override
	public SecurityManager getSecurityManager()
	{
		return securityManager;
	}

	/**
	 * The SecurityManager used to controll Field-Access
	 * 
	 * @param securityManager - the SecurityManager
	 */
	public void setSecurityManager(SecurityManager securityManager)
	{
		this.securityManager = securityManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		if(this.securityManager == null)
		{
			logger.warn("SecurityManager not defined!");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapper#prepare(java.lang.Object, java.lang.Object[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object prepare(Object entity, Object... authorities)
	{
		if(isInvariant(entity))
			return entity;
		if(entity instanceof Collection)
			return prepare((Collection<Object>) entity, authorities);
		if(entity.getClass().isArray())
			return prepare((Object[]) entity, authorities);
		return toMap(entity, authorities);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapper#prepare(java.lang.Object[],
	 * java.lang.Object[])
	 */
	@Override
	public Object[] prepare(Object[] array, Object... authorities)
	{
		Object[] newArray = new Object[array.length];
		for(int i = 0; i < array.length; i++)
		{
			newArray[i] = prepare(array[i], authorities);
		}
		return newArray;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapper#prepare(java.util.Collection,
	 * java.lang.Object[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Object> prepare(Collection<Object> collection, Object... authorities)
	{
		Collection<Object> newCollection;

		if(collection instanceof List)
			newCollection = new ArrayList<Object>(collection.size());
		else if(collection instanceof Set)
			newCollection = new HashSet<Object>(collection.size());
		else if(collection instanceof Queue)
			newCollection = new PriorityQueue<Object>(collection.size());
		else
		{
			// try generic - ArrayList is default if instantiation fails.
			newCollection = new ArrayList<Object>(collection.size());
			try
			{
				newCollection = collection.getClass().newInstance();
			}
			catch(InstantiationException e)
			{
				logger.warn("Could not genericly instantiate new collection, falling back to List", e);
			}
			catch(IllegalAccessException e)
			{
				logger.warn("Could not genericly instantiate new collection, falling back to List", e);
			}

		}

		// transform the collection entries
		for(Object o : collection)
		{
			newCollection.add(prepare(o, authorities));
		}
		return newCollection;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapper#toMap(java.lang.Object, java.lang.Object[])
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> toMap(Object entity, Object... authorities)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		// create the map for the entity
		if(entity instanceof Mapable)
		{
			map = ((Mapable) entity).toMap(authorities);

			Object value;
			for(String key : map.keySet())
			{
				value = map.get(key);
				if(value != null)
					map.put(key, prepare(value, authorities));
			}
		}
		else if(entity instanceof Map)
		{
			// map = (Map<String, Object>) entity;
			// maybe the given entity is not a map with string keys?!
			// copy the content to the type-safe String-Key-Map
			for(Entry<?, ?> e : ((Map<?, ?>) entity).entrySet())
			{
				map.put(e.getKey().toString(), prepare(e.getValue()));
			}
		}
		else
		{
			List<Field> fields = ReflectionsUtil.findFields(entity.getClass(), SERIALIZABLE);
			for(Field field : fields)
			{
				if(isReadable(field, authorities))
				{
					try
					{
						logger.debug("adding field " + entity.getClass().getSimpleName() + "." + field.getName() + ": " + field.get(entity));
						map.put(field.getName(), prepare(field.get(entity)));
					}
					catch(IllegalAccessException e)
					{
						logger.error("Error accessing field '" + field.getName() + "'", e);
					}
				}
			}
		}

		// now traverse all properties if they are entities or mapable as well
		/*
		 * Object value;
		 * for(String key : map.keySet())
		 * {
		 * value = map.get(key);
		 * if(value != null)
		 * map.put(key, prepare(value, authorities));
		 * }
		 */
		return map;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapper#merge(java.lang.Object, java.lang.Object,
	 * java.lang.Object[])
	 */
	@Override
	public <T> T merge(T entity, Object prepared, Object... authorities)
	{
		return (T) merge(entity == null ? null : entity.getClass(), entity, prepared, authorities);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapper#merge(java.lang.Class, java.lang.Object,
	 * java.lang.Object[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T merge(Class<? extends T> type, Object prepared, Object... authorities)
	{
		return (T) merge(type, null, prepared, authorities);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapper#merge(java.lang.reflect.Type,
	 * java.lang.Object,
	 * java.lang.Object, java.lang.Object[])
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T merge(Type type, T entity, Object prepared, Object... authorities)
	{
		Class<? extends T> cls;

		if(entity != null && type == null)
			cls = (Class<? extends T>) entity.getClass();
		else if(type != null && type instanceof ParameterizedType)
			cls = (Class<? extends T>) ((ParameterizedType) type).getRawType();
		else
			cls = (Class<? extends T>) type;

		// logger.trace("type:     " + type);
		// logger.trace("cls:      " + cls);
		// logger.trace("entity:   " + entity + (entity != null ? " (" + entity.getClass() + ")" :
		// ""));
		// logger.trace("prepared: " + prepared + (prepared != null ? " (" + prepared.getClass() +
		// ")" : ""));

		if(prepared == null)
		{
			return null;
		}
		// logger.trace("Collection? " + (prepared instanceof Collection));
		// logger.trace("Array? " + (prepared.getClass().isArray()));
		if(cls != null && cls.isArray())
		{
			if(prepared instanceof Collection)
				return (T) mergeToArray((Collection<Object>) prepared, cls.getComponentType(), authorities);
			else if(prepared.getClass().isArray())
				return (T) mergeToArray((Object[]) prepared, cls.getComponentType(), authorities);
			else
				return null;
		}
		if(cls != null && Collection.class.isAssignableFrom(cls))
		{
			Class<?> elementType = Object.class;

			Type[] t;
			if(type != null && type instanceof ParameterizedType)
				t = ((ParameterizedType) type).getActualTypeArguments();
			else
				t = ReflectionsUtil.getActualTypeArguments(type);

			if(t != null && t.length > 0 && t[0] instanceof Class)
				elementType = (Class<?>) t[0];

			// logger.trace("Collections element type: " + elementType);
			if(prepared instanceof Collection)
			{
				return (T) mergeToCollection((Collection<Object>) prepared, elementType, authorities);
			}
			else
			{
				return (T) mergeToCollection((Object[]) prepared, elementType, (Class<? extends Collection<?>>) cls, authorities);
			}
		}
		// logger.trace("Map? " + (prepared instanceof Map));
		if(prepared instanceof Map)
		{
			if(entity == null && !cls.equals(Object.class))
			{
				try
				{
					entity = cls.newInstance();
				}
				catch(InstantiationException e)
				{
					logger.error("Could not instantiate type " + type, e);
				}
				catch(IllegalAccessException e)
				{
					logger.error("Could not instantiate type " + type, e);
				}
			}
			else if(entity == null && cls.equals(Object.class))
			{
				// If the required type is Object all information in the map will be lost if merged
				// to a new Object. So we create a new map to hold the merged entities but we do not
				// just return the old map since merging may be required for the entires in the map.
				entity = (T) new HashMap<String, Object>();
			}
			return fromMap(entity, (Map<String, Object>) prepared, authorities);
		}
		// logger.trace("Entity null? " + (entity == null));
		// logger.trace("Prepared matching? " + (cls == null ||
		// cls.isAssignableFrom(prepared.getClass())));
		if(entity == null && (cls == null || cls.isAssignableFrom(prepared.getClass())))
		{
			return (T) prepared;
		}
		// logger.trace("String? " + (cls == String.class) + " && " + (prepared instanceof String));
		if(cls == String.class && prepared instanceof String)
		{
			return (T) prepared;
		}
		// logger.trace("Enum? " + (cls.isEnum()) + " && " + (prepared instanceof String));
		if(cls.isEnum() && prepared instanceof String)
		{
			return (T) Enum.valueOf((Class<? extends Enum>) cls, (String) prepared);
		}
		// logger.trace("Number? " + (ClassUtil.isNumber(cls)) + " && " + (prepared instanceof
		// Number));
		if(ClassUtil.isNumber(cls) && prepared instanceof Number)
		{
			return (T) prepared;
		}
		// logger.trace("Boolean? " + ((cls == boolean.class || cls == Boolean.class)) + " && " +
		// (prepared instanceof Boolean));
		if((cls == boolean.class || cls == Boolean.class) && prepared instanceof Boolean)
		{
			return (T) prepared;
		}
		// logger.trace("Character? " + ((cls == char.class || cls == Character.class)) + " && " +
		// (prepared instanceof Character));
		if((cls == char.class || cls == Character.class) && prepared instanceof Character)
		{
			return (T) prepared;
		}
		// logger.trace("nothing suitable found...!");
		return entity;
	}

	/**
	 * Merge all entities inside an Array to another Array
	 * 
	 * @param array - the array whichs entities to merge
	 * @param newComponentType - the type of the elements in the new array
	 * @param authorities - the authorities controlling visibility
	 * @return the new collection
	 */
	public <T> T[] mergeToArray(Object[] array, Class<? extends T> newComponentType, Object... authorities)
	{
		T[] newArray = createArray(newComponentType, array.length);
		// transform the array entries
		for(int i = 0; i < array.length; i++)
		{
			newArray[i] = merge(newComponentType, array[i], authorities);
		}
		return newArray;
	}

	/**
	 * Merge all entities inside an Collection to an Array
	 * 
	 * @param collection - the collection whichs entities to merge
	 * @param newComponentType - the type of the elements in the new array
	 * @param authorities - the authorities controlling visibility
	 * @return the new collection
	 */
	public <T> T[] mergeToArray(Collection<?> collection, Class<? extends T> newComponentType, Object... authorities)
	{
		T[] newArray = createArray(newComponentType, collection.size());
		// transform the collection entries
		int i = 0;
		for(Object o : collection)
		{
			newArray[i++] = merge(newComponentType, o, authorities);
		}
		return newArray;
	}

	/**
	 * Merge all entities inside an Arry to a Collection
	 * 
	 * @param array - the array whichs entities to merge
	 * @param newComponentType - the type of the elements in the new collection
	 * @param authorities - the authorities controlling visibility
	 * @return the new collection
	 */
	public <T> Collection<T> mergeToCollection(Object[] array, Class<? extends T> newComponentType, Class<? extends Collection<?>> collectionType,
			Object... authorities)
	{
		Collection<T> newCollection = createCollection(newComponentType, collectionType, array.length);
		// transform the array entries
		for(Object o : array)
		{
			newCollection.add(merge(newComponentType, o, authorities));
		}
		return newCollection;
	}

	/**
	 * Merge all entities inside a Collection to an Array
	 * 
	 * @param collection - the collection whichs entities to merge
	 * @param newComponentType - the type of the elements in the new collection
	 * @param authorities - the authorities controlling visibility
	 * @return the new collection
	 */
	@SuppressWarnings("unchecked")
	public <T> Collection<T> mergeToCollection(Collection<?> collection, Class<? extends T> newComponentType, Object... authorities)
	{
		Collection<T> newCollection = createCollection(newComponentType, (Class<? extends Collection<?>>) collection.getClass(), collection.size());
		// transform the collection entries
		for(Object o : collection)
		{
			newCollection.add(merge(newComponentType, o, authorities));
		}
		return newCollection;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapper#fromMap(java.lang.Object, java.util.Map,
	 * java.lang.Object[])
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T fromMap(T entity, Map<String, Object> map, Object... authorities)
	{
		if(entity == null)
			throw new IllegalArgumentException("Cannot genericly create new Instance from null entity!");

		if(entity instanceof Mapable)
		{
			((Mapable) entity).fromMap(map, authorities);
		}
		else if(entity instanceof Map)
		{
			// entity = map;
			// maybe the given entity is not a map with the correct key-type?!
			// copy the content into the entity-Map (and merge all entries if necessary)
			Object oldValue;
			for(String key : map.keySet())
			{
				oldValue = null;

				if(((Map) entity).containsKey(key))
					oldValue = ((Map) entity).get(key);

				((Map) entity).put(key, merge(oldValue, map.get(key), authorities));
			}
		}
		else
		{
			List<Field> fields = ReflectionsUtil.findFields(entity.getClass(), SERIALIZABLE);
			Object oldValue, newValue;
			for(Field field : fields)
			{
				if(isWritable(field, authorities))
				{
					try
					{
						oldValue = field.get(entity);
						// logger.trace("setting '" + entity.getClass().getName() + "." +
						// field.getName() + "' to '" + map.get(field.getName()) + "'");
						if(field.getField() != null)
						{
							newValue = merge(field.getField().getGenericType(), oldValue, map.get(field.getName()), authorities);
						}
						else if(field.getGetter() != null)
						{
							newValue = merge(field.getGetter().getGenericReturnType(), oldValue, map.get(field.getName()), authorities);
						}
						else if(field.getSetter() != null)
						{
							newValue = merge(field.getSetter().getTypeParameters()[0], oldValue, map.get(field.getName()), authorities);
						}
						else
						{
							// Type unknown
							newValue = merge(oldValue, map.get(field.getName()), authorities);
						}
						field.set(entity, newValue);
					}
					catch(IllegalAccessException e)
					{
						logger.error("Error accessing field '" + field.getName() + "'", e);
					}
				}
				else if(map.containsKey(field.getName()))
				{
					logger.warn("Trying to write non-accessible Field '" + field.getName() + "' with authorities "
							+ (authorities != null ? Arrays.asList(authorities) : null));
				}
			}
		}
		return entity;
	}

	protected boolean isInvariant(Object entity)
	{
		if(entity == null)
			return true;
		if(entity instanceof String)
			return true;
		if(entity instanceof Number)
			return true;
		if(entity instanceof Boolean)
			return true;
		if(entity instanceof Date)
			return true;
		if(entity.getClass().isEnum())
			return true;
		return false;
	}

	protected boolean isReadable(Field field, Object... authorities)
	{
		if(this.securityManager == null)
			return true;
		else
			return this.securityManager.getAccessController(Field.class).isAccessible(field, AccessController.READ, authorities);
	}

	protected boolean isWritable(Field field, Object... authorities)
	{
		if(this.securityManager == null)
			return true;
		else
			return this.securityManager.getAccessController(Field.class).isAccessible(field, AccessController.WRITE, authorities);
	}

	// @formatter:off
	/**
	 * FieldCriterion, that checks if a Field/Property is accessible.
	 * Accessibility is given if Field is public or Getter and Setter are present and public.
	 */
	public static final FieldCriterion	SERIALIZABLE	= new FieldCriterion() {
		/*
		 * (non-Javadoc)
		 * @see FieldCriterion#isValidField(java.lang.reflect.Field, java.lang.reflect.Method, java.lang.reflect.Method)
		 */
		@Override
		public boolean isValidField(java.lang.reflect.Field field, Method getter, Method setter)
		{
			// either field must be public
			if(field != null && Modifier.isPublic(field.getModifiers()))
				return true;
			// or getter and setter must exist and be public
			if(getter != null && Modifier.isPublic(getter.getModifiers()) && setter != null
					&& Modifier.isPublic(setter.getModifiers()))
				return true;
			return false;
		}
	};
	// @formatter:on

	/**
	 * Create a new generic Array
	 * 
	 * @param newComponentType - the Array type
	 * @param length - the length of the Array
	 * @return the Array
	 */
	@SuppressWarnings("unchecked")
	protected <T> T[] createArray(Class<? extends T> newComponentType, int length)
	{
		return (T[]) Array.newInstance(newComponentType, length);
	}

	/**
	 * @param newComponentType
	 * @param size
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> Collection<T> createCollection(Class<? extends T> newComponentType, Class<? extends Collection<?>> collectionType, int size)
	{
		if(List.class.isAssignableFrom(collectionType))
			return new ArrayList<T>(size);
		if(Set.class.isAssignableFrom(collectionType))
			return new HashSet<T>(size);
		if(Queue.class.isAssignableFrom(collectionType))
			return new PriorityQueue<T>(size);

		// try generic - ArrayList is default if instantiation fails.
		Collection<T> newCollection = new ArrayList<T>(size);
		try
		{
			newCollection = (Collection<T>) collectionType.newInstance();
		}
		catch(InstantiationException e)
		{
			logger.warn("Could not genericly instantiate new collection, falling back to List", e);
		}
		catch(IllegalAccessException e)
		{
			logger.warn("Could not genericly instantiate new collection, falling back to List", e);
		}
		return newCollection;
	}
}

/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.exceptions.ConversionException;
import com.syncnapsis.utils.reflections.FieldCriterion;
import com.syncnapsis.utils.serialization.Mapper;

/**
 * Utility-Class enabling easier access to special functions of java.lang.reflect. See detailed
 * description at Method-Level.
 * 
 * @author ultimate
 */
public class ReflectionsUtil
{
	/**
	 * Logger-Instance
	 */
	private static final Logger														logger					= LoggerFactory
																													.getLogger(ReflectionsUtil.class);

	/**
	 * Prefix for setter-Methods
	 */
	public static final String														PREFIX_SET				= "set";
	/**
	 * Prefix for getter-Methods
	 */
	public static final String														PREFIX_GET				= "get";
	/**
	 * Prefix for getter-Methods (boolean)
	 */
	public static final String														PREFIX_IS				= "is";

	/**
	 * List for caching the default Fields of a class to prevent researching.
	 */
	private static final Map<String, List<com.syncnapsis.utils.reflections.Field>>	defaultFields			= new TreeMap<String, List<com.syncnapsis.utils.reflections.Field>>();

	/**
	 * The criterions for determining the default Fields of a Class.<br>
	 * The default value for this variable is {FieldCriterion.DEFAULT }
	 * 
	 * @see FieldCriterion
	 * @see FieldCriterion#DEFAULT
	 * @see ReflectionsUtil#getDefaultFieldCriterions()
	 * @see ReflectionsUtil#setDefaultFieldCriterions()
	 */
	private static FieldCriterion[]													defaultFieldCriterions	= { FieldCriterion.DEFAULT };

	/**
	 * The criterions for determining the default Fields of a Class.<br>
	 * The default value for this variable is {FieldCriterion.DEFAULT }
	 * 
	 * @see FieldCriterion
	 * @see FieldCriterion#DEFAULT
	 * @see ReflectionsUtil#getDefaultFieldCriterions()
	 * @see ReflectionsUtil#setDefaultFieldCriterions()
	 * @return die Kriterien
	 */
	public static FieldCriterion[] getDefaultFieldCriterions()
	{
		return defaultFieldCriterions;
	}

	/**
	 * The criterions for determining the default Fields of a Class.<br>
	 * The default value for this variable is {FieldCriterion.DEFAULT }
	 * 
	 * @see FieldCriterion
	 * @see FieldCriterion#DEFAULT
	 * @see ReflectionsUtil#getDefaultFieldCriterions()
	 * @see ReflectionsUtil#setDefaultFieldCriterions()
	 * @param defaultFieldCriterions - die Kriterien
	 */
	public static void setDefaultFieldCriterions(FieldCriterion[] defaultFieldCriterions)
	{
		ReflectionsUtil.defaultFieldCriterions = defaultFieldCriterions;
	}

	/**
	 * Set the accessible attribute for an AccessibleObject (Field, Method or Constructor).<br>
	 * The action is performed within a doPrivileged-Block.<br>
	 * The old value of accessible is returned.
	 * 
	 * @see AccessibleObject#setAccessible(boolean)
	 * @see AccessibleObject#isAccessible()
	 * @see AccessController#doPrivileged(PrivilegedAction)
	 * @see PrivilegedAction
	 * @param o - the AccessibleObject
	 * @param accessible - the new value for accessible
	 * @return the old value for accessible
	 */
	public static boolean setAccessible(final AccessibleObject o, final boolean accessible)
	{
		return AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
			/*
			 * (non-Javadoc)
			 * @see java.security.PrivilegedAction#run()
			 */
			@Override
			public Boolean run()
			{
				boolean old = o.isAccessible();
				o.setAccessible(accessible);
				return old;
			}
		});
	}

	/**
	 * Set the value of a Field of a specified Object via Reflections.<br>
	 * If the Field is not accessible for this action (not public) it will be made temporarily
	 * accessible.<br>
	 * For convenience the value that has been set will be returned.
	 * 
	 * @see ReflectionsUtil#setAccessible(AccessibleObject, boolean)
	 * @param <T> - the type of the value
	 * @param o - the Object whichs Field to set
	 * @param fieldName - the name of the Field to set
	 * @param value - the value to set.
	 * @return the value that has been set for convienience
	 * @throws IllegalAccessException - if the Field could not be set
	 * @throws NoSuchFieldException - if the Field does not exist
	 */
	public static <T> T setField(Object o, String fieldName, T value) throws IllegalAccessException, NoSuchFieldException
	{
		Field field = findField(o.getClass(), fieldName);
		boolean oldAccessible = field.isAccessible();
		if(!Modifier.isPublic(field.getModifiers()) && !oldAccessible)
			setAccessible(field, true);
		field.set(o, value);
		if(!Modifier.isPublic(field.getModifiers()) && !oldAccessible)
			setAccessible(field, false);
		return value;
	}

	/**
	 * Get the value of a Field of a specified Object via Reflections.<br>
	 * If the Field is not accessible for this action (not public) it will be made temporarily
	 * accessible.<br>
	 * 
	 * @see ReflectionsUtil#setAccessible(AccessibleObject, boolean)
	 * @param o - the Object whichs Field to get
	 * @param fieldName - the name of the Field to get
	 * @return the value of the Field
	 * @throws IllegalAccessException - if the Field could not be set
	 * @throws NoSuchFieldException - if the Field does not exist
	 */
	public static Object getField(Object o, String fieldName) throws IllegalAccessException, NoSuchFieldException
	{
		return getField(o, findField(o.getClass(), fieldName));
	}

	/**
	 * Get the value of a Field of a specified Object via Reflections.<br>
	 * If the Field is not accessible for this action (not public) it will be made temporarily
	 * accessible.<br>
	 * 
	 * @see ReflectionsUtil#setAccessible(AccessibleObject, boolean)
	 * @param o - the Object whichs Field to get
	 * @param field- the Field to get
	 * @return the value of the Field
	 * @throws IllegalAccessException - if the Field could not be set
	 */
	public static Object getField(Object o, Field field) throws IllegalAccessException
	{
		boolean oldAccessible = field.isAccessible();
		if(!Modifier.isPublic(field.getModifiers()) && !oldAccessible)
			setAccessible(field, true);
		Object value = field.get(o);
		if(!Modifier.isPublic(field.getModifiers()) && !oldAccessible)
			setAccessible(field, false);
		return value;
	}

	/**
	 * Get the value of a Field of a specified Object via Reflections.<br>
	 * If the Field is not accessible for this action (not public) it will be made temporarily
	 * accessible.<br>
	 * 
	 * @see ReflectionsUtil#getField(Object, String)
	 * @param <T> - the type of the value
	 * @param o - the Object whichs Field to get
	 * @param fieldName - the name of the Field to get
	 * @param requiredClass - the required class for the value
	 * @return the value of the Field
	 * @throws IllegalAccessException - if the Field could not be set
	 * @throws NoSuchFieldException - if the Field does not exist
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getField(Object o, String fieldName, Class<T> requiredClass) throws IllegalAccessException, NoSuchFieldException
	{
		return (T) getField(o, fieldName);
	}

	/**
	 * Set the value of a {@link com.syncnapsis.utils.reflections.Field}
	 * 
	 * @see com.syncnapsis.utils.reflections.Field#set(Object, Object)
	 * @param o - the Object whichs Field to set
	 * @param field - the Field to set
	 * @param value - the new value of the Field
	 * @return the old value of the Field for convenience
	 * @throws IllegalAccessException - if the Field and it's setter are not accessible
	 */
	public static Object setField(Object o, com.syncnapsis.utils.reflections.Field field, Object value) throws IllegalAccessException
	{
		return field.set(o, value);
	}

	/**
	 * Get the value of a {@link com.syncnapsis.utils.reflections.Field}
	 * 
	 * @see com.syncnapsis.utils.reflections.Field#get(Object)
	 * @param o - the Object whichs Field to get
	 * @param field - the Field to get
	 * @return the value of the Field
	 * @throws IllegalAccessException - if the Field and it's getter are not accessible
	 */
	public static Object getField(Object o, com.syncnapsis.utils.reflections.Field field) throws IllegalAccessException
	{
		return field.get(o);
	}

	/**
	 * Get the value of a Field in a required type
	 * 
	 * @see com.syncnapsis.utils.reflections.Field#get(Object)
	 * @see ReflectionsUtil#getField(Object, com.syncnapsis.utils.reflections.Field)
	 * @param o - the Object whichs Field to get
	 * @param field - the Field to get
	 * @param requiredClass - the required type
	 * @return the value of the Field
	 * @throws IllegalAccessException - if the Field and it's getter are not accessible
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getField(Object o, com.syncnapsis.utils.reflections.Field field, Class<T> requiredClass) throws IllegalAccessException
	{
		return (T) getField(o, field);
	}

	/**
	 * Get a field value by a given key.<br>
	 * Keys are passed in property format and this algorithm will recursively scan the passed Object
	 * for a Field in the hierarchie specified by the key.<br>
	 * For example if we had the following Object (in json notation): <code>
	 * <pre>
	 * {
	 *   id: 1,
	 *   name: "foo",
	 *   role: {
	 *     id: 2,
	 *     rolename: "bar"
	 *   }
	 * }
	 * </pre>
	 * </code>
	 * <ul>
	 * <li>"name" will return "foo"</li>
	 * <li>"role.rolename" will return "bar"</li>
	 * </ul>
	 * 
	 * @param o - the Object to scan for the key
	 * @param key - the key describing the requested field
	 * @return the field value
	 * @throws NoSuchFieldException if no such field exists
	 * @throws IllegalAccessException if the field found is not accessible
	 */
	public static Object getFieldByKey(Object o, String key) throws NoSuchFieldException, IllegalAccessException
	{
		int dotIndex = key.indexOf('.');
		if(dotIndex == -1)
		{
			if(o instanceof Map)
			{
				if(((Map<?, ?>) o).containsKey(key))
					return ((Map<?, ?>) o).get(key);
				else
					throw new NoSuchFieldException(key);
			}
			else
			{
				return getField(o, key);
			}
		}
		else
		{
			String field = key.substring(0, dotIndex);
			String subField = key.substring(dotIndex + 1);
			return getFieldByKey(getFieldByKey(o, field), subField);
		}
	}

	/**
	 * Get the Field-Object for a Field specified by name.<br>
	 * For retrieving the Field this method recursively scans super-Classes as well.
	 * 
	 * @param cls - the Class that contains the Field
	 * @param fieldName - the name of the Field
	 * @return the Field-Object
	 * @throws NoSuchFieldException - if no matching Field could be found
	 */
	public static Field findField(Class<?> cls, String fieldName) throws NoSuchFieldException
	{
		Field field = null;
		while(field == null)
		{
			try
			{
				field = cls.getDeclaredField(fieldName);
			}
			catch(NoSuchFieldException e)
			{
				cls = cls.getSuperclass();
				if(cls.equals(Object.class))
					throw e;
			}
		}
		return field;
	}

	/**
	 * Retrieve all Fields of a Class as {@link com.syncnapsis.utils.reflections.Field} matching the
	 * given criterions.<br>
	 * The given criterions are applied to all Fields found to check wehter they are valid according
	 * to the criterion (e.g. if getter and setter are present).
	 * 
	 * @see FieldCriterion
	 * @param cls - the Class to scan
	 * @param criterions - the criterions
	 * @return the List of Fields
	 */
	public static List<com.syncnapsis.utils.reflections.Field> findFields(Class<?> cls, FieldCriterion... criterions)
	{
		List<com.syncnapsis.utils.reflections.Field> fields = new LinkedList<com.syncnapsis.utils.reflections.Field>();

		com.syncnapsis.utils.reflections.Field f;
		while(!cls.equals(Object.class))
		{
			Field[] clsFields = cls.getDeclaredFields();
			Method getter, setter;
			for(Field field : clsFields)
			{
				if(field.isSynthetic())
					continue;
				// if(Modifier.isStatic(field.getModifiers()))
				// continue;

				getter = getGetter(cls, field);
				setter = getSetter(cls, field);

				try
				{
					f = new com.syncnapsis.utils.reflections.Field(field.getName(), field, getter, setter);
				}
				catch(Exception e)
				{
					logger.debug("skipping", e);
					continue;
				}
				boolean valid = true;
				for(FieldCriterion criterion : criterions)
				{
					valid = valid && criterion.isValidField(field, getter, setter);
				}
				if(valid)
					fields.add(f);
			}
			cls = cls.getSuperclass();
		}

		// TODO aditionally scan getters and setters? (they may not match the field names...)

		return fields;
	}

	/**
	 * Retrieve all Fields of a Class as {@link com.syncnapsis.utils.reflections.Field} matching the
	 * default
	 * criterions.
	 * 
	 * @see ReflectionsUtil#defaultFieldCriterions
	 * @see ReflectionsUtil#getDefaultFieldCriterions()
	 * @see ReflectionsUtil#setDefaultFieldCriterions()
	 * @see FieldCriterion
	 * @param cls - the Class to scan
	 * @return the List of Fields
	 */
	public static List<com.syncnapsis.utils.reflections.Field> findDefaultFields(Class<?> cls)
	{
		if(defaultFields.get(cls.getName()) == null)
		{
			defaultFields.put(cls.getName(), findFields(cls, defaultFieldCriterions));
		}
		return defaultFields.get(cls.getName());
	}

	/**
	 * Find a specific Method by its name and parameter types.<br>
	 * While doing this, no difference will be made between Wrapper-Types and primitives.
	 * Compatability is therefore checked via
	 * {@link ReflectionsUtil#isMethodSuitableFor(Method, Class...)}<br>
	 * In addition to that this method offers a work around when generic parameters or null
	 * parameters are present, too. The common functionality of {@link java.lang.Class} is not
	 * usable if the exact generic type is unknown.
	 * 
	 * @see ReflectionsUtil#isMethodSuitableFor(Method, Class[])
	 * @param cls - the Class to scan for Methods
	 * @param method - the method name
	 * @param inClasses - the list of parameter types
	 * @return the Method found
	 */
	public static Method findMethod(Class<?> cls, String method, Class<?>... inClasses)
	{
		Method result = null;
		boolean moreSpecificTypes;
		logger.trace("looking for " + method);
		for(Method m : cls.getMethods())
		{
			logger.trace("  comparing " + m.getName());
			if(m.getName().equals(method))
			{
				if(isMethodSuitableFor(m, inClasses))
				{
					logger.trace("  method found: " + m.getName());
					if(result == null)
						result = m;
					else
					{
						// is this method better than the previous one?
						// if methods are overloaded and method(Object) comes before method(POJO),
						// method(Object) would be returned even if the passed argument is of type
						// POJO. To prevent this, we check wether the parameters of the second
						// method found are more specific (subtypes) of the first method. If so the
						// second method is "better"
						if(m.getParameterTypes().length == result.getParameterTypes().length)
						{
							moreSpecificTypes = true;
							int i;
							for(i = 0; i < m.getParameterTypes().length; i++)
							{
								if(!result.getParameterTypes()[i].isAssignableFrom(m.getParameterTypes()[i]))
								{
									moreSpecificTypes = false;
								}
							}
							if(moreSpecificTypes && i > 0)
								result = m;
							continue;
						}
						if(m.getReturnType() != result.getReturnType() && m.getReturnType().isAssignableFrom(result.getReturnType()))
						{
							result = m;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Find a specific Method by its name and parameter values.<br>
	 * By using {@link ReflectionsUtil#isMethodSuitableFor(Method, Object...)} matching Methods are
	 * checked for compatability with the given values. This way even correct treatment of
	 * null-Parameters, generic-Parameters and var-Args is possible.
	 * 
	 * @see ReflectionsUtil#isMethodSuitableFor(Method, Object...)
	 * @param cls - the Class to scan for Methods
	 * @param method - the method name
	 * @param args - the list of parameter values
	 * @return the Method found
	 */
	public static Method findMethod(Class<?> cls, String method, Object... args)
	{
		logger.trace("looking for " + method);
		for(Method m : cls.getMethods())
		{
			logger.trace("  comparing " + m.getName());
			if(m.getName().equals(method))
			{
				logger.trace("    " + m.getParameterTypes().length + " vs. " + args.length);
				if(m.getParameterTypes().length == args.length)
				{
					if(isMethodSuitableFor(m, args))
					{
						logger.trace("  method found: " + m.getName());
						return m;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Check wether a Method is suitable for the given Array of parameter types.<br>
	 * Therefore a suitability Matrix is genericly generated once during first usage guaranteeing
	 * correct handling of null values, primitives and wrapper-Types. (null not permitted for
	 * primitive but for wrapper; primitive suitable for wrapper and vice versa).
	 * 
	 * @param m - the Method to check
	 * @param inClasses - the Array of parameter types.
	 * @return true or false
	 */
	public static boolean isMethodSuitableFor(Method m, Class<?>... inClasses)
	{
		if(inClasses == null)
			inClasses = new Class<?>[0];
		logger.trace("    " + m.getParameterTypes().length + " vs. " + inClasses.length);
		if(m.getParameterTypes().length != inClasses.length)
		{
			return false;
		}

		if(suitabilityMatrix == null)
			initSuitabilityMatrix();

		int v, c;
		for(int i = 0; i < inClasses.length; i++)
		{
			logger.trace("      " + m.getParameterTypes()[i].getName() + " vs. " + (inClasses[i] != null ? inClasses[i].getName() : "null"));

			if(inClasses[i] == m.getParameterTypes()[i])
				continue;
			if(inClasses[i] != null && m.getParameterTypes()[i].isAssignableFrom(inClasses[i]))
				continue;

			for(v = 0; v < suitValueClasses.length - 1; v++)
			{
				if(inClasses[i] == null)
					break;
				if(suitValueClasses[v] != null && suitValueClasses[v].isAssignableFrom(inClasses[i]))
					break;
			}

			for(c = 0; c < suitClasses.length - 1; c++)
			{
				if(suitClasses[c].equals(m.getParameterTypes()[i]))
					break;
			}

			if(!suitabilityMatrix[v][c])
				return false;
			if(c == suitClasses.length && inClasses[i] != null && !m.getParameterTypes()[i].isAssignableFrom(inClasses[i]))
				return false;
		}
		return true;
	}

	/**
	 * Check wether a Method is suitable for the given Array of parameter values.<br>
	 * Therefor parameters types are determined (including null-types) and forwarded to
	 * {@link ReflectionsUtil#isMethodSuitableFor(Method, Class...)} Therefore a suitability Matrix
	 * 
	 * @see ReflectionsUtil#isMethodSuitableFor(Method, Class...)
	 * @param m - the Method to check
	 * @param args - the Array of parameter values.
	 * @return true or false
	 */
	public static boolean isMethodSuitableFor(Method m, Object... args)
	{
		Class<?>[] inClasses = null;
		if(args != null)
		{
			inClasses = new Class<?>[args.length];
			for(int i = 0; i < args.length; i++)
			{
				if(args[i] != null)
					inClasses[i] = args[i].getClass();
			}
		}

		return isMethodSuitableFor(m, inClasses);
	}

	/**
	 * Find a suitable method for the given method name and an array of arguments, that should be
	 * passed to the method.<br>
	 * In difference to {@link ReflectionsUtil#findMethod(Class, String, Object...)} arguments are
	 * converted if necessary using special conversion rules (e.g. for primitives and numbers).<br>
	 * <b>Warning:</b> If a suitable method is found, the argument array will be modified and filled
	 * with the converted arguments!<br>
	 * <br>
	 * Simply forwards to
	 * {@link ReflectionsUtil#findMethodAndConvertArgs(Class, String, Object[], Mapper, Object...)}
	 * with no mapper.
	 * 
	 * @see ReflectionsUtil#checkAndConvertArgs(Method, Object[], Mapper, Object...)
	 * @param cls - the class that contains the method
	 * @param method - the name of the method to find
	 * @param args - the arguments to pass to the method
	 * @return the method found, or null if no method was suitable
	 */
	public static Method findMethodAndConvertArgs(Class<?> cls, String method, Object[] args)
	{
		return findMethodAndConvertArgs(cls, method, args, null);
	}

	/**
	 * Find a suitable method for the given method name and an array of arguments, that should be
	 * passed to the method.<br>
	 * In difference to {@link ReflectionsUtil#findMethod(Class, String, Object...)} arguments are
	 * converted if necessary using special conversion rules (e.g. for primitives and numbers) or
	 * using the given mapper (e.g. for entities).<br>
	 * <b>Warning:</b> If a suitable method is found, the argument array will be modified and filled
	 * with the converted arguments!
	 * 
	 * @see ReflectionsUtil#checkAndConvertArgs(Method, Object[], Mapper, Object...)
	 * @param cls - the class that contains the method
	 * @param method - the name of the method to find
	 * @param args - the arguments to pass to the method
	 * @param mapper - an optional mapper used for merging, if no other conversion rules is found
	 * @param authorities - the authorities to pass to merge(..)
	 * @return the method found, or null if no method was suitable
	 */
	public static Method findMethodAndConvertArgs(Class<?> cls, String method, Object[] args, Mapper mapper, Object... authorities)
	{
		logger.trace("looking for " + method + " in class " + cls);
		Method m;
		// when class is a proxy class, looking at the interfaces is more precise!
		if(Proxy.isProxyClass(cls))
		{
			logger.trace("class is proxy with interfaces " + Arrays.asList(cls.getInterfaces()));
			for(Class<?> iface : cls.getInterfaces())
			{
				logger.trace("looking in " + iface.getName());
				m = findMethodAndConvertArgs(cls, iface, method, args, mapper, authorities);
				if(m != null)
					return m;
				// continue with next interface if no method found
			}
		}
		// lookup the class itself
		logger.trace("looking in " + cls.getName());
		m = findMethodAndConvertArgs(cls, cls, method, args, mapper, authorities);
		return m;
	}

	/**
	 * Find a suitable method for the given method name and an array of arguments, that should be
	 * passed to the method.<br>
	 * In difference to {@link ReflectionsUtil#findMethod(Class, String, Object...)} arguments are
	 * converted if necessary using special conversion rules (e.g. for primitives and numbers) or
	 * using the given mapper (e.g. for entities).<br>
	 * <b>Warning:</b> If a suitable method is found, the argument array will be modified and filled
	 * with the converted arguments!
	 * 
	 * @see ReflectionsUtil#checkAndConvertArgs(Method, Object[], Mapper, Object...)
	 * @param targetClass - the class that contains the method
	 * @param lookupClass - the class in which to look for the method (e.g. a super class or
	 *            interface)
	 * @param method - the name of the method to find
	 * @param args - the arguments to pass to the method
	 * @param mapper - an optional mapper used for merging, if no other conversion rules is found
	 * @param authorities - the authorities to pass to merge(..)
	 * @return the method found, or null if no method was suitable
	 */
	public static Method findMethodAndConvertArgs(Class<?> targetClass, Class<?> lookupClass, String method, Object[] args, Mapper mapper,
			Object... authorities)
	{
		// when Generics are present getMethod won't work well!
		// so finding the method by name from the array is more effective
		for(Method m : lookupClass.getMethods())
		{
			logger.trace("  comparing " + m.getName());
			if(m.getName().equals(method))
			{
				// check if the given args are suitable for the Method
				// also check for var args and convertable types...
				Object[] tmp = Arrays.copyOf(args, args.length);
				if(checkAndConvertArgs(targetClass, lookupClass, m, tmp, mapper, authorities))
				{
					// tmp has been modified
					// copy changes back to args
					for(int i = 0; i < args.length; i++)
						args[i] = tmp[i];
					return m;
				}
			}
		}
		return null;
	}

	/**
	 * Check the arguments for a given method.<br>
	 * The argument array will be processed and each argument will be checked for suitability and
	 * converted if necessary.<br>
	 * <b>Warning:</b> The given argument array will be modified while processing the arguments!
	 * 
	 * @see ReflectionsUtil#checkAndConvertArg(Class, int, Object[], Mapper, Object...)
	 * @param m - the method to check the arguments for
	 * @param args - the arguments to pass to the method
	 * @param mapper - an optional mapper used for merging, if no other conversion rules is found
	 * @param authorities - the authorities to pass to merge(..)
	 * @return true if all arguments passed the check and have been converted (if necessary),
	 *         otherwise false
	 */
	public static boolean checkAndConvertArgs(Class<?> targetClass, Class<?> lookupClass, Method m, Object[] args, Mapper mapper, Object... authorities)
	{
		int params = m.getParameterTypes().length;
		logger.trace(params + (m.isVarArgs() ? "(varArgs)" : "") + " vs. " + args.length);
		if(m.isVarArgs())
		{
			if(params > args.length + 1)
				return false;
		}
		else if(params != args.length)
		{
			return false;
		}

		logger.info("targetClass vs. declaringClass ? " + targetClass.getName() + " vs. " + lookupClass.getName());
		Map<Type, Map<TypeVariable<?>, Type>> targetClassTypeArgs = resolveTypeArguments(lookupClass);

		// check each type to the given argument
		// if possible (and necessary) convert the arg to the type (e.g. Long to int)
		Class<?> type = null;
		boolean varArgsUsed = false;
		for(int i = 0; i < args.length; i++)
		{
			// if we have more args than params, we have varArgs X[]
			// --> every additional arg must be of type X
			// for the arg at the varArg Position both X[] is only suitable if no more args follow
			logger.info("  " + m.getParameterTypes()[i >= params ? params - 1 : i] + " vs. " + (args[i] == null ? null : args[i].getClass()));
			logger.info("  " + getActualParameterType(lookupClass, m, targetClassTypeArgs, i >= params ? params - 1 : i) + " vs. " + (args[i] == null ? null : args[i].getClass()));

			if(i < params - 1)
			{
				if(m.getParameterTypes()[i] == m.getGenericParameterTypes()[i])
					type = m.getParameterTypes()[i];
				else
					type = getActualParameterType(lookupClass, m, targetClassTypeArgs, i);
				if(checkAndConvertArg(type, i, args, mapper, authorities))
					continue;
				return false;
			}
			else if(i == params - 1)
			{
				if(m.getParameterTypes()[i] == m.getGenericParameterTypes()[i])
					type = m.getParameterTypes()[i];
				else
					type = getActualParameterType(lookupClass, m, targetClassTypeArgs, i);
				if(checkAndConvertArg(type, i, args, mapper, authorities))
				{
					// if arg is null it may be X[] or X, but only if X is not primitive
					// set varArgsUsed to true if this is the case in order to check following args
					if(args[i] == null && type.getComponentType() != null && !type.getComponentType().isPrimitive())
						varArgsUsed = true;
					continue;
				}
				else if(m.isVarArgs())
				{
					type = type.getComponentType(); // will be available for following args, too
					varArgsUsed = true;
					if(checkAndConvertArg(type, i, args, mapper, authorities))
						continue;
				}
				return false;
			}
			else
			{
				if(varArgsUsed)
				{
					if(checkAndConvertArg(type, i, args, mapper, authorities))
						continue;
				}
				else
				{
					// array is used instead of varArgs
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Check wether an argument is suitable for a required type and convert it if necessary.<br>
	 * <b>Warning:<b> The argument at the specified index will be replaced within the array when
	 * converted!
	 * 
	 * @see ReflectionsUtil#convert(Class, Object, Mapper, Object...)
	 * @param requiredType - the required type
	 * @param arg - the index of the argument to check and convert
	 * @param args - the array of arguments
	 * @param mapper - an optional mapper used for merging, if no other conversion rules is found
	 * @param authorities - the authorities to pass to merge(..)
	 * @return true if the argument passed the check and has been converted, otherwise false
	 */
	protected static boolean checkAndConvertArg(Class<?> requiredType, int arg, Object[] args, Mapper mapper, Object... authorities)
	{
		try
		{
			args[arg] = ReflectionsUtil.convert(requiredType, args[arg], mapper, authorities);
			return true;
		}
		catch(ConversionException e)
		{
			return false;
		}
	}

	/**
	 * Convert an Object to a required type if possible.<br>
	 * For details see {@link ReflectionsUtil#convert(Class, Object, Mapper, Object...)}
	 * 
	 * @see ReflectionsUtil#convert(Class, Object, Mapper, Object...)
	 * @param requiredType - the required type
	 * @param original - the original object to convert
	 * @return the converted object
	 * @throws ConversionException if the conversion fails
	 */
	public static <T> T convert(Class<T> requiredType, Object original) throws ConversionException
	{
		return convert(requiredType, original, null);
	}

	/**
	 * Convert an Object to a required type if possible.<br>
	 * This method is designed for use with:<br>
	 * <ul>
	 * <li>numbers, including chars (e.g. int &lt;--&gt; long, etc.)</li>
	 * <li>objects (checks assignability, see {@link Class#isAssignableFrom(Class)})</li>
	 * <li>objects (uses the given mapper for merging, see
	 * {@link Mapper#merge(Class, Object, Object...)}</li>
	 * </ul>
	 * 
	 * @param requiredType - the required type
	 * @param original - the original object to convert
	 * @param mapper - an optional mapper used for merging, if no other conversion rules is found
	 * @param authorities - the authorities to pass to merge(..)
	 * @return the converted object
	 * @throws ConversionException if the conversion fails; possible reasons:
	 *             <ul>
	 *             <li>"null not suitable for required primitive type!"</li>
	 *             <li>original.getClass() + " cannot be converted to " + requiredType</li>
	 *             <li>original.getClass() + " cannot be merged to " + requiredType</li>
	 *             <li>"no conversion rule found!"</li>
	 *             </ul>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(Class<T> requiredType, Object original, Mapper mapper, Object... authorities) throws ConversionException
	{
		if(requiredType.isPrimitive() || Number.class.isAssignableFrom(requiredType) || Character.class == requiredType
				|| Boolean.class == requiredType)
		{
			// Numbers, Char, Boolean here
			if(original == null)
			{
				if(requiredType.isPrimitive())
					throw new ConversionException("null not suitable for required primitive type!");
				else
					return (T) original;
			}
			if(requiredType == int.class || requiredType == Integer.class)
			{
				if(original instanceof Integer)
					return (T) original;
				else if(original instanceof Number)
					return (T) (Integer) ((Number) original).intValue();
				else if(original instanceof Character)
					return (T) (Integer) (int) ((Character) original).charValue();
				else
					throw new ConversionException(original.getClass() + " cannot be converted to " + requiredType);
			}
			else if(requiredType == long.class || requiredType == Long.class)
			{
				if(original instanceof Long)
					return (T) original;
				else if(original instanceof Number)
					return (T) (Long) ((Number) original).longValue();
				else if(original instanceof Character)
					return (T) (Long) (long) ((Character) original).charValue();
				else
					throw new ConversionException(original.getClass() + " cannot be converted to " + requiredType);
			}
			else if(requiredType == double.class || requiredType == Double.class)
			{
				if(original instanceof Double)
					return (T) original;
				else if(original instanceof Number)
					return (T) (Double) ((Number) original).doubleValue();
				else if(original instanceof Character)
					return (T) (Double) (double) ((Character) original).charValue();
				else
					throw new ConversionException(original.getClass() + " cannot be converted to " + requiredType);
			}
			else if(requiredType == float.class || requiredType == Float.class)
			{
				if(original instanceof Float)
					return (T) original;
				else if(original instanceof Number)
					return (T) (Float) ((Number) original).floatValue();
				else if(original instanceof Character)
					return (T) (Float) (float) ((Character) original).charValue();
				else
					throw new ConversionException(original.getClass() + " cannot be converted to " + requiredType);
			}
			else if(requiredType == byte.class || requiredType == Byte.class)
			{
				if(original instanceof Byte)
					return (T) original;
				else if(original instanceof Number)
					return (T) (Byte) ((Number) original).byteValue();
				else if(original instanceof Character)
					return (T) (Byte) (byte) ((Character) original).charValue();
				else
					throw new ConversionException(original.getClass() + " cannot be converted to " + requiredType);
			}
			else if(requiredType == short.class || requiredType == Short.class)
			{
				if(original instanceof Short)
					return (T) original;
				else if(original instanceof Number)
					return (T) (Short) ((Number) original).shortValue();
				else if(original instanceof Character)
					return (T) (Short) (short) ((Character) original).charValue();
				else
					throw new ConversionException(original.getClass() + " cannot be converted to " + requiredType);
			}
			else if(requiredType == char.class || requiredType == Character.class)
			{
				if(original instanceof Character)
					return (T) original;
				else if(original instanceof Number)
					return (T) (Character) (char) ((Number) original).intValue();
				else
					throw new ConversionException(original.getClass() + " cannot be converted to " + requiredType);
			}
			else if(requiredType == boolean.class || requiredType == Boolean.class)
			{
				if(original instanceof Boolean)
					return (T) original;
				else
					throw new ConversionException(original.getClass() + " cannot be converted to " + requiredType);
			}
		}
		else
		{
			// other objects
			if(original == null)
				return (T) original;
			if(requiredType == Object.class)
				return (T) original;
			else if(requiredType.isAssignableFrom(original.getClass()))
				return (T) original;
			else if(mapper != null)
			{
				logger.info("requiredType: " + requiredType);
				logger.info("originalType: " + original.getClass());
				logger.info("original: " + original);
				T tmp = mapper.merge(requiredType, original, authorities);
				if(tmp == null)
				{
					// merging failed
					throw new ConversionException(original.getClass() + " cannot be merged to " + requiredType);
				}
				else
				{
					return tmp;
				}
			}
		}
		throw new ConversionException("no conversion rule found!");
	}

	/**
	 * Find the matching getter for a Field within a Class or it's super classes.<br>
	 * When the Field type is boolean isX(..) will be checked as well.
	 * 
	 * @see ReflectionsUtil#getGetter(Class, String, Class, Type)
	 * @param cls - the Class to scan
	 * @param field - the Field whichs getter to find
	 * @return the getter or null if none could be found
	 */
	/* package */static <T> Method getGetter(Class<T> cls, Field field)
	{
		return getGetter(cls, field.getName(), field.getType(), field.getGenericType());
	}

	/**
	 * Find the matching setter for a Field within a Class or it's super classes.<br>
	 * 
	 * @see ReflectionsUtil#getSetter(Class, String, Class)
	 * @param cls - the Class to scan
	 * @param field - the Field whichs setter to find
	 * @return the setter or null if none could be found
	 */
	/* package */static <T> Method getSetter(Class<T> cls, Field field)
	{
		return getSetter(cls, field.getName(), field.getType());
	}

	/**
	 * Find the matching getter for a Field within a Class or it's super classes.<br>
	 * When the Field type is boolean isX(..) will be checked as well.
	 * 
	 * @param cls - the Class to scan
	 * @param fieldName - the name of the Field whichs getter to find
	 * @param type - the type of the Field
	 * @return the getter or null if none could be found
	 */
	public static <T, V> Method getGetter(Class<T> cls, String fieldName, Class<V> type)
	{
		return getGetter(cls, fieldName, type, type);
	}

	/**
	 * Find the matching getter for a Field within a Class or it's super classes.<br>
	 * When the Field type is boolean isX(..) will be checked as well.
	 * 
	 * @param cls - the Class to scan
	 * @param fieldName - the name of the Field whichs getter to find
	 * @param type - the type of the Field
	 * @param genericType - the generic type of the Field
	 * @return the getter or null if none could be found
	 */
	public static <T, V> Method getGetter(Class<T> cls, String fieldName, Class<V> type, Type genericType)
	{
		Method getter;
		if(type.equals(boolean.class) || type.equals(Boolean.class))
		{
			getter = findMethod(cls, PREFIX_IS + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1));
			if(getter == null)
				getter = findMethod(cls, PREFIX_GET + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1));
		}
		else
		{
			getter = findMethod(cls, PREFIX_GET + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1));
		}
		// if(getter != null && !type.equals(getter.getReturnType()))
		if(getter != null && !getter.getGenericReturnType().equals(genericType))
		{
			// if(getter.getGenericReturnType() instanceof TypeVariable)
			// {
			// if(!getter.getGenericReturnType().equals(field.getGenericType()))
			// getter = null;
			// }
			// else
			getter = null;
		}
		return getter;
	}

	/**
	 * Find the matching setter for a Field within a Class or it's super classes.<br>
	 * 
	 * @param cls - the Class to scan
	 * @param fieldName - the name of the Field whichs setter to find
	 * @param type - the type of the Field
	 * @return the setter or null if none could be found
	 */
	public static <T, V> Method getSetter(Class<T> cls, String fieldName, Class<V> type)
	{
		return findMethod(cls, PREFIX_SET + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1), type);
	}

	/**
	 * Returns true if an annotation for the specified type is present on this element, else false.
	 * 
	 * @see AnnotatedElement#isAnnotationPresent(Class)
	 * @param annotated - the AnnotatedElement
	 * @param annotationClass - the Class object corresponding to the annotation type
	 * @return true or false
	 */
	public static boolean isAnnotationPresent(AnnotatedElement annotated, Class<? extends Annotation> annotationClass)
	{
		return getAnnotation(annotated, annotationClass) != null;
	}

	/**
	 * Returns this element's annotation for the specified type if such an annotation is present,
	 * else null. Also includes inherited Annotations from Super-Classes or Interfaces.
	 * 
	 * @see AnnotatedElement#getAnnotation(Class)
	 * @param annotated - the AnnotatedElement
	 * @param annotationClass - the Class object corresponding to the annotation type
	 * @return this element's annotation for the specified annotation type
	 */
	public static <T extends Annotation> T getAnnotation(AnnotatedElement annotated, Class<T> annotationClass)
	{
		T annotation = null;
		if((annotation = annotated.getAnnotation(annotationClass)) != null)
			return annotation;
		if(annotated instanceof Class && !Object.class.equals(annotated))
		{
			if((annotation = getAnnotation(((Class<?>) annotated).getSuperclass(), annotationClass)) != null)
				return annotation;
			for(Class<?> i : ((Class<?>) annotated).getInterfaces())
			{
				if((annotation = getAnnotation(i, annotationClass)) != null)
					return annotation;
			}
		}
		else if(annotated instanceof Method && !Object.class.equals(((Method) annotated).getDeclaringClass()))
		{
			Class<?> superClass;
			Method superMethod;

			superClass = ((Method) annotated).getDeclaringClass().getSuperclass();
			if(superClass != null)
			{
				try
				{
					superMethod = superClass.getMethod(((Method) annotated).getName(), ((Method) annotated).getParameterTypes());
					if((annotation = getAnnotation(superMethod, annotationClass)) != null)
						return annotation;
				}
				catch(NoSuchMethodException e)
				{
					// ignore
				}
			}

			for(Class<?> i : ((Method) annotated).getDeclaringClass().getInterfaces())
			{
				try
				{
					superMethod = i.getMethod(((Method) annotated).getName(), ((Method) annotated).getParameterTypes());
					if((annotation = getAnnotation(superMethod, annotationClass)) != null)
						return annotation;
				}
				catch(NoSuchMethodException e)
				{
					// ignore
				}
			}
		}
		else
		// Field
		{
			annotation = annotated.getAnnotation(annotationClass);
		}
		return annotation;
	}

	/**
	 * Returns all annotations present on this element. (Returns an array of length zero if this
	 * element has no annotations.) The caller of this method is free to modify the returned array;
	 * it will have no effect on the arrays returned to other callers. Also includes inherited
	 * Annotations from Super-Classes or Interfaces.
	 * 
	 * @see AnnotatedElement#getAnnotations()
	 * @param annotated - the AnnotatedElement
	 * @return this element's annotation for the specified annotation type
	 */
	public static Annotation[] getAnnotations(AnnotatedElement annotated)
	{
		List<Annotation> results = new LinkedList<Annotation>();

		results.addAll(Arrays.asList(annotated.getAnnotations()));
		if(annotated instanceof Class && !Object.class.equals(annotated))
		{
			results.addAll(Arrays.asList(getAnnotations(((Class<?>) annotated).getSuperclass())));

			for(Class<?> i : ((Class<?>) annotated).getInterfaces())
			{
				results.addAll(Arrays.asList(getAnnotations(i)));
			}
		}
		else if(annotated instanceof Method && !Object.class.equals(((Method) annotated).getDeclaringClass()))
		{
			Class<?> superClass;
			Method superMethod;

			superClass = ((Method) annotated).getDeclaringClass().getSuperclass();
			if(superClass != null)
			{
				try
				{
					superMethod = superClass.getMethod(((Method) annotated).getName(), ((Method) annotated).getParameterTypes());
					results.addAll(Arrays.asList(getAnnotations(superMethod)));
				}
				catch(NoSuchMethodException e)
				{
					// ignore
				}
			}

			for(Class<?> i : ((Method) annotated).getDeclaringClass().getInterfaces())
			{
				try
				{
					superMethod = i.getMethod(((Method) annotated).getName(), ((Method) annotated).getParameterTypes());
					results.addAll(Arrays.asList(getAnnotations(superMethod)));
				}
				catch(NoSuchMethodException e)
				{
					// ignore
				}
			}
		}
		else
		// Field
		{
			return annotated.getAnnotations();
		}
		return results.toArray(new Annotation[results.size()]);
	}

	/**
	 * Similar to <code>getActualTypeArguments(o, o.getClass())</code>
	 * 
	 * @see ReflectionsUtil#getActualTypeArguments(Object, Class)
	 * @param o - the Object
	 * @return the actual type arguments
	 */
	public static Type[] getActualTypeArguments(Object o)
	{
		return getActualTypeArguments(o, o.getClass());
	}

	/**
	 * Similar to <code>getActualTypeArguments((Type) o.getClass(), requestedGenericType)</code>
	 * 
	 * @see ReflectionsUtil#getActualTypeArguments(Type, Class)
	 * @param o - the Object
	 * @param requestedGenericType - the generic super type
	 * @return the actual type arguments
	 */
	public static Type[] getActualTypeArguments(Object o, Class<?> requestedGenericType)
	{
		Class<?> objectType = o.getClass();
		return getActualTypeArguments((Type) objectType, requestedGenericType);
	}

	/**
	 * Get the actual type arguments for any Object and the given generic super Type as specified by
	 * {@link ParameterizedType#getActualTypeArguments()}. This Method will then return all
	 * Type-Arguments forwarded to the generic super type.<br>
	 * Therefore a map of all present type arguments of the given Class and all of it's super
	 * Classes will be generated from which the actual type arguments can be fetched.
	 * 
	 * @see ReflectionsUtil#resolveTypeArguments(Type)
	 * @see ParameterizedType#getActualTypeArguments()
	 * @param o - the Object
	 * @param requestedGenericType - the generic super type
	 * @return the actual type arguments
	 */
	public static Type[] getActualTypeArguments(Type t, Class<?> requestedGenericType)
	{
		Class<?> rawType;
		if(t instanceof Class)
			rawType = (Class<?>) t;
		else
			rawType = (Class<?>) ((ParameterizedType) t).getRawType();

		if(!requestedGenericType.isAssignableFrom(rawType))
			throw new IllegalArgumentException("Requested Generic Type is not assignable from " + requestedGenericType.getName());
		if(requestedGenericType.getTypeParameters().length == 0)
			return new Type[0];

		Map<Type, Map<TypeVariable<?>, Type>> typeArgs = resolveTypeArguments(t);

		// in order to make sure the args are in the correct order,
		// we have to process the given type variables
		TypeVariable<?>[] typeVariables = requestedGenericType.getTypeParameters();
		Type[] type = new Type[typeVariables.length];
		for(int i = 0; i < typeVariables.length; i++)
		{
			type[i] = typeArgs.get(requestedGenericType).get(typeVariables[i]);
		}

		return type;
	}

	/**
	 * Similar to <code>resolveTypeArguments(o.getClass())</code>
	 * 
	 * @see ReflectionsUtil#resolveTypeArguments(Type)
	 * @param o - the Object which's type arguments to resolve
	 * @return a map with all retrieved type arguments
	 */
	public static Map<Type, Map<TypeVariable<?>, Type>> resolveTypeArguments(Object o)
	{
		return resolveTypeArguments((Type) o.getClass());
	}

	/**
	 * Resolve all type arguments for the given type and all of it's super classes and super
	 * interfaces.<br>
	 * Therefore a Map will be created which contains (super) classes and (super) interfaces as keys
	 * and Maps of TypeVariables as entries. Recursively the TypeVariables are retrieved first and
	 * then associated with the generic type that is used in the specific implementation. This way
	 * the inheritance tree is broken down to a single layer step by step and it is possible to
	 * retrieve the required type arguments from the map easily.
	 * 
	 * @param o - the Object which's type arguments to resolve
	 * @return a map with all retrieved type arguments
	 */
	public static Map<Type, Map<TypeVariable<?>, Type>> resolveTypeArguments(Type t)
	{
		Map<Type, Map<TypeVariable<?>, Type>> typeArgs = new HashMap<Type, Map<TypeVariable<?>, Type>>();
		resolveTypeArguments(t, typeArgs);
		return typeArgs;
	}

	/**
	 * Internal implementation for {@link ReflectionsUtil#resolveTypeArguments(Type)}<br>
	 * The given map will be filled for the current class and the recursion is applied for super
	 * classes and interfaces. Afterwards found TypeVariables are replaced if they have been
	 * forwarded to subclasses/subinterfaces as well.
	 * 
	 * @see ReflectionsUtil#resolveTypeArguments(Type)
	 * @param cls - the class to determine the type arguments for
	 * @param typeArgs - the map to fill with the type args
	 */
	private static void resolveTypeArguments(Class<?> cls, Map<Type, Map<TypeVariable<?>, Type>> typeArgs)
	{
		if(cls.getGenericSuperclass() != null)
			resolveTypeArguments(cls.getGenericSuperclass(), typeArgs);

		for(Type t : cls.getGenericInterfaces())
		{
			resolveTypeArguments(t, typeArgs);
		}

		// replacement of known variables
		boolean replaced = true;
		while(replaced)
		{
			replaced = false;
			for(Entry<TypeVariable<?>, Type> replacable : typeArgs.get(cls).entrySet())
			{
				for(Map<TypeVariable<?>, Type> map : typeArgs.values())
				{
					for(Entry<TypeVariable<?>, Type> replacer : map.entrySet())
					{
						if(replacable.getValue() instanceof TypeVariable)
						{
							if(replacable.getValue().equals(replacer.getKey()))
							{
								replacable.setValue(replacer.getValue());
								replaced = true;
							}
						}
					}
				}
			}
		}
	}

/**
	 * Internal implementation for {@link ReflectionsUtil#resolveTypeArguments(Type)}<br>
	 * The given map will be filled for the current type depending on wether it is a Class or a
	 * ParameterizedType. Afterwards recursively
	 * {@link ReflectionsUtil#resolveTypeArguments(Class, Map) will be called with the Class itself
	 * or with the raw type (if ParameterizedType).
	 * 
	 * @see ReflectionsUtil#resolveTypeArguments(Type)
	 * @see ReflectionsUtil#resolveTypeArguments(Class, Map)
	 * @param t - the type to determine the type arguments for
	 * @param typeArgs - the map to fill with the type args
	 */
	private static void resolveTypeArguments(Type t, Map<Type, Map<TypeVariable<?>, Type>> typeArgs)
	{
		if(t instanceof Class)
		{
			Class<?> cls = (Class<?>) t;

			Map<TypeVariable<?>, Type> m = new HashMap<TypeVariable<?>, Type>();
			TypeVariable<?>[] typeParameters = cls.getTypeParameters();

			for(int i = 0; i < typeParameters.length; i++)
			{
				m.put(typeParameters[i], Object.class); // cannot be resolved
			}

			typeArgs.put(cls, m);
			resolveTypeArguments(cls, typeArgs);
		}
		else
		// if(t instanceof ParameterizedType)
		{
			ParameterizedType pt = (ParameterizedType) t;
			Class<?> cls = (Class<?>) pt.getRawType();

			Map<TypeVariable<?>, Type> m = new HashMap<TypeVariable<?>, Type>();

			TypeVariable<?>[] typeParameters = cls.getTypeParameters();
			Type[] typeArguments = pt.getActualTypeArguments();

			for(int i = 0; i < typeParameters.length; i++)
			{
				m.put(typeParameters[i], typeArguments[i]);
			}

			typeArgs.put(cls, m);
			resolveTypeArguments(cls, typeArgs);
		}
	}

	/**
	 * Get the actual parameter type for the given method if the method has generic parameters.
	 * 
	 * @param m - the method to observe
	 * @param targetClass - the true target class the method should be used for
	 * @param index - the index of the parameter
	 * @return the acutal (non-generic) parameter type
	 */
	public static Class<?> getActualParameterType(Method m, Class<?> targetClass, int index)
	{
		return getActualParameterType(m.getDeclaringClass(), m, resolveTypeArguments(targetClass), index);
	}

	/**
	 * Get the actual parameter type for the given method if the method has generic parameters.<br>
	 * The actual parameter type therefore is being looked up in the given type argument map for the
	 * target class.
	 * 
	 * @see ReflectionsUtil#resolveTypeArguments(Type) to obtain the type argument map
	 * 
	 * @param m - the method to observe
	 * @param targetClassTypeArguments - the type argument map for the target class
	 * @param index - the index of the parameter
	 * @return the acutal (non-generic) parameter type
	 */
	public static Class<?> getActualParameterType(Class<?> lookupClass, Method m, Map<Type, Map<TypeVariable<?>, Type>> targetClassTypeArguments, int index)
	{
		Type realType = targetClassTypeArguments.get(m.getDeclaringClass()).get(m.getGenericParameterTypes()[index]);
		if(realType instanceof Class)
			return (Class<?>) realType;
		return m.getParameterTypes()[index];
	}

	/**
	 * Get the underlying class for a type or null if the type is a variable type
	 * 
	 * @param type - the type
	 * @return the class
	 */
	public static Class<?> getClassForType(Type type)
	{
		if(type instanceof Class)
			return (Class<?>) type;
		if(type instanceof ParameterizedType)
			return getClassForType(((ParameterizedType) type).getRawType());
		if(type instanceof GenericArrayType)
		{
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			Class<?> componentClass = getClassForType(componentType);
			if(componentClass != null)
				return Array.newInstance(componentClass, 0).getClass();
		}
		if(type instanceof TypeVariable)
		{
			return Object.class; // TODO is there a more specific type?
		}
		return null;
	}

//  @formatter:off
	
	/**
	 * Dummy Class used for the initilization of the suitability matrix.
	 * @author ultimate
	 */
	private static final class POJO {};
	/**
	 * All Classes for Arguments to check for suitable Parameters
	 */
	private static final Class<?>[]	suitClasses	= new Class<?>[] {
			int.class,
			Integer.class,
			long.class, 
			Long.class, 
			short.class, 
			Short.class,
			byte.class, 
			Byte.class,
			double.class, 
			Double.class, 
			float.class, 
			Float.class, 
			boolean.class, 
			Boolean.class,
			char.class, 
			Character.class,
			String.class,
			Object.class,
			POJO.class
	};

	/**
	 * Example Parameters to test for the suitClasses
	 */
	private static final Object[]	suitValues	= new Object[] {
			null,
			5, 
			5L, 
			(short) 5, 
			(byte) 5, 
			5.5, 
			5.5F, 
			true,
			"5",
			(char) 5,
			new Object(),
			new POJO()
	};
	
	/**
	 * Classes of the example Parameters
	 */
	private static Class<?>[] suitValueClasses;
	
	/**
	 * The suitability matrix used in {@link ReflectionsUtil#isMethodSuitableFor(Method, Class...)} and {@link ReflectionsUtil#isMethodSuitableFor(Method, Object...)}
	 * 
	 * @see ReflectionsUtil#isMethodSuitableFor(Method, Class...)
	 * @see ReflectionsUtil#isMethodSuitableFor(Method, Object...)
	 */
	private static boolean[][] suitabilityMatrix;

	// @formatter:on
	/**
	 * Initialize the suitability matrix if neccessary.<br>
	 * Therefore several Methods with different arguments are invoked with different parameters and
	 * a matrix containing information about success or failure will be generated.
	 */
	private static synchronized void initSuitabilityMatrix()
	{
		if(suitabilityMatrix == null)
		{
			@SuppressWarnings("unused")
			Object tmp = new Object() {
				// @formatter:off
				public void doX(int a)		{}
				public void doX(Integer a)	{}
				public void doX(long a)		{}
				public void doX(Long a)		{}
				public void doX(short a)	{}
				public void doX(Short a)	{}
				public void doX(byte a)		{}
				public void doX(Byte a)		{}
				public void doX(double a)	{}
				public void doX(Double a)	{}
				public void doX(float a)	{}
				public void doX(Float a)	{}
				public void doX(boolean a)	{}
				public void doX(Boolean a)	{}
				public void doX(char a)		{}
				public void doX(Character a){}
				public void doX(String a)	{}
				public void doX(Object a) 	{}
				public void doX(POJO a) 	{}
				// @formatter:on
			};

			logger.debug("initializing suitabilityMatrix");

			suitabilityMatrix = new boolean[suitValues.length][suitClasses.length];
			suitValueClasses = new Class<?>[suitValues.length];

			Method m;
			for(int v = 0; v < suitValues.length; v++)
			{
				suitValueClasses[v] = suitValues[v] != null ? suitValues[v].getClass() : null;
				for(int c = 0; c < suitClasses.length; c++)
				{
					try
					{
						m = tmp.getClass().getMethod("doX", suitClasses[c]);
						try
						{
							m.invoke(tmp, suitValues[v]);
							suitabilityMatrix[v][c] = true;
						}
						catch(Exception e)
						{
							suitabilityMatrix[v][c] = false;
						}
					}
					catch(Exception e)
					{
						logger.error("Error initializing suitabilityMatrix", e);
					}
				}
			}

			if(logger.isDebugEnabled())
			{
				StringBuilder sb = new StringBuilder();
				sb.append("suitabilityMatrix:\n");

				sb.append("\t");
				String cName;
				for(int c = 0; c < suitClasses.length; c++)
				{
					sb.append("\t");
					cName = suitClasses[c].getSimpleName();
					if(cName.length() > 7)
						cName = cName.substring(0, 5) + "..";
					sb.append(cName);
				}
				sb.append("\n");

				for(int v = 0; v < suitValues.length; v++)
				{
					if(suitValues[v] != null)
					{
						sb.append(suitValues[v].getClass().getSimpleName());
						// if(suitValues[v].getClass().getSimpleName().length() < 5)
						// sb.append("\t");
					}
					else
						sb.append("null");

					for(int c = 0; c < suitClasses.length; c++)
					{
						sb.append("\t" + (suitabilityMatrix[v][c] ? "+" : "-"));
					}
					sb.append("\n");
				}
				logger.debug(sb.toString());
			}
		}
	}
}

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
package com.syncnapsis.utils.constants;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.reflections.Field;
import com.syncnapsis.utils.reflections.FieldCriterion;
import com.syncnapsis.utils.spring.Bean;

/**
 * Class loading constants defined in specified classes.
 * 
 * @author ultimate
 * 
 * @param <R> - the raw type for the constants
 */
public abstract class ConstantLoader<R> extends Bean implements InitializingBean
{
	/**
	 * Logger-instance
	 */
	protected static transient final Logger	logger	= LoggerFactory.getLogger(ConstantLoader.class);

	/**
	 * The raw type for the constants held by this loader
	 */
	protected Class<R>						constantRawType;

	/**
	 * The list of classes to scan for constants
	 */
	protected List<Class<?>>				constantClasses;

	/**
	 * The list of constants contained in the specified constant classes
	 */
	protected List<Constant<R>>				constants;

	/**
	 * Construct a new ConstantLoader with the given raw type.<br>
	 * Note: remember to load constants using load(..) after setting the constant classes.
	 * 
	 * @param constantRawType - the constant raw type
	 */
	public ConstantLoader(Class<R> constantRawType)
	{
		this.constantRawType = constantRawType;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet()
	{
		load();
	}

	/**
	 * The list of classes to scan for constants
	 * 
	 * @param constantClasses - the list of classes
	 */
	public void setConstantClasses(List<Class<?>> constantClasses)
	{
		this.constantClasses = constantClasses;
	}

	/**
	 * The list of classes to scan for constants
	 * 
	 * @param constantClassesStrings - the list of classes as a String array
	 * @throws ClassNotFoundException - if a class could not be loaded from the given strings
	 */
	public void setConstantClasses(String... constantClassesStrings) throws ClassNotFoundException
	{
		this.constantClasses = new ArrayList<Class<?>>(constantClassesStrings.length);
		for(String cls : constantClassesStrings)
		{
			this.constantClasses.add(Class.forName(cls));
		}
	}

	/**
	 * The list of classes to scan for constants
	 * 
	 * @return constantClasses
	 */
	public List<Class<?>> getConstantClasses()
	{
		return constantClasses;
	}

	/**
	 * The raw type for the constants held by this loader
	 * 
	 * @return constantRawType
	 */
	public Class<R> getConstantRawType()
	{
		return constantRawType;
	}

	/**
	 * The list of constants contained in the specified constant classes
	 * 
	 * @return contants
	 */
	public List<Constant<R>> getConstants()
	{
		return constants;
	}

	/**
	 * Load all constants contained in the specified constant classes.
	 */
	public void load()
	{
		if(this.constants == null)
			this.constants = scanClasses(this.constantClasses, this.constantRawType);
		for(Constant<R> c : constants)
			load(c);
	}

	/**
	 * Load a specific constant and define its value.
	 * 
	 * @param constant - the constant to load/define
	 */
	public abstract void load(Constant<R> constant);

	/**
	 * Scan the list of given classes for constants of the given type.
	 * 
	 * @param constantClasses - the list of classes to scan for constants
	 * @param rawType - the raw type of constants to look for
	 * @return the list of constants found
	 */
	@SuppressWarnings("unchecked")
	public static <R> List<Constant<R>> scanClasses(List<Class<?>> constantClasses, Class<R> rawType)
	{
		List<Constant<R>> constants = new LinkedList<Constant<R>>();

		List<Field> fields;
		Constant<R> constant;
		for(Class<?> cls : constantClasses)
		{
			fields = ReflectionsUtil.findFields(cls, FieldCriterion.STATIC, FieldCriterion.FINAL);

			for(Field f : fields)
			{
				if(!Constant.class.isAssignableFrom(f.getField().getType()))
					continue;
				Class<?> genericType;
				if(f.getField().getGenericType() instanceof Class)
					genericType = (Class<?>) ReflectionsUtil.getActualTypeArguments(f.getField().getType(), Constant.class)[0];
				else if(f.getField().getGenericType() instanceof ParameterizedType)
					genericType = (Class<?>) ((ParameterizedType) f.getField().getGenericType()).getActualTypeArguments()[0];
				else
					continue;
				if(!rawType.isAssignableFrom(genericType))
					continue;
				try
				{

					constant = (Constant<R>) ReflectionsUtil.getField(null, f.getField());
					if(!constants.contains(constant))
					{
						constants.add(constant);
						logger.debug("constant found: " + cls.getSimpleName() + "#" + f.getName() + " : " + constant.getName());
					}
				}
				catch(IllegalArgumentException e)
				{
					logger.error("IllegalArgumentException?!", e);
				}
				catch(IllegalAccessException e)
				{
					logger.error("could not access constant:" + cls.getName() + "#" + f.getName());
				}
			}
		}

		return new ArrayList<Constant<R>>(constants);
	}
}

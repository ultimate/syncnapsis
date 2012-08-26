package com.syncnapsis.utils.reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Wrapping representation of Fields or Properties of Java Classes (especially POJOs).<br>
 * A Field can be represented by it's java-Field, java-Getter-Method or java-Setter-Method or
 * combinations of those depending on wether the field is read-only, write-only or both.<br>
 * Via {@link Field#set(Object, Object)} and {@link Field#get(Object)} the value of the Field can be
 * accessed and changed using either the underlying Field or the Getter/Setter depending on their
 * accessibility.
 * 
 * @author ultimate
 */
public class Field
{
	/**
	 * Logger-Instance
	 */
	private final Logger			logger	= LoggerFactory.getLogger(getClass());

	/**
	 * The name of the Field
	 */
	private String					name;
	/**
	 * The underlying java-Field
	 */
	private java.lang.reflect.Field	field;
	/**
	 * The underlying java-Getter-Method
	 */
	private Method					getter;
	/**
	 * The underlying java-Setter-Method
	 */
	private Method					setter;

	/**
	 * @param name - The name of the Field
	 * @param field - The underlying java-Field
	 * @param getter - The underlying java-Getter-Method
	 * @param setter - The underlying java-Setter-Method
	 */
	public Field(String name, java.lang.reflect.Field field, Method getter, Method setter)
	{
		super();
		this.name = name;
		this.field = field;
		this.getter = getter;
		this.setter = setter;

		Assert.isTrue(field != null || getter != null || setter != null, "either field or getter or setter must not be null!");

		// valid getter?
		if(getter != null)
		{
			Assert.isTrue(!void.class.equals(getter.getReturnType()), "illegal getter: Method is void");
			Assert.isTrue(getter.getParameterTypes().length == 0, "illegal getter: Method requires arguments");
		}
		// valid setter?
		if(setter != null)
		{
			Assert.isTrue(setter.getParameterTypes().length == 1, "illegal setter: Method has wrong number of arguments, single argument required");
		}		
		
		// cross checking of types...
		// field vs. getter
		if(field != null && getter != null)
			Assert.isTrue(field.getType().isAssignableFrom(getter.getReturnType()), "incompatible types: field = " + field.getType()
					+ " vs. getter = " + getter.getReturnType());
		// field vs. setter
		if(field != null && setter != null)
			Assert.isTrue(field.getType().isAssignableFrom(setter.getParameterTypes()[0]), "incompatible types: field = " + field.getType()
					+ " vs. setter = " + setter.getParameterTypes()[0]);
		// getter vs. setter
		if(getter != null && setter != null)
			Assert.isTrue(getter.getReturnType().isAssignableFrom(setter.getParameterTypes()[0]),
					"incompatible types: getter = " + getter.getReturnType() + " vs. setter = " + setter.getParameterTypes()[0]);
	}

	/**
	 * The name of the Field
	 * 
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * The underlying java-Field
	 * 
	 * @return the java-Field, if available
	 */
	public java.lang.reflect.Field getField()
	{
		return field;
	}

	/**
	 * The underlying java-Getter-Method, if available
	 * 
	 * @return the java-Getter-Method
	 */
	public Method getGetter()
	{
		return getter;
	}

	/**
	 * The underlying java-Setter-Method, if available
	 * 
	 * @return the java-Setter-Method
	 */
	public Method getSetter()
	{
		return setter;
	}

	/**
	 * Get the value of the Field or Property represented by this Object.<br>
	 * <br>
	 * Getting the value is only possible if at least one of the following conditions apply:<br>
	 * <ul>
	 * <li>field != null && field is public</li>
	 * <li>getter != null && getter is public</li>
	 * </ul>
	 * 
	 * @param obj - the Object which's Field to get
	 * @return the value of the Field
	 * @throws IllegalAccessException - if the Field/Property is not accessible
	 */
	public Object get(Object obj) throws IllegalAccessException
	{
		if(field != null && Modifier.isPublic(field.getModifiers()))
		{
			return field.get(obj);
		}
		if(getter != null && Modifier.isPublic(getter.getModifiers()))
		{
			try
			{
				return getter.invoke(obj);
			}
			catch(IllegalArgumentException e)
			{
				logger.error("this should not occur", e);
			}
			catch(InvocationTargetException e)
			{
				logger.error("this should not occur", e);
			}
		}
		throw new IllegalAccessException("field and getter not accessible");
	}

	/**
	 * Set the value of the Field or Property represented by this Object.<br>
	 * <br>
	 * Setting the value is only possible if at least one of the following conditions apply:<br>
	 * <ul>
	 * <li>field != null && field is public</li>
	 * <li>setter != null && setter is public</li>
	 * </ul>
	 * 
	 * @param obj - the Object which's Field to get
	 * @param value - the new value to set for the Field
	 * @return the previous value of the Field for convenience
	 * @throws IllegalAccessException - if the Field/Property is not accessible
	 */
	public Object set(Object obj, Object value) throws IllegalAccessException
	{
		Object oldValue = get(obj);
		if(field != null && Modifier.isPublic(field.getModifiers()))
		{
			field.set(obj, value);
			return oldValue;
		}
		if(setter != null && Modifier.isPublic(setter.getModifiers()))
		{
			try
			{
				setter.invoke(obj, value);
				return oldValue;
			}
			catch(IllegalArgumentException e)
			{
				logger.error("this should not occur", e);
			}
			catch(InvocationTargetException e)
			{
				logger.error("this should not occur", e);
			}
		}
		throw new IllegalAccessException("field and setter not accessible");
	}

	@Override
	public String toString()
	{
		return "Field [name=" + name + ", type=" + (field != null ? field.getType() : (getter != null ? getter.getReturnType() : (setter != null ? setter.getParameterTypes()[0] : null)))+ "]";
	}
}

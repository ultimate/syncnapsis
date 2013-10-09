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
package com.syncnapsis.security.accesscontrol;

import java.lang.reflect.Modifier;

import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.reflections.Field;

public class FieldAccessController extends AnnotationAccessController<Field>
{
	/**
	 * Are properties readable by default if no {@link Accessible}-Annotation is found?
	 */
	protected boolean		defaultReadable	= true;

	/**
	 * Are properties writable by default if no {@link Accessible}-Annotation is found?
	 */
	protected boolean		defaultWritable	= true;

	/**
	 * Are properties readable by default if no {@link Accessible}-Annotation is found?
	 * 
	 * @return true or false
	 */
	public boolean isDefaultReadable()
	{
		return defaultReadable;
	}

	/**
	 * Are properties readable by default if no {@link Accessible}-Annotation is found?
	 * 
	 * @param defaultReadable - true or false
	 */
	public void setDefaultReadable(boolean defaultReadable)
	{
		this.defaultReadable = defaultReadable;
	}

	/**
	 * Are properties readable by default if no {@link Accessible}-Annotation is found?
	 * 
	 * @return true or false
	 */
	public boolean isDefaultWritable()
	{
		return defaultWritable;
	}

	/**
	 * Are properties readable by default if no {@link Accessible}-Annotation is found?
	 * 
	 * @param defaultWritable - true or false
	 */
	public void setDefaultWritable(boolean defaultWritable)
	{
		this.defaultWritable = defaultWritable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.AccessController#getTargetClass()
	 */
	@Override
	public Class<Field> getTargetClass()
	{
		return Field.class;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.AccessController#isAccessible(java.lang.Object, int, java.lang.Object[])
	 */
	@Override
	public boolean isAccessible(Field target, int operation, Object... authorities)
	{
		boolean a = false;
		if(operation == READ)
		{
			a = isReadable(target, authorities);
			logger.debug("read@" + target + ": " + a);
		}
		else if(operation == WRITE)
		{
			if(Modifier.isFinal(target.getField().getModifiers()))
				a = false;
			else
				a = isWritable(target, authorities);
			logger.debug("write@" + target + ": " + a);
		}
		return a;
	}

	/**
	 * Is a Field readable by the given authorities?
	 * 
	 * @param field - the Field
	 * @param authorities - the authorities to check for accessibility
	 * @return true or false
	 */
	public boolean isReadable(Field field, Object... authorities)
	{
		return isAccessible(getReadableAnnotation(field), defaultReadable, authorities);
	}

	/**
	 * Is a Field writable by the given authorities?
	 * 
	 * @param fiedld - the Field
	 * @param authorities - the authorities to check for accessibility
	 * @return true or false
	 */
	public boolean isWritable(Field field, Object... authorities)
	{
		return isAccessible(getWritableAnnotation(field), defaultWritable, authorities);
	}

	/**
	 * Get the Accessible Annotation for Read-Access. This means the Accessible Annotation at the
	 * Getter and the Field is considerd (Getter is preferred.)
	 * 
	 * @param field - the field to scan for the annotation
	 * @return the Accessible Annotation
	 */
	protected Accessible getReadableAnnotation(Field field)
	{
		Accessible a = null;
		if(field.getGetter() != null && ((a = ReflectionsUtil.getAnnotation(field.getGetter(), Accessible.class)) != null))
			return a;
		if(field.getField() != null && ((a = ReflectionsUtil.getAnnotation(field.getField(), Accessible.class)) != null))
			return a;
		return null;
	}

	/**
	 * Get the Accessible Annotation for Write-Access. This means the Accessible Annotation at the
	 * Setter and the Field is considerd (Setter is preferred.)
	 * 
	 * @param field - the field to scan for the annotation
	 * @return the Accessible Annotation
	 */
	protected Accessible getWritableAnnotation(Field field)
	{
		Accessible a = null;
		if(field.getSetter() != null && ((a = ReflectionsUtil.getAnnotation(field.getSetter(), Accessible.class)) != null))
			return a;
		if(field.getField() != null && ((a = ReflectionsUtil.getAnnotation(field.getField(), Accessible.class)) != null))
			return a;
		return null;
	}
}

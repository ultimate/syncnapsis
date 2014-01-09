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

import java.lang.reflect.Method;

import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.utils.ReflectionsUtil;

/**
 * AccessController for {@link Method}s.
 * This access controller uses {@link Accessible}-annotation to determine invocation permissions
 * 
 * @author ultimate
 */
public class MethodAccessController extends AnnotationAccessController<Method>
{
	/**
	 * The default value for valid accessor for reading fields
	 */
	private int	defaultAccessible	= Accessible.ANYBODY;

	/**
	 * The default value for valid accessor for reading fields
	 * 
	 * @return defaultAccessible
	 */
	public int getDefaultAccessible()
	{
		return defaultAccessible;
	}

	/**
	 * The default value for valid accessor for reading fields
	 * 
	 * @param defaultAccessible - the default value
	 */
	public void setDefaultAccessible(int defaultAccessible)
	{
		this.defaultAccessible = defaultAccessible;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.AccessController#getTargetClass()
	 */
	@Override
	public Class<Method> getTargetClass()
	{
		return Method.class;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.AccessController#isAccessible(java.lang.Object,
	 * java.lang.Object, int, java.lang.Object[])
	 */
	@Override
	public boolean isAccessible(Object entity, Method target, int operation, Object... authorities)
	{
		boolean a = false;
		if(operation == INVOKE)
		{
			a = isAccessible(entity, getAccessibleAnnotation(target), defaultAccessible, authorities);
			logger.debug("invoke@" + target + ": " + a);
		}
		return a;
	}

	/**
	 * Get the Accessible Annotation for a Method.
	 * 
	 * @param method - the method to scan for the annotation
	 * @return the Accessible Annotation
	 */
	protected Accessible getAccessibleAnnotation(Method method)
	{
		return ReflectionsUtil.getAnnotation(method, Accessible.class);
	}
}

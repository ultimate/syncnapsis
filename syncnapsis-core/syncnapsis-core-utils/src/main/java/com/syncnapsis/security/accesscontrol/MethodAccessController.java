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

import com.syncnapsis.security.AccessRule;
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
	 * The default value for <code>@Accessible.by()</code> for method invokation
	 */
	private int	defaultAccessibleBy	= AccessRule.ANYBODY;
	/**
	 * The default value for <code>@Accessible.of()</code> for method invokation
	 */
	private int	defaultAccessibleOf	= AccessRule.ANYROLE;

	/**
	 * Construct a new MethodAccessController with the given rule
	 * 
	 * @param rule - the AccessRule to use
	 */
	public MethodAccessController(AccessRule rule)
	{
		super(Method.class, rule);
	}

	/**
	 * The default value for <code>@Accessible.by()</code> for method invokation
	 * 
	 * @return defaultAccessibleBy
	 */
	public int getDefaultAccessibleBy()
	{
		return defaultAccessibleBy;
	}

	/**
	 * The default value for <code>@Accessible.by()</code> for method invokation
	 * 
	 * @param defaultAccessibleBy - the default value
	 */
	public void setDefaultAccessibleBy(int defaultAccessibleBy)
	{
		this.defaultAccessibleBy = defaultAccessibleBy;
	}

	/**
	 * The default value for <code>@Accessible.of()</code> for method invokation
	 * 
	 * @return defaultAccessibleOf
	 */
	public int getDefaultAccessibleOf()
	{
		return defaultAccessibleOf;
	}

	/**
	 * The default value for <code>@Accessible.of()</code> for method invokation
	 * 
	 * @param defaultAccessibleOf - the default value
	 */
	public void setDefaultAccessibleOf(int defaultAccessibleOf)
	{
		this.defaultAccessibleOf = defaultAccessibleOf;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.security.accesscontrol.AnnotationAccessController#getAnnotation(java.lang.
	 * Object, int)
	 */
	@Override
	public Accessible getAnnotation(Method method, int operation)
	{
		if(operation == INVOKE)
			return ReflectionsUtil.getAnnotation(method, Accessible.class);
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.security.accesscontrol.AnnotationAccessController#getDefaultAccessibleBy(int)
	 */
	@Override
	public int getDefaultAccessibleBy(int operation)
	{
		if(operation == INVOKE)
			return defaultAccessibleBy;
		else
			return AccessRule.NOBODY;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.security.accesscontrol.AnnotationAccessController#getDefaultAccessibleOf(int)
	 */
	@Override
	public int getDefaultAccessibleOf(int operation)
	{
		if(operation == INVOKE)
			return defaultAccessibleOf;
		else
			return AccessRule.NOROLE;
	}
}

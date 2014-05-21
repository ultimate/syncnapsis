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

import com.syncnapsis.security.AccessRule;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.reflections.Field;

/**
 * AccessController for {@link Field}s.
 * This access controller uses {@link Accessible}-annotation to determine read and write permissions
 * 
 * @author ultimate
 */
public class FieldAccessController extends AnnotationAccessController<Field>
{
	/**
	 * The default value for <code>@Accessible.by()</code> for read operations
	 */
	private int	defaultReadableBy	= AccessRule.ANYBODY;
	/**
	 * The default value for <code>@Accessible.by()</code> for write operations
	 */
	private int	defaultWritableBy	= AccessRule.ANYBODY;
	/**
	 * The default value for <code>@Accessible.of()</code> for read operations
	 */
	private int	defaultReadableOf	= AccessRule.ANYBODY;
	/**
	 * The default value for <code>@Accessible.of()</code> for write operations
	 */
	private int	defaultWritableOf	= AccessRule.ANYBODY;

	/**
	 * Construct a new FieldAccessController with the given rule
	 * 
	 * @param rule - the AccessRule to use
	 */
	public FieldAccessController(AccessRule rule)
	{
		super(Field.class, rule);
	}

	/**
	 * The default value for <code>@Accessible.by()</code> for read operations
	 * 
	 * @return defaultReadableBy
	 */
	public int getDefaultReadableBy()
	{
		return defaultReadableBy;
	}

	/**
	 * The default value for <code>@Accessible.by()</code> for read operations
	 * 
	 * @param defaultReadableBy - the default value
	 */
	public void setDefaultReadableBy(int defaultReadableBy)
	{
		this.defaultReadableBy = defaultReadableBy;
	}

	/**
	 * The default value for <code>@Accessible.by()</code> for write operations
	 * 
	 * @return defaultWritableBy
	 */
	public int getDefaultWritableBy()
	{
		return defaultWritableBy;
	}

	/**
	 * The default value for <code>@Accessible.by()</code> for write operations
	 * 
	 * @param defaultWritableBy - the default value
	 */
	public void setDefaultWritableBy(int defaultWritableBy)
	{
		this.defaultWritableBy = defaultWritableBy;
	}

	/**
	 * The default value for <code>@Accessible.of()</code> for read operations
	 * 
	 * @return defaultReadableOf
	 */
	public int getDefaultReadableOf()
	{
		return defaultReadableOf;
	}

	/**
	 * The default value for <code>@Accessible.of()</code> for read operations
	 * 
	 * @param defaultReadableOf - the default value
	 */
	public void setDefaultReadableOf(int defaultReadableOf)
	{
		this.defaultReadableOf = defaultReadableOf;
	}

	/**
	 * The default value for <code>@Accessible.of()</code> for write operations
	 * 
	 * @return defaultWritableOf
	 */
	public int getDefaultWritableOf()
	{
		return defaultWritableOf;
	}

	/**
	 * The default value for <code>@Accessible.of()</code> for write operations
	 * 
	 * @param defaultWritableOf - the default value
	 */
	public void setDefaultWritableOf(int defaultWritableOf)
	{
		this.defaultWritableOf = defaultWritableOf;
	}

	@Override
	public boolean isAccessible(Field target, int operation, Object context, Object... authorities)
	{
		if(operation == WRITE && Modifier.isFinal(target.getField().getModifiers()))
			return false;
		boolean a = super.isAccessible(target, operation, context, authorities);
		logger.debug((operation == READ ? "read" : "write") + "@" + target + ": " + a);
		return a;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.security.accesscontrol.AnnotationAccessController#getAnnotation(java.lang.
	 * Object, int)
	 */
	@Override
	public Accessible getAnnotation(Field target, int operation)
	{
		Accessible a = null;
		if(operation == READ && target.getGetter() != null)
			a = ReflectionsUtil.getAnnotation(target.getGetter(), Accessible.class);
		if(operation == WRITE && target.getSetter() != null)
			a = ReflectionsUtil.getAnnotation(target.getSetter(), Accessible.class);
		if(a == null && target.getField() != null)
			a = ReflectionsUtil.getAnnotation(target.getField(), Accessible.class);
		return a;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.security.accesscontrol.AnnotationAccessController#getDefaultAccessibleBy(int)
	 */
	@Override
	public int getDefaultAccessibleBy(int operation)
	{
		if(operation == READ)
			return defaultReadableBy;
		else if(operation == WRITE)
			return defaultWritableBy;
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
		if(operation == READ)
			return defaultReadableOf;
		else if(operation == WRITE)
			return defaultWritableOf;
		else
			return AccessRule.NOROLE;
	}
}

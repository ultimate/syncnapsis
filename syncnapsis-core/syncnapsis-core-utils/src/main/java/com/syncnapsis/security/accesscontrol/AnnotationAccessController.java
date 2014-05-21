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

import com.syncnapsis.security.AccessController;
import com.syncnapsis.security.AccessRule;
import com.syncnapsis.security.annotations.Accessible;

/**
 * Abstract base for AccessControllers using the {@link Accessible} annotation.
 * 
 * @author ultimate
 * @param <T> - the Target-Type to access
 */
public abstract class AnnotationAccessController<T> extends AccessController<T>
{
	/**
	 * Create a new AccessController with the given rule
	 * 
	 * @param targetClass - the target Class this AccessController is associated with
	 * @param rule - the AccessRule to use
	 */
	public AnnotationAccessController(Class<T> targetClass, AccessRule rule)
	{
		super(targetClass, rule);
	}

	/**
	 * Check an accessible annotation for accessibility
	 * 
	 * @param entity - the entity to check to accessibility for
	 * @param a - the accessible annotation to check
	 * @param defaultAccessibleBy - the default value for <code>@Accessible.by()</code>
	 * @param defaultAccessibleOf - the default value for <code>@Accessible.of()</code>
	 * @param authorities - the authorities to check for
	 * @return true or false
	 */
	public boolean isAccessible(Object entity, Accessible a, int defaultAccessibleBy, int defaultAccessibleOf, Object... authorities)
	{
		int accessibleBy = (a != null ? a.by() : defaultAccessibleBy);
		int accessibleOf = (a != null ? a.of() : defaultAccessibleOf);

		if(accessibleBy == AccessRule.NOBODY || accessibleOf == AccessRule.NOROLE)
			return false;
		return rule.is(accessibleBy, entity, authorities) && rule.isOf(accessibleOf, authorities);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.AccessController#isAccessible(java.lang.Object, int,
	 * java.lang.Object, java.lang.Object[])
	 */
	@Override
	public boolean isAccessible(T target, int operation, Object context, Object... authorities)
	{
		Accessible a = getAnnotation(target, operation);
		logger.debug("a=" + a);
		int defaultAccessibleBy = getDefaultAccessibleBy(operation);
		int defaultAccessibleOf = getDefaultAccessibleOf(operation);
		logger.debug("defaultBy=" + Integer.toHexString(defaultAccessibleBy));
		logger.debug("defaultOf=" + Integer.toHexString(defaultAccessibleOf));
		return isAccessible(context, a, defaultAccessibleBy, defaultAccessibleOf, authorities);
	}

	/**
	 * Get the <code>@Accessible</code> annotation for the target
	 * 
	 * @param target - the target to be accessed
	 * @param operation - the operation to perform
	 * @return the annotation
	 */
	public abstract Accessible getAnnotation(T target, int operation);

	/**
	 * Get the default value for <code>@Accessible.by()</code> for the given operation
	 * 
	 * @param operation - the operation to perform
	 * @return the default value
	 */
	public abstract int getDefaultAccessibleBy(int operation);

	/**
	 * Get the default value for <code>@Accessible.of()</code> for the given operation
	 * 
	 * @param operation - the operation to perform
	 * @return the default value
	 */
	public abstract int getDefaultAccessibleOf(int operation);
}

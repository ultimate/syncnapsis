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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.security.AccessController;
import com.syncnapsis.security.annotations.Accessible;

/**
 * Abstract base for AccessControllers using the {@link Accessible} annotation.
 * 
 * @author ultimate
 * @param <T> - the Type of the Target class accessed
 */
public abstract class AnnotationAccessController<T> extends AccessController<T>
{
	/**
	 * Logger-Instance
	 */
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * Check an accessible annotation for accessibility
	 * 
	 * @param entity - the entity to check to accessibility for
	 * @param a - the accessible annotation to check
	 * @param defaultAccessible - the default value for accessibility
	 * @param authorities - the authorities to check for
	 * @return true or false
	 */
	public boolean isAccessible(Object entity, Accessible a, int defaultAccessible, Object... authorities)
	{
		int accessible = (a != null ? a.value() : defaultAccessible);

		if(accessible == Accessible.NOBODY)
			return false;
		if(accessible == Accessible.ANYBODY)
			return true;
		if((accessible & Accessible.OWNER) != 0 && isOwner(entity, authorities))
			return true;
		if((accessible & Accessible.FRIEND) != 0 && isFriend(entity, authorities))
			return true;
		if((accessible & Accessible.ENEMY) != 0 && isEnemy(entity, authorities))
			return true;
		if((accessible & Accessible.ALLY) != 0 && isAlly(entity, authorities))
			return true;
		return false;
	}
}

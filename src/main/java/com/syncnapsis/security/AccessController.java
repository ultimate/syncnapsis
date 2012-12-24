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
package com.syncnapsis.security;

/**
 * General Interface for Instances controlling the access to other Objects.
 * AccessControllers can be registered for their Object-Type at the SecurityManager
 * 
 * @see com.syncnapsis.security.SecurityManager
 * @author ultimate
 * @param <T> - the Type of Object to access
 */
public interface AccessController<T>
{
	// Operations to be checked - START

	/**
	 * UNDEFINED-Operation
	 */
	public static final int	UNDEFINED	= 0x00;
	/**
	 * READ-Operation
	 */
	public static final int	READ		= 0x01;
	/**
	 * WRITE-Operation
	 */
	public static final int	WRITE		= 0x02;
	/**
	 * Invoke a method
	 */
	public static final int	INVOKE		= 0x03;
	/**
	 * Call a method
	 * 
	 * @see AccessController#INVOKE
	 */
	public static final int	CALL		= INVOKE;

	// Operations to be checked - END

	/**
	 * Get the target Class this AccessController is associated with.
	 * 
	 * @return the target Class
	 */
	public Class<T> getTargetClass();

	/**
	 * Check the accessibility of a target and the given operation for the defined authorities.
	 * 
	 * @param target - the target to access
	 * @param operation - the operation to perform
	 * @param authorities - the authorities to check
	 * @return true or false
	 */
	public boolean isAccessible(T target, int operation, Object... authorities);
}

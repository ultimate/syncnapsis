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

import java.util.List;

/**
 * General Interface for Instances controlling the access to other Objects.
 * AccessControllers can be registered for their Object-Type at the SecurityManager
 * 
 * @see com.syncnapsis.security.SecurityManager
 * @author ultimate
 * @param <T> - the Type of Object to access
 */
public abstract class AccessController<T>
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
	/**
	 * Modify an entity
	 */
	public static final int	MODIFY		= 0x04;
	/**
	 * Manage an entity
	 */
	public static final int	MANAGE		= 0x05;

	// Operations to be checked - END

	/**
	 * Get the target Class this AccessController is associated with.
	 * 
	 * @return the target Class
	 */
	public abstract Class<T> getTargetClass();

	/**
	 * Check the accessibility of a target and the given operation for the defined authorities.
	 * 
	 * @param entity - the entity the target belongs to
	 * @param target - the target to access
	 * @param operation - the operation to perform
	 * @param authorities - the authorities to check
	 * @return true or false
	 */
	public abstract boolean isAccessible(Object entity, T target, int operation, Object... authorities);

	/**
	 * Check whether a given entity is owned by the given authorities.
	 * 
	 * @param entity - the entity to check the owner for
	 * @param authorities - the authorities to check against
	 * @return true or false
	 */
	public static boolean isOwner(Object entity, Object... authorities)
	{
		if(entity instanceof Ownable<?>)
		{
			List<?> owners = ((Ownable<?>) entity).getOwners();
			if(owners != null && authorities != null)
			{
				for(Object authority : authorities)
				{
					if(owners.contains(authority))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check whether a the given authorities are friend for the entities owner.
	 * 
	 * @param entity - the entity to check the friend for
	 * @param authorities - the authorities to check against
	 * @return true or false
	 */
	public static boolean isFriend(Object entity, Object... authorities)
	{
		// TODO create HasFriends-Interface?
		// Maybe owner should implement HasFriends?
		// then we could call entity.getOwners().get(i).getFriends();
		return false;
	}

	/**
	 * Check whether a the given authorities are enemy for the entities owner.
	 * 
	 * @param entity - the entity to check the enemy for
	 * @param authorities - the authorities to check against
	 * @return true or false
	 */
	public static boolean isEnemy(Object entity, Object... authorities)
	{
		// TODO create HasEnemies-Interface?
		// Maybe owner should implement HasEnemies?
		// then we could call entity.getOwners().get(i).getEnemies();
		return false;
	}
	
	/**
	 * Check whether a the given authorities are enemy for the entities owner.
	 * 
	 * @param entity - the entity to check the enemy for
	 * @param authorities - the authorities to check against
	 * @return true or false
	 */
	public static boolean isAlly(Object entity, Object... authorities)
	{
		// TODO create HasAllies-Interface?
		// Maybe owner should implement HasAllies?
		// then we could call entity.getOwners().get(i).getEnemies();
		return false;
	}
}

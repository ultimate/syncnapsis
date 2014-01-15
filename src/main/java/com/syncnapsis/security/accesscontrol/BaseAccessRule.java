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

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.syncnapsis.data.model.base.BitMask;
import com.syncnapsis.security.AccessRule;
import com.syncnapsis.security.Ownable;

/**
 * Base class implementing basic logic for access rules for easier extension
 * 
 * @author ultimate
 */
public class BaseAccessRule implements AccessRule
{
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.AccessRule#is(int, java.lang.Object[])
	 */
	@Override
	public boolean is(int category, Object entity, Object... authorities)
	{
		if(category == AccessRule.NOBODY)
			return false;
		if(category == AccessRule.ANYBODY)
			return true;
		if((category & AccessRule.OWNER) != 0 && isOwner(entity, authorities))
			return true;
		if((category & AccessRule.FRIEND) != 0 && isFriend(entity, authorities))
			return true;
		if((category & AccessRule.ENEMY) != 0 && isEnemy(entity, authorities))
			return true;
		if((category & AccessRule.ALLY) != 0 && isAlly(entity, authorities))
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.AccessRule#isOf(int, java.lang.Object[])
	 */
	@Override
	public boolean isOf(int roles, Object... authorities)
	{
		for(Object authority: authorities)
		{
			if(authority instanceof BitMask)
			{
				if((((BitMask) authority).getMask() & roles) != 0)
					return true;
			}
		}
		return false;
	}

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
				for(Object owner: owners)
				{
					if(ArrayUtils.indexOf(authorities, owner) != -1)
						return true;
					if(isOwner(owner, authorities))
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

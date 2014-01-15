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
 * Interface for defining access rules.
 * 
 * @author ultimate
 */
public interface AccessRule
{
	public static final int	NOBODY	= 0x00000000;
	public static final int	OWNER	= 0x00000001;
	public static final int	FRIEND	= 0x00000002;
	public static final int	ALLY	= 0x00000004;
	public static final int	ENEMY	= 0x00000008;
	public static final int	ANYBODY	= 0xFFFFFFFF;

	public static final int	NOROLE	= 0x00000000;
	public static final int	ANYROLE	= 0xFFFFFFFF;

	/**
	 * Check the given list of authorities for a relation concerning an entity.<br>
	 * For defining categories use constants defined in this interface:<br>
	 * <ul>
	 * <li>{@link AccessRule#NOBODY}</li>
	 * <li>{@link AccessRule#OWNER}</li>
	 * <li>{@link AccessRule#FRIEND}</li>
	 * <li>{@link AccessRule#ALLY}</li>
	 * <li>{@link AccessRule#ENEMY}</li>
	 * <li>...</li>
	 * <li>{@link AccessRule#ANYBODY}</li>
	 * </ul>
	 * 
	 * @param relation - the relation to check for
	 * @param entity - the entity the relation is checked for
	 * @param authorities - the authorities to check
	 * @return true or false
	 */
	public boolean is(int relation, Object entity, Object... authorities);

	/**
	 * Check the given list of authorities for a role.<br>
	 * For defining role use constants defined in this interface:<br>
	 * <ul>
	 * <li>{@link AccessRule#NOROLE}</li>
	 * <li>...</li>
	 * <li>{@link AccessRule#ANYROLE}</li>
	 * </ul>
	 * 
	 * @param roles - the roles to check for
	 * @param authorities - the authorities to check for
	 * @return true or false
	 */
	public boolean isOf(int roles, Object... authorities);
}

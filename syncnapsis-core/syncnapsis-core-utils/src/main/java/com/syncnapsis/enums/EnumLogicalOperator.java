/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
package com.syncnapsis.enums;

/**
 * An enumeration representing the supported logical operations on booleans.<br>
 * Operations are typical logical concatenations:<br>
 * AND - both must agree<br>
 * OR - at least one must agree<br>
 * XOR - exactly one must agree<br>
 * NOR - nothing must agree<br>
 * <br>
 * 
 * @author ultimate
 */
public enum EnumLogicalOperator
{
	/**
	 * Constant for logical AND
	 */
	AND, 
	/**
	 * Constant for logical OR
	 */
	OR, 
	/**
	 * Constant for logical XOR
	 */
	XOR, 
	/**
	 * Constant for logical NOR
	 */
	NOR
}
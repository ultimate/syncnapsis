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
 * Enum for defining the support of children engines.
 * 
 * @author ultimate
 */
public enum EnumEngineSupport
{
	/**
	 * This engine requires children.
	 * It does not handle incoming connections itself but may be able to handle incoming connections
	 * as a fallback to the childrens handling.
	 */
	CHILDREN_REQUIRED,
	/**
	 * This engine does not support children.
	 * It handles incoming connections itself or redirects them to the parent engine.
	 * Engines not supporting children will require a service mapping either directly or indirectly
	 * via a parent.
	 */
	NOT_SUPPORTED;
}
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
 * A validator is used to validate wether items of a given type are valid according to specific
 * rules. Those rules will be implemented in specific implementations of validators.
 * 
 * @author ultimate
 * 
 * @param <T> - the type to validate
 */
public interface Validator<T>
{
	/**
	 * Check wether the given value is valid according to the underlying rules.
	 * 
	 * @param value - the value to validate
	 * @return true or false
	 */
	public boolean isValid(T value);
}

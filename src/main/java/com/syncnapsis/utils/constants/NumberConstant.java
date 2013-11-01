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
package com.syncnapsis.utils.constants;


/**
 * Class representing a simple constant of raw type <code>Number</code> usable in applications.<br>
 * Constants will only be editable using {@link ConstantLoader}s which provide a function to load
 * constants from a data source.
 * 
 * @author ultimate
 */
public class NumberConstant extends Constant<Number>
{
	/**
	 * Initiate a new constant
	 * 
	 * @param name - the name of this constant.
	 */
	public NumberConstant(String name)
	{
		super(name);
	}

	/**
	 * Initiate a new constant
	 * 
	 * @param name - the name of this constant.
	 * @param defaultValue - the default value
	 */
	public NumberConstant(String name, Number defaultValue)
	{
		super(name, defaultValue);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.Constant#define(java.lang.Object)
	 */
	@Override
	void define(Number raw)
	{
		defineNull(); // reset
		if(raw == null)
			return;
		this.value_Raw = raw;
		this.value_String = raw.toString();
		this.value_Long = raw.longValue();
		this.value_Integer = raw.intValue();
		this.value_Double = raw.doubleValue();
		this.value_Float = raw.floatValue();
		this.value_Short = raw.shortValue();
		this.value_Byte = raw.byteValue();
		this.value_Character = (char) raw.intValue();
		this.value_Boolean = this.value_Integer > 0;
	}
}

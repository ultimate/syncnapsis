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
 * Class representing a simple constant usable in applications.<br>
 * Constants will only be editable using {@link ConstantLoader}s which provide a function to load
 * constants from a data source.<br>
 * <br>
 * <b>Note: the type-specific getters may throw a null-pointer if the raw value for this constant
 * cannot be converted into that type.</b>
 * 
 * @author ultimate
 * 
 * @param <R> - the raw type for this constant
 */
public abstract class Constant<R>
{
	/**
	 * The name of this constant
	 */
	protected String	name;

	/**
	 * The raw value
	 */
	protected R			value_Raw;

	/**
	 * The value as a String
	 */
	protected String	value_String;
	/**
	 * The value as an Integer
	 */
	protected Integer	value_Integer;
	/**
	 * The value as an Long
	 */
	protected Long		value_Long;
	/**
	 * The value as an Double
	 */
	protected Double	value_Double;
	/**
	 * The value as an Float
	 */
	protected Float		value_Float;
	/**
	 * The value as an Short
	 */
	protected Short		value_Short;
	/**
	 * The value as an Byte
	 */
	protected Byte		value_Byte;
	/**
	 * The value as an Boolean
	 */
	protected Boolean	value_Boolean;
	/**
	 * The value as an Integer
	 */
	protected Character	value_Character;

	/**
	 * Initiate a new constant
	 * 
	 * @param name - the name of this constant.
	 */
	public Constant(String name)
	{
		this(name, null);
	}

	/**
	 * Initiate a new constant
	 * 
	 * @param name - the name of this constant.
	 * @param defaultRawValue - the default raw value
	 */
	public Constant(String name, R defaultRawValue)
	{
		this.name = name;
		define(defaultRawValue);
	}

	/**
	 * The name of this constant
	 * 
	 * @return name
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * Set the raw value (and all type specific values) for this constant to null
	 */
	public final void defineNull()
	{
		this.value_Raw = null;
		this.value_String = null;
		this.value_Long = null;
		this.value_Integer = null;
		this.value_Double = null;
		this.value_Float = null;
		this.value_Short = null;
		this.value_Byte = null;
		this.value_Character = null;
		this.value_Boolean = null;
	}

	/**
	 * Set the raw value for this constant an initiate all convertible values of other types.
	 * 
	 * @param value
	 */
	public abstract void define(R raw);

	/**
	 * Get the raw value
	 * 
	 * @return value
	 */
	public final R raw()
	{
		return value_Raw;
	}

	/**
	 * Get the value as a String
	 * 
	 * @return value
	 */
	public final String asString()
	{
		return value_String;
	}

	/**
	 * Get the value as an int
	 * 
	 * @return value
	 */
	public final int asInt()
	{
		return value_Integer;
	}

	/**
	 * Get the value as a long
	 * 
	 * @return value
	 */
	public final long asLong()
	{
		return value_Long;
	}

	/**
	 * Get the value as a double
	 * 
	 * @return value
	 */
	public final double asDouble()
	{
		return value_Double;
	}

	/**
	 * Get the value as a float
	 * 
	 * @return value
	 */
	public final float asFloat()
	{
		return value_Float;
	}

	/**
	 * Get the value as a short
	 * 
	 * @return value
	 */
	public final short asShort()
	{
		return value_Short;
	}

	/**
	 * Get the value as a byte
	 * 
	 * @return value
	 */
	public final byte asByte()
	{
		return value_Byte;
	}

	/**
	 * Get the value as a boolean
	 * 
	 * @return value
	 */
	public final boolean asBoolean()
	{
		return value_Boolean;
	}

	/**
	 * Get the value as a char
	 * 
	 * @return value
	 */
	public final char asChar()
	{
		return value_Character;
	}
}

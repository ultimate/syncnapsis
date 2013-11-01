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
 * Class representing a simple constant of raw type <code>String</code> usable in applications.<br>
 * Constants will only be editable using {@link ConstantLoader}s which provide a function to load
 * constants from a data source.
 * 
 * @author ultimate
 */
public final class StringConstant extends Constant<String>
{
	/**
	 * Initiate a new constant
	 * 
	 * @param name - the name of this constant.
	 */
	public StringConstant(String name)
	{
		super(name);
	}

	/**
	 * Initiate a new constant
	 * 
	 * @param name - the name of this constant.
	 * @param defaultValue - the default value
	 */
	public StringConstant(String name, String defaultValue)
	{
		super(name, defaultValue);
	}

	/* package */void define(String raw)
	{
		defineNull(); // reset
		if(raw == null)
			return;
		this.value_Raw = raw;
		this.value_String = raw;
		if("true".equalsIgnoreCase(raw) || "false".equalsIgnoreCase(raw))
		{
			this.value_Boolean = "true".equalsIgnoreCase(raw);
			int b = this.value_Boolean ? 1 : 0;
			this.value_Long = (long) b;
			this.value_Integer = b;
			this.value_Double = (double) b;
			this.value_Float = (float) b;
			this.value_Short = (short) b;
			this.value_Byte = (byte) b;
			this.value_Character = (char) b;
		}
		else
		{
			try
			{
				this.value_Double = Double.parseDouble(raw);
				this.value_Float = this.value_Double.floatValue();
			}
			catch(NumberFormatException e)
			{
				// ignore
			}
			try
			{
				this.value_Long = Long.parseLong(raw);
				this.value_Integer = this.value_Long.intValue();
				this.value_Short = this.value_Integer.shortValue();
				this.value_Byte = this.value_Integer.byteValue();
				this.value_Character = (char) this.value_Integer.intValue();
				this.value_Boolean = this.value_Integer > 0;
			}
			catch(NumberFormatException e)
			{
				if(this.value_Double != null)
				{
					this.value_Long = this.value_Double.longValue();
					this.value_Integer = this.value_Long.intValue();
					this.value_Short = this.value_Integer.shortValue();
					this.value_Byte = this.value_Integer.byteValue();
					this.value_Character = (char) this.value_Integer.intValue();
					this.value_Boolean = this.value_Integer > 0;
				}
			}
		}
	}
}

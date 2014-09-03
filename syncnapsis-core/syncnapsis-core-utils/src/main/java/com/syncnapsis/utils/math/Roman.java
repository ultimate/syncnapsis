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
package com.syncnapsis.utils.math;

/**
 * Special number format allowing output of Roman numbers.<br>
 * 
 * Parsing and formatting is according to
 * <ul>
 * <li><a
 * href="http://www.rosettacode.org/wiki/Roman_numerals/Decode#Java_2">http://www.rosettacode.
 * org/wiki/Roman_numerals/Decode#Java_2</a></li>
 * <li><a
 * href="http://www.rosettacode.org/wiki/Roman_numerals/Encode#Java">http://www.rosettacode.org
 * /wiki/Roman_numerals/Encode#Java</a></li>
 * </ul>
 * 
 * @author ultimate
 */
public class Roman extends Number
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * The list of valid numerals for roman numbers with their corresponding values.
	 * 
	 * @author ultimate
	 */
	public static enum Numeral
	{
		I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100), CD(400), D(500), CM(900), M(1000);

		/**
		 * The value for the numeral
		 */
		public final int	value;

		/**
		 * Constructor
		 * 
		 * @param value - the value for the numeral
		 */
		private Numeral(int value)
		{
			this.value = value;
		}
	}

	/**
	 * The internal int value
	 */
	private int	value;

	/**
	 * Construct a new Roman with value 0
	 */
	public Roman()
	{
		this.value = 0;
	}

	/**
	 * Construct a new Roman with the given value
	 * 
	 * @param value - the value
	 */
	public Roman(int value)
	{
		if(value < 0)
			throw new IllegalArgumentException("value must be >= 0");
		this.value = value;
	}

	/**
	 * Construct a new Roman from the given String value
	 * 
	 * @param value - the String value
	 */
	public Roman(String value)
	{
		this.value = parseRoman(value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#intValue()
	 */
	@Override
	public int intValue()
	{
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#longValue()
	 */
	@Override
	public long longValue()
	{
		return intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#floatValue()
	 */
	@Override
	public float floatValue()
	{
		return intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#doubleValue()
	 */
	@Override
	public double doubleValue()
	{
		return intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Roman other = (Roman) obj;
		if(value != other.value)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return toString(this.value);
	}

	/**
	 * Parse the value of a roman number string into an integer
	 * 
	 * @see <a
	 *      href="http://www.rosettacode.org/wiki/Roman_numerals/Decode#Java_2">http://www.rosettacode.org/wiki/Roman_numerals/Decode#Java_2</a>
	 * @param roman - the string to parse
	 * @return the corresponding int value
	 */
	public static int parseRoman(String roman)
	{
		int i = 0;
		roman = roman.toUpperCase();
		try
		{
			int value = 0;
			int vi = 0;
			int vi1 = Numeral.valueOf("" + roman.charAt(i)).value;
			for(; i < roman.length() - 1; i++)
			{
				vi = vi1;
				vi1 = Numeral.valueOf("" + roman.charAt(i + 1)).value;
				if(vi < vi1)
					value -= vi;
				else
					value += vi;
			}
			value += vi1;
			return value;
		}
		catch(IllegalArgumentException e)
		{
			throw new NumberFormatException("illegal character at position " + i + ": " + roman.charAt(i));
		}
	}

	/**
	 * Get the roman number representation for the given int as a string
	 * 
	 * @see <a
	 *      href="http://www.rosettacode.org/wiki/Roman_numerals/Encode#Java">http://www.rosettacode.org/wiki/Roman_numerals/Encode#Java</a>
	 * @param value - the int value
	 * @return the roman number
	 */
	public static String toString(int value)
	{
		if(value <= 0)
			throw new IllegalArgumentException("value must be > 0");

		StringBuilder sb = new StringBuilder();

		final Numeral[] values = Numeral.values();
		for(int i = values.length - 1; i >= 0; i--)
		{
			while(value >= values[i].value)
			{
				sb.append(values[i]);
				value -= values[i].value;
			}
		}
		return sb.toString();
	}
}

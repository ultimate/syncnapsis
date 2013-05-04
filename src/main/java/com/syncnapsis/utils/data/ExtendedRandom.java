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
package com.syncnapsis.utils.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.util.Assert;

import com.syncnapsis.utils.TimeZoneUtil;

/**
 * {@link Random} extension with support for strings, number in intervals and more
 * 
 * @author ultimate
 */
public class ExtendedRandom extends Random
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Create a new extended Random
	 */
	public ExtendedRandom()
	{
		super();
	}

	/**
	 * Create a new extended random with the given seed
	 * 
	 * @param seed - the seed
	 */
	public ExtendedRandom(long seed)
	{
		super(seed);
	}

	/**
	 * Create a new random Integer within the interval [min; max] (both inclusive).<br>
	 * If min and max are swapped they will be re-swapped internally to guarantee min is smaller
	 * than max.
	 * 
	 * @param min - the lower bound for the interval
	 * @param max - the upper bound for the interval
	 * @return a random Integer
	 */
	public int nextInt(int min, int max)
	{
		if(min == max)
			return min;
		if(min < max)
			return nextInt(max - min + 1) + min;
		else
			return nextInt(min - max + 1) + max;
	}

	/**
	 * Create a new random Long within the interval [min; max] (both inclusive).<br>
	 * If min and max are swapped they will be re-swapped internally to guarantee min is smaller
	 * than max.
	 * 
	 * @param min - the lower bound for the interval
	 * @param max - the upper bound for the interval
	 * @return a random Long
	 */
	public long nextLong(long min, long max)
	{
		if(min == max)
			return min;
		if(min < max)
			return ((long) (nextDouble() * (max - min + 1)) + min);
		else
			return ((long) (nextDouble() * (min - max + 1)) + max);
	}

	/**
	 * Create a new random Double within the interval [min; max] (both inclusive).<br>
	 * If min and max are swapped they will be re-swapped internally to guarantee min is smaller
	 * than max.
	 * 
	 * @param min - the lower bound for the interval
	 * @param max - the upper bound for the interval
	 * @return a random Double
	 */
	public double nextDouble(double min, double max)
	{
		if(min == max)
			return min;
		if(min < max)
			return ((long) (nextDouble() * (max - min + 1)) + min);
		else
			return ((long) (nextDouble() * (min - max + 1)) + max);
	}

	/**
	 * Create a new random boolean with the given probabilities for true and false.<br>
	 * The given probabilities are <b>not</b> treated as percentage but in their relation to each
	 * other as demonstrated below:<br>
	 * <code>p(true) = probabilityTrue/(probabilityTrue + probabilityFalse)</code><br>
	 * <code>p(false) = probabilityFalse/(probabilityTrue + probabilityFalse)</code>
	 * 
	 * @return a random boolean
	 */
	public boolean nextBoolean(int probabilityTrue, int probabilityFalse)
	{
		Assert.isTrue(probabilityTrue >= 0, "probabilityTrue must be >= 0");
		Assert.isTrue(probabilityFalse >= 0, "probabilityFalse must be >= 0");
		int val = nextInt(-probabilityFalse, probabilityTrue - 1);
		return val >= 0;
	}

	/**
	 * Create a new random (equally distributed) enum for the given enum type
	 * 
	 * @return a random enum value
	 */
	@SuppressWarnings("unchecked")
	public <T> T nextEnum(Class<T> cls)
	{
		Assert.isTrue(cls.isEnum(), "Class must be Enum-Class");
		try
		{
			Method valuesMethod = cls.getMethod("values");
			T[] values = (T[]) valuesMethod.invoke(null);
			int i = nextInt(values.length);
			return values[i];
		}
		catch(NoSuchMethodException e)
		{
			throw new IllegalArgumentException("values() not found for " + cls.getName());
		}
		catch(IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Could not invoke values() for " + cls.getName() + ": " + e.getMessage());
		}
		catch(IllegalAccessException e)
		{
			throw new IllegalArgumentException("Could not invoke values() for " + cls.getName() + ": " + e.getMessage());
		}
		catch(InvocationTargetException e)
		{
			throw new IllegalArgumentException("Could not invoke values() for " + cls.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * Get a random entry from the given list
	 * 
	 * @param list - the list
	 * @return a random entry
	 */
	public <T> T nextEntry(List<T> list)
	{
		Assert.notNull(list);
		return list.get(nextInt(list.size()));
	}

	/**
	 * Get a random entry from the given array
	 * 
	 * @param array - the array
	 * @return a random entry
	 */
	public <T> T nextEntry(T[] array)
	{
		Assert.notNull(array);
		return array[nextInt(array.length)];
	}

	/**
	 * Create a new random Date within the given time range using
	 * {@link ExtendedRandom#nextLong(long, long)}:<br>
	 * <code>return new Date(randomLong(from.getTime(), until.getTime()));</code>
	 * 
	 * @param from - start date of the time range
	 * @param until - end date of the time range
	 * @return a random Date
	 */
	public Date nextDate(Date from, Date until)
	{
		return new Date(nextLong(from.getTime(), until.getTime()));
	}

	/**
	 * Create new random String with random length for the character source
	 * {@link DefaultData#STRING_ASCII_COMPLETE_NO_CONTROLCHARS}
	 * 
	 * @see ExtendedRandom#nextString(int, String)
	 * @return a random String
	 */
	public String nextString()
	{
		return nextString(nextInt(20), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS);
	}

	/**
	 * Create a new random String with the specified length from the given character source from the
	 * underlying random number generator.
	 * 
	 * @param length - the desired length of the string
	 * @param source - the character source
	 * @return a random string
	 */
	public String nextString(int length, String source)
	{
		Assert.isTrue(length >= 0, "length must be >= 0");
		Assert.hasLength(source, "source must not be empty");
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++)
		{
			sb.append(source.charAt(nextInt(source.length())));
		}
		return sb.toString();
	}

	/**
	 * Create a new random and valid domain from the underlying random number generator.<br>
	 * The domain will be valid according to {@link DefaultData#REGEXP_DOMAIN}
	 * 
	 * @return a random and valid domain
	 */
	public String nextDomain()
	{
		String topLevelDomain;
		if(nextBoolean())
			topLevelDomain = nextEntry(DefaultData.STRING_TOP_LEVEL_DOMAIN_EXTENSIONS_LIST);
		else
			topLevelDomain = nextString(2, DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER);

		String domain = nextString(1, DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER
				+ DefaultData.STRING_ASCII_NUMBERS)
				+ nextString(nextInt(256 - topLevelDomain.length() - 2), DefaultData.STRING_ASCII_LETTERS_LOWER
						+ DefaultData.STRING_ASCII_LETTERS_UPPER + DefaultData.STRING_ASCII_NUMBERS + "-")
				+ nextString(1, DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER + DefaultData.STRING_ASCII_NUMBERS);

		return domain + "." + topLevelDomain;
	}

	/**
	 * Create a new random and valid e-mail-address from the underlying random number generator.<br>
	 * The address will be valid according to {@link DefaultData#REGEXP_EMAIL}
	 * 
	 * @param maxLength - the max length for the email
	 * @return a random and valid e-mail-address
	 */
	public String nextEmail(int maxLength)
	{
		int domainLengthReservated = 10;
		int l1 = nextInt(1, maxLength - domainLengthReservated);
		int l2 = nextInt(-10, maxLength - domainLengthReservated - l1);
		int l3 = nextInt(-10, maxLength - domainLengthReservated - l1 - (l2 < 0 ? 0 : l2));
		int l4 = maxLength - domainLengthReservated - l1 - (l2 < 0 ? 0 : l2) - (l3 < 0 ? 0 : l3);

		String s1 = null;
		String s2 = null;
		String s3 = null;
		String s4 = null;

		s1 = nextString(l1, DefaultData.STRING_EMAIL_COMPLETE_NO_DOT_NO_AT);
		if(l2 > 0)
			s2 = nextString(l2, DefaultData.STRING_EMAIL_COMPLETE_NO_DOT_NO_AT);
		if(l3 > 0)
			s3 = nextString(l3, DefaultData.STRING_EMAIL_COMPLETE_NO_DOT_NO_AT);
		if(l4 > 0)
			s4 = nextString(l4, DefaultData.STRING_EMAIL_COMPLETE_NO_DOT_NO_AT);

		String name = s1 + (l2 > 0 ? "." + s2 : "") + (l3 > 0 ? "." + s3 : "") + (l4 > 0 ? "." + s4 : "");
		String domain;
		do
		{
			domain = nextDomain();
		} while(domain.length() + 1 + name.length() > maxLength);

		return name + "@" + domain;
	}

	/**
	 * Create a new random TimeZoneId to be usable with {@link TimeZoneUtil} The TimeZoneId will
	 * consist of region and id.
	 * 
	 * @return a random TimeZoneId
	 */
	public String nextTimeZoneId()
	{
		String region = nextEntry(TimeZoneUtil.getRegions());
		String id = nextEntry(TimeZoneUtil.getIdsByRegions(region));
		return region + "/" + id;
	}
}

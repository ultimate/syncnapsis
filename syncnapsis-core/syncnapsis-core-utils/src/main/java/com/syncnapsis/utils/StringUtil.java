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
package com.syncnapsis.utils;

import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

import org.dbunit.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.utils.reflections.Field;

/**
 * Utility-Class for String operations like password encoding or message formatting.
 * 
 * @author ultimate
 */
public abstract class StringUtil
{
	protected static transient final Logger	logger			= LoggerFactory.getLogger(StringUtil.class);

	/**
	 * Regular Expression for HEX-Values.<br>
	 * (odd number of hex-digits allowed)
	 */
	public static String					REGEXP_HEX		= "[0-9A-Fa-f]*";
	/**
	 * Regular Expression for HEX-Values.<br>
	 * (odd number of hex-digits not allowed, always containing full byte)
	 */
	public static String					REGEXP_HEX_2	= "([0-9A-Fa-f]{2})*";
	/**
	 * Regular Expression for HEX-Values.<br>
	 * (arbitrary number of binary-digits allowed)
	 */
	public static String					REGEXP_BINARY	= "[0-1]*";
	/**
	 * Regular Expression for HEX-Values.<br>
	 * (arbitrary number of binary-digits allowed, always containing full byte)
	 */
	public static String					REGEXP_BINARY_8	= "([0-1]{8})*";

	/**
	 * Length of a second in ms
	 */
	public static final long				TIME_SECOND		= 1000;
	/**
	 * Length of a minute in ms
	 */
	public static final long				TIME_MINUTE		= TIME_SECOND * 60;
	/**
	 * Length of an hourin ms
	 */
	public static final long				TIME_HOUR		= TIME_MINUTE * 60;
	/**
	 * Length of a day in ms
	 */
	public static final long				TIME_DAY		= TIME_HOUR * 24;
	/**
	 * Length of a year in ms
	 */
	public static final long				TIME_YEAR		= TIME_DAY * 365;

	/**
	 * Encode a string with the given algorithm using MessageDigest
	 * 
	 * @see MessageDigest#getInstance(String)
	 * @param s - the string to encode
	 * @param algorithm - the algorithm for MessageDigest
	 * @return the encoded password
	 */
	public static String encode(String s, String algorithm)
	{
		byte[] unencodedPassword = s.getBytes();

		MessageDigest md = null;

		try
		{
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		}
		catch(Exception e)
		{
			logger.error("Exception: " + e);
			return s;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		return toHexString(encodedPassword);
	}

	/**
	 * Format a message with the given pattern and arguments in the given EnumLocale.<br>
	 * This Method will temporarily create an empty StringBuffer.
	 * 
	 * @see StringUtil#format(String, Locale, Object...)
	 * @see StringUtil#format(StringBuffer, String, Locale, Object...)
	 * @param pattern - the message pattern
	 * @param locale - the EnumLocale (Locale is required for Number and Date formatting
	 * @param args - the message args
	 * @return the generated String
	 */
	public static String format(String pattern, EnumLocale locale, Object... args)
	{
		return format(pattern, locale != null ? locale.getLocale() : null, args);
	}

	/**
	 * Format a message with the given pattern and arguments in the given Locale.<br>
	 * This Method will temporarily create an empty StringBuffer.
	 * 
	 * @see StringUtil#format(StringBuffer, String, Locale, Object...)
	 * @param pattern - the message pattern
	 * @param locale - the Locale (Locale is required for Number and Date formatting
	 * @param args - the message args
	 * @return the generated String
	 */
	public static String format(String pattern, Locale locale, Object... args)
	{
		return format(new StringBuffer(), pattern, locale, args).toString();
	}

	/**
	 * Format a message with the given pattern and arguments in the given EnumLocale.
	 * 
	 * @see StringUtil#format(StringBuffer, String, Locale, Object...)
	 * @param result - a StringBuffer to append the result to
	 * @param pattern - the message pattern
	 * @param locale - the EnumLocale (Locale is required for Number and Date formatting
	 * @param args - the message args
	 * @return the modified StringBuffer
	 */
	public static StringBuffer format(StringBuffer result, String pattern, EnumLocale locale, Object... args)
	{
		return format(result, pattern, locale != null ? locale.getLocale() : null, args);
	}

	/**
	 * Format a message with the given pattern and arguments in the given Locale.
	 * 
	 * @param result - a StringBuffer to append the result to
	 * @param pattern - the message pattern
	 * @param locale - the Locale (Locale is required for Number and Date formatting
	 * @param args - the message args
	 * @return the modified StringBuffer
	 */
	public static StringBuffer format(StringBuffer result, String pattern, Locale locale, Object... args)
	{
		return getFormat(pattern, locale).format(args, result, null);
	}

	/**
	 * Create a temporarily MessageFormat Object for the given pattern and locale
	 * 
	 * @param pattern - the message pattern
	 * @param locale - the Locale (Locale is required for Number and Date formatting
	 * @return the MessageFormat
	 */
	public static MessageFormat getFormat(String pattern, Locale locale)
	{
		if(locale == null)
			logger.warn("locale is null! Using EnumLocale.getDefault().");
		return new MessageFormat(pattern, locale != null ? locale : EnumLocale.getDefault().getLocale());
	}

	/**
	 * Fillup a String to the required minimum length by appending the fillup-char to the beginning
	 * or end of the string until it reaches the required length.
	 * 
	 * @param s - the string to fillup
	 * @param minLength - the minimun length to reach
	 * @param fillup - the char to append
	 * @param begin - true for prefixing, false for suffixing
	 * @return the modified string
	 */
	public static String fillup(String s, int minLength, char fillup, boolean begin)
	{
		if(s == null)
			s = "" + null;
		while(s.length() < minLength)
		{
			if(begin)
				s = fillup + s;
			else
				s = s + fillup;
		}
		return s;
	}

	/**
	 * Equal to <code>StringUtil.fillup("" + n, minLength, ' ', true)</code>
	 * 
	 * @see StringUtil#fillup(String, int, char, boolean)
	 * @param n - the number to convert to a string
	 * @param minLength - the minimun length to reach
	 * @return the number as a string
	 */
	public static String fillupNumber(Number n, int minLength)
	{
		return fillup("" + n, minLength, ' ', true);
	}

	/**
	 * Encode a byte Array to a Hex String.<br>
	 * In order to guarantee 2 Hex digits per byte each byte is checked for values < 0x10 and
	 * prefixed with '0' if neccessary.
	 * 
	 * @param bytes - the byte Array
	 * @return the Hex String
	 */
	public static String toHexString(byte[] bytes, int offset, int length)
	{
		StringBuffer buf = new StringBuffer();
		for(int i = offset; i < offset + length; i++)
		{
			if((bytes[i] & 0xff) < 0x10)
				buf.append("0");
			buf.append(Integer.toHexString(bytes[i] & 0xff));
		}
		return buf.toString();
	}

	/**
	 * See {@link StringUtil#toHexString(byte[], int, int)}:
	 * <code>toHexString(bytes, 0, bytes.length)</code>
	 * 
	 * @see StringUtil#toHexString(byte[], int, int)
	 * @param bytes
	 * @return
	 */
	public static String toHexString(byte[] bytes)
	{
		return toHexString(bytes, 0, bytes.length);
	}

	/**
	 * Decode a Hex-String to a byte Array.
	 * 
	 * @param s - the Hex-String to decode
	 * @return the byte Array
	 */
	public static byte[] fromHexString(String s)
	{
		if(!s.matches(REGEXP_HEX))
			throw new IllegalArgumentException("cannot parse '" + s + "'");

		int length = (int) Math.ceil(s.length() / 2.0);
		byte[] bytes = new byte[length];

		int c = s.length() - 2;
		for(int i = length - 1; i >= 0; i--)
		{
			bytes[i] = (byte) Integer.parseInt(s.substring(c < 0 ? 0 : c, c + 2), 16);
			c = c - 2;
		}

		return bytes;
	}

	/**
	 * Encode a byte Array to a Binary String.<br>
	 * In order to guarantee 8 Binary digits per byte each byte is checked for lengths < 8 and
	 * prefixed with '0' if neccessary.
	 * 
	 * @param bytes - the byte Array
	 * @return the Hex String
	 */
	public static String toBinaryString(byte[] bytes, int offset, int length)
	{
		StringBuffer buf = new StringBuffer();
		String s;
		for(int i = offset; i < offset + length; i++)
		{
			s = Integer.toBinaryString(bytes[i]);
			for(int c = 8; c > s.length(); c--)
				buf.append("0");
			buf.append(s.substring(Math.max(s.length() - 8, 0), s.length()));
		}
		return buf.toString();
	}

	/**
	 * See {@link StringUtil#toBinaryString(byte[], int, int)}:
	 * <code>toBinaryString(bytes, 0, bytes.length)</code>
	 * 
	 * @see StringUtil#toBinaryString(byte[], int, int)
	 * @param bytes
	 * @return
	 */
	public static String toBinaryString(byte[] bytes)
	{
		return toBinaryString(bytes, 0, bytes.length);
	}

	/**
	 * Decode a Binary-String to a byte Array.
	 * 
	 * @param s - the Binary-String to decode
	 * @return the byte Array
	 */
	public static byte[] fromBinaryString(String s)
	{
		if(!s.matches(REGEXP_BINARY))
			throw new IllegalArgumentException("cannot parse '" + s + "'");

		int length = (int) Math.ceil(s.length() / 8.0);
		byte[] bytes = new byte[length];

		int c = s.length() - 8;
		for(int i = length - 1; i >= 0; i--)
		{
			bytes[i] = (byte) Integer.parseInt(s.substring(c < 0 ? 0 : c, c + 8), 2);
			c = c - 8;
		}

		return bytes;
	}

	/**
	 * Encode a String base64 style.<br>
	 * This Method will forward to an external implementation for future compatibility and
	 * exchangeability.
	 * 
	 * @param s - the String to encode
	 * @return the base64 encoding of the string
	 */
	public static String encodeBase64(String s)
	{
		return Base64.encodeString(s);
	}

	/**
	 * Decode a String base64 style
	 * This Method will forward to an external implementation for future compatibility and
	 * exchangeability.
	 * 
	 * @param s - the base64 String to decode
	 * @return the decoded String
	 */
	public static String decodeBase64(String s)
	{
		return Base64.decodeToString(s);
	}

	/**
	 * Count the occurrences of a String within another String
	 * 
	 * @param s - the String to scan for part
	 * @param part - the part to look for
	 * @param allowOverlap - wether to allow overlaps while counting
	 * @return the number of occurrences
	 */
	public static int countOccurrences(String s, String part, boolean allowOverlap)
	{
		int index = 0;
		int count = 0;
		while((index = s.indexOf(part, index)) >= 0)
		{
			count++;
			if(!allowOverlap)
				index += part.length();
			else
				index++;
		}
		return count;
	}

	/**
	 * Genericly convert an Object into a String containing all fields and their values.
	 * 
	 * @param o - the object to convert
	 * @param depth - the depth for recursive invocation of this method
	 * @return the String representation
	 */
	public static String toString(Object o, int depth)
	{
		List<Field> fields = ReflectionsUtil.findFields(o.getClass());
		StringBuilder sb = new StringBuilder();
		sb.append(o.getClass().getName());
		sb.append("@" + Integer.toHexString(o.hashCode()));
		sb.append("[");
		Object val;
		boolean first = true;
		for(Field f : fields)
		{
			if(first)
				first = false;
			else
				sb.append(", ");
			sb.append(f.getName());
			sb.append("=");
			try
			{
				val = ReflectionsUtil.getField(o, f.getName());

				if(val != null && depth > 0)
				{
					if(!ClassUtil.isNumber(f.getField().getType()) && !String.class.isAssignableFrom(f.getField().getType())
							&& !f.getField().getType().isPrimitive() && !Boolean.class.isAssignableFrom(f.getField().getType())
							&& !Character.class.isAssignableFrom(f.getField().getType()))
						val = toString(val, depth - 1);
				}
			}
			catch(IllegalAccessException e)
			{
				val = "/inaccessible/";
			}
			catch(NoSuchFieldException e)
			{
				logger.error(e.getMessage());
				val = "/no-such-field/";
			}

			if(String.class.isAssignableFrom(f.getField().getType()))
				sb.append("\"" + val + "\"");
			else
				sb.append(val);
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Convert a time period to a string in the form<br>
	 * <code>1y 2d 3h 4m 5s 7ms</code>
	 * 
	 * @param period - the time period
	 * @return the string
	 */
	public static String toString(long period)
	{
		long y = period / TIME_YEAR;
		long d = (period % TIME_YEAR) / TIME_DAY;
		long h = (period % TIME_YEAR % TIME_DAY) / TIME_HOUR;
		long m = (period % TIME_YEAR % TIME_DAY % TIME_HOUR) / TIME_MINUTE;
		long s = (period % TIME_YEAR % TIME_DAY % TIME_HOUR % TIME_MINUTE) / TIME_SECOND;
		long ms = (period % TIME_YEAR % TIME_DAY % TIME_HOUR % TIME_MINUTE % TIME_SECOND);

		return y + "y " + d + "d " + h + "h " + m + "m " + s + "s " + ms + "ms";
	}

	/**
	 * 64-bit variant of {@link String#hashCode()}<br>
	 * 
	 * @see http://stackoverflow.com/a/1660613
	 * @param s - the string to hash
	 * @return the 64-bit hash code
	 */
	public static long hashCode64(String s)
	{
		long h = 1125899906842597L; // prime
		int len = s.length();
		for(int i = 0; i < len; i++)
		{
			h = 31 * h + s.charAt(i);
		}
		return h;
	}
}

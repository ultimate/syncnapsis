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
package com.syncnapsis.utils;

import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Locale;

import com.syncnapsis.enums.EnumLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * Encode a password with the given algorithm using MessageDigest
	 * 
	 * @see MessageDigest#getInstance(String)
	 * @param password - the password to encode
	 * @param algorithm - the algorithm for MessageDigest
	 * @return the encoded password
	 */
	public static String encodePassword(String password, String algorithm)
	{
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try
		{
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		}
		catch(Exception e)
		{
			logger.error("Exception: " + e);
			return password;
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
}

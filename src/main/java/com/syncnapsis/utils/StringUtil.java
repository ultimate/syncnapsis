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
	protected static transient final Logger	logger	= LoggerFactory.getLogger(StringUtil.class);

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
	 * Encode a byte Array to a Hex String.<br>
	 * In order to guarantee 2 Hex digits per byte each byte is checked for values < 0x10 and prefix with '0' if neccessary.<br>
	 * <code><pre>
	 * for(byte b : bytes)
	 * {
	 *   if((b & 0xff) < 0x10)
	 *   {
	 *     buf.append("0");
     *   }
	 *   buf.append(Long.toString(b & 0xff, 16));
	 * }
	 * </pre></code> 
	 * 
	 * @param bytes - the byte Array
	 * @return the Hex String
	 */
	public static String toHexString(byte[] bytes)
	{
		StringBuffer buf = new StringBuffer();
		for(byte b : bytes)
		{
			if((b & 0xff) < 0x10)
			{
				buf.append("0");
			}
			buf.append(Long.toString(b & 0xff, 16));
		}
		return buf.toString();
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
}

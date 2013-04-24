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

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class StringUtilTest extends LoggerTestCase
{
	public void testEncode() throws Exception
	{
		logger.debug("testing encodePassword...");

		String password = "tomcat";
		String encrypted = "536c0b339345616c1b33caf454454d8b8a190d6c";
		assertEquals(encrypted, StringUtil.encode(password, "SHA"));
	}

	public void testFormat()
	{
		// Test correct forwarding of arguments
		String pattern = "aPattern";
		EnumLocale locale = EnumLocale.DE;
		Object[] args = new Object[] { 1L, "abc", true };

		MessageFormat mf_locale = StringUtil.getFormat(pattern, locale.getLocale());
		MessageFormat mf_default = StringUtil.getFormat(pattern, null);

		String res_util_enumlocale;
		String res_util_locale;
		String res_util_null_enumlocale;
		String res_util_null_locale;
		String res_locale;
		String res_default;

		res_util_enumlocale = StringUtil.format(pattern, locale, args);
		res_util_locale = StringUtil.format(pattern, locale.getLocale(), args);
		res_util_null_enumlocale = StringUtil.format(pattern, (EnumLocale) null, args);
		res_util_null_locale = StringUtil.format(pattern, (Locale) null, args);
		res_locale = mf_locale.format(args, new StringBuffer(), null).toString();
		res_default = mf_default.format(args, new StringBuffer(), null).toString();

		assertNotNull(res_util_enumlocale);
		assertNotNull(res_util_locale);
		assertNotNull(res_util_null_enumlocale);
		assertNotNull(res_util_null_locale);
		assertNotNull(res_locale);
		assertNotNull(res_default);

		assertEquals(res_locale, res_util_enumlocale);
		assertEquals(res_locale, res_util_locale);
		assertEquals(res_default, res_util_null_enumlocale);
		assertEquals(res_default, res_util_null_locale);
	}

	public void testGetFormat()
	{
		String pattern = "alasjd {1} hf";
		Locale locale = Locale.GERMAN;

		MessageFormat mf = StringUtil.getFormat(pattern, locale);

		assertNotNull(mf);
		assertEquals(locale, mf.getLocale());
		assertEquals(pattern, mf.toPattern());
	}

	public void testFillup()
	{
		String org = "test";

		assertEquals("      " + org, StringUtil.fillup(org, 10, ' ', true));
		assertEquals(org + "      ", StringUtil.fillup(org, 10, ' ', false));

		assertEquals(" " + org, StringUtil.fillup(org, 5, ' ', true));
		assertEquals(org + " ", StringUtil.fillup(org, 5, ' ', false));

		assertEquals(org, StringUtil.fillup(org, 1, ' ', true));
		assertEquals(org, StringUtil.fillup(org, 1, ' ', false));

		assertEquals("aaaaaa" + org, StringUtil.fillup(org, 10, 'a', true));
		assertEquals(org + "aaaaaa", StringUtil.fillup(org, 10, 'a', false));

		assertEquals("a" + org, StringUtil.fillup(org, 5, 'a', true));
		assertEquals(org + "a", StringUtil.fillup(org, 5, 'a', false));

		assertEquals(org, StringUtil.fillup(org, 1, 'a', true));
		assertEquals(org, StringUtil.fillup(org, 1, 'a', false));

		assertEquals("      " + null, StringUtil.fillup(null, 10, ' ', true));
		assertEquals(null + "      ", StringUtil.fillup(null, 10, ' ', false));

		assertEquals(" " + null, StringUtil.fillup(null, 5, ' ', true));
		assertEquals(null + " ", StringUtil.fillup(null, 5, ' ', false));

		assertEquals("" + null, StringUtil.fillup(null, 1, ' ', true));
		assertEquals(null + "", StringUtil.fillup(null, 1, ' ', false));
	}

	public void testFillupNumber()
	{
		for(int i = 1; i <= 10000000; i=i*10)
		{
			assertTrue(StringUtil.fillupNumber(i, 6).endsWith("" + i));
			assertTrue(StringUtil.fillupNumber(-i, 6).endsWith("" + (-i)));
			if(i < 1000000)
				assertEquals(6, StringUtil.fillupNumber(i, 6).length());
			else
				assertEquals((""+i).length(), StringUtil.fillupNumber(i, 6).length());
		}

	}

	public void testToHexString_old()
	{
		String hex1 = "af";
		String hex2 = "03";
		String hex3 = "8c";
		String hex4 = "b0";

		byte[] bytes = new byte[] { (byte) Integer.parseInt(hex1, 16), (byte) Integer.parseInt(hex2, 16), (byte) Integer.parseInt(hex3, 16),
				(byte) Integer.parseInt(hex4, 16) };

		String result = StringUtil.toHexString(bytes);

		assertEquals(hex1 + hex2 + hex3 + hex4, result);
	}

	@TestCoversMethods("*HexString")
	public void testHexStringConversion()
	{
		Random r = new Random();
		
		byte[] bytes;
		for(int i = 0; i < 100; i++)
		{
			bytes = new byte[r.nextInt(10)];
			r.nextBytes(bytes);
			
			assertTrue(Arrays.equals(bytes, StringUtil.fromHexString(StringUtil.toHexString(bytes))));
		}
	}

	@TestCoversMethods("*BinaryString")
	public void testBinaryStringConversion()
	{
		Random r = new Random();
		
		byte[] bytes;
		for(int i = 0; i < 100; i++)
		{
			bytes = new byte[r.nextInt(10)];
			r.nextBytes(bytes);
			
			assertTrue(Arrays.equals(bytes, StringUtil.fromBinaryString(StringUtil.toBinaryString(bytes))));
		}
	}

	@TestCoversMethods("*codeBase64")
	public void testBase64() throws Exception
	{
		String s = "my test String";
		assertFalse(s.equals(StringUtil.encodeBase64(s)));
		assertEquals(s, StringUtil.decodeBase64(StringUtil.encodeBase64(s)));
	}
	
	public void testCountOccurrences() throws Exception
	{
		String s = "abcdefabcdeababa";
		
		assertEquals(1, StringUtil.countOccurrences(s, "f", true));
		assertEquals(1, StringUtil.countOccurrences(s, "f", false));

		assertEquals(1, StringUtil.countOccurrences(s, "def", true));
		assertEquals(1, StringUtil.countOccurrences(s, "def", false));

		assertEquals(2, StringUtil.countOccurrences(s, "de", true));
		assertEquals(2, StringUtil.countOccurrences(s, "de", false));
		
		assertEquals(5, StringUtil.countOccurrences(s, "a", true));
		assertEquals(5, StringUtil.countOccurrences(s, "a", false));
		
		assertEquals(4, StringUtil.countOccurrences(s, "ab", true));
		assertEquals(4, StringUtil.countOccurrences(s, "ab", false));
		
		assertEquals(2, StringUtil.countOccurrences(s, "aba", true));
		assertEquals(1, StringUtil.countOccurrences(s, "aba", false));
	}
}

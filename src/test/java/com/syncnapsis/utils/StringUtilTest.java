package com.syncnapsis.utils;

import java.text.MessageFormat;
import java.util.Locale;

import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.tests.LoggerTestCase;

public class StringUtilTest extends LoggerTestCase
{
	public void testEncodePassword() throws Exception
	{
		logger.debug("testing encodePassword...");

		String password = "tomcat";
		String encrypted = "536c0b339345616c1b33caf454454d8b8a190d6c";
		assertEquals(encrypted, StringUtil.encodePassword(password, "SHA"));
	}

	public void testFormat()
	{
		// Test correct forwarding of arguments
		String pattern = "aPattern";
		EnumLocale locale = EnumLocale.DE;
		Object[] args = new Object[]{1L, "abc", true};
		
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

	public void testToHexString()
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
}

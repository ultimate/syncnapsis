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
package com.syncnapsis.providers.impl;

import com.syncnapsis.providers.ParameterizedProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({LabelProvider.class, ParameterizedProvider.class})
public class LabelProviderTest extends LoggerTestCase
{
	@TestCoversMethods("*")
	public void testLabelProvider()
	{
		logger.warn("nothing done here");
	}
}

//package com.syncnapsis.providers;
//
//import java.util.Locale;
//
//import com.syncnapsis.constants.SecurityConstants;
//import com.syncnapsis.enums.EnumLocale;
//import com.syncnapsis.mock.zk.MockSession;
//import com.syncnapsis.tests.PELoggerTestCase;
//import com.syncnapsis.tests.annotations.TestCoversMethods;
//import com.syncnapsis.tests.annotations.TestExcludesMethods;
//
//@TestExcludesMethods({"resetLoader", "setVariableResolver", "getLoader"})
//public class LabelProviderTest extends PELoggerTestCase
//{	
//	public void testGetSessionLocale() throws Exception
//	{
//		logger.debug("testing getSessionLocale...");
//		
//		LabelProvider loader = LabelProvider.getLoader();
//
//		EnumLocale locale;
//		
//		locale = EnumLocale.EN;
//		session.setAttribute(SecurityConstants.SESSION_LOCALE_KEY, locale);		
//		assertEquals(locale.getLocale(), loader.getSessionLocale());
//		
//		locale = EnumLocale.DE;
//		session.setAttribute(SecurityConstants.SESSION_LOCALE_KEY, locale);		
//		assertEquals(locale.getLocale(), loader.getSessionLocale());
//		
//		locale = EnumLocale.EN;
//		session.setAttribute(SecurityConstants.SESSION_LOCALE_KEY, null);		
//		assertEquals(locale.getLocale(), loader.getSessionLocale());
//	}
//
//	public void testGetAvailableLocales() throws Exception
//	{
//		logger.debug("testing getAvailableLocales...");
//		
//		Locale[] locales = LabelProvider.getAvailableLocales();
//		EnumLocale[] enumLocales = EnumLocale.values();
//		
//		assertEquals(enumLocales.length, locales.length);
//		
//		for(int i = 0; i < locales.length; i++)
//		{
//			assertEquals(enumLocales[i].getLocale(), locales[i]);
//		}
//	}
//
//	@TestCoversMethods({"getText", "getLabel", "locate"})
//	public void testGetText() throws Exception
//	{
//		logger.debug("testing getText...");
//		
//		MockSession session = setUpZkMockEnvironment();
//		
//		LabelProvider loader = LabelProvider.getLoader();
//
//		EnumLocale locale;
//		
//		locale = EnumLocale.EN;
//		session.setAttribute(SecurityConstants.SESSION_LOCALE_KEY, locale);		
//		assertEquals("English", loader.getLabel("locale.name"));
//		
//		locale = EnumLocale.DE;
//		session.setAttribute(SecurityConstants.SESSION_LOCALE_KEY, locale);		
//		assertEquals("Deutsch", loader.getLabel("locale.name"));
//	}
//}

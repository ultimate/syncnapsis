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
package com.syncnapsis.web;

import javax.servlet.ServletException;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.impl.SessionBasedLocaleProvider;
import com.syncnapsis.providers.impl.ThreadLocalSessionProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 *
 */
@TestExcludesMethods({"get*", "set*", "is*", "afterPropertiesSet"})
public class LocaleFilterTest extends LoggerTestCase
{
	public void testRewriteURL() throws Exception
	{
		LocaleFilter f = new LocaleFilter();

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(new MockHttpSession());
		MockHttpServletResponse response = new MockHttpServletResponse();		
		
		SessionProvider sessionProvider = new ThreadLocalSessionProvider();
		sessionProvider.set(request.getSession());
		f.setSessionProvider(sessionProvider);
		
		SessionBasedLocaleProvider localeProvider = new SessionBasedLocaleProvider();
		localeProvider.setSessionProvider(sessionProvider);
		localeProvider.set(EnumLocale.DE);
		f.setLocaleProvider(localeProvider);
		
		
		String path = "/a/b/c/d";
		
		f.setLocaleDirectoryLevel(0);
		f.setLocaleLowerCase(true);
		assertEquals("/de/a/b/c/d", f.rewriteURL(path, request, response));
		f.setLocaleLowerCase(false);
		assertEquals("/DE/a/b/c/d", f.rewriteURL(path, request, response));
		
		f.setLocaleDirectoryLevel(1);
		f.setLocaleLowerCase(true);
		assertEquals("/a/de/b/c/d", f.rewriteURL(path, request, response));
		f.setLocaleLowerCase(false);
		assertEquals("/a/DE/b/c/d", f.rewriteURL(path, request, response));

		f.setLocaleDirectoryLevel(2);
		f.setLocaleLowerCase(true);
		assertEquals("/a/b/de/c/d", f.rewriteURL(path, request, response));
		f.setLocaleLowerCase(false);
		assertEquals("/a/b/DE/c/d", f.rewriteURL(path, request, response));

		f.setLocaleDirectoryLevel(3);
		f.setLocaleLowerCase(true);
		assertEquals("/a/b/c/de/d", f.rewriteURL(path, request, response));
		f.setLocaleLowerCase(false);
		assertEquals("/a/b/c/DE/d", f.rewriteURL(path, request, response));
		
		f.setLocaleDirectoryLevel(4);
		try
		{
			f.setLocaleLowerCase(true);
			f.rewriteURL(path, request, response);
			
			fail("expected Exception not occurred!");
		}
		catch(ServletException e)
		{
			assertNotNull(e);
		}
		try
		{
			f.setLocaleLowerCase(false);
			f.rewriteURL(path, request, response);
			
			fail("expected Exception not occurred!");
		}
		catch(ServletException e)
		{
			assertNotNull(e);
		}
	}
}

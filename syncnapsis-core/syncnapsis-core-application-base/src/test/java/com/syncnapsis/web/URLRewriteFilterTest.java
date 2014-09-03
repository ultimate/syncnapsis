/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * Test for {@link URLRewriteFilter}
 * 
 * @author ultimate
 */
@TestExcludesMethods({ "*UseInclude", "afterPropertiesSet", "init", "destroy" })
public class URLRewriteFilterTest extends LoggerTestCase
{
	@TestCoversMethods({ "doFilter", "rewriteURL" })
	public void testDoFilter() throws Exception
	{
		final String rewrittenUrl = "/a/new/url";
		URLRewriteFilter filter = new URLRewriteFilter() {
			@Override
			protected String rewriteURL(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException
			{
				return rewrittenUrl;
			}
		};
		
		final RequestDispatcher mockDispatcher = mockContext.mock(RequestDispatcher.class);

		final MockHttpServletRequest request = new MockHttpServletRequest() {
			@Override
			public RequestDispatcher getRequestDispatcher(String path)
			{
				assertEquals(rewrittenUrl, path);
				return mockDispatcher;
			}
		};
		request.setServletPath("/a/path");
		final MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		
		filter.setUseInclude(false);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDispatcher).forward(request, response);
			}
		});
		
		filter.doFilter(request, response, chain);
		mockContext.assertIsSatisfied();
		
		filter.setUseInclude(true);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDispatcher).include(request, response);
			}
		});
		
		filter.doFilter(request, response, chain);
		mockContext.assertIsSatisfied();
	}
}

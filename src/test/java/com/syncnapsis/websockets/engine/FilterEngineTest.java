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
package com.syncnapsis.websockets.engine;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.mock.web.MockFilterConfig;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * Test for {@link FilterEngine}
 * 
 * @author ultimate
 */
@TestExcludesMethods({"destroy"})
public class FilterEngineTest extends LoggerTestCase
{
	public void testInit() throws Exception
	{
		FilterEngine e = new FilterEngine() {
			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
			{
			}
		};
		FilterConfig config = new MockFilterConfig();
		e.init(config);
		assertSame(config, e.filterConfig);
	}
}
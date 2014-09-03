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
package com.syncnapsis.websockets.engine;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.mock.web.MockServletConfig;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * Test for {@link ServletEngine}
 * 
 * @author ultimate
 */
@TestExcludesMethods({ "destroy" })
public class ServletEngineTest extends LoggerTestCase
{
	@TestCoversMethods({ "init", "getServletConfig" })
	public void testInit() throws Exception
	{
		ServletEngine e = new ServletEngine() {
			@Override
			public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
			{
			}
		};
		ServletConfig config = new MockServletConfig();
		e.init(config);
		assertSame(config, e.getServletConfig());
	}

	public void testGetServletInfo() throws Exception
	{
		ServletEngine e = new ServletEngine() {
			@Override
			public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
			{
			}
		};
		assertNotNull(e.getServletInfo());
	}
}
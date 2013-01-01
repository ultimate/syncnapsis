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

import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class ServletUtilTest extends LoggerTestCase
{
	@TestCoversMethods("toString")
	public void testToString_Request()
	{
		String hn = "aHeaderName";
		String hv = "aHeaderValue";
		String pn = "aParameterName";
		String pv = "aParameterValue";

		String content = "aContent";

		MockHttpServletRequest req = new MockHttpServletRequest();
		req.setServerName("aServerName");
		req.addHeader(hn, hv);
		req.addParameter(pn, pv);
		req.setContent(content.getBytes());

		String s = ServletUtil.toString(req);

		assertNotNull(s);

		assertTrue(s.contains(req.getServerName()));
		assertTrue(s.contains(hn));
		assertTrue(s.contains(hv));
		assertTrue(s.contains(pn));
		assertTrue(s.contains(pv));

		assertEquals(req.getInputStream().markSupported(), s.contains(content));
	}

	@TestCoversMethods("toString")
	public void testToString_Response()
	{
		final String hn = "aHeaderName";
		String hv = "aHeaderValue";

		MockHttpServletResponse resp = new MockHttpServletResponse() {
			// fix AbstractMethodError ?!
			@Override
			public Set<String> getHeaderNames()
			{
				return super.getHeaderNames();
			}
		};
		resp.setStatus(999);
		resp.addHeader(hn, hv);

		String s = ServletUtil.toString(resp);

		assertNotNull(s);

		assertTrue(s.contains("" + resp.getStatus()));
		assertTrue(s.contains(hn));
		assertTrue(s.contains(hv));
	}

	public void testInsertDirectory() throws Exception
	{
		String path = "/a/b/c/d";
		String directory = "x";

		assertEquals("/x/a/b/c/d", ServletUtil.insertDirectory(path, directory, 0));
		assertEquals("/a/x/b/c/d", ServletUtil.insertDirectory(path, directory, 1));
		assertEquals("/a/b/x/c/d", ServletUtil.insertDirectory(path, directory, 2));
		assertEquals("/a/b/c/x/d", ServletUtil.insertDirectory(path, directory, 3));

		try
		{
			ServletUtil.insertDirectory(path, directory, 4);

			fail("expected Exception not occurred!");
		}
		catch(ServletException e)
		{
			assertNotNull(e);
		}
	}

	public void testCountDirectories() throws Exception
	{
		assertEquals(1, ServletUtil.countDirectories("/a"));
		assertEquals(1, ServletUtil.countDirectories("//a"));
		assertEquals(1, ServletUtil.countDirectories("/a/"));
		assertEquals(1, ServletUtil.countDirectories("//a/"));
		assertEquals(1, ServletUtil.countDirectories("//a//"));

		assertEquals(2, ServletUtil.countDirectories("/a/b"));
		assertEquals(2, ServletUtil.countDirectories("//a//b"));
		assertEquals(2, ServletUtil.countDirectories("/a/b/"));
		assertEquals(2, ServletUtil.countDirectories("//a//b/"));
		assertEquals(2, ServletUtil.countDirectories("//a//b//"));
	}
}

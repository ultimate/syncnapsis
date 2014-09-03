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
package com.syncnapsis.utils;

import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

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

	@TestCoversMethods("toString")
	public void testToString_Session()
	{
		String an = "anAttributeName";
		String av = "anAttributeValue";

		int interval = 12345;

		MockHttpSession session = new MockHttpSession();
		session.setMaxInactiveInterval(interval);
		session.setAttribute(an, av);

		String s = ServletUtil.toString(session);

		assertNotNull(s);

		assertTrue(s.contains("" + interval));
		assertTrue(s.contains(an));
		assertTrue(s.contains(av));
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
	
	public void testCopyRequestClientInfo() throws Exception
	{
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpSession sess = new MockHttpSession();
		
		req.addHeader(ServletUtil.ATTRIBUTE_USER_AGENT, "my browser");
		req.setRemoteHost("1.2.3.4");
		req.setRemotePort(1234);
		
		ServletUtil.copyRequestClientInfo(req, sess);
		
		assertEquals(sess.getAttribute(ServletUtil.ATTRIBUTE_REMOTE_HOST), req.getRemoteHost());
		assertEquals(sess.getAttribute(ServletUtil.ATTRIBUTE_REMOTE_PORT), req.getRemotePort());
		assertEquals(sess.getAttribute(ServletUtil.ATTRIBUTE_USER_AGENT), req.getHeader(ServletUtil.ATTRIBUTE_USER_AGENT));
	}
	
	public void testGetRemoteAddr() throws Exception
	{
		MockHttpSession sess = new MockHttpSession();
		
		String ip = "1.2.3.4";
		String name = "www.example.com";
		
		sess.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_ADDR, ip);
		sess.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_HOST, ip);
		
		assertEquals(ip, ServletUtil.getRemoteAddr(sess));
		
		sess.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_HOST, name);
		
		assertEquals(ip + " (" + name + ")", ServletUtil.getRemoteAddr(sess));
	}
	
	public void testGetUserAgent() throws Exception
	{
		MockHttpSession sess = new MockHttpSession();
		
		String agent = "my browser";
		
		sess.setAttribute(ServletUtil.ATTRIBUTE_USER_AGENT, agent);
		
		assertEquals(agent, ServletUtil.getUserAgent(sess));
	}
	
	public void testHeaderContainsValue() throws Exception
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		String header = "my-test-header";
		String value1 = "value";
		String value2 = "VaLuE"; 
		String value3 = "VALUE"; 
		String value4a = "val";
		String value4b = "4";
		String value4 = value4a + ", " + value4b;
		String valueN = "nothing"; 
		
		request.addHeader(header, value1);
		request.addHeader(header, value2);
		request.addHeader(header, value4);
		
		assertTrue(ServletUtil.headerContainsValue(request, header, value1, true));
		assertTrue(ServletUtil.headerContainsValue(request, header, value1, false));
		
		assertTrue(ServletUtil.headerContainsValue(request, header, value2, true));
		assertTrue(ServletUtil.headerContainsValue(request, header, value2, false));
		
		assertTrue(ServletUtil.headerContainsValue(request, header, value3, true));
		assertFalse(ServletUtil.headerContainsValue(request, header, value3, false));
		
		assertTrue(ServletUtil.headerContainsValue(request, header, value4, true));
		assertTrue(ServletUtil.headerContainsValue(request, header, value4, true));
		assertTrue(ServletUtil.headerContainsValue(request, header, value4a, true));
		assertTrue(ServletUtil.headerContainsValue(request, header, value4a, true));
		assertTrue(ServletUtil.headerContainsValue(request, header, value4b, true));
		assertTrue(ServletUtil.headerContainsValue(request, header, value4b, true));
		
		assertFalse(ServletUtil.headerContainsValue(request, header, valueN, true));
		assertFalse(ServletUtil.headerContainsValue(request, header, valueN, false));
	}
}

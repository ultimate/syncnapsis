package com.syncnapsis.utils;

import com.syncnapsis.tests.LoggerTestCase;
import org.springframework.mock.web.MockHttpServletRequest;

public class ServletUtilTest extends LoggerTestCase
{
	public void testToString()
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
}

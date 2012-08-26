package com.syncnapsis.utils.spring;

import com.syncnapsis.mock.MockFilter;
import com.syncnapsis.mock.MockServlet;
import com.syncnapsis.mock.MockWebApplicationContextWrapper;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.context.WebApplicationContext;

@TestCoversClasses({ BeanProxy.class, FilterToBeanProxy.class, ServletToBeanProxy.class })
@TestExcludesMethods({ "destroy" })
public class BeanProxyTest extends BaseSpringContextTestCase
{
	private MockFilter	mockFilter;
	private MockServlet	mockServlet;

	@TestCoversMethods({ "getContext", "delegate", "getDelegate", "init", "doFilter" })
	public void testFilterToBeanProxy() throws Exception
	{
		MockWebApplicationContextWrapper webApplicationContext = new MockWebApplicationContextWrapper();
		webApplicationContext.setApplicationContext(applicationContext);

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();

		MockFilterConfig config = new MockFilterConfig();
		config.addInitParameter("targetBean", "mockFilter");
		config.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);

		FilterToBeanProxy proxy = new FilterToBeanProxy();
		proxy.init(config);
		proxy.doFilter(request, response, chain);

		assertNotNull(proxy.getDelegate());
		assertSame(mockFilter, proxy.getDelegate());

		assertSame(config, mockFilter.getFilterConfig());
		assertSame(request, mockFilter.getLastRequest());
		assertSame(response, mockFilter.getLastResponse());
		assertSame(chain, mockFilter.getLastChain());
	}

	@TestCoversMethods({ "getContext", "delegate", "getDelegate", "init", "service", "getServletConfig", "getServletInfo" })
	public void testServletToBeanProxy() throws Exception
	{
		MockWebApplicationContextWrapper webApplicationContext = new MockWebApplicationContextWrapper();
		webApplicationContext.setApplicationContext(applicationContext);

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		MockServletConfig config = new MockServletConfig();
		config.addInitParameter("targetBean", "mockServlet");
		config.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);

		ServletToBeanProxy proxy = new ServletToBeanProxy();
		proxy.init(config);
		proxy.service(request, response);

		assertNotNull(proxy.getDelegate());
		assertSame(mockServlet, proxy.getDelegate());

		assertSame(config, mockServlet.getServletConfig());
		assertSame(request, mockServlet.getLastRequest());
		assertSame(response, mockServlet.getLastResponse());

		assertEquals(mockServlet.getServletInfo(), proxy.getServletInfo());
	}
}

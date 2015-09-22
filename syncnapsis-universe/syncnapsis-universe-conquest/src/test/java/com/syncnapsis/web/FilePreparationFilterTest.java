package com.syncnapsis.web;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.web.FilePreparationFilter;

@TestExcludesMethods({ "prepare" })
public class FilePreparationFilterTest extends LoggerTestCase
{
	@TestCoversMethods("doFilter")
	public void testDoFilter_withoutFileFilter() throws Exception
	{
		final AtomicBoolean prepareCalled = new AtomicBoolean();
		final AtomicBoolean preparationRequired = new AtomicBoolean();

		FilePreparationFilter fpf = new FilePreparationFilter(null) {
			@Override
			public boolean prepare(File realFile, File servletFile)
			{
				prepareCalled.set(true);
				return true;
			}

			@Override
			public boolean requiresPreparation(File realFile, File servletFile)
			{
				return preparationRequired.get();
			}
		};

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response;
		MockFilterChain chain;
		

		request.setMethod("GET");

		// FileFilter = null, req = false
		chain = new MockFilterChain();
		response = new MockHttpServletResponse();
		prepareCalled.set(false);
		preparationRequired.set(false);
		fpf.doFilter(request, response, chain);
		assertFalse(prepareCalled.get());
		
		// FileFilter = null, req = true
		chain = new MockFilterChain();
		response = new MockHttpServletResponse();
		prepareCalled.set(false);
		preparationRequired.set(true);
		fpf.doFilter(request, response, chain);
		assertTrue(prepareCalled.get());

		request.setMethod("POST");

		// post
		chain = new MockFilterChain();
		response = new MockHttpServletResponse();
		prepareCalled.set(false);
		fpf.doFilter(request, response, chain);
		assertEquals(HttpServletResponse.SC_METHOD_NOT_ALLOWED, response.getStatus());
	}

	@TestCoversMethods("doFilter")
	public void testDoFilter_withFileFilter() throws Exception
	{
		final AtomicBoolean prepareCalled = new AtomicBoolean();
		final AtomicBoolean preparationRequired = new AtomicBoolean();
		final AtomicBoolean fileFilterAccept = new AtomicBoolean();
		
		FileFilter ff = new FileFilter() {
			@Override
			public boolean accept(File pathname)
			{
				return fileFilterAccept.get();
			}
		};
		
		FilePreparationFilter fpf = new FilePreparationFilter(ff) {
			@Override
			public boolean prepare(File realFile, File servletFile)
			{
				prepareCalled.set(true);
				return true;
			}
			
			@Override
			public boolean requiresPreparation(File realFile, File servletFile)
			{
				return preparationRequired.get();
			}
		};
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response;
		MockFilterChain chain;
		
		
		request.setMethod("GET");
		
		// FileFilter = false, req = false
		chain = new MockFilterChain();
		response = new MockHttpServletResponse();
		prepareCalled.set(false);
		preparationRequired.set(false);
		fileFilterAccept.set(false);
		fpf.doFilter(request, response, chain);
		assertFalse(prepareCalled.get());

		// FileFilter = false, req = true
		chain = new MockFilterChain();
		response = new MockHttpServletResponse();
		prepareCalled.set(false);
		preparationRequired.set(true);
		fileFilterAccept.set(false);
		fpf.doFilter(request, response, chain);
		assertFalse(prepareCalled.get());

		// FileFilter = true, req = false
		chain = new MockFilterChain();
		response = new MockHttpServletResponse();
		prepareCalled.set(false);
		preparationRequired.set(false);
		fileFilterAccept.set(true);
		fpf.doFilter(request, response, chain);
		assertFalse(prepareCalled.get());
		
		// FileFilter = true, req = true
		chain = new MockFilterChain();
		response = new MockHttpServletResponse();
		prepareCalled.set(false);
		preparationRequired.set(true);
		fileFilterAccept.set(true);
		fpf.doFilter(request, response, chain);
		assertTrue(prepareCalled.get());
		
		request.setParameter(FilePreparationFilter.PARAM_FORCE, "true");

		// FileFilter = false, req = false, force = false
		chain = new MockFilterChain();
		response = new MockHttpServletResponse();
		prepareCalled.set(false);
		preparationRequired.set(false);
		fileFilterAccept.set(false);
		fpf.doFilter(request, response, chain);
		assertFalse(prepareCalled.get());

		// FileFilter = true, req = false, force = true
		chain = new MockFilterChain();
		response = new MockHttpServletResponse();
		prepareCalled.set(false);
		preparationRequired.set(false);
		fileFilterAccept.set(true);
		fpf.doFilter(request, response, chain);
		assertTrue(prepareCalled.get());
		
		request.setMethod("POST");
		
		// post
		chain = new MockFilterChain();
		response = new MockHttpServletResponse();
		prepareCalled.set(false);
		fpf.doFilter(request, response, chain);
		assertEquals(HttpServletResponse.SC_METHOD_NOT_ALLOWED, response.getStatus());
	}

	public void testRequiresPreparation() throws Exception
	{
		FilePreparationFilter fpf = new FilePreparationFilter(null) {
			@Override
			public boolean prepare(File realFile, File servletFile)
			{
				return true;
			}
		};

		String file = "index.html";
		String folder = "target/webapp";

		File existingFile = new File(folder + "/" + file);
		File nonExistingFile = new File(folder + "/" + "main.html");

		File servletFile = new File(file);

		logger.debug(existingFile.getAbsolutePath());
		assertFalse(nonExistingFile.exists());
		assertTrue(fpf.requiresPreparation(nonExistingFile, servletFile));

		logger.debug(nonExistingFile.getAbsolutePath());
		assertTrue(existingFile.exists());
		assertFalse(fpf.requiresPreparation(existingFile, servletFile));
	}
}

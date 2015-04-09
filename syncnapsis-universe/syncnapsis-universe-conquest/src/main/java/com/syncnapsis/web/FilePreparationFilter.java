package com.syncnapsis.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.syncnapsis.websockets.engine.FilterEngine;

/**
 * Filter which prepares files of a given type within a specified path.<br>
 * e.g. if /galaxy/1234.json is requested the filter will call prepare("1234") for preparation.
 * 
 * @author ultimate
 */
public abstract class FilePreparationFilter extends FilterEngine
{
	/**
	 * The file path for which to prepare files.
	 */
	protected String	filePath;
	/**
	 * The file type for which to prepare files
	 */
	protected String	fileType;

	/**
	 * Construct a new {@link FilePreparationFilter}
	 * 
	 * @param filePath - The file path for which to prepare files.
	 * @param fileType - The file type for which to prepare files.
	 */
	public FilePreparationFilter(String filePath, String fileType)
	{
		super();
		this.filePath = filePath;
		if(!fileType.startsWith("."))
			fileType = '.' + fileType;
		this.fileType = fileType;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		if(!(request instanceof HttpServletRequest))
		{
			throw new ServletException("Can only process HttpServletRequest");
		}
		if(!(response instanceof HttpServletResponse))
		{
			throw new ServletException("Can only process HttpServletResponse");
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if(!httpRequest.getMethod().equals("GET"))
		{
			httpResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}

		logger.debug("requesting: " + httpRequest.getServletPath());

		String fullName = httpRequest.getServletPath();
		if(fullName.startsWith(filePath) && fullName.endsWith(fileType))
		{
			int start = filePath.length();
			int end = fullName.length() - fileType.length();
			String fileName =  fullName.substring(start, end);
			boolean prepared = prepare(fileName);
			logger.info("file '" + fileName + "' successfully prepared? " + prepared);
		}
		else
		{
			logger.debug("file does not match path and type");
		}

		chain.doFilter(httpRequest, httpResponse);
	}
	
	public abstract boolean prepare(String fileName);
}

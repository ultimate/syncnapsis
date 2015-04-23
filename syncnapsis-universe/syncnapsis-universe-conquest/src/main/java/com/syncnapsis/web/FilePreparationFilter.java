package com.syncnapsis.web;

import java.io.File;
import java.io.FileFilter;
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
	 * An optional {@link FileFilter} defining which files to handle
	 */
	protected FileFilter	fileFilter;

	/**
	 * Construct a new {@link FilePreparationFilter}
	 * 
	 * @param fileFilter - An optional {@link FileFilter} defining which files to handle
	 */
	public FilePreparationFilter(FileFilter fileFilter)
	{
		super();
		this.fileFilter = fileFilter;
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

		File realFile = new File(httpRequest.getServletContext().getRealPath(httpRequest.getServletPath()));
		File servletFile = new File(httpRequest.getServletPath());

		logger.debug("requesting: " + httpRequest.getServletPath() + " -> " + realFile.getPath());
		logger.debug("path:   " + realFile.getPath());
		logger.debug("parent: " + realFile.getParent());
		logger.debug("name:   " + realFile.getName());

		if(fileFilter != null && fileFilter.accept(servletFile))
		{
			if(requiresPreparation(realFile, servletFile))
			{
				if(!realFile.getParentFile().exists())
				{
					logger.debug("creating parent directory...");
					realFile.getParentFile().mkdirs();
				}

				boolean prepared = prepare(realFile, servletFile);
				if(prepared)
					logger.info("file '" + realFile.getAbsolutePath() + "' successfully prepared!");
				else
					logger.info("file '" + realFile.getAbsolutePath() + "' could not be prepared!");
			}
			else
			{
				logger.debug("file does");
			}
		}
		else
		{
			logger.debug("file does not match the given FileFilter");
		}

		chain.doFilter(httpRequest, httpResponse);
	}

	/**
	 * Check whether the given files requires preparation.<br>
	 * Note: in order to be able the file on the file system it is passed with the real file path
	 * and with the servlet relative path.<br>
	 * By default this method returns false if the file exists, and true if the file does not exist.
	 * 
	 * @param file - the file to prepare
	 * @return true if the file was prepared or already was prepared, false otherwise
	 */
	public boolean requiresPreparation(File realFile, File servletFile)
	{
		return !realFile.exists();
	}

	/**
	 * Prepare the given file.<br>
	 * Note: in order to be able to access the file on the file system it is passed with the real
	 * file path and with the servlet relative path.
	 * 
	 * @param file - the file to prepare
	 * @return true if the file was prepared or already was prepared, false otherwise
	 */
	public abstract boolean prepare(File realFile, File servletFile);
}

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

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class offering debug methods for HttpRequests and HttpResponses.
 * 
 * @author ultimate
 */
public abstract class ServletUtil
{
	/**
	 * The directory separator inside URLs
	 */
	protected static final char				DIRECTORY_SEPARATOR	= '/';
	/**
	 * Logger-Instance
	 */
	@SuppressWarnings("unused")
	private static transient final Logger	logger				= LoggerFactory.getLogger(ServletUtil.class);

	/**
	 * Create a String representing all relevant Information of the given HttpServletRequest.
	 * 
	 * @param req - the request
	 * @return the request info as a String
	 */
	public static String toString(HttpServletRequest req)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("HttpServletRequest:   " + req.getClass().getName() + "\n");
		sb.append("  AuthType:           " + req.getAuthType() + "\n");
		sb.append("  CharacterEncoding:  " + req.getCharacterEncoding() + "\n");
		sb.append("  ContentLength:      " + req.getContentLength() + "\n");
		sb.append("  ContentType:        " + req.getContentType() + "\n");
		sb.append("  ContextPath:        " + req.getContextPath() + "\n");
		sb.append("  LocalAddr:          " + req.getLocalAddr() + "\n");
		sb.append("  LocalName:          " + req.getLocalName() + "\n");
		sb.append("  LocalPort:          " + req.getLocalPort() + "\n");
		sb.append("  Method:             " + req.getMethod() + "\n");
		sb.append("  PathInfo:           " + req.getPathInfo() + "\n");
		sb.append("  PathTranslated:     " + req.getPathTranslated() + "\n");
		sb.append("  Protocol:           " + req.getProtocol() + "\n");
		sb.append("  QueryString:        " + req.getQueryString() + "\n");
		sb.append("  RemoteAddr:         " + req.getRemoteAddr() + "\n");
		sb.append("  RemoteHost:         " + req.getRemoteHost() + "\n");
		sb.append("  RemotePort:         " + req.getRemotePort() + "\n");
		sb.append("  RemoteUser:         " + req.getRemoteUser() + "\n");
		sb.append("  RequestedSessionId: " + req.getRequestedSessionId() + "\n");
		sb.append("  RequestURI:         " + req.getRequestURI() + "\n");
		sb.append("  Scheme:             " + req.getScheme() + "\n");
		sb.append("  ServerName:         " + req.getServerName() + "\n");
		sb.append("  ServerPort:         " + req.getServerPort() + "\n");
		sb.append("  ServletPath:        " + req.getServletPath() + "\n");
		sb.append("  Headers:\n");
		Enumeration<String> headerNames = req.getHeaderNames();
		String h;
		while(headerNames.hasMoreElements())
		{
			h = headerNames.nextElement();
			sb.append("    " + h + ":" + req.getHeader(h) + "\n");
		}
		sb.append("  Parameters:\n");
		Enumeration<String> parameterNames = req.getParameterNames();
		String p;
		while(parameterNames.hasMoreElements())
		{
			p = parameterNames.nextElement();
			sb.append("    " + p + ":" + req.getParameter(p) + "\n");
		}
		sb.append("  Content:\n");
		try
		{
			int readlimit = 100;
			if(req.getInputStream().markSupported())
			{
				req.getInputStream().mark(readlimit);
				int c;
				int read = 0;
				while((c = req.getInputStream().read()) != -1)
				{
					read++;
					sb.append((char) c);
					if(read >= readlimit)
					{
						sb.append("[...]");
						break;
					}
				}
				req.getInputStream().reset();
				if(read == 0)
					sb.append("[--no content--]");
			}
			else
			{
				sb.append("[--content unreadable: mark not supported--]");
			}
		}
		catch(IOException e)
		{
			sb.append("[--content unreadable: exception occurred--]");
		}

		return sb.toString();
	}

	/**
	 * Insert a (sub)-directory in the given path at the required directory level.<br>
	 * e.g.<br>
	 * <ul>
	 * <li>transform /a/b/c with directory x and level 0 --> /x/a/b/c</li>
	 * <li>transform /a/b/c with directory x and level 1 --> /a/x/b/c</li>
	 * <li>transform /a/b/c with directory x and level 2 --> /a/b/x/c</li>
	 * </ul>
	 * 
	 * @param path - the path to transform
	 * @param directory - the (sub)-directory to insert
	 * @param directoryLevel - the level to insert the (sub)-directory at
	 * @return the tranformed path
	 * @throws ServletException - if transforming fails (e.g. directoryLevel is higher than the
	 *             number of available directories
	 */
	public static String insertDirectory(String path, String directory, int directoryLevel) throws ServletException
	{
		int insertIndex = 0;
		for(int i = 0; i < directoryLevel && insertIndex >= 0; i++)
		{
			insertIndex = path.indexOf(DIRECTORY_SEPARATOR, insertIndex + 1);
		}
		if(insertIndex == -1)
			throw new ServletException("directoryLevel (" + directoryLevel + ")to high for given url: " + path);
		StringBuilder sb = new StringBuilder();
		sb.append(path.substring(0, insertIndex));
		sb.append(DIRECTORY_SEPARATOR);
		sb.append(directory);
		sb.append(path.substring(insertIndex));
		return sb.toString();
	}

	/**
	 * Count the number of directories in the given path
	 * 
	 * @param path - the path to scan
	 * @return the amount of directories
	 */
	public static int countDirectories(String path)
	{
		int dirs = 0;
		for(int i = 1; i < path.length(); i++)
		{
			if(path.charAt(i) != DIRECTORY_SEPARATOR && path.charAt(i-1) == DIRECTORY_SEPARATOR)
				dirs++;
		}
		return dirs;
	}
}

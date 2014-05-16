/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	protected static final char				DIRECTORY_SEPARATOR		= '/';
	/**
	 * Session attribute name for information copied from the request: RemoteAddr
	 */
	public static final String				ATTRIBUTE_REMOTE_ADDR	= "RemoteAddr";
	/**
	 * Session attribute name for information copied from the request: RemoteHost
	 */
	public static final String				ATTRIBUTE_REMOTE_HOST	= "RemoteHost";
	/**
	 * Session attribute name for information copied from the request: RemotePort
	 */
	public static final String				ATTRIBUTE_REMOTE_PORT	= "RemotePort";
	/**
	 * Session attribute name for information copied from the request: User-Agent
	 */
	public static final String				ATTRIBUTE_USER_AGENT	= "User-Agent";
	
	/**
	 * Separators for multi in one headers
	 */
	public static final String				HEADER_SEPARATOR	= " \t\n\r\f,";
	/**
	 * Logger-Instance
	 */
	@SuppressWarnings("unused")
	private static transient final Logger	logger					= LoggerFactory.getLogger(ServletUtil.class);

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
		Enumeration<String> headerValues;
		while(headerNames.hasMoreElements())
		{
			h = headerNames.nextElement();
			headerValues = req.getHeaders(h);
			sb.append("    " + h + ":");
			while(headerValues.hasMoreElements())
				sb.append(" " + headerValues.nextElement());
			sb.append("\n");
		}
		sb.append("  Parameters:\n");
		Enumeration<String> parameterNames = req.getParameterNames();
		String p;
		String[] parameters;
		while(parameterNames.hasMoreElements())
		{
			p = parameterNames.nextElement();
			parameters = req.getParameterValues(p);
			sb.append("    " + p + ":");
			for(String parameter: parameters)
				sb.append(" " + parameter);
			sb.append("\n");
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
	 * Create a String representing all relevant Information of the given HttpServletResponse.
	 * 
	 * @param resp - the response
	 * @return the request info as a String
	 */
	public static String toString(HttpServletResponse resp)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("HttpServletResponse:  " + resp.getClass().getName() + "\n");
		sb.append("  BufferSize:         " + resp.getBufferSize() + "\n");
		sb.append("  CharacterEncoding:  " + resp.getCharacterEncoding() + "\n");
		sb.append("  ContentType:        " + resp.getContentType() + "\n");
		sb.append("  Locale:             " + resp.getLocale() + "\n");
		sb.append("  Status:             " + resp.getStatus() + "\n");
		sb.append("  Headers:\n");
		Collection<String> headerNames = resp.getHeaderNames();
		for(String h : headerNames)
		{
			sb.append("    " + h + ": " + resp.getHeader(h) + "\n");
		}

		return sb.toString();
	}

	/**
	 * Create a String representing all relevant Information of the given HttpSession
	 * 
	 * @param session - the session
	 * @return the session info as a String
	 */
	public static String toString(HttpSession session)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("HttpSession:            " + session.getClass().getName() + "\n");
		sb.append("  CreationTime          " + session.getCreationTime() + "\n");
		sb.append("  Id:                   " + session.getId() + "\n");
		sb.append("  LastAccessedTime:     " + session.getLastAccessedTime() + "\n");
		sb.append("  MaxInactiveInterval:  " + session.getMaxInactiveInterval() + "\n");
		sb.append("  ServletContext:       " + session.getServletContext() + "\n");
		sb.append("  Attributes:\n");
		Enumeration<String> attributeNames = session.getAttributeNames();
		String a;
		while(attributeNames.hasMoreElements())
		{
			a = attributeNames.nextElement();
			sb.append("    " + a + ": " + session.getAttribute(a) + "\n");
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
			if(path.charAt(i) != DIRECTORY_SEPARATOR && path.charAt(i - 1) == DIRECTORY_SEPARATOR)
				dirs++;
		}
		return dirs;
	}

	/**
	 * Copy the relevant info contained in a HttpServletRequest to a HttpSession.<br>
	 * This way the info (e.g. client-address, user agent etc.) will be available for later use
	 * (e.g. in service layer) without the availability of the original request.
	 * 
	 * @param req - the request to copy the info from
	 * @param session - the session to copy the info to
	 */
	public static void copyRequestClientInfo(HttpServletRequest req, HttpSession session)
	{
		if(req.getHeader(ATTRIBUTE_USER_AGENT) != null)
			session.setAttribute(ATTRIBUTE_USER_AGENT, req.getHeader(ATTRIBUTE_USER_AGENT));
		if(req.getRemoteAddr() != null)
			session.setAttribute(ATTRIBUTE_REMOTE_ADDR, req.getRemoteAddr());
		if(req.getRemoteHost() != null)
			session.setAttribute(ATTRIBUTE_REMOTE_HOST, req.getRemoteHost());
		session.setAttribute(ATTRIBUTE_REMOTE_PORT, req.getRemotePort());
	}

	/**
	 * Get the remote address to a HttpSession. The address has to be stored as an attribute before
	 * (using {@link ServletUtil#copyRequestClientInfo(HttpServletRequest, HttpSession)}
	 * 
	 * @param session - the HttpSession
	 * @return the remote address
	 */
	public static String getRemoteAddr(HttpSession session)
	{
		if(session.getAttribute(ATTRIBUTE_REMOTE_ADDR).equals(session.getAttribute(ATTRIBUTE_REMOTE_HOST)))
			return (String) session.getAttribute(ATTRIBUTE_REMOTE_ADDR);
		else
			return session.getAttribute(ATTRIBUTE_REMOTE_ADDR) + " (" + session.getAttribute(ATTRIBUTE_REMOTE_HOST) + ")";
	}

	/**
	 * Get the user agent to a HttpSession. The user agent has to be stored as an attribute before
	 * (using {@link ServletUtil#copyRequestClientInfo(HttpServletRequest, HttpSession)}
	 * 
	 * @param session - the HttpSession
	 * @return the user agent
	 */
	public static String getUserAgent(HttpSession session)
	{
		return (String) session.getAttribute(ATTRIBUTE_USER_AGENT);
	}

	/**
	 * Check wether a header of the given HttpServletRequest contains a required header value.
	 * 
	 * @param request - the HttpServletRequest
	 * @param headerName - the header to check
	 * @param requiredValue - the value to look for
	 * @param ignoreCase - wether to ignore case on comparison
	 * @return true or false
	 */
	public static boolean headerContainsValue(HttpServletRequest request, String headerName, String requiredValue, boolean ignoreCase)
	{
		Enumeration<String> headerValues = request.getHeaders(headerName);
		String value;
		StringTokenizer st;
		String valueToken;
		while(headerValues.hasMoreElements())
		{
			value = headerValues.nextElement();
			if(ignoreCase && requiredValue.equalsIgnoreCase(value))
				return true;
			else if(!ignoreCase && requiredValue.equals(value))
				return true;
			
			// try again for tokens
			st = new StringTokenizer(value, HEADER_SEPARATOR);
			if(st.countTokens() > 1)
			{
				while(st.hasMoreTokens())
				{
					valueToken = st.nextToken();
					if(ignoreCase && requiredValue.equalsIgnoreCase(valueToken))
						return true;
					else if(!ignoreCase && requiredValue.equals(valueToken))
						return true;
				}
			}
		}
		return false;
	}
}

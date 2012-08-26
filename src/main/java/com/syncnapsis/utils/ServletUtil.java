package com.syncnapsis.utils;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * Util class offering debug methods for HttpRequests and HttpResponses.
 * 
 * @author ultimate
 */
public abstract class ServletUtil
{
	// /**
	// * Logger-Instance
	// */
	// private static transient final Logger logger = LoggerFactory.getLogger(ServletUtil.class);

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
}

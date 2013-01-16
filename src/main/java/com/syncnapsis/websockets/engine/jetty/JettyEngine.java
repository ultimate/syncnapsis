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
package com.syncnapsis.websockets.engine.jetty;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketFactory;

import com.syncnapsis.utils.JettyUtil;
import com.syncnapsis.utils.ServletUtil;
import com.syncnapsis.websockets.Protocol;
import com.syncnapsis.websockets.Service;
import com.syncnapsis.websockets.engine.ServletEngine;

/**
 * Einfaches WebSocketServlet, dass bei Aufruf von doWebSocketConnect den Aufruf an den
 * WebSocketManager weiterleitet.
 * 
 * @author ultimate
 */
public class JettyEngine extends ServletEngine implements WebSocketFactory.Acceptor
{
	/**
	 * The WebSocketEngine used to create the WebSocketService
	 */
	private JettyWebSocketServlet	servlet;

	/**
	 * The WebSocketFactory used to create the WebSocket
	 */
	private WebSocketFactory		webSocketFactory;

	/**
	 * Empty default Constructor
	 * If using this, engine has to be set via setter!
	 */
	public JettyEngine()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		this.webSocketFactory = new WebSocketFactory(this);
		this.webSocketFactory.setBufferSize(this.getConnectionProperties().getBufferSize());
		this.webSocketFactory.setMaxIdleTime(this.getConnectionProperties().getMaxIdleTime());
		this.webSocketFactory.setMaxTextMessageSize(this.getConnectionProperties().getMaxTextMessageSize());
		this.webSocketFactory.setMaxBinaryMessageSize(this.getConnectionProperties().getMaxBinaryMessageSize());

		this.servlet = new JettyWebSocketServlet();
		this.servlet.init(config);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#getServletConfig()
	 */
	@Override
	public ServletConfig getServletConfig()
	{
		return this.servlet.getServletConfig();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse)
	 */
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
	{
		this.servlet.service(req, res);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#getServletInfo()
	 */
	@Override
	public String getServletInfo()
	{
		return this.servlet.getServletInfo();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	@Override
	public void destroy()
	{
		this.servlet.destroy();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jetty.websocket.WebSocketFactory.Acceptor#doWebSocketConnect(javax.servlet
	 * .http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String subprotocol)
	{
		logger.debug("incoming request for websocket subprotocol '" + subprotocol + "'");
		Service service = this.getService(subprotocol, request.getHeaders(Protocol.HEADER_SEC_WEBSOCKET_EXTENSIONS));
		if(service != null)
		{
			logger.debug("service created: " + service.getClass());
			ServletUtil.copyRequestClientInfo(request, request.getSession());
			return new JettyWebSocketWrapper(service, manager, request.getSession());
		}
		else
		{
			logger.debug("protocol rejected: '" + subprotocol + "'");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocketFactory.Acceptor#checkOrigin(javax.servlet.http.
	 * HttpServletRequest, java.lang.String)
	 */
	public boolean checkOrigin(HttpServletRequest request, String origin)
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.engine.ServletEngine#start0()
	 */
	@Override
	public void start0()
	{
		super.start0();
		if(!JettyUtil.checkJettyVersion())
			JettyUtil.printError(getClass());
	}

	/**
	 * Internal WebSocketServlet to be used to handle the connection via Jetty
	 * 
	 * @author ultimate
	 */
	private class JettyWebSocketServlet extends HttpServlet
	{
		/**
		 * Default serialVersionUID
		 */
		private static final long	serialVersionUID	= 1L;

		/*
		 * (non-Javadoc)
		 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
		 * javax.servlet.http.HttpServletResponse)
		 */
		@Override
		protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			if(JettyEngine.this.webSocketFactory.acceptWebSocket(request, response) || response.isCommitted())
				return;
			super.service(request, response);
		}
	}
}
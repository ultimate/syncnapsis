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
package com.syncnapsis.websockets.engine.http;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.syncnapsis.websockets.Message;
import com.syncnapsis.websockets.Protocol;
import com.syncnapsis.websockets.Service;
import com.syncnapsis.websockets.engine.FilterEngine;

/**
 * Engine enabling WebSocket support via HTTP fallback.
 * In coming requests are treated like websocket messages and forwarded to the corresponding
 * Service.
 * 
 * @author ultimate
 */
public class HttpFallbackEngine extends FilterEngine
{
	/**
	 * Header name for the protocol name in the http request.
	 */
	private static final String							HEADER_PROTOCOL				= "protocol";
	/**
	 * Header name for the extension names in the http request.
	 */
	private static final String							HEADER_EXTENSIONS			= "extensions";
	/**
	 * Header name for the action in the http request.
	 * Action can be [connect|disconnect|message]
	 */
	private static final String							HEADER_ACTION				= "action";
	/**
	 * The connect value fot the action parameter.
	 */
	private static final String							ACTION_CONNECT				= "connect";
	/**
	 * The disconnect value fot the action parameter.
	 */
	private static final String							ACTION_DISCONNECT			= "disconnect";
	/**
	 * The message value fot the action parameter.
	 */
	private static final String							ACTION_MESSAGE				= "message";
	/**
	 * The header name for specifying how many more messages are buffered
	 */
	private static final String							HEADER_MESSAGES_BUFFERED	= "Messages-Buffered";
	/**
	 * The header name for specifying the op code
	 */
	private static final String							HEADER_OP_CODE				= "Op-Code";
	/**
	 * The header name for specifying the op code
	 */
	private static final String							HEADER_CLOSE_CODE			= "Close-Code";
	/**
	 * The header name for specifying the precondition on response
	 */
	private static final String							HEADER_PRECONDITION			= "precondition";
	/**
	 * The connected value for the precondition header
	 */
	private static final String							PRECONDITION_CONNECTED		= "connected";
	/**
	 * The disconnected value for the precondition header
	 */
	private static final String							PRECONDITION_DISCONNECTED	= "disconnected";

	/**
	 * Protocol instance for all established Http Fallback Connections.
	 */
	private static final Protocol						httpProtocol				= new HttpProtocol();

	/**
	 * Internal list of all active Connections
	 */
	private Map<String, Map<String, HttpConnection>>	connections;

	/**
	 * Empty default Constructor
	 * If using this, engine has to be set via setter!
	 */
	public HttpFallbackEngine()
	{
		this.connections = new TreeMap<String, Map<String, HttpConnection>>();
	}

	/**
	 * Get the Connection for a given subprotocol and session.
	 * 
	 * @param subprotocol - the subprotocol name
	 * @param sessionId - the session ID
	 * @return the connection or null if no connection found
	 */
	protected HttpConnection getConnection(String subprotocol, String sessionId)
	{
		if(this.connections.get(subprotocol) == null)
			return null;
		return this.connections.get(subprotocol).get(sessionId);
	}

	/**
	 * Add a Connection for a given subprotocol and session
	 * 
	 * @param subprotocol - the subprotocol name
	 * @param sessionId - the session ID
	 * @param connection - the Connection to add
	 */
	protected void addConnection(String subprotocol, String sessionId, HttpConnection connection)
	{
		if(this.connections.get(subprotocol) == null)
			this.connections.put(subprotocol, new TreeMap<String, HttpConnection>());
		this.connections.get(subprotocol).put(sessionId, connection);
	}

	/**
	 * Remove the Connection for a given subprotocol and session
	 * 
	 * @param subprotocol - the subprotocol name
	 * @param sessionId - the session ID
	 */
	protected HttpConnection removeConnection(String subprotocol, String sessionId)
	{
		if(this.connections.get(subprotocol) != null)
			return this.connections.get(subprotocol).remove(sessionId);
		return null;
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

		if(isWebSocketHandshake(httpRequest, httpResponse))
		{
			logger.debug("incoming websocket handshake");
			if(chain != null)
				chain.doFilter(httpRequest, httpResponse);
		}
		else
		{
			if(httpRequest.getMethod().equals("POST") || httpRequest.getMethod().equals("GET"))
			{
				doFallback(httpRequest, httpResponse);
			}
			else
			{
				httpResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Only POST and GET are allowed");
				return;
			}
		}
	}

	/**
	 * Do the HTTP fallback for this websocket.
	 * Via POST new messages are send to the server and WebSocket can be connected or disconnected.
	 * Via GET buffered messages can be fetched.
	 * 
	 * @param req - the request
	 * @param resp - the response
	 */
	protected void doFallback(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String subprotocol = req.getHeader(HEADER_PROTOCOL);

		if(subprotocol == null)
		{
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Protocol name not specified!");
			return;
		}

		String sessionId = req.getSession().getId();
		HttpConnection connection = getConnection(subprotocol, sessionId);
		
		this.manager.getSessionProvider().set(req.getSession());
		this.manager.getConnectionProvider().set(connection);

		if(req.getMethod().equals("POST"))
		{
			String action = req.getHeader(HEADER_ACTION);

			if(ACTION_MESSAGE.equals(action))
			{
				logger.debug(connection != null ? connection.getId() : "???new???@" + subprotocol + " - sending message");
				if(connection != null)
				{
					handleMessage(connection, req);

					resp.setStatus(HttpServletResponse.SC_OK);
				}
				else
				{
					resp.addHeader(HEADER_PRECONDITION, PRECONDITION_DISCONNECTED);
					resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Not connected!");
				}

			}
			else if(ACTION_CONNECT.equals(action))
			{
				logger.debug(connection != null ? connection.getId() : "???new???@" + subprotocol + " - connecting");
				if(connection == null)
				{
					Enumeration<String> extensions = req.getHeaders(HEADER_EXTENSIONS);

					doConnect(sessionId, subprotocol, extensions, req.getSession());
					resp.setStatus(HttpServletResponse.SC_OK);
				}
				else
				{
					resp.addHeader(HEADER_PRECONDITION, PRECONDITION_CONNECTED);
					resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Trying to connect a connection that is already connected!");
				}
			}
			else if(ACTION_DISCONNECT.equals(action))
			{
				logger.debug(connection != null ? connection.getId() : "???new???@" + subprotocol + " - disconnecting");
				if(connection != null)
				{
					doDisconnect(connection, req);
				}
				else
				{
					resp.addHeader(HEADER_PRECONDITION, PRECONDITION_DISCONNECTED);
					resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Trying to disconnect a connection that is not connected!");
				}
			}
			else
			{
				logger.debug(connection != null ? connection.getId() : "???new???@" + subprotocol + " - illegal action");
				// illegal action
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal action specified!");
			}
		}
		else if(req.getMethod().equals("GET"))
		{
			logger.debug(connection != null ? connection.getId() : "???new???@" + subprotocol + " - fetching message");
			if(connection != null)
			{
				fetchMessage(connection, resp);
			}
			else
			{
				resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Not connected!");
			}
		}
		else
		{
			logger.debug(connection != null ? connection.getId() : "???new???@" + subprotocol + " - illegal method");
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Only POST and GET are allowed");
		}
	}

	/**
	 * Initiate a new connection
	 * 
	 * @param sessionId - the session ID
	 * @param protocol - the protocol name
	 * @param extensions - optional extensions requested by the client
	 * @parma session - the HttpSession associated with the connection
	 */
	protected void doConnect(String sessionId, String subprotocol, Enumeration<String> extensions, HttpSession session) throws IOException
	{
		Service service = this.getService(subprotocol, extensions);

		HttpConnection connection = new HttpConnection(subprotocol);
		connection.setProtocol(httpProtocol);
		connection.setService(service);
		connection.setManager(this.manager);
		connection.setSession(session);

		addConnection(subprotocol, sessionId, connection);

		service.onOpen(connection);
		service.onHandshake(connection);
	}

	/**
	 * Disconnect an existing connection.
	 * 
	 * @param connection - the connection
	 * @param protocol - the protocol
	 * @param req - the request
	 */
	protected void doDisconnect(HttpConnection connection, HttpServletRequest req) throws IOException
	{
		int closeCode = req.getIntHeader(HEADER_CLOSE_CODE);

		Message message = getMessage(connection, req);

		// send a closing response
		// if the server already closed the connection, this will do nothing...
		connection.close(closeCode, message.getDataString());
		// call onClose-Event in Service
		connection.getService().onClose(connection, closeCode, message.getDataString());

		removeConnection(connection.getSubprotocol(), req.getSession().getId());
	}

	/**
	 * Handle an array of incoming message with the given connection
	 * 
	 * @param connection - the connection
	 * @param req - the request
	 */
	protected void handleMessage(HttpConnection connection, HttpServletRequest req) throws IOException
	{
		Protocol protocol = connection.getProtocol();

		Message message = getMessage(connection, req);

		if(protocol.isText(message.getOpCode()) || protocol.isBinary(message.getOpCode()))
			connection.getService().onMessage(connection, message);
		else
			connection.getService().onFrame(connection, (byte) 0, message.getOpCode(), message.getData(), message.getOffset(), message.getLength());
	}

	/**
	 * Get the message send with a request
	 * 
	 * @param connection - the connection used
	 * @param req - the incoming request
	 * @return the message
	 * @throws IOException - if reading the message fails
	 */
	protected Message getMessage(HttpConnection connection, HttpServletRequest req) throws IOException
	{
		Protocol protocol = connection.getProtocol();

		byte opCode = (byte) req.getIntHeader(HEADER_OP_CODE);
		int messageLength;
		if(protocol.isBinary(opCode))
			messageLength = this.getConnectionProperties().getMaxBinaryMessageSize();
		else if(protocol.isText(opCode))
			messageLength = this.getConnectionProperties().getMaxTextMessageSize();
		else
			messageLength = 0;
		byte[] data = new byte[messageLength];
		messageLength = req.getInputStream().read(data);
		if(messageLength < 0)
			messageLength = 0;
		logger.debug(connection.getId() + " - message send: " + new String(data, 0, messageLength));

		if(protocol.isText(opCode))
			return new Message(new String(data, 0, messageLength), protocol);
		else
			return new Message(data, 0, messageLength, protocol);
	}

	/**
	 * Fetch all buffered messages from the given service and write them into the response body.
	 * 
	 * @param connection - the connection
	 * @param resp - the response
	 * @throws IOException - if writing the response fails
	 */
	protected void fetchMessage(HttpConnection connection, HttpServletResponse resp) throws IOException
	{
		Protocol protocol = connection.getProtocol();

		Message message;

		synchronized(connection)
		{
			logger.debug("no messages found: waiting for incoming message...");
			while(connection.getMessageBuffer().size() == 0)
			{
				try
				{
					connection.wait();
				}
				catch(InterruptedException e)
				{
					logger.error(e.getMessage(), e);
				}
			}
			logger.debug("message incoming:");
			logger.debug(connection.getMessageBuffer().getFirst().getDataString());
			logger.debug("Op-Code: " + connection.getMessageBuffer().getFirst().getOpCode());
			logger.debug("Close-Code: " + connection.getMessageBuffer().getFirst().getCloseCode());

			message = connection.getMessageBuffer().removeFirst();
		}

		if(protocol.isText(message.getOpCode()))
		{
			resp.getOutputStream().write(message.getData());
			resp.setHeader(HEADER_OP_CODE, "" + protocol.textOpCode());
			// resp.setContentType("text/html");
		}
		else if(protocol.isBinary(message.getOpCode()))
		{
			resp.getOutputStream().write(message.getData(), message.getOffset(), message.getLength());
			resp.setHeader(HEADER_OP_CODE, "" + protocol.binaryOpCode());
			// resp.setContentType("binary");
		}
		else
		{
			// control
			resp.getOutputStream().write(message.getData(), message.getOffset(), message.getLength());
			resp.setHeader(HEADER_OP_CODE, "" + message.getOpCode());
			// resp.setContentType("binary");
			if(protocol.isClose(message.getOpCode()))
				resp.setHeader(HEADER_CLOSE_CODE, "" + message.getCloseCode());
		}
		resp.addHeader(HEADER_MESSAGES_BUFFERED, "" + connection.getMessageBuffer().size());
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * Check wether the given Request is a valid WebSocket handshake. Therefore following headers
	 * must be correct:
	 * Upgrade: WebSocket
	 * Connection: Upgrade
	 * 
	 * @param req - the request
	 * @param resp - the response
	 * @return true or false
	 */
	protected boolean isWebSocketHandshake(HttpServletRequest req, HttpServletResponse resp)
	{
		return req.getMethod().equals("GET") && Protocol.HEADER_CONNECTION_UPGRADE.equalsIgnoreCase(req.getHeader(Protocol.HEADER_CONNECTION))
				&& Protocol.HEADER_UPGRADE_WEBSOCKET.equalsIgnoreCase(req.getHeader(Protocol.HEADER_UPGRADE));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.engine.FilterEngine#stop0()
	 */
	@Override
	protected void stop0()
	{
		// free all waiting request by notifying them
		// for(String protocol: connections.keySet())
		// {
		// for(HttpConnection connection: connections.get(protocol).values())
		// {
		// synchronized(connection)
		// {
		// connection.notifyAll();
		// }
		// }
		// }
		super.stop0();
	}
}

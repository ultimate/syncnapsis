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
//@requires("WebSockets")
//@requires("Strings")
//@requires("Console")

WebSockets.http = {};

// Header name for the protocol name in the http request.
WebSockets.http.HEADER_PROTOCOL = "protocol";
// Header name for the extension names in the http request.
WebSockets.http.HEADER_EXTENSIONS = "extensions";
// Header name for the action in the http request. Action can be
// [connect|disconnect|message]
WebSockets.http.HEADER_ACTION = "action";
// Header name for failed preconditions
// [connected|disconnected]
WebSockets.http.HEADER_PRECONDITION = "precondition";
// The connect value fot the action parameter.
WebSockets.http.ACTION_CONNECT = "connect";
// The disconnect value fot the action parameter.
WebSockets.http.ACTION_DISCONNECT = "disconnect";
// The message value fot the action parameter.
WebSockets.http.ACTION_MESSAGE = "message";
// The header name for specifying how many more messages are buffered
WebSockets.http.HEADER_MESSAGES_BUFFERED = "Messages-Buffered";
// The header name for specifying the op code
WebSockets.http.HEADER_OP_CODE = "Op-Code";
// The text value for the op-code parameter
WebSockets.http.OP_CODE_TEXT = 0x1;
// The binary value for the op-code parameter
WebSockets.http.OP_CODE_BINARY = 0x2;
// The binary value for the op-code parameter
WebSockets.http.OP_CODE_CLOSE = 0x8;
// The header name for specifying the op code
WebSockets.http.HEADER_CLOSE_CODE = "Close-Code";
// The precondition returned if the socket is connected
WebSockets.http.PRECONDITION_CONNECTED = "connected";
// The precondition returned if the socket is disconnected
WebSockets.http.PRECONDITION_DISCONNECTED = "disconnected";

// list of all active sockets
WebSockets.http.sockets = {};

// HttpSocket states
WebSockets.http.state = {};
WebSockets.http.state.INITIALIZING = "initializing";
WebSockets.http.state.OPEN = "open";
WebSockets.http.state.CLOSING = "closing";
WebSockets.http.state.CLOSED = "closed";

WebSockets.HttpSocket = function(url, protocol)
{
	if(url == null)
		throw new Error("url must not be null");
	if(url.startsWith("ws"))
		url = "http" + url.substring(2);
	if(protocol == null)
		protocol = "default"; // empty protocol required?

	this.url = url;
	this.protocol = protocol;
	this.state = WebSockets.http.state.INITIALIZING;

	// register global instance
	this.index = WebSockets.http.sockets.length;
	WebSockets.http.sockets[this.index] = this;

	// the headers for the opening handshake
	this._connect = {};
	this._connect[WebSockets.http.HEADER_PROTOCOL] = this.protocol;
	this._connect[WebSockets.http.HEADER_ACTION] = WebSockets.http.ACTION_CONNECT;

	// the headers for the closing handshake
	this._disconnect = {};
	this._disconnect[WebSockets.http.HEADER_PROTOCOL] = this.protocol;
	this._disconnect[WebSockets.http.HEADER_ACTION] = WebSockets.http.ACTION_DISCONNECT;
	this._disconnect[WebSockets.http.HEADER_OP_CODE] = WebSockets.http.OP_CODE_CLOSE;

	// the headers for sending a message
	this._message = {};
	this._message[WebSockets.http.HEADER_PROTOCOL] = this.protocol;
	this._message[WebSockets.http.HEADER_ACTION] = WebSockets.http.ACTION_MESSAGE;

	// the headers for fetching buffered messages
	this._fetch = {};
	this._fetch[WebSockets.http.HEADER_PROTOCOL] = this.protocol;

	// TODO add window close event
	Events.addEventListener("unload", function(e)
	{
		this.close(1001, "Client closed website");
	});

	this.open();
};

// to be overwritten by client
WebSockets.HttpSocket.prototype.onopen = function(e)
{
	console.debug("WebSocket opened");
};
// to be overwritten by client
WebSockets.HttpSocket.prototype.onmessage = function(e)
{
	console.debug("Message received: " + e.data);
};
// to be overwritten by client
WebSockets.HttpSocket.prototype.onerror = function(e)
{
	console.debug("Error received: " + e.data);
};
// to be overwritten by client
WebSockets.HttpSocket.prototype.onclose = function(e)
{
	console.debug("WebSocket closed");
};

// fallback implementation

// open connection
WebSockets.HttpSocket.prototype.open = function()
{
	if(this.state == WebSockets.http.state.CLOSING)
	{
		this.onerror(this.createEvent2("WebSocket currently closing"));
		return;
	}
	else if(this.state == WebSockets.http.state.OPEN)
	{
		this.onerror(this.createEvent2("WebSocket alread open"));
		return;
	}
	AJAX.sendRequest(this.url, null, HTTP.POST, this._connect, this._handleOpen, this._handleClose, this);
};

// close connection
WebSockets.HttpSocket.prototype.close = function(code, reason)
{
	if(code == undefined)
		code = 1000;
	this._disconnect[WebSockets.http.HEADER_CLOSE_CODE] = code;
	if(this.state == WebSockets.http.state.OPEN)
	{
		console.debug("closing connection: " + reason);
		// client closes the connection
		// after closing waiting for server-response via fetch is neccessary
		AJAX.sendRequest(this.url, reason, HTTP.POST, this._disconnect, this._handleNothing, this._handleClose, this);
		this.state = WebSockets.http.state.CLOSING;
	}
	else if(this.state == WebSockets.http.state.CLOSING)
	{
		console.debug("confirming close by server: " + reason);
		// server already send a closing message
		// this is only the response
		AJAX.sendRequest(this.url, reason, HTTP.POST, this._disconnect, this._handleClose, this._handleClose, this);
	}
	else
	{
		this.onerror(this.createEvent2("WebSocket not connected"));
	}
};

// send a message via AJAX
WebSockets.HttpSocket.prototype.send = function(message)
{
	if(this.state != WebSockets.http.state.OPEN)
	{
		this.onerror(this.createEvent2("WebSocket not connected"));
		return;
	}
	if(typeof (message) == "string" || message instanceof String)
		this._message[WebSockets.http.HEADER_OP_CODE] = WebSockets.http.OP_CODE_TEXT;
	else
		this._message[WebSockets.http.HEADER_OP_CODE] = WebSockets.http.OP_CODE_BINARY;

	AJAX.sendRequest(this.url, message, HTTP.POST, this._message, this._handleSend, this._handleError, this);
};

// fetch buffered messages
WebSockets.HttpSocket.prototype.fetch = function()
{
	AJAX.sendRequest(this.url, null, HTTP.GET, this._fetch, this._handleFetch, this._handleError, this);
};

WebSockets.HttpSocket.prototype.createEvent = function(request)
{
	var e = {}; // faking a MessageEvent
	// e.opcode = request.getResponseHeader(WebSockets.http.HEADER_OP_CODE);
	e.data = request.responseText;
	return e;
};

WebSockets.HttpSocket.prototype.createEvent2 = function(message)
{
	var e = {}; // faking a MessageEvent
	e.data = message;
	return e;
};

WebSockets.HttpSocket.prototype._handleOpen = function(request)
{
	this.state = WebSockets.http.state.OPEN;
	this.onopen(this.createEvent(request));
	this.fetch();
};

WebSockets.HttpSocket.prototype._handleClose = function(request)
{
	console.log("websocket closed: " + request.status);
	console.log("precondition: " + request.getResponseHeader(WebSockets.http.HEADER_PRECONDITION));
	if(request.status == HTTP.STATUS_PRECONDITION_FAILED)
	{
		if(request.getResponseHeader(WebSockets.http.HEADER_PRECONDITION) == WebSockets.http.PRECONDITION_CONNECTED)
		{
			// already connected
			this._handleOpen(request);
			return;
		}
	}
	this.state = WebSockets.http.state.CLOSED;
	this.onclose(this.createEvent(request));
};

WebSockets.HttpSocket.prototype._handleSend = function(request)
{
	// nothing to do, cause fetching is running parallel
};

WebSockets.HttpSocket.prototype._handleError = function(request)
{
	this.onerror(this.createEvent(request));
};

WebSockets.HttpSocket.prototype._handleNothing = function(request)
{
	// do nothing
};

WebSockets.HttpSocket.prototype._handleFetch = function(request)
{
	if(request.getResponseHeader(WebSockets.http.HEADER_OP_CODE))
	{
		if(request.getResponseHeader(WebSockets.http.HEADER_OP_CODE) == WebSockets.http.OP_CODE_CLOSE)
		{
			if(this.state == WebSockets.http.state.CLOSING)
			{
				console.debug("Server confirmed close: " + request.responseText);
				// close message already send to server
				// this is the closing response
				this.onclose(this.createEvent(request));
			}
			else
			{
				console.debug("Server closing connection: " + request.responseText);
				this.state = WebSockets.http.state.CLOSING;
				// server closes the connection
				// send a closing response
				this.close(request.getResponseHeader(WebSockets.http.HEADER_CLOSE_CODE), request.responseText);
			}
			return; // no more fetching
		}
		else
		{
			this.onmessage(this.createEvent(request));
		}
	}
	this.fetch();
};

if(!window.WebSocket)
{
	console.info("WebSocket not natively supported, falling back to AJAX");

	window.WebSocket = WebSockets.HttpSocket;
}
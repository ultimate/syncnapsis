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
//@requires("Events")
//@requires("Console")
//@requires("JSON")
//@requires("Reflections")
//@requires("Proxies")

WebSockets.rpc = {};

// constants here
// WebSockets.rpc.abc

WebSockets.RPCSocket = function(url, protocol, defaultRPCHandler, defaultResultHandler, defaultErrorHandler)
{
	this._ws = null;
	this._url = url;
	this._protocol = protocol;
	this._messageBuffer = new Array();

	// event handlers
	this._defaultRPCHandler = defaultRPCHandler ? defaultRPCHandler : Events.consoleInfoEventHandler();
	this._defaultResultHandler = defaultResultHandler ? defaultResultHandler : Events.consoleInfoEventHandler();
	this._defaultErrorHandler = defaultErrorHandler ? defaultErrorHandler : Events.consoleErrorEventHandler();

	// list of registered callbacks
	this._callbacks = {};
	this._currentCall = 0;

	// init the WebSocket-Connection
	this.open();
};

WebSockets.RPCSocket.prototype._onopen = function(e)
{
	// send buffered messages
	while(this._messageBuffer.length > 0)
		this.send(this._messageBuffer.pop());
	if(this.onopen)
		this.onopen(e);
};

WebSockets.RPCSocket.prototype._onclose = function(e)
{
	// reset the WS-Object
	this._ws = null;
	if(this.onclose)
		this.onclose(e);
};

WebSockets.RPCSocket.prototype._onmessage = function(e)
{
	var msg = this.parseMessage(e.data);

	if(msg.ccid)
		this._onresult(msg);
	else
		this._onrpc(msg);
};

WebSockets.RPCSocket.prototype._onerror = function(e)
{
	var msg;

	try
	{
		msg = this.parseMessage(e.data);
	}
	catch(err)
	{
		msg = {
			data : e.data
		};

		// we get here on Jetty Shutdown because ChannelConnectors closes before
		// close message can be send from server
		// Fallback.js receives a response with empty message "" an Status Code
		// 0
		if(this._ws instanceof WebSockets.HttpSocket)
		{
			console.error("Http-Fallback-WebSocket unexpectedly closed!")
			this._onclose(msg);
			return;
		}
	}

	if(this._defaultErrorHandler)
	{
		if(this._defaultErrorHandler instanceof Events.EventHandler)
			this._defaultErrorHandler.onEvent(msg);
		else
			this._defaultErrorHandler.call(null, msg);
	}
	else
		console.error("No handler defined for errors: " + e.data);
};

WebSockets.RPCSocket.prototype._onresult = function(msg)
{
	if(this._callbacks[msg.ccid])
	{
		if(this._callbacks[msg.ccid] instanceof Events.EventHandler)
			this._callbacks[msg.ccid].onEvent(msg);
		else
			this._callbacks[msg.ccid].call(null, msg);
	}
	else if(this._defaultResultHandler)
	{
		if(this._defaultResultHandler instanceof Events.EventHandler)
			this._defaultResultHandler.onEvent(msg);
		else
			this._defaultResultHandler.call(null, msg);
	}
	else
		console.error("No handler defined for this result!");
};

WebSockets.RPCSocket.prototype._onrpc = function(msg)
{
	if(this._defaultRPCHandler)
	{
		if(this._defaultRPCHandler instanceof Events.EventHandler)
			this._defaultRPCHandler.onEvent(msg);
		else
			this._defaultRPCHandler.call(null, msg);
	}
	else
		console.error("No handler defined for RPC!");
};

WebSockets.RPCSocket.prototype.open = function()
{
	if(!this._ws)
	{
		// create the WebSocket
		this._ws = new WebSocket(this._url, this._protocol);

		// redirect events to this instance
		this._ws.onopen = Events.wrapEventHandler(this, this._onopen);
		this._ws.onclose = Events.wrapEventHandler(this, this._onclose);
		this._ws.onmessage = Events.wrapEventHandler(this, this._onmessage);
		this._ws.onerror = Events.wrapEventHandler(this, this._onerror);
	}
};

WebSockets.RPCSocket.prototype.close = function(code, reason)
{
	if(this._ws)
		this._ws.close(code, reason);
};

WebSockets.RPCSocket.prototype.cid = function()
{
	return ++this._currentCall;
};

WebSockets.RPCSocket.prototype.call = function(object, method, args, callback)
{
	// create unique call id
	var ccid = this.cid();

	// register callback with unique call id
	if(callback)
		this._callbacks[ccid] = callback;

	var data = {};
	data.object = object;
	data.method = method;
	data.args = args;

	// create message (including unique call id)
	// omit Socket because socket can not be transmitted to Server
	var message = new WebSockets.rpc.Message(null, ccid, null, data);
	// send message
	this.send(message);
};

WebSockets.RPCSocket.prototype.respond = function(scid, data)
{
	// create message (including server call id)
	// omit Socket because socket can not be transmitted to Server
	var message = new WebSockets.rpc.Message(null, null, scid, data);
	// send message
	this.send(message);
};

WebSockets.RPCSocket.prototype.send = function(message)
{
	if(!this._ws)
	{
		// buffer the current message, it will be send onopen
		this.bufferMessage(message);
		// (re)establish the connection
		this.open();
		return;
	}
	this._ws.send(JSON.stringify(message));
};

WebSockets.RPCSocket.prototype.bufferMessage = function(message)
{
	this._messageBuffer.push(message);
};

WebSockets.RPCSocket.prototype.parseMessage = function(message)
{
	var m = JSON.parse(message);
	return new WebSockets.rpc.Message(this, m.ccid, m.scid, m.data);
};

WebSockets.rpc.Message = function(socket, ccid, scid, data)
{
	// by checking arguments for null-values only filled properties are set
	// this way we can prevent null properties from being transmitted via JSON
	if(socket)
		this.socket = socket;
	if(ccid)
		this.ccid = ccid;
	if(scid)
		this.scid = scid;
	this.data = data;
};

WebSockets.rpc.GenericRPCHandler = function(objectHolder)
{
	this._objectHolder = objectHolder ? objectHolder : window;

	this._handlerObject = this;
	this._handlerMethod = this.doRPC;
};

WebSockets.rpc.GenericRPCHandler.prototype = new Events.EventHandler();
WebSockets.rpc.GenericRPCHandler.prototype.constructor = WebSockets.rpc.GenericRPCHandler;

WebSockets.rpc.GenericRPCHandler.prototype.doRPC = function(msg)
{
	var object = this._objectHolder[msg.data.object] ? this._objectHolder[msg.data.object] : this._objectHolder;
	var method = object[msg.data.method];
	var args = msg.data.args;

	var result = Reflections.call(object, method, args);

	if(result)
	{
		// send result back to server
		msg.socket.respond(msg.scid, result);
	}
};

WebSockets.rpc.GenericRPCInvocationHandler = function(rpcSocket, objectHolder)
{
	this._rpcSocket = rpcSocket;
	this._objectHolder = objectHolder ? objectHolder : window;
};

WebSockets.rpc.GenericRPCInvocationHandler.prototype = new Proxies.InvocationHandler();
WebSockets.rpc.GenericRPCInvocationHandler.prototype.constructor = WebSockets.rpc.GenericRPCInvocationHandler;

WebSockets.rpc.GenericRPCInvocationHandler.prototype.invoke = function(proxy, method, args)
{
	var objectName = Reflections.resolveName(proxy, this._objectHolder);
	if(objectName instanceof Array)
	{
		console.warn("Multiple names found for proxy: \"" + objectName + "\"! Using first one found!");
		objectName = objectName[0];
	}

	var methodName = Reflections.resolveName(method, proxy);
	if(methodName instanceof Array)
	{
		console.warn("Multiple names found for method: \"" + methodName + "\"! Using first one found!");
		methodName = methodName[0];
	}

	var resultHandler = null;
	
	// check wether the original method for the method invoked returns a function
	// the original method is stored within the stub of the proxy
	var originalResult = (proxy.stub[methodName])();
	if(typeof originalResult == Reflections.type.FUNCTION)
		resultHandler = originalResult;

	// check if the number of arguments passed is equal to the number expected
	var numberOfArgumentsExpected = Reflections.numberOfArguments(method);
	if(args.length > numberOfArgumentsExpected)
	{
		// more arguments than expected
		// one additional argument should be the resulthandler
		resultHandler = args[numberOfArgumentsExpected];
		// remove the additional arguments, the Server will not support them ;-)
		while(args.length > numberOfArgumentsExpected)
			args.pop();
	}
	else if(args.length < numberOfArgumentsExpected)
	{
		// fill up the arguments, the Server will need them ;-)
		while(args.length < numberOfArgumentsExpected)
			args.push(null);
	}

	this._rpcSocket.call(objectName, methodName, args, resultHandler);
};
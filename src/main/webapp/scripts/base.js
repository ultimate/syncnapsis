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
//@requires("UI")
//@requires("RPCSocket")
//@requires("PlayerManager")
//@requires("application-base")

var WSConfiguration = {};
WSConfiguration.protocol = "rpc";
WSConfiguration.path = "/ws";

var client = {};
var server = {};
var rpcSocket;

init = function()
{
	connect();
};

connect = function()
{
	// do the WS-connection stuff
	var genericRPCHandler = new WebSockets.rpc.GenericRPCHandler(client);
	var url = WebSockets.getRelativeURL(WSConfiguration.path);
	rpcSocket = new WebSockets.RPCSocket(url, WSConfiguration.protocol, genericRPCHandler.anonymous(), null, null);
	var genericRPCInvocationHandler = new WebSockets.rpc.GenericRPCInvocationHandler(rpcSocket, server);
	// init client-side managers
	client.uiManager = new UIManager();
	// init server-side managers as proxies (may use same "class/interface") 
	server.playerManager = Proxies.newProxyInstance(PlayerManager, genericRPCInvocationHandler);
	server.uiManager = Proxies.newProxyInstance(ServerUIManager, genericRPCInvocationHandler);
	// init additional services
	server.entityManager = new EntityManager(server);
};

disconnect = function()
{
	rpcSocket.close();
};
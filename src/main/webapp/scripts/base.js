//@requires("UI")
//@requires("RPCSocket")
//@requires("PlayerManager")

var WSConfiguration = {};
WSConfiguration.protocol = "rpc";
WSConfiguration.path = "/ws";

var client = {};
var server = {};
var rpcSocket;

init = function()
{
	UI.init();
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
	client.playerManager = new PlayerManager();
	// init server-side managers as proxies (may use same "class/interface") 
	server.playerManager = Proxies.newProxyInstance(PlayerManager, genericRPCInvocationHandler);
};

disconnect = function()
{
	rpcSocket.close();
};
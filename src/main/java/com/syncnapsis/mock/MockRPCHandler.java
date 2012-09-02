package com.syncnapsis.mock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.service.rpc.RPCCall;
import com.syncnapsis.websockets.service.rpc.RPCHandler;

public class MockRPCHandler implements RPCHandler
{
	private List<RPCCall>							calls;
	private Object									result;

	private Map<Connection, Map<String, Object>>	clientInstances	= new HashMap<Connection, Map<String, Object>>();

	public MockRPCHandler()
	{
		this.calls = new LinkedList<RPCCall>();
	}

	@Override
	public Object doRPC(RPCCall call, Object... authorities)
	{
		calls.add(call);
		return result;
	}

	public void setResult(Object result)
	{
		this.result = result;
	}

	public List<RPCCall> getCalls()
	{
		return calls;
	}

	public void setClientInstance(String objectName, Connection connection, Object object)
	{
		if(clientInstances.get(connection) == null)
			clientInstances.put(connection, new HashMap<String, Object>());
		clientInstances.get(connection).put(objectName, object);
	}

	@Override
	public Object getClientInstance(String objectName, Connection connection)
	{
		if(clientInstances.get(connection) == null)
			return null;
		return clientInstances.get(connection).get(objectName);
	}

}

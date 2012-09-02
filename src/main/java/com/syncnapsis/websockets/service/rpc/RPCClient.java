package com.syncnapsis.websockets.service.rpc;

import com.syncnapsis.websockets.Connection;

public interface RPCClient<T>
{
	public T getClientInstance(Connection connection);
}

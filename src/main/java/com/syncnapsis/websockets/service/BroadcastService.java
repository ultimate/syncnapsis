package com.syncnapsis.websockets.service;

import com.syncnapsis.websockets.Connection;


/**
 * Simple service that echoes back all messages received to all clients
 * 
 * @author ultimate
 */
public class BroadcastService extends BaseService
{
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onMessage(com.syncnapsis.websockets.Connection, java.lang.String)
	 */
	@Override
	public void onMessage(Connection connection, String data)
	{
		connection.getService().broadcast(data);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onMessage(com.syncnapsis.websockets.Connection, byte[], int, int)
	 */
	@Override
	public void onMessage(Connection connection, byte[] data, int offset, int length)
	{
		connection.getService().broadcast(data, offset, length);
	}
}

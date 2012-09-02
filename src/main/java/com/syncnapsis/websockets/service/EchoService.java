package com.syncnapsis.websockets.service;

import java.io.IOException;

import com.syncnapsis.websockets.Connection;


/**
 * Simple service that echoes back all messages received to the client
 * 
 * @author ultimate
 */
public class EchoService extends BaseService
{
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.BaseService#onMessage(com.syncnapsis.websockets.Connection, java.lang.String)
	 */
	@Override
	public void onMessage(Connection connection, String data)
	{
		try
		{
			connection.sendMessage(data);
		}
		catch(IOException e)
		{
			logger.error("could not send echo: service '" + connection.getId() + "'");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.BaseService#onMessage(com.syncnapsis.websockets.Connection, byte[], int, int)
	 */
	@Override
	public void onMessage(Connection connection, byte[] data, int offset, int length)
	{
		try
		{
			connection.sendMessage(data, offset, length);
		}
		catch(IOException e)
		{
			logger.error("could not send echo: service '" + connection.getId() + "'");
		}
	}
}

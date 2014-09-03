/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

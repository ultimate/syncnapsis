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
package com.syncnapsis.websockets;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the global properties for Connections
 * 
 * @author ultimate
 */
public class ConnectionProperties
{
	/**
	 * Logger-Instance
	 */
	protected final Logger		logger						= LoggerFactory.getLogger(getClass());
	/**
	 * The bufferSize used.
	 * Default = 8192
	 */
	private int					bufferSize					= 8192;

	/**
	 * The max idle time for connections
	 * Default = 18000000
	 */
	private int					maxIdleTime					= 1800000;

	/**
	 * The max text message size
	 * Default = bufferSize
	 * If -1 no message aggregation from frames will be done.
	 * 
	 * @see bufferSize
	 */
	private int					maxTextMessageSize			= bufferSize;

	/**
	 * The max binary message size
	 * Default = -1
	 * If -1 no message aggregation from frames will be done.
	 */
	private int					maxBinaryMessageSize		= -1;

	/**
	 * The key for the buffer size
	 * @see ConnectionProperties#ConnectionProperties(Properties)
	 */
	public static final String	KEY_BUFFER_SIZE				= "connection.bufferSize";
	/**
	 * The key for the max idle time
	 * @see ConnectionProperties#ConnectionProperties(Properties)
	 */
	public static final String	KEY_MAX_IDLE_TIME			= "connection.maxIdleTime";
	/**
	 * The key for the max text message size
	 * @see ConnectionProperties#ConnectionProperties(Properties)
	 */
	public static final String	KEY_MAX_TEXT_MESSAGE_SIZE	= "connection.maxTextMessageSize";
	/**
	 * The key for the max binary message size
	 * @see ConnectionProperties#ConnectionProperties(Properties)
	 */
	public static final String	KEY_MAX_BINARY_MESSAGE_SIZE	= "connection.maxBinaryMessageSize";

	/**
	 * Empty default Constructor
	 */
	public ConnectionProperties()
	{
	}

	/**
	 * Construction reading properties from a Properties-Object<br>
	 * <br>
	 * Properties useable:<br>
	 * <ul>
	 * <li>connection.bufferSize ({@link ConnectionProperties#KEY_BUFFER_SIZE})</li>
	 * <li>connection.maxIdleTime ({@link ConnectionProperties#KEY_MAX_IDLE_TIME})</li>
	 * <li>connection.maxTextMessageSize ({@link ConnectionProperties#KEY_MAX_TEXT_MESSAGE_SIZE})</li>
	 * <li>connection.maxBinaryMessageSize ({@link ConnectionProperties#KEY_MAX_BINARY_MESSAGE_SIZE}
	 * )</li>
	 * </ul>
	 * 
	 * @param p - the properties to use
	 */
	public ConnectionProperties(Properties p)
	{
		this.bufferSize = fromProperties(p, KEY_BUFFER_SIZE);
		this.maxIdleTime = fromProperties(p, KEY_MAX_IDLE_TIME);
		this.maxTextMessageSize = fromProperties(p, KEY_MAX_TEXT_MESSAGE_SIZE);
		this.maxBinaryMessageSize = fromProperties(p, KEY_MAX_BINARY_MESSAGE_SIZE);
	}

	/**
	 * Get an int from the Properties if the value is not null. Otherwise return -1.<br>
	 * <br>
	 * Properties useable:<br>
	 * <ul>
	 * <li>connection.bufferSize ({@link ConnectionProperties#KEY_BUFFER_SIZE})</li>
	 * <li>connection.maxIdleTime ({@link ConnectionProperties#KEY_MAX_IDLE_TIME})</li>
	 * <li>connection.maxTextMessageSize ({@link ConnectionProperties#KEY_MAX_TEXT_MESSAGE_SIZE})</li>
	 * <li>connection.maxBinaryMessageSize ({@link ConnectionProperties#KEY_MAX_BINARY_MESSAGE_SIZE}
	 * )</li>
	 * 
	 * @param p - the Properties
	 * @param key - the property key
	 * @return the value as an int
	 */
	private int fromProperties(Properties p, String key)
	{
		try
		{
			if(p.getProperty(key) != null)
				return Integer.parseInt(p.getProperty(key));
		}
		catch(NumberFormatException e)
		{
			logger.error("Could not parse property '" + key + "': " + e.getMessage());
		}
		return -1;
	}

	/**
	 * The bufferSize used.<br>
	 * Default = 8192
	 * 
	 * @return bufferSize
	 */
	public int getBufferSize()
	{
		return bufferSize;
	}

	/**
	 * The bufferSize used.<br>
	 * Default = 8192
	 * 
	 * @param bufferSize - the bufferSize
	 */
	public void setBufferSize(int bufferSize)
	{
		this.bufferSize = bufferSize;
	}

	/**
	 * The max idle time for connections.<br>
	 * Default = 18000000
	 * 
	 * @return maxIdleTime
	 */
	public int getMaxIdleTime()
	{
		return maxIdleTime;
	}

	/**
	 * The max idle time for connections.<br>
	 * Default = 18000000
	 * 
	 * @param maxIdleTime - the max idle time
	 */
	public void setMaxIdleTime(int maxIdleTime)
	{
		this.maxIdleTime = maxIdleTime;
	}

	/**
	 * The max text message size.<br>
	 * Default = bufferSize.<br>
	 * If -1 no message aggregation from frames will be done.
	 * 
	 * @see ConnectionProperties#getBufferSize()
	 * @return maxTextMessageSize
	 */
	public int getMaxTextMessageSize()
	{
		return maxTextMessageSize;
	}

	/**
	 * The max text message size.<br>
	 * Default = bufferSize.<br>
	 * If -1 no message aggregation from frames will be done.
	 * 
	 * @see ConnectionProperties#getBufferSize()
	 * @param maxTextMessageSize - the max text message size
	 */
	public void setMaxTextMessageSize(int maxTextMessageSize)
	{
		this.maxTextMessageSize = maxTextMessageSize;
	}

	/**
	 * The max binary message size.<br>
	 * Default = -1.<br>
	 * If -1 no message aggregation from frames will be done.
	 * 
	 * @return maxBinaryMessageSize
	 */
	public int getMaxBinaryMessageSize()
	{
		return maxBinaryMessageSize;
	}

	/**
	 * The max binary message size.<br>
	 * Default = -1.<br>
	 * If -1 no message aggregation from frames will be done.
	 * 
	 * @param maxBinaryMessageSize - the max binary message size
	 */
	public void setMaxBinaryMessageSize(int maxBinaryMessageSize)
	{
		this.maxBinaryMessageSize = maxBinaryMessageSize;
	}
}

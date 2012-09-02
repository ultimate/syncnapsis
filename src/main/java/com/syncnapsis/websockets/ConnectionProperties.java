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
	protected final Logger	logger					= LoggerFactory.getLogger(getClass());
	/**
	 * The bufferSize used.
	 * Default = 8192
	 */
	private int				bufferSize				= 8192;

	/**
	 * The max idle time for connections
	 * Default = 18000000
	 */
	private int				maxIdleTime				= 1800000;

	/**
	 * The max text message size
	 * Default = bufferSize
	 * If -1 no message aggregation from frames will be done.
	 * 
	 * @see bufferSize
	 */
	private int				maxTextMessageSize		= bufferSize;

	/**
	 * The max binary message size
	 * Default = -1
	 * If -1 no message aggregation from frames will be done.
	 */
	private int				maxBinaryMessageSize	= -1;

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
	 * <li>connection.bufferSize</li>
	 * <li>connection.maxIdleTime</li>
	 * <li>connection.maxTextMessageSize</li>
	 * <li>connection.maxBinaryMessageSize</li>
	 * </ul>
	 * 
	 * @param p - the properties to use
	 */
	public ConnectionProperties(Properties p)
	{
		this.bufferSize = fromProperties(p, "connection.bufferSize");
		this.maxIdleTime = fromProperties(p, "connection.maxIdleTime");
		this.maxTextMessageSize = fromProperties(p, "connection.maxTextMessageSize");
		this.maxBinaryMessageSize = fromProperties(p, "connection.maxBinaryMessageSize");
	}

	/**
	 * Get an int from the Properties if the value is not null. Otherwise return -1.
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

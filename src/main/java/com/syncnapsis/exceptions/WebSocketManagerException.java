package com.syncnapsis.exceptions;

/**
 * RuntimeException to be thrown if something fails within a WebSocketManager
 * 
 * @author ultimate
 */
public class WebSocketManagerException extends RuntimeException
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see RuntimeException#RuntimeException()
	 */
	public WebSocketManagerException()
	{
		super();
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public WebSocketManagerException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @see RuntimeException#RuntimeException(String)
	 */
	public WebSocketManagerException(String message)
	{
		super(message);
	}

	/**
	 * @see RuntimeException#RuntimeException(Throwable)
	 */
	public WebSocketManagerException(Throwable cause)
	{
		super(cause);
	}

}


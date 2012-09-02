package com.syncnapsis.exceptions;

/**
 * RuntimeException to be thrown if something fails within a WebSocket-Engine
 * 
 * @author ultimate
 */
public class WebSocketEngineException extends RuntimeException
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see RuntimeException#RuntimeException()
	 */
	public WebSocketEngineException()
	{
		super();
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public WebSocketEngineException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @see RuntimeException#RuntimeException(String)
	 */
	public WebSocketEngineException(String message)
	{
		super(message);
	}

	/**
	 * @see RuntimeException#RuntimeException(Throwable)
	 */
	public WebSocketEngineException(Throwable cause)
	{
		super(cause);
	}

}

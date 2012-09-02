package com.syncnapsis.exceptions;

/**
 * RuntimeException to be thrown if something fails within a WebSocket-Service
 * 
 * @author ultimate
 */
public class WebSocketServiceException extends RuntimeException
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see RuntimeException#RuntimeException()
	 */
	public WebSocketServiceException()
	{
		super();
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public WebSocketServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @see RuntimeException#RuntimeException(String)
	 */
	public WebSocketServiceException(String message)
	{
		super(message);
	}

	/**
	 * @see RuntimeException#RuntimeException(Throwable)
	 */
	public WebSocketServiceException(Throwable cause)
	{
		super(cause);
	}

}

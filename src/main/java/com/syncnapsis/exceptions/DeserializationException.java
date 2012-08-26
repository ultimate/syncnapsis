package com.syncnapsis.exceptions;

/**
 * Exception to be thrown when serialization fails.
 * @author ultimate
 *
 */
public class DeserializationException extends Exception
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see Exception#Exception()
	 */
	public DeserializationException()
	{
		super();
	}

	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public DeserializationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @see Exception#Exception(String)
	 */
	public DeserializationException(String message)
	{
		super(message);
	}

	/**
	 * @see Exception#Exception(Throwable)
	 */
	public DeserializationException(Throwable cause)
	{
		super(cause);
	}
}

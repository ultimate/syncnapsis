/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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

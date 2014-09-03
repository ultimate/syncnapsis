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
package com.syncnapsis.websockets.service.rpc;

import java.util.HashMap;
import java.util.Map;

import com.syncnapsis.utils.serialization.Mapable;

/**
 * Class for returnen RPC errors with exception class and message
 * 
 * @author ultimate
 */
public class RPCError implements Mapable<RPCError>
{
	/**
	 * The exception class
	 */
	protected String	exceptionClass;
	/**
	 * The error message
	 */
	protected String	message;

	/**
	 * Create an new RPCError
	 * 
	 * @param exceptionClass - the exception class
	 * @param message - the error message
	 */
	public RPCError(String exceptionClass, String message)
	{
		super();
		this.exceptionClass = exceptionClass;
		this.message = message;
	}

	/**
	 * Create a new RPCError from a Throwable
	 * 
	 * @param t - the Throwable
	 */
	public RPCError(Throwable t)
	{
		this(t.getClass().getName(), t.getMessage());
	}

	/**
	 * The exception class
	 * 
	 * @return exceptionClass
	 */
	public String getExceptionClass()
	{
		return exceptionClass;
	}

	/**
	 * The error message
	 * 
	 * @return message
	 */
	public String getMessage()
	{
		return message;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapable#toMap(java.lang.Object[])
	 */
	@Override
	public Map<String, ?> toMap(Object... authorities)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("exceptionClass", exceptionClass);
		m.put("message", message);
		return m;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapable#fromMap(java.util.Map, java.lang.Object[])
	 */
	@Override
	public RPCError fromMap(Map<String, ?> map, Object... authorities)
	{
		this.exceptionClass = (String) map.get("exceptionClass");
		this.message = (String) map.get("message");
		return this;
	}
}

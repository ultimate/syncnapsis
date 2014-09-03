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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.syncnapsis.utils.serialization.Mapable;

/**
 * Class respresenting the Information required for an RPC call (object, method, arguments).
 * The object my be null in specific cases:
 * - the RPCHandler knows how to deal with null (e.g. null is treated as the RPCHandler itself)
 * - the Method is defined globally (e.g. globally visible in JavaScript)
 * 
 * @author ultimate
 */
public class RPCCall implements Mapable<RPCCall>
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * The Object to invoke the Method from
	 */
	private String						object;
	/**
	 * The Method to invoke
	 */
	private String						method;
	/**
	 * The arguments to pass to the method
	 */
	private Object[]					args;

	/**
	 * Information on the invoked {@link Method} and Object
	 */
	private transient InvocationInfo	invocationInfo;

	/**
	 * Empty-Default Constructor for Instantiation before using fromMap
	 */
	public RPCCall()
	{
	}

	/**
	 * Construct a new RPCCall-Information Object.
	 * If object is null, be sure the receiver of the message knows how to deal with null.
	 * Method must never be null.
	 * If null is passed as args it will be replaced by an empty Array.
	 * 
	 * @param object - The Object to invoke the Method from
	 * @param method - The Method to invoke (never null!)
	 * @param args - The arguments to pass to the method
	 */
	public RPCCall(String object, String method, Object[] args)
	{
		super();
		Assert.notNull(method, "method must not be null!");
		this.object = object;
		this.method = method;
		this.args = (args != null ? args : new Object[0]);
	}

	/**
	 * The Object to invoke the Method from
	 * 
	 * @return the name of the Object
	 */
	public String getObject()
	{
		return object;
	}

	/**
	 * The Method to invoke
	 * 
	 * @return the name of the Method
	 */
	public String getMethod()
	{
		return method;
	}

	/**
	 * The arguments to pass to the method
	 * 
	 * @return the Array of arguments
	 */
	public Object[] getArgs()
	{
		return args;
	}

	/**
	 * Get the {@link InvocationInfo} for this {@link RPCCall} if available. {@link InvocationInfo}
	 * must manually be set using {@link RPCCall#setInvocationInfo(InvocationInfo)}
	 * 
	 * @return the invocationInfo
	 */
	public InvocationInfo getInvocationInfo()
	{
		return invocationInfo;
	}

	/**
	 * Add an {@link InvocationInfo} for this {@link RPCCall}
	 * 
	 * @param invocationInfo - the info object
	 */
	public void setInvocationInfo(InvocationInfo invocationInfo)
	{
		this.invocationInfo = invocationInfo;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapable#toMap(java.lang.Object[])
	 */
	@Override
	public Map<String, ?> toMap(Object... authorities)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("object", object);
		m.put("method", method);
		m.put("args", args);
		return m;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapable#fromMap(java.util.Map, java.lang.Object[])
	 */
	@Override
	public RPCCall fromMap(Map<String, ?> map, Object... authorities)
	{
		this.object = (String) map.get("object");
		this.method = (String) map.get("method");
		// no fromMap-conversion here yet. RPCHandler will
		// do this depending on required arguments
		if(map.get("args") instanceof Collection)
			this.args = ((Collection<?>) map.get("args")).toArray();
		else
			this.args = (Object[]) map.get("args");
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		RPCCall other = (RPCCall) obj;
		if(!Arrays.equals(args, other.args))
			return false;
		if(method == null)
		{
			if(other.method != null)
				return false;
		}
		else if(!method.equals(other.method))
			return false;
		if(object == null)
		{
			if(other.object != null)
				return false;
		}
		else if(!object.equals(other.object))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("object", object).append("method", method).append("args", Arrays.toString(args));
		return builder.toString();
	}
}

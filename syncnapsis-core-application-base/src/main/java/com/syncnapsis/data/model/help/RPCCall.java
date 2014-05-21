/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.data.model.help;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syncnapsis.data.model.base.Model;

/**
 * RPCCall-Entity representing all information also available in
 * {@link com.syncnapsis.websockets.service.rpc.RPCCall}.<br>
 * Database entity is separated for two reasons:
 * <ul>
 * <li>keeping persistance annotations away from the websockets module</li>
 * <li>storing the arguments using json-serialization for better debugability</li>
 * </ul>
 * 
 * @author ultimate
 */
@Embeddable
public class RPCCall implements Model
{

	/**
	 * The RPCCall-Object
	 * 
	 * @see com.syncnapsis.websockets.service.rpc.RPCCall#getObject()
	 */
	protected String	object;
	/**
	 * The RPCCall-Method
	 * 
	 * @see com.syncnapsis.websockets.service.rpc.RPCCall#getMethod()
	 */
	protected String	method;
	/**
	 * The RPCCall-Args serialized as a String
	 * 
	 * @see com.syncnapsis.websockets.service.rpc.RPCCall#getArgs()
	 */
	protected String	args;

	/**
	 * The RPCCall-Object
	 * 
	 * @see com.syncnapsis.websockets.service.rpc.RPCCall#getObject()
	 * @return object
	 */
	@Column(nullable = false, length = LENGTH_NAME_LONG)
	public String getObject()
	{
		return object;
	}

	/**
	 * The RPCCall-Method
	 * 
	 * @see com.syncnapsis.websockets.service.rpc.RPCCall#getMethod()
	 * @return method
	 */
	@Column(nullable = false, length = LENGTH_NAME_LONG)
	public String getMethod()
	{
		return method;
	}

	/**
	 * The RPCCall-Args serialized as a String
	 * 
	 * @see com.syncnapsis.websockets.service.rpc.RPCCall#getArgs()
	 * @return args
	 */
	@Column(nullable = false, length=LENGTH_TEXT)
	public String getArgs()
	{
		return args;
	}

	/**
	 * The RPCCall-Object
	 * 
	 * @see com.syncnapsis.websockets.service.rpc.RPCCall#getObject()
	 * @param object - the RPC-Object
	 */
	public void setObject(String object)
	{
		this.object = object;
	}

	/**
	 * The RPCCall-Method
	 * 
	 * @see com.syncnapsis.websockets.service.rpc.RPCCall#getMethod()
	 * @param method - the RPC-Method
	 */
	public void setMethod(String method)
	{
		this.method = method;
	}

	/**
	 * The RPCCall-Args serialized as a String
	 * 
	 * @see com.syncnapsis.websockets.service.rpc.RPCCall#getArgs()
	 * @param args - the RPC-Args
	 */
	public void setArgs(String args)
	{
		this.args = args;
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
		result = prime * result + ((args == null) ? 0 : args.hashCode());
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
		if(args == null)
		{
			if(other.args != null)
				return false;
		}
		else if(!args.equals(other.args))
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
		builder.append("object", object).append("method", method).append("args", args);
		return builder.toString();
	}
}

/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.websockets.service.rpc;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.websockets.Connection;

/**
 * A simple base for RPCHandlers that delegate {@link RPCHandler#doRPC(RPCCall, Object...)} to other
 * RPCHandlers.<br>
 * This class just holds the delegate to pass the method calls and may be extended for pre- or
 * post-processing of the RPCCall.
 * 
 * @author ultimate
 */
public class DelegatingRPCHandler implements RPCHandler, InitializingBean
{
	/**
	 * The RPCHandler to delegate the RPCCalls to
	 */
	protected RPCHandler	delegate;

	/**
	 * The RPCHandler to delegate the RPCCalls to
	 * 
	 * @return delegate
	 */
	public RPCHandler getDelegate()
	{
		return delegate;
	}

	/**
	 * The RPCHandler to delegate the RPCCalls to
	 * 
	 * @param delegate - the RPCHandler
	 */
	public void setDelegate(RPCHandler delegate)
	{
		this.delegate = delegate;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(delegate, "delegate must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.rpc.RPCHandler#doRPC(com.syncnapsis.websockets.service.
	 * rpc.RPCCall, java.lang.Object[])
	 */
	@Override
	public Object doRPC(RPCCall call, Object... authorities) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{
		return delegate.doRPC(call, authorities);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.RPCHandler#getClientInstance(java.lang.String,
	 * com.syncnapsis.websockets.Connection)
	 */
	@Override
	public Object getClientInstance(String objectName, Connection connection)
	{
		return delegate.getClientInstance(objectName, connection);
	}
}

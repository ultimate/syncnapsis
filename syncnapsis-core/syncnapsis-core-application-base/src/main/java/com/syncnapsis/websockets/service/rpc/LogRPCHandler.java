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
import java.util.Date;

import org.springframework.util.Assert;

import com.syncnapsis.data.service.RPCLogManager;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.providers.UserProvider;

/**
 * Delegating RPCHandler that logs all incoming RPCCalls and their results to the Database.
 * 
 * @author ultimate
 */
public class LogRPCHandler extends DelegatingRPCHandler
{
	/**
	 * The SessionProvider used to access the current session.
	 */
	protected SessionProvider			sessionProvider;

	/**
	 * The TimeProvider used to access the current time
	 */
	protected TimeProvider				timeProvider;

	/**
	 * The UserProvider used to access the current user.
	 */
	protected UserProvider				userProvider;

	/**
	 * The RPCLogManager used for logging the RPCCalls.
	 */
	protected RPCLogManager				rpcLogManager;

	/**
	 * The SessionProvider used to access the current session.
	 * 
	 * @return sessionProvider
	 */
	public SessionProvider getSessionProvider()
	{
		return sessionProvider;
	}

	/**
	 * The SessionProvider used to access the current session.
	 * 
	 * @param sessionProvider - the SessionProvider
	 */
	public void setSessionProvider(SessionProvider sessionProvider)
	{
		this.sessionProvider = sessionProvider;
	}

	/**
	 * The TimeProvider used to access the current time
	 * 
	 * @return timeProvider
	 */
	public TimeProvider getTimeProvider()
	{
		return timeProvider;
	}

	/**
	 * The TimeProvider used to access the current time
	 * 
	 * @param timeProvider - the TimeProvider
	 */
	public void setTimeProvider(TimeProvider timeProvider)
	{
		this.timeProvider = timeProvider;
	}

	/**
	 * The UserProvider used to access the current user.
	 * 
	 * @return userProvider
	 */
	public UserProvider getUserProvider()
	{
		return userProvider;
	}

	/**
	 * The UserProvider used to access the current user.
	 * 
	 * @param userProvider - the UserProvider
	 */
	public void setUserProvider(UserProvider userProvider)
	{
		this.userProvider = userProvider;
	}

	/**
	 * The RPCLogManager used for logging the RPCCalls.
	 * 
	 * @return rpcLogManager
	 */
	public RPCLogManager getRpcLogManager()
	{
		return rpcLogManager;
	}

	/**
	 * The RPCLogManager used for logging the RPCCalls.
	 * 
	 * @param rpcLogManager - the RPCLogManager
	 */
	public void setRpcLogManager(RPCLogManager rpcLogManager)
	{
		this.rpcLogManager = rpcLogManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.DelegatingRPCHandler#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();

		Assert.notNull(sessionProvider, "sessionProvider must not be null!");
		Assert.notNull(timeProvider, "timeProvider must not be null!");
		Assert.notNull(userProvider, "userProvider must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.rpc.DelegatingRPCHandler#doRPC(com.syncnapsis.websockets
	 * .service.rpc.RPCCall, java.lang.Object[])
	 */
	@Override
	public Object doRPC(RPCCall call, Object... authorities) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Date executionDate = new Date(timeProvider.get());
		try
		{
			Object result = delegate.doRPC(call, authorities);
			rpcLogManager.log(call, result, executionDate, userProvider.get(), sessionProvider.get(), authorities);
			return result;
		}
		catch(IllegalAccessException e)
		{
			rpcLogManager.log(call, e, executionDate, userProvider.get(), sessionProvider.get(), authorities);
			throw e;
		}
		catch(IllegalArgumentException e)
		{
			rpcLogManager.log(call, e, executionDate, userProvider.get(), sessionProvider.get(), authorities);
			throw e;
		}
		catch(InvocationTargetException e)
		{
			rpcLogManager.log(call, e.getCause(), executionDate, userProvider.get(), sessionProvider.get(), authorities);
			throw e;
		}
	}
}

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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.security.AccessController;
import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.serialization.Serializer;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.Service;
import com.syncnapsis.websockets.service.InterceptorService;

/**
 * Generic RPCHandler that executes the RPC via Reflections.
 * Therefore the target is scanned for suitable Method for the given arguments.
 * 
 * @author ultimate
 */
public abstract class GenericRPCHandler implements RPCHandler, InitializingBean
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * The Serializer used to (de)-serialize messages.
	 */
	protected Serializer<String>		serializer;

	/**
	 * The Serializer used to (de)-serialize messages.
	 * 
	 * @return serializer
	 */
	public Serializer<String> getSerializer()
	{
		return serializer;
	}

	/**
	 * The Serializer used to (de)-serialize messages.
	 * 
	 * @param serializer - the Serializer
	 */
	public void setSerializer(Serializer<String> serializer)
	{
		this.serializer = serializer;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(serializer, "serializer must not be null");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.rpc.RPCHandler#doRPC(com.syncnapsis.websockets.service.
	 * rpc.RPCCall)
	 */
	@Override
	public Object doRPC(RPCCall call, Object... authorities) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Object target = this.getTarget(call.getObject());

		Method method = this.getMethod(target, call.getMethod(), call.getArgs(), authorities);

//		if(logger.isDebugEnabled())
//		{
//			logger.debug("RPCCall (modified):");
//			logger.debug("  Object:          " + call.getObject());
//			logger.debug("  Method:          " + call.getMethod());
//			logger.debug("  Args:");
//			for(Object arg : call.getArgs())
//				logger.debug("                 " + arg + (arg != null ? " (" + arg.getClass() + ")" : ""));
//			logger.debug("  Args (modified):");
//			for(Object arg : call.getArgs())
//				logger.debug("                 " + arg + (arg != null ? " (" + arg.getClass() + ")" : ""));
//			logger.debug("Method found: " + method);
//		}
		
		call.setInvocationInfo(new InvocationInfo(target, method));

		if(isAccessible(target, method, authorities))
		{
			Object result = method.invoke(target, call.getArgs());
			if(method.getReturnType().equals(void.class))
				return Void.TYPE;
//				invocationInfo.setResult(Void.TYPE);
//			else
//				invocationInfo.setResult(result);
//			return invocationInfo;
			return result;
		}
		else
		{
			throw new IllegalAccessException("Trying to call not accessible method '" + method.getDeclaringClass().getName() + "." + method.getName()
					+ "() with authorities " + Arrays.asList(authorities));
		}
	}

	/**
	 * Obtain the target to do the RPC on.
	 * 
	 * @param objectName - the name of the target entity
	 * @return the target or null, if the method to execute is static
	 */
	public abstract Object getTarget(String objectName);
	
	/**
	 * Obtain the method to do the RPC on.
	 * 
	 * @param target - the target object
	 * @param method - the method name
	 * @param args - the RPC arguments
	 * @param authorities - the authorities used for transforming the input arguments
	 * @return the method
	 */
	public Method getMethod(Object target, String method, Object[] args, Object... authorities)
	{
		return ReflectionsUtil.findMethodAndConvertArgs(target.getClass(), method, args, serializer.getMapper(), authorities);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.RPCHandler#getClientInstance(java.lang.String,
	 * com.syncnapsis.websockets.Connection)
	 */
	@Override
	public synchronized Object getClientInstance(String objectName, Connection connection)
	{
		if(objectName == null)
			return null;
		if(connection.getAttribute(objectName) != null)
		{
			return connection.getAttribute(objectName);
		}
		else
		{
			Object original = getTarget(objectName);
			Object proxy = Proxy.newProxyInstance(original.getClass().getClassLoader(), original.getClass().getInterfaces(),
					getInvocationHandler(objectName, connection));
			connection.setAttribute(objectName, proxy);
			return proxy;
		}
	}

	/**
	 * Get the GenericRPCInvocationHandler required for creating the ClientInstance-Proxy
	 * 
	 * @param objectName - The name of the Object the Proxy will be created for
	 * @param connection - The Connection to the Client the Proxy is standing for.
	 * @return the RPCInvocationHandler
	 */
	protected GenericRPCInvocationHandler getInvocationHandler(String objectName, Connection connection)
	{
		return new GenericRPCInvocationHandler(objectName, connection);
	}

	/**
	 * Check wether a method is accessible with the given authorities using the registered
	 * AccessController.
	 * 
	 * @see AccessController#isAccessible(Object, int, Object...)
	 * @see GenericRPCHandler#getAccessController()
	 * @param target - the target to access
	 * @param method - the method to check
	 * @param authorities - the authorities
	 * @return true or false
	 */
	public boolean isAccessible(Object target, Method method, Object... authorities)
	{
		// TODO ownable!?
		AccessController<Method> controller = getAccessController();
		if(controller == null)
			return true;
		else
			return controller.isAccessible(method, AccessController.INVOKE, target, authorities);

	}

	/**
	 * Get the AccessController for Methods registered at the SecurityManager.
	 * 
	 * @return the AccessController, or null if none is registered
	 */
	protected AccessController<Method> getAccessController()
	{
		if(serializer.getMapper().getSecurityManager() == null)
			return null;
		else
			return serializer.getMapper().getSecurityManager().getAccessController(Method.class);
	}

	/**
	 * InvocationHandler that converts a Method-Invocation into arguments for the RPCService.
	 * 
	 * @author ultimate
	 */
	protected class GenericRPCInvocationHandler implements InvocationHandler
	{
		/**
		 * The name for the Object represented by the Proxy
		 */
		private String		objectName;
		/**
		 * The Connection to the Client the Proxy is standing for.
		 */
		private Connection	connection;
		/**
		 * The RPCService which is used to communicate using the Connection
		 */
		private RPCService	rpcService;

		/**
		 * Construct a new GenericRPCInvocationHandler
		 * 
		 * @param objectName - The name for the Object represented by the Proxy
		 * @param connection - The Connection to the Client the Proxy is standing for.
		 */
		public GenericRPCInvocationHandler(String objectName, Connection connection)
		{
			super();
			Assert.notNull(connection, "connection must not be null!");

			// since interrupted Services are possible we have to check for
			// the real RPCService for being able to do the RPC
			Service tmp = connection.getService();
			while(tmp != null && !(tmp instanceof RPCService) && tmp instanceof InterceptorService)
			{
				tmp = ((InterceptorService) tmp).getInterceptedService();
			}
			Assert.isTrue(tmp instanceof RPCService);

			this.objectName = objectName;
			this.connection = connection;
			this.rpcService = (RPCService) tmp;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
		 * java.lang.reflect.Method,
		 * java.lang.Object[])
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
		{
			return this.rpcService.call(this.connection, this.objectName, method.getName(), args, method.getReturnType());
		}
	}
}

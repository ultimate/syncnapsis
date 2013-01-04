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
package com.syncnapsis.websockets.service.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import com.syncnapsis.security.AccessController;
import com.syncnapsis.utils.serialization.Serializer;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.Service;
import com.syncnapsis.websockets.service.InterceptorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

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
	public Object doRPC(RPCCall call, Object... authorities)
	{
		Object target = this.getTarget(call.getObject());

		logger.debug("RPCCall (modified):");
		logger.debug("  Object:          " + call.getObject());
		logger.debug("  Method:          " + call.getMethod());
		// logger.debug("  Args:            " + Arrays.asList(call.getArgs()));
		logger.debug("  Args:");
		for(Object arg : call.getArgs())
			logger.debug("                 " + arg + (arg != null ? " (" + arg.getClass() + ")" : ""));

		// Method method = ReflectionsUtil.findMethod(target.getClass(), call.getMethod(),
		// call.getArgs());
		Method method = findMethodAndConvertArgs(target.getClass(), call.getMethod(), call.getArgs(), authorities);

		// logger.debug("  Args (modified): " + Arrays.asList(call.getArgs()));
		logger.debug("  Args (modified):");
		for(Object arg : call.getArgs())
			logger.debug("                 " + arg + (arg != null ? " (" + arg.getClass() + ")" : ""));
		logger.debug("Method found: " + method);

		try
		{
			if(isAccessible(method, authorities))
			{
				Object result = method.invoke(target, call.getArgs());
				if(method.getReturnType().equals(void.class))
					return Void.TYPE;
				return result;
			}
			else
			{
				logger.warn("Trying to call not accessible method '" + method.getDeclaringClass().getName() + "." + method.getName()
						+ "() with authorities " + Arrays.asList(authorities));
			}
		}
		catch(IllegalAccessException e)
		{
			logger.error("Exception doing RPC", e);
		}
		catch(InvocationTargetException e)
		{
			logger.error("Exception doing RPC", e);
		}
		return null;
	}

	/**
	 * Obtain the target to do the RPC on.
	 * 
	 * @param objectName - the name of the target entity
	 * @return the target or null, if the method to execute is static
	 */
	public abstract Object getTarget(String objectName);

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

	public Method findMethodAndConvertArgs(Class<?> cls, String method, Object[] args, Object... authorities)
	{
		logger.trace("looking for " + method);
		// when Generics are present getMethod won't work well!
		for(Method m : cls.getMethods())
		{
			logger.trace("  comparing " + m.getName());
			if(m.getName().equals(method))
			{
				// check if the given args are suitable for the Method
				// also check for var args and convertable types...
				Object[] tmp = Arrays.copyOf(args, args.length);
				if(checkAndConvertArgs(m, tmp, authorities))
				{
					// tmp has been modified
					// copy changes back to args
					for(int i = 0; i < args.length; i++)
						args[i] = tmp[i];
					return m;
				}
			}
		}
		return null;
	}

	protected boolean checkAndConvertArgs(Method m, Object[] args, Object... authorities)
	{
		int params = m.getParameterTypes().length;
		logger.trace(params + (m.isVarArgs() ? "(varArgs)" : "") + " vs. " + args.length);
		if(m.isVarArgs())
		{
			if(params > args.length + 1)
				return false;
		}
		else if(params != args.length)
		{
			return false;
		}

		// check each type to the given argument
		// if possible (and necessary) convert the arg to the type (e.g. Long to int)
		Class<?> type = null;
		boolean varArgsUsed = false;
		for(int i = 0; i < args.length; i++)
		{
			// if we have more args than params, we have varArgs X[]
			// --> every additional arg must be of type X
			// for the arg at the varArg Position both X[] is only suitable if no more args follow
			logger.trace("  " + m.getParameterTypes()[i >= params ? params - 1 : i] + " vs. " + args[i]);
			if(i < params - 1)
			{
				type = m.getParameterTypes()[i];
				if(checkAndConvertArg(type, i, args, authorities))
					continue;
				return false;
			}
			else if(i == params - 1)
			{
				type = m.getParameterTypes()[i];
				if(checkAndConvertArg(type, i, args, authorities))
				{
					// if arg is null it may be X[] or X, but only if X is not primitive
					// set varArgsUsed to true if this is the case in order to check following args
					if(args[i] == null && type.getComponentType() != null && !type.getComponentType().isPrimitive())
						varArgsUsed = true;
					continue;
				}
				else if(m.isVarArgs())
				{
					type = type.getComponentType(); // will be available for following args, too
					varArgsUsed = true;
					if(checkAndConvertArg(type, i, args, authorities))
						continue;
				}
				return false;
			}
			else
			{
				if(varArgsUsed)
				{
					if(checkAndConvertArg(type, i, args, authorities))
						continue;
				}
				else
				{
					// array is used instead of varArgs
					return false;
				}
			}
		}
		return true;
	}

	protected boolean checkAndConvertArg(Class<?> requiredType, int arg, Object[] args, Object... authorities)
	{
		if(requiredType.isPrimitive() || Number.class.isAssignableFrom(requiredType) || Character.class == requiredType
				|| Boolean.class == requiredType)
		{
			// Numbers, Char, Boolean here
			if(args[arg] == null)
			{
				if(requiredType.isPrimitive())
					return false;
				else
					return true;
			}
			if(requiredType == int.class || requiredType == Integer.class)
			{
				if(args[arg] instanceof Number)
					args[arg] = ((Number) args[arg]).intValue();
				else if(args[arg] instanceof Character)
					args[arg] = (int) ((Character) args[arg]).charValue();
				else
					return false;
			}
			else if(requiredType == long.class || requiredType == Long.class)
			{
				if(args[arg] instanceof Number)
					args[arg] = ((Number) args[arg]).longValue();
				else if(args[arg] instanceof Character)
					args[arg] = (long) ((Character) args[arg]).charValue();
				else
					return false;
			}
			else if(requiredType == double.class || requiredType == Double.class)
			{
				if(args[arg] instanceof Number)
					args[arg] = ((Number) args[arg]).doubleValue();
				else if(args[arg] instanceof Character)
					args[arg] = (double) ((Character) args[arg]).charValue();
				else
					return false;
			}
			else if(requiredType == float.class || requiredType == Float.class)
			{
				if(args[arg] instanceof Number)
					args[arg] = ((Number) args[arg]).floatValue();
				else if(args[arg] instanceof Character)
					args[arg] = (float) ((Character) args[arg]).charValue();
				else
					return false;
			}
			else if(requiredType == byte.class || requiredType == Byte.class)
			{
				if(args[arg] instanceof Number)
					args[arg] = ((Number) args[arg]).byteValue();
				else if(args[arg] instanceof Character)
					args[arg] = (byte) ((Character) args[arg]).charValue();
				else
					return false;
			}
			else if(requiredType == short.class || requiredType == Short.class)
			{
				if(args[arg] instanceof Number)
					args[arg] = ((Number) args[arg]).shortValue();
				else if(args[arg] instanceof Character)
					args[arg] = (short) ((Character) args[arg]).charValue();
				else
					return false;
			}
			else if(requiredType == char.class || requiredType == Character.class)
			{
				if(args[arg] instanceof Number)
					args[arg] = (char) ((Number) args[arg]).intValue();
				else if(args[arg] instanceof Character)
					args[arg] = ((Character) args[arg]).charValue();
				else
					return false;
			}
			else if(requiredType == boolean.class || requiredType == Boolean.class)
			{
				if(args[arg] instanceof Boolean)
					return true;
				else
					return false;
			}
		}
		else
		{
			// other objects
			if(args[arg] == null)
				return true;
			if(requiredType == Object.class)
				return true;
			else if(requiredType.isAssignableFrom(args[arg].getClass()))
				return true;
			else
			{
				Object tmp = convertArg(requiredType, args[arg], authorities);
				if(tmp == null)
				{
					// merging failed
					return false;
				}
				else
				{
					args[arg] = tmp;
					return true;
				}
			}
		}
		return true;
	}

	/**
	 * Convert the given argument to the required type if possible.
	 * 
	 * @param requiredType - the required type for the argument
	 * @param arg - the argument to convert
	 * @param authorities - the authorities to be considered for merging
	 * @return the merged argument
	 */
	protected Object convertArg(Class<?> requiredType, Object arg, Object... authorities)
	{
		return serializer.getMapper().merge(requiredType, arg, authorities);
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
	 * @param method - the method to check
	 * @param authorities - the authorities
	 * @return true or false
	 */
	public boolean isAccessible(Method method, Object... authorities)
	{
		AccessController<Method> controller = getAccessController();
		if(controller == null)
			return true;
		else
			return controller.isAccessible(method, AccessController.INVOKE, authorities);

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

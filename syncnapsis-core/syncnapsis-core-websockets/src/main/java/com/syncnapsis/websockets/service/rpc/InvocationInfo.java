package com.syncnapsis.websockets.service.rpc;

import java.lang.reflect.Method;

/**
 * Extended {@link RPCCall} information on the Object and Method used for invocation.
 * 
 * @author ultimate
 */
public class InvocationInfo
{
	/**
	 * The real Object used to invoke the Method (not just the name)
	 */
	private Object	object;
	/**
	 * The real Method invoked (not just the name)
	 */
	private Method	method;

//	/**
//	 * The result returned by the invocation
//	 */
//	private Object	result;

	/**
	 * Create a new InvocationInfo with a given object and method
	 * 
	 * @param object - The real Object used to invoke the Method (not just the name)
	 * @param method - The real Method invoked (not just the name)
	 */
	public InvocationInfo(Object object, Method method)
	{
		super();
		this.object = object;
		this.method = method;
	}

	/**
	 * The real Object used to invoke the Method (not just the name)
	 * 
	 * @return the object
	 */
	public Object getObject()
	{
		return object;
	}

	/**
	 * The real Method invoked (not just the name)
	 * 
	 * @return the method
	 */
	public Method getMethod()
	{
		return method;
	}

//	/**
//	 * The result returned by the invocation
//	 * 
//	 * @return the result
//	 */
//	public Object getResult()
//	{
//		return result;
//	}
//
//	/**
//	 * The result returned by the invocation
//	 * 
//	 * @param result - the result
//	 */
//	public void setResult(Object result)
//	{
//		this.result = result;
//	}
}

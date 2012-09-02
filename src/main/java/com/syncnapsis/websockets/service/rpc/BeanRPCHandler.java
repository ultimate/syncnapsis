package com.syncnapsis.websockets.service.rpc;

import com.syncnapsis.utils.ApplicationContextUtil;

/**
 * Extension of GenericRPCHandler allowing access to the Spring ApplicationContext for client-server
 * and server-client RPC.
 * Therefore getTarget(String) forwards to ApplicationContextUtil#getBean(String)
 * and getTargetName(Object) forwards to ApplicationContextUtil#getBeanName(Object)
 * 
 * @see ApplicationContextUtil#getBean(String)
 * @see ApplicationContextUtil#getBeanName(Object)
 * @author ultimate
 */
public class BeanRPCHandler extends GenericRPCHandler
{
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.GenericRPCHandler#getTarget(java.lang.String)
	 */
	@Override
	public Object getTarget(String objectName)
	{
		return ApplicationContextUtil.getBean(objectName);
	}
}

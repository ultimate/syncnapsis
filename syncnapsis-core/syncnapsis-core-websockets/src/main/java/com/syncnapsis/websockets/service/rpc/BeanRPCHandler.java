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

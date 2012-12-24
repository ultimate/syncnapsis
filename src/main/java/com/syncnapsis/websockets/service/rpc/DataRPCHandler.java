/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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

import java.io.Serializable;
import java.util.Map;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.UniversalManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Extension of BeanRPCHandler offering additional support for rmote procedure calls to Manager
 * and/or Dao-Classes from com.syncnapsis.data.<br>
 * On RPC the given parameters will be checked for their required argument type and if necessary be
 * preloaded from the database for merging. This way only property changes have to be transmitted
 * and all invariant values will autmomatically be obtained from the database.
 * 
 * @author ultimate
 */
public class DataRPCHandler extends BeanRPCHandler implements InitializingBean
{
	/**
	 * The key used for the ID of an Object in it's serialization Map.
	 */
	public static final String	ID_KEY	= "id";
	/**
	 * The UniversalManager used to retrieve entries from the Database
	 */
	protected UniversalManager	universalManager;

	/**
	 * The UniversalManager used to retrieve entries from the Database
	 * 
	 * @return universalManager
	 */
	public UniversalManager getUniversalManager()
	{
		return universalManager;
	}

	/**
	 * The UniversalManager used to retrieve entries from the Database
	 * 
	 * @param universalManager - the UniversalManager
	 */
	public void setUniversalManager(UniversalManager universalManager)
	{
		this.universalManager = universalManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.GenericRPCHandler#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(universalManager, "universalManager must not be null");
		super.afterPropertiesSet();
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.GenericRPCHandler#convertArg(java.lang.Class,
	 * java.lang.Object, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Object convertArg(Class<?> requiredType, Object arg, Object... authorities)
	{
		if(BaseObject.class.isAssignableFrom(requiredType))
		{
			if(arg instanceof Map && ((Map<String, Object>) arg).containsKey(ID_KEY))
			{
				// get instance of required type with requested ID from DB if possible
				Serializable id = (Serializable) ((Map<String, Object>) arg).get(ID_KEY);
				if(id != null)
				{
					BaseObject<?> entity = universalManager.get((Class<? extends BaseObject<?>>) requiredType, id);
					if(entity != null)
						return serializer.getMapper().merge(entity, arg, authorities);
				}
			}
		}
		return super.convertArg(requiredType, arg, authorities);
	}
}

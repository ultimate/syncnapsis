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
package com.syncnapsis.utils.serialization;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Identifiable;
import com.syncnapsis.data.service.UniversalManager;

/**
 * 
 * Extension of BaseMapper offering additional support for merging models-classes (from
 * com.syncnapsis.data.*).<br>
 * On merging the given prepared instance will be checked for their required type and if necessary
 * be preloaded from the database for merging. This way only property changes have to be transmitted
 * and all invariant values will autmomatically be obtained from the database.
 * 
 * @author ultimate
 */
public class DataMapper extends BaseMapper
{
	/**
	 * The key used for the ID of an Object in it's serialization Map.
	 */
	public static final String		KEY_ID		= "id";
	/**
	 * The key used for the Java Type of an Object in it's serialization Map.
	 */
	public static final String		KEY_TYPE	= "j_type";

	/**
	 * Since we cannot add additional recursively used parameters to prepare(..) we use a
	 * ThreadLocal to store protocol parameters during mapping for the current process.<br>
	 */
	private final Protocol	protocol	= new Protocol();

	/**
	 * The UniversalManager used to retrieve entries from the Database
	 */
	protected UniversalManager		universalManager;

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

	// /*
	// * (non-Javadoc)
	// * @see com.syncnapsis.utils.serialization.BaseMapper#merge(java.lang.Class, java.lang.Object,
	// * java.lang.Object[])
	// */
	// @SuppressWarnings("unchecked")
	// @Override
	// public <T> T merge(Class<? extends T> type, Object prepared, Object... authorities)
	// {
	// if(BaseObject.class.isAssignableFrom(type))
	// {
	// if(prepared instanceof Map && ((Map<String, Object>) prepared).containsKey(KEY_ID))
	// {
	// // get instance of required type with requested ID from DB if possible
	// Serializable id = (Serializable) ((Map<String, Object>) prepared).get(KEY_ID);
	// if(id != null)
	// {
	// BaseObject<?> entity = universalManager.get((Class<? extends BaseObject<?>>) type, id);
	// if(entity != null)
	// return (T) merge(entity, prepared, authorities);
	// }
	// }
	// }
	// return super.merge(type, prepared, authorities);
	// }
	//
	// /*
	// * (non-Javadoc)
	// * @see com.syncnapsis.utils.serialization.BaseMapper#prepare(java.lang.Object,
	// * java.lang.Object[])
	// */
	// @SuppressWarnings("unchecked")
	// @Override
	// public Object prepare(Object entity, Object... authorities)
	// {
	// Object prepared;
	//
	// logger.debug("reference levels left: " + depth.get());
	//
	// if(isInvariant(entity))
	// return entity;
	//
	// if(depth.get() != 0 && entity instanceof Identifiable)
	// {
	// HashMap<String, Object> emptyMap = new HashMap<String, Object>();
	// emptyMap.put("id", ((Identifiable<?>) entity).getId());
	// prepared = emptyMap;
	// }
	// else
	// {
	// depth.increase();
	// prepared = super.prepare(entity, authorities);
	// depth.decrease();
	// }
	//
	// if(prepared instanceof Map && entity instanceof BaseObject)
	// {
	// ((Map<String, Object>) prepared).put(KEY_TYPE, entity.getClass().getName());
	// }
	//
	// if(depth.get() == 0)
	// {
	// // not mandatory
	// depth.remove();
	// }
	//
	// return prepared;
	// }

	@Override
	public Map<String, Object> toMap(Object entity, Object... authorities)
	{
		Map<String, Object> map;
		if(entity instanceof BaseObject)
		{
			if(!protocol.baseObjectMapped())
			{
				protocol.markBaseObjectMapped();
				map = super.toMap(entity, authorities);
				protocol.reset();
			}
			else
			{
				map = new HashMap<String, Object>();
				map.put("id", ((Identifiable<?>) entity).getId());
			}
			map.put(KEY_TYPE, entity.getClass().getName());
		}
		else
		{
			map = super.toMap(entity, authorities);
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.BaseMapper#fromMap(java.lang.Object, java.util.Map,
	 * java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromMap(T entity, Map<String, Object> map, Object... authorities)
	{
		if(entity instanceof BaseObject && map.containsKey(KEY_ID))
		{
			Serializable id = (Serializable) map.get(KEY_ID);
			if(id != null)
				entity = (T) universalManager.get(entity.getClass(), id);
		}
		return super.fromMap(entity, map, authorities);
	}

	/**
	 * Simple extension of ThreadLocal providing additionl increase(..) and decrease(..)
	 * functionality.
	 * 
	 * @author ultimate
	 */
	private class Protocol extends ThreadLocal<Boolean>
	{
		/*
		 * (non-Javadoc)
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		@Override
		protected Boolean initialValue()
		{
			return false;
		}

		/**
		 * Mark the protocol for a BaseObject beeing mapped
		 */
		public void markBaseObjectMapped()
		{
			set(true);
		}

		/**
		 * Has a BaseObject been mapped?
		 * 
		 * @return true or false
		 */
		public boolean baseObjectMapped()
		{
			return get();
		}

		/**
		 * Reset the status for the Protocol
		 */
		public void reset()
		{
			set(false);
		}
	};
}

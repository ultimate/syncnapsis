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
package com.syncnapsis.utils.serialization;

import java.util.HashMap;

import com.syncnapsis.data.model.base.Identifiable;

/**
 * Quick workaround extending {@link BaseMapper} for enabling cyclic references.<br>
 * The {@link LimitedReferenceDepthMapper} is initiated with a maximum referenceDepth which controls
 * how many references will be included in the map when preparing an Object.<br>
 * <br>
 * WARNING: This class will probably be removed later!<br>
 * 
 * @author ultimate
 */
public class LimitedReferenceDepthMapper extends BaseMapper
{
	/**
	 * The maximum references depth to process
	 */
	protected int			depthLimit;

	/**
	 * Since we cannot add additional recursive used depth parameters to prepare(..) we use a
	 * ThreadLocal to store the preparation depth for current process.<br>
	 * The depth used represents the number of references left to include while preparing and will
	 * be reduced everytime a new reference level is reached.
	 */
	private DepthCounter	depth	= new DepthCounter();

	/**
	 * The maximum references depth to process
	 * 
	 * @return depthLimit
	 */
	public int getDepthLimit()
	{
		return depthLimit;
	}

	/**
	 * The maximum references depth to process
	 * 
	 * @param depthLimit - the depth
	 */
	public void setDepthLimit(int depthLimit)
	{
		this.depthLimit = depthLimit;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.BaseMapper#prepare(java.lang.Object,
	 * java.lang.Object[])
	 */
	@Override
	public Object prepare(Object entity, Object... authorities)
	{
		Object prepared;

		logger.debug("reference levels left: " + depth.get());
		
		if(isInvariant(entity))
			return entity;
		
		if(depth.get() < 0)
		{
			HashMap<String, Object> emptyMap = new HashMap<String, Object>();
			if(entity instanceof Identifiable)
			{
				emptyMap.put("id", ((Identifiable<?>) entity).getId());
			}
			prepared = emptyMap;
		}
		else
		{
			depth.decrease();
			prepared = super.prepare(entity, authorities);
			depth.increase();
		}
		
		if(depth.get() == depthLimit)
		{
			// reset the thread local value for this thread for capability of
			// updating the depth limit between multiple preparations in one thread
			// (otherwise the thread local value would remain unchanged and not be
			// reinited when setDepthLimit(..) is called)
			depth.remove();
		}

		return prepared;
	}

	/**
	 * Simple extension of ThreadLocal providing additionl increase(..) and decrease(..)
	 * functionality.
	 * 
	 * @author ultimate
	 */
	private class DepthCounter extends ThreadLocal<Integer>
	{
		/*
		 * (non-Javadoc)
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		@Override
		protected Integer initialValue()
		{
			return depthLimit;
		}

		/**
		 * Increase the depth value
		 */
		public void increase()
		{
			set(get() + 1);
		};

		/**
		 * Decrease the depth value
		 */
		public void decrease()
		{
			set(get() - 1);
		};
	};
}

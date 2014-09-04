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
package com.syncnapsis.data.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.utils.MapUtil;
import com.syncnapsis.utils.serialization.Mapable;

/**
 * A Window-POJO
 * 
 * @author ultimate
 */
@Entity(name="guiwindow")
public class Window extends BaseObject<Long> implements Mapable<Window>
{
	private int		positionX;
	private int		positionY;
	private int		width;
	private int		height;
	private double	rotation;

	public int getPositionX()
	{
		return positionX;
	}

	public void setPositionX(int positionX)
	{
		this.positionX = positionX;
	}

	public int getPositionY()
	{
		return positionY;
	}

	public void setPositionY(int positionY)
	{
		this.positionY = positionY;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public double getRotation()
	{
		return rotation;
	}

	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + height;
		result = prime * result + positionX;
		result = prime * result + positionY;
		long temp;
		temp = Double.doubleToLongBits(rotation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + width;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		Window other = (Window) obj;
		if(height != other.height)
			return false;
		if(positionX != other.positionX)
			return false;
		if(positionY != other.positionY)
			return false;
		if(Double.doubleToLongBits(rotation) != Double.doubleToLongBits(other.rotation))
			return false;
		if(width != other.width)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapable#fromMap(java.util.Map, java.lang.Object[])
	 */
	@Override
	public Window fromMap(Map<String, ?> map, Object... views)
	{
		// no views yet
		if(this.id == null)
			this.id = MapUtil.getLong(map, "id", true);
		this.positionX = MapUtil.getInteger(map, "positionX", false);
		this.positionY = MapUtil.getInteger(map, "positionY", false);
		this.width = MapUtil.getInteger(map, "width", false);
		this.height = MapUtil.getInteger(map, "height", false);
		this.rotation = MapUtil.getDouble(map, "rotation", false, false);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapable#toMap(java.lang.Object[])
	 */
	@Override
	public Map<String, ?> toMap(Object... views)
	{
		// no views yet
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("positionX", this.positionX);
		map.put("positionY", this.positionY);
		map.put("width", this.width);
		map.put("height", this.height);
		map.put("rotation", this.rotation);
		return map;
	}	
}

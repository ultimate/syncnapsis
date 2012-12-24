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
package com.syncnapsis.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.syncnapsis.data.model.base.ActivatableInstance;

/**
 * Entity representation of a simple solar system.<br>
 * The solar system only described via a 3-dimensional position within a galaxy and it's
 * environmental parameters (max population, habitability, growth rate) but does not consist of
 * multiple planets.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "solarsystem")
public class SolarSystem extends ActivatableInstance<Long>
{
	/**
	 * The Galaxy this solar system is in
	 */
	protected Galaxy	galaxy;

	/**
	 * The name of this solar system
	 */
	protected String	name;

	/**
	 * The X-Component of the Coordinates/Position of this solar system
	 */
	protected int		coordinateX;
	/**
	 * The Y-Component of the Coordinates/Position of this solar system
	 */
	protected int		coordinateY;
	/**
	 * The Z-Component of the Coordinates/Position of this solar system
	 */
	protected int		coordinateZ;

	/**
	 * The size of this solar system (in a range from 0 to 100)
	 */
	protected int		size;

	/**
	 * The habitability of this solar system (in a range from 0 to 100)
	 */
	protected int		habitability;

	/**
	 * The Galaxy this solar system is in
	 * 
	 * @return galaxy
	 */
	@ManyToOne
	@JoinColumn(name = "fkGalaxy", nullable = false)
	public Galaxy getGalaxy()
	{
		return galaxy;
	}

	/**
	 * The name of this solar system
	 * 
	 * @return name
	 */
	@Column(nullable = false, length = LENGTH_NAME_NORMAL)
	public String getName()
	{
		return name;
	}

	/**
	 * The X-Component of the Coordinates/Position of this solar system
	 * 
	 * @return coordinateX
	 */
	@Column(nullable = false)
	public int getCoordinateX()
	{
		return coordinateX;
	}

	/**
	 * The Y-Component of the Coordinates/Position of this solar system
	 * 
	 * @return coordinateY
	 */
	@Column(nullable = false)
	public int getCoordinateY()
	{
		return coordinateY;
	}

	/**
	 * The Z-Component of the Coordinates/Position of this solar system
	 * 
	 * @return coordinateZ
	 */
	@Column(nullable = false)
	public int getCoordinateZ()
	{
		return coordinateZ;
	}

	/**
	 * The size of this solar system (in a range from 0 to 100)
	 * 
	 * @return size
	 */
	@Column(nullable = false)
	public int getSize()
	{
		return size;
	}

	/**
	 * The habitability of this solar system (in a range from 0 to 100)
	 * 
	 * @return habitability
	 */
	@Column(nullable = false)
	public int getHabitability()
	{
		return habitability;
	}

	/**
	 * The Galaxy this solar system is in
	 * 
	 * @param galaxy
	 */
	public void setGalaxy(Galaxy galaxy)
	{
		this.galaxy = galaxy;
	}

	/**
	 * The name of this solar system
	 * 
	 * @param name - the name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * The X-Component of the Coordinates/Position of this solar system
	 * 
	 * @param coordinateX - the coordinate's component
	 */
	public void setCoordinateX(int coordinateX)
	{
		this.coordinateX = coordinateX;
	}

	/**
	 * The Y-Component of the Coordinates/Position of this solar system
	 * 
	 * @param coordinateY - the coordinate's component
	 */
	public void setCoordinateY(int coordinateY)
	{
		this.coordinateY = coordinateY;
	}

	/**
	 * The Z-Component of the Coordinates/Position of this solar system
	 * 
	 * @param coordinateZ - the coordinate's component
	 */
	public void setCoordinateZ(int coordinateZ)
	{
		this.coordinateZ = coordinateZ;
	}

	/**
	 * The size of this solar system (in a range from 0 to 100)
	 * 
	 * @param size - the size
	 */
	public void setSize(int size)
	{
		this.size = size;
	}

	/**
	 * The habitability of this solar system (in a range from 0 to 100)
	 * 
	 * @param habitability - the habitability
	 */
	public void setHabitability(int habitability)
	{
		this.habitability = habitability;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + coordinateX;
		result = prime * result + coordinateY;
		result = prime * result + coordinateZ;
		result = prime * result + ((galaxy == null) ? 0 : galaxy.getId().hashCode());
		result = prime * result + habitability;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		SolarSystem other = (SolarSystem) obj;
		if(coordinateX != other.coordinateX)
			return false;
		if(coordinateY != other.coordinateY)
			return false;
		if(coordinateZ != other.coordinateZ)
			return false;
		if(galaxy == null)
		{
			if(other.galaxy != null)
				return false;
		}
		else if(!galaxy.getId().equals(other.galaxy.getId()))
			return false;
		if(habitability != other.habitability)
			return false;
		if(name == null)
		{
			if(other.name != null)
				return false;
		}
		else if(!name.equals(other.name))
			return false;
		if(size != other.size)
			return false;
		return true;
	}
}

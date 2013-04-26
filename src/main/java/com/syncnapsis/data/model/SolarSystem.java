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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.data.model.help.Vector;

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
	protected Galaxy			galaxy;

	/**
	 * The name of this solar system
	 */
	protected String			name;

	/**
	 * The the Coordinates/Position of this solar system.<br>
	 * (Initialized with default coords for convenience)
	 */
	protected Vector.Integer	coords	= new Vector.Integer();

	/**
	 * The size of this solar system (in a range from 0 to 100)
	 */
	protected int				size;

	/**
	 * The habitability of this solar system (in a range from 0 to 100)
	 */
	protected int				habitability;

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
	 * The Coordinates/Position of this solar system
	 * 
	 * @return coords
	 */
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "coordinateX", nullable = false)),
			@AttributeOverride(name = "y", column = @Column(name = "coordinateY", nullable = false)),
			@AttributeOverride(name = "z", column = @Column(name = "coordinateZ", nullable = false)) })
	public Vector.Integer getCoords()
	{
		return coords;
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
	 * The Coordinates/Position of this solar system
	 * 
	 * @param coords - the coordinates
	 */
	public void setCoords(Vector.Integer coords)
	{
		this.coords = coords;
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
		result = prime * result + ((coords == null) ? 0 : coords.hashCode());
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
		if(coords == null)
		{
			if(other.coords != null)
				return false;
		}
		else if(!coords.equals(other.coords))
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

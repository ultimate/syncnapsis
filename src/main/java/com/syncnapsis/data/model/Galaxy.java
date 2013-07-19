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

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.data.model.help.Vector;

/**
 * Entity representing a galaxy usable to create matches.<br>
 * A galaxy consists of it's basic information (name, creator, etc.) the 3-dimenstional size and a
 * list of solar systems mapped to the galaxy.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "galaxy")
public class Galaxy extends ActivatableInstance<Long>
{
	/**
	 * The name of this galaxy
	 */
	protected String			name;

	/**
	 * The creator of this galaxy
	 */
	protected Player			creator;

	/**
	 * The date and time this galaxy was created
	 */
	protected Date				creationDate;

	/**
	 * The size of the galaxy.<br>
	 * This means coordinates are within the interval <code>[ -min? ; max? ]</code> where ? is one
	 * of three coordinates and <code>size? = max? - min? + 1</code>
	 */
	protected Vector.Integer	size	= new Vector.Integer();

	/**
	 * The maximum gap between two neighbour SolarSystems.<br>
	 * If the nearest neighbour is determined for every SolarSystem this is the maximum of all those
	 * values.
	 */
	protected int				maxGap;

	/**
	 * The seed that has been used for the random generator to generate the SolarSystems
	 */
	protected long				seed;

	/**
	 * The solar systems belonging to this galaxy
	 */
	protected List<SolarSystem>	solarSystems;

	/**
	 * The matches taking place in this galaxy
	 */
	protected List<Match>		matches;

	/**
	 * The name of this galaxy
	 * 
	 * @return name
	 */
	@Column(nullable = false, length = LENGTH_NAME_LONG)
	public String getName()
	{
		return name;
	}

	/**
	 * The creator of this galaxy
	 * 
	 * @return creator
	 */
	@ManyToOne
	@JoinColumn(name = "fkCreator", nullable = false)
	public Player getCreator()
	{
		return creator;
	}

	/**
	 * The date and time this galaxy was created
	 * 
	 * @return creationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreationDate()
	{
		return creationDate;
	}

	/**
	 * The size of the galaxy.<br>
	 * This means coordinates are within the interval <code>[ -min? ; max? ]</code> where ? is one
	 * of three coordinates and <code>size? = max? - min? + 1</code>
	 * 
	 * @return size
	 */
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "sizeX", nullable = false)),
			@AttributeOverride(name = "y", column = @Column(name = "sizeY", nullable = false)),
			@AttributeOverride(name = "z", column = @Column(name = "sizeZ", nullable = false)) })
	public Vector.Integer getSize()
	{
		return size;
	}

	/**
	 * The maximum gap between two neighbour SolarSystems.<br>
	 * If the nearest neighbour is determined for every SolarSystem this is the maximum of all those
	 * values.
	 * 
	 * @return maxGap
	 */
	@Column(nullable = false)
	public int getMaxGap()
	{
		return maxGap;
	}

	/**
	 * The seed that has been used for the random generator to generate the SolarSystems
	 * 
	 * @return seed
	 */
	@Column(nullable = false)
	public long getSeed()
	{
		return seed;
	}

	/**
	 * The solar systems belonging to this galaxy
	 * 
	 * @return solarSystems
	 */
	@OneToMany(mappedBy = "galaxy")
	public List<SolarSystem> getSolarSystems()
	{
		return solarSystems;
	}

	/**
	 * The matches taking place in this galaxy
	 * 
	 * @return matches
	 */
	@OneToMany(mappedBy = "galaxy")
	public List<Match> getMatches()
	{
		return matches;
	}

	/**
	 * The name of this galaxy
	 * 
	 * @param name - the name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * The creator of this galaxy
	 * 
	 * @param creator - the creator
	 */
	public void setCreator(Player creator)
	{
		this.creator = creator;
	}

	/**
	 * The date and time this galaxy was created
	 * 
	 * @param creationDate - the date and time
	 */
	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * The size of the galaxy.<br>
	 * This means coordinates are within the interval <code>[ -min? ; max? ]</code> where ? is one
	 * of three coordinates and <code>size? = max? - min? + 1</code>
	 * 
	 * @param size - the size
	 */
	public void setSize(Vector.Integer size)
	{
		this.size = size;
	}

	/**
	 * The maximum gap between two neighbour SolarSystems.<br>
	 * If the nearest neighbour is determined for every SolarSystem this is the maximum of all those
	 * values.
	 * 
	 * @param maxGap - the maximum gap
	 */
	public void setMaxGap(int maxGap)
	{
		this.maxGap = maxGap;
	}

	/**
	 * The seed that has been used for the random generator to generate the SolarSystems
	 * 
	 * @param seed - the seed
	 */
	public void setSeed(long seed)
	{
		this.seed = seed;
	}

	/**
	 * The solar systems belonging to this galaxy
	 * 
	 * @param solarSystems - the list of solar system
	 */
	public void setSolarSystems(List<SolarSystem> solarSystems)
	{
		this.solarSystems = solarSystems;
	}

	/**
	 * The matches taking place in this galaxy
	 * 
	 * @param matches - the list of matches
	 */
	public void setMatches(List<Match> matches)
	{
		this.matches = matches;
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
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.getId().hashCode());
		result = prime * result + maxGap;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (seed ^ (seed >>> 32));
		result = prime * result + ((size == null) ? 0 : size.hashCode());
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
		Galaxy other = (Galaxy) obj;
		if(creationDate == null)
		{
			if(other.creationDate != null)
				return false;
		}
		else if(!creationDate.equals(other.creationDate))
			return false;
		if(creator == null)
		{
			if(other.creator != null)
				return false;
		}
		else if(!creator.getId().equals(other.creator.getId()))
			return false;
		if(maxGap != other.maxGap)
			return false;
		if(name == null)
		{
			if(other.name != null)
				return false;
		}
		else if(!name.equals(other.name))
			return false;
		if(seed != other.seed)
			return false;
		if(size == null)
		{
			if(other.size != null)
				return false;
		}
		else if(!size.equals(other.size))
			return false;
		return true;
	}

}

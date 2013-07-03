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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.syncnapsis.data.model.base.ActivatableInstance;

/**
 * Entity representation of a simple solar system infrastructure.<br>
 * The infrastructure is created once for each populated solar system within a match and is used by
 * all participants.<br>
 * This way the infrastructure of a system can be manipulated by all players simultaneously (build,
 * destroy) and accordingly helps all participants in the same way.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "solarsysteminfrastructure")
public class SolarSystemInfrastructure extends ActivatableInstance<Long>
{
	/**
	 * The solar system this infrastructure is for in the specified match
	 */
	protected SolarSystem					solarSystem;
	/**
	 * The match this solar system infrastructure is for
	 */
	protected Match							match;

	/**
	 * The first date of colonization of the solar system within this game (or null if the system
	 * has not yet been colonized)
	 */
	protected Date							firstColonizationDate;

	/**
	 * The size of this solar system (in a range from 0 to 100)
	 */
	protected int							size;

	/**
	 * The habitability of this solar system (in a range from 0 to 100)
	 */
	protected int							habitability;

	/**
	 * The current amount/value of infrastructure
	 */
	protected long							infrastructure;

	/**
	 * The populations present in this solar system (includes old populations as well)
	 */
	protected List<SolarSystemPopulation>	populations;

	/**
	 * The solar system this infrastructure is for
	 * 
	 * @return solarSystem
	 */
	@ManyToOne
	@JoinColumn(name = "fkSolarSystem", nullable = false)
	public SolarSystem getSolarSystem()
	{
		return solarSystem;
	}

	/**
	 * The match this solar system infrastructure is for
	 * 
	 * @return match
	 */
	@ManyToOne
	@JoinColumn(name = "fkMatch", nullable = false)
	public Match getMatch()
	{
		return match;
	}

	/**
	 * The first date of colonization of the solar system within this game (or null if the system
	 * has not yet been colonized)
	 * 
	 * @return colonizationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getFirstColonizationDate()
	{
		return firstColonizationDate;
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
	 * The current amount/value of infrastructure
	 * 
	 * @return infrastructure
	 */
	@Column(nullable = false)
	public long getInfrastructure()
	{
		return infrastructure;
	}

	/**
	 * The populations present in this solar system (includes old populations as well)
	 * 
	 * @return populations
	 */
	@OneToMany(mappedBy = "infrastructure")
	public List<SolarSystemPopulation> getPopulations()
	{
		return populations;
	}

	/**
	 * The solar system this infrastructure is for
	 * 
	 * @param solarSystem - the solar system
	 */
	public void setSolarSystem(SolarSystem solarSystem)
	{
		this.solarSystem = solarSystem;
	}

	/**
	 * 
	 * The match this solar system infrastructure is for
	 * 
	 * @param match - the Match
	 */
	public void setMatch(Match match)
	{
		this.match = match;
	}

	/**
	 * The first date of colonization of the solar system within this game (or null if the system
	 * has not yet been colonized)
	 * 
	 * @param firstColonizationDate - the date and time
	 */
	public void setFirstColonizationDate(Date firstColonizationDate)
	{
		this.firstColonizationDate = firstColonizationDate;
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

	/**
	 * The current amount/value of infrastructure
	 * 
	 * @param infrastructure - the amount/value
	 */
	public void setInfrastructure(long infrastructure)
	{
		this.infrastructure = infrastructure;
	}

	/**
	 * The populations present in this solar system (includes old populations as well)
	 * 
	 * @param populations - the list of solar system populations
	 */
	public void setPopulations(List<SolarSystemPopulation> populations)
	{
		this.populations = populations;
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
		result = prime * result + ((firstColonizationDate == null) ? 0 : firstColonizationDate.hashCode());
		result = prime * result + habitability;
		result = prime * result + (int) (infrastructure ^ (infrastructure >>> 32));
		result = prime * result + ((match == null) ? 0 : match.getId().hashCode());
		result = prime * result + size;
		result = prime * result + ((solarSystem == null) ? 0 : solarSystem.getId().hashCode());
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
		SolarSystemInfrastructure other = (SolarSystemInfrastructure) obj;
		if(firstColonizationDate == null)
		{
			if(other.firstColonizationDate != null)
				return false;
		}
		else if(!firstColonizationDate.equals(other.firstColonizationDate))
			return false;
		if(habitability != other.habitability)
			return false;
		if(infrastructure != other.infrastructure)
			return false;
		if(match == null)
		{
			if(other.match != null)
				return false;
		}
		else if(!match.getId().equals(other.match.getId()))
			return false;
		if(size != other.size)
			return false;
		if(solarSystem == null)
		{
			if(other.solarSystem != null)
				return false;
		}
		else if(!solarSystem.getId().equals(other.solarSystem.getId()))
			return false;
		return true;
	}
}
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

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumPopulationPriority;

/**
 * Entity representation of an empires/players population for a specific {@link SolarSystem} within
 * a
 * specific {@link Match}.<br>
 * The population is associated with the {@link SolarSystem}s unique
 * {@link SolarSystemInfrastructure} for this {@link Match} and holds information about the current
 * population and the date of colonization and destruction.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "solarsystempopulation")
public class SolarSystemPopulation extends ActivatableInstance<Long>
{
	/**
	 * The solar system this population "lives" in.<br>
	 * The solar system is associated via it's match-specific infrastructure-representation.
	 */
	protected SolarSystemInfrastructure		infrastructure;

	/**
	 * The participant (empire/player) this population belongs to
	 */
	protected Participant					participant;

	/**
	 * The population this population originated from
	 */
	protected SolarSystemPopulation			origin;
	/**
	 * The date this population originated from it's origin
	 */
	protected Date							originationDate;

	/**
	 * The last progress calculated for the travel of this population from it's origin to it's
	 * target (progress from started = 0.0 to arrived = 1.0).
	 */
	protected double						travelProgress;
	/**
	 * The date the last travelProgress was calculated
	 */
	protected Date							travelProgressDate;
	/**
	 * The speed this population is travelling with (in percent).
	 */
	protected int							travelSpeed;

	/**
	 * The date this population arrived at the associated solar system
	 */
	protected Date							colonizationDate;

	/**
	 * The date the population has been destroyed
	 */
	protected Date							destructionDate;
	/**
	 * The way this population was destroyed
	 * 
	 * @see EnumDestructionType
	 */
	protected EnumDestructionType			destructionType;

	/**
	 * The date of the last update for this population
	 */
	protected Date							lastUpdateDate;

	/**
	 * The current amount/value of population
	 */
	protected long							population;

	/**
	 * An optional amount of stored infrastructure taken from the origin population/system.
	 */
	protected long							storedInfrastructure;

	/**
	 * The priority to use for building a colony (population).<br>
	 * If set to {@link EnumPopulationPriority#population} population will increase faster than
	 * usual but infrastructure will increase less fast. For
	 * {@link EnumPopulationPriority#infrastructure} vice versa and for
	 * {@link EnumPopulationPriority#balanced} both will increase with normal speed.
	 */
	protected EnumPopulationPriority		buildPriority;

	/**
	 * The priority to use for attacking a colony (population).<br>
	 * If set to {@link EnumPopulationPriority#population} population will attacked most to preserve
	 * infrastructure.For {@link EnumPopulationPriority#infrastructure} vice versa and for
	 * {@link EnumPopulationPriority#balanced} both will be attacked evenly.
	 */
	protected EnumPopulationPriority		attackPriority;

	/**
	 * The spin-offs that orginated from this population
	 */
	protected List<SolarSystemPopulation>	spinoffs;

	// transient properties

	/**
	 * The delta to apply to this population
	 */
	protected double						delta;
	/**
	 * Has this population been modified?
	 */
	protected boolean						modified;

	/**
	 * The solar system this population "lives" in.<br>
	 * The solar system is associated via it's match-specific infrastructure-representation.
	 * 
	 * @return infrastructure
	 */
	@ManyToOne
	@JoinColumn(name = "fkInfrastructure", nullable = false)
	public SolarSystemInfrastructure getInfrastructure()
	{
		return infrastructure;
	}

	/**
	 * The participant (empire/player) this population belongs to
	 * 
	 * @return participant
	 */
	@ManyToOne
	@JoinColumn(name = "fkParticipant", nullable = false)
	public Participant getParticipant()
	{
		return participant;
	}

	/**
	 * The population this population originated from
	 * 
	 * @return origin
	 */
	@ManyToOne
	@JoinTable(name = "solarsystempopulation_origin", joinColumns = @JoinColumn(name = "fkPopulation"), inverseJoinColumns = @JoinColumn(name = "fkOrigin"))
	public SolarSystemPopulation getOrigin()
	{
		return origin;
	}

	/**
	 * The date this population originated from it's origin
	 * 
	 * @return originationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getOriginationDate()
	{
		return originationDate;
	}

	/**
	 * The last progress calculated for the travel of this population from it's origin to it's
	 * target (progress from started = 0.0 to arrived = 1.0).
	 * 
	 * @return travelProgress
	 */
	@Column(nullable = false)
	public double getTravelProgress()
	{
		return travelProgress;
	}

	/**
	 * The date the last travelProgress was calculated
	 * 
	 * @return travelProgressDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getTravelProgressDate()
	{
		return travelProgressDate;
	}

	/**
	 * The speed this population is travelling with (in percent).
	 * 
	 * @return travelSpeed
	 */
	@Column(nullable = false)
	public int getTravelSpeed()
	{
		return travelSpeed;
	}

	/**
	 * The date this population arrived at the associated solar system
	 * 
	 * @return colonizationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getColonizationDate()
	{
		return colonizationDate;
	}

	/**
	 * The date the population has been destroyed
	 * 
	 * @return destructionDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getDestructionDate()
	{
		return destructionDate;
	}

	/**
	 * The way this population was destroyed
	 * 
	 * @return destructionType
	 */
	@Column(nullable = true, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumDestructionType getDestructionType()
	{
		return destructionType;
	}

	/**
	 * The date of the last update for this population
	 * 
	 * @return lastUpdateDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	/**
	 * The current amount/value of population
	 * 
	 * @return population
	 */
	@Column(nullable = false)
	public long getPopulation()
	{
		return population;
	}

	/**
	 * An optional amount of stored infrastructure taken from the origin population/system.
	 * 
	 * @return storedInfrastructure
	 */
	@Column(nullable = false)
	public long getStoredInfrastructure()
	{
		return storedInfrastructure;
	}

	/**
	 * The priority to use for building a colony (population).<br>
	 * If set to {@link EnumPopulationPriority#population} population will increase faster than
	 * usual but infrastructure will increase less fast. For
	 * {@link EnumPopulationPriority#infrastructure} vice versa and for
	 * {@link EnumPopulationPriority#balanced} both will increase with normal speed.
	 * 
	 * @return buildPriority
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = LENGTH_NAME_NORMAL)
	public EnumPopulationPriority getBuildPriority()
	{
		return buildPriority;
	}

	/**
	 * The priority to use for attacking a colony (population).<br>
	 * If set to {@link EnumPopulationPriority#population} population will attacked most to preserve
	 * infrastructure.For {@link EnumPopulationPriority#infrastructure} vice versa and for
	 * {@link EnumPopulationPriority#balanced} both will be attacked evenly.
	 * 
	 * @return attackPriority
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = LENGTH_NAME_NORMAL)
	public EnumPopulationPriority getAttackPriority()
	{
		return attackPriority;
	}

	/**
	 * The spinoffs that orginated from this population
	 * 
	 * @return spinoffs
	 */
	@OneToMany(mappedBy = "origin")
	public List<SolarSystemPopulation> getSpinoffs()
	{
		return spinoffs;
	}

	/**
	 * The delta to apply to this population
	 * 
	 * @return delta
	 */
	@Transient
	public double getDelta()
	{
		return delta;
	}

	/**
	 * Has this population been modified?
	 * 
	 * @return modified
	 */
	@Transient
	public boolean isModified()
	{
		return modified;
	}

	/**
	 * The solar system this population "lives" in.<br>
	 * The solar system is associated via it's match-specific infrastructure-representation.
	 * 
	 * @param infrastructure - the infrastructure
	 */
	public void setInfrastructure(SolarSystemInfrastructure infrastructure)
	{
		this.infrastructure = infrastructure;
	}

	/**
	 * The participant (empire/player) this population belongs to
	 * 
	 * @param participant - the participant
	 */
	public void setParticipant(Participant participant)
	{
		this.participant = participant;
	}

	/**
	 * The population this population originated from
	 * 
	 * @param origin - the origin population
	 */
	public void setOrigin(SolarSystemPopulation origin)
	{
		this.origin = origin;
	}

	/**
	 * The date this population originated from it's origin
	 * 
	 * @param originationDate - the date and time
	 */
	public void setOriginationDate(Date originationDate)
	{
		this.originationDate = originationDate;
	}

	/**
	 * The last progress calculated for the travel of this population from it's origin to it's
	 * target (progress from started = 0.0 to arrived = 1.0).
	 * 
	 * 
	 * @param travelProgress - the travel progress
	 */
	public void setTravelProgress(double travelProgress)
	{
		this.travelProgress = travelProgress;
	}

	/**
	 * The date the last travelProgress was calculated
	 * 
	 * @param travelProgressDate - the date and time
	 */
	public void setTravelProgressDate(Date travelProgressDate)
	{
		this.travelProgressDate = travelProgressDate;
	}

	/**
	 * The speed this population is travelling with (in percent).
	 * 
	 * @param travelSpeed - the speed in percent
	 */
	public void setTravelSpeed(int travelSpeed)
	{
		this.travelSpeed = travelSpeed;
	}

	/**
	 * The date this population arrived at the associated solar system
	 * 
	 * @param colonizationDate - the date and time
	 */
	public void setColonizationDate(Date colonizationDate)
	{
		this.colonizationDate = colonizationDate;
	}

	/**
	 * The date the population has been destroyed
	 * 
	 * @param destructionDate - the date and time
	 */
	public void setDestructionDate(Date destructionDate)
	{
		this.destructionDate = destructionDate;
	}

	/**
	 * The way this population was destroyed
	 * 
	 * @param destructionType - the destruction type
	 */
	public void setDestructionType(EnumDestructionType destructionType)
	{
		this.destructionType = destructionType;
	}

	/**
	 * The date of the last update for this population
	 * 
	 * @param lastUpdateDate - the date and time
	 */
	public void setLastUpdateDate(Date lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * The current amount/value of population
	 * 
	 * @param population - the amount/value
	 */
	public void setPopulation(long population)
	{
		this.population = population;
	}

	/**
	 * An optional amount of stored infrastructure taken from the origin population/system.
	 * 
	 * @param storedInfrastructure - the stored infrastructure value
	 */
	public void setStoredInfrastructure(long storedInfrastructure)
	{
		this.storedInfrastructure = storedInfrastructure;
	}

	/**
	 * The priority to use for building a colony (population).<br>
	 * If set to {@link EnumPopulationPriority#population} population will increase faster than
	 * usual but infrastructure will increase less fast. For
	 * {@link EnumPopulationPriority#infrastructure} vice versa and for
	 * {@link EnumPopulationPriority#balanced} both will increase with normal speed.
	 * 
	 * @param buildPriority - the priority
	 */
	public void setBuildPriority(EnumPopulationPriority buildPriority)
	{
		this.buildPriority = buildPriority;
	}

	/**
	 * The priority to use for attacking a colony (population).<br>
	 * If set to {@link EnumPopulationPriority#population} population will attacked most to preserve
	 * infrastructure.For {@link EnumPopulationPriority#infrastructure} vice versa and for
	 * {@link EnumPopulationPriority#balanced} both will be attacked evenly.
	 * 
	 * @param attackPriority - the priority
	 */
	public void setAttackPriority(EnumPopulationPriority attackPriority)
	{
		this.attackPriority = attackPriority;
	}

	/**
	 * The spinoffs that orginatef from this population
	 * 
	 * @param spinoffs - the list of originated populations
	 */
	public void setSpinoffs(List<SolarSystemPopulation> spinoffs)
	{
		this.spinoffs = spinoffs;
	}

	/**
	 * The delta to apply to this population
	 * 
	 * @param delta - the delta
	 */
	public void setDelta(double delta)
	{
		this.delta = delta;
	}

	/**
	 * Has this population been modified?
	 * 
	 * @param modified - true or false
	 */
	public void setModified(boolean modified)
	{
		this.modified = modified;
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
		result = prime * result + ((attackPriority == null) ? 0 : attackPriority.hashCode());
		result = prime * result + ((buildPriority == null) ? 0 : buildPriority.hashCode());
		result = prime * result + ((colonizationDate == null) ? 0 : colonizationDate.hashCode());
		result = prime * result + ((destructionDate == null) ? 0 : destructionDate.hashCode());
		result = prime * result + ((destructionType == null) ? 0 : destructionType.hashCode());
		result = prime * result + ((infrastructure == null) ? 0 : infrastructure.getId().hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.getId().hashCode());
		result = prime * result + ((originationDate == null) ? 0 : originationDate.hashCode());
		result = prime * result + ((participant == null) ? 0 : participant.getId().hashCode());
		result = prime * result + (int) (population ^ (population >>> 32));
		result = prime * result + (int) (storedInfrastructure ^ (storedInfrastructure >>> 32));
		long temp;
		temp = Double.doubleToLongBits(travelProgress);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((travelProgressDate == null) ? 0 : travelProgressDate.hashCode());
		result = prime * result + travelSpeed;
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
		SolarSystemPopulation other = (SolarSystemPopulation) obj;
		if(attackPriority != other.attackPriority)
			return false;
		if(buildPriority != other.buildPriority)
			return false;
		if(colonizationDate == null)
		{
			if(other.colonizationDate != null)
				return false;
		}
		else if(!colonizationDate.equals(other.colonizationDate))
			return false;
		if(destructionDate == null)
		{
			if(other.destructionDate != null)
				return false;
		}
		else if(!destructionDate.equals(other.destructionDate))
			return false;
		if(destructionType != other.destructionType)
			return false;
		if(infrastructure == null)
		{
			if(other.infrastructure != null)
				return false;
		}
		else if(!infrastructure.getId().equals(other.infrastructure.getId()))
			return false;
		if(lastUpdateDate == null)
		{
			if(other.lastUpdateDate != null)
				return false;
		}
		else if(!lastUpdateDate.equals(other.lastUpdateDate))
			return false;
		if(origin == null)
		{
			if(other.origin != null)
				return false;
		}
		else if(!origin.getId().equals(other.origin.getId()))
			return false;
		if(originationDate == null)
		{
			if(other.originationDate != null)
				return false;
		}
		else if(!originationDate.equals(other.originationDate))
			return false;
		if(participant == null)
		{
			if(other.participant != null)
				return false;
		}
		else if(!participant.getId().equals(other.participant.getId()))
			return false;
		if(population != other.population)
			return false;
		if(storedInfrastructure != other.storedInfrastructure)
			return false;
		if(Double.doubleToLongBits(travelProgress) != Double.doubleToLongBits(other.travelProgress))
			return false;
		if(travelProgressDate == null)
		{
			if(other.travelProgressDate != null)
				return false;
		}
		else if(!travelProgressDate.equals(other.travelProgressDate))
			return false;
		if(travelSpeed != other.travelSpeed)
			return false;
		return true;
	}

	/**
	 * A comparator using {@link SolarSystemPopulation#colonizationDate} to compare entities
	 */
	public static final Comparator<SolarSystemPopulation>	BY_COLONIZATIONDATE	= new Comparator<SolarSystemPopulation>() {
																					@Override
																					public int compare(SolarSystemPopulation o1,
																							SolarSystemPopulation o2)
																					{
																						return o1.getColonizationDate().compareTo(
																								o2.getColonizationDate());
																					}
																				};
}

package com.syncnapsis.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.enums.EnumDestructionType;

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
public class SolarSystemPopulation extends ActivatableInstance<Long>
{
	/**
	 * The solar system this population "lives" in.<br>
	 * The solar system is associated via it's match-specific infrastructure-representation.
	 */
	protected SolarSystemInfrastructure	infrastructure;

	/**
	 * The participant (empire/player) this population belongs to
	 */
	protected Participant				participant;

	/**
	 * The date this population arrived at the associated solar system
	 */
	protected Date						colonizationDate;

	/**
	 * The date the population has been destroyed
	 */
	protected Date						destructionDate;
	/**
	 * The way this population was destroyed
	 * 
	 * @see EnumDestructionType
	 */
	protected EnumDestructionType		destructionType;

	/**
	 * The current amount/value of population
	 */
	protected int						population;

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
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	public EnumDestructionType getDestructionType()
	{
		return destructionType;
	}

	/**
	 * The current amount/value of population
	 * 
	 * @return population
	 */
	@Column(nullable = false)
	public int getPopulation()
	{
		return population;
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
	 * The current amount/value of population
	 * 
	 * @param population - the amount/value
	 */
	public void setPopulation(int population)
	{
		this.population = population;
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
		result = prime * result + ((colonizationDate == null) ? 0 : colonizationDate.hashCode());
		result = prime * result + ((destructionType == null) ? 0 : destructionType.hashCode());
		result = prime * result + ((destructionDate == null) ? 0 : destructionDate.hashCode());
		result = prime * result + ((infrastructure == null) ? 0 : infrastructure.getId().hashCode());
		result = prime * result + ((participant == null) ? 0 : participant.getId().hashCode());
		result = prime * result + population;
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
		if(colonizationDate == null)
		{
			if(other.colonizationDate != null)
				return false;
		}
		else if(!colonizationDate.equals(other.colonizationDate))
			return false;
		if(destructionType != other.destructionType)
			return false;
		if(destructionDate == null)
		{
			if(other.destructionDate != null)
				return false;
		}
		else if(!destructionDate.equals(other.destructionDate))
			return false;
		if(infrastructure == null)
		{
			if(other.infrastructure != null)
				return false;
		}
		else if(!infrastructure.getId().equals(other.infrastructure.getId()))
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
		return true;
	}
}

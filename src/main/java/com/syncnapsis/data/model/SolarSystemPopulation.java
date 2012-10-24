package com.syncnapsis.data.model;

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
	 * The current amount/value of population
	 */
	protected int							population;

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
	 * The secessions that orginated from this population
	 */
	protected List<SolarSystemPopulation>	secessions;

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
	 * The secessions that orginated from this population
	 * 
	 * @return secessions
	 */
	@OneToMany(mappedBy = "origin")
	public List<SolarSystemPopulation> getSecessions()
	{
		return secessions;
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
	 * The secessions that orginatef from this population
	 * 
	 * @param secessions - the list of originated populations
	 */
	public void setSecessions(List<SolarSystemPopulation> secessions)
	{
		this.secessions = secessions;
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
		result = prime * result + ((origin == null) ? 0 : origin.getId().hashCode());
		result = prime * result + ((originationDate == null) ? 0 : originationDate.hashCode());
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
		return true;
	}
}

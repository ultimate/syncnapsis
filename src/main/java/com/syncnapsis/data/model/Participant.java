package com.syncnapsis.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumVictoryCondition;

/**
 * Entity representing participants of a match.<br>
 * The player taking part in a match is represented by his empire that is associated via this class
 * with the match.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "participant")
public class Participant extends ActivatableInstance<Long>
{
	/**
	 * The match the associated empire is participating
	 */
	protected Match							match;

	/**
	 * The empire that is participating
	 */
	protected Empire						empire;

	/**
	 * The current rank of this participant/empire within the match
	 */
	protected int							rank;

	/**
	 * The date the participant (empire/player) joined the match
	 */
	protected Date							joinedDate;
	/**
	 * The date the participant (empire/player) has been destroyed
	 */
	protected Date							destructionDate;
	/**
	 * The way this participant was destroyed.
	 * 
	 * @see EnumDestructionType
	 */
	protected EnumDestructionType			destructionType;

	/**
	 * The rival(s) that have been randomly chosen for this participant when the matches victory
	 * condition is set to {@link EnumVictoryCondition#vendetta}
	 * 
	 * @see EnumVictoryCondition
	 */
	protected List<Participant>				rivals;

	/**
	 * The populations of this participant/empire within this match
	 */
	protected List<SolarSystemPopulation>	populations;

	// cannot map this, maybe make it transient and inject in manager
	// protected List<SolarSystemInfrastructure> systemsOwned;

	// cannot map this, maybe make it transient and inject in manager
	// protected List<SolarSystemInfrastructure> systemsDisputed;

	/**
	 * The match the associated empire is participating
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
	 * The empire that is participating
	 * 
	 * @return empire
	 */
	@ManyToOne
	@JoinColumn(name = "fkEmpire", nullable = false)
	public Empire getEmpire()
	{
		return empire;
	}

	/**
	 * The current rank of this participant/empire within the match
	 * 
	 * @return rank
	 */
	@Column(nullable = false)
	public int getRank()
	{
		return rank;
	}

	/**
	 * The date the participant (empire/player) joined the match
	 * 
	 * @return joinedDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getJoinedDate()
	{
		return joinedDate;
	}

	/**
	 * The date the participant (empire/player) has been destroyed
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
	 * The way this participant was destroyed.
	 * 
	 * @see EnumDestructionType
	 * @return destructionType
	 */
	@Column(nullable = true)
	@Enumerated(value = EnumType.STRING)
	public EnumDestructionType getDestructionType()
	{
		return destructionType;
	}

	/**
	 * The rival(s) that have been randomly chosen for this participant when the matches victory
	 * condition is set to {@link EnumVictoryCondition#vendetta}
	 * 
	 * @see EnumVictoryCondition
	 * @return rivals
	 */
	@ManyToMany
	@JoinTable(name = "matchparticipants", joinColumns = { @JoinColumn(name = "fkMatch") }, inverseJoinColumns = { @JoinColumn(name = "fkParticipant") })
	public List<Participant> getRivals()
	{
		return rivals;
	}

	/**
	 * The populations of this participant/empire within this match
	 * 
	 * @return populations
	 */
	@OneToMany(mappedBy = "participant")
	public List<SolarSystemPopulation> getPopulations()
	{
		return populations;
	}

	// public List<SolarSystemInfrastructure> getSystemsOwned()
	// {
	// return systemsOwned;
	// }

	// public List<SolarSystemInfrastructure> getSystemsDisputed()
	// {
	// return systemsDisputed;
	// }

	/**
	 * The match the associated empire is participating
	 * 
	 * @param match - the match
	 */
	public void setMatch(Match match)
	{
		this.match = match;
	}

	/**
	 * The empire that is participating
	 * 
	 * @param empire - the empire
	 */
	public void setEmpire(Empire empire)
	{
		this.empire = empire;
	}

	/**
	 * The current rank of this participant/empire within the match
	 * 
	 * @param rank - the rank
	 */
	public void setRank(int rank)
	{
		this.rank = rank;
	}

	/**
	 * The date the participant (empire/player) joined the match
	 * 
	 * @param joinedDate - the date and time
	 */
	public void setJoinedDate(Date joinedDate)
	{
		this.joinedDate = joinedDate;
	}

	/**
	 * The date the participant (empire/player) has been destroyed
	 * 
	 * @param destructionDate - the date and time
	 */
	public void setDestructionDate(Date destructionDate)
	{
		this.destructionDate = destructionDate;
	}

	/**
	 * The way this participant was destroyed.
	 * 
	 * @see EnumDestructionType
	 * @param destructionType - the type of destruction
	 */
	public void setDestructionType(EnumDestructionType destructionType)
	{
		this.destructionType = destructionType;
	}

	/**
	 * The rival(s) that have been randomly chosen for this participant when the matches victory
	 * condition is set to {@link EnumVictoryCondition#vendetta}
	 * 
	 * @see EnumVictoryCondition
	 * @param rivals - the list of rivals
	 */
	public void setRivals(List<Participant> rivals)
	{
		this.rivals = rivals;
	}

	/**
	 * The populations of this participant/empire within this match
	 * 
	 * @param populations - the list of populations
	 */
	public void setPopulations(List<SolarSystemPopulation> populations)
	{
		this.populations = populations;
	}

	// public void setSystemsOwned(List<SolarSystemInfrastructure> systemsOwned)
	// {
	// this.systemsOwned = systemsOwned;
	// }

	// public void setSystemsDisputed(List<SolarSystemInfrastructure> systemsDisputed)
	// {
	// this.systemsDisputed = systemsDisputed;
	// }

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((destructionDate == null) ? 0 : destructionDate.hashCode());
		result = prime * result + ((destructionType == null) ? 0 : destructionType.hashCode());
		result = prime * result + ((empire == null) ? 0 : empire.getId().hashCode());
		result = prime * result + ((joinedDate == null) ? 0 : joinedDate.hashCode());
		result = prime * result + ((match == null) ? 0 : match.getId().hashCode());
		result = prime * result + rank;
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
		Participant other = (Participant) obj;
		if(destructionDate == null)
		{
			if(other.destructionDate != null)
				return false;
		}
		else if(!destructionDate.equals(other.destructionDate))
			return false;
		if(destructionType != other.destructionType)
			return false;
		if(empire == null)
		{
			if(other.empire != null)
				return false;
		}
		else if(!empire.getId().equals(other.empire.getId()))
			return false;
		if(joinedDate == null)
		{
			if(other.joinedDate != null)
				return false;
		}
		else if(!joinedDate.equals(other.joinedDate))
			return false;
		if(match == null)
		{
			if(other.match != null)
				return false;
		}
		else if(!match.getId().equals(other.match.getId()))
			return false;
		if(rank != other.rank)
			return false;
		return true;
	}

}

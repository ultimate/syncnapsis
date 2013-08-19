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
	 * The current value being used to determine the rank of this participant/empire within the
	 * match.
	 */
	protected int							rankValue;

	/**
	 * The date the rank was last calculated
	 */
	protected Date							rankDate;

	/**
	 * Is the calculated rank final (not modifiable any more)? The flag will be set on calculation
	 * when the player is destroyed.
	 */
	protected boolean						rankFinal;

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
	 * The number of start systems the player has selected
	 */
	protected int							startSystemsSelected;

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
	 * The current value being used to determine the rank of this participant/empire within the
	 * match.
	 * 
	 * @return rankValue
	 */
	@Column(nullable = false)
	public int getRankValue()
	{
		return rankValue;
	}

	/**
	 * The date the rank was last calculated
	 * 
	 * @return rankDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getRankDate()
	{
		return rankDate;
	}

	/**
	 * Is the calculated rank final (not modifiable any more)? The flag will be set on calculation
	 * when the player is destroyed.
	 * 
	 * @return rankFinal
	 */
	@Column(nullable = false)
	public boolean isRankFinal()
	{
		return rankFinal;
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
	@Column(nullable = true, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumDestructionType getDestructionType()
	{
		return destructionType;
	}

	/**
	 * The number of start systems the player has selected
	 * 
	 * @return startSystemsSelected
	 */
	@Column(nullable = false)
	public int getStartSystemsSelected()
	{
		return startSystemsSelected;
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
	 * The current value being used to determine the rank of this participant/empire within the
	 * match.
	 * 
	 * @param rankValue - the rank value
	 */
	public void setRankValue(int rankValue)
	{
		this.rankValue = rankValue;
	}

	/**
	 * The date the rank was last calculated
	 * 
	 * @param rankDate - the date and time
	 */
	public void setRankDate(Date rankDate)
	{
		this.rankDate = rankDate;
	}

	/**
	 * Is the calculated rank final (not modifiable any more)? The flag will be set on calculation
	 * when the player is destroyed.
	 * 
	 * @param rankFinal - true or false
	 */
	public void setRankFinal(boolean rankFinal)
	{
		this.rankFinal = rankFinal;
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
	 * The number of start systems the player has selected
	 * 
	 * @param startSystemsSelected - the number of start systems
	 */
	public void setStartSystemsSelected(int startSystemsSelected)
	{
		this.startSystemsSelected = startSystemsSelected;
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

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#hashCode()
	 */
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
		result = prime * result + ((rankDate == null) ? 0 : rankDate.hashCode());
		result = prime * result + (rankFinal ? 1231 : 1237);
		result = prime * result + rankValue;
		result = prime * result + startSystemsSelected;
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
		if(rankDate == null)
		{
			if(other.rankDate != null)
				return false;
		}
		else if(!rankDate.equals(other.rankDate))
			return false;
		if(rankFinal != other.rankFinal)
			return false;
		if(rankValue != other.rankValue)
			return false;
		if(startSystemsSelected != other.startSystemsSelected)
			return false;
		return true;
	}

	/**
	 * A comparator using {@link Participant#rankValue} to compare entities
	 */
	public static final Comparator<Participant>	BY_RANKVALUE	= new Comparator<Participant>() {
																	@Override
																	public int compare(Participant o1, Participant o2)
																	{
																		return o2.rankValue - o1.rankValue;
																	}
																};
	/**
	 * A comparator using {@link Participant#empire} to compare entities
	 */
	public static final Comparator<Participant>	BY_EMPIRE		= new Comparator<Participant>() {
																	@Override
																	public int compare(Participant o1, Participant o2)
																	{
																		return o1.empire.getId().compareTo(o2.empire.getId());
																	}
																};
}

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

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.enums.EnumJoinType;
import com.syncnapsis.enums.EnumMatchState;
import com.syncnapsis.enums.EnumStartCondition;
import com.syncnapsis.enums.EnumVictoryCondition;
import com.syncnapsis.security.Ownable;

/**
 * Entity representing a single match (or game) within the global game of universe-conquest.<br>
 * Games can be created by players and take place in a specified galaxy.<br>
 * Depending on the choice of the creator the folowing properties can be set for the match:<br>
 * <ul>
 * <li>name</li>
 * <li>number of participants (with upper and lower bound)</li>
 * <li>start date (e.g. for delayed start)</li>
 * <li>victory condition (@see {@link EnumVictoryCondition})</li>
 * </ul>
 * 
 * @author ultimate
 */
@Entity
@Table(name = "match")
public class Match extends ActivatableInstance<Long> implements Ownable<Player>
{
	/**
	 * The title of this match
	 */
	protected String				title;

	/**
	 * The galaxy this match takes place in
	 */
	protected Galaxy				galaxy;

	/**
	 * The creator of the match
	 */
	protected Player				creator;

	/**
	 * The date and time this match was created
	 */
	protected Date					creationDate;
	/**
	 * The date and time this match started (or will start)
	 */
	protected Date					startDate;
	/**
	 * The date and time this match ended (or null if still running)
	 */
	protected Date					finishedDate;
	/**
	 * The date and time this match was canceled
	 */
	protected Date					canceledDate;

	/**
	 * The state of this match
	 */
	protected EnumMatchState		state;

	/**
	 * The speed this match is played with (a factor for travel times and growth rates)
	 */
	protected int					speed;

	/**
	 * The seed used for the random generator for creating the SolarSystemInfrastructures
	 */
	protected long					seed;

	/**
	 * The number of start systems the participants may select.
	 */
	protected int					startSystemCount;
	/**
	 * Is manual start system selection enabled?
	 */
	protected boolean				startSystemSelectionEnabled;
	/**
	 * The total population for all start systems
	 */
	protected long					startPopulation;

	/**
	 * The join rules defined for joins <b>before</b> the match has been started.
	 */
	protected EnumJoinType			plannedJoinType;
	/**
	 * The join rules defined for joins <b>after</b> the match has been started.
	 */
	protected EnumJoinType			startedJoinType;

	/**
	 * The maximum number of participants
	 */
	protected int					participantsMax;

	/**
	 * The minimum number of participants
	 */
	protected int					participantsMin;

	/**
	 * The start condition for this match
	 * 
	 * @see EnumStartCondition
	 */
	protected EnumStartCondition	startCondition;

	/**
	 * The victory condition for this match
	 * 
	 * @see EnumVictoryCondition
	 */
	protected EnumVictoryCondition	victoryCondition;

	/**
	 * A parameter defining the amount or value for the victory condition if necessary
	 * 
	 * @see Match#victoryCondition
	 */
	protected int					victoryParameter;

	/**
	 * The timeout for the victory condition. It should be set initially from the victory condition
	 * in order to simplify displaying a countdown on client side and to prevent accidential changes
	 * during countdown.
	 */
	protected long					victoryTimeout;

	/**
	 * The participants in this match
	 */
	protected List<Participant>		participants;
	
	/**
	 * The infrastructures for this match
	 */
	protected List<SolarSystemInfrastructure> infrastructures;

	/**
	 * The title of this match
	 * 
	 * @return title
	 */
	@Column(nullable = false, length = LENGTH_TITLE)
	public String getTitle()
	{
		return title;
	}

	/**
	 * The galaxy this match takes place in
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
	 * The creator of the match
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
	 * The date and time this match was created
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
	 * The date and time this match started (or will start)
	 * 
	 * @return startDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * The date and time this match ended (or null if still running)
	 * 
	 * @return finishedDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getFinishedDate()
	{
		return finishedDate;
	}

	/**
	 * The date and time this match was canceled
	 * 
	 * @return canceledDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getCanceledDate()
	{
		return canceledDate;
	}

	/**
	 * The state of this match
	 * 
	 * @return state
	 */
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = LENGTH_ENUM)
	public EnumMatchState getState()
	{
		return state;
	}

	/**
	 * The speed this match is played with (a factor for travel times and growth rates)
	 * 
	 * @return speed
	 */
	@Column(nullable = false)
	public int getSpeed()
	{
		return speed;
	}

	/**
	 * The seed used for the random generator for creating the SolarSystemInfrastructures
	 * 
	 * @return seed
	 */
	@Column(nullable = false)
	public long getSeed()
	{
		return seed;
	}

	/**
	 * The number of start systems the participants may select.
	 * 
	 * @return startSystemCount
	 */
	@Column(nullable = false)
	public int getStartSystemCount()
	{
		return startSystemCount;
	}

	/**
	 * Is manual start system selection enabled?
	 * 
	 * @return startSystemSelectionEnabled
	 */
	@Column(nullable = false)
	public boolean isStartSystemSelectionEnabled()
	{
		return startSystemSelectionEnabled;
	}

	/**
	 * The total population for all start systems
	 * 
	 * @return startPopulation
	 */
	@Column(nullable = false)
	public long getStartPopulation()
	{
		return startPopulation;
	}

	/**
	 * The join rules defined for joins <b>before</b> the match has been started.
	 * 
	 * @return plannedJoinType
	 */
	@Column(nullable = false, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumJoinType getPlannedJoinType()
	{
		return plannedJoinType;
	}

	/**
	 * The join rules defined for joins <b>after</b> the match has been started.
	 * 
	 * @return startedJoinType
	 */
	@Column(nullable = false, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumJoinType getStartedJoinType()
	{
		return startedJoinType;
	}

	/**
	 * The maximum number of participants
	 * 
	 * @return participantsMax
	 */
	@Column(nullable = false)
	public int getParticipantsMax()
	{
		return participantsMax;
	}

	/**
	 * The minimum number of participants
	 * 
	 * @return participantsMin
	 */
	@Column(nullable = false)
	public int getParticipantsMin()
	{
		return participantsMin;
	}

	/**
	 * The start condition for this match
	 * 
	 * @return startCondition
	 */
	@Column(nullable = false, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumStartCondition getStartCondition()
	{
		return startCondition;
	}

	/**
	 * The victory condition for this match
	 * 
	 * @return victoryCondition
	 */
	@Column(nullable = true, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumVictoryCondition getVictoryCondition()
	{
		return victoryCondition;
	}

	/**
	 * A parameter defining the amount or value for the victoryCondition if necessary
	 * 
	 * @see Match#victoryCondition
	 * @return victoryParameter
	 */
	@Column(nullable = false)
	public int getVictoryParameter()
	{
		return victoryParameter;
	}

	/**
	 * The timeout for the victory condition. It should be set initially from the victory condition
	 * in order to simplify displaying a countdown on client side and to prevent accidential changes
	 * during countdown.
	 * 
	 * @return victoryTimeout
	 */
	@Column(nullable = false)
	public long getVictoryTimeout()
	{
		return victoryTimeout;
	}

	/**
	 * The participants in this match
	 * 
	 * @return participants
	 */
	@OneToMany(mappedBy = "match")
	public List<Participant> getParticipants()
	{
		return participants;
	}
	
	/**
	 * The infrastructures for this match
	 * @return infrastructures
	 */
	@OneToMany(mappedBy = "match")
	public List<SolarSystemInfrastructure> getInfrastructures()
	{
		return infrastructures;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.Ownable#getOwners()
	 */
	@Transient
	@Override
	public List<Player> getOwners()
	{
		return Collections.nCopies(1, getCreator());
	}

	/**
	 * The title of this match
	 * 
	 * @param title - the title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * The galaxy this match takes place in
	 * 
	 * @param galaxy - the galaxy
	 */
	public void setGalaxy(Galaxy galaxy)
	{
		this.galaxy = galaxy;
	}

	/**
	 * The creator of the match
	 * 
	 * @param creator - the creator
	 */
	public void setCreator(Player creator)
	{
		this.creator = creator;
	}

	/**
	 * The date and time this match was created
	 * 
	 * @param creationDate - the date and time
	 */
	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * The date and time this match started (or will start)
	 * 
	 * @param startDate - the date and time
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * The date and time this match ended (or null if still running)
	 * 
	 * @param finishedDate - the date and time
	 */
	public void setFinishedDate(Date finishedDate)
	{
		this.finishedDate = finishedDate;
	}

	/**
	 * The date and time this match was canceled
	 * 
	 * @param canceledDate - the date and time
	 */
	public void setCanceledDate(Date canceledDate)
	{
		this.canceledDate = canceledDate;
	}

	/**
	 * The state of this match
	 * 
	 * @param state - the state
	 */
	public void setState(EnumMatchState state)
	{
		this.state = state;
	}

	/**
	 * The speed this match is played with (a factor for travel times and growth rates)
	 * 
	 * @param speed - the speed factor
	 */
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	/**
	 * The seed used for the random generator for creating the SolarSystemInfrastructures
	 * 
	 * @param seeed - the seed
	 */
	public void setSeed(long seed)
	{
		this.seed = seed;
	}

	/**
	 * The number of start systems the participants may select.
	 * 
	 * @param startSystemCount - the number
	 */
	public void setStartSystemCount(int startSystemCount)
	{
		this.startSystemCount = startSystemCount;
	}

	/**
	 * Is manual start system selection enabled?
	 * 
	 * @param startSystemSelectionEnabled - true or false
	 */
	public void setStartSystemSelectionEnabled(boolean startSystemSelectionEnabled)
	{
		this.startSystemSelectionEnabled = startSystemSelectionEnabled;
	}

	/**
	 * The total population for all start systems
	 * 
	 * @param startPopulation - the population count
	 */
	public void setStartPopulation(long startPopulation)
	{
		this.startPopulation = startPopulation;
	}

	/**
	 * The join rules defined for joins <b>before</b> the match has been started.
	 * 
	 * @param plannedJoinType - the EnumJoinType
	 */
	public void setPlannedJoinType(EnumJoinType plannedJoinType)
	{
		this.plannedJoinType = plannedJoinType;
	}

	/**
	 * The join rules defined for joins <b>after</b> the match has been started.
	 * 
	 * @param startedJoinType - the EnumJoinType
	 */
	public void setStartedJoinType(EnumJoinType startedJoinType)
	{
		this.startedJoinType = startedJoinType;
	}

	/**
	 * The maximum number of participants
	 * 
	 * @param participantsMax - the maximum
	 */
	public void setParticipantsMax(int participantsMax)
	{
		this.participantsMax = participantsMax;
	}

	/**
	 * The minimum number of participants
	 * 
	 * @param participantsMin - the minimum
	 */
	public void setParticipantsMin(int participantsMin)
	{
		this.participantsMin = participantsMin;
	}

	/**
	 * The start condition for this match
	 * 
	 * @param startCondition - the start condition
	 */
	public void setStartCondition(EnumStartCondition startCondition)
	{
		this.startCondition = startCondition;
	}

	/**
	 * The victory condition for this match
	 * 
	 * @param victoryCondition - the victory condition
	 */
	public void setVictoryCondition(EnumVictoryCondition victoryCondition)
	{
		this.victoryCondition = victoryCondition;
	}

	/**
	 * A parameter defining the amount or value for the victoryCondition if necessary
	 * 
	 * @see Match#victoryCondition
	 * @param victoryParameter - the victory parameter
	 */
	public void setVictoryParameter(int victoryParameter)
	{
		this.victoryParameter = victoryParameter;
	}

	/**
	 * The timeout for the victory condition. It should be set initially from the victory condition
	 * in order to simplify displaying a countdown on client side and to prevent accidential changes
	 * during countdown.
	 * 
	 * @param victoryTimeout - the timeout in ms
	 */
	public void setVictoryTimeout(long victoryTimeout)
	{
		this.victoryTimeout = victoryTimeout;
	}

	/**
	 * The participants in this match
	 * 
	 * @param participants - the List of participants
	 */
	public void setParticipants(List<Participant> participants)
	{
		this.participants = participants;
	}

	/**
	 * The infrastructures for this match
	 * 
	 * @param infrastructures - the List of infrastructures
	 */
	public void setInfrastructures(List<SolarSystemInfrastructure> infrastructures)
	{
		this.infrastructures = infrastructures;
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
		result = prime * result + ((canceledDate == null) ? 0 : canceledDate.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.getId().hashCode());
		result = prime * result + ((finishedDate == null) ? 0 : finishedDate.hashCode());
		result = prime * result + ((galaxy == null) ? 0 : galaxy.getId().hashCode());
		result = prime * result + participantsMax;
		result = prime * result + participantsMin;
		result = prime * result + ((plannedJoinType == null) ? 0 : plannedJoinType.hashCode());
		result = prime * result + (int) (seed ^ (seed >>> 32));
		result = prime * result + speed;
		result = prime * result + ((startCondition == null) ? 0 : startCondition.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + (int) (startPopulation ^ (startPopulation >>> 32));
		result = prime * result + startSystemCount;
		result = prime * result + (startSystemSelectionEnabled ? 1231 : 1237);
		result = prime * result + ((startedJoinType == null) ? 0 : startedJoinType.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((victoryCondition == null) ? 0 : victoryCondition.hashCode());
		result = prime * result + victoryParameter;
		result = prime * result + (int) (victoryTimeout ^ (victoryTimeout >>> 32));
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
		Match other = (Match) obj;
		if(canceledDate == null)
		{
			if(other.canceledDate != null)
				return false;
		}
		else if(!canceledDate.equals(other.canceledDate))
			return false;
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
		if(finishedDate == null)
		{
			if(other.finishedDate != null)
				return false;
		}
		else if(!finishedDate.equals(other.finishedDate))
			return false;
		if(galaxy == null)
		{
			if(other.galaxy != null)
				return false;
		}
		else if(!galaxy.getId().equals(other.galaxy.getId()))
			return false;
		if(participantsMax != other.participantsMax)
			return false;
		if(participantsMin != other.participantsMin)
			return false;
		if(plannedJoinType != other.plannedJoinType)
			return false;
		if(seed != other.seed)
			return false;
		if(speed != other.speed)
			return false;
		if(startCondition != other.startCondition)
			return false;
		if(startDate == null)
		{
			if(other.startDate != null)
				return false;
		}
		else if(!startDate.equals(other.startDate))
			return false;
		if(startPopulation != other.startPopulation)
			return false;
		if(startSystemCount != other.startSystemCount)
			return false;
		if(startSystemSelectionEnabled != other.startSystemSelectionEnabled)
			return false;
		if(startedJoinType != other.startedJoinType)
			return false;
		if(state != other.state)
			return false;
		if(title == null)
		{
			if(other.title != null)
				return false;
		}
		else if(!title.equals(other.title))
			return false;
		if(victoryCondition != other.victoryCondition)
			return false;
		if(victoryParameter != other.victoryParameter)
			return false;
		if(victoryTimeout != other.victoryTimeout)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Match [title=").append(title).append(", galaxy=").append(galaxy == null ? null : galaxy.getId()).append(", creator=")
				.append(creator == null ? null : creator.getId()).append(", creationDate=").append(creationDate).append(", startDate=")
				.append(startDate).append(", finishedDate=").append(finishedDate).append(", canceledDate=").append(canceledDate).append(", state=")
				.append(state).append(", speed=").append(speed).append(", seed=").append(seed).append(", startSystemCount=").append(startSystemCount)
				.append(", startSystemSelectionEnabled=").append(startSystemSelectionEnabled).append(", startPopulation=").append(startPopulation)
				.append(", plannedJoinType=").append(plannedJoinType).append(", startedJoinType=").append(startedJoinType)
				.append(", participantsMax=").append(participantsMax).append(", participantsMin=").append(participantsMin)
				.append(", startCondition=").append(startCondition).append(", victoryCondition=").append(victoryCondition)
				.append(", victoryParameter=").append(victoryParameter).append(", victoryTimeout=").append(victoryTimeout).append("]");
		return builder.toString();
	}
}

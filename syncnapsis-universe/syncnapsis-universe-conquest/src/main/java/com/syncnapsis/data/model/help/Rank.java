package com.syncnapsis.data.model.help;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.syncnapsis.data.model.base.Model;

/**
 * Entity representing a rank for another entity to be embedded into that class for database
 * storage.
 * 
 * @author ultimate
 */
@Embeddable
@MappedSuperclass
public class Rank implements Model
{
	/**
	 * The current rank of this participant/empire within the match
	 */
	protected int		rank;

	/**
	 * The current value being used to determine the rank of this participant/empire within the
	 * match expressed in percent.
	 */
	protected int		value;

	/**
	 * THe current value being used to determine the rank of this participant/empire within the
	 * match as a raw value.
	 */
	protected long		rawValue;

	/**
	 * The date the rank was last calculated
	 */
	protected Date		date;

	/**
	 * The date the rank value reached the victory condition
	 */
	protected Date		victoryDate;

	/**
	 * Is the calculated rank rankFinal (not modifiable any more)? The flag will be set on
	 * calculation
	 * when the player is destroyed.
	 */
	@Transient
	// required since getter/setter refer to "final" only, but this is a reserved word
	protected boolean	rankFinal;

	/**
	 * The display name for this rank.
	 */
	protected String	displayName;

	/**
	 * The current rank of this participant/empire within the match
	 * 
	 * @return rank
	 */
	@Column(nullable = false, name = "rank")
	public int getRank()
	{
		return rank;
	}

	/**
	 * The current value being used to determine the rank of this participant/empire within the
	 * match expressend in percent
	 * 
	 * @return value
	 */
	@Column(nullable = false, name = "rankValue")
	public int getValue()
	{
		return value;
	}

	/**
	 * The current value being used to determine the rank of this participant/empire within the
	 * match as a raw value.
	 * 
	 * @return rawValue
	 */
	@Column(nullable = false, name = "rankRawValue")
	public long getRawValue()
	{
		return rawValue;
	}

	/**
	 * The date the rank was last calculated
	 * 
	 * @return date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "rankDate")
	public Date getDate()
	{
		return date;
	}

	/**
	 * The date the rank value reached the victory condition
	 * 
	 * @return victoryDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true, name = "rankVictoryDate")
	public Date getVictoryDate()
	{
		return victoryDate;
	}

	/**
	 * Is the calculated rank rankFinal (not modifiable any more)? The flag will be set on
	 * calculation
	 * when the player is destroyed.
	 * 
	 * @return rankFinal
	 */
	@Column(nullable = false, name = "rankFinal")
	public boolean isFinal()
	{
		return rankFinal;
	}

	/**
	 * The display name for this rank.
	 * 
	 * @return displayName
	 */
	@Transient
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * The display name for this rank.
	 * 
	 * @param displayName - the display name
	 */
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
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
	 * match expressed in percent.
	 * 
	 * @param value - the rank value
	 */
	public void setValue(int value)
	{
		this.value = value;
	}

	/**
	 * The current value being used to determine the rank of this participant/empire within the
	 * match as a raw value.
	 * 
	 * @param rawValue - the rank raw value
	 */
	public void setRawValue(long rawValue)
	{
		this.rawValue = rawValue;
	}

	/**
	 * The date the rank was last calculated
	 * 
	 * @param date - the date and time
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * The date the rank value reached the victory condition
	 * 
	 * @param victoryDate - the date and time
	 */
	public void setVictoryDate(Date victoryDate)
	{
		this.victoryDate = victoryDate;
	}

	/**
	 * Is the calculated rank rankFinal (not modifiable any more)? The flag will be set on
	 * calculation
	 * when the player is destroyed.
	 * 
	 * @param rankFinal - true or false
	 */
	public void setFinal(boolean rankFinal)
	{
		this.rankFinal = rankFinal;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + rank;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (rankFinal ? 1231 : 1237);
		result = prime * result + (int) (rawValue ^ (rawValue >>> 32));
		result = prime * result + value;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Rank other = (Rank) obj;
		if(rank != other.rank)
			return false;
		if(date == null)
		{
			if(other.date != null)
				return false;
		}
		else if(!date.equals(other.date))
			return false;
		if(rankFinal != other.rankFinal)
			return false;
		if(rawValue != other.rawValue)
			return false;
		if(value != other.value)
			return false;
		return true;
	}
}

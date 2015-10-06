package com.syncnapsis.data.model.help;

import com.syncnapsis.enums.EnumPopulationPriority;

/**
 * Helper class representing a troop send order.<br>
 * This class is used to request troop movements at the server by the client.
 * 
 * @author ultimate
 */
public class Order
{
	/**
	 * The ID of the origin population
	 */
	protected long						originId;
	/**
	 * The ID of the target infrastructure
	 */
	protected long						targetId;
	/**
	 * The amount of population to send
	 */
	protected long						population;
	/**
	 * The attack priority to use for the target population
	 */
	protected EnumPopulationPriority	attackPriority;
	/**
	 * The build priority to use for the target population
	 */
	protected EnumPopulationPriority	buildPriority;
	/**
	 * The speed to travel with
	 */
	protected int						travelSpeed;
	/**
	 * Whether to give up the origin population
	 */
	protected boolean					exodus;

	/**
	 * Default constructor
	 */
	public Order()
	{
		super();
	}
	
	/**
	 * The ID of the origin population
	 * 
	 * @return originId
	 */
	public long getOriginId()
	{
		return originId;
	}

	/**
	 * The ID of the target infrastructure
	 * 
	 * @return targetId
	 */
	public long getTargetId()
	{
		return targetId;
	}

	/**
	 * The amount of population to send
	 * 
	 * @return population
	 */
	public long getPopulation()
	{
		return population;
	}

	/**
	 * The attack priority to use for the target population
	 * 
	 * @return attackPriorit
	 */
	public EnumPopulationPriority getAttackPriority()
	{
		return attackPriority;
	}

	/**
	 * The build priority to use for the target population
	 * 
	 * @return buildPriority
	 */
	public EnumPopulationPriority getBuildPriority()
	{
		return buildPriority;
	}

	/**
	 * The speed to travel with
	 * 
	 * @return travelSpeed
	 */
	public int getTravelSpeed()
	{
		return travelSpeed;
	}

	/**
	 * Whether to give up the origin population
	 * 
	 * @return exodus
	 */
	public boolean isExodus()
	{
		return exodus;
	}

	/**
	 * The ID of the origin population
	 * 
	 * @param originId - the origin ID
	 */
	public void setOriginId(long originId)
	{
		this.originId = originId;
	}

	/**
	 * The ID of the target infrastructure
	 * 
	 * @param targetId - the target ID
	 */
	public void setTargetId(long targetId)
	{
		this.targetId = targetId;
	}

	/**
	 * The amount of population to send
	 * 
	 * @param population - the amount of population
	 */
	public void setPopulation(long population)
	{
		this.population = population;
	}

	/**
	 * The attack priority to use for the target population
	 * 
	 * @param attackPriority - the priority as an {@link EnumPopulationPriority}
	 */
	public void setAttackPriority(EnumPopulationPriority attackPriority)
	{
		this.attackPriority = attackPriority;
	}

	/**
	 * The build priority to use for the target population
	 * 
	 * @param buildPriority - the priority as an {@link EnumPopulationPriority}
	 */
	public void setBuildPriority(EnumPopulationPriority buildPriority)
	{
		this.buildPriority = buildPriority;
	}

	/**
	 * The speed to travel with
	 * 
	 * @param travelSpeed - the speed
	 */
	public void setTravelSpeed(int travelSpeed)
	{
		this.travelSpeed = travelSpeed;
	}

	/**
	 * Whether to give up the origin population
	 * 
	 * @param exodus - true or false
	 */
	public void setExodus(boolean exodus)
	{
		this.exodus = exodus;
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
		result = prime * result + ((attackPriority == null) ? 0 : attackPriority.hashCode());
		result = prime * result + ((buildPriority == null) ? 0 : buildPriority.hashCode());
		result = prime * result + (exodus ? 1231 : 1237);
		result = prime * result + (int) (originId ^ (originId >>> 32));
		result = prime * result + (int) (population ^ (population >>> 32));
		result = prime * result + (int) (targetId ^ (targetId >>> 32));
		result = prime * result + travelSpeed;
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
		Order other = (Order) obj;
		if(attackPriority != other.attackPriority)
			return false;
		if(buildPriority != other.buildPriority)
			return false;
		if(exodus != other.exodus)
			return false;
		if(originId != other.originId)
			return false;
		if(population != other.population)
			return false;
		if(targetId != other.targetId)
			return false;
		if(travelSpeed != other.travelSpeed)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Order [originId=").append(originId).append(", targetId=").append(targetId).append(", population=").append(population)
				.append(", attackPriority=").append(attackPriority).append(", buildPriority=").append(buildPriority).append(", travelSpeed=")
				.append(travelSpeed).append(", exodus=").append(exodus).append("]");
		return builder.toString();
	}
}

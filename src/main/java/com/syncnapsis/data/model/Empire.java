package com.syncnapsis.data.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.syncnapsis.data.model.base.Institution;

/**
 * Model-Klasse "Imperium"
 * Ein Spieler kann mehrere Imperien besitzen, die er beherrscht und über die er
 * am Spiel teilnehmen kann. Imperien können Mitglieder in Allianzen sein und
 * diplomatische Beziehungen aufbauen.
 * Über ein Imperium kann der Spieler Kolonien, Schiffe und Gebäude bauen, und
 * im Universum expandieren.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "empire")
public class Empire extends Institution<Long>
{
	/**
	 * Der Benutzer dem dieses Imperium gehört
	 */
	protected Player			player;

	/**
	 * Leerer Standard Constructor
	 */
	public Empire()
	{
	}

	/**
	 * Der Spieler dem dieses Imperium gehört
	 * 
	 * @return player
	 */
	@ManyToOne
	@JoinColumn(name = "fkPlayer", nullable = false)
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Der Spieler dem dieses Imperium gehört
	 * 
	 * @param player - der Benutzer
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
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
		result = prime * result + ((player == null) ? 0 : player.hashCode());
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
		Empire other = (Empire) obj;
		if(player == null)
		{
			if(other.player != null)
				return false;
		}
		else if(!player.equals(other.player))
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
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("version", version).append("activated", activated).append("shortName", shortName)
				.append("fullName", fullName).append("description", description).append("imageURL", imageURL).append("player", player.getId());
		return builder.toString();
	}
}

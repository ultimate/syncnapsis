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
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syncnapsis.data.model.base.Institution;
import com.syncnapsis.security.Ownable;

/**
 * Model-Klasse "Imperium"
 * Ein Spieler kann mehrere Imperien besitzen, die er beherrscht und �ber die er
 * am Spiel teilnehmen kann. Imperien k�nnen Mitglieder in Allianzen sein und
 * diplomatische Beziehungen aufbauen.
 * �ber ein Imperium kann der Spieler Kolonien, Schiffe und Geb�ude bauen, und
 * im Universum expandieren.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "empire")
public class Empire extends Institution<Long> implements Ownable<Player>
{
	/**
	 * Der Benutzer dem dieses Imperium geh�rt
	 */
	protected Player	player;

	/**
	 * Leerer Standard Constructor
	 */
	public Empire()
	{
	}

	/**
	 * Der Spieler dem dieses Imperium geh�rt
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
	 * Der Spieler dem dieses Imperium geh�rt
	 * 
	 * @param player - der Benutzer
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.Ownable#getOwners()
	 */
	@Transient
	@Override
	public List<Player> getOwners()
	{
		return Collections.nCopies(1, getPlayer());
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

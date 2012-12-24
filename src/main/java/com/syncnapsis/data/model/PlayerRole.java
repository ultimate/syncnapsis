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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.syncnapsis.data.model.base.BaseObject;

/**
 * Model-Klasse "Benutzerrolle"
 * Benutzerrollen dienen der Rechtevergabe im Spiel. Über sie ist geregelt, was
 * ein Benutzer/Spieler darf oder nicht. Sie legen außerdem bestimmte Grenzen
 * und Parameter für Benutzer/Spieler dieser Rolle fest.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "playerrole")
public class PlayerRole extends BaseObject<Long>
{
	/**
	 * Der Name der Benutzerrolle
	 */
	protected String		rolename;
	/**
	 * Die Standardrolle, die dem Benutzer zugewiesen wird, wenn die aktuelle Rolle abgelaufen ist.
	 */
	protected PlayerRole	fallbackRole;

	/**
	 * Wie viele Imperien darf ein Spieler dieser Rolle maximal haben?
	 */
	protected int			maxEmpires;

	/**
	 * Wie viele Sitter darf ein Spieler dieser Rolle maximal haben?
	 */
	protected int			maxSitters;
	/**
	 * Wie viele Spieler darf ein Spieler dieser Rolle maximal sitten?
	 */
	protected int			maxSitted;

	/**
	 * Leerer Standard Constructor
	 */
	public PlayerRole()
	{
	}

	/**
	 * Der Name der Benutzerrolle
	 * 
	 * @return rolename
	 */
	@Column(nullable = false, unique = true, length = LENGTH_ID)
	public String getRolename()
	{
		return rolename;
	}

	/**
	 * Die Standardrolle, die dem Benutzer zugewiesen wird, wenn die aktuelle Rolle abgelaufen ist.
	 * 
	 * @return fallbackRole
	 */
	@ManyToOne
	@JoinTable(name = "playerrole_fallback", joinColumns = @JoinColumn(name = "fkRole"), inverseJoinColumns = @JoinColumn(name = "fkFallbackRole"))
	public PlayerRole getFallbackRole()
	{
		return fallbackRole;
	}

	/**
	 * Wie viele Imperien darf ein Spieler dieser Rolle maximal haben?
	 * 
	 * @return maxEmpires
	 */
	@Column(nullable = false)
	public int getMaxEmpires()
	{
		return maxEmpires;
	}

	/**
	 * Wie viele Sitter darf ein Spieler dieser Rolle maximal haben?
	 * 
	 * @return maxSitters
	 */
	@Column(nullable = false)
	public int getMaxSitters()
	{
		return maxSitters;
	}

	/**
	 * Wie viele Spieler darf ein Spieler dieser Rolle maximal sitten?
	 * 
	 * @return maxSitted
	 */
	@Column(nullable = false)
	public int getMaxSitted()
	{
		return maxSitted;
	}

	/**
	 * Der Name der Benutzerrolle
	 * 
	 * @param rolename - der Name
	 */
	public void setRolename(String rolename)
	{
		this.rolename = rolename;
	}

	/**
	 * Die Standardrolle, die dem Benutzer zugewiesen wird, wenn die aktuelle Rolle abgelaufen ist.
	 * 
	 * @param fallbackRole - die Rolle
	 */
	public void setFallbackRole(PlayerRole fallbackRole)
	{
		this.fallbackRole = fallbackRole;
	}

	/**
	 * Wie viele Imperien darf ein Spieler dieser Rolle maximal haben?
	 * 
	 * @param maxEmpires - die Anzahl
	 */
	public void setMaxEmpires(int maxEmpires)
	{
		this.maxEmpires = maxEmpires;
	}

	/**
	 * Wie viele Sitter darf ein Spieler dieser Rolle maximal haben?
	 * 
	 * @param maxSitters - die Anzahl
	 */
	public void setMaxSitters(int maxSitters)
	{
		this.maxSitters = maxSitters;
	}

	/**
	 * Wie viele Spieler darf ein Spieler dieser Rolle maximal sitten?
	 * 
	 * @param maxSitted - die Anzahl
	 */
	public void setMaxSitted(int maxSitted)
	{
		this.maxSitted = maxSitted;
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
		PlayerRole other = (PlayerRole) obj;
		if(fallbackRole == null)
		{
			if(other.fallbackRole != null)
				return false;
		}
		else if(!fallbackRole.equals(other.fallbackRole))
			return false;
		if(maxEmpires != other.maxEmpires)
			return false;
		if(maxSitted != other.maxSitted)
			return false;
		if(maxSitters != other.maxSitters)
			return false;
		if(rolename == null)
		{
			if(other.rolename != null)
				return false;
		}
		else if(!rolename.equals(other.rolename))
			return false;
		return true;
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
		result = prime * result + ((fallbackRole == null) ? 0 : fallbackRole.hashCode());
		result = prime * result + maxEmpires;
		result = prime * result + maxSitted;
		result = prime * result + maxSitters;
		result = prime * result + ((rolename == null) ? 0 : rolename.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("version", version).append("rolename", rolename).append("maxEmpires", maxEmpires)
				.append("maxSitted", maxSitted).append("maxSitters", maxSitters);
		return builder.toString();
	}
}

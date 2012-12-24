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
@Table(name = "app_userrole")
public class UserRole extends BaseObject<Long>
{
	/**
	 * Der Name der Benutzerrolle
	 */
	protected String	rolename;
	/**
	 * Die Standardrolle, die dem Benutzer zugewiesen wird, wenn die aktuelle Rolle abgelaufen ist.
	 */
	protected UserRole	fallbackRole;
	/**
	 * Darf dieser Benutzer nur aktivierte Einträge sehen?
	 */
	protected boolean	onlyAllowedToSeeActivated;

	/**
	 * Leerer Standard Constructor
	 */
	public UserRole()
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
	@JoinTable(name = "app_userrole_fallback", joinColumns = @JoinColumn(name = "fkRole"), inverseJoinColumns = @JoinColumn(name = "fkFallbackRole"))
	public UserRole getFallbackRole()
	{
		return fallbackRole;
	}

	/**
	 * Darf dieser Benutzer nur aktivierte Einträge sehen?
	 * 
	 * @return allowedToSeeDeactivated
	 */
	@Column(nullable = false)
	public boolean isOnlyAllowedToSeeActivated()
	{
		return onlyAllowedToSeeActivated;
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
	public void setFallbackRole(UserRole fallbackRole)
	{
		this.fallbackRole = fallbackRole;
	}

	/**
	 * Darf dieser Benutzer nur aktivierte Einträge sehen?
	 * 
	 * @param onlyAllowedToSeeActivated - true oder false
	 */
	public void setOnlyAllowedToSeeActivated(boolean onlyAllowedToSeeActivated)
	{
		this.onlyAllowedToSeeActivated = onlyAllowedToSeeActivated;
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
		UserRole other = (UserRole) obj;
		if(onlyAllowedToSeeActivated != other.onlyAllowedToSeeActivated)
			return false;
		if(fallbackRole == null)
		{
			if(other.fallbackRole != null)
				return false;
		}
		else if(!fallbackRole.equals(other.fallbackRole))
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
		result = prime * result + (onlyAllowedToSeeActivated ? 1231 : 1237);
		result = prime * result + ((fallbackRole == null) ? 0 : fallbackRole.hashCode());
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
		builder.append("id", id).append("version", version).append("rolename", rolename)
				.append("onlyAllowedToSeeActivated", onlyAllowedToSeeActivated);
		return builder.toString();
	}
}

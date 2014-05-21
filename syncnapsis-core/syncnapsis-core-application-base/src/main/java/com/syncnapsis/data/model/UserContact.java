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

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.security.Ownable;

/**
 * Model-Klasse "Kontakt-/Freundschaftszuordnung"
 * Diese Klasse repräsentiert die Kontakt-/Freundschaftszuordnung zwischen 2
 * Benutzern. Sie ist nicht zu verwechseln mit den diplomatischen Beziehungen
 * zwischen 2 Imperien.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "usercontact")
public class UserContact extends BaseObject<Long> implements Ownable<User>
{
	/**
	 * Der 1. Benutzer für die Kontakt-/Freundschaftszuordnung
	 */
	protected User	user1;
	/**
	 * Der 2. Benutzer für die Kontakt-/Freundschaftszuordnung
	 */
	protected User	user2;
	/**
	 * Hat der 1. Benutzer die Kontakt-/Freundschaftszuordnung bestätigt?
	 */
	protected boolean	approvedByUser1;
	/**
	 * Hat der 2. Benutzer die Kontakt-/Freundschaftszuordnung bestätigt?
	 */
	protected boolean	approvedByUser2;

	/**
	 * Leerer Standard Constructor
	 */
	public UserContact()
	{
	}

	/**
	 * Der 1. Benutzer für die Kontakt-/Freundschaftszuordnung
	 * 
	 * @return user1
	 */
	@ManyToOne
	@JoinColumn(name = "fkUser1", nullable = false)
	public User getUser1()
	{
		return user1;
	}

	/**
	 * Der 2. Benutzer für die Kontakt-/Freundschaftszuordnung
	 * 
	 * @return user2
	 */
	@ManyToOne
	@JoinColumn(name = "fkUser2", nullable = false)
	public User getUser2()
	{
		return user2;
	}

	/**
	 * Hat der 1. Benutzer die Kontakt-/Freundschaftszuordnung bestätigt?
	 * 
	 * @return approvedByUser1
	 */
	@Column(nullable = true)
	public boolean isApprovedByUser1()
	{
		return approvedByUser1;
	}

	/**
	 * Hat der 2. Benutzer die Kontakt-/Freundschaftszuordnung bestätigt?
	 * 
	 * @return approvedByUser2
	 */
	@Column(nullable = true)
	public boolean isApprovedByUser2()
	{
		return approvedByUser2;
	}

	/**
	 * Gibt den anderen Benutzer zurück
	 * 
	 * @param user - der eine Benutzer
	 * @return den anderen Benutzer
	 */
	@Transient
	public User getOtherUser(User user)
	{
		if(user1.getId().equals(user.getId()))
			return user2;
		if(user2.getId().equals(user.getId()))
			return user1;
		return null;
	}

	/**
	 * Prüft, ob der andere Benutzer die Kontakt-/Freundschaftszuordnung
	 * bestätigt hat
	 * 
	 * @param user - einer der beiden Benutzer
	 * @return approvedByUser 'OtherThanX'
	 */
	@Transient
	public boolean isApprovedByOtherUser(User user)
	{
		if(user1.getId().equals(user.getId()))
			return approvedByUser2;
		if(user2.getId().equals(user.getId()))
			return approvedByUser1;
		return false;
	}

	/**
	 * Prüft, ob der gegebene Benutzer die Kontakt-/Freundschaftszuordnung
	 * bestätigt hat
	 * 
	 * @param user - einer der beiden Benutzer
	 * @return approvedByUser 'X'
	 */
	@Transient
	public boolean isApprovedByUser(User user)
	{
		if(user1.getId().equals(user.getId()))
			return approvedByUser1;
		if(user2.getId().equals(user.getId()))
			return approvedByUser2;
		return false;
	}

	/**
	 * Der 1. Benutzer für die Kontakt-/Freundschaftszuordnung
	 * 
	 * @param user1 - der 1. Benutzer
	 */
	public void setUser1(User user1)
	{
		this.user1 = user1;
	}

	/**
	 * Der 2. Benutzer für die Kontakt-/Freundschaftszuordnung
	 * 
	 * @param user2 - der 2. Benutzer
	 */
	public void setUser2(User user2)
	{
		this.user2 = user2;
	}

	/**
	 * Hat der 1. Benutzer die Kontakt-/Freundschaftszuordnung bestätigt?
	 * 
	 * @param approvedByUser1 - true oder false
	 */
	public void setApprovedByUser1(boolean approvedByUser1)
	{
		this.approvedByUser1 = approvedByUser1;
	}

	/**
	 * Hat der 2. Benutzer die Kontakt-/Freundschaftszuordnung bestätigt?
	 * 
	 * @param approvedByUser2 - true oder false
	 */
	public void setApprovedByUser2(boolean approvedByUser2)
	{
		this.approvedByUser2 = approvedByUser2;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.Ownable#getOwners()
	 */
	@Transient
	@Override
	public List<User> getOwners()
	{
		return Arrays.asList(user1, user2);
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
		if(!(obj instanceof UserContact))
			return false;
		UserContact other = (UserContact) obj;
		if(approvedByUser1 != other.approvedByUser1)
			return false;
		if(approvedByUser2 != other.approvedByUser2)
			return false;
		if(user1 == null)
		{
			if(other.user1 != null)
				return false;
		}
		else if(!user1.getId().equals(other.user1.getId()))
			return false;
		if(user2 == null)
		{
			if(other.user2 != null)
				return false;
		}
		else if(!user2.getId().equals(other.user2.getId()))
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
		result = prime * result + (approvedByUser1 ? 1231 : 1237);
		result = prime * result + (approvedByUser2 ? 1231 : 1237);
		result = prime * result + ((user1 == null) ? 0 : user1.getId().hashCode());
		result = prime * result + ((user2 == null) ? 0 : user2.getId().hashCode());
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
		builder.append("id", id).append("version", version).append("user1", user1.getId()).append("approvedByUser1", approvedByUser1).append("user2",
				user2.getId()).append("approvedByUser2", approvedByUser2);
		return builder.toString();
	}
}

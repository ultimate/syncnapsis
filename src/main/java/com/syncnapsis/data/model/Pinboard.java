/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syncnapsis.data.model.base.ActivatableInstance;

/**
 * Pinboard entity for posting messages related to the topic of this pinboard.
 * 
 * @author ultimate
 */
@Entity
@Table(name="pinboard")
public class Pinboard extends ActivatableInstance<Long>
{
	/**
	 * The name of this pinboard
	 */
	protected String				name;

	/**
	 * A description for this pinboard
	 */
	protected String				description;

	/**
	 * The user that created the pinboard
	 */
	protected User					creator;

	/**
	 * The date this pinboard was created
	 */
	protected Date					creationDate;

	/**
	 * Is this pinboard locked for new messages?
	 */
	protected boolean				locked;

	/**
	 * The messages posted to this pinboard
	 */
	protected List<PinboardMessage>	messages;

	/**
	 * The name of this pinboard
	 * 
	 * @return name
	 */
	@Column(nullable = false, unique = true, length = LENGTH_NAME_NORMAL)
	public String getName()
	{
		return name;
	}

	/**
	 * A description for this pinboard
	 * 
	 * @return description
	 */
	@Column(nullable = true, length = LENGTH_DESCRIPTION)
	public String getDescription()
	{
		return description;
	}

	/**
	 * The user that created the pinboard
	 * 
	 * @return creator
	 */
	@ManyToOne
	@JoinColumn(name = "fkCreator", nullable = false)
	public User getCreator()
	{
		return creator;
	}

	/**
	 * The date this pinboard was created
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
	 * Is this pinboard locked for new messages?
	 * 
	 * @return locked
	 */
	@Column(nullable = false)
	public boolean isLocked()
	{
		return locked;
	}

	/**
	 * The messages posted to this pinboard
	 * 
	 * @return messages
	 */
	@OneToMany(mappedBy = "pinboard")
	public List<PinboardMessage> getMessages()
	{
		return messages;
	}

	/**
	 * The name of this pinboard
	 * 
	 * @param name - the name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * A description for this pinboard
	 * 
	 * @param description - the description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * The user that created the pinboard
	 * 
	 * @param creator - the User
	 */
	public void setCreator(User creator)
	{
		this.creator = creator;
	}

	/**
	 * The date this pinboard was created
	 * 
	 * @param creationDate - the date and time
	 */
	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * Is this pinboard locked for new messages?
	 * 
	 * @param locked - true or false
	 */
	public void setLocked(boolean locked)
	{
		this.locked = locked;
	}

	/**
	 * The messages posted to this pinboard
	 * 
	 * @param messages - the List of messages
	 */
	public void setMessages(List<PinboardMessage> messages)
	{
		this.messages = messages;
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
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.getId().hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (locked ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Pinboard other = (Pinboard) obj;
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
		if(description == null)
		{
			if(other.description != null)
				return false;
		}
		else if(!description.equals(other.description))
			return false;
		if(locked != other.locked)
			return false;
		if(name == null)
		{
			if(other.name != null)
				return false;
		}
		else if(!name.equals(other.name))
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
		builder.append("name", name).append("description", description).append("creator", creator.getId()).append("creationDate", creationDate)
				.append("locked", locked);
		return builder.toString();
	}

}

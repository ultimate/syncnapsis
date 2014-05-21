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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.syncnapsis.data.model.base.BaseObject;

/**
 * Entity representing a black-list for Strings
 * 
 * @author ultimate
 */
@Entity
@Table(name = "blacklistentry")
public class BlackListEntry extends BaseObject<Long>
{
	/**
	 * The black-list value
	 */
	protected String	value;

	/**
	 * The black-list this entry belongs to
	 */
	protected BlackList	blackList;

	/**
	 * The black-list value
	 * 
	 * @return value
	 */
	@Column(nullable = false, length = LENGTH_EMAIL)
	public String getValue()
	{
		return value;
	}

	/**
	 * The black-list this entry belongs to
	 * 
	 * @return blackList
	 */
	@ManyToOne
	@JoinColumn(name = "fkBlackList", nullable = false)
	public BlackList getBlackList()
	{
		return blackList;
	}

	/**
	 * The black-list value
	 * 
	 * @param value - the value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * The black-list this entry belongs to
	 * 
	 * @param blackList - the BlackList
	 */
	public void setBlackList(BlackList blackList)
	{
		this.blackList = blackList;
	}

	/*
	 * 	(non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((blackList == null) ? 0 : blackList.getId().hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		BlackListEntry other = (BlackListEntry) obj;
		if(blackList == null)
		{
			if(other.blackList != null)
				return false;
		}
		else if(!blackList.getId().equals(other.blackList.getId()))
			return false;
		if(value == null)
		{
			if(other.value != null)
				return false;
		}
		else if(!value.equals(other.value))
			return false;
		return true;
	}
}

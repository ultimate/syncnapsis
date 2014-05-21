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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.syncnapsis.data.model.base.BaseObject;

/**
 * Klasse, die Gruppen für die Zusammenfassung von einzelnen Rechten definiert.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "authoritygroup")
public class AuthorityGroup extends BaseObject<Long>
{
	/**
	 * Der Name dieser Rechte-Gruppe
	 */
	protected String	name;
	/**
	 * Die Liste der dazugehörigen Authority-Namen
	 */
	protected List<Authority> authorities;

	/**
	 * Leerer Standard Constructor
	 */
	public AuthorityGroup()
	{
	}

	/**
	 * Der Name dieses Rechts
	 * @return name
	 */
	@Column(nullable = false, unique = true, length = LENGTH_NAME_LONG)
	public String getName()
	{
		return name;
	}

	/** 
	 * Die Liste der dazugehörigen Authority-Namen
	 * 
	 * @return authorities
	 */
	@OneToMany(mappedBy = "group")
	public List<Authority> getAuthorities()
	{
		return authorities;
	}

	/**
	 * Der Name dieses Rechts
	 * @param name - der Name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Die Liste der dazugehörigen Authority-Namen
	 * 
	 * @param authorities
	 */
	public void setAuthorities(List<Authority> authorities)
	{
		this.authorities = authorities;
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
		Authority other = (Authority) obj;
		if(name == null)
		{
			if(other.name != null)
				return false;
		}
		else if(!name.equals(other.name))
			return false;
		return true;
	}
}

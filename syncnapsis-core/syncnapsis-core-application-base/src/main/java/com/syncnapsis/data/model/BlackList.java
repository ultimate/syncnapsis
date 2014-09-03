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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.security.validation.BlackListStringValidator;

/**
 * Entity representing a black-list for Strings
 * 
 * @author ultimate
 */
@Entity
@Table(name = "blacklist")
public class BlackList extends BaseObject<Long>
{
	/**
	 * The name of the black-list
	 */
	protected String				name;

	/**
	 * Is this black-list strict?
	 * 
	 * @see BlackListStringValidator#isStrict()
	 */
	protected boolean				strict;

	/**
	 * The list of entries for this black-list
	 */
	protected List<BlackListEntry>	entries;

	/**
	 * The name of the black-list
	 * 
	 * @return name
	 */
	@Column(nullable = false, length = LENGTH_NAME_NORMAL)
	public String getName()
	{
		return name;
	}

	/**
	 * Is this black-list strict?
	 * 
	 * @see BlackListStringValidator#isStrict()
	 * 
	 * @return strict
	 */
	@Column(nullable = false)
	public boolean isStrict()
	{
		return strict;
	}

	/**
	 * The list of entries for this black-list
	 * 
	 * @return entries
	 */
	@OneToMany(mappedBy = "blackList")
	public List<BlackListEntry> getEntries()
	{
		return entries;
	}

	/**
	 * The name of the black-list
	 * 
	 * @param name - the name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Is this black-list strict?
	 * 
	 * @see BlackListStringValidator#isStrict()
	 * 
	 * @param strict - true or false
	 */
	public void setStrict(boolean strict)
	{
		this.strict = strict;
	}

	/**
	 * The list of entries for this black-list
	 * 
	 * @param entries - the List
	 */
	public void setEntries(List<BlackListEntry> entries)
	{
		this.entries = entries;
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
		result = prime * result + (strict ? 1231 : 1237);
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
		BlackList other = (BlackList) obj;
		if(name == null)
		{
			if(other.name != null)
				return false;
		}
		else if(!name.equals(other.name))
			return false;
		if(strict != other.strict)
			return false;
		return true;
	}
}

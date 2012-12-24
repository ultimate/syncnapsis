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
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syncnapsis.data.model.base.BaseObject;

/**
 * Model-Class "Parameter"
 * Parameter are used to configure the application via the database.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "parameter")
public class Parameter extends BaseObject<Long>
{
	/**
	 * The parameter name
	 */
	private String	name;
	/**
	 * The parameter value
	 */
	private String	value;

	/**
	 * Empty default construcotr
	 */
	public Parameter()
	{
	}
	
	/**
	 * The parameter name
	 * 
	 * @return name
	 */
	@Column(nullable = false, length = LENGTH_NAME_NORMAL)
	public String getName()
	{
		return name;
	}

	/**
	 * The parameter name
	 * 
	 * @param name - the name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	
	/**
	 * The parameter value
	 * 
	 * @return value
	 */
	@Column(nullable = false, length = LENGTH_PARAMETERVALUE)
	public String getValue()
	{
		return value;
	}

	/**
	 * The parameter value
	 * 
	 * @param value - the value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.model.base.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(!(obj instanceof Parameter))
			return false;
		Parameter other = (Parameter) obj;
		if(value == null)
		{
			if(other.value != null)
				return false;
		}
		else if(!value.equals(other.value))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.model.base.BaseObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		builder.append("id", id).append("version", version).append("value", value);
		return builder.toString();
	}
}

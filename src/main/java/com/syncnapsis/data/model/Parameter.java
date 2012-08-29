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
public class Parameter extends BaseObject<String>
{
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

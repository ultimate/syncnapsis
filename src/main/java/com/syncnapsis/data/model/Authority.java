package com.syncnapsis.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.syncnapsis.data.model.base.BaseObject;

/**
 * Klasse, die die einzelnen möglichen Rechte definiert. Sie dient nur als Lookup-Table und enthält
 * quasi nur die Namen der Rechte.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "authority")
public class Authority extends BaseObject<Long>
{
	/**
	 * Der Name dieses Rechts
	 */
	protected String	name;
	/**
	 * Eine Gruppe, der diese Rechte zugeordnet sind.
	 */
	protected AuthorityGroup group;

	/**
	 * Leerer Standard Constructor
	 */
	public Authority()
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
	 * Eine Gruppe, der diese Rechte zugeordnet sind.
	 * 
	 * @return group
	 */
	@ManyToOne
	@JoinColumn(name = "fkAuthorityGroup", nullable = true)
	public AuthorityGroup getGroup()
	{
		return group;
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
	 * Eine Gruppe, der diese Rechte zugeordnet sind.
	 * 
	 * @param group
	 */
	public void setGroup(AuthorityGroup group)
	{
		this.group = group;
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
		result = prime * result + ((group == null) ? 0 : group.getId().hashCode());
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
		if(group == null)
		{
			if(other.group != null)
				return false;
		}
		else if(!group.getId().equals(other.group.getId()))
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
}

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
package com.syncnapsis.data.model.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Institution<PK extends Serializable> extends ActivatableInstance<PK>
{
	/**
	 * Kurzname bzw. Abk�rzung des Imperiums. Muss einmalig sein.
	 */
	protected String	shortName;
	/**
	 * Ausgeschriebener Name des Imperiums.
	 */
	protected String	fullName;

	/**
	 * Beschreibung
	 */
	protected String	description;
	/**
	 * URL zu einem optionalen Bild/Logo
	 */
	protected String	imageURL;

	/**
	 * The primary color for this institution
	 */
	protected String	primaryColor;
	/**
	 * The primary color for this institution
	 */
	protected String	secondaryColor;

	/**
	 * The date this institution was founded
	 */
	protected Date		foundationDate;
	/**
	 * The date this institution was deleted
	 */
	protected Date		dissolutionDate;

	/**
	 * Kurzname bzw. Abk�rzung des Imperiums. Muss einmalig sein.
	 * 
	 * @return shortName
	 */
	@Column(nullable = false, unique = true, length = LENGTH_NAME_SHORT)
	public String getShortName()
	{
		return shortName;
	}

	/**
	 * Ausgeschriebener Name des Imperiums.
	 * 
	 * @return fullName
	 */
	@Column(nullable = true, unique = false, length = LENGTH_NAME_LONG)
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * Beschreibung
	 * 
	 * @return description
	 */
	@Column(nullable = true, length = LENGTH_DESCRIPTION)
	public String getDescription()
	{
		return description;
	}

	/**
	 * URL zu einem optionalen Bild/Logo
	 * 
	 * @return imageURL
	 */
	@Column(nullable = true, length = LENGTH_URL)
	public String getImageURL()
	{
		return imageURL;
	}

	/**
	 * The primary color for this institution
	 * 
	 * @return primaryColor
	 */
	@Column(nullable = false, length = LENGTH_NAME_NORMAL)
	public String getPrimaryColor()
	{
		return primaryColor;
	}

	/**
	 * The secondary color for this institution
	 * 
	 * @return secondaryColor
	 */
	@Column(nullable = true, length = LENGTH_NAME_NORMAL)
	public String getSecondaryColor()
	{
		return secondaryColor;
	}

	/**
	 * The date this institution was founded
	 * 
	 * @return foundationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getFoundationDate()
	{
		return foundationDate;
	}

	/**
	 * The date this institution was founded
	 * 
	 * @return dissolutionDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getDissolutionDate()
	{
		return dissolutionDate;
	}

	/**
	 * Kurzname bzw. Abk�rzung des Imperiums. Muss einmalig sein.
	 * 
	 * @param shortName - der Kurzname
	 */
	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	/**
	 * Ausgeschriebener Name des Imperiums.
	 * 
	 * @param fullName - der Name
	 */
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	/**
	 * Beschreibung
	 * 
	 * @param description - die Beschreibung
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * URL zu einem optionalen Bild/Logo
	 * 
	 * @param imageURL - die URL
	 */
	public void setImageURL(String imageURL)
	{
		this.imageURL = imageURL;
	}

	/**
	 * The primary color for this institution
	 * 
	 * @param primaryColor - the primary color
	 */
	public void setPrimaryColor(String primaryColor)
	{
		this.primaryColor = primaryColor;
	}

	/**
	 * The secondary color for this institution
	 * 
	 * @param secondaryColor - the secondary color
	 */
	public void setSecondaryColor(String secondaryColor)
	{
		this.secondaryColor = secondaryColor;
	}

	/**
	 * The date this institution was founded
	 * 
	 * @param foundationDate - the date and time
	 */
	public void setFoundationDate(Date foundationDate)
	{
		this.foundationDate = foundationDate;
	}

	/**
	 * The date this institution was deleted
	 * 
	 * @param dissolutionDate - the date and time
	 */
	public void setDissolutionDate(Date dissolutionDate)
	{
		this.dissolutionDate = dissolutionDate;
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
		result = prime * result + ((foundationDate == null) ? 0 : foundationDate.hashCode());
		result = prime * result + ((dissolutionDate == null) ? 0 : dissolutionDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + ((primaryColor == null) ? 0 : primaryColor.hashCode());
		result = prime * result + ((secondaryColor == null) ? 0 : secondaryColor.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		Institution other = (Institution) obj;
		if(foundationDate == null)
		{
			if(other.foundationDate != null)
				return false;
		}
		else if(!foundationDate.equals(other.foundationDate))
			return false;
		if(dissolutionDate == null)
		{
			if(other.dissolutionDate != null)
				return false;
		}
		else if(!dissolutionDate.equals(other.dissolutionDate))
			return false;
		if(description == null)
		{
			if(other.description != null)
				return false;
		}
		else if(!description.equals(other.description))
			return false;
		if(fullName == null)
		{
			if(other.fullName != null)
				return false;
		}
		else if(!fullName.equals(other.fullName))
			return false;
		if(imageURL == null)
		{
			if(other.imageURL != null)
				return false;
		}
		else if(!imageURL.equals(other.imageURL))
			return false;
		if(primaryColor == null)
		{
			if(other.primaryColor != null)
				return false;
		}
		else if(!primaryColor.equals(other.primaryColor))
			return false;
		if(secondaryColor == null)
		{
			if(other.secondaryColor != null)
				return false;
		}
		else if(!secondaryColor.equals(other.secondaryColor))
			return false;
		if(shortName == null)
		{
			if(other.shortName != null)
				return false;
		}
		else if(!shortName.equals(other.shortName))
			return false;
		return true;
	}
}

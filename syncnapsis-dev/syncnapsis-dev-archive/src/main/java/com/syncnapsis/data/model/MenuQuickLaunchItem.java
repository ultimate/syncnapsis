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
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.syncnapsis.data.model.base.BaseObject;

/**
 * Model-Klasse "Schnellstart-Menü-Eintrag"
 * Die Klasse repräsentiert eine Verknüpfung zwischen MenuItem und Benutzer um
 * festzulegen, an welcher Stelle und mit welchen ggf. notwendigen Parametern
 * ein Menü-Eintrag zusätzlich auch im Schnellstart-Menü angezeigt wird.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "menuquicklaunchitem")
public class MenuQuickLaunchItem extends BaseObject<Long>
{
	/**
	 * Der Benutzer für den das Schnellstart-Menü gilt
	 */
	private User		user;
	/**
	 * Der Menü-Eintrag, der angezeigt werden soll
	 */
	private MenuItem	menuItem;
	/**
	 * Position des Menü-Eintrags im Schnellstart-Menü
	 */
	private int			position;
	/**
	 * Ggf. notwendiger Parameter-Wert für die Action des Menu-Eintrags
	 */
	private String		parameterValueString;
	/**
	 * Wert des ggf. notwendigen Parameters zur Übergabe an die GUIAction
	 */
	private Long		parameterValueLong;

	/**
	 * Leerer Standard Constructor
	 */
	public MenuQuickLaunchItem()
	{
	}

	/**
	 * Der Benutzer für den das Schnellstart-Menü gilt
	 * 
	 * @return user
	 */
	@ManyToOne
	@JoinColumn(name = "fkUser", nullable = false)
	public User getUser()
	{
		return user;
	}

	/**
	 * Der Menü-Eintrag, der angezeigt werden soll
	 * 
	 * @return menuItem
	 */
	@ManyToOne
	@JoinColumn(name = "fkMenuItem", nullable = false)
	public MenuItem getMenuItem()
	{
		return menuItem;
	}

	/**
	 * Position des Menü-Eintrags im Schnellstart-Menü
	 * 
	 * @return position
	 */
	@Column(nullable = false)
	public int getPosition()
	{
		return position;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur Übergabe an die GUIAction
	 * 
	 * @return parameterValueString
	 */
	@Column(nullable = true, length = LENGTH_PARAMETERVALUE)
	public String getParameterValueString()
	{
		return parameterValueString;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur Übergabe an die GUIAction
	 * 
	 * @return parameterValueLong
	 */
	@Column(nullable = true)
	public Long getParameterValueLong()
	{
		return parameterValueLong;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur Übergabe an die GUIAction
	 * Gibt den gesetzten ParameterValue zurück. Entweder String oder Long
	 * 
	 * @return parameterValue
	 */
	@Transient
	public Object getParameterValue()
	{
		if(parameterValueString != null)
			return parameterValueString;
		if(parameterValueLong != null)
			return parameterValueLong;
		return null;
	}

	/**
	 * Der Benutzer für den das Schnellstart-Menü gilt
	 * 
	 * @param user - der Benutzer
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

	/**
	 * Der Menü-Eintrag, der angezeigt werden soll
	 * 
	 * @param menuItem - der Menü-Eintrag
	 */
	public void setMenuItem(MenuItem menuItem)
	{
		this.menuItem = menuItem;
	}

	/**
	 * Position des Menü-Eintrags im Schnellstart-Menü
	 * 
	 * @param position - die Position
	 */
	public void setPosition(int position)
	{
		this.position = position;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur Übergabe an die GUIAction
	 * 
	 * @param parameterValueString - der Wert
	 */
	public void setParameterValueString(String parameterValueString)
	{
		this.parameterValueString = parameterValueString;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur Übergabe an die GUIAction als
	 * Long geparst
	 * 
	 * @param parameterValueLong - der Wert
	 */
	public void setParameterValueLong(Long parameterValueLong)
	{
		this.parameterValueLong = parameterValueLong;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur Übergabe an die GUIAction
	 * 
	 * @param parameterValue - der Wert
	 */
	public void setParameterValue(Object parameterValue)
	{
		if(parameterValue == null)
		{
			this.setParameterValueString(null);
			this.setParameterValueLong(null);
		}
		else if(parameterValue instanceof String)
		{
			this.setParameterValueString((String) parameterValue);
			this.setParameterValueLong(null);
		}
		else if(parameterValue instanceof Long)
		{
			this.setParameterValueString(null);
			this.setParameterValueLong((Long) parameterValue);
		}
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
		if(!(obj instanceof MenuQuickLaunchItem))
			return false;
		MenuQuickLaunchItem other = (MenuQuickLaunchItem) obj;
		if(menuItem == null)
		{
			if(other.menuItem != null)
				return false;
		}
		else if(!menuItem.getId().equals(other.menuItem.getId()))
			return false;
		if(position != other.position)
			return false;
		if(user == null)
		{
			if(other.user != null)
				return false;
		}
		else if(!user.getId().equals(other.user.getId()))
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
		result = prime * result + ((menuItem == null) ? 0 : menuItem.getId().hashCode());
		result = prime * result + position;
		result = prime * result + ((user == null) ? 0 : user.getId().hashCode());
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
		builder.append("id", id).append("version", version).append("menuItem", menuItem.getId()).append("position", position)
		.append("user",	user.getId())
		;
		return builder.toString();
	}
}

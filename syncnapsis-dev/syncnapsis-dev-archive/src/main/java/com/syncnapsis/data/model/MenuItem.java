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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.enums.EnumMenuItemDynamicType;
import com.syncnapsis.enums.EnumMenuItemType;
import com.syncnapsis.utils.graphs.GenericTreeNode;

/**
 * Model-Klasse "Men�-Eintrag"
 * Diese Klasse repr�sentiert einen Men�-Eintrag, der im Hauptmen� der Anwendung
 * dargestellt wird. Die Men�-Eintr�ge sind dabei in einer Baumstruktur
 * organisiert und repr�sentieren dessen Knoten.
 * �ber die Verkn�pfung durch die Klasse MenuQuickLaunchItem zu einem Benutzer
 * k�nnen die gleichen Men�-Eintr�ge auch im Schnellstart-Men� angezeigt werden.
 * Die Klasse implementiert GenericTreeNode f�r die dynamische Erstellung des
 * Men�s.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "menuitem")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class MenuItem extends BaseObject<String> implements GenericTreeNode<MenuItem>, Cloneable
{
	/**
	 * Schl�ssel f�r die Sprachabh�ngige Ausgabe des Titels des Men�-Eintrags
	 */
	private String					titleKey;
	/**
	 * Spezifizierung des Typs
	 */
	private EnumMenuItemType		type;
	/**
	 * Spezifizierung des dynamischen Typs
	 */
	private EnumMenuItemDynamicType	dynamicType;
	/**
	 * Spezifizierung des dynamischen Subtyps
	 */
	private String					dynamicSubType;
	/**
	 * URL f�r die Anzeige eines Bildes
	 */
	private String					imageURL;
	/**
	 * Kann dieser Men�-Eintrag im Schnellstart-Men� verwendet werden?
	 */
	private boolean					quickLaunchEnabled;
	/**
	 * Ist dieser Men�-Eintrag nur bei erweitertem Men� sichtbar?
	 */
	private boolean					advancedItem;
	/**
	 * An welcher Stelle steht dieser Men�-Eintrag unter allern Kindern des
	 * Elternknotens
	 */
	private int						position;

	/**
	 * Die GUIAction, die bei einem Klick ausgef�hrt wird
	 */
	private GUIAction				action;
	/**
	 * Name des ggf. notwendigen Parameters zur �bergabe an die GUIAction
	 */
	private String					parameterName;
	/**
	 * Wert des ggf. notwendigen Parameters zur �bergabe an die GUIAction
	 */
	private String					parameterValueString;
	/**
	 * Wert des ggf. notwendigen Parameters zur �bergabe an die GUIAction
	 */
	private Long					parameterValueLong;
	/**
	 * Ist der Men�-Eintrag inaktiv
	 * (tempor�rer Wert)
	 */
	private boolean					disabled;

	/**
	 * Der �bergeordnete Men�-Eintrag
	 */
	private MenuItem				parent;
	/**
	 * Liste aller untergeordneten Men�-Eintr�ge
	 */
	private List<MenuItem>			children;

	/**
	 * Leerer Standard Constructor
	 */
	public MenuItem()
	{
	}

	/**
	 * Schl�ssel f�r die Sprachabh�ngige Ausgabe des Titels des Men�-Eintrags
	 * 
	 * @return titleKey - der Schl�ssel
	 */
	@Column(nullable = true, length = LENGTH_LANGUAGE_KEY)
	public String getTitleKey()
	{
		return titleKey;
	}

	/**
	 * Spezifizierung des Typs
	 * 
	 * @return type
	 */
	@Column(nullable = false, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumMenuItemType getType()
	{
		return type;
	}

	/**
	 * Spezifizierung des dynamischen Typs
	 * 
	 * @return dynamicType
	 */
	@Column(nullable = true, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumMenuItemDynamicType getDynamicType()
	{
		return dynamicType;
	}

	/**
	 * Spezifizierung des dynamischen Subtyps
	 * @return dynamicSubType
	 */
	@Column(nullable = true, length = LENGTH_ENUM)
	public String getDynamicSubType()
	{
		return dynamicSubType;
	}

	/**
	 * URL f�r die Anzeige eines Bildes
	 * 
	 * @return imageURL
	 */
	@Column(nullable = true, length = LENGTH_URL)
	public String getImageURL()
	{
		return imageURL;
	}

	/**
	 * Kann dieser Men�-Eintrag im Schnellstart-Men� verwendet werden?
	 * 
	 * @return quickLaunchEnabled
	 */
	@Column(nullable = false)
	public boolean isQuickLaunchEnabled()
	{
		return quickLaunchEnabled;
	}

	/**
	 * Ist dieser Men�-Eintrag nur bei erweitertem Men� sichtbar?
	 * 
	 * @return advancedItem
	 */
	@Column(nullable = false)
	public boolean isAdvancedItem()
	{
		return advancedItem;
	}

	/**
	 * Die GUIAction, die bei einem Klick ausgef�hrt wird
	 * 
	 * @return position
	 */
	@Column(nullable = false)
	public int getPosition()
	{
		return position;
	}

	/**
	 * Die GUIAction, die bei einem Klick ausgef�hrt wird
	 * 
	 * @return action
	 */
	@ManyToOne
	@JoinColumn(name = "fkGUIAction", nullable = true)
	public GUIAction getAction()
	{
		return action;
	}

	/**
	 * Name des ggf. notwendigen Parameters zur �bergabe an die GUIAction
	 * 
	 * @return parameterName
	 */
	@Column(nullable = true, length = LENGTH_PARAMETER)
	public String getParameterName()
	{
		if(parameterName == null)
			return "";
		return parameterName;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur �bergabe an die GUIAction
	 * 
	 * @return parameterValueString
	 */
	@Column(nullable = true, length = LENGTH_PARAMETERVALUE)
	public String getParameterValueString()
	{
		return parameterValueString;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur �bergabe an die GUIAction
	 * 
	 * @return parameterValueLong
	 */
	@Column(nullable = true)
	public Long getParameterValueLong()
	{
		return parameterValueLong;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur �bergabe an die GUIAction
	 * Gibt den gesetzten ParameterValue zur�ck. Entweder String oder Long
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

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.trees.GenericTreeNode#isDisabled()
	 */
	@Override
	@Transient
	public boolean isDisabled()
	{
		return disabled;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.trees.GenericTreeNode#getParent()
	 */
	@Override
	@ManyToOne
	@JoinTable(name = "menuparent", joinColumns = { @JoinColumn(name = "fkMenuItem") }, inverseJoinColumns = { @JoinColumn(name = "fkParentMenuItem") })
	public MenuItem getParent()
	{
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.trees.GenericTreeNode#getChildren()
	 */
	@Override
	@Transient
	public List<MenuItem> getChildren()
	{
		return children;
	}

	/**
	 * Schl�ssel f�r die Sprachabh�ngige Ausgabe des Titels des Men�-Eintrags
	 * 
	 * @param titleKey
	 */
	public void setTitleKey(String titleKey)
	{
		this.titleKey = titleKey;
	}

	/**
	 * Spezifizierung des Typs
	 * 
	 * @param type - der Typ
	 */
	public void setType(EnumMenuItemType type)
	{
		this.type = type;
	}

	/**
	 * Spezifizierung des dynamischen Typs
	 * 
	 * @param dynamicType - der dynamische Typ
	 */
	public void setDynamicType(EnumMenuItemDynamicType dynamicType)
	{
		this.dynamicType = dynamicType;
	}

	/**
	 * Spezifizierung des dynamischen Subtyps
	 * 
	 * @param dynamicSubType
	 */
	public void setDynamicSubType(String dynamicSubType)
	{
		this.dynamicSubType = dynamicSubType;
	}

	/**
	 * URL f�r die Anzeige eines Bildes
	 * 
	 * @param imageURL - die URL
	 */
	public void setImageURL(String imageURL)
	{
		this.imageURL = imageURL;
	}

	/**
	 * Kann dieser Men�-Eintrag im Schnellstart-Men� verwendet werden?
	 * 
	 * @param quickLaunchEnabled - true oder false
	 */
	public void setQuickLaunchEnabled(boolean quickLaunchEnabled)
	{
		this.quickLaunchEnabled = quickLaunchEnabled;
	}

	/**
	 * Ist dieser Men�-Eintrag nur bei erweitertem Men� sichtbar?
	 * 
	 * @param advancedItem - true oder false
	 */
	public void setAdvancedItem(boolean advancedItem)
	{
		this.advancedItem = advancedItem;
	}

	/**
	 * Die GUIAction, die bei einem Klick ausgef�hrt wird
	 * 
	 * @param position - die Position
	 */
	public void setPosition(int position)
	{
		this.position = position;
	}

	/**
	 * Die GUIAction, die bei einem Klick ausgef�hrt wird
	 * 
	 * @param action - die GUIAction
	 */
	public void setAction(GUIAction action)
	{
		this.action = action;
	}

	/**
	 * Name des ggf. notwendigen Parameters zur �bergabe an die GUIAction
	 * 
	 * @param parameterName - der Name
	 */
	public void setParameterName(String parameterName)
	{
		this.parameterName = parameterName;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur �bergabe an die GUIAction
	 * 
	 * @param parameterValueString - der Wert
	 */
	public void setParameterValueString(String parameterValueString)
	{
		this.parameterValueString = parameterValueString;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur �bergabe an die GUIAction als Long
	 * 
	 * @param parameterValueLong - der Wert
	 */
	public void setParameterValueLong(Long parameterValueLong)
	{
		this.parameterValueLong = parameterValueLong;
	}

	/**
	 * Wert des ggf. notwendigen Parameters zur �bergabe an die GUIAction
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
	 * @see com.syncnapsis.ui.components.trees.GenericTreeNode#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.ui.components.trees.GenericTreeNode#setParent(java.lang.Object
	 * )
	 */
	@Override
	public void setParent(MenuItem parent)
	{
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.ui.components.trees.GenericTreeNode#setChildren(java.util
	 * .List)
	 */
	@Override
	public void setChildren(List<MenuItem> children)
	{
		this.children = children;
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
		if(!(obj instanceof MenuItem))
			return false;
		MenuItem other = (MenuItem) obj;
		if(action == null)
		{
			if(other.action != null)
				return false;
		}
		else if(!action.equals(other.action))
			return false;
		if(advancedItem != other.advancedItem)
			return false;
		if(disabled != other.disabled)
			return false;
		if(dynamicType == null)
		{
			if(other.dynamicType != null)
				return false;
		}
		else if(!dynamicType.equals(other.dynamicType))
			return false;
		if(imageURL == null)
		{
			if(other.imageURL != null)
				return false;
		}
		else if(!imageURL.equals(other.imageURL))
			return false;
		if(parameterName == null)
		{
			if(other.parameterName != null)
				return false;
		}
		else if(!parameterName.equals(other.parameterName))
			return false;
		if(parent == null)
		{
			if(other.parent != null)
				return false;
		}
		else if(!parent.getId().equals(other.parent.getId()))
			return false;
		if(position != other.position)
			return false;
		if(quickLaunchEnabled != other.quickLaunchEnabled)
			return false;
		if(titleKey == null)
		{
			if(other.titleKey != null)
				return false;
		}
		else if(!titleKey.equals(other.titleKey))
			return false;
		if(type == null)
		{
			if(other.type != null)
				return false;
		}
		else if(!type.equals(other.type))
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
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + (advancedItem ? 1231 : 1237);
		result = prime * result + (disabled ? 1231 : 1237);
		result = prime * result + ((dynamicType == null) ? 0 : dynamicType.hashCode());
		result = prime * result + ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + ((parameterName == null) ? 0 : parameterName.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.getId().hashCode());
		result = prime * result + position;
		result = prime * result + (quickLaunchEnabled ? 1231 : 1237);
		result = prime * result + ((titleKey == null) ? 0 : titleKey.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		builder.append("id", id).append("version", version).append("action", (action == null ? null : action.getAction())).append("advancedItem",
				advancedItem).append("disabled", disabled).append("dynamicType", dynamicType).append("dynamicSubType", dynamicSubType).append("imageURL", imageURL).append("parameterName",
				parameterName).append("parameterValueString", parameterValueString).append("parameterValueLong", parameterValueLong).append("parent",
				(parent == null ? null : parent.getId())).append("position", position).append("quickLaunchEnabled", quickLaunchEnabled).append(
				"titleKey", titleKey).append("type", type);
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public MenuItem clone()
	{
		MenuItem tmpItem = null;
		try
		{
			tmpItem = (MenuItem) super.clone();
		}
		catch(CloneNotSupportedException e)
		{
		}
		tmpItem.action = action;
		tmpItem.advancedItem = advancedItem;
		tmpItem.children = children;
		tmpItem.dynamicType = dynamicType;
		tmpItem.dynamicSubType = dynamicSubType;
		tmpItem.id = id;
		tmpItem.imageURL = imageURL;
		tmpItem.parameterName = parameterName;
		tmpItem.parameterValueString = parameterValueString;
		tmpItem.parameterValueLong = parameterValueLong;
		tmpItem.parent = parent;
		tmpItem.position = position;
		tmpItem.quickLaunchEnabled = quickLaunchEnabled;
		tmpItem.titleKey = titleKey;
		tmpItem.type = type;
		tmpItem.version = version;
		return tmpItem;
	}
}

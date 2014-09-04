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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.syncnapsis.data.model.base.BaseObject;

/**
 * Model-Klasse "Kontaktgruppe"
 * Kontaktgruppen dienen der Gruppierung von Kontakten eines Imperiums oder
 * einer Allianz zu anderen Imperien bzw. Allianzen. So ist f�r den Spieler
 * schneller ersichtlich, welche Beziehungen "Freunde" oder "Feinde" sind.
 * Desweiteren k�nnen die Kontaktgruppen f�r andere Spieler sichtbar gemacht
 * oder verborgen werden, wobei diese dann nur den Namen der Kontaktgruppe und
 * die dar�ber zugeordneten Imperium, nicht aber die Details der Abkommen sehen
 * k�nnen. Die Kontaktzuordnungen innerhalb einer Kontaktgruppe m�ssen nicht die
 * gleichen Berechtigungen f�r die gegenseitigen Beziehungen enthalten. Jede der
 * Kontaktzuordnung definiert ihre eigenen rechtlichen Grundlagen. Jedem
 * Imperium werden bei der Gr�ndung automatisch standard Kontakgruppen wie
 * ("Freundschaft", "Nicht-Angriffspakt" oder "Krieg" etc.) zugeordnet.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "contactgroup")
public class ContactGroup extends BaseObject<Long>
{
	/**
	 * Name der Kontaktgruppe. Es sind hier auch Platzhalter m�glich um einen
	 * sprachabh�ngigen Namen verwenden zu k�nnen.
	 */
	private String			name;
	/**
	 * Eine optionale Beschreibung der Kontaktgruppe.
	 */
	private String			description;

	/**
	 * Sind die Kontaktzuordnungen dieser Kontaktgruppe per default sichtbar f�r
	 * andere Spieler? Dieses Attribut kann von jeder Kontaktgruppe einzeln oder
	 * gesondert f�r bestimmte Allianzen und Imperien �berschrieben werden.
	 */
	private boolean			defaultVisible;

	/**
	 * Die Allianz, der diese Kontaktgruppe zugeordnet ist.
	 * (Es kann immer nur entweder eine Allianz oder ein Imperium gesetzt sein!)
	 */
	private Alliance		ownerAlliance;
	/**
	 * Das Imperium, dem diese Kontaktgruppe zugeordnet ist.
	 * (Es kann immer nur entweder eine Allianz oder ein Imperium gesetzt sein!)
	 */
	private Empire			ownerEmpire;

	/**
	 * Eine tempor�r Liste aller Allianzen, die �ber Kontakte dieser
	 * Kontaktgruppe zugeordnet sind.
	 */
	private List<Alliance>	allianceContacts;

	/**
	 * Eine tempor�r Liste aller Imperien, die �ber Kontakte dieser
	 * Kontaktgruppe zugeordnet sind.
	 */
	private List<Empire>	empireContacts;

	/**
	 * Leerer Standard Constructor
	 */
	public ContactGroup()
	{
	}

	/**
	 * Name der Kontaktgruppe. Es sind hier auch Platzhalter m�glich um einen
	 * sprachabh�ngigen Namen verwenden zu k�nnen.
	 * 
	 * @return name
	 */
	@Column(nullable = false, length = LENGTH_NAME_LONG)
	public String getName()
	{
		return name;
	}

	/**
	 * Eine optionale Beschreibung der Kontaktgruppe.
	 * 
	 * @return description
	 */
	@Column(nullable = true, length = LENGTH_DESCRIPTION)
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sind die Kontaktzuordnungen dieser Kontaktgruppe per default sichtbar f�r
	 * andere Spieler? Dieses Attribut kann von jeder Kontaktgruppe einzeln oder
	 * gesondert f�r bestimmte Allianzen und Imperien �berschrieben werden.
	 * 
	 * @return defaultVisible
	 */
	@Column(nullable = false)
	public boolean isDefaultVisible()
	{
		return defaultVisible;
	}

	/**
	 * Die Allianz, der diese Kontaktgruppe zugeordnet ist.
	 * (Es kann immer nur entweder eine Allianz oder ein Imperium gesetzt sein!)
	 * 
	 * @return ownerAlliance
	 */
	@ManyToOne
	@JoinColumn(name = "fkOwnerAlliance", nullable = true)
	public Alliance getOwnerAlliance()
	{
		return ownerAlliance;
	}

	/**
	 * Das Imperium, dem diese Kontaktgruppe zugeordnet ist.
	 * (Es kann immer nur entweder eine Allianz oder ein Imperium gesetzt sein!)
	 * 
	 * @return ownerEmpire
	 */
	@ManyToOne
	@JoinColumn(name = "fkOwnerEmpire", nullable = true)
	public Empire getOwnerEmpire()
	{
		return ownerEmpire;
	}

	/**
	 * Eine tempor�r Liste aller Allianzen, die �ber Kontakte dieser
	 * Kontaktgruppe zugeordnet sind.
	 * 
	 * @return allianceContacts
	 */
	@Transient
	public List<Alliance> getAllianceContacts()
	{
		return allianceContacts;
	}

	/**
	 * Eine tempor�r Liste aller Imperien, die �ber Kontakte dieser
	 * Kontaktgruppe zugeordnet sind.
	 * 
	 * @return empireContacts
	 */
	@Transient
	public List<Empire> getEmpireContacts()
	{
		return empireContacts;
	}

	/**
	 * Name der Kontaktgruppe. Es sind hier auch Platzhalter m�glich um einen
	 * sprachabh�ngigen Namen verwenden zu k�nnen.
	 * 
	 * @param name - der Name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Eine optionale Beschreibung der Kontaktgruppe.
	 * 
	 * @param description - die Beschreibung
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Sind die Kontaktzuordnungen dieser Kontaktgruppe per default sichtbar f�r
	 * andere Spieler? Dieses Attribut kann von jeder Kontaktgruppe einzeln oder
	 * gesondert f�r bestimmte Allianzen und Imperien �berschrieben werden.
	 * 
	 * @param defaultVisible - true oder false
	 */
	public void setDefaultVisible(boolean defaultVisible)
	{
		this.defaultVisible = defaultVisible;
	}

	/**
	 * Die Allianz, der diese Kontaktgruppe zugeordnet ist.
	 * (Es kann immer nur entweder eine Allianz oder ein Imperium gesetzt sein!)
	 * 
	 * @param ownerAlliance - die Allianz
	 */
	public void setOwnerAlliance(Alliance ownerAlliance)
	{
		this.ownerAlliance = ownerAlliance;
	}

	/**
	 * Das Imperium, dem diese Kontaktgruppe zugeordnet ist.
	 * (Es kann immer nur entweder eine Allianz oder ein Imperium gesetzt sein!)
	 * 
	 * @param ownerEmpire - das Imperium
	 */
	public void setOwnerEmpire(Empire ownerEmpire)
	{
		this.ownerEmpire = ownerEmpire;
	}

	/**
	 * Eine tempor�r Liste aller Allianzen, die �ber Kontakte dieser
	 * Kontaktgruppe zugeordnet sind.
	 * 
	 * @param allianceContacts - die Liste
	 */
	public void setAllianceContacts(List<Alliance> allianceContacts)
	{
		this.allianceContacts = allianceContacts;
	}

	/**
	 * Eine tempor�r Liste aller Imperien, die �ber Kontakte dieser
	 * Kontaktgruppe zugeordnet sind.
	 * 
	 * @param empireContacts - die Liste
	 */
	public void setEmpireContacts(List<Empire> empireContacts)
	{
		this.empireContacts = empireContacts;
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
		result = prime * result + (defaultVisible ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ownerAlliance == null) ? 0 : ownerAlliance.getId().hashCode());
		result = prime * result + ((ownerEmpire == null) ? 0 : ownerEmpire.getId().hashCode());
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
		ContactGroup other = (ContactGroup) obj;
		if(defaultVisible != other.defaultVisible)
			return false;
		if(description == null)
		{
			if(other.description != null)
				return false;
		}
		else if(!description.equals(other.description))
			return false;
		if(name == null)
		{
			if(other.name != null)
				return false;
		}
		else if(!name.equals(other.name))
			return false;
		if(ownerAlliance == null)
		{
			if(other.ownerAlliance != null)
				return false;
		}
		else if(!ownerAlliance.getId().equals(other.ownerAlliance.getId()))
			return false;
		if(ownerEmpire == null)
		{
			if(other.ownerEmpire != null)
				return false;
		}
		else if(!ownerEmpire.getId().equals(other.ownerEmpire.getId()))
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
		builder.append("defaultVisible", defaultVisible).append("description", description).append("name", name).append("ownerAlliance",
				ownerAlliance.getId()).append("ownerEmpire", ownerEmpire.getId()).append("id", id).append("version", version);
		return builder.toString();
	}
}

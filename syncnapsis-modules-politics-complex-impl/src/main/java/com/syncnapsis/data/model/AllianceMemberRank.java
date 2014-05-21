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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.syncnapsis.data.model.base.BaseObject;

/**
 * Model-Klasse "Allianz-Rang"
 * Diese Klasse definiert Posten bzw. Ränge innerhalb einer Allianz. Über diese
 * Ränge sind Imperien der Allianz zugeordnet. Der Spieler, dem das zugeordnete
 * Imperium gehört bekleidet diesen Rang und verfügt über die dem Rang
 * zugeordneten Rechte für die Verwaltung bzw. Führung der Allianz. Die Ränge
 * einer Allianz bilden eine hierarchische Struktur ab, in dem jedem Rang ein
 * übergeordneter Rang zugewiesen werden kann. So ist gewährleistet, dass
 * mögliche Beförderungen nur innerhalb von Teilhierarchien erlaubt sind und
 * eine Führungstruktur für die Allianz aufgebaut werden kann.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "alliancememberrank")
public class AllianceMemberRank extends BaseObject<Long>
{
	/**
	 * Der Name des Ranges. Es sind hier auch Platzhalter möglich um einen
	 * sprachabhängigen Namen verwenden zu können.
	 */
	private String						rankName;
	/**
	 * Ist dieser Rang für Nicht-Mitglieder sichtbar?
	 */
	private boolean						visible;
	/**
	 * Das Stimmgewicht der Mitglieder dieses Ranges.
	 */
	private int							voteWeight;

	/**
	 * Die Allianz, zu der dieser Rang gehört.
	 */
	private Alliance					alliance;
	/**
	 * Die Verwaltungs-Rechte, die diesem Rang zugeordnet sind und Kontakt-Rechte, die allen
	 * Imperien untereinander erteilt werden, die diesem Rang zugeordnet sind.
	 */
	private AuthoritiesGenericImpl		authorities;

	/**
	 * Eine Liste aller Imperien, die diesem Rang zugeordnet sind.
	 */
	private List<Empire>				empires;

	/**
	 * Ein optionaler übergeordneter Rang.
	 */
	private AllianceMemberRank			parent;
	/**
	 * Die Liste aller Ränge, denen dieser Rang als übergeordneter Rang
	 * zugeordnet ist.
	 */
	private List<AllianceMemberRank>	children;

	/**
	 * Leerer Standard Constructor
	 */
	public AllianceMemberRank()
	{
	}

	/**
	 * Der Name des Ranges. Es sind hier auch Platzhalter möglich um einen
	 * sprachabhängigen Namen verwenden zu können.
	 * 
	 * @return rankName
	 */
	@Column(nullable = false, length = LENGTH_NAME_LONG)
	public String getRankName()
	{
		return rankName;
	}

	/**
	 * Ist dieser Rang für Nicht-Mitglieder sichtbar?
	 * 
	 * @return visible
	 */
	@Column(nullable = false)
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * Das Stimmgewicht der Mitglieder dieses Ranges.
	 * 
	 * @return voteWeight
	 */
	@Column(nullable = false)
	public int getVoteWeight()
	{
		return voteWeight;
	}

	/**
	 * Die Allianz, zu der dieser Rang gehört.
	 * 
	 * @return alliance
	 */
	@ManyToOne
	@JoinColumn(name = "fkAlliance", nullable = true)
	public Alliance getAlliance()
	{
		return alliance;
	}

	/**
	 * Die Verwaltungs-Rechte, die diesem Rang zugeordnet sind und Kontakt-Rechte, die allen
	 * Imperien untereinander erteilt werden, die diesem Rang zugeordnet sind.
	 * 
	 * @return authorities
	 */
	@ManyToOne
	@JoinColumn(name = "fkAuthorities", nullable = true)
	public AuthoritiesGenericImpl getAuthorities()
	{
		return authorities;
	}

	/**
	 * Eine Liste aller Imperien, die diesem Rang zugeordnet sind.
	 * 
	 * @return empires
	 */
	@ManyToMany
	@JoinTable(name = "alliancemember", joinColumns = { @JoinColumn(name = "fkAllianceMemberRank") }, inverseJoinColumns = { @JoinColumn(name = "fkEmpire") })
	public List<Empire> getEmpires()
	{
		return empires;
	}

	/**
	 * Ein optionaler übergeordneter Rang.
	 * 
	 * @return parent
	 */
	@ManyToOne
	@JoinTable(name = "alliancememberrankparent", joinColumns = { @JoinColumn(name = "fkAllianceMemberRank") }, inverseJoinColumns = { @JoinColumn(name = "fkParentAllianceMemberRank") })
	public AllianceMemberRank getParent()
	{
		return parent;
	}

	/**
	 * Die Liste aller Ränge, denen dieser Rang als übergeordneter Rang
	 * zugeordnet ist.
	 * 
	 * @return children
	 */
	@OneToMany(mappedBy = "parent")
	public List<AllianceMemberRank> getChildren()
	{
		return children;
	}

	/**
	 * Der Name des Ranges. Es sind hier auch Platzhalter möglich um einen
	 * sprachabhängigen Namen verwenden zu können.
	 * 
	 * @param rankName - der Name
	 */
	public void setRankName(String rankName)
	{
		this.rankName = rankName;
	}

	/**
	 * Ist dieser Rang für Nicht-Mitglieder sichtbar?
	 * 
	 * @param visible - true oder false
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	/**
	 * Das Stimmgewicht der Mitglieder dieses Ranges.
	 * 
	 * @param voteWeight - das Stimmgewicht
	 */
	public void setVoteWeight(int voteWeight)
	{
		this.voteWeight = voteWeight;
	}

	/**
	 * Die Allianz, zu der dieser Rang gehört.
	 * 
	 * @param alliance - die Allianz
	 */
	public void setAlliance(Alliance alliance)
	{
		this.alliance = alliance;
	}

	/**
	 * Die Verwaltungs-Rechte, die diesem Rang zugeordnet sind und Kontakt-Rechte, die allen
	 * Imperien untereinander erteilt werden, die diesem Rang zugeordnet sind.
	 * 
	 * @param authorities - die Rechte
	 */
	public void setAuthorities(AuthoritiesGenericImpl authorities)
	{
		this.authorities = authorities;
	}

	/**
	 * Eine Liste aller Imperien, die diesem Rang zugeordnet sind.
	 * 
	 * @param empires - die Liste
	 */
	public void setEmpires(List<Empire> empires)
	{
		this.empires = empires;
	}

	/**
	 * Ein optionaler übergeordneter Rang.
	 * 
	 * @param parent - der Rang
	 */
	public void setParent(AllianceMemberRank parent)
	{
		this.parent = parent;
	}

	/**
	 * Die Liste aller Ränge, denen dieser Rang als übergeordneter Rang
	 * zugeordnet ist.
	 * 
	 * @param children - die Liste
	 */
	public void setChildren(List<AllianceMemberRank> children)
	{
		this.children = children;
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
		if(!(obj instanceof AllianceMemberRank))
			return false;
		AllianceMemberRank other = (AllianceMemberRank) obj;
		if(alliance == null)
		{
			if(other.alliance != null)
				return false;
		}
		else if(!alliance.getId().equals(other.alliance.getId()))
			return false;
		if(authorities == null)
		{
			if(other.authorities != null)
				return false;
		}
		else if(!authorities.getId().equals(other.authorities.getId()))
			return false;
		if(parent == null)
		{
			if(other.parent != null)
				return false;
		}
		else if(!parent.getId().equals(other.parent.getId()))
			return false;
		if(rankName == null)
		{
			if(other.rankName != null)
				return false;
		}
		else if(!rankName.equals(other.rankName))
			return false;
		if(visible != other.visible)
			return false;
		return true;
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
		result = prime * result + ((alliance == null) ? 0 : alliance.getId().hashCode());
		result = prime * result + ((authorities == null) ? 0 : authorities.getId().hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.getId().hashCode());
		result = prime * result + ((rankName == null) ? 0 : rankName.hashCode());
		result = prime * result + (visible ? 1231 : 1237);
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
		builder.append("id", id).append("version", version).append("rankName", rankName).append("visible", visible).append("alliance",
				alliance.getId()).append("authorities", authorities.getId()).append("parent", parent.getId());
		return builder.toString();
	}
}

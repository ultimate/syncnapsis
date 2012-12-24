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

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.syncnapsis.data.model.base.Institution;

/**
 * Model-Klasse "Allianz"
 * Allianzen sind Bündnisse verschieder Imperien. Imperien können Mitglieder in
 * beliebig vielen Allianzen sein. Für Allianzen gibt es die gleichen
 * diplomatischen Möglichkeiten, wie für Imperien. Imperien einer Allianz teilen
 * in Abhängigkeit ihres Ranges z.B. gemeinsame Sichtbarkeiten oder Armeen.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "alliance")
public class Alliance extends Institution<Long>
{	
	/**
	 * Liste aller Allianz-Ränge, über die der Allianz Mitglieder zugeordnet
	 * werden.
	 */
	private List<AllianceMemberRank>	allianceMemberRanks;

	/**
	 * Leerer Standard Constructor
	 */
	public Alliance()
	{
	}

	/**
	 * Liste aller Allianz-Ränge, über die der Allianz Mitglieder zugeordnet
	 * werden.
	 * 
	 * @return allianceMemberRanks
	 */
	@OneToMany(mappedBy = "alliance")
	public List<AllianceMemberRank> getAllianceMemberRanks()
	{
		return allianceMemberRanks;
	}

	/**
	 * Liste aller Allianz-Ränge, über die der Allianz Mitglieder zugeordnet
	 * werden.
	 * 
	 * @param allianceMemberRanks - die Liste der Allianz-Ränge
	 */
	public void setAllianceMemberRanks(List<AllianceMemberRank> allianceMemberRanks)
	{
		this.allianceMemberRanks = allianceMemberRanks;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Institution#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Institution#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("version", version).append("activated", activated).append("shortName", shortName)
				.append("fullName", fullName).append("description", description).append("imageURL", imageURL);
		return builder.toString();
	}
}

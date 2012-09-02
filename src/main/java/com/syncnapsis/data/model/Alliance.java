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

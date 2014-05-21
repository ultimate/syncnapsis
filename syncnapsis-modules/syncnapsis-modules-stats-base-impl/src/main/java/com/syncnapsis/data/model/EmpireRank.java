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

import com.syncnapsis.data.model.annotations.RankCriterion;
import com.syncnapsis.data.model.base.Rank;

/**
 * Rang-Objekt für die Bewertung von Imperien auf Basis von GenericRank.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "empirerank")
public class EmpireRank extends Rank<Empire>
{
	// TODO anzahl kolonien und anzahl blöcke
	
	/**
	 * Die Punktbewertung der wirtschaftlichen Stärke des Imperiums
	 */
	@RankCriterion
	private int economy;

	/**
	 * Die Punktbewertung der militärischen Stärke des Imperiums
	 */
	@RankCriterion
	private int military;

	/**
	 * Die Punktbewertung der wissenschaftlichen Stärke des Imperiums
	 */
	@RankCriterion
	private int science;

	/**
	 * Die Gesamtpunkte des Imperiums
	 */
	@RankCriterion(primary = true)
	private int total;

	/**
	 * Die Punktbewertung der wirtschaftlichen Stärke des Imperiums
	 * 
	 * @return die Wirtschaftspunkte
	 */
	@Column(nullable = false)
	public int getEconomy()
	{
		return economy;
	}

	/**
	 * Die Punktbewertung der militärischen Stärke des Imperiums
	 * 
	 * @return die Militärpunkte
	 */
	@Column(nullable = false)
	public int getMilitary()
	{
		return military;
	}

	/**
	 * Die Punktbewertung der wissenschaftlichen Stärke des Imperiums
	 * 
	 * @return die Wissenschaftspunkte
	 */
	@Column(nullable = false)
	public int getScience()
	{
		return science;
	}

	/**
	 * Die Gesamtpunkte des Imperiums
	 * 
	 * @return die Gesamtpunkte
	 */
	@Column(nullable = false)
	public int getTotal()
	{
		return total;
	}

	/**
	 * Die Punktbewertung der wirtschaftlichen Stärke des Imperiums
	 * 
	 * @param economy - die Wirtschaftspunkte
	 */
	public void setEconomy(int economy)
	{
		this.economy = economy;
	}

	/**
	 * Die Punktbewertung der militärischen Stärke des Imperiums
	 * 
	 * @param military - die Militärpunkte
	 */
	public void setMilitary(int military)
	{
		this.military = military;
	}

	/**
	 * Die Punktbewertung der wissenschaftlichen Stärke des Imperiums
	 * 
	 * @param science - die Wissenschaftspunkte
	 */
	public void setScience(int science)
	{
		this.science = science;
	}

	/**
	 * Die Gesamtpunkte des Imperiums
	 * 
	 * @param total - die Gesamtpunkte
	 */
	public void setTotal(int total)
	{
		this.total = total;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Rank#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + economy;
		result = prime * result + military;
		result = prime * result + science;
		result = prime * result + total;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Rank#equals(java.lang.Object)
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
		EmpireRank other = (EmpireRank) obj;
		if(economy != other.economy)
			return false;
		if(military != other.military)
			return false;
		if(science != other.science)
			return false;
		if(total != other.total)
			return false;
		return true;
	}
}

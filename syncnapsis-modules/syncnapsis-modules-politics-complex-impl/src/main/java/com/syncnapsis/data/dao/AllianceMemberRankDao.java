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
package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.AllianceMemberRank;

/**
 * Dao-Interface für den Zugriff auf AllianceMemberRank
 * 
 * @author ultimate
 */
public interface AllianceMemberRankDao extends GenericDao<AllianceMemberRank, Long>
{
	/**
	 * Lade alle Allianzränge zu einem Imperium.<br/>
	 * Im gegensatz zu AllianceDao.getByEmpire(...) findet hier keine Filterung
	 * statt und es werden auch mehrfache Ränge zu einer Allianz zurückgegeben.
	 * 
	 * @param empireId - die ID des Imperiums
	 * @return die Liste der Allianz-Ränge
	 */
	public List<AllianceMemberRank> getByEmpire(Long empireId);
}

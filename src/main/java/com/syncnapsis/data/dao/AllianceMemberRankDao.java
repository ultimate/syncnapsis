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

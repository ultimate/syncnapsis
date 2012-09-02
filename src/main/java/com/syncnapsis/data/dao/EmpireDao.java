package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.Empire;

/**
 * Dao-Interface für den Zugriff auf Empire
 * 
 * @author ultimate
 */
public interface EmpireDao extends GenericNameDao<Empire, Long>
{
	/**
	 * Lade eine Liste aller Imperien zu einem Spieler.
	 * 
	 * @param playerId - die ID des Spielers
	 * @return die Liste der Imperien
	 */
	public List<Empire> getByPlayer(Long playerId);
}

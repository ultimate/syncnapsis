package com.syncnapsis.data.service;

import java.util.List;

import com.syncnapsis.data.model.Empire;

/**
 * Manager-Interface für den Zugriff auf Empire.
 * 
 * @author ultimate
 */
public interface EmpireManager extends GenericNameManager<Empire, Long>
{
	/**
	 * Lade eine Liste aller Imperien zu einem Spieler.
	 * 
	 * @param userId - die ID des Spielers
	 * @return die Liste der Imperien
	 */
	public List<Empire> getByPlayer(Long playerId);
}

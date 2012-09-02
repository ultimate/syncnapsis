package com.syncnapsis.data.service;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;

/**
 * Manager-Interface für den Zugriff auf Rank.
 * 
 * @author ultimate
 * @param <R> - die Rank-Klasse
 * @param <T> - die Klasse auf die sich die Bewertung bezieht
 * @param <PK> - der Primary-Key-Typ des vom Rank aus referenzierten Objekts
 */
public interface RankManager<R extends Rank<T>, T extends BaseObject<PK>, PK extends Serializable> extends GenericManager<R, Long>
{
	/**
	 * Gibt den aktuellen Rang zu einem Objekt zurück
	 * 
	 * @param entityId - die ID des Objekts
	 * @return der Rang
	 */
	public R getByEntity(PK entityId);

	/**
	 * Gibt die Historie der Ränge zu einem Objekt zurück
	 * 
	 * @param entityId - die ID des Objekts
	 * @return der Historie der Ränge
	 */
	public List<R> getHistoryByEntity(PK entityId);

	/**
	 * Gibt alle aktuellen Ränge sortiert nach dem Bewertungskriterium zurück.
	 * 
	 * @param criterion - das Bewertungskriterium
	 * @return die Liste der Ränge
	 */
	public List<R> getByCriterion(String criterion);

	/**
	 * Gibt alle aktuellen Ränge sortiert nach dem primären Bewertungskriterium der Default-Kategorie zurück.
	 * 
	 * @return die Liste der Ränge
	 */
	public List<R> getByDefaultPrimaryCriterion();
}
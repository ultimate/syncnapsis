package com.syncnapsis.data.dao;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.model.base.BaseObject;

/**
 * Generische DAO (Data Access Object), die erweiterte Operationen nach Name zur Verfügung stellt.
 * 
 * @param <T> die Model-Klasse
 * @param <PK> die Klasse des Primärschlüssels
 * @author ultimate
 */
public interface GenericNameDao<T extends BaseObject<PK>, PK extends Serializable> extends GenericDao<T, PK>
{
	/**
	 * Lade eine Liste aller Einträge, sortiert nach Name
	 * 
	 * @param returnOnlyActivated - sollen nur aktivierte Einträge zurückgegeben
	 *            werden?
	 * @return die Liste aller Einträge
	 */
	public List<T> getOrderedByName(boolean returnOnlyActivated);
	
	/**
	 * Lade eine Liste aller Einträge, sortiert nach Name mit eingeschränktem Präfix des Namens
	 * 
	 * @param prefix - die Anfangsbuchstaben des Namens
	 * @param nRows - die maximale Anzahl an zurückzugebenen Ergebnisses (negativ = alle)
	 * @param returnOnlyActivated - sollen nur aktivierte Einträge zurückgegeben
	 *            werden?
	 * @return die Liste aller Einträge
	 */
	public List<T> getByPrefix(String prefix, int nRows, boolean returnOnlyActivated);
	
	/**
	 * Lade einen Eintrag anhand seines Namens
	 * 
	 * @param name - der Name
	 * @return de Eintrag
	 */
	public T getByName(String name);

	/**
	 * Prüft, ob ein gewünschter Name für diese Klasse noch verfügbar ist.
	 * 
	 * @param name - der zu prüfende Name
	 * @return true oder false
	 */
	public boolean isNameAvailable(String name);
}
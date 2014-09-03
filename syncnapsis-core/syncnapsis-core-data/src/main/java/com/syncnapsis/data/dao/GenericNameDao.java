/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.model.base.Identifiable;

/**
 * Generische DAO (Data Access Object), die erweiterte Operationen nach Name zur Verf�gung stellt.
 * 
 * @param <T> die Model-Klasse
 * @param <PK> die Klasse des Prim�rschl�ssels
 * @author ultimate
 */
public interface GenericNameDao<T extends Identifiable<PK>, PK extends Serializable> extends GenericDao<T, PK>
{
	/**
	 * Lade eine Liste aller Eintr�ge, sortiert nach Name
	 * 
	 * @param returnOnlyActivated - sollen nur aktivierte Eintr�ge zur�ckgegeben
	 *            werden?
	 * @return die Liste aller Eintr�ge
	 */
	public List<T> getOrderedByName(boolean returnOnlyActivated);
	
	/**
	 * Lade eine Liste aller Eintr�ge, sortiert nach Name mit eingeschr�nktem Pr�fix des Namens
	 * 
	 * @param prefix - die Anfangsbuchstaben des Namens
	 * @param nRows - die maximale Anzahl an zur�ckzugebenen Ergebnisses (negativ = alle)
	 * @param returnOnlyActivated - sollen nur aktivierte Eintr�ge zur�ckgegeben
	 *            werden?
	 * @return die Liste aller Eintr�ge
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
	 * Pr�ft, ob ein gew�nschter Name f�r diese Klasse noch verf�gbar ist.
	 * 
	 * @param name - der zu pr�fende Name
	 * @return true oder false
	 */
	public boolean isNameAvailable(String name);
}
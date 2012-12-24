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

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.model.base.BaseObject;

/**
 * Generische DAO (Data Access Object), die die Standard CRUD-Operationen
 * (Create, Retrieve, Update, Delete) generisch für alle Model-Klassen zur
 * Verfügung stellt.
 * 
 * @param <T> die Model-Klasse
 * @param <PK> die Klasse des Primärschlüssels
 * @author ultimate
 */
public interface GenericDao<T extends BaseObject<PK>, PK extends Serializable>
// extends UniversalDao
{
	/**
	 * Generische Methode um alle Objekte eines Typs zu laden (nur aktivierte).
	 * 
	 * @return eine Liste aller Objekte der Model-Klasse
	 */
	public List<T> getAll();

	/**
	 * Generische Methode um alle Objekte eines Typs zu laden. Es kann gewählt
	 * werden, ob nur aktivierte oder aktiviert und deaktiviert Objekte geladen
	 * werden sollen.
	 * 
	 * @return eine Liste aller Objekte der Model-Klasse
	 */
	public List<T> getAll(boolean returnOnlyActivated);

	/**
	 * Generische Methode um ein Objekt anhand seines Promärschlüssels zu laden.
	 * 
	 * @param id - der Primärschlüssel des Objektes
	 * @return das Objekt
	 */
	public T get(PK id);

	/**
	 * Generische Methode um anhand einer Liste von Primärschlüsseln die
	 * dazugehörigen Objekte zu laden. Die dazugehörigen Objekte befinden sich
	 * in der Ergebnisliste an der gleichen Stelle, wie ihr Primärschlüssel in
	 * der übergeben Liste. Wird zu einem Primärschlüssel kein Objekt gefunden,
	 * so enthält die Liste an dieser Stelle null.
	 * 
	 * @param idList - die Liste der Primärschlüssel
	 * @return die Liste der Objekte der Model-Klasse zu den übergebenen
	 *         Primärschlüsseln
	 */
	public List<T> getByIdList(List<PK> idList);

	/**
	 * Prüft, ob ein Objekt dieser Modell-Klasse zu dem gegeben Primärschlüssel
	 * existiert.
	 * 
	 * @param id - der zu prüfende Primärschlüssel
	 * @return true oder false
	 */
	public boolean exists(PK id);

	/**
	 * Generische Methode um ein Objekt der Modell-Klasse zu speichern. Es
	 * können sowohl neue Objekte, als auch Veränderungen an existieren Objekten
	 * gespeichert werden. Nach dem Speichervorgang wird das geänderte Objekt
	 * zurückgegeben. Es enthält eine neue Versionsnummer und bei
	 * Neu-Speicherung den zugewiesenen Primärschlüssel.
	 * 
	 * @param object - das zu speichernde Objekt
	 * @return das gespeichert Objekt
	 */
	public T save(T object);

	/**
	 * Generische Methode um eine Objekt der Modell-Klasse zu löschen. Handelt
	 * es sich bei der Modell-Klasse um eine Subklasse von ActivatableInstance,
	 * so wird das Objekt deaktiviert anstatt gelöscht zu werden.
	 * 
	 * @param object - das zu löschende Objekt
	 * @return "deleted", wenn das Objekt gelöscht wurde, "deactivated", wenn es
	 *         deaktiviert wurde
	 */
	public String remove(T object);

	// /**
	// * May not by supported by GenericDao! Use {@link GenericDao#remove(BaseObject)} instead.
	// * @throws UnsupportedOperationException
	// */
	// @Override
	// public void delete(Object o);
}
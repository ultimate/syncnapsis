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
 * Generische DAO (Data Access Object), die die Standard CRUD-Operationen
 * (Create, Retrieve, Update, Delete) generisch f�r alle Model-Klassen zur
 * Verf�gung stellt.
 * 
 * @param <T> die Model-Klasse
 * @param <PK> die Klasse des Prim�rschl�ssels
 * @author ultimate
 */
public interface GenericDao<T extends Identifiable<PK>, PK extends Serializable>
// extends UniversalDao
{
	/**
	 * Generische Methode um alle Objekte eines Typs zu laden (nur aktivierte).
	 * 
	 * @return eine Liste aller Objekte der Model-Klasse
	 */
	public List<T> getAll();

	/**
	 * Generische Methode um alle Objekte eines Typs zu laden. Es kann gew�hlt
	 * werden, ob nur aktivierte oder aktiviert und deaktiviert Objekte geladen
	 * werden sollen.
	 * 
	 * @return eine Liste aller Objekte der Model-Klasse
	 */
	public List<T> getAll(boolean returnOnlyActivated);

	/**
	 * Generische Methode um ein Objekt anhand seines Prom�rschl�ssels zu laden.
	 * 
	 * @param id - der Prim�rschl�ssel des Objektes
	 * @return das Objekt
	 */
	public T get(PK id);

	/**
	 * Generische Methode um anhand einer Liste von Prim�rschl�sseln die
	 * dazugeh�rigen Objekte zu laden. Die dazugeh�rigen Objekte befinden sich
	 * in der Ergebnisliste an der gleichen Stelle, wie ihr Prim�rschl�ssel in
	 * der �bergeben Liste. Wird zu einem Prim�rschl�ssel kein Objekt gefunden,
	 * so enth�lt die Liste an dieser Stelle null.
	 * 
	 * @param idList - die Liste der Prim�rschl�ssel
	 * @return die Liste der Objekte der Model-Klasse zu den �bergebenen
	 *         Prim�rschl�sseln
	 */
	public List<T> getByIdList(List<PK> idList);

	/**
	 * Pr�ft, ob ein Objekt dieser Modell-Klasse zu dem gegeben Prim�rschl�ssel
	 * existiert.
	 * 
	 * @param id - der zu pr�fende Prim�rschl�ssel
	 * @return true oder false
	 */
	public boolean exists(PK id);

	/**
	 * Generische Methode um ein Objekt der Modell-Klasse zu speichern. Es
	 * k�nnen sowohl neue Objekte, als auch Ver�nderungen an existieren Objekten
	 * gespeichert werden. Nach dem Speichervorgang wird das ge�nderte Objekt
	 * zur�ckgegeben. Es enth�lt eine neue Versionsnummer und bei
	 * Neu-Speicherung den zugewiesenen Prim�rschl�ssel.
	 * 
	 * @param object - das zu speichernde Objekt
	 * @return das gespeichert Objekt
	 */
	public T save(T object);

	/**
	 * Generische Methode um eine Objekt der Modell-Klasse zu l�schen. Handelt
	 * es sich bei der Modell-Klasse um eine Subklasse von ActivatableInstance,
	 * so wird das Objekt deaktiviert anstatt gel�scht zu werden.
	 * 
	 * @param object - das zu l�schende Objekt
	 * @return "deleted", wenn das Objekt gel�scht wurde, "deactivated", wenn es
	 *         deaktiviert wurde
	 */
	public String remove(T object);

	/**
	 * True DELETE of the given entity if supported by the underlying DAO. For save usage ise
	 * {@link GenericDao#remove(BaseObject)} instead.
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void delete(T o);
}
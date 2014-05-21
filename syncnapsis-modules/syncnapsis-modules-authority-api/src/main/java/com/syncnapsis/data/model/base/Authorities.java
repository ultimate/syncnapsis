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
package com.syncnapsis.data.model.base;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Abstrakte Oberklasse für alle Rechte-Objekte.
 * Rechte können auf verschiedene Weise implementiert und somit strukturiert werden.
 * 
 * @author ultimate
 */
@MappedSuperclass
public abstract class Authorities extends BaseObject<Long>
{
	/**
	 * Schaltet ein Recht frei, sofern es für dieses Authorities-Objekt gültig ist
	 * 
	 * @param authorityName - das freizuschaltende Recht
	 * @return der vorherige Zustand: freigeschaltet oder nicht
	 */
	@Transient
	public abstract boolean grantAuthority(String authorityName);

	/**
	 * Entzieht ein Recht, sofern es für dieses Authorities-Objekt gültig ist
	 * 
	 * @param authorityName - das zu entziehende Recht
	 * @return der vorherige Zustand: freigeschaltet oder nicht
	 */
	@Transient
	public abstract boolean withdrawAuthority(String authorityName);

	/**
	 * Prüft ob ein bestimmtes Recht freigeschaltet ist oder nicht
	 * 
	 * @param authorityName - das zu prüfende Recht
	 * @return der Zustand: freigeschaltet oder nicht
	 */
	@Transient
	public abstract boolean isAuthorityGranted(String authorityName);

	/**
	 * Gibt die Liste der freigeschalteten Rechte zurück
	 * 
	 * @return die Liste
	 */
	@Transient
	public abstract List<String> getAuthorityNamesGranted();

	/**
	 * Gibt die Liste der nicht freigeschalteten Rechte zurück
	 * 
	 * @return die Liste
	 */
	@Transient
	public abstract List<String> getAuthorityNamesNotGranted();
}

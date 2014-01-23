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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.syncnapsis.security.AccessRule;
import com.syncnapsis.security.annotations.Accessible;

/**
 * Abstrakte Klasse als Basis für alle Instanzen, die nicht gelöscht, sondern
 * deaktiviert werden. Beim Löschen eines solchen Objektes wird dann
 * automatische setActivated(false) aufgerufen und das Objekt anschließend
 * deaktiviert gespeichert, anstatt dies endgültig zu löschen.
 * Deaktivierte Instanzen bleiben demnach in der Datenbank vorhanden. Dies ist
 * insbesondere für Fremschlüsselverknüpfungen hilfreich, oder wenn die Daten
 * für spätere Zwecke noch abrufbar sein sollen.
 * 
 * @author ultimate
 * @param <PK> - die Klasse für den Primärschlüssel
 */
@MappedSuperclass
public abstract class ActivatableInstance<PK extends Serializable> extends BaseObject<PK>
{
	/**
	 * Ist diese Instanz aktiviert?
	 */
	protected boolean	activated;

	/**
	 * Ist diese Instanz aktiviert?
	 * 
	 * @return activated
	 */
	@Column(nullable = false)
	@Accessible(by = AccessRule.NOBODY)
	public boolean isActivated()
	{
		return activated;
	}

	/**
	 * Ist diese Instanz aktiviert?
	 * 
	 * @param activated - true oder false
	 */
	@Accessible(by = AccessRule.NOBODY)
	public void setActivated(boolean activated)
	{
		this.activated = activated;
	}
}

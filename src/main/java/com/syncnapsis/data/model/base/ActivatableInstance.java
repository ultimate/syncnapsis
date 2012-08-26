package com.syncnapsis.data.model.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

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
	@Accessible(defaultAccessible = false)
	public boolean isActivated()
	{
		return activated;
	}

	/**
	 * Ist diese Instanz aktiviert?
	 * 
	 * @param activated - true oder false
	 */
	@Accessible(defaultAccessible = false)
	public void setActivated(boolean activated)
	{
		this.activated = activated;
	}
}

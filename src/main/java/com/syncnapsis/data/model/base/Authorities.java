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

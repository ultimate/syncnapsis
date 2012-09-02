package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.Alliance;

/**
 * Dao-Interface für den Zugriff auf Alliance
 * 
 * @author ultimate
 */
public interface AllianceDao extends GenericNameDao<Alliance, Long>
{
	/**
	 * Lade alle Allianzen zu einem Imperium.<br/>
	 * Da die Verknüpfung von Imperium und Allianz über Allianzränge geschieht,
	 * muss hier eine Filterung erfolgen, die verhindert, dass eine Allianz, zu
	 * der ein Imperium mehrere Ränge hat, doppelt zurück gegeben wird.
	 * 
	 * @param empireId - die ID des Imperiums
	 * @return eine Liste aller Allianzen des Imperiums
	 */
	public List<Alliance> getByEmpire(Long empireId);
}

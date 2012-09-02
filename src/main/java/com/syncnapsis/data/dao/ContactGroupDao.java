package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.ContactGroup;

/**
 * Dao-Interface für den Zugriff auf ContactGroup
 * 
 * @author ultimate
 */
public interface ContactGroupDao extends GenericDao<ContactGroup, Long>
{
	/**
	 * Gibt eine Liste der Kontaktgruppen zu einem Imperium zurück
	 * @param empireId - das Imperium
	 * @return die Liste
	 */
	public List<ContactGroup> getByEmpire(Long empireId);
	
	/**
	 * Gibt eine Liste der Kontaktgruppen zu einer Allianz zurück
	 * @param allianceId - das Allianz
	 * @return die Liste
	 */
	public List<ContactGroup> getByAlliance(Long allianceId);
}

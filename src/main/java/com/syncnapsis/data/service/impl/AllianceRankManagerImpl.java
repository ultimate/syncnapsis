package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.AllianceRankDao;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.AllianceRank;
import com.syncnapsis.data.service.AllianceRankManager;

/**
 * Manager-Implementierung für den Zugriff auf AllianceRank.
 * 
 * @author ultimate
 */
public class AllianceRankManagerImpl extends GenericRankManagerImpl<AllianceRank, Alliance, Long> implements AllianceRankManager
{
	/**
	 * AllianceRankDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private AllianceRankDao allianceRankDao;

	/**
	 * Standard-Constructor, der die DAO speichert
	 * 
	 * @param allianceRankDao - die Dao
	 */
	public AllianceRankManagerImpl(AllianceRankDao allianceRankDao)
	{
		super(allianceRankDao);
		this.allianceRankDao = allianceRankDao;
	}

}

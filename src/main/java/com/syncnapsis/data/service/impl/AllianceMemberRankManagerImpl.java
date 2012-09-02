package com.syncnapsis.data.service.impl;

import java.util.List;

import com.syncnapsis.data.dao.AllianceMemberRankDao;
import com.syncnapsis.data.model.AllianceMemberRank;
import com.syncnapsis.data.service.AllianceMemberRankManager;

/**
 * Manager-Implementierung für den Zugriff auf AllianceMemberRank.
 * 
 * @author ultimate
 */
public class AllianceMemberRankManagerImpl extends GenericManagerImpl<AllianceMemberRank, Long> implements AllianceMemberRankManager
{
	/**
	 * AllianceMemberRankDao für den Datenbankzugriff
	 */
	private AllianceMemberRankDao	allianceMemberRankDao;

	/**
	 * Standard-Constructor
	 * @param allianceMemberRankDao - AllianceMemberRankDao für den Datenbankzugriff
	 */
	public AllianceMemberRankManagerImpl(AllianceMemberRankDao allianceMemberRankDao)
	{
		super(allianceMemberRankDao);
		this.allianceMemberRankDao = allianceMemberRankDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.AllianceMemberRankManager#getByEmpire(java.lang.Long)
	 */
	@Override
	public List<AllianceMemberRank> getByEmpire(Long empireId)
	{
		return allianceMemberRankDao.getByEmpire(empireId);
	}
}

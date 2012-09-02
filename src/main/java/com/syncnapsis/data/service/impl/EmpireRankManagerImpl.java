package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.EmpireRankDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.EmpireRank;
import com.syncnapsis.data.service.EmpireRankManager;

/**
 * Manager-Implementierung für den Zugriff auf EmpireRank.
 * 
 * @author ultimate
 */
public class EmpireRankManagerImpl extends GenericRankManagerImpl<EmpireRank, Empire, Long> implements EmpireRankManager
{
	/**
	 * EmpireRankDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private EmpireRankDao empireRankDao;

	/**
	 * Standard-Constructor, der die DAO speichert
	 * 
	 * @param empireRankDao - die Dao
	 */
	public EmpireRankManagerImpl(EmpireRankDao empireRankDao)
	{
		super(empireRankDao);
		this.empireRankDao = empireRankDao;
	}

}

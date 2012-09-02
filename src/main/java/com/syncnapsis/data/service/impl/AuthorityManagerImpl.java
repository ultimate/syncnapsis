package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.AuthorityDao;
import com.syncnapsis.data.model.Authority;
import com.syncnapsis.data.service.AuthorityManager;

/**
 * Manager-Implementierung für den Zugriff auf Authority.
 * 
 * @author ultimate
 */
public class AuthorityManagerImpl extends GenericNameManagerImpl<Authority, Long> implements AuthorityManager
{
	/**
	 * AuthorityDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private AuthorityDao authorityDao;

	/**
	 * Standard-Constructor, der die DAO speichert
	 * 
	 * @param authorityDao - die Dao
	 */
	public AuthorityManagerImpl(AuthorityDao authorityDao)
	{
		super(authorityDao);
		this.authorityDao = authorityDao;
	}
}

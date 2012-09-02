package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.AuthorityGroupDao;
import com.syncnapsis.data.model.AuthorityGroup;
import com.syncnapsis.data.service.AuthorityGroupManager;

/**
 * Manager-Implementierung für den Zugriff auf AuthorityGroup.
 * 
 * @author ultimate
 */
public class AuthorityGroupManagerImpl extends GenericNameManagerImpl<AuthorityGroup, Long> implements AuthorityGroupManager
{
	/**
	 * AuthorityGroupDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private AuthorityGroupDao authorityGroupDao;

	/**
	 * Standard-Constructor, der die DAO speichert
	 * 
	 * @param authorityGroupDao - die Dao
	 */
	public AuthorityGroupManagerImpl(AuthorityGroupDao authorityGroupDao)
	{
		super(authorityGroupDao);
		this.authorityGroupDao = authorityGroupDao;
	}
}

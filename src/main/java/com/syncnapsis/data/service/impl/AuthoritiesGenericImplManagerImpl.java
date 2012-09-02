package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.AuthoritiesGenericImplDao;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.service.AuthoritiesGenericImplManager;

/**
 * Manager-Implementierung für den Zugriff auf AuthoritiesGenericImpl.
 * 
 * @author ultimate
 */
public class AuthoritiesGenericImplManagerImpl extends GenericManagerImpl<AuthoritiesGenericImpl, Long> implements AuthoritiesGenericImplManager
{
	/**
	 * AuthoritiesGenericImplDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private AuthoritiesGenericImplDao authoritiesDao;

	/**
	 * Standard-Constructor, der die DAO speichert
	 * 
	 * @param authoritiesDao - die Dao
	 */
	public AuthoritiesGenericImplManagerImpl(AuthoritiesGenericImplDao authoritiesDao)
	{
		super(authoritiesDao);
		this.authoritiesDao = authoritiesDao;
	}
}

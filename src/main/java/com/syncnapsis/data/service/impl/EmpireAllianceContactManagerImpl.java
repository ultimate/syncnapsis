package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.EmpireAllianceContactDao;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.model.contacts.EmpireAllianceContact;
import com.syncnapsis.data.service.EmpireAllianceContactManager;

/**
 * Manager-Implementierung für den Zugriff auf EmpireAllianceContact.
 * 
 * @author ultimate
 */
public class EmpireAllianceContactManagerImpl extends GenericManagerImpl<EmpireAllianceContact, Long> implements EmpireAllianceContactManager
{
	/**
	 * EmpireAllianceContactDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private EmpireAllianceContactDao	empireAllianceContactDao;

	/**
	 * Standard-Constructor
	 * @param empireAllianceContactDao - EmpireAllianceContactDao für den Datenbankzugriff
	 */
	public EmpireAllianceContactManagerImpl(EmpireAllianceContactDao empireAllianceContactDao)
	{
		super(empireAllianceContactDao);
		this.empireAllianceContactDao = empireAllianceContactDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactManager#getContact(java.lang.Long, java.lang.Long)
	 */
	@Override
	public EmpireAllianceContact getContact(Long contactId1, Long contactId2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactManager#getAuthoritiesForContact1(java.lang.Long, java.lang.Long)
	 */
	@Override
	public AuthoritiesGenericImpl getAuthoritiesForContact1(Long contactId1, Long contactId2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactManager#getAuthoritiesForContact2(java.lang.Long, java.lang.Long)
	 */
	@Override
	public AuthoritiesGenericImpl getAuthoritiesForContact2(Long contactId1, Long contactId2)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

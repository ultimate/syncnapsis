package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.AllianceAllianceContactDao;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.model.contacts.AllianceAllianceContact;
import com.syncnapsis.data.service.AllianceAllianceContactManager;

/**
 * Manager-Implementierung für den Zugriff auf AllianceAllianceContact.
 * 
 * @author ultimate
 */
public class AllianceAllianceContactManagerImpl extends GenericManagerImpl<AllianceAllianceContact, Long> implements AllianceAllianceContactManager
{
	/**
	 * AllianceAllianceContactDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private AllianceAllianceContactDao	allianceAllianceContactDao;

	/**
	 * Standard-Constructor
	 * @param allianceAllianceContactDao - AllianceAllianceContactDao für den Datenbankzugriff
	 */
	public AllianceAllianceContactManagerImpl(AllianceAllianceContactDao allianceAllianceContactDao)
	{
		super(allianceAllianceContactDao);
		this.allianceAllianceContactDao = allianceAllianceContactDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactManager#getContact(java.lang.Long, java.lang.Long)
	 */
	@Override
	public AllianceAllianceContact getContact(Long contactId1, Long contactId2)
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

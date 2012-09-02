package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.EmpireEmpireContactDao;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.model.contacts.EmpireEmpireContact;
import com.syncnapsis.data.service.EmpireEmpireContactManager;

/**
 * Manager-Implementierung für den Zugriff auf EmpireEmpireContact.
 * 
 * @author ultimate
 */
public class EmpireEmpireContactManagerImpl extends GenericManagerImpl<EmpireEmpireContact, Long> implements EmpireEmpireContactManager
{
	/**
	 * EmpireEmpireContactDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private EmpireEmpireContactDao	empireEmpireContactDao;

	/**
	 * Standard-Constructor
	 * @param empireEmpireContactDao - EmpireEmpireContactDao für den Datenbankzugriff
	 */
	public EmpireEmpireContactManagerImpl(EmpireEmpireContactDao empireEmpireContactDao)
	{
		super(empireEmpireContactDao);
		this.empireEmpireContactDao = empireEmpireContactDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactManager#getContact(java.lang.Long, java.lang.Long)
	 */
	@Override
	public EmpireEmpireContact getContact(Long contactId1, Long contactId2)
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

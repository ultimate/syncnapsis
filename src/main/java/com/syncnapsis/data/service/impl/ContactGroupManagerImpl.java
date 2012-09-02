package com.syncnapsis.data.service.impl;

import java.util.List;

import com.syncnapsis.data.dao.ContactGroupDao;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.service.ContactGroupManager;

/**
 * Manager-Implementierung für den Zugriff auf ContactGroup.
 * 
 * @author ultimate
 */
public class ContactGroupManagerImpl extends GenericManagerImpl<ContactGroup, Long> implements ContactGroupManager
{
	/**
	 * ContactGroupDao für den Datenbankzugriff
	 */
	private ContactGroupDao	contactGroupDao;

	/**
	 * Standard-Constructor
	 * @param contactGroupDao - ContactGroupDao für den Datenbankzugriff
	 */
	public ContactGroupManagerImpl(ContactGroupDao contactGroupDao)
	{
		super(contactGroupDao);
		this.contactGroupDao = contactGroupDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactGroupManager#getByAlliance(java.lang.Long)
	 */
	@Override
	public List<ContactGroup> getByAlliance(Long allianceId)
	{
		return contactGroupDao.getByAlliance(allianceId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ContactGroupManager#getByEmpire(java.lang.Long)
	 */
	@Override
	public List<ContactGroup> getByEmpire(Long empireId)
	{
		return contactGroupDao.getByEmpire(empireId);
	}
}

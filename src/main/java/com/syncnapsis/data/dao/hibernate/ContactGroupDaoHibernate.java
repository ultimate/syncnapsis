package com.syncnapsis.data.dao.hibernate;

import java.util.List;

import com.syncnapsis.data.dao.ContactGroupDao;
import com.syncnapsis.data.model.ContactGroup;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf ContactGroup
 * 
 * @author ultimate
 */
public class ContactGroupDaoHibernate extends GenericDaoHibernate<ContactGroup, Long> implements ContactGroupDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse ContactGroup
	 */
	public ContactGroupDaoHibernate()
	{
		super(ContactGroup.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.ContactGroupDao#getByAlliance(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContactGroup> getByAlliance(Long allianceId)
	{
		return createQuery("from ContactGroup where ownerAlliance.id = ?", allianceId).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.ContactGroupDao#getByEmpire(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContactGroup> getByEmpire(Long empireId)
	{
		return createQuery("from ContactGroup where ownerEmpire.id = ?", empireId).list();
	}
}

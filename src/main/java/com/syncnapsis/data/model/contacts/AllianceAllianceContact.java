package com.syncnapsis.data.model.contacts;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.base.ContactExtension;

/**
 * Klasse für die Kontaktzuordnung zwischen Allianz und Allianz.
 * Basiert auf ContactExtension<Alliance, Alliance>
 * 
 * @author ultimate
 */
@Entity
@Table(name = "alliancealliancecontact")
public class AllianceAllianceContact extends ContactExtension<Alliance, Alliance>
{
	/**
	 * Leerer Standard Constructor
	 */
	public AllianceAllianceContact()
	{
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Contact#getContactGroups()
	 */
	@ManyToMany
	@JoinTable(name = "alliancealliancecontactgroup",
			joinColumns = @JoinColumn(name = "fkAllianceAllianceContact"),
			inverseJoinColumns = @JoinColumn(name = "fkContactGroup"))	
	@Override
	public List<ContactGroup> getContactGroups()
	{
		return super.getContactGroups();
	}
}

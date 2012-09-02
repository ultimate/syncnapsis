package com.syncnapsis.data.model.contacts;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.base.ContactExtension;

/**
 * Klasse für die Kontaktzuordnung zwischen Imperium und Allianz.
 * Basiert auf ContactExtension<Empire, Alliance>
 * 
 * @author ultimate
 */
@Entity
@Table(name = "empirealliancecontact")
public class EmpireAllianceContact extends ContactExtension<Empire, Alliance>
{
	/**
	 * Leerer Standard Constructor
	 */
	public EmpireAllianceContact()
	{
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Contact#getContactGroups()
	 */
	@ManyToMany
	@JoinTable(name = "empirealliancecontactgroup",
			joinColumns = @JoinColumn(name = "fkEmpireAllianceContact"),
			inverseJoinColumns = @JoinColumn(name = "fkContactGroup"))	
	@Override
	public List<ContactGroup> getContactGroups()
	{
		return super.getContactGroups();
	}
}

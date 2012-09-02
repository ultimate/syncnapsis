package com.syncnapsis.data.model.contacts;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.base.ContactExtension;

/**
 * Klasse für die Kontaktzuordnung zwischen Imperium und Imperium.
 * Basiert auf ContactExtension<Empire, Empire>
 * 
 * @author ultimate
 */
@Entity
@Table(name = "empireempirecontact")
public class EmpireEmpireContact extends ContactExtension<Empire, Empire>
{
	/**
	 * Leerer Standard Constructor
	 */
	public EmpireEmpireContact()
	{
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Contact#getContactGroups()
	 */
	@ManyToMany
	@JoinTable(name = "empireempirecontactgroup",
			joinColumns = @JoinColumn(name = "fkEmpireEmpireContact"),
			inverseJoinColumns = @JoinColumn(name = "fkContactGroup"))	
	@Override
	public List<ContactGroup> getContactGroups()
	{
		return super.getContactGroups();
	}
}

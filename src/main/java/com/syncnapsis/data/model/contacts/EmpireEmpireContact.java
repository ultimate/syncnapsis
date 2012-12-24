/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
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

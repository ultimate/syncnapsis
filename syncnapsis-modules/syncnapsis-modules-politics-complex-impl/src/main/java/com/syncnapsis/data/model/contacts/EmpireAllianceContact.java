/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.base.ContactExtension;

/**
 * Klasse f�r die Kontaktzuordnung zwischen Imperium und Allianz.
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

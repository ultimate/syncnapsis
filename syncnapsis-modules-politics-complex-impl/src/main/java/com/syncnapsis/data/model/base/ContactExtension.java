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
package com.syncnapsis.data.model.base;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.syncnapsis.data.model.base.Contact;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;

/**
 * Erweiterung der abstrakten Klasse Contact für die Zuordnung zu ContactGroups
 * 
 * @author ultimate
 * @param <C1> - die Klasse für die eine Seite der Kontaktzuordnung
 * @param <C2> - die Klasse für die andere Seite der Kontaktzuordnung
 */
@MappedSuperclass
public abstract class ContactExtension<C1 extends BaseObject<?>, C2 extends BaseObject<?>> extends Contact<C1, C2, AuthoritiesGenericImpl>
{

	/**
	 * Die Liste der Kontaktgruppen, denen diese Kontaktzuordnung zugeordnet
	 * ist. Eine Kontaktzuordnung kann grundsätzlich in mehreren Kontaktgruppen
	 * enthalten sein.
	 */
	private List<ContactGroup>	contactGroups;

	/**
	 * Die Liste der Kontaktgruppen, denen diese Kontaktzuordnung zugeordnet
	 * ist. Eine Kontaktzuordnung kann grundsätzlich in mehreren Kontaktgruppen
	 * enthalten sein.
	 * 
	 * @return contactGroups
	 */
	// Mapping in Subclass
	@Transient
	public List<ContactGroup> getContactGroups()
	{
		return contactGroups;
	}

	/**
	 * Die Liste der Kontaktgruppen, denen diese Kontaktzuordnung zugeordnet
	 * ist. Eine Kontaktzuordnung kann grundsätzlich in mehreren Kontaktgruppen
	 * enthalten sein.
	 * 
	 * @param contactGroups - die Liste
	 */
	public void setContactGroups(List<ContactGroup> contactGroups)
	{
		this.contactGroups = contactGroups;
	}

}

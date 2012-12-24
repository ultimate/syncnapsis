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
package com.syncnapsis.data.service;

import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.model.contacts.EmpireAllianceContact;

/**
 * Manager-Interface für den Zugriff auf EmpireAllianceContact
 * 
 * @author ultimate
 */
public interface EmpireAllianceContactManager extends GenericManager<EmpireAllianceContact, Long>, ContactManager<Empire, Alliance, AuthoritiesGenericImpl>
{

}

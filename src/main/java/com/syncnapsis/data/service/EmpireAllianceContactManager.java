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

package com.syncnapsis.data.service;

import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.model.contacts.EmpireEmpireContact;

/**
 * Manager-Interface für den Zugriff auf EmpireEmpireContact
 * 
 * @author ultimate
 */
public interface EmpireEmpireContactManager extends GenericManager<EmpireEmpireContact, Long>, ContactManager<Empire, Empire, AuthoritiesGenericImpl>
{

}

package com.syncnapsis.data.service;

import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.model.contacts.AllianceAllianceContact;

/**
 * Manager-Interface für den Zugriff auf AllianceAllianceContact
 * 
 * @author ultimate
 */
public interface AllianceAllianceContactManager extends GenericManager<AllianceAllianceContact, Long>, ContactManager<Alliance, Alliance, AuthoritiesGenericImpl>
{

}

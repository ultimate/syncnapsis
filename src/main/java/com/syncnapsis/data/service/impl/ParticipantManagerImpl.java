package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.ParticipantDao;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.service.ParticipantManager;

/**
 * Manager-Implementation for access to Participant.
 * 
 * @author ultimate
 */
public class ParticipantManagerImpl extends GenericManagerImpl<Participant, Long> implements ParticipantManager
{
	/**
	 * ParticipantDao for database access
	 */
	protected ParticipantDao			participantDao;

	/**
	 * Standard Constructor
	 * 
	 * @param participantDao - ParticipantDao for database access
	 */
	public ParticipantManagerImpl(ParticipantDao participantDao)
	{
		super(participantDao);
		this.participantDao = participantDao;
	}
}

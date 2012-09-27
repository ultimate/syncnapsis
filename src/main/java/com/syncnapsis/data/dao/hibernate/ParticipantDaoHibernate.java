package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.ParticipantDao;
import com.syncnapsis.data.model.Participant;

/**
 * Dao-Implementation for Hibernate for access to Participant
 * 
 * @author ultimate
 */
public class ParticipantDaoHibernate extends GenericDaoHibernate<Participant, Long> implements ParticipantDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class Participant
	 */
	public ParticipantDaoHibernate()
	{
		super(Participant.class);
	}
}

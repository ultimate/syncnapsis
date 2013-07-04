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
package com.syncnapsis.data.dao.hibernate;

import java.util.List;

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

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.ParticipantDao#getByMatch(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Participant> getByMatch(long matchId)
	{
		return createQuery("from Participant p where p.match.id=?", matchId).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.ParticipantDao#getByMatchAndEmpire(long, long)
	 */
	@Override
	public Participant getByMatchAndEmpire(long matchId, long empireId)
	{
		return (Participant) createQuery("from Participant p where p.match.id=? and p.empire.id=?", matchId, empireId).uniqueResult();
	}
}

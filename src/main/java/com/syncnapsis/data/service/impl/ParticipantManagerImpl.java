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

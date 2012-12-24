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

import com.syncnapsis.data.dao.AllianceDao;
import com.syncnapsis.data.model.Alliance;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf Alliance
 * 
 * @author ultimate
 */
public class AllianceDaoHibernate extends GenericNameDaoHibernate<Alliance, Long> implements AllianceDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse Alliance
	 */
	public AllianceDaoHibernate()
	{
		super(Alliance.class, "shortName");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.AllianceDao#getByEmpire(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Alliance> getByEmpire(Long empireId)
	{
		return createQuery(
				"select distinct a from Alliance a" + " left outer join a.allianceMemberRanks amr" + " left outer join amr.empires e"
						+ " where e.id = ? order by a.shortName", empireId).list();
	}
}

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

import org.hibernate.SessionFactory;

import com.syncnapsis.data.dao.EmpireRankDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.EmpireRank;

public class EmpireRankDaoHibernate extends GenericRankDaoHibernate<EmpireRank, Empire, Long> implements EmpireRankDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse EmpireRank
	 */
	@Deprecated
	public EmpireRankDaoHibernate()
	{
		super(EmpireRank.class, ", entity.shortName asc");
	}

	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse EmpireRank and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public EmpireRankDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, EmpireRank.class, ", entity.shortName asc");
	}
}

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

import com.syncnapsis.data.dao.GUIActionDao;
import com.syncnapsis.data.model.GUIAction;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class GUIActionDaoHibernate extends GenericNameDaoHibernate<GUIAction, Long> implements GUIActionDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse GUIAction
	 */
	public GUIActionDaoHibernate()
	{
		super(GUIAction.class, "action");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.GUIActionDao#getByWindowId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GUIAction getByWindowId(String windowId)
	{
		List<GUIAction> items = createQuery("from GUIAction g where g.windowId=?", windowId).list();
		return singleResult(items);
	}
}

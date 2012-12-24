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

import com.syncnapsis.data.dao.MenuItemDao;
import com.syncnapsis.data.model.MenuItem;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class MenuItemDaoHibernate extends GenericDaoHibernate<MenuItem, String> implements MenuItemDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse MenuItem und der Option idOverwrite = true
	 */
	public MenuItemDaoHibernate()
	{
		super(MenuItem.class, true);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.MenuItemDao#get(java.lang.String, boolean)
	 */
	@Override
	public MenuItem get(String id, boolean evict)
	{
		MenuItem item = get(id);
		if(evict)
			currentSession().getSessionFactory().getCache().evictEntity(MenuItem.class, item.getId());
		return item;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.MenuItemDao#getChildren(java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuItem> getChildren(String parentId, boolean includeAdvanced)
	{
		List<MenuItem> items;
		if(includeAdvanced)
			items = createQuery("from MenuItem m where m.parent.id=? order by m.position", parentId).list();
		else
			items = createQuery("from MenuItem m where m.parent.id=? and m.advancedItem=false order by m.position", parentId).list();
		for(MenuItem item : items)
			currentSession().getSessionFactory().getCache().evictEntity(MenuItem.class, item.getId());
		return items;
	}
}

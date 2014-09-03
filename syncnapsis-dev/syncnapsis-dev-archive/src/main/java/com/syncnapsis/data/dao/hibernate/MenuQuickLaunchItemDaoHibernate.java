/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import com.syncnapsis.data.dao.MenuQuickLaunchItemDao;
import com.syncnapsis.data.model.MenuQuickLaunchItem;

/**
 * Dao-Implementierung f�r Hibernate f�r den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class MenuQuickLaunchItemDaoHibernate extends GenericDaoHibernate<MenuQuickLaunchItem, Long> implements MenuQuickLaunchItemDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse MenuQuickLaunchItem
	 */
	public MenuQuickLaunchItemDaoHibernate()
	{
		super(MenuQuickLaunchItem.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.MenuQuickLaunchItemDao#getByUser(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuQuickLaunchItem> getByUser(Long userId)
	{
		return createQuery("from MenuQuickLaunchItem q where q.user.id=? order by q.position", userId).list();
	}
}

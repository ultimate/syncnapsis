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

package com.syncnapsis.data.dao.hibernate;

import java.util.List;

import com.syncnapsis.data.dao.MenuQuickLaunchItemDao;
import com.syncnapsis.data.model.MenuQuickLaunchItem;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
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

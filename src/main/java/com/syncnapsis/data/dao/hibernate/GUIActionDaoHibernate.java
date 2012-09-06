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

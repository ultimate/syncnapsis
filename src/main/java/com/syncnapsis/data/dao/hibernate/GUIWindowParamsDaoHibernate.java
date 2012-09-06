package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.GUIWindowParamsDao;
import com.syncnapsis.data.model.GUIWindowParams;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class GUIWindowParamsDaoHibernate extends GenericDaoHibernate<GUIWindowParams, Long> implements GUIWindowParamsDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse GUIWindowParams
	 */
	public GUIWindowParamsDaoHibernate()
	{
		super(GUIWindowParams.class);
	}
}

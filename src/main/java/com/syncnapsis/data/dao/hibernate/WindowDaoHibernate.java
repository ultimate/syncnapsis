package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.WindowDao;
import com.syncnapsis.data.model.Window;
import com.syncnapsis.data.dao.hibernate.GenericDaoHibernate;

/**
 * Window-Dao-Implementation for hibernate
 * 
 * @author ultimate
 */
public class WindowDaoHibernate extends GenericDaoHibernate<Window, Long> implements WindowDao
{
	/**
	 * Default Constructor
	 */
	public WindowDaoHibernate()
	{
		super(Window.class);
	}

	// nothing here yet
}

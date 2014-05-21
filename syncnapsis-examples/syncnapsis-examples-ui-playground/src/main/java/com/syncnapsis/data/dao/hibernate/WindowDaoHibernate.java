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
	@Deprecated
	public WindowDaoHibernate()
	{
		super(Window.class);
	}

	/**
	 * Constructor with SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public WindowDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, Window.class);
	}

	// nothing here yet
}

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

import org.hibernate.SessionFactory;

import com.syncnapsis.data.dao.PlayerDao;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.exceptions.UserNotFoundException;

/**
 * Dao-Implementierung f�r Hibernate f�r den Zugriff auf Player
 * 
 * @author ultimate
 */
public class PlayerDaoHibernate extends GenericDaoHibernate<Player, Long> implements PlayerDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse Player
	 */
	@Deprecated
	public PlayerDaoHibernate()
	{
		super(Player.class);
	}

	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse Player and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public PlayerDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, Player.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.PlayerDao#getByUser(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Player getByUser(Long userId)
	{
		return (Player) singleResult(createQuery("from Player p where p.user.id=?", userId).list());
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.PlayerDao#getByUsername(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Player getByUsername(String username)
	{
		Player player = (Player) singleResult(createQuery("from Player p where p.user.username=?", username).list());
		if(player == null)
			throw new UserNotFoundException(username);
		return player;
	}
}

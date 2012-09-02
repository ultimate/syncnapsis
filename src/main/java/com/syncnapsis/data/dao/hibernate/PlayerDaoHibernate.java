package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.PlayerDao;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.exceptions.UserNotFoundException;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf Player
 * 
 * @author ultimate
 */
public class PlayerDaoHibernate extends GenericDaoHibernate<Player, Long> implements PlayerDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse Player
	 */
	public PlayerDaoHibernate()
	{
		super(Player.class);
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

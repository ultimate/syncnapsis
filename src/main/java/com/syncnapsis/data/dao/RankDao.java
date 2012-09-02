package com.syncnapsis.data.dao;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;

/**
 * Dao-Interface for accessing Rank
 * 
 * @author ultimate
 * @param <R> - the Rank-Class
 * @param <T> - the Class the rating refers to
 * @param <PK> - der Primary-Key-Type of the Object referenced by the Rank
 */
public interface RankDao<R extends Rank<T>, T extends BaseObject<PK>, PK extends Serializable> extends GenericDao<R, Long>
{
	/**
	 * Get the current Rank for an Object
	 * 
	 * @param entityId - the ID of the Object
	 * @return der Rang
	 */
	public R getByEntity(PK entityId);

	/**
	 * Get the history of Ranks for an Object
	 * 
	 * @param entityId - the ID of the Object
	 * @return the history of Ranks
	 */
	public List<R> getHistoryByEntity(PK entityId);

	/**
	 * Get all current Ranks ordered by the given rating criterion
	 * 
	 * @param criterion - the rating criterion
	 * @return the List of Ranks
	 */
	public List<R> getByCriterion(String criterion);

	/**
	 * Get all current Ranks ordered by the primary rating criterion of the default category.
	 * 
	 * @return the List of Ranks
	 */
	public List<R> getByDefaultPrimaryCriterion();
}
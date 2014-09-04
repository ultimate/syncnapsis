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
package com.syncnapsis.data.service;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;

/**
 * Manager-Interface for accessing Rank.
 * 
 * @author ultimate
 * @param <R> - the Rank-Class
 * @param <T> - the Class the rating refers to
 * @param <PK> - der Primary-Key-Type of the Object referenced by the Rank
 */
public interface RankManager<R extends Rank<T>, T extends BaseObject<PK>, PK extends Serializable> extends GenericManager<R, Long>
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
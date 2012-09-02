package com.syncnapsis.data.service;

import java.util.List;

import com.syncnapsis.data.model.Person;
import com.syncnapsis.data.service.GenericNameManager;

/**
 * A generic manager for Persons
 * 
 * @author ultimate
 */
public interface PersonManager extends GenericNameManager<Person, Long>
{
	/**
	 * Get all persons in a specified id range
	 * @param minId - start of the range
	 * @param maxId - end of the range
	 * @return the list of persons
	 */
	public List<Person> getInRange(Long minId, Long maxId, String orderBy);
}

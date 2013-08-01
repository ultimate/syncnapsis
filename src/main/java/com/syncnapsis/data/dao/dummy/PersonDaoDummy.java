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
package com.syncnapsis.data.dao.dummy;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.syncnapsis.data.dao.PersonDao;
import com.syncnapsis.data.model.Person;

/**
 * A PersonDao-Dummy
 * 
 * @author ultimate
 * 
 */
public class PersonDaoDummy implements PersonDao
{
	private List<Person>	persons;

	private Long			idCount	= 0L;

	public PersonDaoDummy()
	{
		this.persons = new LinkedList<Person>();
		this.persons.add(newPerson());
		this.persons.add(newPerson());
		this.persons.add(newPerson());
		this.persons.add(newPerson());
		this.persons.add(newPerson());
	}

	private Person newPerson()
	{
		return newPerson(idCount++, 1, Integer.toHexString((int) (Math.random() * 1000000)), Integer.toHexString((int) (Math.random() * 1000000)));
	}

	private Person newPerson(Long id, Integer version, String firstName, String lastName)
	{
		Person p = new Person();
		p.setId(id);
		p.setVersion(version);
		p.setFirstName(firstName);
		p.setLastName(lastName);
		return p;
	}

	@Override
	public Person getByName(String arg0)
	{
		for(Person p : persons)
			if(p.getLastName().equals(arg0))
				return p;
		return null;
	}

	@Override
	public List<Person> getByPrefix(String arg0, int arg1, boolean arg2)
	{
		List<Person> results = new LinkedList<Person>();
		for(Person p : persons)
			if(p.getLastName().startsWith(arg0))
				results.add(p);
		if(arg1 < results.size())
			return results.subList(0, arg1);
		return results;
	}

	@Override
	public List<Person> getOrderedByName(boolean arg0)
	{
		List<Person> results = new LinkedList<Person>(persons);
		Collections.sort(results, lnc);
		return results;
	}

	@Override
	public boolean isNameAvailable(String arg0)
	{
		return getByName(arg0) != null;
	}

	@Override
	public boolean exists(Long arg0)
	{
		return get(arg0) != null;
	}

	@Override
	public Person get(Long arg0)
	{
		for(Person p : persons)
			if(p.getId().equals(arg0))
				return p;
		return null;
	}

	@Override
	public List<Person> getAll()
	{
		return getAll(false);
	}

	@Override
	public List<Person> getAll(boolean arg0)
	{
		return new LinkedList<Person>(persons);
	}

	@Override
	public List<Person> getByIdList(List<Long> arg0)
	{
		List<Person> results = new LinkedList<Person>();
		for(Person p : persons)
			if(arg0.contains(p.getId()))
				results.add(p);
		return results;
	}

	@Override
	public String remove(Person arg0)
	{
		this.delete(arg0);
		return "deleted";
	}

	@Override
	public void delete(Person o)
	{
		this.persons.remove(o);
	}

	@Override
	public Person save(Person arg0)
	{
		if(arg0.getId() == null)
		{
			this.persons.add(arg0);
			arg0.setId(idCount++);
			arg0.setVersion(1);
			return arg0;
		}
		else
		{
			Person p = get(arg0.getId());
			p.setFirstName(arg0.getFirstName());
			p.setLastName(arg0.getLastName());
			p.setVersion(p.getVersion() + 1);
			return p;
		}
	}

	@Override
	public List<Person> getInRange(Long minId, Long maxId, String orderBy)
	{
		List<Person> results = new LinkedList<Person>();
		for(Person p : persons)
		{
			if(minId != null && p.getId() < minId)
				continue;
			if(maxId != null && p.getId() > maxId)
				continue;
			results.add(p);
		}
		if("firstName".equals(orderBy))
			Collections.sort(results, fnc);
		else if("lastName".equals(orderBy))
			Collections.sort(results, lnc);
		return results;
	}

	private static final Comparator<Person>	fnc	= new Comparator<Person>() {
													@Override
													public int compare(Person o1, Person o2)
													{
														return sc.compare(o1.getFirstName(), o2.getFirstName());
													}
												};

	private static final Comparator<Person>	lnc	= new Comparator<Person>() {
													@Override
													public int compare(Person o1, Person o2)
													{
														return sc.compare(o1.getLastName(), o2.getLastName());
													}
												};

	private static final Comparator<String>	sc	= new Comparator<String>() {

													@Override
													public int compare(String o1, String o2)
													{
														if(o1 == null)
															return -1;
														else if(o2 == null)
															return 1;
														else
															return o1.toLowerCase().compareTo(o2.toLowerCase());
													}
												};
}

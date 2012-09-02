package com.syncnapsis.data.dao.hibernate;

import java.util.List;

import com.syncnapsis.data.dao.PersonDao;
import com.syncnapsis.data.model.Person;
import com.syncnapsis.data.dao.hibernate.GenericNameDaoHibernate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Person-Dao-Implementation for hibernate
 * 
 * @author ultimate
 */
public class PersonDaoHibernate extends GenericNameDaoHibernate<Person, Long> implements PersonDao
{
	/**
	 * Default Constructor
	 */
	public PersonDaoHibernate()
	{
		super(Person.class, "lastName");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.PersonDao#getInRange(java.lang.Long, java.lang.Long)
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getInRange(Long minId, Long maxId, String orderBy)
	{
		Assert.isTrue(orderBy == null || orderBy.equals("id") || orderBy.equals("firstName") || orderBy.equals("lastName"), "illegal orderBy: '"
				+ orderBy + "'");
		String order = (orderBy != null ? "order by lower(p." + orderBy + ")" : "");
		if(minId != null && maxId != null)
			return createQuery("from Person p where p.id >= ? and p.id <= ? " + order, minId, maxId).list();
		else if(minId != null)
			return createQuery("from Person p where p.id >= ? " + order, minId).list();
		else if(maxId != null)
			return createQuery("from Person p where p.id <= ? " + order, maxId).list();
		else
			return createQuery("from Person p " + order).list();
	}
}

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
package com.syncnapsis.tests;

import java.lang.reflect.Method;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.persister.entity.EntityPersister;
import com.syncnapsis.utils.HibernateUtil;

public abstract class HibernateConfigurationTestCase extends BaseDaoTestCase
{
	@SuppressWarnings("rawtypes")
	public void testColumnMapping() throws Exception
	{
		logger.debug("testing column mapping...");
		
		Map metadata = HibernateUtil.currentSession().getSessionFactory().getAllClassMetadata();
		for(Object o : metadata.values())
		{
			EntityPersister persister = (EntityPersister) o;
			String className = persister.getEntityName();
			logger.debug("Trying select * from: " + className);
			Query q = HibernateUtil.currentSession().createQuery("from " + className + " c");
			q.iterate();
			logger.debug("ok: " + className);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testEqualsAndHashCode() throws Exception
	{
		logger.debug("testing column mapping...");

		Class contactClass = null;
		try
		{
			contactClass = Class.forName("com.syncnapsis.data.model.Contact");			
		}
		catch(ClassNotFoundException e)
		{
			logger.debug("Class 'Contact' not found!");
		}
		
		Map metadata = HibernateUtil.currentSession().getSessionFactory().getAllClassMetadata();
		for(Object o : metadata.values())
		{
			EntityPersister persister = (EntityPersister) o;
			String className = persister.getEntityName();
			
			logger.debug("Testing for hashcode and equals: " + className);
			Class<?> cls = Class.forName(className);
			
			if(contactClass != null && contactClass.isAssignableFrom(cls))
			{
				logger.debug("Ignored subclass of Contact: " + className);
				continue;
			}			
			
			boolean found = false;
			for(Method m: cls.getDeclaredMethods())
			{
				if(m.getName().equals("equals") && m.getParameterTypes().length == 1 && m.getParameterTypes()[0].equals(Object.class))
				{
					found = true;
					break;
				}
			}
			assertTrue("no equals found: " + className, found);
			found = false;
			for(Method m: cls.getDeclaredMethods())
			{
				if(m.getName().equals("hashCode") && m.getParameterTypes().length == 0)
				{
					found = true;
					break;
				}
			}
			assertTrue("no hashCode found: " + className, found);
		}
	}
}

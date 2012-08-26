package com.syncnapsis.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class HibernateUtilTest extends LoggerTestCase
{
	@TestCoversMethods({ "getInstance", "*etSessionFactory", "initSessionFactory", "currentSession", "closeSession" })
	public void testInstantiation()
	{
		HibernateUtil h = HibernateUtil.getInstance();
		HibernateUtil h2 = HibernateUtil.getInstance();

		assertNotNull(h);
		assertSame(h, h2);

		SessionFactory overrideSF = createSessionFactory();
		SessionFactory defaultSF = h.getSessionFactory();

		assertNotNull(defaultSF);
		h.setSessionFactory(overrideSF);
		assertNotSame(overrideSF, defaultSF);
		assertSame(overrideSF, h.getSessionFactory());
		
		Session session = HibernateUtil.currentSession();
		
		assertNotNull(session);
		assertSame(h.getSessionFactory().getCurrentSession(), session);
		assertTrue(session.isOpen());
		
		HibernateUtil.closeSession();
		
		assertFalse(session.isOpen());
		assertNotSame(session, HibernateUtil.currentSession());
		assertTrue(HibernateUtil.currentSession().isOpen());
	}

	private SessionFactory createSessionFactory()
	{
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		return configuration.buildSessionFactory(serviceRegistry);
	}
}

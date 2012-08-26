package com.syncnapsis.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util-Klasse für den Zugriff auf die Hibernate-Session
 * 
 * @author ultimate
 */
public class HibernateUtil
{
	/**
	 * Logger-Instance
	 */
	private static transient final Logger	logger		= LoggerFactory.getLogger(HibernateUtil.class);

	/**
	 * The local HibernateUtil
	 */
	private static HibernateUtil			instance	= new HibernateUtil();

	/**
	 * Hibernate SessionFactory singleton
	 */
	private static SessionFactory			sessionFactory;

	/**
	 * Only allow creation via getInstance
	 */
	private HibernateUtil()
	{

	}

	/**
	 * Get the singleton instance allowing access to this Util
	 * 
	 * @return the HibernateUtil
	 */
	public static HibernateUtil getInstance()
	{
		return instance;
	}

	/**
	 * The SessionFactory to be used by the HibernateUtil
	 * 
	 * @param sessionFactory - the SessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		logger.debug("setting SessionFactory");
		if(HibernateUtil.sessionFactory != null)
			logger.warn("SessionFactory is not null but will be overwritten!");
		HibernateUtil.sessionFactory = sessionFactory;
	}

	/**
	 * The SessionFactory to be used by the HibernateUtil.
	 * If the inner SessionFactory is null a new one will be initiated by default.
	 * 
	 * @return the SessionFactory
	 */
	public SessionFactory getSessionFactory()
	{
		return initSessionFactory(null);
	}

	/**
	 * The current Session.
	 * 
	 * @see HibernateUtil#getSessionFactory()
	 * @see SessionFactory#getCurrentSession()
	 * @return the Hibernate-Session
	 */
	public static Session currentSession() throws HibernateException
	{
		return instance.getSessionFactory().getCurrentSession();
	}

	/**
	 * Closes the current Session
	 * 
	 * @see HibernateUtil#currentSession()
	 * @see Session#close()
	 */
	public static void closeSession() throws HibernateException
	{
		currentSession().close();
	}

	/**
	 * Initialize the SessionFactory if no SessionFactory is set.
	 * 
	 * @param resource - an optional path to a Resource from which the SessionFactory will be
	 *            initialized.
	 * @see Configuration#configure(String)
	 * @see Configuration#configure()
	 * @return the newly created SessionFactory
	 */
	public SessionFactory initSessionFactory(String resource)
	{
		if(sessionFactory == null)
		{
			try
			{
				Configuration configuration;
				if(resource == null)
					configuration = new Configuration().configure();
				else
					configuration = new Configuration().configure(resource);
				
				ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				
				logger.info("Hibernate configuration file loaded: " + (resource == null ? "hibernate.cfg.xml" : resource));
			}
			catch(Throwable ex)
			{
				logger.error("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sessionFactory;
	}
}

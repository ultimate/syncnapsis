package com.syncnapsis.providers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Base for Providers that provide values stored as attributes in the current Session.<br>
 * Therefore a SessionProvider is used to get the current Session.
 * 
 * @see Provider
 * @see SessionProvider
 * @see HttpSession#getAttribute(String)
 * @see HttpSession#setAttribute(String, Object)
 * @author ultimate
 * @param <T> - the Type this Provider provides
 */
public class SessionBasedProvider<T> implements Provider<T>, InitializingBean
{
	/**
	 * The SessionProvider used to get the current Session
	 */
	protected SessionProvider	sessionProvider;
	/**
	 * The name of the attribute stored in the Session
	 * 
	 * @see HttpSession#getAttribute(String)
	 * @see HttpSession#setAttribute(String, Object)
	 */
	protected String			attributeName;

	/**
	 * Default Constructor initializing the SessionBasedProvider with a given attributeName
	 * 
	 * @param attributeName - The name of the attribute stored in the Session
	 */
	public SessionBasedProvider(String attributeName)
	{
		super();
		this.attributeName = attributeName;
	}

	/**
	 * The SessionProvider used to get the current Session
	 * 
	 * @return sessionProvider
	 */
	public SessionProvider getSessionProvider()
	{
		return sessionProvider;
	}

	/**
	 * The SessionProvider used to get the current Session
	 * 
	 * @param sessionProvider - the SessionProvider
	 */
	public void setSessionProvider(SessionProvider sessionProvider)
	{
		this.sessionProvider = sessionProvider;
	}

	/**
	 * The name of the attribute stored in the Session
	 * 
	 * @see HttpSession#getAttribute(String)
	 * @see HttpSession#setAttribute(String, Object)
	 * @return attributeName
	 */
	public String getAttributeName()
	{
		return attributeName;
	}

	/**
	 * The name of the attribute stored in the Session
	 * 
	 * @see HttpSession#getAttribute(String)
	 * @see HttpSession#setAttribute(String, Object)
	 * @param attributeName - the attribute name
	 */
	public void setAttributeName(String attributeName)
	{
		this.attributeName = attributeName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(sessionProvider, "sessionProvider must not be null!");
		Assert.notNull(attributeName, "attributeName must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#get()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get()
	{
		return (T) sessionProvider.get().getAttribute(attributeName);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#set(java.lang.Object)
	 */
	@Override
	public void set(T t) throws UnsupportedOperationException
	{
		sessionProvider.get().setAttribute(attributeName, t);
	}
}

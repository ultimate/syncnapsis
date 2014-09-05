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
package com.syncnapsis.utils.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Generic implementation for entity clouds being load
 * 
 * @author ultimate
 * 
 * @param <E> - the type of entities in this cloud
 */
public abstract class Cloud<E>
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger		= LoggerFactory.getLogger(getClass());

	/**
	 * Has the interal list of points of this cloud initialized and is this cloud fully
	 * prepared for accessing the entity list?
	 */
	private boolean						prepared	= false;

	/**
	 * The list of entities for this cloud
	 */
	private List<E>						entities;

	/**
	 * Has the interal list of points of this cloud initialized and is this cloud fully
	 * prepared for accessing the entity list?
	 * 
	 * @return true or false
	 */
	public boolean isPrepared()
	{
		return prepared;
	}

	/**
	 * The list of entities for this cloud.<br>
	 * This may be null if {@link Cloud#isPrepared()} is not true.
	 * 
	 * @return
	 */
	public List<E> getEntities()
	{
		return entities;
	}

	/**
	 * Prepare the list of entities for this cloude, e.g.
	 * <ul>
	 * <li>generate random values</li>
	 * <li>load from file</li>
	 * <li>etc.</li>
	 * </ul>
	 * The process of preparation will be multi-threaded if the subclass allows it but will never
	 * use more threads that defined by the argument by using a {@link ThreadQueue}.
	 * 
	 * @param maxNumberOfThreads - the maximum number of threads to use
	 */
	public synchronized final void prepare(int maxNumberOfThreads)
	{
		Assert.isTrue(maxNumberOfThreads > 0);
		List<Creator> creators = prepareCreators(maxNumberOfThreads);
		Assert.isTrue(creators.size() <= maxNumberOfThreads); // TODO invalid

		this.entities = Collections.synchronizedList(new ArrayList<E>(getExpectedEntities()));

		// TODO use threadqueue
		List<Thread> threads = new ArrayList<Thread>(creators.size());
		for(Creator c : creators)
		{
			threads.add(new Thread(c));
		}

		for(Thread t : threads)
		{
			t.start();
		}
		for(Thread t : threads)
		{
			try
			{
				t.join();
			}
			catch(InterruptedException e)
			{
				logger.error("could not join Thread '" + t.getName() + "'", e);
			}
		}

		// TODO don't wait here
		// set prepared on threadqueue end
		// return tq ?

		this.prepared = true;
	}

	public void joinPrepare()
	{
		// TODO
	}

	/**
	 * Get the number of expected entities.<br>
	 * (Used for list initialization)
	 * 
	 * @return the number of expected entities
	 */
	protected int getExpectedEntities()
	{
		return 0;
	}

	/**
	 * Prepare the list of entity creators for use in {@link Cloud#prepare(int)}.<br>
	 * The preferred number is given by {@link Cloud#prepare(int)} as the max number of threads but
	 * may exceeded anyway since a {@link ThreadQueue} will be used.
	 * 
	 * @param preferredNumberOfCreators - the preferred number of creators
	 * @return the list of creators
	 */
	protected abstract List<Creator> prepareCreators(int preferredNumberOfCreators);

	/**
	 * Abstract base for creators used in {@link Cloud#prepare(int)}
	 * 
	 * @author ultimate
	 */
	protected abstract class Creator implements Runnable
	{
		/**
		 * The name of this creator
		 */
		private String	name;

		/**
		 * Create a new Creator
		 * 
		 * @param name - the name of this creator
		 */
		public Creator(String name)
		{
			Assert.notNull(name);
			this.name = name;
		}

		/**
		 * The name of this creator
		 * 
		 * @return the name
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * Create the next entity
		 * 
		 * @return the newly created entity
		 */
		public abstract E nextEntity();

		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public final void run()
		{
			E e;
			while((e = nextEntity()) != null)
			{
				entities.add(e);
			}
		}
	}
}

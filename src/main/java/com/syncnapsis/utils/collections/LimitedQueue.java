package com.syncnapsis.utils.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Einfache FIFO-Queue, die eine limitierte Länge hat. Wird die Länge überschritten wird das
 * erste Element rausgeschmissen.
 * 
 * @author ultimate
 * @param <E>
 */
public class LimitedQueue<E> implements Queue<E>
{
	/**
	 * Die Länge dieser Queue
	 */
	private int					limit;
	/**
	 * Die Liste zum Vorhalten der Elemente
	 */
	private List<E>				list				= new LinkedList<E>();

	/**
	 * Constructor mit vorgegebener Länge
	 * 
	 * @param limit - Die Länge dieser Queue
	 */
	public LimitedQueue(int limit)
	{
		this.limit = limit;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.LinkedList#add(java.lang.Object)
	 */
	@Override
	public boolean add(E o)
	{
		this.list.add(o);
		while(size() > this.limit)
		{
			this.remove();
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size()
	{
		return this.list.size();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty()
	{
		return this.list.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o)
	{
		return this.list.contains(o);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<E> iterator()
	{
		return this.list.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray()
	{
		return this.list.toArray();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#toArray(T[])
	 */
	@Override
	public <T> T[] toArray(T[] a)
	{
		return this.list.toArray(a);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o)
	{
		return this.list.remove(o);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c)
	{
		return this.list.containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		if(c == null || c.size() == 0)
			return false;
		for(E e : c)
			this.add(e);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c)
	{
		return this.list.removeAll(c);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c)
	{
		return this.list.retainAll(c);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear()
	{
		this.list.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Queue#offer(java.lang.Object)
	 */
	@Override
	public boolean offer(E e)
	{
		return this.add(e);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Queue#remove()
	 */
	@Override
	public E remove()
	{
		return this.list.remove(0);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Queue#poll()
	 */
	@Override
	public E poll()
	{
		return this.remove();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Queue#element()
	 */
	@Override
	public E element()
	{
		return this.list.get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Queue#peek()
	 */
	@Override
	public E peek()
	{
		if(this.isEmpty())
			return null;
		return element();
	}
}
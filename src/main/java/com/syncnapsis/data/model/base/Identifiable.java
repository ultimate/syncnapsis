package com.syncnapsis.data.model.base;

import java.io.Serializable;

import com.syncnapsis.security.annotations.Accessible;

/**
 * Interface for identifiable Objects.<br>
 * Those Objects must provide an ID via getId() and setId(PK id).<br>
 * <br>
 * Usage examples:<br>
 * <ul>
 * <li>matching authority-Objects to values in {@link Accessible}-Annotations</li>
 * <li>super Interface for com.syncnapsis.BaseObject (in com.syncnapsis.data)</li>
 * </ul>
 * 
 * @author ultimate
 * @param <PK> - the type of the id
 */
public interface Identifiable<PK extends Serializable>
{
	public PK getId();

	public void setId(PK id);
}

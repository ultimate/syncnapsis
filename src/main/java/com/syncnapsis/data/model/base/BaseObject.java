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
package com.syncnapsis.data.model.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.security.annotations.Accessible;

/**
 * Abstrakte Oberklasse als Basis für alle Model-Klassen und somit alle
 * Objekttypen, die als über Tabellen in der Datenbank gespeichert werden.
 * In dieser Klasse sind die immer notwendigen Felder/Spalten "ID" und "Version"
 * definiert, so dass alle Subklassen über diese Eigenschaften verfügen. Diese
 * Eigenschaften sind nicht nur für die Verwendung von Hibernate, sondern auch
 * für die grundlegende Identifizierung und Unterscheidung von Objekten einer
 * Klasse hilfreich.
 * Desweiteren definiert diese Klasse eine PELogger-Instanz, für die Verwendung
 * in den Subklassen.
 * 
 * @author ultimate
 * @param <PK> - die Klasse für den Primärschlüssel
 */
@MappedSuperclass
public abstract class BaseObject<PK extends Serializable> implements Model, Identifiable<PK>
{
	/**
	 * Logger-Instanz zur Verwendung in allen Subklassen
	 */
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * Der Primärschlüssel des Objektes
	 */
	protected PK			id;
	/**
	 * Die Version des Objektes
	 */
	protected Integer		version;

	/**
	 * Leerer Standard Constructor
	 */
	public BaseObject()
	{
	}

	/**
	 * Der Primärschlüssel des Objektes
	 * 
	 * @return id
	 */
	@Id
	@Column(length = LENGTH_ID)
	@SequenceGenerator(name = "HIBERNATE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "HIBERNATE_SEQ")
	@Accessible(Accessible.ANYBODY)
	public PK getId()
	{
		return id;
	}

	/**
	 * Die Version des Objektes
	 * 
	 * @return version
	 */
	@Version
	@Accessible(Accessible.NOBODY)
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * Der Primärschlüssel des Objektes
	 * 
	 * @param id - der Primärschlüssel
	 */
	@Accessible(Accessible.NOBODY)
	public void setId(PK id)
	{
		this.id = id;
	}

	/**
	 * Die Version des Objektes
	 * 
	 * @param version - die Version
	 */
	@Accessible(Accessible.NOBODY)
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof BaseObject))
			return false;
		BaseObject other = (BaseObject) obj;
		if(id == null)
		{
			if(other.id != null)
				return false;
		}
		else if(!id.equals(other.id))
			return false;
		if(version == null)
		{
			if(other.version != null)
				return false;
		}
		else if(!version.equals(other.version))
			return false;
		return true;
	}
}

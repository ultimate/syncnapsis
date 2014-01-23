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

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.syncnapsis.security.Ownable;

/**
 * Abstrakte Klasse als generische Grundlage für die Beziehungen zwischen:
 * - Allianz & Allianz
 * - Allianz & Imperium
 * - Imperium & Allianz
 * - Imperium & Imperium
 * Über die Kontaktzuordnung werden beiden Parteien Kontakt-Rechte zugeordnet.
 * Desweiteren bietet diese Klasse die Möglichkeit den derzeitigen Zustand der
 * Bestätigung beider Parteien zu erfassen, sowie Änderungen vorzuschlagen und
 * diese erneut zu bestätigen.
 * 
 * @author ultimate
 * @param <C1> - die Klasse für die eine Seite der Kontaktzuordnung
 * @param <C2> - die Klasse für die andere Seite der Kontaktzuordnung
 * @param <R> - die Klasse für die Rechte, die vergeben werden
 */
@MappedSuperclass
public abstract class Contact<C1 extends BaseObject<?>, C2 extends BaseObject<?>, A extends Authorities> extends BaseObject<Long> implements
		Ownable<BaseObject<?>>
{
	/**
	 * Ist diese Kontaktzuordnung grundsätzlich gleichberechtigt für beide
	 * Parteien? Es wird in diesem Fall nur ein ContactAuthorities-Objekt verwaltet.
	 */
	private boolean	equalContactAuthorities;

	/**
	 * Die 1. Partei
	 */
	private C1		contact1;
	/**
	 * Die Rechte der 1. Partei
	 */
	private A		contactAuthorities1;
	/**
	 * Ein möglicher Änderungsvorschlag für die Rechte der 1. Partei
	 */
	private A		changedContactAuthorities1;
	/**
	 * Wurde die Zuordnung von der 1. Partei bestätigt?
	 */
	private boolean	approvedByContact1;
	/**
	 * Wurden die gemachten Änderungen von der 1. Partei bestätigt?
	 */
	private boolean	changesApprovedByContact1;
	/**
	 * Ist diese Kontaktzuordnung im Profil der 1. Partei per default sichtbar?
	 */
	private boolean	defaultVisible1;

	/**
	 * Die 2. Partei
	 */
	private C2		contact2;
	/**
	 * Die Rechte der 2. Partei
	 */
	private A		contactAuthorities2;
	/**
	 * Ein möglicher Änderungsvorschlag für die Rechte der 2. Partei
	 */
	private A		changedContactAuthorities2;
	/**
	 * Wurde die Zuordnung von der 2. Partei bestätigt?
	 */
	private boolean	approvedByContact2;
	/**
	 * Wurden die gemachten Änderungen von der 2. Partei bestätigt?
	 */
	private boolean	changesApprovedByContact2;
	/**
	 * Ist diese Kontaktzuordnung im Profil der 2. Partei per default sichtbar?
	 */
	private boolean	defaultVisible2;

	/**
	 * Leerer Standard Constructor
	 */
	public Contact()
	{
	}

	/**
	 * Ist diese Kontaktzuordnung grundsätzlich gleichberechtigt für beide
	 * Parteien? Es wird in diesem Fall nur ein ContactAuthorities-Objekt verwaltet.
	 * 
	 * @return equalContactAuthorities
	 */
	@Column(nullable = false)
	public boolean isEqualContactAuthorities()
	{
		return equalContactAuthorities;
	}

	/**
	 * Die 1. Partei
	 * 
	 * @return contact1
	 */
	@ManyToOne
	@JoinColumn(name = "fkContact1", nullable = false)
	public C1 getContact1()
	{
		return contact1;
	}

	/**
	 * Die Rechte der 1. Partei
	 * 
	 * @return contactAuthorities1
	 */
	@ManyToOne
	@JoinColumn(name = "fkContactAuthorities1", nullable = false)
	public A getContactAuthorities1()
	{
		return contactAuthorities1;
	}

	/**
	 * Ein möglicher Änderungsvorschlag für die Rechte der 1. Partei
	 * 
	 * @return changedContactAuthorities1
	 */
	@ManyToOne
	@JoinColumn(name = "fkChangedContactAuthorities1", nullable = true)
	public A getChangedContactAuthorities1()
	{
		return changedContactAuthorities1;
	}

	/**
	 * Wurde die Zuordnung von der 1. Partei bestätigt?
	 * 
	 * @return approvedByContact1
	 */
	@Column(nullable = false)
	public boolean isApprovedByContact1()
	{
		return approvedByContact1;
	}

	/**
	 * Wurden die gemachten Änderungen von der 1. Partei bestätigt?
	 * 
	 * @return changesApprovedByContact1
	 */
	@Column(nullable = false)
	public boolean isChangesApprovedByContact1()
	{
		return changesApprovedByContact1;
	}

	/**
	 * Ist diese Kontaktzuordnung im Profil der 1. Partei per default sichtbar?
	 * 
	 * @return defaultVisible1
	 */
	@Column(nullable = false)
	public boolean isDefaultVisible1()
	{
		return defaultVisible1;
	}

	/**
	 * Die 2. Partei
	 * 
	 * @return contact2
	 */
	@ManyToOne
	@JoinColumn(name = "fkContact2", nullable = false)
	public C2 getContact2()
	{
		return contact2;
	}

	/**
	 * Die Rechte der 2. Partei
	 * 
	 * @return contactAuthorities2
	 */
	@ManyToOne
	@JoinColumn(name = "fkContactAuthorities2", nullable = false)
	public A getContactAuthorities2()
	{
		return contactAuthorities2;
	}

	/**
	 * Ein möglicher Änderungsvorschlag für die Rechte der 2. Partei
	 * 
	 * @return changedContactAuthorities2
	 */
	@ManyToOne
	@JoinColumn(name = "fkChangedContactAuthorities2", nullable = true)
	public A getChangedContactAuthorities2()
	{
		return changedContactAuthorities2;
	}

	/**
	 * Wurde die Zuordnung von der 2. Partei bestätigt?
	 * 
	 * @return approvedByContact2
	 */
	@Column(nullable = false)
	public boolean isApprovedByContact2()
	{
		return approvedByContact2;
	}

	/**
	 * Wurden die gemachten Änderungen von der 2. Partei bestätigt?
	 * 
	 * @return changesApprovedByContact2
	 */
	@Column(nullable = false)
	public boolean isChangesApprovedByContact2()
	{
		return changesApprovedByContact2;
	}

	/**
	 * Ist diese Kontaktzuordnung im Profil der 2. Partei per default sichtbar?
	 * 
	 * @return defaultVisible2
	 */
	@Column(nullable = false)
	public boolean isDefaultVisible2()
	{
		return defaultVisible2;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.Ownable#getOwners()
	 */
	@Transient
	@Override
	public List<BaseObject<?>> getOwners()
	{
		return Arrays.asList(getContact1(), getContact2());
	}

	/**
	 * Ist diese Kontaktzuordnung grundsätzlich gleichberechtigt für beide
	 * Parteien? Es wird in diesem Fall nur ein ContactAuthorities-Objekt verwaltet.
	 * 
	 * @param equalContactAuthorities - true oder false
	 */
	public void setEqualContactAuthorities(boolean equalContactAuthorities)
	{
		this.equalContactAuthorities = equalContactAuthorities;
	}

	/**
	 * Die 1. Partei
	 * 
	 * @param contact1 - die Partei
	 */
	public void setContact1(C1 contact1)
	{
		this.contact1 = contact1;
	}

	/**
	 * Die Rechte der 1. Partei
	 * 
	 * @param contactAuthorities1 - die Rechte
	 */
	public void setContactAuthorities1(A contactAuthorities1)
	{
		this.contactAuthorities1 = contactAuthorities1;
	}

	/**
	 * Ein möglicher Änderungsvorschlag für die Rechte der 1. Partei
	 * 
	 * @param changedContactAuthorities1 - die Rechte
	 */
	public void setChangedContactAuthorities1(A changedContactAuthorities1)
	{
		this.changedContactAuthorities1 = changedContactAuthorities1;
	}

	/**
	 * Wurde die Zuordnung von der 1. Partei bestätigt?
	 * 
	 * @param approvedByContact1 - true oder false
	 */
	public void setApprovedByContact1(boolean approvedByContact1)
	{
		this.approvedByContact1 = approvedByContact1;
	}

	/**
	 * Wurden die gemachten Änderungen von der 1. Partei bestätigt?
	 * 
	 * @param changesApprovedByContact1 - true oder false
	 */
	public void setChangesApprovedByContact1(boolean changesApprovedByContact1)
	{
		this.changesApprovedByContact1 = changesApprovedByContact1;
	}

	/**
	 * Ist diese Kontaktzuordnung im Profil der 1. Partei per default sichtbar?
	 * 
	 * @param defaultVisible1 - true oder false
	 */
	public void setDefaultVisible1(boolean defaultVisible1)
	{
		this.defaultVisible1 = defaultVisible1;
	}

	/**
	 * Die 2. Partei
	 * 
	 * @param contact2 - die Partei
	 */
	public void setContact2(C2 contact2)
	{
		this.contact2 = contact2;
	}

	/**
	 * Die Rechte der 2. Partei
	 * 
	 * @param contactAuthorities2 - die Rechte
	 */
	public void setContactAuthorities2(A contactAuthorities2)
	{
		this.contactAuthorities2 = contactAuthorities2;
	}

	/**
	 * Ein möglicher Änderungsvorschlag für die Rechte der 2. Partei
	 * 
	 * @param changedContactAuthorities2 - die Rechte
	 */
	public void setChangedContactAuthorities2(A changedContactAuthorities2)
	{
		this.changedContactAuthorities2 = changedContactAuthorities2;
	}

	/**
	 * Wurde die Zuordnung von der 2. Partei bestätigt?
	 * 
	 * @param approvedByContact2 - true oder false
	 */
	public void setApprovedByContact2(boolean approvedByContact2)
	{
		this.approvedByContact2 = approvedByContact2;
	}

	/**
	 * Wurden die gemachten Änderungen von der 2. Partei bestätigt?
	 * 
	 * @param changesApprovedByContact2 - true oder false
	 */
	public void setChangesApprovedByContact2(boolean changesApprovedByContact2)
	{
		this.changesApprovedByContact2 = changesApprovedByContact2;
	}

	/**
	 * Ist diese Kontaktzuordnung im Profil der 2. Partei per default sichtbar?
	 * 
	 * @param defaultVisible2 - true oder false
	 */
	public void setDefaultVisible2(boolean defaultVisible2)
	{
		this.defaultVisible2 = defaultVisible2;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (approvedByContact1 ? 1231 : 1237);
		result = prime * result + (approvedByContact2 ? 1231 : 1237);
		result = prime * result + ((changedContactAuthorities1 == null) ? 0 : changedContactAuthorities1.getId().hashCode());
		result = prime * result + ((changedContactAuthorities2 == null) ? 0 : changedContactAuthorities2.getId().hashCode());
		result = prime * result + (changesApprovedByContact1 ? 1231 : 1237);
		result = prime * result + (changesApprovedByContact2 ? 1231 : 1237);
		result = prime * result + ((contact1 == null) ? 0 : contact1.getId().hashCode());
		result = prime * result + ((contact2 == null) ? 0 : contact2.getId().hashCode());
		result = prime * result + ((contactAuthorities1 == null) ? 0 : contactAuthorities1.getId().hashCode());
		result = prime * result + ((contactAuthorities2 == null) ? 0 : contactAuthorities2.getId().hashCode());
		result = prime * result + (defaultVisible1 ? 1231 : 1237);
		result = prime * result + (defaultVisible2 ? 1231 : 1237);
		result = prime * result + (equalContactAuthorities ? 1231 : 1237);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if(approvedByContact1 != other.approvedByContact1)
			return false;
		if(approvedByContact2 != other.approvedByContact2)
			return false;
		if(changedContactAuthorities1 == null)
		{
			if(other.changedContactAuthorities1 != null)
				return false;
		}
		else if(!changedContactAuthorities1.getId().equals(other.changedContactAuthorities1.getId()))
			return false;
		if(changedContactAuthorities2 == null)
		{
			if(other.changedContactAuthorities2 != null)
				return false;
		}
		else if(!changedContactAuthorities2.getId().equals(other.changedContactAuthorities2.getId()))
			return false;
		if(changesApprovedByContact1 != other.changesApprovedByContact1)
			return false;
		if(changesApprovedByContact2 != other.changesApprovedByContact2)
			return false;
		if(contact1 == null)
		{
			if(other.contact1 != null)
				return false;
		}
		else if(!contact1.getId().equals(other.contact1.getId()))
			return false;
		if(contact2 == null)
		{
			if(other.contact2 != null)
				return false;
		}
		else if(!contact2.getId().equals(other.contact2.getId()))
			return false;
		if(contactAuthorities1 == null)
		{
			if(other.contactAuthorities1 != null)
				return false;
		}
		else if(!contactAuthorities1.getId().equals(other.contactAuthorities1.getId()))
			return false;
		if(contactAuthorities2 == null)
		{
			if(other.contactAuthorities2 != null)
				return false;
		}
		else if(!contactAuthorities2.getId().equals(other.contactAuthorities2.getId()))
			return false;
		if(defaultVisible1 != other.defaultVisible1)
			return false;
		if(defaultVisible2 != other.defaultVisible2)
			return false;
		if(equalContactAuthorities != other.equalContactAuthorities)
			return false;
		return true;
	}
}

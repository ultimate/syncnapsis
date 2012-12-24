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
package com.syncnapsis.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.syncnapsis.data.model.base.Authorities;

/**
 * Klasse, die die einzelnen Rechte zusammenfasst, damit diese von woanders als "Paket" referenziert
 * werden können.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "authorities")
public class AuthoritiesGenericImpl extends Authorities
{	
	/**
	 * Die Liste der freigeschalteten Rechte
	 */
	protected List<Authority>	authoritiesGranted;
	/**
	 * Die Liste der nicht freigeschalteten Rechte
	 */
	protected List<Authority>	authoritiesNotGranted;

	/**
	 * Leerer Standard Constructor
	 */
	public AuthoritiesGenericImpl()
	{
	}

	/**
	 * Die Liste der freigeschalteten Rechte
	 * 
	 * @return authoritiesGranted
	 */
	@ManyToMany
	@JoinTable(name = "authoritiesGranted", joinColumns = { @JoinColumn(name = "fkAuthorities") }, inverseJoinColumns = { @JoinColumn(name = "fkAuthority") })
	public List<Authority> getAuthoritiesGranted()
	{
		return authoritiesGranted;
	}

	/**
	 * Die Liste der nicht freigeschalteten Rechte
	 * 
	 * @return authoritiesNotGranted
	 */
	@ManyToMany
	@JoinTable(name = "authoritiesNotGranted", joinColumns = { @JoinColumn(name = "fkAuthorities") }, inverseJoinColumns = { @JoinColumn(name = "fkAuthority") })
	public List<Authority> getAuthoritiesNotGranted()
	{
		return authoritiesNotGranted;
	}

	/**
	 * Die Liste der nicht freigeschalteten Rechte
	 * 
	 * @param authoritiesGranted
	 */
	public void setAuthoritiesGranted(List<Authority> authoritiesGranted)
	{
		this.authoritiesGranted = authoritiesGranted;
	}

	/**
	 * Die Liste der nicht freigeschalteten Rechte
	 * 
	 * @param authoritiesNotGranted
	 */
	public void setAuthoritiesNotGranted(List<Authority> authoritiesNotGranted)
	{
		this.authoritiesNotGranted = authoritiesNotGranted;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Authorities#getAuthorityNamesGranted()
	 */
	@Transient
	@Override
	public List<String> getAuthorityNamesGranted()
	{
		return createStringList(this.authoritiesGranted);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Authorities#getAuthorityNamesNotGranted()
	 */
	@Transient
	@Override
	public List<String> getAuthorityNamesNotGranted()
	{
		return createStringList(this.authoritiesNotGranted);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Authorities#grantAuthority(java.lang.String)
	 */
	@Transient
	@Override
	public boolean grantAuthority(String authorityName)
	{
		return moveAuthority(authorityName, this.authoritiesNotGranted, this.authoritiesGranted);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Authorities#withdrawAuthority(java.lang.String)
	 */
	@Transient
	@Override
	public boolean withdrawAuthority(String authorityName)
	{
		return !moveAuthority(authorityName, this.authoritiesGranted, this.authoritiesNotGranted);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.Authorities#isAuthorityGranted(java.lang.String)
	 */
	@Transient
	@Override
	public boolean isAuthorityGranted(String authorityName)
	{
		for(Authority a: this.authoritiesGranted)
		{
			if(a.getName().equals(authorityName))
				return true;
		}
		return false;
	}
	
	/**
	 * Erzeugt aus einer Liste von Authority eine String-Liste
	 * 
	 * @param authorities - die Authorities
	 * @return die String-Liste
	 */
	@Transient
	private List<String> createStringList(List<Authority> authorities)
	{
		List<String> result = new ArrayList<String>(authorities.size());
		for(Authority a: authorities)
		{
			result.add(a.getName());
		}
		return result;
	}
	
	/**
	 * Entfernt eine Authority aus der einen Liste und fügt sie in die andere ein.
	 * 
	 * @param authorityName - der Name der zu wechselnden Authority
	 * @param fromList - die Liste aus der die Authority zu löschen ist
	 * @param toList - die Liste in die die Authority einzufügen ist 
	 * @return war die Authority in der fromList enthalten
	 */
	@Transient
	private synchronized boolean moveAuthority(String authorityName, List<Authority> fromList, List<Authority> toList)
	{
		for(int i = 0; i < fromList.size(); i++)
		{
			if(fromList.get(i).getName().equals(authorityName))
			{
				toList.add(fromList.remove(i));
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}
}

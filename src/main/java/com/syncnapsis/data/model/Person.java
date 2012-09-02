package com.syncnapsis.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.syncnapsis.data.model.base.BaseObject;

/**
 * A Person-POJO
 * 
 * @author ultimate
 */
@Entity
public class Person extends BaseObject<Long>
{
	/**
	 * The firstName
	 */
	private String	firstName;
	/**
	 * The lastName
	 */
	private String	lastName;

	/**
	 * The firstName
	 * 
	 * @return firstName
	 */
	@Column
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * The firstName
	 * 
	 * @param firstName - the firstName
	 */
	@Column
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	/**
	 * The lastName
	 * 
	 * @return lastName
	 */
	@Column
	public String getLastName()
	{
		return lastName;
	}
	
	/**
	 * The lastName
	 * 
	 * @param lastName - the lastName
	 */
	@Column
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
}

package com.syncnapsis.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.syncnapsis.data.model.base.BaseObject;

/**
 * Model-Klasse "Messenger-Kontakt/Adresse"
 * Relation zwischen User und Messenger, die die Messenger-Adresse/ID enthält.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "messengercontact")
public class MessengerContact extends BaseObject<Long>
{
	/**
	 * Die Adresse/ID
	 */
	protected String		address;

	/**
	 * Der Benutzer zu dem die Adresse/ID gehört
	 */
	protected User		user;
	/**
	 * Der Messenger zu dem die Adresse/ID gehört
	 */
	protected Messenger	messenger;

	/**
	 * Leerer Standard Constructor
	 */
	public MessengerContact()
	{
	}

	/**
	 * Die Adresse/ID
	 * 
	 * @return address
	 */
	@Column(nullable = true, length = LENGTH_EMAIL)
	public String getAddress()
	{
		return address;
	}

	/**
	 * Der Benutzer zu dem die Adresse/ID gehört
	 * 
	 * @return user
	 */
	@ManyToOne
	@JoinColumn(name = "fkUser", nullable = false)
	public User getUser()
	{
		return user;
	}

	/**
	 * Der Messenger zu dem die Adresse/ID gehört
	 * 
	 * @return messenger
	 */
	@ManyToOne
	@JoinColumn(name = "fkMessenger", nullable = false)
	public Messenger getMessenger()
	{
		return messenger;
	}

	/**
	 * Die Adresse/ID
	 * 
	 * @param address - die Adresse/ID
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * Der Benutzer zu dem die Adresse/ID gehört
	 * 
	 * @param user - der Benutzer
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

	/**
	 * Der Messenger zu dem die Adresse/ID gehört
	 * 
	 * @param messenger - der Messenger
	 */
	public void setMessenger(Messenger messenger)
	{
		this.messenger = messenger;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(!(obj instanceof MessengerContact))
			return false;
		MessengerContact other = (MessengerContact) obj;
		if(address == null)
		{
			if(other.address != null)
				return false;
		}
		else if(!address.equals(other.address))
			return false;
		if(messenger == null)
		{
			if(other.messenger != null)
				return false;
		}
		else if(!messenger.getId().equals(other.messenger.getId()))
			return false;
		if(user == null)
		{
			if(other.user != null)
				return false;
		}
		else if(!user.getId().equals(other.user.getId()))
			return false;
		return true;
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((messenger == null) ? 0 : messenger.getId().hashCode());
		result = prime * result + ((user == null) ? 0 : user.getId().hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("version", version).append("messenger", messenger.getId()).append("user", user.getId()).append("address",
				address);
		return builder.toString();
	}
}

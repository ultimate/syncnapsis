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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.enums.EnumNewsAge;
import com.syncnapsis.enums.EnumNewsType;

/**
 * Model-Klasse "Neuigkeit/Nachricht"
 * Neuigkeiten/Nachrichten werden von Moderatoren oder Admins erstellt und sind
 * für alle Benutzer/Spieler sichtbar. Damit die Nachrichten in mehreren
 * Sprachen erfasst werden können gibt es zusätzlich zur ID eine NewsID, welche
 * unter alle sprachvarianten einer Nachricht gleich ist, um deren
 * Zusammengehörigkeit zu definieren.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "news")
public class News extends BaseObject<Long>
{
	/**
	 * Die NewsID für die Definition der Zusammengehörigkeit mehrere
	 * sprachvarianten einer Nachricht.
	 */
	private String			newsId;
	/**
	 * Die Sprache in der diese sprachvariante der Nachricht verfasst ist.
	 */
	private EnumLocale		locale;
	/**
	 * Das maximale Alter, mit dem diese Nachricht noch angezeigt werden soll.
	 * Wichtigere Nachrichten werden nach ihrem Erscheinen noch länger
	 * angezeigt.
	 */
	private EnumNewsAge		maxAge;
	/**
	 * Typ der Nachricht
	 * TODO make that a database type (for generic extensibility)
	 */
	private EnumNewsType	type;
	/**
	 * Ist die Nachricht von hoher Priorität (z.B. Server-Shutdown)
	 */
	private boolean			highPriority;
	/**
	 * Zu welchem Zeitpunkt soll die Nachricht erscheinen
	 */
	private Date			date;
	/**
	 * Titel der Nachricht
	 */
	private String			title;
	/**
	 * Text der Nachricht
	 */
	private String			text;
	/**
	 * Author der die Nachricht verfasst hat.
	 */
	private User			author;

	/**
	 * Leerer Standard Constructor
	 */
	public News()
	{
	}

	/**
	 * Die NewsID für die Definition der Zusammengehörigkeit mehrere
	 * sprachvarianten einer Nachricht.
	 * 
	 * @return newsId
	 */
	@Column(nullable = false, length = LENGTH_ID)
	public String getNewsId()
	{
		return newsId;
	}

	/**
	 * Die Sprache in der diese sprachvariante der Nachricht verfasst ist.
	 * 
	 * @return locale
	 */
	@Column(nullable = false, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumLocale getLocale()
	{
		return locale;
	}

	/**
	 * Das maximale Alter, mit dem diese Nachricht noch angezeigt werden soll.
	 * Wichtigere Nachrichten werden nach ihrem Erscheinen noch länger
	 * angezeigt.
	 * 
	 * @return maxAge
	 */
	@Column(nullable = false, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumNewsAge getMaxAge()
	{
		return maxAge;
	}

	/**
	 * Typ der Nachricht
	 * 
	 * @return type
	 */
	@Column(nullable = false, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumNewsType getType()
	{
		return type;
	}

	/**
	 * Ist die Nachricht von hoher Priorität (z.B. Server-Shutdown)
	 * 
	 * @return highPriority
	 */
	@Column(nullable = false)
	public boolean isHighPriority()
	{
		return highPriority;
	}

	/**
	 * Zu welchem Zeitpunkt soll die Nachricht erscheinen
	 * 
	 * @return date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getDate()
	{
		return date;
	}

	/**
	 * Titel der Nachricht
	 * 
	 * @return title
	 */
	@Column(nullable = false, length = LENGTH_TITLE)
	public String getTitle()
	{
		return title;
	}

	/**
	 * Text der Nachricht
	 * 
	 * @return text
	 */
	@Column(nullable = false, length = LENGTH_TEXT)
	public String getText()
	{
		return text;
	}

	/**
	 * Author der die Nachricht verfasst hat.
	 * 
	 * @return author
	 */
	@ManyToOne
	@JoinColumn(name = "fkUser", nullable = false)
	public User getAuthor()
	{
		return author;
	}

	/**
	 * Die NewsId für die Definition der Zusammengehörigkeit mehrere
	 * sprachvarianten einer Nachricht.
	 * 
	 * @param newsId - die NewsID
	 */
	public void setNewsId(String newsId)
	{
		this.newsId = newsId;
	}

	/**
	 * Die Sprache in der diese sprachvariante der Nachricht verfasst ist.
	 * 
	 * @param locale - die Sprache
	 */
	public void setLocale(EnumLocale locale)
	{
		this.locale = locale;
	}

	/**
	 * Das maximale Alter, mit dem diese Nachricht noch angezeigt werden soll.
	 * Wichtigere Nachrichten werden nach ihrem Erscheinen noch länger
	 * angezeigt.
	 * 
	 * @param maxAge - das maximale Alter
	 */
	public void setMaxAge(EnumNewsAge maxAge)
	{
		this.maxAge = maxAge;
	}

	/**
	 * Typ der Nachricht
	 * 
	 * @param type - der Typ
	 */
	public void setType(EnumNewsType type)
	{
		this.type = type;
	}

	/**
	 * Ist die Nachricht von hoher Priorität (z.B. Server-Shutdown)
	 * 
	 * @param highPriority - true oder false
	 */
	public void setHighPriority(boolean highPriority)
	{
		this.highPriority = highPriority;
	}

	/**
	 * Zu welchem Zeitpunkt soll die Nachricht erscheinen
	 * 
	 * @param date - das Datum
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * Titel der Nachricht
	 * 
	 * @param title - der Titel
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Text der Nachricht
	 * 
	 * @param text - der Text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Author der die Nachricht verfasst hat.
	 * 
	 * @param author - der Author
	 */
	public void setAuthor(User author)
	{
		this.author = author;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.model.base.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(!(obj instanceof News))
			return false;
		News other = (News) obj;
		if(author == null)
		{
			if(other.author != null)
				return false;
		}
		else if(!author.getId().equals(other.author.getId()))
			return false;
		if(date == null)
		{
			if(other.date != null)
				return false;
		}
		else if(!date.equals(other.date))
			return false;
		if(highPriority != other.highPriority)
			return false;
		if(locale == null)
		{
			if(other.locale != null)
				return false;
		}
		else if(!locale.equals(other.locale))
			return false;
		if(maxAge == null)
		{
			if(other.maxAge != null)
				return false;
		}
		else if(!maxAge.equals(other.maxAge))
			return false;
		if(newsId == null)
		{
			if(other.newsId != null)
				return false;
		}
		else if(!newsId.equals(other.newsId))
			return false;
		if(text == null)
		{
			if(other.text != null)
				return false;
		}
		else if(!text.equals(other.text))
			return false;
		if(title == null)
		{
			if(other.title != null)
				return false;
		}
		else if(!title.equals(other.title))
			return false;
		if(type == null)
		{
			if(other.type != null)
				return false;
		}
		else if(!type.equals(other.type))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.model.base.BaseObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((author == null) ? 0 : author.getId().hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (highPriority ? 1231 : 1237);
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((maxAge == null) ? 0 : maxAge.hashCode());
		result = prime * result + ((newsId == null) ? 0 : newsId.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		builder.append("id", id).append("version", version)
		.append("author", author.getId())
		.append("date", date)
				.append("highPriority", highPriority).append("locale", locale).append("maxAge", maxAge).append("newsId", newsId).append("text", text)
				.append("title", title).append("type", type);
		return builder.toString();
	}
}

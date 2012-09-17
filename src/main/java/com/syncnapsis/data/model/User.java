package com.syncnapsis.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Formula;
import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.enums.EnumAccountStatus;
import com.syncnapsis.enums.EnumDateFormat;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.enums.EnumGender;

/**
 * Model-Klasse "Benutzer"
 * Um am Spiel teilzunehmen muss man sich als Benutzer für die Anwendung
 * registrieren. Über die dem Benutzerkonto zugewiesene Benutzerrolle werden
 * dann alle Zugriffsrechte, sowie Beschränkungen in der Anwendung geregelt.
 * Darüber hinaus enthält das User-Objekt die meisten wichtigen oder unwichtigen
 * Informationen über den Benutzer (wie z.B. Spitzname, Beschreibung,
 * Geburtsdatum, etc.) Dabei sind die meisten Angaben jedoch freiwillig.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "app_user")
public class User extends ActivatableInstance<Long>
{
	/**
	 * Benutzername:
	 * Dieser Name muss einmalig ohne Berücksichtigung der Groß- und
	 * Kleinschreibung sein!
	 */
	protected String					username;
	/**
	 * E-Mail-Adresse:
	 * Muss einmalig sein. Kann i.d.R. nicht geändert werden.
	 */
	protected String					email;

	/**
	 * Passwort (wird verschlüsselt in der DB gespeichert)
	 */
	protected String					password;

	/**
	 * Benutzerrolle
	 */
	protected UserRole					role;
	/**
	 * Datum, zu dem die Benutzerrolle für diesen Benutzer/Spieler abläuft
	 * Wenn die Benutzerrolle abgelaufen ist, wird der Spieler wieder zum
	 * 'NORMAL_USER'
	 */
	protected Date						roleExpireDate;

	/**
	 * Datum der Registrierung
	 */
	protected Date						registrationDate;
	/**
	 * Datum der Löschung des Kontos
	 */
	protected Date						deleteDate;
	/**
	 * Aktueller Kontostatus
	 */
	protected EnumAccountStatus			accountStatus;
	/**
	 * Datum, zu dem der Kontostatus abläuft
	 * Wenn der Kontostatus abgelaufen ist, wird der Kontostatus wieder auf
	 * 'active' gesetzt.
	 */
	protected Date						accountStatusExpireDate;

	/**
	 * Optionale Spielerinformation: Spitzname
	 */
	protected String					nickname;
	/**
	 * Optionale Benutzerinformation: Titel
	 */
	protected String					title;
	/**
	 * Optionale Benutzerinformation: Geburtstag
	 */
	protected Date						birthday;
	/**
	 * Optionale Benutzerinformation: Alter (automatisch aus Geburtsdatum
	 * berechnet)
	 */
	protected Integer					age;
	/**
	 * Optionale Benutzerinformation: Wohnort
	 */
	protected String					city;
	/**
	 * Optionale Benutzerinformation: Geschlecht
	 */
	protected EnumGender				gender;
	/**
	 * Optionale Benutzerinformation: Beschreibung
	 */
	protected String					description;
	/**
	 * Optionale Benutzerinformation: Bild-URL
	 */
	protected String					imageURL;

	/**
	 * Spieleinstellung: Sprache
	 */
	protected EnumLocale				locale;
	/**
	 * Spieleinstellung: Zeitzone (zur Verwendung mit TimeZoneUtil)
	 */
	protected String					timeZoneID;
	/**
	 * Spieleinstellung: Datumsformat
	 */
	protected EnumDateFormat			dateFormat;

	/**
	 * Spieleinstellung: Werden erweiterte Menüs verwendet?
	 */
	protected boolean					usingAdvancedMenu;
	/**
	 * Spieleinstellung: Sollen spielerklärende Tooltips gezeigt werden?
	 */
	protected boolean					usingTooltips;
	/**
	 * Spieleinstellung: Soll die Session niemals ablaufen?
	 * (nach Ablauf der Session ist ein erneutes Einloggen notwendig.)
	 */
	protected boolean					usingInfiniteSession;
	/**
	 * Spieleinstellung: Nach welcher Zeit soll die Session ablaufen? (Sekunden)
	 * (nach Ablauf der Session ist ein erneutes Einloggen notwendig.)
	 */
	protected int						sessionTimeout;
	/**
	 * Datum der letzten Aktivität
	 */
	protected Date						lastActiveDate;

	/**
	 * Soll die e-Mail-Adresse angezeigt werden?
	 */
	protected boolean					showEmail;
	/**
	 * Liste der Messenger-Adressen
	 */
	protected List<MessengerContact>	messengerContacts;

	/**
	 * Liste der Freundschafts-/Kontaktzuordnungen (Teil1)
	 * (In dieser Liste ist der Benutzer 'user1')
	 */
	protected List<UserContact>			userContacts1;
	/**
	 * Liste der Freundschafts-/Kontaktzuordnungen (Teil2)
	 * (In dieser Liste ist der Benutzer 'user2')
	 */
	protected List<UserContact>			userContacts2;
	/**
	 * Temporäre zusammengesetzte Liste aus userContacts1 & userContacts2
	 */
	protected List<UserContact>			userContacts;

	/**
	 * Leerer Standard Constructor
	 */
	public User()
	{
	}

	/**
	 * Benutzername:
	 * Dieser Name muss einmalig ohne Berücksichtigung der Groß- und
	 * Kleinschreibung sein!
	 * 
	 * @return username
	 */
	@Column(nullable = false, unique = true, length = LENGTH_NAME_NORMAL)
	public String getUsername()
	{
		return username;
	}

	/**
	 * E-Mail-Adresse:
	 * Muss einmalig sein. Kann i.d.R. nicht geändert werden.
	 * 
	 * @return email
	 */
	@Column(nullable = false, unique = true, length = LENGTH_EMAIL)
	public String getEmail()
	{
		return email;
	}

	/**
	 * Passwort (wird verschlüsselt in der DB gespeichert)
	 * 
	 * @return password
	 */
	@Column(nullable = false, length = LENGTH_PASSWORD)
	public String getPassword()
	{
		return password;
	}

	/**
	 * Benutzerrolle
	 * 
	 * @return role
	 */
	@ManyToOne
	@JoinColumn(name = "fkUserRole", nullable = false)
	public UserRole getRole()
	{
		return role;
	}

	/**
	 * Datum, zu dem die Benutzerrolle für diesen Benutzer abläuft
	 * Wenn die Benutzerrolle abgelaufen ist, wird der Benutzer wieder zum
	 * 'NORMAL_USER'
	 * 
	 * @return roleExpireDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getRoleExpireDate()
	{
		return roleExpireDate;
	}

	/**
	 * Datum der Registrierung
	 * 
	 * @return registrationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getRegistrationDate()
	{
		return registrationDate;
	}

	/**
	 * Datum der Löschung des Kontos
	 * 
	 * @return deleteDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getDeleteDate()
	{
		return deleteDate;
	}

	/**
	 * Aktueller Kontostatus
	 * 
	 * @return accountStatus
	 */
	@Column(nullable = true, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumAccountStatus getAccountStatus()
	{
		return accountStatus;
	}

	/**
	 * Datum, zu dem der Kontostatus abläuft
	 * Wenn der Kontostatus abgelaufen ist, wird der Kontostatus wieder auf
	 * 'active' gesetzt.
	 * 
	 * @return accountStatusExpireDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getAccountStatusExpireDate()
	{
		return accountStatusExpireDate;
	}

	/**
	 * Optionale Benutzerinformation: Spitzname
	 * 
	 * @return nickname
	 */
	@Column(nullable = true, length = LENGTH_NAME_LONG)
	public String getNickname()
	{
		return nickname;
	}

	/**
	 * Optionale Benutzerinformation: Titel
	 * 
	 * @return title
	 */
	@Column(nullable = true, length = LENGTH_NAME_NORMAL)
	public String getTitle()
	{
		return title;
	}

	/**
	 * Optionale Benutzerinformation: Geburtstag
	 * 
	 * @return birthday
	 */
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	public Date getBirthday()
	{
		return birthday;
	}

	/**
	 * Optionale Benutzerinformation: Alter (automatisch aus Geburtsdatum
	 * berechnet)
	 * 
	 * @return age
	 */
	@Formula("extract(year from age(birthday))")
	public Integer getAge()
	{
		return age;
	}

	/**
	 * Optionale Benutzerinformation: Wohnort
	 * 
	 * @return city
	 */
	@Column(nullable = true, length = LENGTH_NAME_LONG)
	public String getCity()
	{
		return city;
	}

	/**
	 * Optionale Benutzerinformation: Geschlecht
	 * 
	 * @return gender
	 */
	@Column(nullable = true, length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumGender getGender()
	{
		return gender;
	}

	/**
	 * Optionale Benutzerinformation: Beschreibung
	 * 
	 * @return description
	 */
	@Column(nullable = true, length = LENGTH_DESCRIPTION)
	public String getDescription()
	{
		return description;
	}

	/**
	 * Optionale Benutzerinformation: Bild-URL
	 * 
	 * @return imageURL
	 */
	@Column(nullable = true, length = LENGTH_URL)
	public String getImageURL()
	{
		return imageURL;
	}

	/**
	 * Spieleinstellung: Sprache
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
	 * Spieleinstellung: Zeitzone (zur Verwendung mit TimeZoneUtil)
	 * 
	 * @return timeZoneID
	 */
	@Column(nullable = false, length = LENGTH_ID)
	public String getTimeZoneID()
	{
		return timeZoneID;
	}

	/**
	 * Spieleinstellung: Datumsformat
	 * 
	 * @return dateFormat
	 */
	@Column(length = LENGTH_ENUM)
	@Enumerated(value = EnumType.STRING)
	public EnumDateFormat getDateFormat()
	{
		return dateFormat;
	}

	/**
	 * Spieleinstellung: Werden erweiterte Menüs verwendet?
	 * 
	 * @return usingAdvancedMenu
	 */
	@Column(nullable = false)
	public boolean isUsingAdvancedMenu()
	{
		return usingAdvancedMenu;
	}

	/**
	 * Spieleinstellung: Sollen spielerklärende Tooltips gezeigt werden?
	 * 
	 * @return usingTooltips
	 */
	@Column(nullable = false)
	public boolean isUsingTooltips()
	{
		return usingTooltips;
	}

	/**
	 * Spieleinstellung: Soll die Session niemals ablaufen?
	 * (nach Ablauf der Session ist ein erneutes Einloggen notwendig.)
	 * 
	 * @return usingInfiniteSession
	 */
	@Column(nullable = false)
	public boolean isUsingInfiniteSession()
	{
		return usingInfiniteSession;
	}

	/**
	 * Spieleinstellung: Nach welcher Zeit soll die Session ablaufen? (Minuten)
	 * (nach Ablauf der Session ist ein erneutes Einloggen notwendig.)
	 * 
	 * @return sessionTimeout
	 */
	@Column(nullable = false)
	public int getSessionTimeout()
	{
		return sessionTimeout;
	}

	/**
	 * Datum der letzten Aktivität
	 * 
	 * @return lastActiveDate
	 */
	@Column(nullable = false)
	public Date getLastActiveDate()
	{
		return lastActiveDate;
	}

	/**
	 * Soll die e-Mail-Adresse angezeigt werden?
	 * 
	 * @return showEmail
	 */
	@Column(nullable = false)
	public boolean isShowEmail()
	{
		return showEmail;
	}

	/**
	 * Liste der Messenger-Adressen
	 * 
	 * @return messengerContacts
	 */
	@OneToMany(mappedBy = "user")
	public List<MessengerContact> getMessengerContacts()
	{
		return messengerContacts;
	}

	/**
	 * Liste der Freundschafts-/Kontaktzuordnungen (Teil1)
	 * (In dieser Liste ist der Benutzer 'user1')
	 * 
	 * @return userContacts1
	 */
	@OneToMany(mappedBy = "user1")
	public List<UserContact> getUserContacts1()
	{
		return userContacts1;
	}

	/**
	 * Liste der Freundschafts-/Kontaktzuordnungen (Teil2)
	 * (In dieser Liste ist der Benutzer 'user2')
	 * 
	 * @return userContacts2
	 */
	@OneToMany(mappedBy = "user2")
	public List<UserContact> getUserContacts2()
	{
		return userContacts2;
	}

	/**
	 * Temporäre zusammengesetzte Liste aus userContacts1 & userContacts2
	 * 
	 * @return userContacts
	 */
	@Transient
	public List<UserContact> getUserContacts()
	{
		if(userContacts == null)
		{
			userContacts = new ArrayList<UserContact>();
			userContacts.addAll(userContacts1);
			userContacts.addAll(userContacts2);
		}
		return userContacts;
	}

	/**
	 * Benutzername:
	 * Dieser Name muss einmalig ohne Berücksichtigung der Groß- und
	 * Kleinschreibung sein!
	 * 
	 * @param username - der Benutzername
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * E-Mail-Adresse:
	 * Muss einmalig sein. Kann i.d.R. nicht geändert werden.
	 * 
	 * @param email - die e-Mail-Adresse
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Passwort (wird verschlüsselt in der DB gespeichert)
	 * 
	 * @param password - das Passwort (verschlüsselt)
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Benutzerrolle
	 * 
	 * @param role - die Benutzerrolle
	 */
	public void setRole(UserRole role)
	{
		this.role = role;
	}

	/**
	 * Datum, zu dem die Benutzerrolle für diesen Benutzer abläuft
	 * Wenn die Benutzerrolle abgelaufen ist, wird der Benutzer wieder zum
	 * 'NORMAL_USER'
	 * 
	 * @param roleExpireDate - das Datum
	 */
	public void setRoleExpireDate(Date roleExpireDate)
	{
		this.roleExpireDate = roleExpireDate;
	}

	/**
	 * Datum der Registrierung
	 * 
	 * @param registrationDate - das Datum
	 */
	public void setRegistrationDate(Date registrationDate)
	{
		this.registrationDate = registrationDate;
	}

	/**
	 * Datum der Löschung des Kontos
	 * 
	 * @param deleteDate - das Datum
	 */
	public void setDeleteDate(Date deleteDate)
	{
		this.deleteDate = deleteDate;
	}

	/**
	 * Aktueller Kontostatus
	 * 
	 * @param accountStatus - der Kontostatus
	 */
	public void setAccountStatus(EnumAccountStatus accountStatus)
	{
		this.accountStatus = accountStatus;
	}

	/**
	 * Datum, zu dem der Kontostatus abläuft
	 * Wenn der Kontostatus abgelaufen ist, wird der Kontostatus wieder auf
	 * 'active' gesetzt.
	 * 
	 * @param accountStatusExpireDate - das Datum
	 */
	public void setAccountStatusExpireDate(Date accountStatusExpireDate)
	{
		this.accountStatusExpireDate = accountStatusExpireDate;
	}

	/**
	 * Optionale Benutzerinformation: Spitzname
	 * 
	 * @param nickname - der Spitzname
	 */
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	/**
	 * Optionale Benutzerinformation: Titel
	 * 
	 * @param title - der Titel
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Optionale Benutzerinformation: Geburtstag
	 * 
	 * @param birthday - das Datum
	 */
	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * Optionale Benutzerinformation: Alter (automatisch aus Geburtsdatum
	 * berechnet)
	 * Da dieser Wert automatisch berechnet wird, hat ein setzen des Alters
	 * keine Einfluss auf das Geburtsdatum!
	 * 
	 * @param age - das Alter
	 */
	public void setAge(Integer age)
	{
		this.age = age;
	}

	/**
	 * Optionale Benutzerinformation: Wohnort
	 * 
	 * @param city - die Stadt
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * Optionale Benutzerinformation: Geschlecht
	 * 
	 * @param gender - das Geschlecht
	 */
	public void setGender(EnumGender gender)
	{
		this.gender = gender;
	}

	/**
	 * Optionale Benutzerinformation: Beschreibung
	 * 
	 * @param description - die Beschreibung
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Optionale Benutzerinformation: Bild-URL
	 * 
	 * @param imageURL - die URL
	 */
	public void setImageURL(String imageURL)
	{
		this.imageURL = imageURL;
	}

	/**
	 * Spieleinstellung: Sprache
	 * 
	 * @param locale - die Sprache
	 */
	public void setLocale(EnumLocale locale)
	{
		this.locale = locale;
	}

	/**
	 * Spieleinstellung: Zeitzone (zur Verwendung mit TimeZoneUtil)
	 * 
	 * @param timeZoneID - die Zeitzone
	 */
	public void setTimeZoneID(String timeZoneID)
	{
		this.timeZoneID = timeZoneID;
	}

	/**
	 * Spieleinstellung: Datumsformat
	 * 
	 * @param dateFormat - das Format
	 */
	public void setDateFormat(EnumDateFormat dateFormat)
	{
		this.dateFormat = dateFormat;
	}

	/**
	 * Spieleinstellung: Werden erweiterte Menüs verwendet?
	 * 
	 * @param usingAdvancedMenu - true oder false
	 */
	public void setUsingAdvancedMenu(boolean usingAdvancedMenu)
	{
		this.usingAdvancedMenu = usingAdvancedMenu;
	}

	/**
	 * Spieleinstellung: Sollen spielerklärende Tooltips gezeigt werden?
	 * 
	 * @param usingTooltips - true oder false
	 */
	public void setUsingTooltips(boolean usingTooltips)
	{
		this.usingTooltips = usingTooltips;
	}

	/**
	 * Spieleinstellung: Soll die Session niemals ablaufen?
	 * (nach Ablauf der Session ist ein erneutes Einloggen notwendig.)
	 * 
	 * @param usingInfiniteSession - true oder false
	 */
	public void setUsingInfiniteSession(boolean usingInfiniteSession)
	{
		this.usingInfiniteSession = usingInfiniteSession;
	}

	/**
	 * Spieleinstellung: Nach welcher Zeit soll die Session ablaufen? (Minuten)
	 * (nach Ablauf der Session ist ein erneutes Einloggen notwendig.)
	 * 
	 * @param sessionTimeout - die Zeit
	 */
	public void setSessionTimeout(int sessionTimeout)
	{
		this.sessionTimeout = sessionTimeout;
	}

	/**
	 * Datum der letzten Aktivität
	 * 
	 * @param lastActiveDate - das Datum
	 */
	public void setLastActiveDate(Date lastActiveDate)
	{
		this.lastActiveDate = lastActiveDate;
	}

	/**
	 * Soll die e-Mail-Adresse angezeigt werden?
	 * 
	 * @param showEmail - true oder false
	 */
	public void setShowEmail(boolean showEmail)
	{
		this.showEmail = showEmail;
	}

	/**
	 * Liste der Messenger-Adressen
	 * 
	 * @param messengerContacts - die Liste
	 */
	public void setMessengerContacts(List<MessengerContact> messengerContacts)
	{
		this.messengerContacts = messengerContacts;
	}

	/**
	 * Liste der Freundschafts-/Kontaktzuordnungen (Teil1)
	 * (In dieser Liste ist der Benutzer 'user1')
	 * 
	 * @param userContacts1 - die Liste
	 */
	public void setUserContacts1(List<UserContact> userContacts1)
	{
		this.userContacts1 = userContacts1;
	}

	/**
	 * Liste der Freundschafts-/Kontaktzuordnungen (Teil2)
	 * (In dieser Liste ist der Benutzer 'user2')
	 * 
	 * @param userContacts2 - die Liste
	 */
	public void setUserContacts2(List<UserContact> userContacts2)
	{
		this.userContacts2 = userContacts2;
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
		if(!(obj instanceof User))
			return false;
		User other = (User) obj;
		if(accountStatus == null)
		{
			if(other.accountStatus != null)
				return false;
		}
		else if(!accountStatus.equals(other.accountStatus))
			return false;
		if(accountStatusExpireDate == null)
		{
			if(other.accountStatusExpireDate != null)
				return false;
		}
		else if(!accountStatusExpireDate.equals(other.accountStatusExpireDate))
			return false;
		if(activated != other.activated)
			return false;
		if(age == null)
		{
			if(other.age != null)
				return false;
		}
		else if(!age.equals(other.age))
			return false;
		if(birthday == null)
		{
			if(other.birthday != null)
				return false;
		}
		else if(!birthday.equals(other.birthday))
			return false;
		if(city == null)
		{
			if(other.city != null)
				return false;
		}
		else if(!city.equals(other.city))
			return false;
		if(dateFormat == null)
		{
			if(other.dateFormat != null)
				return false;
		}
		else if(!dateFormat.equals(other.dateFormat))
			return false;
		if(deleteDate == null)
		{
			if(other.deleteDate != null)
				return false;
		}
		else if(!deleteDate.equals(other.deleteDate))
			return false;
		if(description == null)
		{
			if(other.description != null)
				return false;
		}
		else if(!description.equals(other.description))
			return false;
		if(email == null)
		{
			if(other.email != null)
				return false;
		}
		else if(!email.equals(other.email))
			return false;
		if(imageURL == null)
		{
			if(other.imageURL != null)
				return false;
		}
		else if(!imageURL.equals(other.imageURL))
			return false;
		if(lastActiveDate == null)
		{
			if(other.lastActiveDate != null)
				return false;
		}
		else if(!lastActiveDate.equals(other.lastActiveDate))
			return false;
		if(locale == null)
		{
			if(other.locale != null)
				return false;
		}
		else if(!locale.equals(other.locale))
			return false;
		if(nickname == null)
		{
			if(other.nickname != null)
				return false;
		}
		else if(!nickname.equals(other.nickname))
			return false;
		if(password == null)
		{
			if(other.password != null)
				return false;
		}
		else if(!password.equals(other.password))
			return false;
		if(registrationDate == null)
		{
			if(other.registrationDate != null)
				return false;
		}
		else if(!registrationDate.equals(other.registrationDate))
			return false;
		if(role == null)
		{
			if(other.role != null)
				return false;
		}
		else if(!role.getId().equals(other.role.getId()))
			return false;
		if(roleExpireDate == null)
		{
			if(other.roleExpireDate != null)
				return false;
		}
		else if(!roleExpireDate.equals(other.roleExpireDate))
			return false;
		if(sessionTimeout != other.sessionTimeout)
			return false;
		if(gender == null)
		{
			if(other.gender != null)
				return false;
		}
		else if(!gender.equals(other.gender))
			return false;
		if(showEmail != other.showEmail)
			return false;
		if(timeZoneID == null)
		{
			if(other.timeZoneID != null)
				return false;
		}
		else if(!timeZoneID.equals(other.timeZoneID))
			return false;
		if(title == null)
		{
			if(other.title != null)
				return false;
		}
		else if(!title.equals(other.title))
			return false;
		if(username == null)
		{
			if(other.username != null)
				return false;
		}
		else if(!username.equals(other.username))
			return false;
		if(usingAdvancedMenu != other.usingAdvancedMenu)
			return false;
		if(usingInfiniteSession != other.usingInfiniteSession)
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
		result = prime * result + ((accountStatus == null) ? 0 : accountStatus.hashCode());
		result = prime * result + ((accountStatusExpireDate == null) ? 0 : accountStatusExpireDate.hashCode());
		result = prime * result + (activated ? 1231 : 1237);
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((dateFormat == null) ? 0 : dateFormat.hashCode());
		result = prime * result + ((deleteDate == null) ? 0 : deleteDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + ((lastActiveDate == null) ? 0 : lastActiveDate.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((roleExpireDate == null) ? 0 : roleExpireDate.hashCode());
		result = prime * result + sessionTimeout;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + (showEmail ? 1231 : 1237);
		result = prime * result + ((timeZoneID == null) ? 0 : timeZoneID.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + (usingAdvancedMenu ? 1231 : 1237);
		result = prime * result + (usingInfiniteSession ? 1231 : 1237);
		result = prime * result + (usingTooltips ? 1231 : 1237);
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
		builder.append("id", id).append("version", version).append("activated", activated).append("username", username)
				.append("accountStatus", accountStatus).append("accountStatusExpireDate", accountStatusExpireDate).append("age", age)
				.append("birthday", birthday).append("city", city).append("dateFormat", dateFormat).append("deleteDate", deleteDate)
				.append("description", description).append("email", email).append("imageURL", imageURL).append("lastActiveDate", lastActiveDate)
				.append("locale", locale).append("nickname", nickname).append("registrationDate", registrationDate)
				.append("role", role.getRolename()).append("roleExpireDate", roleExpireDate).append("sessionTimeout", sessionTimeout)
				.append("gender", gender).append("showEmail", showEmail).append("timeZoneID", timeZoneID).append("title", title)
				.append("usingAdvancedMenu", usingAdvancedMenu).append("usingInfiniteSession", usingInfiniteSession)
				.append("usingTooltips", usingTooltips);
		return builder.toString();
	}
}

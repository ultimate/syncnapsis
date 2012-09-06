package com.syncnapsis.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.syncnapsis.data.model.base.BaseObject;

/**
 * Model-Klasse "Action für die GUI"
 * Actions sind durch eine Aktion des Benutzers (Klick, etc.) ausgelöste
 * Aktionen auf dem Server. Es wird dabei zwischen Actions unterschieden, die
 * ein Fenster öffnen, und solchen, die dies nicht tun. Bei Actions, die ein
 * Fenster öffnen steht das Attribut "action" für den Name der im Fenster
 * anzuzeigenden Seite. Bei Actions, die kein Fenster öffnen, ist dies ein
 * Methodenname in der Klasse com.syncnapsis.ui.Actions
 * 
 * @author ultimate
 */
@Entity
@Table(name = "guiaction")
public class GUIAction extends BaseObject<Long>
{
	/**
	 * Name der GUIAction
	 * Die Action ist dabei entweder der Name einer zul-Seite oder eine Methode
	 * der Klasse Actions. Im ersten Fall wird bei Ausführung automatisch ein
	 * Fenster mit der entsprechenden Seite als Inhalt angezeigt.
	 */
	private String			action;
	/**
	 * Optionale Id des zu öffnenen Fensters. Existiert bereits ein Fenster mit
	 * dieser Id, wird dieses aktualisiert, anstatt ein neues Fenster zu öffnen.
	 */
	private String			windowId;
	/**
	 * Schlüssel für die sprachabhängige Beschriftung im Titel des Fensters
	 */
	private String			titleKey;

	/**
	 * Öffnet diese GUIAction ein Fenster?
	 */
	private boolean			windowAction;
	/**
	 * Optionale GUIWindowParams für die Übergabe an das zu erstellende Fenster
	 */
	private GUIWindowParams	windowParams;

	/**
	 * Leerer Standard Constructor
	 */
	public GUIAction()
	{
	}

	/**
	 * Name der GUIAction
	 * Die Action ist dabei entweder der Name einer zul-Seite oder eine Methode
	 * der Klasse Actions. Im ersten Fall wird bei Ausführung automatisch ein
	 * Fenster mit der entsprechenden Seite als Inhalt angezeigt.
	 * 
	 * @return action
	 */
	@Column(nullable = false, unique = true, length = LENGTH_ACTION)
	public String getAction()
	{
		return action;
	}

	/**
	 * Optionale Id des zu öffnenen Fensters. Existiert bereits ein Fenster mit
	 * dieser Id, wird dieses aktualisiert, anstatt ein neues Fenster zu öffnen.
	 * 
	 * @return windowId
	 */
	@Column(nullable = true, length = LENGTH_ID)
	public String getWindowId()
	{
		return windowId;
	}

	/**
	 * Schlüssel für die sprachabhängige Beschriftung im Titel des Fensters
	 * 
	 * @return titleKey
	 */
	@Column(nullable = true, length = LENGTH_LANGUAGE_KEY)
	public String getTitleKey()
	{
		return titleKey;
	}

	/**
	 * Öffnet diese GUIAction ein Fenster?
	 * 
	 * @return windowAction
	 */
	@Column(nullable = false)
	public boolean isWindowAction()
	{
		return windowAction;
	}

	/**
	 * Optionale GUIWindowParams für die Übergabe an das zu erstellende Fenster
	 * 
	 * @return windowParams
	 */
	@ManyToOne
	@JoinColumn(name = "fkWindowParams", nullable = true)
	public GUIWindowParams getWindowParams()
	{
		return windowParams;
	}

	/**
	 * Name der GUIAction
	 * Die Action ist dabei entweder der Name einer zul-Seite oder eine Methode
	 * der Klasse Actions. Im ersten Fall wird bei Ausführung automatisch ein
	 * Fenster mit der entsprechenden Seite als Inhalt angezeigt.
	 * 
	 * @param action - der Name
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * Optionale ID des zu öffnenen Fensters. Existiert bereits ein Fenster mit
	 * dieser ID, wird dieses aktualisiert, anstatt ein neues Fenster zu öffnen.
	 * 
	 * @param windowId - die ID
	 */
	public void setWindowId(String windowId)
	{
		this.windowId = windowId;
	}

	/**
	 * Schlüssel für die sprachabhängige Beschriftung im Titel des Fensters
	 * 
	 * @param titleKey - der Schlüssel
	 */
	public void setTitleKey(String titleKey)
	{
		this.titleKey = titleKey;
	}

	/**
	 * Öffnet diese GUIAction ein Fenster?
	 * 
	 * @param windowAction - true oder false
	 */
	public void setWindowAction(boolean windowAction)
	{
		this.windowAction = windowAction;
	}

	/**
	 * Optionale GUIWindowParams für die Übergabe an das zu erstellende Fenster
	 * 
	 * @param windowParams - die Fenstereigenschaften
	 */
	public void setWindowParams(GUIWindowParams windowParams)
	{
		this.windowParams = windowParams;
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
		if(!(obj instanceof GUIAction))
			return false;
		GUIAction other = (GUIAction) obj;
		if(action == null)
		{
			if(other.action != null)
				return false;
		}
		else if(!action.equals(other.action))
			return false;
		if(titleKey == null)
		{
			if(other.titleKey != null)
				return false;
		}
		else if(!titleKey.equals(other.titleKey))
			return false;
		if(windowAction != other.windowAction)
			return false;
		if(windowId == null)
		{
			if(other.windowId != null)
				return false;
		}
		else if(!windowId.equals(other.windowId))
			return false;
		if(windowParams == null)
		{
			if(other.windowParams != null)
				return false;
		}
		else if(!windowParams.getId().equals(other.windowParams.getId()))
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
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((titleKey == null) ? 0 : titleKey.hashCode());
		result = prime * result + (windowAction ? 1231 : 1237);
		result = prime * result + ((windowId == null) ? 0 : windowId.hashCode());
		result = prime * result + ((windowParams == null) ? 0 : windowParams.getId().hashCode());
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
		builder.append("id", id).append("version", version).append("action", action).append("titleKey", titleKey)
				.append("windowAction", windowAction).append("windowId", windowId).append("windowParams",
						(windowParams == null ? null : windowParams.getId()));
		return builder.toString();
	}
}

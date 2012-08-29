package com.syncnapsis.data.model.help;

import com.syncnapsis.data.model.base.Model;

/**
 * Hilfsklasse zur Beschreibung einer auszuführenden GUIAction
 * 
 * @author ultimate
 */
public class GUIActionDescription implements Model
{
	/**
	 * Der Name der GUIAction
	 */
	private String	action;
	/**
	 * Der Schlüssel für den auf einem Button anzuzeigenden Text
	 */
	private String	titleKey;
	/**
	 * Das auf einem Button anzuzeigende Bild
	 */
	private String	image;
	/**
	 * Das auf einem Button anzuzeigende Hover-Bild
	 */
	private String	hoverImage;

	/**
	 * Leerer Standard-Constructor
	 */
	public GUIActionDescription()
	{
		super();
	}

	/**
	 * Constructor für den Aufruf mit allen Fields
	 * 
	 * @param action - Der Name der GUIAction
	 * @param titleKey - Der Schlüssel für den auf einem Button anzuzeigenden Text
	 * @param image - Das auf einem Button anzuzeigende Bild
	 * @param hoverImage - Das auf einem Button anzuzeigende Hover-Bild
	 */
	public GUIActionDescription(String action, String titleKey, String image, String hoverImage)
	{
		super();
		this.action = action;
		this.titleKey = titleKey;
		this.image = image;
		this.hoverImage = hoverImage;
	}

	/**
	 * Der Name der GUIAction
	 * 
	 * @return action
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * Der Schlüssel für den auf einem Button anzuzeigenden Text
	 * 
	 * @return titleKey
	 */
	public String getTitleKey()
	{
		return titleKey;
	}

	/**
	 * Das auf einem Button anzuzeigende Bild
	 * 
	 * @return image
	 */
	public String getImage()
	{
		return image;
	}

	/**
	 * Das auf einem Button anzuzeigende Hover-Bild
	 * 
	 * @return hoverImage
	 */
	public String getHoverImage()
	{
		return hoverImage;
	}

	/**
	 * Der Name der GUIAction
	 * 
	 * @param action - die Action
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * Der Schlüssel für den auf einem Button anzuzeigenden Text
	 * 
	 * @param titleKey - der Schlüssel
	 */
	public void setTitleKey(String titleKey)
	{
		this.titleKey = titleKey;
	}

	/**
	 * Das auf einem Button anzuzeigende Bild
	 * 
	 * @param image - das Bild
	 */
	public void setImage(String image)
	{
		this.image = image;
	}

	/**
	 * Das auf einem Button anzuzeigende Hover-Bild
	 * 
	 * @param hoverImage - das Hover-Bild
	 */
	public void setHoverImage(String hoverImage)
	{
		this.hoverImage = hoverImage;
	}
}

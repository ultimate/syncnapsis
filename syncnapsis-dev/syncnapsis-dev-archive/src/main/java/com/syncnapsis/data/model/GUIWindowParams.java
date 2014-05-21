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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.syncnapsis.data.model.base.BaseObject;

/**
 * Model-Klasse "Fenster-Parameter für die GUI"
 * Wenn ein neues Fenster erzeugt wird und für die aufgerufene GUIAction eine
 * GUIWindowParams-Objekt existiert, so werden die in dieser Klasse gesetzten
 * Attribute an das Fenster übergeben. Alle hier definierten Attribute sind an
 * GUIWindow orientiert.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "guiwindowparams")
// TODO javadoc
public class GUIWindowParams extends BaseObject<Long>
{
	/**
	 * @see GUIWindow#isClosable()
	 * @see GUIWindow#setClosable(boolean)
	 */
	private boolean	closable;
	/**
	 * @see GUIWindow#isMaximizable()
	 * @see GUIWindow#setMaximizable(boolean)
	 */
	private boolean	maximizable;
	/**
	 * @see GUIWindow#isMaximized()
	 * @see GUIWindow#setMaximized(boolean)
	 */
	private boolean	maximized;
	/**
	 * @see GUIWindow#isMinimizable()
	 * @see GUIWindow#setMinimizable(boolean)
	 */
	private boolean	minimizable;
	/**
	 * @see GUIWindow#isMinimized()
	 * @see GUIWindow#setMinimized(boolean)
	 */
	private boolean	minimized;
	/**
	 * @see GUIWindow#isCollapsible()
	 * @see GUIWindow#setCollapsible(boolean)
	 */
	private boolean	collapsible;
	/**
	 * @see GUIWindow#isOpen()
	 * @see GUIWindow#setOpen(boolean)
	 */
	private boolean	open;
	/**
	 * @see GUIWindow#isSizable()
	 * @see GUIWindow#setSizable(boolean)
	 */
	private boolean	sizable;
	/**
	 * @see GUIWindow#isVisible()
	 * @see GUIWindow#setVisible(boolean)
	 */
	private boolean	visible;

	/**
	 * @see GUIWindow#setDraggable(String)
	 */
	private String	draggable;
	/**
	 * @see GUIWindow#setDroppable(String)
	 */
	private String	droppable;

	/**
	 * @see GUIWindow#getPosition()
	 * @see GUIWindow#setPosition(String)
	 */
	private String	position;
	/**
	 * @see GUIWindow#getLeft()
	 * @see GUIWindow#setLeft(String)
	 */
	private String	left;
	/**
	 * @see GUIWindow#getTop()
	 * @see GUIWindow#setTop(String)
	 */
	private String	top;
	/**
	 * @see GUIWindow#getZindex()
	 * @see GUIWindow#setZindex(int)
	 */
	private int		zindex;

	/**
	 * @see GUIWindow#getHeight()
	 * @see GUIWindow#setHeight(String)
	 */
	private String	height;
	/**
	 * @see GUIWindow#getMinheight()
	 * @see GUIWindow#setMinheight(int)
	 */
	private int		minheight;

	/**
	 * @see GUIWindow#getWidth()
	 * @see GUIWindow#setWidth(String)
	 */
	private String	width;
	/**
	 * @see GUIWindow#getMinwidth()
	 * @see GUIWindow#setMinwidth(int)
	 */
	private int		minwidth;

	/**
	 * Leerer Standard Constructor
	 */
	public GUIWindowParams()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#isClosable()
	 */
	@Column(nullable = false)
	public boolean isClosable()
	{
		return closable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#isMaximizable()
	 */
	@Column(nullable = false)
	public boolean isMaximizable()
	{
		return maximizable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#isMaximized()
	 */
	@Column(nullable = false)
	public boolean isMaximized()
	{
		return maximized;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#isMinimizable()
	 */
	@Column(nullable = false)
	public boolean isMinimizable()
	{
		return minimizable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#isMinimized()
	 */
	@Column(nullable = false)
	public boolean isMinimized()
	{
		return minimized;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindowParams#isCollapsible()
	 */

	public boolean isCollapsible()
	{
		return collapsible;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindowParams#isOpen()
	 */

	public boolean isOpen()
	{
		return open;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#isSizable()
	 */
	@Column(nullable = false)
	public boolean isSizable()
	{
		return sizable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#isVisible()
	 */
	@Column(nullable = false)
	public boolean isVisible()
	{
		return visible;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#getDraggable()
	 */
	@Column(nullable = true, length = LENGTH_PARAMETERVALUE)
	public String getDraggable()
	{
		return draggable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#getDroppable()
	 */
	@Column(nullable = true, length = LENGTH_PARAMETERVALUE)
	public String getDroppable()
	{
		return droppable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#getPosition()
	 */
	@Column(nullable = true, length = LENGTH_PARAMETERVALUE)
	public String getPosition()
	{
		return position;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#getLeft()
	 */
	// left ist ein reserviertes Wort und darf nicht als Spalte vorkommen
	@Column(name = "positionLeft", nullable = false, length = LENGTH_PARAMETERVALUE)
	public String getLeft()
	{
		return left;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#getTop()
	 */
	// top wird analog zu left angepasst (damit es einheitlich ist)
	@Column(name = "positionTop", nullable = false, length = LENGTH_PARAMETERVALUE)
	public String getTop()
	{
		return top;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#getZindex()
	 */
	@Column(nullable = false)
	public int getZindex()
	{
		return zindex;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#getHeight()
	 */
	@Column(nullable = false, length = LENGTH_PARAMETERVALUE)
	public String getHeight()
	{
		return height;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#getMinheight()
	 */
	@Column(nullable = false)
	public int getMinheight()
	{
		return minheight;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#getWidth()
	 */
	@Column(nullable = false, length = LENGTH_PARAMETERVALUE)
	public String getWidth()
	{
		return width;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#getMinwidth()
	 */
	@Column(nullable = false)
	public int getMinwidth()
	{
		return minwidth;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setClosable(boolean)
	 */

	public void setClosable(boolean closable)
	{
		this.closable = closable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setMaximizable(boolean)
	 */

	public void setMaximizable(boolean maximizable)
	{
		this.maximizable = maximizable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setMaximized(boolean)
	 */

	public void setMaximized(boolean maximized)
	{
		this.maximized = maximized;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setMinimizable(boolean)
	 */

	public void setMinimizable(boolean minimizable)
	{
		this.minimizable = minimizable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setMinimized(boolean)
	 */

	public void setMinimized(boolean minimized)
	{
		this.minimized = minimized;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindowParams#setCollapsible(boolean)
	 */

	public void setCollapsible(boolean collapsible)
	{
		this.collapsible = collapsible;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindowParams#setOpen(boolean)
	 */

	public void setOpen(boolean open)
	{
		this.open = open;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setSizable(boolean)
	 */

	public void setSizable(boolean sizable)
	{
		this.sizable = sizable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setVisible(boolean)
	 */

	public boolean setVisible(boolean visible)
	{
		boolean old = this.visible;
		this.visible = visible;
		return old;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setDraggable(java.lang.String)
	 */

	public void setDraggable(String draggable)
	{
		this.draggable = draggable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setDroppable(java.lang.String)
	 */

	public void setDroppable(String droppable)
	{
		this.droppable = droppable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setPosition(java.lang.String)
	 */

	public void setPosition(String position)
	{
		this.position = position;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setLeft(int)
	 */

	public void setLeft(String left)
	{
		this.left = left;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setTop(int)
	 */

	public void setTop(String top)
	{
		this.top = top;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setZindex(int)
	 */

	public void setZindex(int zindex)
	{
		this.zindex = zindex;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setHeight(int)
	 */

	public void setHeight(String height)
	{
		this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setMinheight(int)
	 */

	public void setMinheight(int minheight)
	{
		this.minheight = minheight;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setWidth(int)
	 */

	public void setWidth(String width)
	{
		this.width = width;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.ui.components.GUIWindow#setMinwidth(int)
	 */

	public void setMinwidth(int minwidth)
	{
		this.minwidth = minwidth;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.model.base.BaseObject#equals(java.lang.Object)
	 */

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(!(obj instanceof GUIWindowParams))
			return false;
		GUIWindowParams other = (GUIWindowParams) obj;
		if(closable != other.closable)
			return false;
		if(collapsible != other.collapsible)
			return false;
		if(draggable == null)
		{
			if(other.draggable != null)
				return false;
		}
		else if(!draggable.equals(other.draggable))
			return false;
		if(droppable == null)
		{
			if(other.droppable != null)
				return false;
		}
		else if(!droppable.equals(other.droppable))
			return false;
		if(height != other.height)
			return false;
		if(left != other.left)
			return false;
		if(maximizable != other.maximizable)
			return false;
		if(maximized != other.maximized)
			return false;
		if(minheight != other.minheight)
			return false;
		if(minwidth != other.minwidth)
			return false;
		if(minimizable != other.minimizable)
			return false;
		if(minimized != other.minimized)
			return false;
		if(open != other.open)
			return false;
		if(position == null)
		{
			if(other.position != null)
				return false;
		}
		else if(!position.equals(other.position))
			return false;
		if(sizable != other.sizable)
			return false;
		if(top != other.top)
			return false;
		if(visible != other.visible)
			return false;
		if(width != other.width)
			return false;
		if(zindex != other.zindex)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.model.base.BaseObject#hashCode()
	 */

	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (closable ? 1231 : 1237);
		result = prime * result + (collapsible ? 1231 : 1237);
		result = prime * result + ((draggable == null) ? 0 : draggable.hashCode());
		result = prime * result + ((droppable == null) ? 0 : droppable.hashCode());
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + (maximizable ? 1231 : 1237);
		result = prime * result + (maximized ? 1231 : 1237);
		result = prime * result + minheight;
		result = prime * result + minwidth;
		result = prime * result + (minimizable ? 1231 : 1237);
		result = prime * result + (minimized ? 1231 : 1237);
		result = prime * result + (open ? 1231 : 1237);
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + (sizable ? 1231 : 1237);
		result = prime * result + ((top == null) ? 0 : top.hashCode());
		result = prime * result + (visible ? 1231 : 1237);
		result = prime * result + ((width == null) ? 0 : width.hashCode());
		result = prime * result + zindex;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("version", version).append("closable", closable).append("collapsible", collapsible)
				.append("draggable", draggable).append("droppable", droppable).append("height", height).append("left", left)
				.append("maximizable", maximizable).append("maximized", maximized).append("minHeight", minheight).append("minWidth", minwidth)
				.append("minimizable", minimizable).append("minimized", minimized).append("open", open).append("position", position)
				.append("sizable", sizable).append("top", top).append("visible", visible).append("width", width).append("zindex", zindex);
		return builder.toString();
	}
}

/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
package com.syncnapsis.utils.graphs;

import java.util.List;

/**
 * Generic Interface to be used in GenericTreeModel. Any class implementing this interface can
 * genericly be handled or displayed as a treelike structure.
 * 
 * @author ultimate
 * @param <D> - the type of the GenericTreeNode
 */
public interface GenericTreeNode<D>
{
	/**
	 * The parent node
	 * 
	 * @return the parent node
	 */
	public D getParent();

	/**
	 * The parent node
	 * 
	 * @param parent - the new parent node
	 */
	public void setParent(D parent);

	/**
	 * The list of child nodes
	 * 
	 * @return the list
	 */
	public List<D> getChildren();

	/**
	 * The list of child nodes
	 * 
	 * @param children - the new list of child nodes
	 */
	public void setChildren(List<D> children);

	/**
	 * Is this node disabled?
	 * 
	 * @return true or false
	 */
	public boolean isDisabled();

	/**
	 * Is this node disabled?
	 * 
	 * @param disabled - true or false
	 */
	public void setDisabled(boolean disabled);
}

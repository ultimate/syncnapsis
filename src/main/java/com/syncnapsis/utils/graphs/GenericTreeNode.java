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

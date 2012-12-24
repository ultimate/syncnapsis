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
package com.syncnapsis.utils.graphs;

/**
 * Generic Implementation of a tree model used for easy creation of treelike structurs of classes
 * implementing GenericTreeNode.
 * 
 * @author ultimate
 * @param <D> - the type of the GenericTreeNode
 */
public class GenericTreeModel<D extends GenericTreeNode<D>>
{
	/**
	 * The root node of this tree
	 */
	private D	root;

	/**
	 * Create a new tree with the given root node
	 * 
	 * @param root - the root node
	 */
	public GenericTreeModel(D root)
	{
		this.root = root;
	}

	/**
	 * Get the child node of a parent node at a specified index
	 * 
	 * @param parent - the parent node
	 * @param index - the index
	 * @return the child node
	 */
	public D getChild(D parent, int index)
	{
		return parent.getChildren().get(index);
	}

	/**
	 * Get the child node of a parent node from a specified path of indexes
	 * 
	 * @param parent - the parent node
	 * @param path - the path to the child node
	 * @return the child node
	 */
	public D getChild(D parent, int[] path)
	{
		D child = parent;
		for(int index: path)
			child = getChild(child, index);
		return child;
	}

	/**
	 * Get the number of children a parent node has
	 * 
	 * @param parent - the parent node
	 * @return the number of children
	 */
	public int getChildCount(D parent)
	{
		return parent.getChildren().size();
	}

	/**
	 * Get the path from a given parent to a given node below this parent.
	 * The path is returned as an Array of indexes used to navigate through the lists of child nodes
	 * for each next node.
	 * 
	 * @param parent - the parent node
	 * @param lastNode - the node the path leads to
	 * @return the path
	 */
	public int[] getPath(D parent, D lastNode)
	{
		D tmpNode = lastNode;
		int count = 0;
		while(tmpNode != parent)
		{
			tmpNode = tmpNode.getParent();
			count++;
		}
		tmpNode = lastNode;
		int[] path = new int[count];
		for(int i = count - 1; i >= 0; i--)
		{
			path[i] = tmpNode.getParent().getChildren().indexOf(tmpNode);
			tmpNode = tmpNode.getParent();
		}
		return path;
	}

	/**
	 * The root node of this tree
	 * 
	 * @return root
	 */
	public D getRoot()
	{
		return root;
	}

	/**
	 * Is the given node a leaf.
	 * 
	 * @param node - the node to test
	 * @return true or false
	 */
	public boolean isLeaf(D node)
	{
		if(node.getChildren() == null)
			return true;
		return node.getChildren().size() == 0;
	}
}

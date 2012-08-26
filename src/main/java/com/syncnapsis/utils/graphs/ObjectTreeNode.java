package com.syncnapsis.utils.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple TreeNode-Implementation forming a Node inside a Tree that can hold an Object Value.
 * 
 * @author ultimate
 * @param <T> - the Type of the Value
 */
public class ObjectTreeNode<T> implements GenericTreeNode<ObjectTreeNode<T>>
{
	/**
	 * @see GenericTreeNode#getParent()
	 */
	private ObjectTreeNode<T>		parent;
	/**
	 * @see GenericTreeNode#getChildren()
	 */
	private List<ObjectTreeNode<T>>	children;
	/**
	 * @see GenericTreeNode#isDisabled()
	 */
	private boolean					disabled;

	/**
	 * The value of this node
	 */
	private T						value;

	/**
	 * Default Constructor without a value
	 */
	public ObjectTreeNode()
	{
		this(null);
	}

	/**
	 * Constructs a new Node with the given value
	 * 
	 * @param value - the Object to hold in this Node
	 */
	public ObjectTreeNode(T value)
	{
		super();
		this.value = value;
		this.children = new ArrayList<ObjectTreeNode<T>>();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.graphs.GenericTreeNode#getParent()
	 */
	@Override
	public ObjectTreeNode<T> getParent()
	{
		return this.parent;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.graphs.GenericTreeNode#setParent(java.lang.Object)
	 */
	@Override
	public void setParent(ObjectTreeNode<T> parent)
	{
		if(parent != this.parent)
		{
			this.parent = parent;
			parent.getChildren().add(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.graphs.GenericTreeNode#getChildren()
	 */
	@Override
	public List<ObjectTreeNode<T>> getChildren()
	{
		return this.children;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.graphs.GenericTreeNode#setChildren(java.util.List)
	 */
	@Override
	public void setChildren(List<ObjectTreeNode<T>> children)
	{
		this.children = children;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.graphs.GenericTreeNode#isDisabled()
	 */
	@Override
	public boolean isDisabled()
	{
		return this.disabled;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.graphs.GenericTreeNode#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}

	/**
	 * The value of this node
	 * 
	 * @return value
	 */
	public T getValue()
	{
		return value;
	}

	/**
	 * The value of this node
	 * 
	 * @param value - the value
	 */
	public void setValue(T value)
	{
		this.value = value;
	}
}

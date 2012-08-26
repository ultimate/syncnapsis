package com.syncnapsis.utils.graphs;

import java.util.Arrays;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({ GenericTreeModel.class, GenericTreeNode.class, ObjectTreeNode.class })
public class GenericTreeTest extends LoggerTestCase
{
	@TestCoversMethods({"getChild", "*Disabled", "*etParent", "*etChildren", "*etValue"})
	public void testGetChild()
	{
		GenericTreeModel<ObjectTreeNode<Integer>> tree = buildTree();

		assertNotNull(tree.getChild(tree.getRoot(), 0));
		assertEquals(1, (int) tree.getChild(tree.getRoot(), 0).getValue());
		assertNotNull(tree.getChild(tree.getRoot(), 1));
		assertEquals(2, (int) tree.getChild(tree.getRoot(), 1).getValue());

		assertNotNull(tree.getChild(tree.getRoot(), new int[] { 0, 0 }));
		assertEquals(11, (int) tree.getChild(tree.getRoot(), new int[] { 0, 0 }).getValue());
		assertNotNull(tree.getChild(tree.getRoot(), new int[] { 0, 1 }));
		assertEquals(12, (int) tree.getChild(tree.getRoot(), new int[] { 0, 1 }).getValue());

		assertNotNull(tree.getChild(tree.getRoot(), new int[] { 0, 0, 0 }));
		assertEquals(111, (int) tree.getChild(tree.getRoot(), new int[] { 0, 0, 0 }).getValue());
		assertNotNull(tree.getChild(tree.getRoot(), new int[] { 0, 0, 1 }));
		assertEquals(112, (int) tree.getChild(tree.getRoot(), new int[] { 0, 0, 1 }).getValue());
	}

	public void testGetChildCount()
	{
		GenericTreeModel<ObjectTreeNode<Integer>> tree = buildTree();
		assertEquals(2, tree.getChildCount(tree.getRoot()));
		assertEquals(2, tree.getChildCount(tree.getChild(tree.getRoot(), 0)));
		assertEquals(0, tree.getChildCount(tree.getChild(tree.getRoot(), 1)));
	}

	public void testGetPath()
	{
		GenericTreeModel<ObjectTreeNode<Integer>> tree = buildTree();

		int[] path;
		ObjectTreeNode<Integer> parent;

		parent = tree.getRoot();
		path = new int[] { 0, 0, 0 };		
		assertTrue(Arrays.equals(path, tree.getPath(parent, tree.getChild(parent, path))));
		path = new int[] { 0, 0, 1 };
		assertTrue(Arrays.equals(path, tree.getPath(parent, tree.getChild(parent, path))));

		parent = tree.getChild(parent, 0);
		path = new int[] { 0, 0 };
		assertTrue(Arrays.equals(path, tree.getPath(parent, tree.getChild(parent, path))));
		path = new int[] { 0, 1 };
		assertTrue(Arrays.equals(path, tree.getPath(parent, tree.getChild(parent, path))));
	}

	public void testGetRoot()
	{
		GenericTreeModel<ObjectTreeNode<Integer>> tree = buildTree();
		assertNotNull(tree.getRoot());
		assertEquals(0, (int) tree.getRoot().getValue());
	}

	public void testIsLeaf()
	{
		GenericTreeModel<ObjectTreeNode<Integer>> tree = buildTree();

		assertNotNull(tree.getChild(tree.getRoot(), new int[] { 0 }));
		assertFalse(tree.isLeaf(tree.getChild(tree.getRoot(), new int[] { 0 })));
		assertNotNull(tree.getChild(tree.getRoot(), new int[] { 0, 0 }));
		assertFalse(tree.isLeaf(tree.getChild(tree.getRoot(), new int[] { 0, 0 })));
		assertNotNull(tree.getChild(tree.getRoot(), new int[] { 0, 0, 0 }));
		assertTrue(tree.isLeaf(tree.getChild(tree.getRoot(), new int[] { 0, 0, 0 })));
		assertNotNull(tree.getChild(tree.getRoot(), new int[] { 0, 0, 1 }));
		assertTrue(tree.isLeaf(tree.getChild(tree.getRoot(), new int[] { 0, 0, 1 })));
		assertNotNull(tree.getChild(tree.getRoot(), new int[] { 0, 1 }));
		assertTrue(tree.isLeaf(tree.getChild(tree.getRoot(), new int[] { 0, 1 })));
		assertNotNull(tree.getChild(tree.getRoot(), new int[] { 1 }));
		assertTrue(tree.isLeaf(tree.getChild(tree.getRoot(), new int[] { 1 })));
	}

	protected GenericTreeModel<ObjectTreeNode<Integer>> buildTree()
	{
		ObjectTreeNode<Integer> n0 = new ObjectTreeNode<Integer>(0);
		ObjectTreeNode<Integer> n1 = new ObjectTreeNode<Integer>(1);
		n1.setParent(n0);
		ObjectTreeNode<Integer> n11 = new ObjectTreeNode<Integer>(11);
		n11.setParent(n1);
		ObjectTreeNode<Integer> n111 = new ObjectTreeNode<Integer>(111);
		n111.setParent(n11);
		ObjectTreeNode<Integer> n112 = new ObjectTreeNode<Integer>(112);
		n112.setParent(n11);
		ObjectTreeNode<Integer> n12 = new ObjectTreeNode<Integer>(12);
		n12.setParent(n1);
		ObjectTreeNode<Integer> n2 = new ObjectTreeNode<Integer>(2);
		n2.setParent(n0);

		return new GenericTreeModel<ObjectTreeNode<Integer>>(n0);
	}
}

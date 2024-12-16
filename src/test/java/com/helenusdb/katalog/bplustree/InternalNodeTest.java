package com.helenusdb.katalog.bplustree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InternalNodeTest
{
	@Test
	void shouldCreateEmpty()
	{
		InternalNode<Integer, String> node = new InternalNode<>();
		assertNotNull(node);
		assertEquals(0, node.size());
		assertFalse(node.isLeaf());
		assertNull(node.split(3));
	}

	@Test
	void shouldInsert()
	{
		LeafNode<Integer, String> node = new LeafNode<>();
		node.insert(2, "two");
		node.insert(2, "two-also");
		node.insert(3, "three");
		node.insert(1, "one");
		assertEquals(3, node.size());
		assertEquals("one", node.search(1));
		assertEquals("two-also", node.search(2));
		assertEquals("three", node.search(3));
	}

	@Test
	void shouldSplitLeafOrderThree()
	{
		LeafNode<Integer, String> leaf = new LeafNode<>();
		leaf.insert(1, "one");
		leaf.insert(3, "three");
		leaf.insert(2, "two");
		assertEquals(3, leaf.getMiddleKey(3).intValue());
		LeafNode<Integer, String> sibling = leaf.split(3);
		assertNotNull(sibling);
		assertEquals(1, sibling.size());
		assertEquals(2, leaf.size());
		assertTrue(leaf.isLeaf());
		assertEquals("one", leaf.search(1));
		assertEquals("two", leaf.search(2));
		assertEquals("three", sibling.search(3));
	}

	@Test
	void shouldSplitLeafOrderFour()
	{
		LeafNode<Integer, String> leaf = new LeafNode<>();
		leaf.insert(1, "one");
		leaf.insert(2, "two");
		leaf.insert(3, "three");
		leaf.insert(4, "four");
		assertEquals(3, leaf.getMiddleKey(4).intValue());
		LeafNode<Integer, String> sibling = leaf.split(4);
		assertNotNull(sibling);
		assertEquals(2, leaf.size());
		assertEquals(2, sibling.size());
		assertEquals("one", leaf.search(1));
		assertEquals("two", leaf.search(2));
		assertEquals("three", sibling.search(3));
		assertEquals("four", sibling.search(4));
	}

	@Test
	void shouldMergeLeaf()
	{
		LeafNode<Integer, String> node = new LeafNode<>();
		node.insert(1, "one");
		node.insert(2, "two");
		LeafNode<Integer, String> sibling = new LeafNode<>();
		sibling.insert(3, "three");
		sibling.insert(4, "four");
		node.merge(sibling);
		assertEquals(4, node.size());
		assertEquals("one", node.search(1));
		assertEquals("two", node.search(2));
		assertEquals("three", node.search(3));
		assertEquals("four", node.search(4));
	}

	@Test
	void shouldNotSplitLeaf()
	{
		LeafNode<Integer, String> node = new LeafNode<>();
		node.insert(1, "one");
		node.insert(2, "two");
		node.insert(3, "three");
		assertNull(node.split(4));
	}

	@Test
	void shouldGetMiddleKeyOrderThree()
	{
		LeafNode<Integer, String> node = new LeafNode<>();
		node.insert(1, "one");
		node.insert(2, "two");
		node.insert(3, "three");
		assertEquals(3, node.getMiddleKey(3).intValue());
	}

	@Test
	void shouldGetMiddleKeyOrderFour()
	{
		LeafNode<Integer, String> node = new LeafNode<>();
		node.insert(1, "one");
		node.insert(2, "two");
		node.insert(3, "three");
		node.insert(4, "four");
		assertEquals(3, node.getMiddleKey(4).intValue());
	}
}

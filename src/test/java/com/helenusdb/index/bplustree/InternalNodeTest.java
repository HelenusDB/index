package com.helenusdb.index.bplustree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class InternalNodeTest
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
	void shouldInsertOutOfOrder()
	{
		InternalNode<Integer, String> node = new InternalNode<>();
		InternalNode<Integer, String> one = new InternalNode<>();
		one.insert(1, new LeafNode<>(1, "one"), null);
		InternalNode<Integer, String> two = new InternalNode<>();
		two.insert(2, new LeafNode<>(2, "two"), null);
		InternalNode<Integer, String> three = new InternalNode<>();
		three.insert(3, new LeafNode<>(3, "three"), null);
		node.insert(3, three, null);
		node.insert(1, one, three);
		node.insert(2, two, three);
		assertEquals(3, node.size());
		assertEquals("one", node.traverse(1));
		assertEquals("two", node.traverse(2));
		assertEquals("three", node.traverse(3));
	}

	@Test
	void shouldInsertInOrder()
	{
		InternalNode<Integer, String> node = new InternalNode<>();
		InternalNode<Integer, String> one = new InternalNode<>();
		one.insert(1, new LeafNode<>(1, "one"), null);
		InternalNode<Integer, String> two = new InternalNode<>();
		two.insert(2, new LeafNode<>(2, "two"), null);
		InternalNode<Integer, String> three = new InternalNode<>();
		three.insert(3, new LeafNode<>(3, "three"), null);
		node.insert(1, one, two);
		node.insert(2, two, three);
		node.insert(3, three, null);
		assertEquals(3, node.size());
		assertEquals("one", node.traverse(1));
		assertEquals("two", node.traverse(2));
		assertEquals("three", node.traverse(3));
	}

	@Test
	void shouldSplitOrderThree()
	{
		InternalNode<Integer, String> node = new InternalNode<>();
		LeafNode<Integer, String> one = new LeafNode<>(1, "one");
		LeafNode<Integer, String> two = new LeafNode<>(2, "two");
		LeafNode<Integer, String> three = new LeafNode<>(3, "three");
		node.insert(1, one, null);
		node.insert(3, three, null);
		node.insert(2, two, three);
		int order = 3;
		assertEquals(3, node.getMiddleKey(order).intValue());
		InternalNode<Integer, String> sibling = node.split(order);
		assertNotNull(sibling);
		assertEquals(1, sibling.size());
		assertEquals(2, node.size());
		assertEquals("one", node.traverse(1));
		assertEquals("two", node.traverse(2));
		assertEquals("three", sibling.traverse(3));
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

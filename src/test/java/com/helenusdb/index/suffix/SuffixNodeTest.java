package com.helenusdb.index.suffix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.helenusdb.index.suffix.SuffixNode;

class SuffixNodeTest
{
	@Test
	void testAddAndGetChild()
	{
		SuffixNode node = new SuffixNode();

		// Add a child and retrieve it
		node.addChildIfAbsent('a');
		SuffixNode childNode = node.getChild('a');

		assertNotNull(childNode, "Child node for 'a' should exist.");
		assertTrue(node.containsChild('a'), "Node should contain child for 'a'.");

		// Ensure a non-existent child returns null
		assertNull(node.getChild('b'), "Child node for 'b' should not exist.");
		assertFalse(node.containsChild('b'), "Node should not contain child for 'b'.");
	}

	@Test
	void testAddDuplicateChild()
	{
		SuffixNode node = new SuffixNode();

		// Add a child and retrieve it
		node.addChildIfAbsent('a');
		SuffixNode firstChild = node.getChild('a');

		// Add the same child character again
		node.addChildIfAbsent('a');
		SuffixNode secondChild = node.getChild('a');

		assertSame(firstChild, secondChild, "Child nodes for duplicate additions should be the same instance.");
	}

	@Test
	void testAddAndGetIndices()
	{
		SuffixNode node = new SuffixNode();

		// Add indices
		node.addIndex(1);
		node.addIndex(2);
		node.addIndex(3);

		Set<Integer> indices = node.getIndices();

		// Validate indices
		assertEquals(3, indices.size(), "There should be 3 indices.");
		assertTrue(indices.contains(1), "Indices should contain 1.");
		assertTrue(indices.contains(2), "Indices should contain 2.");
		assertTrue(indices.contains(3), "Indices should contain 3.");
	}

	@Test
	void testIndicesAreUnmodifiable()
	{
		SuffixNode node = new SuffixNode();

		// Add an index
		node.addIndex(1);

		Set<Integer> indices = node.getIndices();

		// Validate unmodifiable behavior
		assertThrows(UnsupportedOperationException.class, () -> indices.add(2), "Indices list should be unmodifiable.");
	}

	@Test
	void testToString()
	{
		SuffixNode node = new SuffixNode();

		// Add children and indices
		node.addChildIfAbsent('a');
		node.addChildIfAbsent('b');
		node.addIndex(1);

		String expected = "SuffixNode{children=[a, b], indices=[1]}";
		assertEquals(expected, node.toString(), "toString() output should match expected format.");
	}
}

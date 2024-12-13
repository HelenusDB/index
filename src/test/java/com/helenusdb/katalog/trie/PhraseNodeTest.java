package com.helenusdb.katalog.trie;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhraseNodeTest
{
	@Test
	void testAddAndGetChild()
	{
		PhraseNode node = new PhraseNode();

		// Add a child and retrieve it
		node.addChild('a');
		PhraseNode childNode = node.getChild('a');

		assertNotNull(childNode, "Child node for 'a' should exist.");
		assertTrue(node.containsChild('a'), "Node should contain child for 'a'.");

		// Ensure a non-existent child returns null
		assertNull(node.getChild('b'), "Child node for 'b' should not exist.");
		assertFalse(node.containsChild('b'), "Node should not contain child for 'b'.");
	}

	@Test
	void testAddDuplicateChild()
	{
		PhraseNode node = new PhraseNode();

		// Add a child and retrieve it
		node.addChild('a');
		PhraseNode firstChild = node.getChild('a');

		// Add the same child character again
		node.addChild('a');
		PhraseNode secondChild = node.getChild('a');

		assertSame(firstChild, secondChild, "Child nodes for duplicate additions should be the same instance.");
	}

	@Test
	void testAddAndGetIndices()
	{
		PhraseNode node = new PhraseNode();

		// Add indices
		node.addIndex(1);
		node.addIndex(2);
		node.addIndex(3);

		List<Integer> indices = node.getIndices();

		// Validate indices
		assertEquals(3, indices.size(), "There should be 3 indices.");
		assertTrue(indices.contains(1), "Indices should contain 1.");
		assertTrue(indices.contains(2), "Indices should contain 2.");
		assertTrue(indices.contains(3), "Indices should contain 3.");
	}

	@Test
	void testIndicesAreUnmodifiable()
	{
		PhraseNode node = new PhraseNode();

		// Add an index
		node.addIndex(1);

		List<Integer> indices = node.getIndices();

		// Validate unmodifiable behavior
		assertThrows(UnsupportedOperationException.class, () -> indices.add(2), "Indices list should be unmodifiable.");
	}

	@Test
	void testToString()
	{
		PhraseNode node = new PhraseNode();

		// Add children and indices
		node.addChild('a');
		node.addChild('b');
		node.addIndex(1);

		String expected = "PhraseNode{children=[a, b], indices=[1]}";
		assertEquals(expected, node.toString(), "toString() output should match expected format.");
	}
}

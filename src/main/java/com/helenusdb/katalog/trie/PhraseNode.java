package com.helenusdb.katalog.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * PhraseNode is a node in PhraseIndex to store phrases and indices into their associated values.
 */
class PhraseNode
{
	/**
	 * The children of this node, indexed by the first character of the phrase.
     */
	private HashMap<Character, PhraseNode> children = new HashMap<>();

	/**
	 * The indices of the values associated with the phrase ending. Only populated
	 * if this node is the end of a phrase (a leaf node).
	 */
	private List<Integer> indices = new ArrayList<>();

	/**
	 * Adds a child node to this node. If the child already exists, it is not replaced.
	 *
	 * @param c The character to index the child node by.
	 */
	public void addChild(char c)
	{
		children.putIfAbsent(c, new PhraseNode());
	}

	/**
	 * Gets the child node indexed by the given character.
	 *
	 * @param c The character to get the child node for.
	 * @return The child node indexed by the given character, or null if no such child exists.
	 */
	public PhraseNode getChild(char c)
	{
		return children.get(c);
	}

	/**
	 * Checks if this node has a child indexed by the given character.
	 *
	 * @param c The character to check for.
	 * @return True if this node has a child indexed by the given character, false otherwise.
	 * @see #getChild(char)
	 */
	public boolean containsChild(char c)
	{
		return children.containsKey(c);
	}

	/**
	 * Adds an index to the list of indices in this leaf node.
	 *
	 * @param index The index to add
	 * @see #getIndices()
	 */
	public void addIndex(int index)
	{
		indices.add(index);
	}

	/**
	 * Gets the list of indices in this leaf node. The list is unmodifiable to prevent
	 * modification of the internal state.
	 * 
	 * @return The list of indices (into the PhraseIndex) in this leaf node.
	 */
	public List<Integer> getIndices()
	{
		return Collections.unmodifiableList(indices);
	}

	@Override
	public String toString()
	{
		return "PhraseNode{" + "children=" + children.keySet() + ", indices=" + indices + '}';
	}
}

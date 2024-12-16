package com.helenusdb.katalog.trie;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PhraseNode is a node in PhraseIndex to store phrases and indices into their associated values.
 */
class PhraseNode
{
	/**
	 * The children of this node, indexed by the first character of the phrase.
	 */
	private Map<Character, PhraseNode> children;

	/**
	 * The indices of the values associated with the phrase ending. Only populated if this node is the end of a phrase
	 * (a leaf node).
	 */
	private Set<Integer> indices;

	/**
	 * Adds a child node to this node. If the child already exists, it is not replaced.
	 *
	 * @param c The character to index the child node by.
	 */
	public PhraseNode addChildIfAbsent(char c)
	{
		if (children == null)
		{
			children = new ConcurrentHashMap<>();
		}

		return children.computeIfAbsent(c, k -> new PhraseNode());
	}

	/**
	 * Gets the child node indexed by the given character.
	 *
	 * @param c The character to get the child node for.
	 * @return The child node indexed by the given character, or null if no such child exists.
	 */
	public PhraseNode getChild(char c)
	{
		return children == null ? null : children.get(c);
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
		return children != null && children.containsKey(c);
	}

	/**
	 * 
	 * @return
	 */
	public Collection<PhraseNode> getChildren()
	{
		return children == null ? Collections.emptyList() : Collections.unmodifiableCollection(children.values());
	}

	/**
	 * Adds an index to the list of indices in this leaf node.
	 *
	 * @param index The index to add
	 * @see #getIndices()
	 */
	public void addIndex(int index)
	{
		if (indices == null)
		{
			indices = new HashSet<>();
		}

		indices.add(index);
	}

	/**
	 * Gets the set of indices in this leaf node. The list is unmodifiable to prevent modification of the internal
	 * state.
	 * 
	 * @return The set of indices (into the PhraseIndex) in this leaf node.
	 */
	public Set<Integer> getIndices()
	{
		return indices == null ? Collections.emptySet() : Collections.unmodifiableSet(indices);
	}

	/**
	 * Checks if this node is a leaf node (i.e. has no children).
	 *
	 * @return True if this node is a leaf node, false otherwise.
	 */
	public boolean isLeaf()
	{
		return children == null || children.isEmpty();
	}

	@Override
	public String toString()
	{
		return "PhraseNode{" + "children=" + (children == null ? "[]" : children.keySet()) + ", indices="
			+ (indices == null ? "[]" : indices) + '}';
	}
}

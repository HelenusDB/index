package com.helenusdb.katalog.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * This PhraseIndex stores values and associated phrases (like DB records associated with a string column), allowing
 * searching those values for those that contain a given substring. The index is built by adding strings and values then
 * the search method can be used to find all values that contain a given query substring.
 * 
 * @author Todd Fredrich
 * @since 13 Dec 2024
 * @see PhraseNode
 */
public class PhraseIndex<T>
{
	private PhraseNode root;
	private List<T> values;

	public PhraseIndex()
	{
		this.root = new PhraseNode();
		this.values = new ArrayList<>();
	}

	/**
	 * Inserts a phrase and its associated value into the index.
	 *
	 * @param phrase The phrase to associate with the value.
	 * @param value The value to associate with the phrase.
	 * @return The PhraseIndex instance for chaining.
	 */
	public PhraseIndex<T> insert(String phrase, T value)
	{
		if (phrase == null || phrase.isEmpty()) return this;

		int index = values.size();
		values.add(value);

		for (int i = 0; i < phrase.length(); i++)
		{
			insertSuffix(phrase.substring(i), index);
		}

		return this;
	}

	/**
	 * Searches the index for all values that contain the given query substring.
	 *
	 * @param query The substring to search for.
	 * @return A list of all values that contain the query substring.
	 */
	public List<T> search(String query)
	{
		if (query == null || query.isEmpty()) return Collections.emptyList();

		return getIndicesFor(query).stream().map(values::get).toList();
	}

	/**
	 * Searches the index for the given query returning a list of indices for the query.
	 * If the query is not found an empty list is returned.
	 * 
	 * @param query The query to search for.
	 * @return The list of indices for the query. Never null.
	 */
	public Set<Integer> getIndicesFor(String query)
	{
		if (query == null || query.isEmpty()) return Collections.emptySet();
		PhraseNode current = root;

		for (char c : query.toCharArray())
		{
			if (!current.containsChild(c))
			{
				return Collections.emptySet();
			}

			current = current.getChild(c);
		}

		return current.getIndices();
	}

	/**
	 * Inserts a suffix into the index with the given index.
	 * 
	 * @param suffix The suffix to insert.
	 * @param index The index to associate with the suffix.
	 */
	private void insertSuffix(String suffix, int index)
	{
		PhraseNode current = root;

		for (char c : suffix.toCharArray())
		{
			current.addChildIfAbsent(c);
			current = current.getChild(c);
			current.addIndex(index);
		}
	}
}

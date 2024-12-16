package com.helenusdb.katalog.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
	private boolean isCaseSensitive = false;

	public PhraseIndex()
	{
		this.root = new PhraseNode();
		this.values = new ArrayList<>();
	}

	public PhraseIndex(boolean isCaseSensitive)
	{
		this();
		setCaseSensitive(isCaseSensitive);
	}

	/**
	 * Sets whether the index is case sensitive or not. MUST be called
	 * before inserting any values.
	 *
	 * @param value True if the index is case sensitive, false otherwise.
	 * @return The PhraseIndex instance for chaining.
	 */
	public PhraseIndex<T> setCaseSensitive(boolean value)
	{
		this.isCaseSensitive = value;
		return this;
	}

	/**
	 * Returns whether the index is case sensitive or not.
	 * 
	 * @return True if the index is case sensitive, false otherwise.
	 */
	public boolean isCaseSensitive()
    {
        return isCaseSensitive;
    }

	/**
	 * Inserts a phrase and its associated value into the index.
	 *
	 * @param phrase The phrase to associate with the value.
	 * @param value  The value to associate with the phrase.
	 * @return The PhraseIndex instance for chaining.
	 */
	public PhraseIndex<T> insert(String phrase, T value)
	{
		if (phrase == null || phrase.isEmpty()) return this;

		int index = values.size();
		values.add(value);
		String normalizedPhrase = normalizeCase(phrase);

		for (int i = 0; i < phrase.length(); i++)
		{
			insertSuffix(normalizedPhrase.substring(i), index);
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
		return getIndicesFor(query).stream().map(values::get).toList();
	}

	/**
	 * Searches the index for the given query returning a list of indices for the query. If the query is not found an
	 * empty list is returned.
	 * 
	 * @param query The query to search for.
	 * @return The list of indices for the query. Never null.
	 */
	public Set<Integer> getIndicesFor(String query)
	{
		if (query == null || query.isEmpty()) return Collections.emptySet();
		String normalizedQuery = normalizeCase(query);
		return getIndicesFor(normalizedQuery.toCharArray(), 0, root);
	}

	/**
	 * A recursive helper method to search the index for the given query returning a list of indices for the query. If
	 * the query is not found an empty list is returned. It supports the wildcard characters '*' and '?'.
	 * 
	 * @param query  The query being processed.
	 * @param index  The index into the current query character being processed.
	 * @param current The current node in the phrase index.
	 */
	private Set<Integer> getIndicesFor(char[] query, int index, PhraseNode current)
	{
		if (query == null || query.length == 0) return Collections.emptySet();
		if (index == query.length) return current.getIndices();

		char c = query[index];
		Set<Integer> indices = new HashSet<>();

		if (c == '*') // Match zero or more characters.
		{
			for (PhraseNode child : current.getChildren())
			{
				indices.addAll(getIndicesFor(query, index, child));
				indices.addAll(getIndicesFor(query, index + 1, child));
			}
		}
		else if (c == '?') // Match any single character.
		{
			for (PhraseNode child : current.getChildren())
			{
				indices.addAll(getIndicesFor(query, index + 1, child));
			}

			indices.addAll(getIndicesFor(query, index + 1, current));
		}
		else // Exact match.
		{
			PhraseNode child = current.getChild(c);

			if (child != null)
			{
				indices.addAll(getIndicesFor(query, index + 1, child));
			}
		}
	
		return indices;
	}

	/**
	 * Inserts a suffix into the index with the given index.
	 * 
	 * @param suffix The suffix to insert.
	 * @param index  The index to associate with the suffix.
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

	/**
	 * Normalizes the case of the given text based on the case sensitivity of the index.
	 * 
	 * @param text The text to normalize.
	 * @return The normalized text.
	 */
	private String normalizeCase(String text)
	{
		return isCaseSensitive ? text : text.toLowerCase();
	}
}

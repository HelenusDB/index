package com.helenusdb.index.suffix;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This SuffixIndex stores values and associated phrases (like DB records associated with a string column), allowing
 * searching those values for those that contain a given substring. The index is built by adding strings and values then
 * the search method can be used to find all values that contain a given query substring.
 * 
 * The index supports the wildcard characters '*' and '?'. The '*' character matches zero or more characters and the '?'
 * character matches any single character.
 * 
 * The index is case sensitive by default but can be set to case insensitive at construction time.
 * However, note that case insensitivity essentially causes a doubling in the memory size of the index.
 * 
 * Usage:
 *		 SuffixIndex<User> index = new SuffixIndex<>(); // Case insensitive by default.
 *		 index.insert("Alice Brown", new User("Alice", "Brown", 25, "Anytown, USA"));
 *		 index.insert("Bob Barker", new User("Bob", "Barker", 30, "Littletown, USA"));
 *		 index.insert("Charlie Lane", new User("Charlie", "Lane", 35, "Bigtown, USA"));
 *		 index.insert("David Smith", new User("David", "Smith", 40, "Hometown, USA"));
 *
 *		 List<User> results = index.search("b*"); // Returns Bob and Alice.
 *		 results = index.search("lane"); // Returns Carlie.
 *		 results = index.search("i?"); // Returns Alice, Charlie, and David.
 * 
 * @author Todd Fredrich
 * @since 13 Dec 2024
 * @see SuffixNode
 */
public class SuffixIndex<T>
{
	// The wildcard character for matching any single character in a query.
	private static final char SINGLE_CHARACTER_WILDCARD = '?';

	// The wildcard character for matching zero or more characters in a query.
	private static final char ZERO_OR_MORE_WILDCARD = '*';

	// The root node of the phrase index.
	private SuffixNode root;

	// The list of values associated with the phrases in the index.
	private List<T> values;

	// Whether the index is case sensitive or not. Can only be set at construction time.
	private boolean isCaseSensitive = false;

	/**
	 * Constructs a new SuffixIndex without case sensitivity.
     */
	public SuffixIndex()
	{
		this.root = new SuffixNode();
		this.values = new CopyOnWriteArrayList<>();
	}

	/**
	 * Constructs a new SuffixIndex with the given case sensitivity.
	 *
	 * @param isCaseSensitive True if the index is case sensitive, false otherwise.
	 */
	public SuffixIndex(boolean isCaseSensitive)
	{
		this();
		setCaseSensitive(isCaseSensitive);
	}

	/**
	 * Sets whether the index is case sensitive or not. MUST be called
	 * before inserting any values.
	 *
	 * @param value True if the index is case sensitive, false otherwise.
	 * @return The SuffixIndex instance for chaining.
	 */
	public SuffixIndex<T> setCaseSensitive(boolean value)
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
	 * @return The SuffixIndex instance for chaining.
	 */
	public SuffixIndex<T> insert(String phrase, T value)
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
	private Set<Integer> getIndicesFor(char[] query, int index, SuffixNode current)
	{
		if (query == null || query.length == 0) return Collections.emptySet();
		if (index == query.length) return current.getIndices();

		char c = query[index];
		Set<Integer> indices = new HashSet<>();

		if (c == ZERO_OR_MORE_WILDCARD) // Match zero or more characters.
		{
			for (SuffixNode child : current.getChildren())
			{
				indices.addAll(getIndicesFor(query, index, child));
				indices.addAll(getIndicesFor(query, index + 1, child));
			}
		}
		else if (c == SINGLE_CHARACTER_WILDCARD) // Match any single character.
		{
			for (SuffixNode child : current.getChildren())
			{
				indices.addAll(getIndicesFor(query, index + 1, child));
			}

			indices.addAll(getIndicesFor(query, index + 1, current));
		}
		else // Exact match.
		{
			SuffixNode child = current.getChild(c);

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
		SuffixNode current = root;

		for (char c : suffix.toCharArray())
		{
			current = current.addChildIfAbsent(c);
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

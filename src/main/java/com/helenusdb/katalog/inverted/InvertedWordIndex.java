package com.helenusdb.katalog.inverted;

import java.util.*;

/**
 * An inverted index that supports searching whole words in phrases.
 * Word order is not considered which results in multiple word queries
 * returning phrases that contain the words in any order.
 * 
 * The index is case insensitive by default. This can be changed by
 * calling the setCaseSensitive method. However, it must be called
 * before any phrases are inserted.
 * 
 * Insertion time complexity: O(n) where n is the number of words in the phrase.
 * Search time complexity: O(n) where n is the number of words in the query.
 * 
 * In contrast to {@link SuffixIndex}, this data structure is much faster at
 * inserting phrases but doesn't support substring searches or 
 * wildcard queries. It is also more memory efficient than the suffix index.
 * 
 * @param <T> The type of the values that are associated with the phrases.
 * @see SuffixIndex
 * @author Todd Fredrich
 */
public class InvertedWordIndex<T>
{
	private Map<String, Set<Integer>> index;
	private List<T> phrases;
	private boolean isCaseSensitive = false;

	/**
	 * Constructs a new inverted word index that is case insensitive.
	 */
	public InvertedWordIndex()
	{
		this.index = new HashMap<>();
		this.phrases = new ArrayList<>();
	}

	/**
	 * Constructs a new inverted word index with the specified case sensitivity.
	 * 
	 * @param isCaseSensitive Whether the index should be case sensitive.
	 */
	public InvertedWordIndex(boolean isCaseSensitive)
	{
		this();
		setCaseSensitive(isCaseSensitive);
	}

	/**
	 * Returns whether the index is case sensitive.
	 * 
	 * @return True if the index is case sensitive, false otherwise.
	 */
	public boolean isCaseSensitive()
	{
		return isCaseSensitive;
	}

	/**
	 * Sets whether the index is case sensitive. Must be called before any phrases
	 * are inserted into the index.
	 * 
	 * @param isCaseSensitive Whether the index should be case sensitive.
	 */
	public void setCaseSensitive(boolean isCaseSensitive)
	{
		this.isCaseSensitive = isCaseSensitive;
	}

	/**
	 * Adds a phrase to the index.
	 * 
	 * @param phrase The phrase to add.
	 */
	public InvertedWordIndex<T> insert(String phrase, T value)
	{
		phrase = normalizePhrase(phrase);
		phrases.add(value);

		for (String word : phrase.split("\\s+"))
		{
			index.computeIfAbsent(word, k -> new HashSet<>()).add(phrases.size() - 1);
		}

		return this;
	}

	/**
	 * Searches for all phrases containing the query substring.
	 * 
	 * @param query The query string to search for.
	 * @return A list of phrases that contain the query substring.
	 */
	public List<T> search(String query)
	{
		List<T> results = new ArrayList<>();
		for (int index : getIndicesFor(query))
		{
			results.add(phrases.get(index));
		}
		return results;
	}

	/**
	 * Returns the indices of the phrases that contain the query.
	 * 
	 * @param query The words to search for.
	 * @return The indices of the phrases that contain the query.
	 */
	public Set<Integer> getIndicesFor(String query)
	{
		query = normalizePhrase(query);
		Set<Integer> resultIndices = new HashSet<>();
		String[] wordsInQuery = query.split("\\s+");

		for (String word : wordsInQuery)
		{
			if (index.containsKey(word))
			{
				resultIndices.addAll(index.get(word));
			}
		}

		return resultIndices;
	}

	/**
	 * Normalizes the phrase by converting it to lower case if the index is not case sensitive.
	 * 
	 * @param phrase The phrase to normalize.
	 * @return The normalized phrase.
	 */
	private String normalizePhrase(String phrase)
	{
		if (phrase == null)
		{
			return "";
		}

		return isCaseSensitive ? phrase : phrase.toLowerCase();
	}
}

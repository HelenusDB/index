package com.helenusdb.index.trie;

import java.util.Collections;
import java.util.List;

/**
 * Trie is a data structure that stores a dynamic set of strings, where each
 * string is represented by a sequence of characters. It supports efficient
 * insertion, search, and prefix-based queries.
 * <p>
 * Useful for applications like autocomplete, spell checking, and
 * dictionary implementations.
 * <p>
 * Usage:
 * <pre>
 * Trie trie = new Trie();
 * trie.insert("hello");
 * trie.insert("helicopter");
 * trie.insert("hero");
 * boolean exists = trie.search("hello"); // returns true
 * boolean startsWith = trie.startsWith("he"); // returns true
 * List&lt;String&gt; suggestions = trie.getSuggestions("hel"); // returns ["hello", "helicopter"]
 * </pre>
 */
public class Trie {
	private TrieNode root = new TrieNode();

	/**
	 * Inserts a word into the Trie.
	 * 
	 * @param word The word to insert. It must not be null or empty.
	 * @throws IllegalArgumentException if the word is null or empty.
	 */
	public Trie insert(String word) {
		if (word == null || word.trim().isEmpty()) {
			throw new IllegalArgumentException("Word cannot be null or empty");
		}

		String lowerCaseWord = word.trim().toLowerCase();
		TrieNode currentNode = root;

		for (Character ch : lowerCaseWord.toCharArray()) {
			if (!currentNode.contains(ch)) {
				currentNode.put(ch, new TrieNode());
			}

			currentNode = currentNode.get(ch);
		}

		currentNode.setEndOfWord(true);
		return this;
	}

	/**
	 * Searches for a word in the Trie. If the word exists, it returns true;
	 * otherwise, it returns false.
	 * 
	 * @param word The word to search for. It must not be null or empty.
	 * @return true if the word exists in the Trie, false otherwise.
	 * @throws IllegalArgumentException if the word is null or empty.
	 */
	public boolean search(String word) {
		TrieNode node = findNode(word);
		return node != null && node.isEndOfWord();
	}

	/**
	 * Checks if any word in the Trie starts with the given prefix.
	 * 
	 * @param prefix The prefix to check. It must not be null or empty.
	 * @return true if any word starts with the prefix, false otherwise.
	 */
	public boolean startsWith(String prefix) {
		TrieNode node = findNode(prefix);
		return node != null;
	}

	/**
	 * Returns a list of all words in the Trie that start with the given prefix.
	 * 
	 * @param prefix The prefix to search for. It must not be null or empty.
	 * @return A list of words that start with the prefix, or an empty list if no such words exist.
	 * @throws IllegalArgumentException if the prefix is null or empty.
	 */
	public List<String> getSuggestions(String prefix) {
		TrieNode node = findNode(prefix);

		if (node == null) {
			return Collections.emptyList();
		}

		return node.getWords(prefix);
	}

	/**
	 * Finds the TrieNode corresponding to the given prefix (or word). Returns null
	 * if the prefix does not exist in the Trie.
	 * 
	 * @param prefix The word to find. It must not be null or empty.
	 * @return The TrieNode corresponding to the prefix, or null if it does not
	 *         exist.
	 * @throws IllegalArgumentException if the prefix is null or empty.
	 */
	private TrieNode findNode(String prefix) {
		if (prefix == null || prefix.trim().isEmpty()) {
			throw new IllegalArgumentException("Prefix cannot be null or empty");
		}

		String lowerCaseWord = prefix.trim().toLowerCase();
		TrieNode currentNode = root;

		for (Character ch : lowerCaseWord.toCharArray()) {
			if (!currentNode.contains(ch)) {
				return null;
			}

			currentNode = currentNode.get(ch);
		}

		return currentNode;
	}
}

package com.helenusdb.index.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {
	private final Map<Character, TrieNode> children = new HashMap<>();
	private boolean isEndOfWord = false;

	public boolean isEndOfWord() {
		return isEndOfWord;
	}

	public void setEndOfWord(boolean isEndOfWord) {
		this.isEndOfWord = isEndOfWord;
	}

	public TrieNode get(Character ch) {
		return children.get(ch);
	}

	public TrieNode put(Character ch, TrieNode node) {
		return children.put(ch, node);
	}

	public boolean contains(Character ch) {
		return children.containsKey(ch);
	}

	public boolean isEmpty() {
		return children.isEmpty();
	}

	public List<String> getWords(String prefix) {
		List<String> words = new ArrayList<>();

		if (isEndOfWord) {
			words.add(prefix);
		}

		for (Map.Entry<Character, TrieNode> entry : children.entrySet()) {
			char ch = entry.getKey();
			TrieNode childNode = entry.getValue();
			words.addAll(childNode.getWords(prefix + ch));
		}

		Collections.sort(words);
		return words;
	}
}

package com.helenusdb.index.trie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TrieTest {
	private static Trie trie;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		trie = new Trie();
		trie.insert("hello")
			.insert("helicopter")
			.insert("hero")
			.insert("heron")
			.insert("her")
			.insert("he")
			.insert("hi")
			.insert("hike")
			.insert("hiking")
			.insert("hills")
			.insert("hilltop")
			.insert("hilly")
			.insert("hive")
			.insert("hover")
			.insert("hovercraft")
			.insert("hoover")
			.insert("hoopla")
			.insert("hopeful")
			.insert("hopeless");
	}

	@Test
	void shouldSearch() {
		assertTrue(trie.search("hello"));
		assertTrue(trie.search("helicopter"));
		assertTrue(trie.search("hopeless"));
		assertFalse(trie.search("heroic"));
		assertFalse(trie.search("hik"));
	}

	@Test
	void shouldStartsWith() {
		assertTrue(trie.startsWith("he"));
		assertTrue(trie.startsWith("hi"));
		assertTrue(trie.startsWith("hov"));
		assertFalse(trie.startsWith("abc"));
		assertFalse(trie.startsWith("hb"));
	}

	@Test
	void shouldGetSuggestions() {
		assertEquals(List.of("he", "helicopter", "hello", "her", "hero", "heron"), trie.getSuggestions("he"));
		assertEquals(List.of("hi", "hike", "hiking", "hills", "hilltop", "hilly", "hive"), trie.getSuggestions("hi"));
		assertEquals(List.of("hover", "hovercraft"), trie.getSuggestions("hov"));
		assertEquals(Collections.emptyList(), trie.getSuggestions("abc"));
		assertEquals(Collections.emptyList(), trie.getSuggestions("hb"));
	}
}

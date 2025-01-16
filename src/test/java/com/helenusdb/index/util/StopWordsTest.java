package com.helenusdb.index.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class StopWordsTest {
	private static final String TEXT = "The quick brown fox jumps over the lazy dog, by and by.";

	@Test
	void shouldFilterDefault() {
		List<String> filteredTokens = StopWords.minimal().filter(TEXT);

		assertEquals(7, filteredTokens.size());
		assertTrue(filteredTokens.contains("quick"));
		assertTrue(filteredTokens.contains("brown"));
		assertTrue(filteredTokens.contains("fox"));
		assertTrue(filteredTokens.contains("jumps"));
		assertTrue(filteredTokens.contains("over"));
		assertTrue(filteredTokens.contains("lazy"));
		assertTrue(filteredTokens.contains("dog"));

		assertFalse(filteredTokens.contains("The"));
		assertFalse(filteredTokens.contains("the"));
		assertFalse(filteredTokens.contains("and"));
	}

	@Test
	void shouldFilterUsingNew() {
		StopWords stopWords = new StopWords(new String[] { "the", "by" });
		List<String> filteredTokens = stopWords.filter(TEXT);

		assertEquals(8, filteredTokens.size());
		assertTrue(filteredTokens.contains("quick"));
		assertTrue(filteredTokens.contains("and"));
		assertTrue(filteredTokens.contains("brown"));
		assertTrue(filteredTokens.contains("fox"));
		assertTrue(filteredTokens.contains("jumps"));
		assertTrue(filteredTokens.contains("over"));
		assertTrue(filteredTokens.contains("lazy"));
		assertTrue(filteredTokens.contains("dog"));

		assertFalse(filteredTokens.contains("The"));
		assertFalse(filteredTokens.contains("the"));
	}

	@Test
	void shouldFilterAdded() {
		List<String> filteredTokens = new StopWords().add("brown").filter(TEXT);

		assertEquals(6, filteredTokens.size());
		assertTrue(filteredTokens.contains("quick"));
		assertTrue(filteredTokens.contains("fox"));
		assertTrue(filteredTokens.contains("jumps"));
		assertTrue(filteredTokens.contains("over"));
		assertTrue(filteredTokens.contains("lazy"));
		assertTrue(filteredTokens.contains("dog"));

		assertFalse(filteredTokens.contains("brown"));
		assertFalse(filteredTokens.contains("The"));
		assertFalse(filteredTokens.contains("the"));
		assertFalse(filteredTokens.contains("and"));
		assertFalse(filteredTokens.contains("by"));
	}

	@Test
	void shouldFilterSet() {
		StopWords stopWords = new StopWords();
		stopWords.set(new String[] { "the", "quick", "brown" });
		List<String> filteredTokens = stopWords.filter(TEXT);

		assertEquals(8, filteredTokens.size());
		assertTrue(filteredTokens.contains("fox"));
		assertTrue(filteredTokens.contains("jumps"));
		assertTrue(filteredTokens.contains("over"));
		assertTrue(filteredTokens.contains("lazy"));
		assertTrue(filteredTokens.contains("dog"));
		assertTrue(filteredTokens.contains("and"));
		assertTrue(filteredTokens.contains("by"));

		assertFalse(filteredTokens.contains("quick"));
		assertFalse(filteredTokens.contains("brown"));
		assertFalse(filteredTokens.contains("The"));
		assertFalse(filteredTokens.contains("the"));
	}

	@Test
	void shouldFilterGeneralText() {
		List<String> filteredTokens = StopWords.generalText().filter(TEXT);

		assertEquals(6, filteredTokens.size());
		assertTrue(filteredTokens.contains("quick"));
		assertTrue(filteredTokens.contains("brown"));
		assertTrue(filteredTokens.contains("fox"));
		assertTrue(filteredTokens.contains("jumps"));
		assertTrue(filteredTokens.contains("lazy"));
		assertTrue(filteredTokens.contains("dog"));

		assertFalse(filteredTokens.contains("The"));
		assertFalse(filteredTokens.contains("the"));
		assertFalse(filteredTokens.contains("and"));
		assertFalse(filteredTokens.contains("over"));
		assertFalse(filteredTokens.contains("by"));
	}

	@Test
	void shouldFilterEnglish() {
		List<String> filteredTokens = StopWords.english().filter(TEXT);

		assertEquals(7, filteredTokens.size());
		assertTrue(filteredTokens.contains("quick"));
		assertTrue(filteredTokens.contains("brown"));
		assertTrue(filteredTokens.contains("fox"));
		assertTrue(filteredTokens.contains("jumps"));
		assertTrue(filteredTokens.contains("over"));
		assertTrue(filteredTokens.contains("lazy"));
		assertTrue(filteredTokens.contains("dog"));

		assertFalse(filteredTokens.contains("The"));
		assertFalse(filteredTokens.contains("the"));
		assertFalse(filteredTokens.contains("and"));
		assertFalse(filteredTokens.contains("by"));
	}

	@Test
	void shouldFilterInnoDB() {
		List<String> filteredTokens = StopWords.innoDb().filter(TEXT);

		assertEquals(8, filteredTokens.size());
		assertTrue(filteredTokens.contains("quick"));
		assertTrue(filteredTokens.contains("and"));
		assertTrue(filteredTokens.contains("brown"));
		assertTrue(filteredTokens.contains("fox"));
		assertTrue(filteredTokens.contains("jumps"));
		assertTrue(filteredTokens.contains("over"));
		assertTrue(filteredTokens.contains("lazy"));
		assertTrue(filteredTokens.contains("dog"));

		assertFalse(filteredTokens.contains("The"));
		assertFalse(filteredTokens.contains("the"));
		assertFalse(filteredTokens.contains("by"));
	}


	@Test
	void shouldHandleNull() {
		assertReturnsEmpty(null);
		assertReturnsEmpty("");
		assertReturnsEmpty("  ");
	}

	void assertReturnsEmpty(String text) {
		List<String> filteredTokens = StopWords.minimal().filter(text);

		assertTrue(filteredTokens.isEmpty());
	}
}

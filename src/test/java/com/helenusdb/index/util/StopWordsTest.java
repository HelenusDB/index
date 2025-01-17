package com.helenusdb.index.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class StopWordsTest {
	private static final String TEXT = "The quick brown fox jumps over the lazy dog, by and by.";

	@Test
	void shouldFilterDefault() {
		List<String> filteredTokens = StopWords.minimal().filter(TEXT);

		assertContains(filteredTokens, "quick", "brown", "fox", "jumps", "over", "lazy", "dog");
		assertNotContains(filteredTokens, "The", "the", "and", "by");
	}

	@Test
	void shouldFilterUsingNew() {
		StopWords stopWords = new StopWords(new String[] { "the", "by" });
		List<String> filteredTokens = stopWords.filter(TEXT);

		assertContains(filteredTokens, "quick", "brown", "fox", "jumps", "over", "lazy", "dog", "and");
		assertNotContains(filteredTokens, "The", "the", "by");
	}

	@Test
	void shouldFilterAdded() {
		List<String> filteredTokens = new StopWords().add("brown").filter(TEXT);

		assertContains(filteredTokens, "quick", "fox", "jumps", "over", "lazy", "dog");
		assertNotContains(filteredTokens, "The", "the", "brown", "and", "by");
	}

	@Test
	void shouldFilterSet() {
		StopWords stopWords = new StopWords();
		stopWords.set(new String[] { "the", "quick", "brown" });
		List<String> filteredTokens = stopWords.filter(TEXT);

		assertContains(filteredTokens, "fox", "jumps", "over", "lazy", "dog", "by", "and", "by");
		assertNotContains(filteredTokens, "The", "the", "quick", "brown");
	}

	@Test
	void shouldFilterGeneralText() {
		List<String> filteredTokens = StopWords.generalText().filter(TEXT);

		assertContains(filteredTokens, "quick", "brown", "fox", "jumps", "lazy", "dog");
		assertNotContains(filteredTokens, "The", "the", "and", "over", "by");
	}

	@Test
	void shouldFilterEnglish() {
		List<String> filteredTokens = StopWords.english().filter(TEXT);

		assertContains(filteredTokens, "quick", "brown", "fox", "jumps", "over", "lazy", "dog");
		assertNotContains(filteredTokens, "The", "the", "and", "by");
	}

	@Test
	void shouldFilterInnoDB() {
		List<String> filteredTokens = StopWords.innoDb().filter(TEXT);
		
		assertContains(filteredTokens, "quick", "brown", "fox", "jumps", "over", "lazy", "dog", "and");
		assertNotContains(filteredTokens, "The", "the", "by");
	}

	@Test
	void shouldHandleNull() {
		assertReturnsEmpty(null);
		assertReturnsEmpty("");
		assertReturnsEmpty("  ");
	}

	private void assertContains(List<String> filteredTokens, String... strings)
	{
		assertEquals(strings.length, filteredTokens.size());

		for (int i = 0; i < strings.length; i++)
		{
			assertEquals(strings[i], filteredTokens.get(i));
		}
	}

	private void assertNotContains(List<String> filteredTokens, String... strings)
	{
		Stream.of(strings).forEach(s -> assertFalse(filteredTokens.contains(s)));
	}

	private void assertReturnsEmpty(String text) {
		List<String> filteredTokens = StopWords.minimal().filter(text);

		assertTrue(filteredTokens.isEmpty());
	}
}

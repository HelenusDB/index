package com.helenusdb.katalog.trie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class PhraseIndexTest
{
	private static final String DOG_PHRASE = "the lazy brown dog takes a nap";
	private static final Integer DOG_INDEX = 0;
	private static final String FOX_PHRASE = "the quick brown fox jumps over the lazy dog";
	private static final Integer FOX_INDEX = 1;
	private static final String MOOSE_PHRASE = "the massive moose wants a muffin";
	private static final Integer MOOSE_INDEX = 2;
	private static final String MOUSE_PHRASE = "the tiny mouse wants a cookie";
	private static final Integer MOUSE_INDEX = 3;

	@Test
	void shouldConstructTrie()
	{
		PhraseIndex<String> index = new PhraseIndex<>();
		index.insert("banana", null);
		assertTrue(index.getIndicesFor("banana").contains(0));
	}

	@Test
	void shouldIndexStrings()
	{
		PhraseIndex<String> index = new PhraseIndex<>();
		index.insert(FOX_PHRASE, FOX_PHRASE)
			.insert(DOG_PHRASE, DOG_PHRASE)
			.insert(MOOSE_PHRASE, MOOSE_PHRASE)
			.insert(MOUSE_PHRASE, MOUSE_PHRASE);

		assertTrue(index.search("").isEmpty());
		assertTrue(index.search(null).isEmpty());
		assertTrue(index.search("notfound").isEmpty());

		assertTrue(index.search("DO").containsAll(List.of(FOX_PHRASE, DOG_PHRASE)));
		assertTrue(index.search("OG").containsAll(List.of(FOX_PHRASE, DOG_PHRASE)));
		assertEquals(List.of(FOX_PHRASE), index.search("fox"));
		assertEquals(List.of(MOOSE_PHRASE), index.search("moose"));
		assertEquals(List.of(MOUSE_PHRASE), index.search("mouse"));
		assertEquals(List.of(FOX_PHRASE), index.search("quick brown"));
		assertTrue(index.search("want").containsAll(List.of(MOOSE_PHRASE, MOUSE_PHRASE)));
		assertTrue(index.search("the").containsAll(List.of(FOX_PHRASE, DOG_PHRASE, MOOSE_PHRASE, MOUSE_PHRASE)));
	}

	@Test
	void shouldIndexNull()
	{
		PhraseIndex<Object> index = new PhraseIndex<>();
		index.insert(DOG_PHRASE, null)
			.insert(FOX_PHRASE, null)
			.insert(MOOSE_PHRASE, null)
			.insert(MOUSE_PHRASE, null);

		assertTrue(index.getIndicesFor("").isEmpty());
		assertTrue(index.getIndicesFor(null).isEmpty());
		assertTrue(index.getIndicesFor("notfound").isEmpty());

		assertTrue(index.getIndicesFor("do").containsAll(Set.of(DOG_INDEX, FOX_INDEX)));
		assertTrue(index.getIndicesFor("og").containsAll(Set.of(FOX_INDEX, DOG_INDEX)));
		assertEquals(Set.of(FOX_INDEX), index.getIndicesFor("fox"));
		assertEquals(Set.of(MOOSE_INDEX), index.getIndicesFor("moose"));
		assertEquals(Set.of(MOUSE_INDEX), index.getIndicesFor("mouse"));
		assertEquals(Set.of(FOX_INDEX), index.getIndicesFor("quick brown"));
		assertTrue(index.getIndicesFor("want").containsAll(Set.of(MOOSE_INDEX, MOUSE_INDEX)));
		assertTrue(index.getIndicesFor("the").containsAll(Set.of(FOX_INDEX, DOG_INDEX, MOOSE_INDEX, MOUSE_INDEX)));
	}

	@Test
	void shouldBeCaseSensitive()
	{
		final String upperFox = FOX_PHRASE.toUpperCase();
		PhraseIndex<String> index = new PhraseIndex<>(true);
		index.insert(FOX_PHRASE, FOX_PHRASE)
			.insert(DOG_PHRASE, DOG_PHRASE)
			.insert(MOOSE_PHRASE, MOOSE_PHRASE)
			.insert(MOUSE_PHRASE, MOUSE_PHRASE)
			.insert(upperFox, upperFox);

		assertTrue(index.search("do").containsAll(List.of(FOX_PHRASE, DOG_PHRASE)));
		assertTrue(index.search("og").containsAll(List.of(FOX_PHRASE, DOG_PHRASE)));
		assertFalse(index.search("og").containsAll(List.of(upperFox)));
		assertTrue(index.search("DO").containsAll(List.of(upperFox)));
		assertTrue(index.search("OG").containsAll(List.of(upperFox)));
		assertFalse(index.search("OG").containsAll(List.of(FOX_PHRASE, DOG_PHRASE)));
	}

	@Test
	void shouldSupportWildcards()
	{
		PhraseIndex<String> index = new PhraseIndex<>();
		index.insert(FOX_PHRASE, FOX_PHRASE)
			.insert(DOG_PHRASE, DOG_PHRASE)
			.insert(MOOSE_PHRASE, MOOSE_PHRASE)
			.insert(MOUSE_PHRASE, MOUSE_PHRASE);

		assertTrue(index.search("d?g").containsAll(List.of(FOX_PHRASE, DOG_PHRASE)));
		assertEquals(2, index.search("d?g").size());
		assertTrue(index.search("m*se").containsAll(List.of(MOUSE_PHRASE, MOOSE_PHRASE)));
		assertEquals(2, index.search("m*se").size());
		assertTrue(index.search("m*").containsAll(List.of(MOUSE_PHRASE, MOOSE_PHRASE, FOX_PHRASE)));
		assertEquals(3, index.search("m*").size());
		assertTrue(index.search("lazy*dog").containsAll(List.of(DOG_PHRASE, FOX_PHRASE)));
		assertEquals(2, index.search("lazy*dog").size());
	}
}

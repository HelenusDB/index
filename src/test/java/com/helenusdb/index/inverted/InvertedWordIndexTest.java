package com.helenusdb.index.inverted;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.helenusdb.index.inverted.InvertedWordIndex;

class InvertedWordIndexTest
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
	void shouldIndexPhrases()
	{
		InvertedWordIndex<String> index = new InvertedWordIndex<>();
		index.insert(FOX_PHRASE, FOX_PHRASE)
			.insert(DOG_PHRASE, DOG_PHRASE)
			.insert(MOOSE_PHRASE, MOOSE_PHRASE)
			.insert(MOUSE_PHRASE, MOUSE_PHRASE);

		assertTrue(index.search("").isEmpty());
		assertTrue(index.search(null).isEmpty());
		assertTrue(index.search("notfound").isEmpty());

		assertTrue(index.search("DOG").containsAll(List.of(FOX_PHRASE, DOG_PHRASE)));
		assertEquals(List.of(FOX_PHRASE), index.search("fox"));
		assertEquals(List.of(MOOSE_PHRASE), index.search("moose"));
		assertEquals(List.of(MOUSE_PHRASE), index.search("mouse"));
		assertEquals(List.of(FOX_PHRASE, DOG_PHRASE), index.search("quick brown"));
		assertTrue(index.search("wants").containsAll(List.of(MOOSE_PHRASE, MOUSE_PHRASE)));
		assertTrue(index.search("the").containsAll(List.of(FOX_PHRASE, DOG_PHRASE, MOOSE_PHRASE, MOUSE_PHRASE)));
	}

	@Test
	void shouldBeCaseSensitive() {
		final String upperFox = FOX_PHRASE.toUpperCase();
		InvertedWordIndex<String> index = new InvertedWordIndex<>(true);
		index.insert(FOX_PHRASE, FOX_PHRASE)
			.insert(DOG_PHRASE, DOG_PHRASE)
			.insert(MOOSE_PHRASE, MOOSE_PHRASE)
			.insert(MOUSE_PHRASE, MOUSE_PHRASE)
			.insert(upperFox, upperFox);

		assertTrue(index.search("dog").containsAll(List.of(FOX_PHRASE, DOG_PHRASE)));
		assertFalse(index.search("dog").containsAll(List.of(upperFox)));
		assertTrue(index.search("DOG").containsAll(List.of(upperFox)));
		assertFalse(index.search("DOG").containsAll(List.of(FOX_PHRASE, DOG_PHRASE)));
	}

	@Test
	void shouldIndexNull() {
		InvertedWordIndex<Object> index = new InvertedWordIndex<>();
		index.insert(DOG_PHRASE, null).insert(FOX_PHRASE, null).insert(MOOSE_PHRASE, null).insert(MOUSE_PHRASE, null);

		assertTrue(index.getIndicesFor("").isEmpty());
		assertTrue(index.getIndicesFor(null).isEmpty());
		assertTrue(index.getIndicesFor("notfound").isEmpty());

		assertTrue(index.getIndicesFor("dog").containsAll(Set.of(DOG_INDEX, FOX_INDEX)));
		assertEquals(Set.of(FOX_INDEX), index.getIndicesFor("fox"));
		assertEquals(Set.of(MOOSE_INDEX), index.getIndicesFor("moose"));
		assertEquals(Set.of(MOUSE_INDEX), index.getIndicesFor("mouse"));
		assertEquals(Set.of(FOX_INDEX, DOG_INDEX), index.getIndicesFor("quick brown"));
		assertTrue(index.getIndicesFor("wants").containsAll(Set.of(MOOSE_INDEX, MOUSE_INDEX)));
		assertTrue(index.getIndicesFor("the").containsAll(Set.of(FOX_INDEX, DOG_INDEX, MOOSE_INDEX, MOUSE_INDEX)));
	}

}
